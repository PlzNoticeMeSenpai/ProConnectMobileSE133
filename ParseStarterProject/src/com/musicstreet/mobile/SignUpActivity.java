package com.musicstreet.mobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/**
 * SignUpActivity class is the sign up activity for the user interface. The user
 * is prompted to supply an email and password to register.
 * This class is paired with the activity_signup xml class.
 */
public class SignUpActivity extends Activity {

    private EditText email;
    private EditText password;
    private String emailTxt;
    private String passwordTxt;
    private Button returnToMain;
    private Button signUpButton;

    public void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText) findViewById(R.id.editTextEmailSignUP);
        password = (EditText) findViewById(R.id.editTextPWSignUp);

        signUpButton = (Button) findViewById(R.id.SignUpbuttonSignUp);
        returnToMain = (Button) findViewById(R.id.ReturnToLoginbuttonSignUP);


        //Returns the user to Log in Page
        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Registers the user to the application and sends the information to the database
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailTxt = email.getText().toString();
                passwordTxt = password.getText().toString();


                if(emailTxt.equals("") && passwordTxt.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please input a valid email and password",Toast.LENGTH_LONG).show();
                }
                else {
                    ParseUser user = new ParseUser();
                    user.setUsername(emailTxt);
                    user.setPassword(passwordTxt);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "Successfully Signed Up", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Failed to Sign Up", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });




    }
}
