package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class EditProfileActivity extends AppCompatActivity {
    EditText username, password, confirmPassword, email;
    MaterialButton confirm, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = findViewById(R.id.userNameEdit);
        password = findViewById(R.id.passwordEdit);
        confirmPassword = findViewById(R.id.confirmPasswordEdit);
        email = findViewById(R.id.editEmail);
        confirm = findViewById(R.id.button_update);
        delete = findViewById(R.id.button_delete);
    }
}