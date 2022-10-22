package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class RegisterActivity extends AppCompatActivity {
    EditText firstname, lastname, username, dob, email, password;
    MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = findViewById(R.id.firstName);
        lastname = findViewById(R.id.lastName);
        username = findViewById(R.id.userName);
        dob = findViewById(R.id.dateOfBirth);
        email = findViewById(R.id.emailAddress);
        password = findViewById(R.id.password);
        button = findViewById(R.id.signup);
    }
}