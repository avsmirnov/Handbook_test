package ru.smirnov.handbook.adapter.datasource;

import android.database.Cursor;

import ru.smirnov.handbook.adapter.QuickAdapter;
import ru.smirnov.handbook.db.DB;

/**
 * Created by Alexander on 26.09.2014.
 */
public class NewsDataSource implements QuickAdapter.DataSource {
    private DB mDB;

    public NewsDataSource(DB db) {
        mDB = db;
    }

    @Override
    public Cursor getRowIds() {
        return mDB.getAllNewsRowIds();
    }

    @Override
    public Cursor getRowById(long rowId) {
        return mDB.getNewsById(rowId);
    }
}
