package ru.smirnov.handbook.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.smirnov.handbook.R;

/**
 * Created by Alexander on 26.09.2014.
 */
public class HandbookViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contacts, container, false);
    }
}
