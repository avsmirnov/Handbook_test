package ru.smirnov.handbook.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.smirnov.handbook.R;
import ru.smirnov.handbook.db.DB;
import ru.smirnov.handbook.db.structure.DepartmentsStructure;
import ru.smirnov.handbook.db.structure.NewsStructure;

/**
 * Created by Alexander on 29.09.2014.
 */
public class DepartmentsViewFragment extends Fragment {
    private long mId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mId = bundle.getLong(NewsStructure.COL_ID);
        }

        if (mId < 0) { getFragmentManager().popBackStack(); }

        View view = inflater.inflate(R.layout.departments_view, container, false);

        DB db = new DB(getActivity());
        db.open();
        Cursor cursor = db.getDepartmentById(mId);
        if (cursor == null) { getFragmentManager().popBackStack(); }

        cursor.moveToFirst();

        ((TextView) view.findViewById(R.id.name))
                .setText(cursor.getString(cursor.getColumnIndex(DepartmentsStructure.COL_NAME)));
        ((TextView) view.findViewById(R.id.enterprise_name))
                .setText(cursor.getString(cursor.getColumnIndex(DepartmentsStructure.COL_ENTERPRISE_NAME)));
        ((TextView) view.findViewById(R.id.phone))
                .setText(cursor.getString(cursor.getColumnIndex(DepartmentsStructure.COL_PHONE)));
        ((TextView) view.findViewById(R.id.address))
                .setText(cursor.getString(cursor.getColumnIndex(DepartmentsStructure.COL_ADDRESS)));
        ((TextView) view.findViewById(R.id.description))
                .setText(cursor.getString(cursor.getColumnIndex(DepartmentsStructure.COL_DESCRIPTION)));

        db.close();
        return view;
    }

}
