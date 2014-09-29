package ru.smirnov.handbook.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ru.smirnov.handbook.R;
import ru.smirnov.handbook.adapter.viewholder.NewsViewHolder;
import ru.smirnov.handbook.db.structure.NewsStructure;

/**
 * Created by Alexander on 26.09.2014.
 */
public class NewsAdapter extends QuickAdapter {

    private static final String DT_FORMAT_OUTPUT = "d-MM-yyyy HH:mm";
    private static final String DT_FORMAT_INPUT = "yyyy-MM-dd HH:mm:ss.SSS";

    public NewsAdapter(Context context, DataSource dataSource) {
        super(context, dataSource);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_list_item, null);
        NewsViewHolder vh = new NewsViewHolder();
        vh.id = cursor.getInt(cursor.getColumnIndex(NewsStructure.COL_ID));
        vh.title = (TextView) view.findViewById(R.id.title);
        vh.datetime = (TextView) view.findViewById(R.id.time);
        view.setTag(vh);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        NewsViewHolder vh = (NewsViewHolder) view.getTag();
        if (vh != null) {
            vh.id = cursor.getInt(cursor.getColumnIndex(NewsStructure.COL_ID));
            vh.title.setText(cursor.getString(cursor.getColumnIndex(NewsStructure.COL_TITLE)));
            vh.datetime.setText(convertDT(cursor.getString(cursor.getColumnIndex(NewsStructure.COL_TIME))));
        }
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
