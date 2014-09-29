package ru.smirnov.handbook.adapter;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorTreeAdapter;

import ru.smirnov.handbook.db.DB;

/**
 * Created by Alexander on 26.09.2014.
 */
public class HandbookAdapter extends SimpleCursorTreeAdapter {

    private final DB mDB;

    // Note that the constructor does not take a Cursor. This is done to avoid querying the
    // database on the main thread.
    public HandbookAdapter(Context context, int groupLayout,
                     String[] groupFrom, int[] groupTo, int childLayout,
                     String[] childFrom, int[] childTo, DB db) {
        super(context, null, groupLayout, groupFrom, groupTo,
                childLayout, childFrom, childTo);

        mDB = db;
    }

    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        int idColumn = groupCursor.getColumnIndex("_id");
        return mDB.getDepartmentByParentId(groupCursor.getLong(idColumn));
    }

}
