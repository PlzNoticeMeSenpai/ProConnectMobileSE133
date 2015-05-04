package com.musicstreet.mobile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 5/1/2015.
 */
public class EventFragment extends Fragment {

    String obj;

    public EventFragment(String object) {

        obj = object;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_event_description, container, false);


        final EditText titleText = (EditText) rootView.findViewById(R.id.editTextTitleEvent);
        final EditText dateText = (EditText) rootView.findViewById(R.id.editTextDateEvent);
        final EditText timeText = (EditText) rootView.findViewById(R.id.editTextTimeEvent);
        final EditText locationText = (EditText) rootView.findViewById(R.id.editTextLocationEvent);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.whereEqualTo("objectId",obj);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                for (ParseObject object : parseObjects) {
                    titleText.setText((String) object.get("EventName"));
                    dateText.setText((String) object.get("EventDate"));
                    timeText.setText((String) object.get("EventTime"));
                    locationText.setText((String) object.get("EventLocation"));

                }
            }
        });



        return rootView;

    }
}
