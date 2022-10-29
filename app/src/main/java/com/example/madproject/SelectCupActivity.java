package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class SelectCupActivity extends AppCompatActivity {

    TextView gls1, gls2, gls3,gls4, gls5, gls6, gls_view;
    RecyclerView recyclerView;
    GlassAdapter glassAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cup);

        gls1 = findViewById(R.id.glass1);
        gls2 = findViewById(R.id.glass2);
        gls3 = findViewById(R.id.glass3);
        gls4 = findViewById(R.id.glass4);
        gls5 = findViewById(R.id.glass5);
        gls6 = findViewById(R.id.glass6);
        gls_view = findViewById((R.id.glass_view));
        recyclerView = findViewById(R.id.recycler_view_glass);
        setupRecyclerView();

        gls1.setOnClickListener((v) -> selectGlass("400"));
        gls2.setOnClickListener((v) -> selectGlass("500"));
        gls3.setOnClickListener((v) -> selectGlass("450"));
        gls4.setOnClickListener((v) -> selectGlass("200"));
        gls5.setOnClickListener((v) -> selectGlass("500"));
        gls6.setOnClickListener((v) -> selectGlass("500"));

    }

    public  void selectGlass(String amt){
        Glass glass = new Glass();
        Date date = new Date();

        glass.setAmount(amt);
        glass.setDate(date);

        saveGlassToFirebase(glass);
    }

    public void saveGlassToFirebase(Glass glass){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForGlass().document();

        documentReference.set(glass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(SelectCupActivity.this, "Glass added successfully");
                }else {
                    Utility.showToast(SelectCupActivity.this, "Glass cannot be added");

                }
            }
        });

    }

    public void setupRecyclerView(){
        Query query = Utility.getCollectionReferenceForGlass().orderBy("date", Query.Direction.ASCENDING).limitToLast(1);
        FirestoreRecyclerOptions<Glass> options = new FirestoreRecyclerOptions.Builder<Glass>()
                .setQuery(query,Glass.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        glassAdapter = new GlassAdapter(options,this);
        recyclerView.setAdapter(glassAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        glassAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        glassAdapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glassAdapter.notifyDataSetChanged();
    }

}