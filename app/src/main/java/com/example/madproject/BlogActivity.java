package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class BlogActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BlogAdapter blogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        recyclerView = findViewById(R.id.recycler_view);
        setupRecyclerView();

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