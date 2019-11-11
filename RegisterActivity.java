package com.cs188group6.hiddengems_dsm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    private Button registerButton, loginField;
    private EditText emailField;
    private EditText passwordField;
    private EditText nameField;
    public User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.register_button);
        emailField = (EditText) findViewById(R.id.email_field);
        passwordField = (EditText) findViewById(R.id.password_field);
        nameField = (EditText) findViewById(R.id.name_field);
        loginField = (Button) findViewById(R.id.login_extension);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Realm realm = Realm.getDefaultInstance();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        user = new User(); // when you click register, a new user is created
                        user.setEmail(emailField.getText().toString());
                        user.setPassword(passwordField.getText().toString());
                        user.setName(nameField.getText().toString());
                        user.setPoints(0);
                        user.setLevel(0);
                        realm.copyToRealmOrUpdate(user);
                    }
                });

                Intent intent = new Intent(view.getContext(), HomePageActivity.class); // Goes to HomePageActivity so you don't have to login right after registering
                intent.putExtra("user", user.getId());
                startActivity(intent);
            }
        });

        loginField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
