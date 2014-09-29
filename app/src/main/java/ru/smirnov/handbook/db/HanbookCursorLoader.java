package ru.smirnov.handbook.db;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by Alexander on 29.09.2014.
 */
public class HanbookCursorLoader extends CursorLoader {
        DB mDB;

        public HanbookCursorLoader(Context context, DB db) {
            super(context);
            mDB = db;
        }

        @Override
        public Cursor loadInBackground() {
            return mDB.getAllEnterprise();
        }
}
