package ru.smirnov.handbook.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ru.smirnov.handbook.db.structure.NewsStructure;

/**
 * Created by Alexander on 26.09.2014.
 */
public class DB {
    private Context mContext;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    static final String TABLE_NEWS = "NEWS";
    static final String TABLE_DEPARTMENTS = "Departments";
    static final String TABLE_ENTERPRISE = "Enterprise";

    public DB(Context context) {
        mContext = context.getApplicationContext();
    }

    public void open() {
        close();

        mDBHelper = new DBHelper(mContext);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    public Cursor getAllEnterprise() {
        return mDB.query(TABLE_ENTERPRISE, null, null, null, null, null, null);
    }

    public Cursor getDepartmentByParentId(long id) {
        return mDB.query(TABLE_DEPARTMENTS, null, getSelectionCondition("enterprise_id"),
                getSelectionArgs(id), null, null, null);
    }

    public Cursor getDepartmentById(long id) {
        String query = "SELECT DP._id as _id, DP.name as name, " +
                "DP.enterprise_id as enterprise_id, EP.name as enterprise_name, " +
                "DP.description as description, DP.phone as phone, DP.address as address " +
                "FROM Departments as DP INNER JOIN Enterprise as EP on DP.enterprise_id = EP._id " +
                "where DP._id = ?";

        Log.d("TEST", "id = " + id);

        return mDB.rawQuery(query, getSelectionArgs(id));
    }


    public Cursor getAllNewsRowIds() {
        return mDB.query(TABLE_NEWS, new String[]{NewsStructure.COL_ID},
                null, null, null, null, null);
    }

    public Cursor getNewsById(long id) {
        return mDB.query(TABLE_NEWS, null, getSelectionCondition(NewsStructure.COL_ID),
                getSelectionArgs(id), null, null, null);
    }

    private String getSelectionCondition(String column_id) {
        return String.format("%s = ?", column_id);
    }

    private String[] getSelectionArgs(long id) {
        return new String[] {Long.toString(id)};
    }
}
