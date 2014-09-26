package ru.smirnov.handbook.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.smirnov.handbook.adapter.viewholder.EnterpriseViewHolder;

/**
 * Created by Alexander on 26.09.2014.
 */
public class HandbookAdapter extends QuickAdapter {
    public HandbookAdapter(Context context, DataSource dataSource) {
        super(context, dataSource);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null);
        EnterpriseViewHolder vh = new EnterpriseViewHolder();
        vh.id = cursor.getInt(cursor.getColumnIndex("id"));
        vh.name = (TextView) view.findViewById(android.R.id.text1);
        view.setTag(vh);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        EnterpriseViewHolder vh = (EnterpriseViewHolder) view.getTag();
        if (vh != null) {
            vh.id = cursor.getInt(cursor.getColumnIndex("id"));
            vh.name.setText(cursor.getString(cursor.getColumnIndex("name")));
        }
    }
}
