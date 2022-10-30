package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    MaterialButton button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        button = findViewById(R.id.getStartedButton);
        progressBar = findViewById(R.id.progressBar3);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeInProgress(true);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }, 1000);

        } else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            });
        }
    }

    public void changeInProgress(boolean progressStatus) {
        if (progressStatus) {
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }
}