package com.example.madproject;

import static com.example.madproject.R.id.goal_submit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class CreateWaterGoalActivity extends AppCompatActivity {

    EditText goal, mode, completed;
    MaterialButton submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_water_goal);

        goal = findViewById(R.id.waterTarget);
        mode = findViewById(R.id.dailyMode);
        completed = findViewById(R.id.targetCompleted);
        submitBtn = findViewById(R.id.goal_submit);

        submitBtn.setOnClickListener((v) -> crateGoal());


    }

    public  void crateGoal(){
        String waterGoal = goal.getText().toString();
        String dailyMode = mode.getText().toString();
        String lastCompleted= completed.getText().toString();
        Date date = new Date();

        if(waterGoal == null || waterGoal.isEmpty()){
            goal.setError("Title is required");
            return;
        }

        WaterGoal waterGoal1 = new WaterGoal();
        waterGoal1.setWaterGoal(waterGoal);
        waterGoal1.setDailyMode(dailyMode);
        waterGoal1.setCompleted(lastCompleted);
        waterGoal1.setDate(date);

        saveGoalToFirebase(waterGoal1);

    }

    public void saveGoalToFirebase(WaterGoal waterGoal){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForWater().document();

        documentReference.set(waterGoal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(CreateWaterGoalActivity.this, "Goal added successfully");
                    finish();
                }else {
                    Utility.showToast(CreateWaterGoalActivity.this, "Goal cannot be added");

                }
            }
        });
    }
}