package ru.smirnov.handbook.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import ru.smirnov.handbook.R;
import ru.smirnov.handbook.activity.MainActivity;
import ru.smirnov.handbook.adapter.HandbookAdapter;
import ru.smirnov.handbook.db.DB;
import ru.smirnov.handbook.db.structure.DepartmentsStructure;
import ru.smirnov.handbook.db.structure.EnterpriseStructure;
import ru.smirnov.handbook.db.structure.NewsStructure;

/**
 * Created by Alexander Smirnov on 25.09.2014.
 */
public class HandbookFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private DB mDB;
    private HandbookAdapter mAdapter;
    private MainActivity mainActivity;

    public HandbookFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDB = new DB(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        mDB.open();
    }

    @Override
    public void onPause() {
        super.onPause();

        mDB.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDB.open();

        String[] groupFrom = {EnterpriseStructure.COL_NAME};
        int[] groupTo = {android.R.id.text1};

        String[] childFrom = {DepartmentsStructure.COL_NAME};
        int[] childTo = {android.R.id.text1};

        mAdapter = new HandbookAdapter(getActivity(),
                android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo,
                android.R.layout.simple_list_item_1, childFrom, childTo, mDB);

        getActivity().getLoaderManager().initLoader(0, null, this);

        ExpandableListView expListView = (ExpandableListView) inflater.inflate(R.layout.handbook, container, false);
        expListView.setAdapter(mAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mainActivity.getBundle().putLong(NewsStructure.COL_ID, id);
                mainActivity.onNavigationDrawerItemSelected(4);
                return false;
            }
        });

        return expListView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new MyCursorLoader(getActivity(), mDB);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.setGroupCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof MainActivity) {
            mainActivity = (MainActivity) activity;
        } else {
            throw new ClassCastException("Activity must be instance of MainActivity.");
        }
    }

    static class MyCursorLoader extends CursorLoader {
        DB mDB;

        public MyCursorLoader(Context context, DB db) {
            super(context);
            mDB = db;
        }

        @Override
        public Cursor loadInBackground() {
            return mDB.getAllEnterprise();
        }

    }
}
