package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
}