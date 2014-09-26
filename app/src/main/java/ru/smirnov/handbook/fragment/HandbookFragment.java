package ru.smirnov.handbook.fragment;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;

import ru.smirnov.handbook.R;
import ru.smirnov.handbook.adapter.HandbookAdapter;
import ru.smirnov.handbook.adapter.datasource.HandbookDataSource;
import ru.smirnov.handbook.db.DB;

/**
 * Created by Alexander Smirnov on 25.09.2014.
 */
public class HandbookFragment extends Fragment {

    private DB mDB;
    private ExpandableListView mListView;

    public HandbookFragment() {}

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

        HandbookAdapter adapter = new HandbookAdapter(getActivity(), new HandbookDataSource(mDB.getDB()));

        View view = inflater.inflate(R.layout.handbook, container, false);
        Log.e("TEST", "-| " + (view instanceof ExpandableListView));
        mListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        mListView.setAdapter(adapter);


        return view;
    }

    class MyAdapter extends SimpleCursorTreeAdapter {

        public MyAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
        }

        protected Cursor getChildrenCursor(Cursor groupCursor) {
            // получаем курсор по элементам для конкретной группы
            int idColumn = groupCursor.getColumnIndex("id");
            return mDB.getDepartmentById(groupCursor.getInt(idColumn));
        }
    }


}
