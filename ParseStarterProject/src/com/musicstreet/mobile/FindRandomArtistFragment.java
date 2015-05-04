package com.musicstreet.mobile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Owner on 5/1/2015.
 */
public class FindRandomArtistFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_findrandomartist,container,false);
        RelativeLayout layout =(RelativeLayout) rootView.findViewById(R.id.findRandomArtistLayout);


        rootView.setOnTouchListener(new OnSwipeTouchListener() {


            public boolean onSwipeTop() {
                System.out.println("This goes up");
                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onSwipeRight() {
                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onSwipeLeft() {
                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onSwipeBottom() {
                Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return rootView;
    }

}
