package com.musicstreet.mobile;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * ProfileFragment class represents the User's profile. The profile has a name,
 * profile picture, hometown, occupation/job description, and links that the user
 * may supply. This class is paired with the fragment_profile class.
 */
public class ProfileFragment extends Fragment {

    View rootView;
    TextView artistText;
    private ParseUser user;
    EditText profession;
    EditText hometown;
    EditText links;

    public ProfileFragment(ParseUser user){
        this.user = user;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        //user who has logged on


        //reference to profile image
        final ParseImageView profileImage = (ParseImageView) rootView.findViewById(R.id.imageView);

        //Artist Name TextView Reference
        artistText = (TextView) rootView.findViewById(R.id.textView9);

        profession = (EditText) rootView.findViewById(R.id.editText);

        hometown = (EditText) rootView.findViewById(R.id.editText2);

        links = (EditText) rootView.findViewById(R.id.editText3);

        //Retrieving User's ArtistName
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Media");
        query.whereEqualTo("user",user);


        //query to get the columns and data of the current user
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    for (ParseObject obj : parseObjects) {

                        //Sets the User's ArtistName
                        String artistname = (String) obj.get("artistname");

                        if (artistname == null) {
                            artistText.setText("Unknown");
                        } else {
                            artistText.setText(artistname);
                        }


                        //Sets the User's Profile Picture
                        ParseFile image = obj.getParseFile("profileImage");
                        String temp = "" + image;


                        if( image != null) {
                            profileImage.setParseFile(image);
                            profileImage.loadInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {
                                    if (e == null) {

                                    }
                                }
                            });
                        }
                        else {
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.enderman);

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();

                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] tempImage = stream.toByteArray();

                            ParseFile file = new ParseFile("enderman.png",tempImage);
                            profileImage.setParseFile(file);
                            profileImage.loadInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] bytes, ParseException e) {
                                    if(e == null) {

                                    }
                                     else {
                                        Toast.makeText(rootView.getContext(), "Error", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }






                        String professionString = (String) obj.get("profession");
                        profession.setText(professionString);

                        hometown.setText((String) obj.get("hometown"));


                        links.setText((String) obj.get("Facebook") + "\n"
                                + obj.get("Instagram") + "\n"
                                + obj.get("SoundCloud") + "\n"
                                + obj.get("Twitter"));
                    }
                }
            }
        });



        //testingText.setText("" + user.getBoolean("premiumSubscription"));


        return rootView;
    }

}
