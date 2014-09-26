package ru.smirnov.handbook.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Alexander on 26.09.2014.
 */
public class DB {
    private Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context context) {
        mContext = context.getApplicationContext();
    }

    public void open() {
        mDBHelper = new DBHelper(mContext);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    public SQLiteDatabase getDB() {
        return mDB;
    }

    public Cursor getDepartmentByParentId(int id) {
        return mDB.rawQuery("SELECT * FROM Department WHERE enterprise_id = ?", new String[]{Long.toString(id)});
    }

    public Cursor getDepartmentById(int id) {
        return mDB.rawQuery("SELECT * FROM Department WHERE id = ?", new String[]{Long.toString(id)});
    }

    public Cursor getNewsById(int id) {
        return mDB.rawQuery("SELECT * FROM News WHERE id = ?", new String[]{Long.toString(id)});
    }
}
