package com.musicstreet.mobile;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Owner on 4/26/2015.
 */
public class ContactFragment extends ListFragment {

    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayList<ParseUser> userArray;
    private ParseUser user;


    public ContactFragment() {

        arrayList = new ArrayList<String>();
        userArray = new ArrayList<ParseUser>();

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = getQueryType();
        setQueryFactors(query, user);


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null && arrayList.size() == 0) {
                    for (ParseObject obj : parseObjects) {
                        ParseUser tempUser = getProperUser(obj);
                        userArray.add(tempUser);
                        String tempFriend = tempUser.getUsername();
                        arrayList.add(tempFriend);

                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
                setListAdapter(adapter);

            }
        });

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    public ParseQuery<ParseObject> getQueryType() {
        return ParseQuery.getQuery("Contact");
    }

    public ParseUser getProperUser(ParseObject obj) {
        return (ParseUser) obj.get("friend");
    }

    public void setQueryFactors(ParseQuery<ParseObject> query, ParseUser user) {
        query.whereEqualTo("me", user);
        query.include("friend");
    }


    public void onListItemClick(ListView l, View v,int position, long id) {

        Toast.makeText(getActivity(), arrayList.get(position).toString(), Toast.LENGTH_LONG).show();
        ParseUser tempUser = userArray.get(position);



        FragmentTransaction t = getFragmentManager().beginTransaction();

        t.replace(R.id.container, new ProfileFragment(tempUser));
        t.commit();



    }
}
