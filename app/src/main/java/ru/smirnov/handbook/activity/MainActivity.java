package ru.smirnov.handbook.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

import ru.smirnov.handbook.R;
import ru.smirnov.handbook.fragment.ContactsFragment;
import ru.smirnov.handbook.fragment.HandbookFragment;
import ru.smirnov.handbook.fragment.HandbookViewFragment;
import ru.smirnov.handbook.fragment.NavigationDrawerFragment;
import ru.smirnov.handbook.fragment.NewsFragment;
import ru.smirnov.handbook.fragment.NewsViewFragment;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getMenuTitle(0);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new HandbookFragment();
                break;
            case 1:
                fragment = new NewsFragment();
                break;
            case 2:
                fragment = new ContactsFragment();
                break;
            case 3:
                fragment = new NewsViewFragment();
                if (mBundle != null) {
                    fragment.setArguments(mBundle);
                    mBundle = null;
                }
                break;
            case 4:
                fragment = new HandbookViewFragment();
                if (mBundle != null) {
                    fragment.setArguments(mBundle);
                    mBundle = null;
                }
                break;
            default:
                fragment = new HandbookFragment();
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        if (position < 3) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack("fragBack")
                    .commit();
        }

        mTitle = getMenuTitle(position);
    }

    public Bundle getBundle() {
        mBundle = new Bundle();
        return mBundle;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        assert actionBar != null;

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private CharSequence getMenuTitle(int position) {
        if (navMenuTitles == null) {
            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        }

        return navMenuTitles.length > position ? navMenuTitles[position] : getTitle();
    }

}
