package ru.smirnov.handbook.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ru.smirnov.handbook.R;
import ru.smirnov.handbook.activity.MainActivity;
import ru.smirnov.handbook.adapter.NewsAdapter;
import ru.smirnov.handbook.adapter.datasource.NewsDataSource;
import ru.smirnov.handbook.adapter.viewholder.NewsViewHolder;
import ru.smirnov.handbook.db.DB;
import ru.smirnov.handbook.db.structure.NewsStructure;

/**
 * Created by Alexander Smirnov on 25.09.2014.
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 */
public class NewsFragment extends Fragment {

    private DB mDB;
    private MainActivity mainActivity;

    public NewsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDB = new DB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mDB.open();

        NewsAdapter adapter = new NewsAdapter(getActivity(), new NewsDataSource(mDB));

        View view = inflater.inflate(R.layout.news, container, false);
        ListView mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(getOnItemClickListener());
        mListView.setItemsCanFocus(true);

        return view;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsViewHolder vh = (NewsViewHolder) view.getTag();

                if (vh != null) {
                    mainActivity.getBundle().putLong(NewsStructure.COL_ID, vh.id);
                    mainActivity.onNavigationDrawerItemSelected(3);
                }
            }
        };
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        mainActivity = null;
        mDB.close();
    }
}
