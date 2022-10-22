package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class UserProfileActivity extends AppCompatActivity {
    MaterialButton viewActivities, editProfile;
    EditText username, dob, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username = findViewById(R.id.username);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        viewActivities = findViewById(R.id.viewActivities);
        editProfile = findViewById(R.id.editProfile);

    }
}