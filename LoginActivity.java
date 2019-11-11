package com.cs188group6.hiddengems_dsm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity {
    //this is variable declarations
    private TextView loginError; // adding a textview to display errors
    private Button loginButton;
    private EditText emailField;
    private EditText passwordField;
    private Button forgotButton, newAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //ties buttons to login Activity views so app can find them
        loginButton = (Button) findViewById(R.id.onboarding_login_button);
        newAccountButton = (Button) findViewById(R.id.new_account_button);
        emailField = (EditText) findViewById(R.id.email_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        forgotButton = (Button) findViewById(R.id.forgot_button);
        loginError = (TextView) findViewById(R.id.login_error);

        //connects data to realm for storage
        Realm realm = Realm.getDefaultInstance();

        // Receives user id from ForgotPassword and fills in email field
        String id = getIntent().getStringExtra("user");
        User user = realm.where(User.class).equalTo("id", id).findFirst();
        if (user != null) {
            emailField.setText(user.getEmail());
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // Finds the user that matches the email given
                        User user = realm.where(User.class).equalTo("email", emailField.getText().toString()).findFirst();
                        // Error Message if email doesn't match any users in database
                        if(user != null) {
                            // Error message if password is incorrect
                            if(!passwordField.getText().toString().equals(user.getPassword())) {
                                loginError.setText("Incorrect Password");
                                return;
                            }
                        } else {
                            loginError.setText("Incorrect email");
                            return;
                        }

                /*intents perform transitions and open Android services like camera
                context is the current location, MainActivity.class is where you want to go
                so now the login button opens the main activity*/
                        Intent intent = new Intent(getBaseContext(), HomePageActivity.class);
                        //add a parameter to intent (email) the getText()gets the user input.
                        if (user != null) {
                            intent.putExtra("user", user.getId());
                        }
                        startActivity(intent);
                    }
                });


            }
        });

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}




