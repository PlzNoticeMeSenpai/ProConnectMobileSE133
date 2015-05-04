package com.musicstreet.mobile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;
import java.util.zip.Inflater;

import android.widget.TableRow.*;
import android.widget.TextView;

/**
 * Created by Owner on 5/3/2015.
 */
public class PostFragment extends Fragment {

    private String postObjID;

    public PostFragment (String s) {

        postObjID = s;
        System.out.println(postObjID);
    }

    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post,container,false);

        final TableLayout t = (TableLayout) rootView.findViewById(R.id.tablePost);


        TableRow row1 = new TableRow(getActivity());
        row1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ));
        TextView col2 = new TextView(getActivity());
        col2.setText("Content");
        col2.setTextSize(30);
        col2.setPadding(10,10,10,10);
        row1.addView(col2);
        t.addView(row1, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Comment");
        query.whereEqualTo("objectId", postObjID);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e == null) {
                    System.out.println("this is executing");
                    for(ParseObject obj : parseObjects) {

                        System.out.println((String) obj.get("content"));
                        TableRow row = new TableRow(getActivity());
                        row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT ));

                        TextView col1 = new TextView(getActivity());
                        col1.setText((String) obj.get("content"));
                        col1.setTextSize(15);
                        col1.setSingleLine(false);
                        col1.setPadding(10,10,10,10);
                        row.addView(col1);
                        t.addView(row, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                    }
                }
            }
        });




        return rootView;

    }
}
