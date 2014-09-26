package ru.smirnov.handbook.adapter.datasource;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.smirnov.handbook.adapter.QuickAdapter;

/**
 * Created by Alexander on 26.09.2014.
 */
public class NewsDataSource implements QuickAdapter.DataSource {
    private SQLiteDatabase mDB;

    public NewsDataSource(SQLiteDatabase db) {
        mDB = db;
    }

    @Override
    public Cursor getRowIds() {
        return mDB.rawQuery("SELECT id FROM News", new String[]{});
    }

    @Override
    public Cursor getRowById(long rowId) {
        return mDB.rawQuery("SELECT * FROM News WHERE id = ?", new String[]{Long.toString(rowId)});
    }
}
