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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class EditBlogActivity extends AppCompatActivity {


    EditText title, content, tags;
    MaterialButton updateButton, deleteButton, imageButton;
    String titleA, contentA, imageA, tagA,docId;
    Uri imageUri, downloadUri;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_blog);


        title= findViewById(R.id.edit_title);
        content = findViewById(R.id.edit_description);
        tags = findViewById(R.id.edit_tag);
        updateButton = findViewById(R.id.button_update);
        deleteButton = findViewById(R.id.button_delete);
        imageButton = findViewById(R.id.edit_image);

        titleA = getIntent().getStringExtra("title");
        contentA = getIntent().getStringExtra("description");
        tagA = getIntent().getStringExtra("tag");
        imageA = getIntent().getStringExtra("image");
        docId = getIntent().getStringExtra("docId");

        title.setText(titleA);
        content.setText(contentA);
        tags.setText(tagA);
        imageButton.setText(imageA);

        imageButton.setOnClickListener((v) -> selectImage());
        updateButton.setOnClickListener((v) -> editBlog());
        deleteButton.setOnClickListener((v) -> deleteBlogFromFirebase());
        
        
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
                            // Handle failures
                            // ...
                        }
                    }
                });
            }
        });
    }


    public  void editBlog(){

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
        blog.setBlogUser(Utilities.getCurrentUser().getUid());

        saveBlogToFirebase(blog);
    }

    public void saveBlogToFirebase(Blog blog){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document(docId);

        documentReference.set(blog).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utility.showToast(EditBlogActivity.this, "Blog added successfully");
                    finish();
//                    startActivity(new Intent(EditActivity.this, DisplayBlogActivity.class));
                }else {
                    Utility.showToast(EditBlogActivity.this, "Blog cannot be added");

                }
            }
        });

    }


    void deleteBlogFromFirebase(){

        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Utility.showToast(EditBlogActivity.this, "Note is deleted");
                    finish();
                } else {
                    Utility.showToast(EditBlogActivity.this, "Failed to delete");

                }
            }
            });
    }


}