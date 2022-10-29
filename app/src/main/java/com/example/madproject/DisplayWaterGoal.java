package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class DisplayWaterGoal extends AppCompatActivity {

    RecyclerView recyclerView;
    WaterAdapter waterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_water_goal);

        recyclerView = findViewById(R.id.recycler_view_water);
        setupRecyclerView();
    }

    public void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForWater().orderBy("date", Query.Direction.ASCENDING).limitToLast(1);
        FirestoreRecyclerOptions<WaterGoal> options = new FirestoreRecyclerOptions.Builder<WaterGoal>()
                .setQuery(query,WaterGoal.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        waterAdapter = new WaterAdapter(options,this);
        recyclerView.setAdapter(waterAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        waterAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        waterAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        waterAdapter.notifyDataSetChanged();
    }

}