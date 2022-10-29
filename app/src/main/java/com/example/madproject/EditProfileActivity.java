package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    EditText username, password, confirmPassword, email;
    MaterialButton confirm, delete;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = findViewById(R.id.userNameEdit);
        password = findViewById(R.id.passwordEdit);
        confirmPassword = findViewById(R.id.confirmPasswordEdit);
        email = findViewById(R.id.editEmail);
        confirm = findViewById(R.id.button_update);
        delete = findViewById(R.id.button_delete);

        documentReference = Utilities.getUserCollectionReference();
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    username.setText(task.getResult().get("username").toString());
                    email.setText(task.getResult().get("email").toString());
                }
            }
        });

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

        confirm.setOnClickListener((v) -> handleUpdate());
    }

    private void handleUpdate() {
        Map<String, Object> editedValues = new HashMap<>();

        String editUsername = username.getText().toString();
        String editEmail = email.getText().toString();
        String editPassword = password.getText().toString();
        String confirmEditPassword = confirmPassword.getText().toString();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        if (!editPassword.isEmpty()) {
            if (confirmEditPassword.isEmpty()) {
                confirmPassword.setError("Confirm password cannot be null");
                return;
            }

            boolean validate = validatePassword(editPassword, confirmEditPassword);

            if (validate) {
                Utilities.getCurrentUser().updatePassword(editPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if (!editUsername.isEmpty()) editedValues.put("username", editUsername);
                        if (!editEmail.isEmpty()) editedValues.put("email", editEmail);


                        if (!editedValues.isEmpty()) Utilities.getUserCollectionReference().update(editedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Utilities.createToast(getApplicationContext(),"Successfully edited the profile");
                                firebaseAuth.signOut();
                                startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Utilities.createToast(getApplicationContext(), e.getLocalizedMessage());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("test fail");
                        System.out.println(e.getLocalizedMessage());
                    }
                });
            }
        } else {
            if (!editUsername.isEmpty()) editedValues.put("username", editUsername);
            if (!editEmail.isEmpty()) editedValues.put("email", editEmail);

            if (!editedValues.isEmpty()) Utilities.getUserCollectionReference().update(editedValues).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Utilities.createToast(getApplicationContext(),"Successfully edited the profile");
                    startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Utilities.createToast(getApplicationContext(), e.getLocalizedMessage());
                }
            });
        }
    }

    private boolean validatePassword(String pwd, String confirmPwd) {
        if (pwd.length() < 6) {
            password.setError("Password should contain at least 6 characters");
            return false;
        }
        if (!pwd.equals(confirmPwd)) {
            confirmPassword.setError("Password mismatch");
            return false;
        }
        return true;
    }
}