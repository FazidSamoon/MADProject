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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateBlogActivity extends AppCompatActivity {
    EditText title, content, tags;
    MaterialButton button, image;
    Uri imageUri, downloadUri;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_blog);

        title = findViewById(R.id.blogTitle);
        content = findViewById(R.id.blogDescriptionInput);
        tags = findViewById(R.id.blogTagsInput);
        image = findViewById(R.id.blogImageInput);
        button = findViewById(R.id.createBLogButton);

        image.setOnClickListener((v) -> selectImage());
        button.setOnClickListener((v) -> createBlog());
    }


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
        String filename =  title.getText().toString() + "_" + dateFormat.format(date);


        storageReference = FirebaseStorage.getInstance().getReference("blogImages/" + filename);
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
                            //handle features
                        }
                    }
                });
            }
        });
    }

    public void createBlog(){

        String blogTitle = title.getText().toString();
        String blogContent = content.getText().toString();
        String blogTag = tags.getText().toString();
        String blogImage = String.valueOf(downloadUri);
        Date date = new Date();

        if(blogTitle == null || blogTitle.isEmpty()){
            title.setError("Title is required");
            return;
        }

        Blog blog = new Blog();
        blog.setTitle(blogTitle);
        blog.setContent(blogContent);
        blog.setImage(blogImage);
        blog.setTags(blogTag);
        blog.setDate(date);

        saveBlogToFirebase(blog);
    }

    public void saveBlogToFirebase(Blog blog){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document();

        documentReference.set(blog).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(CreateBlogActivity.this, "Blog added successfully");
                    finish();
                }else {
                    Utility.showToast(CreateBlogActivity.this, "Blog cannot be added");

                }
            }
        });
    }
}