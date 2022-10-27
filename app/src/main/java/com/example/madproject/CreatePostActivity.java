package com.example.madproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class CreatePostActivity extends AppCompatActivity {
    EditText title, description, tags;
    MaterialButton button, image;
    Uri imageUri, downloadUri;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        title = findViewById(R.id.titleInputPost);
        description = findViewById(R.id.descriptionInputPost);
        tags = findViewById(R.id.tagsInputPost);
        image = findViewById(R.id.imageInputPost);
        button = findViewById(R.id.createPostButton);


        //selecting images
        image.setOnClickListener((v) -> selectImage());

        //uploading post
        button.setOnClickListener((v) -> uploadPost());
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
        DocumentReference documentReference = Utilities.getPostCollectionReference();
        Map<String, Object> post = new HashMap<>();
        Date date = new Date();

        post.put("title", title.getText().toString());
        post.put("description", description.getText().toString());
        post.put("tags", tags.getText().toString());
        post.put("imageUrl", downloadUri);
        post.put("createdAt", date);
        post.put("createdBy", Utilities.getCurrentUser().getDisplayName());


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