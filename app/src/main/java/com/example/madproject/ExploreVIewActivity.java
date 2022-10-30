package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ExploreVIewActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_view);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home: startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;
                    case R.id.explore: startActivity(new Intent(getApplicationContext(), ExploreVIewActivity.class));
                        return true;
                    case R.id.blog: startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                        return true;
                    case R.id.settings: startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        return true;
                }
                return false;
            }
        });

        recyclerView = findViewById(R.id.postRecycleView);
        setupRecycleView();
    }

    private void setupRecycleView() {

        Query query = FirebaseFirestore.getInstance().collection("posts").orderBy("createdAt", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postAdapter = new PostAdapter(options, this);
        recyclerView.setAdapter(postAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        postAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        postAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        postAdapter.notifyDataSetChanged();
    }
}