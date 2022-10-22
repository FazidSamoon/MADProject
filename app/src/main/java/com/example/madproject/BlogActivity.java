package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class BlogActivity extends AppCompatActivity {
    ImageButton like, comment, share;
    TextView username, timestamp, blogContent;
    ImageView blogImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        like = findViewById(R.id.likeButton);
        comment = findViewById(R.id.commentButton);
        share = findViewById(R.id.shareButton);

    }
}