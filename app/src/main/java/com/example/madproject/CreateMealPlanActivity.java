package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateMealPlanActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EditText BreakfastType, LunchType, DinnerType, BreakfastDescription, LunchDescription, DinnerDescription,
            Breakfastquantity, Lunchquantity, Dinnerquantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal_plan);
        BreakfastType = findViewById(R.id.Bf_type_input);
        LunchType = findViewById(R.id.Lu_type);
        DinnerType = findViewById(R.id.Din_type);
        BreakfastDescription = findViewById(R.id.Bf_des);
        LunchDescription = findViewById(R.id.Lu_des);
        DinnerDescription = findViewById(R.id.Din_des);
        Breakfastquantity = findViewById(R.id.Bf_quantity_input);
        Lunchquantity = findViewById(R.id.Lu_quantity);
        Dinnerquantity = findViewById(R.id.Din_quantity);

    }

    public void createMealPlan() {
        //get the data from the form and save it to the database
        String breakfastType = BreakfastType.getText().toString();
        String lunchType= LunchType.getText().toString();
        String dinnerType = DinnerType.getText().toString();
        int breakfastQuantity = Integer.parseInt(Breakfastquantity.getText().toString());
        int lunchQuantity = Integer.parseInt(Lunchquantity.getText().toString());
        int dinnerQuantity = Integer.parseInt(Dinnerquantity.getText().toString());
        String breakfastDescription = BreakfastDescription.getText().toString();
        String lunchDescription = LunchDescription.getText().toString();
        String dinnerDescription = DinnerDescription.getText().toString();

        Meal meal = new Meal();
        meal.setBreakfastType(breakfastType);
        meal.setLunchType(lunchType);
        meal.setDinnerType(dinnerType);
        meal.setBreakfastQuantity(breakfastQuantity);
        meal.setLunchQuantity(lunchQuantity);
        meal.setDinnerQuantity(dinnerQuantity);
        meal.setBreakfastDescription(breakfastDescription);
        meal.setLunchDescription(lunchDescription);
        meal.setDinnerDescription(dinnerDescription);

        saveMealToFirebase(meal);

    }
    void saveMealToFirebase(Meal meal) {
        DocumentReference documentReference = Utilities.CollectionReferenceForMeals().document();
        documentReference.set(meal).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateMealPlanActivity.this, "Meal Plan Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateMealPlanActivity.this, ViewMealPlanActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CreateMealPlanActivity.this, "Error Creating Meal Plan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}