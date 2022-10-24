package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class UserActivitiesActivity extends AppCompatActivity {
    MaterialButton newBlog, newPost;
    ImageView image;
    ImageButton like, comment, share;
    TextView likeText, user, timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activities);

        newBlog = findViewById(R.id.newBlog);
        newPost = findViewById(R.id.newPost);
        image = findViewById(R.id.postImage);
        like = findViewById(R.id.likeButton);
        comment = findViewById(R.id.commentButton);
        share = findViewById(R.id.shareButton);
        likeText = findViewById(R.id.likesText);
        user = findViewById(R.id.username);
        timeStamp = findViewById(R.id.timeStamp);

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreatePostActivity.class));
            }
        });

        newBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateBlogActivity.class));
            }
        });
    }
}