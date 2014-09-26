package ru.smirnov.handbook.adapter.datasource;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.smirnov.handbook.adapter.QuickAdapter;

/**
 * Created by Alexander on 26.09.2014.
 */
public class HandbookDataSource implements QuickAdapter.DataSource {

    private SQLiteDatabase mDB;

    public HandbookDataSource(SQLiteDatabase db) {
        mDB = db;
    }

    @Override
    public Cursor getRowIds() {
        return mDB.rawQuery("SELECT EP.id as id, EP.name as name, count(DP.id) " +
                "FROM Enterprise as EP " +
                "INNER JOIN Departments as DP on EP.id = DP.enterprise_id " +
                "GROUP BY DP.enterprise_id;", new String[]{});
    }

    @Override
    public Cursor getRowById(long rowId) {
        return mDB.rawQuery("SELECT * FROM Enterprise WHERE id = ?", new String[]{Long.toString(rowId)});
    }
}
