package com.musicstreet.mobile;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;



/**
 * ParseStarterProjectActivity is the main launcher activity class that is the first
 * activity that the user will experience. It prompts the user to either sign up with a
 * new account or allow the user to log in to his or her account.
 * This class is paired with the main xml class.
 */
public class WelcomeActivity extends Activity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginbutton;
    private Button signupButton;
    private String emailTxt;
    private String passwordTxt;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		ParseAnalytics.trackAppOpenedInBackground(getIntent());

        emailEditText = (EditText) findViewById(R.id.editTextEmailMain);
        passwordEditText = (EditText) findViewById(R.id.editTextPWMain);

        loginbutton = (Button) findViewById(R.id.LogInbuttonMain);
        signupButton = (Button) findViewById(R.id.SignUpbuttonMain);

        //Sign up
        signupButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                emailTxt = emailEditText.getText().toString();
                passwordTxt = passwordEditText.getText().toString();

                ParseUser.logInInBackground(emailTxt,passwordTxt, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(user !=null) {
                            Intent intent = new Intent(getApplicationContext(),Home.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Logged In",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),"So Sorry, You couldn't log in", Toast.LENGTH_SHORT).show();

                        }
                    }
                } );
            }
        });

	}
}
