package ru.smirnov.handbook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.smirnov.handbook.R;

/**
 * Created by Alexander Smirnov on 25.09.2014.
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 */
public class NewsFragment extends Fragment {

    public NewsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news, container, false);
    }
}
