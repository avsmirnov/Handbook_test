package ru.smirnov.handbook.fragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.smirnov.handbook.R;

/**
 * Created by Alexander Smirnov on 25.09.2014.
 * Fragment used for output Contacts information.
 */
public class ContactsFragment extends Fragment {

    public ContactsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts, container, false);

        Resources res = getResources();
        String text = String.format(res.getString(R.string.contacts));

        TextView contactsTextView = (TextView) view.findViewById(R.id.contacts);
        contactsTextView.setText(Html.fromHtml(text));

        return view;
    }
}
