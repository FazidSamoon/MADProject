package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class CreateWorkoutActivity extends AppCompatActivity {

    EditText work1, work2, work3, workde;
    MaterialButton button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        work1 = findViewById(R.id.workout1);
        work2 = findViewById(R.id.workout2);
        work3 = findViewById(R.id.workout3);
        workde= findViewById(R.id.description);
        button = findViewById(R.id.createButton);

        button.setOnClickListener((V) -> createWorkout());
    }

    public void createWorkout(){

        String w1 = work1.getText().toString();
        String w2 = work2.getText().toString();
        String w3 = work3.getText().toString();
        String des = workde.getText().toString();
        Date date= new Date();

        Workout workout = new Workout();
        workout.setWorkout1(w1);
        workout.setWorkout2(w2);
        workout.setWorkout3(w3);
        workout.setWorkoutdes(des);
        workout.setDate(date);

        saveBlogToFirebase(workout);
    }

    public void saveBlogToFirebase(Workout workout) {
        DocumentReference documentReference;
        documentReference = WorkoutUtilities.getCollectionReferenceForWorkout().document();

        documentReference.set(workout).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    WorkoutUtilities.showToast(CreateWorkoutActivity.this,"Workout plan created successfully");
                    startActivity(new Intent(getApplicationContext(),DisplayWorkoutActivity.class));
                } else {
                    WorkoutUtilities.showToast(CreateWorkoutActivity.this,"Workout plan cannot be created");

                }

            }
        });

    }}