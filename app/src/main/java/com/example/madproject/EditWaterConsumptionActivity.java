package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class EditWaterConsumptionActivity extends AppCompatActivity {

    EditText goal, mode, completed;
    MaterialButton updateButton, deleteButton;
    String goalA, modeA, completedA,docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_water_consumption);

        goal = findViewById(R.id.editWaterTarget);
        mode = findViewById(R.id.editDailyMode);
        completed = findViewById(R.id.editTargetCompleted);
        updateButton = findViewById(R.id.button_update_water);
        deleteButton = findViewById(R.id.button_delete_water);

        goalA = getIntent().getStringExtra("waterGoal");
        modeA = getIntent().getStringExtra("dailyMode");
        completedA = getIntent().getStringExtra("completed");
        docId = getIntent().getStringExtra("docId");

        goal.setText(goalA);
        mode.setText(modeA);
        completed.setText(completedA);

        updateButton.setOnClickListener((v) -> editWater());
        deleteButton.setOnClickListener((v) -> deleteWaterFromFirebase());
    }

    public  void editWater(){

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
                    Utility.showToast(EditWaterConsumptionActivity.this, "Goal added successfully");
                    finish();
                }else {
                    Utility.showToast(EditWaterConsumptionActivity.this, "Goal cannot be added");

                }
            }
        });
    }

    void deleteWaterFromFirebase(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForWater().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(EditWaterConsumptionActivity.this, "Note is deleted");
                    finish();
                }else {
                    Utility.showToast(EditWaterConsumptionActivity.this, "Failed to delete");

                }
            }
        });
    }
}