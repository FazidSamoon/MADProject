package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Console;

public class ViewMealPlanActivity extends AppCompatActivity {
    TextView BreakfastType, LunchType, DinnerType, BreakfastDescription, LunchDescription, DinnerDescription,
            Breakfastquantity, Lunchquantity, Dinnerquantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meal_plan);


        BreakfastType = findViewById(R.id.Bf_type_input);
        LunchType = findViewById(R.id.Lu_type);
        DinnerType = findViewById(R.id.Din_type);
        BreakfastDescription = findViewById(R.id.Bf_des);
        LunchDescription = findViewById(R.id.Lu_des);
        DinnerDescription = findViewById(R.id.Din_des);
        Breakfastquantity = findViewById(R.id.Bf_quantity_input);
        Lunchquantity = findViewById(R.id.Lu_quantity);
        Dinnerquantity = findViewById(R.id.Din_quantity);


        DocumentReference documentReference = Utilities.getCollectionReferenceForMeals();

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    BreakfastType.setText(task.getResult().getString("breakfastType"));
                    LunchType.setText(task.getResult().getString("lunchType"));
                    DinnerType.setText(task.getResult().getString("dinnerType"));
                    BreakfastDescription.setText(task.getResult().getString("breakfastDescription"));
                    LunchDescription.setText(task.getResult().getString("lunchDescription"));
                    DinnerDescription.setText(task.getResult().getString("dinnerDescription"));
                   // Breakfastquantity.setText(task.getResult().getString("breakfastquantity"));
                    //Lunchquantity.setText(task.getResult().getString("lunchquantity"));
                   // Dinnerquantity.setText(task.getResult().getString("dinnerquantity"));

                } else {
                    System.out.println("Error");


                }
            }

            });

        }
    }
