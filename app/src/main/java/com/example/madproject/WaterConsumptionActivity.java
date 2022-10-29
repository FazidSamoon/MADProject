package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class WaterConsumptionActivity extends AppCompatActivity {

    TextView select, myGoal, createGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_consumption);

        select = findViewById(R.id.selectA);
        myGoal = findViewById(R.id.mygoalsA);
        createGoal = findViewById(R.id.createA);

        select.setOnClickListener((v) -> startActivity(new Intent(WaterConsumptionActivity.this, SelectCupActivity.class)));
        createGoal.setOnClickListener((v) -> startActivity(new Intent(WaterConsumptionActivity.this, CreateWaterGoalActivity.class)));
        myGoal.setOnClickListener((v) -> startActivity(new Intent(WaterConsumptionActivity.this, DisplayWaterGoal.class)));


    }
}