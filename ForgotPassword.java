package com.cs188group6.hiddengems_dsm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;

public class ForgotPassword extends AppCompatActivity{
    private TextView textView, textView2, errorMessage;
    private EditText emailField, passwordField, confirmPasswordField;
    private Button changeButton, registerButton; // changing updateButton to changeButton
    private String newPassword, confirmPassword;
    private Boolean success;
    public User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_forgot);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2); // textview to match Courtney's app design
        errorMessage = findViewById(R.id.error);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        confirmPasswordField = findViewById(R.id.confirm_field);
        changeButton = findViewById(R.id.change_button); // changed id to reflect what the button is called in Courtney's app design
        registerButton = findViewById(R.id.no_account_button);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        success = false;
                        newPassword = passwordField.getText().toString();
                        confirmPassword = confirmPasswordField.getText().toString();
                        // Finds the user that matches the email given
                        user = realm.where(User.class).equalTo("email", emailField.getText().toString()).findFirst();
                        // Error Message if email doesn't match any users in database
                        if (user != null) {
                            // Error message if passwords don't match
                            if (!newPassword.equals(confirmPassword)) {
                                errorMessage.setText("Passwords don't match");
                            } else {
                                user.setPassword(newPassword);
                                realm.copyToRealmOrUpdate(user);
                                success = true;
                            }
                        } else {
                            errorMessage.setText("User does not exist");
                        }
                    }
                });
                if (user != null && success) {
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    intent.putExtra("user", user.getId()); // sends the user to the LoginActivity
                    startActivity(intent);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
