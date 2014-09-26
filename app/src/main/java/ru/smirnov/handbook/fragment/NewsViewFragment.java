package ru.smirnov.handbook.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import ru.smirnov.handbook.R;
import ru.smirnov.handbook.db.DB;

/**
 * Created by Alexander on 26.09.2014.
 */
public class NewsViewFragment extends Fragment {
    private static final String DT_FORMAT_OUTPUT = "d-MM-yyyy HH:mm";
    private static final String DT_FORMAT_INPUT = "yyyy-MM-dd HH:mm:ss.SSS";

    private int mId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getInt("id");
        }

        if (mId < 0) {
            Log.e("TEST", "id below 0");
            getFragmentManager().popBackStack();
        }

        View view = inflater.inflate(R.layout.news_view, container, false);

        DB db = new DB(getActivity());
        db.open();
        Cursor cursor = db.getNewsById(mId);

        Map<String, String> map = new HashMap<String, String>();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    for (String cn : cursor.getColumnNames()) {
                        map.put(cn, cursor.getString(cursor.getColumnIndex(cn)));
                    }
                } while(cursor.moveToNext());
            }
        } else {
            Log.e("TEST", "Cursor is null");
            getFragmentManager().popBackStack();
        }

        ((TextView) view.findViewById(R.id.title)).setText(map.get("title"));
        ((TextView) view.findViewById(R.id.small_content)).setText(map.get("small_content"));
        ((TextView) view.findViewById(R.id.content)).setText(map.get("content"));
        ((TextView) view.findViewById(R.id.time)).setText(convertDT(map.get("time")));
        ((TextView) view.findViewById(R.id.author)).setText(map.get("author"));

        db.close();
        return view;
    }

    public String convertDT(String dt) {
        SimpleDateFormat format_input = new SimpleDateFormat(DT_FORMAT_INPUT);
        SimpleDateFormat format_output = new SimpleDateFormat(DT_FORMAT_OUTPUT);
        String dt_output = dt;
        try {
            dt_output = format_output.format(format_input.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dt_output;
    }
}
