package com.musicstreet.mobile;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

/**
 * ParseApplication class is a helper class to allow the connection between
 * the mobile application and to the online database, Parse.
 */
public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
      Parse.initialize(this, "iAkM7wKq1rqr60qYUUig2fbyNqoVmKgNo7S1pcd9", "NvTCkczQ6p3jAhxi6hYZbdLkaMf47fnRolVjBUhS");


    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
