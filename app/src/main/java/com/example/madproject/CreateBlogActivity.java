package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class CreateBlogActivity extends AppCompatActivity {
    EditText title, content, image, tags;
    MaterialButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blog);

        title = findViewById(R.id.blogTitle);
        content = findViewById(R.id.blogDescriptionInput);
        tags = findViewById(R.id.blogTagsInput);
        button = findViewById(R.id.createBLogButton);
    }
}