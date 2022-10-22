package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class EditPostActivity extends AppCompatActivity {
    EditText title, description, tags, image;
    MaterialButton update, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        title = findViewById(R.id.editPostTitle);
        description = findViewById(R.id.editPostDescription);
        tags = findViewById(R.id.editPostTags);
        image = findViewById(R.id.editPostPicture);
        update = findViewById(R.id.button_update);
        delete = findViewById(R.id.button_delete);
    }
}