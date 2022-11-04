package com.example.madproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class EditWorkoutActivity extends AppCompatActivity {


    EditText work1, work2, work3, work4;
    MaterialButton updateButton, deleteButton;
    String wo1, wo2, wo3, wo4,docId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);


        work1 = findViewById(R.id.editworkout1);
        work2 = findViewById(R.id.editworkout2);
        work3 = findViewById(R.id.editworkout3);
        work4 = findViewById(R.id.editdescription);

        updateButton = findViewById(R.id.button_update);
        deleteButton = findViewById(R.id.button_delete);

        wo1 = getIntent().getStringExtra("workout1");
        wo2 = getIntent().getStringExtra("workout2");
        wo3 = getIntent().getStringExtra("workout3");
        wo4 = getIntent().getStringExtra("workoutdes");
        docId = getIntent().getStringExtra("docId");

        work1.setText(wo1);
        work2.setText(wo2);
        work3.setText(wo3);
        work4.setText(wo4);

        updateButton.setOnClickListener((v) -> editWorkout());
        deleteButton.setOnClickListener((v) -> deleteWorkoutFromFirebase());


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


    public  void editWorkout(){

        String we1 = work1.getText().toString();
        String we2 = work2.getText().toString();
        String we3 = work3.getText().toString();
        String we4 = work4.getText().toString();
        Date date = new Date();


        Workout workout = new Workout();
        workout.setWorkout1(we1);
        workout.setWorkout2(we2);
        workout.setWorkout3(we3);
        workout.setWorkoutdes(we4);
        workout.setDate(date);

        saveBlogToFirebase(workout);
    }

    public void saveBlogToFirebase(Workout workout){
        DocumentReference documentReference;
        documentReference = WorkoutUtilities.getCollectionReferenceForWorkout().document(docId);

        documentReference.set(workout).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(EditWorkoutActivity.this, "workout added successfully");
                    finish();
//                    startActivity(new Intent(EditActivity.this, DisplayBlogActivity.class));
                }else {
                    Utility.showToast(EditWorkoutActivity.this, "workout cannot be added");

                }
            }
        });

    }


    void deleteWorkoutFromFirebase(){

        DocumentReference documentReference;
        documentReference = WorkoutUtilities.getCollectionReferenceForWorkout().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.showToast(EditWorkoutActivity.this, "workout is deleted");
                    finish();
                } else {
                    Utility.showToast(EditWorkoutActivity.this, "Failed to delete");

                }
            }
        });
    }


}