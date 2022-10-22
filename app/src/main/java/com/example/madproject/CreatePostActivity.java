package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class CreatePostActivity extends AppCompatActivity {
    EditText title, description, tags, image;
    MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        title = findViewById(R.id.titleInputPost);
        description = findViewById(R.id.descriptionInputPost);
        tags = findViewById(R.id.tagsInputPost);
        image = findViewById(R.id.imageInputPost);
        button = findViewById(R.id.createPostButton);
    }
}