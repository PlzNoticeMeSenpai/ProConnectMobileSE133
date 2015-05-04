package com.musicstreet.mobile;
import android.util.Log;
import com.parse.DeleteCallback;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;


/**
 * Created by johnfranklin on 4/5/15.
 * A small helper class to provide ways to manipulate the settings of a user without mucking with user queries.
 */
public class ProConnectSettings {
    /**
     * Exactly what it says on the tin. logs in the user with the username in question.
     * @param username username to log in
     * @param password password to log in with
     *
     */
    static void login(String username, String password)
    {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e == null) {
                    // Do stuff after successful login.
                } else {
                    // The login failed. Check error to see why.
                }
            }
        });}

    /**
     * registers a user with the given username and password
     * @param username username to register
     * @param registerPassword password to use for that user
     * @throws ParseException
     */
    static void register(String username, String registerPassword) throws ParseException {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(username);
        newUser.setPassword(registerPassword);
        newUser.signUp();
        /*InBackground(new SignUpCallback(){
            @Override
            public void done(ParseException e) {
                if(e == null)
                {
                    // move user to next page to add additional info, in register.js
                }
                else//email is already taken
                {
                    //response.error("Error: " + error.code + " " + error.message); //can't return to upper method. what would you like me to do?
                }
            }
        })*/;
    }

    /*
This function returns true if the account was successfully deleted.
It returns false if 1) the user's passwords do not match or if the user does not exist in the database.
*/

    /**
     * deletes the object if it exists and returns the text response to the GUI to be handled appropriately.
     * @param username username of the account to delete
     * @param deletePassword1 first password of the account to delete
     * @param deletePassword2 verification password of the account to delet
     * @return true if the passwords match and the user was found correctly, and returns false if the passwords don't match or if the user doesn't exist.
     */
    static boolean deleteAccount(String username, String deletePassword1, String deletePassword2) throws ParseException {

// Parse.User.current()

        if(!(deletePassword1.equals(deletePassword2))) {//shouldn't we be using hashes? or is that what we're comparing here
            return false;
        }
        ParseUser.logIn(username, deletePassword1);
        //ParseUser

        final ParseObject user = ParseUser.getCurrentUser();
        String s = (String)user.get("username");
        if(s.equals(username))
            user.delete();
        else
            return false;
        return true;
    }

    /**
     * changes the sub status for the current user in question.
     * @param subOn boolean status of whether or not the user should be subscribed.
     */
    static void changeSubStatus(boolean subOn) throws ParseException{
        ParseObject currentUser = ParseUser.getCurrentUser();//Will it have this information here?

        if(subOn){
            currentUser.put("premiumSubscription", true);
        }else{
            currentUser.put("premiumSubscription", false);
        }
        currentUser.save();
    }

    /**
     * sets whether or not users recieve email notifications for specific actions.
     * @param EmailOn whether or not emails should be sent.
     * @throws ParseException
     */
    static void changeEmailNotifications(boolean EmailOn) throws ParseException{
        ParseObject currentUser = ParseUser.getCurrentUser();//Will it have this information here?

        if(EmailOn){
            currentUser.put("emailNotifications", true);
        }else{
            currentUser.put("emailNotifications", false);
        }
        currentUser.save();
    }

    /**
     * a method to update the current user's email.
     * @param email1 first email
     * @param email2 second email
     * @return true if the emails match and the email was changed, false if they did not match.
     * @throws ParseException to pass up any message Parse sends through an exception
     */
    static boolean updateEmail(String email1, String email2) throws ParseException {
        ParseObject currentUser = ParseUser.getCurrentUser();//Will it have this information here?
        if(!email1.equals(email2))
        {
            return false;
        }

        currentUser.put("email", email1);
        currentUser.save();
        return true;
    }

    /**
     * sends an email from which the current user can change their password.
     * @throws ParseException
     */
    static void updatePassword() throws ParseException {
        ParseObject currentUser = ParseUser.getCurrentUser();
        /*Parse.User.*/
        ParseUser.requestPasswordReset((String) currentUser.get("username"));
        //What would you like me to do on error? I think just passing it up to another method is probably for the best; can't know what to do with it here.

    }


}
