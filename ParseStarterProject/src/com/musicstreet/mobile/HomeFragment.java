package com.musicstreet.mobile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;


/**
 * HomeFragment class represents the main page after the user
 * has logged in. This class is paired with the fragment_home
 * xml class.
 */
public class HomeFragment extends Fragment {

    View rootView;
    TextView testingText;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView currentUserText = (TextView) rootView.findViewById(R.id.textView8);

        ParseUser user = ParseUser.getCurrentUser();

        currentUserText.setText("Hello... " + user.getUsername());
            /*Testing a fragment page */


        return rootView;
    }
}
