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

public class DisplayWorkoutActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WorkoutAdapter1 workoutAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_workout);

        recyclerView = findViewById(R.id.recycler_view);
        setupRecyclerView();

    }

    public void setupRecyclerView(){
        Query query = WorkoutUtilities.getCollectionReferenceForWorkout().orderBy("date", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Workout> options = new FirestoreRecyclerOptions.Builder<Workout>()
                .setQuery(query,Workout.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        workoutAdapter1 = new WorkoutAdapter1(options,this);
        recyclerView.setAdapter(workoutAdapter1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        workoutAdapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        workoutAdapter1.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        workoutAdapter1.notifyDataSetChanged();
    }


}