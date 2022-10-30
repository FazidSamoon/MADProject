package com.example.madproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class CreatePostActivity extends AppCompatActivity {
    EditText title, description, tags;
    MaterialButton button, image, deleteButton;
    Uri imageUri, downloadUri;
    StorageReference storageReference;
    String titleIntent, descriptionIntent, picIntent, tagsIntent, docIdIntent;
    boolean isEditMode = false;
    DocumentReference documentReference;
    TextView heading;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        title = findViewById(R.id.titleInputPost);
        description = findViewById(R.id.descriptionInputPost);
        tags = findViewById(R.id.tagsInputPost);
        image = findViewById(R.id.imageInputPost);
        button = findViewById(R.id.createPostButton);
        deleteButton = findViewById(R.id.button_delete);
        heading = findViewById(R.id.heading);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        //getIntents
        titleIntent = getIntent().getStringExtra("title");
        descriptionIntent = getIntent().getStringExtra("description");
        picIntent = getIntent().getStringExtra("imageUrl");
        tagsIntent = getIntent().getStringExtra("tags");
        docIdIntent = getIntent().getStringExtra("docId");


        if (docIdIntent != null && !docIdIntent.isEmpty()) {
            isEditMode = true;
            heading.setText("Edit Post");
            deleteButton.setVisibility(View.VISIBLE);
        }

        if (isEditMode) {
            description.setText(descriptionIntent);
            title.setText(titleIntent);
            tags.setText(tagsIntent);
        }

        //selecting images
        image.setOnClickListener((v) -> selectImage());

        //uploading post
        button.setOnClickListener((v) -> uploadPost());
        
        deleteButton.setOnClickListener((v) -> deletePost(docIdIntent));
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

    private void deletePost(String docIdIntent) {
        Utilities.getExistingCollectionReference(docIdIntent).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utilities.createToast(getApplicationContext(), "Note deleted successfully");
                    startActivity(new Intent(getApplicationContext(), UserActivitiesActivity.class));
                    finish();
                } else {
                    Utilities.createToast(getApplicationContext(), "Failed while deleting");
                }
            }
        });
    }

    //select image function
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK & result.getData() != null) {

                        Intent data = result.getData();
                        imageUri = data.getData();
                        uploadImage();
                    }
                }
            }
    );

    private void uploadImage() {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date date = new Date();
        String filename = Utilities.getCurrentUser().getUid() + "_" + title.getText().toString() + "_" + dateFormat.format(date);


        storageReference = FirebaseStorage.getInstance().getReference("images/" + filename);
        UploadTask uploadTask = storageReference.putFile(imageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //retrieving the download uri of the image to store in firestore

                Task<Uri> uriTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadUri = task.getResult();

                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
            }
        });
    }


    //upload post function
    private void uploadPost() {

        if (isEditMode) {
            documentReference = Utilities.getExistingCollectionReference(docIdIntent);
        } else {
            documentReference = Utilities.getPostCollectionReference();
        }

        Map<String, Object> post = new HashMap<>();
        Date date = new Date();
        List<FirebaseUser> list = new ArrayList<>();

        post.put("title", title.getText().toString());
        post.put("description", description.getText().toString());
        post.put("tags", tags.getText().toString());
        post.put("imageUrl", downloadUri);
        post.put("createdAt", date);
        post.put("createdBy", Utilities.getCurrentUser().getUid());
        post.put("likes", list);


        documentReference.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Utilities.createToast(getApplicationContext(), "Post successfully created");
                startActivity(new Intent(getApplicationContext(), UserActivitiesActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utilities.createToast(getApplicationContext(), e.getLocalizedMessage());
            }
        });
    }

}