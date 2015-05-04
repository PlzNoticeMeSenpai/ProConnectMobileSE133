package com.musicstreet.mobile;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 5/1/2015.
 */
public class EventListViewFragment extends ListFragment {

    private ArrayList<String> arrayList;
    private ArrayList<String> arrayObj;

    public EventListViewFragment() {

        arrayList = new ArrayList<>();
        arrayObj = new ArrayList<>();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        ParseQuery<ParseObject> query = new ParseQuery<>("Events");
        query.addAscendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                if(e == null && arrayList.size() == 0) {

                    for (ParseObject obj : parseObjects) {
                        String temp = (String) obj.get("EventName");
                        arrayObj.add(obj.getObjectId());

                        arrayList.add(temp);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                setListAdapter(adapter);

            }

        });

        return super.onCreateView(inflater,container,savedInstanceState);

    }

    public void onListItemClick(ListView l, View v,int position, long id) {

        Toast.makeText(getActivity(), arrayList.get(position).toString(), Toast.LENGTH_LONG).show();

        FragmentTransaction t = getFragmentManager().beginTransaction();

        t.replace(R.id.container, new EventFragment(arrayObj.get(position)));
        t.commit();



    }
}
