package com.musicstreet.mobile;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 5/3/2015.
 */


public class ForumFragment extends ListFragment {
    boolean done = true;
    private ArrayList<String> categoryArray;
    private ArrayList<String> commentArray;
    private ArrayList<String> threadArray;
    private ArrayList<String> categoryObjIDArray;
    private ArrayList<String> commentObjIDArray;
    private ArrayList<String> objIDArray;
    private String commentObjID;
    private int itemClickPosition;
    private int forumPosition;

    public ForumFragment(int pos, int threadPosition) {

        categoryObjIDArray = new ArrayList<>();
        commentArray = new ArrayList<>();
        threadArray = new ArrayList<>();
        commentObjIDArray = new ArrayList<>();
        categoryArray = new ArrayList<>();
        forumPosition = pos;
        itemClickPosition = threadPosition;
        objIDArray = new ArrayList<>();
    }
    public ForumFragment(int pos, int threadPosition, ArrayList<String> s)
    {
        objIDArray = new ArrayList<>();
        categoryObjIDArray = s;
        commentObjID = null;
        commentObjIDArray = new ArrayList<>();
        threadArray = new ArrayList<>();
        categoryArray = new ArrayList<>();
        forumPosition = pos;
        itemClickPosition = threadPosition;
        commentArray = new ArrayList<>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        switch (forumPosition) {
            case 0:
                getCategory();
                break;
            case 1:

                getThread();
                break;
            case 2:
                getPost();
                break;

        }


        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public List<String> getCategory() {

        ParseQuery<ParseObject> query = new ParseQuery<>("Comment");
        done = false;
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                if (e == null && categoryArray.size() == 0) {

                    for (ParseObject obj : parseObjects) {
                        if (obj.getBoolean("isCategory") == true) {
                            String tempID = (String) obj.getObjectId();
                            String temp = (String) obj.get("title");
                            categoryObjIDArray.add(tempID);
                            categoryArray.add(temp);
                        }
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categoryArray);
                setListAdapter(adapter);
                done = true;
            }

        });

        forumPosition = 1;

        return categoryArray;
    }

    public List<String> getThread() {

        ParseQuery<ParseObject> query = new ParseQuery<>("Comment");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                if (e == null && threadArray.size() == 0 && categoryObjIDArray.size() > 0) {
                    for (ParseObject obj : parseObjects) {
                        if (obj.getBoolean("isThread") == true) {
                            String tempObj = (String) obj.get("parentID");

                            if (tempObj.equals(categoryObjIDArray.get(itemClickPosition))) {
                                objIDArray.add((String) obj.getObjectId());
                                threadArray.add((String) obj.get("title"));
                            }


                        }
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, threadArray);
                setListAdapter(adapter);
                done = true;
            }

        });

        forumPosition = 2;

        return threadArray;
    }

    public List<String> getPost() {

        ParseQuery<ParseObject> query = new ParseQuery<>("Comment");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                if (e == null && commentArray.size() == 0) {
                    for (ParseObject obj : parseObjects) {
                        if (obj.getBoolean("isComment") == true) {
                            String tempObj1 = (String) obj.get("parentID");
                            System.out.println("ParentID Comments: " + tempObj1);

                            if(tempObj1.equals(categoryObjIDArray.get(itemClickPosition))) {
                                commentObjID = (String) obj.getObjectId();
                                commentArray.add((String) obj.get("title"));
                            }


                        }
                    }

                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, commentArray);
                setListAdapter(adapter);
                done = true;
            }

        });

        forumPosition = 3;

        return commentArray;
    }


    public synchronized void onListItemClick(ListView l, View v, int position, long id) {
        if(done) {
            switch (forumPosition) {
                case 1:
                    Toast.makeText(getActivity(), categoryArray.get(position).toString(), Toast.LENGTH_LONG).show();
                    //itemClickPosition = position;

                    FragmentTransaction t = getFragmentManager().beginTransaction();

                    t.replace(R.id.container, new ForumFragment(forumPosition, position, categoryObjIDArray));
                    t.commit();
                    break;
                case 2:

                    Toast.makeText(getActivity(), threadArray.get(position).toString(), Toast.LENGTH_LONG).show();

                    FragmentTransaction t1 = getFragmentManager().beginTransaction();
                    t1.replace(R.id.container, new ForumFragment(forumPosition, position, objIDArray));
                    t1.commit();

                    break;
                case 3:

                    FragmentTransaction t2 = getFragmentManager().beginTransaction();
                    t2.replace(R.id.container, new PostFragment(commentObjID));
                    t2.commit();

                    break;

            }
        }
    }
}
