package com.musicstreet.mobile;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.TextView;

import com.parse.ParseUser;


/**
 * Home class represents the main controller for the other classes in the system.
 * It uses the NavigationDrawerFragment class that was predefined by the Android Studio
 * source to select which fragment the user will load depending on the user's choice.
 * This is paired with an activity_home xml class that has a menu drawer that will display
 * multiple options that the user can choose from.
 */
public class Home extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {


        Fragment objectFragment = null;

        switch (position) {
            case 0:
                objectFragment = new EventListViewFragment();
               break;
            case 1:
                objectFragment = new SearchFragment();
                break;
            case 2:
                objectFragment = new ProfileFragment(ParseUser.getCurrentUser());
                break;
            case 3:
                objectFragment = new ContactFragment();
                break;
            case 4:
                objectFragment = new SettingFragment();
                break;
            case 5:
                int pos = 0;
                objectFragment = new ForumFragment(pos,pos);
                break;
            case 6:
                objectFragment = new FindRandomArtistFragment();
                break;
            case 7:
                ParseUser.logOut();
                break;
            case 8:
                ParseUser.logOut();

                break;

        }



        // update the main content by replacing fragments
        /*
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit(); */
        FragmentManager fragmentManager = getFragmentManager();

        if (objectFragment != null) {
            fragmentManager.beginTransaction().replace(R.id.container,objectFragment).commit();
        }
        else if (position == 7 ) {

            Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(intent);
            this.finish();

        }
        else {
            ParseUser.logOut();

            finish();
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = "Contacts";
                break;
            case 5:
                mTitle = "Setting";
                break;
            case 6:
                mTitle = "Forum";
                break;
            case 7:
                mTitle = "Find Random Artist";
                break;
            case 8:
                mTitle = "Sign out";
                break;
            case 9:
                mTitle = "Exit";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
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
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
