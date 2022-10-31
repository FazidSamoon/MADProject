package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class NutrientMainActivity extends AppCompatActivity {
    LinearLayout ViewPlan, CreatePlan, EditPlan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrient_main);

        ViewPlan = findViewById(R.id.view_plan);
        CreatePlan = findViewById(R.id.create_plan);
        EditPlan = findViewById(R.id.edit_plan);

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

        ViewPlan.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), ViewMealPlanActivity.class)));
        CreatePlan.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), CreateMealPlanActivity.class)));
        EditPlan.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EditMealPlanActivity.class)));
    }
}