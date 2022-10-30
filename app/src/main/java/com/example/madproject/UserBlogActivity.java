package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.Query;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class UserBlogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BlogAdapter blogAdapter;
    MaterialButton blogBtn, postBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_blog);

        recyclerView = findViewById(R.id.recycler_view);
        blogBtn = findViewById(R.id.new_blog_button);
        postBtn = findViewById(R.id.new_post_button);

        blogBtn.setOnClickListener((v) -> startActivity(new Intent(UserBlogActivity.this, CreateBlogActivity.class)));
        postBtn.setOnClickListener((v) -> startActivity(new Intent(UserBlogActivity.this, CreatePostActivity.class)));

        setupRecyclerView();
        
        
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
        
    }



    public void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForNotes().orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Blog> options = new FirestoreRecyclerOptions.Builder<Blog>()
                .setQuery(query,Blog.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        blogAdapter = new BlogAdapter(options,this);
        recyclerView.setAdapter(blogAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        blogAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        blogAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        blogAdapter.notifyDataSetChanged();

    }
}