package ru.smirnov.handbook.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Alexander on 26.09.2014.
 * QuickAdapter - very quick adapter, use Cursor
 */
public abstract class QuickAdapter extends BaseAdapter {

    private final DataSource mDataSource;
    private int mSize = 0;
    private Cursor mRowIds = null;
    private final Context mContext;

    public QuickAdapter(Context context, DataSource dataSource) {
        mDataSource = dataSource;
        mContext = context;
        doQuery();
    }

    private void doQuery() {
        if (mRowIds != null) {
            mRowIds.close();
        }

        mRowIds = mDataSource.getRowIds();
        mSize = mRowIds.getCount();
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public Object getItem(int position) {
        if (mRowIds.moveToPosition(position)) {
            long rowId = mRowIds.getLong(0);
            return mDataSource.getRowById(rowId);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if (mRowIds.moveToPosition(position)) {
            return mRowIds.getLong(0);
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mRowIds.moveToPosition(position);
        long rowId = mRowIds.getLong(0);
        Cursor cursor = mDataSource.getRowById(rowId);
        cursor.moveToFirst();
        View v;
        if (convertView == null) {
            v = newView(mContext, cursor, parent);
        } else {
            v = convertView;
        }
        bindView(v, mContext, cursor);
        cursor.close();
        return v;
    }

    public abstract View newView(Context context, Cursor cursor, ViewGroup parent);
    public abstract void bindView(View view, Context context, Cursor cursor);

    public interface DataSource {
        Cursor getRowIds();
        Cursor getRowById(long rowId);
    }
}
