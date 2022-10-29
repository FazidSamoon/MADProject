package com.example.madproject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Utility {

    static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReferenceForNotes(){
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Blogs");
//                .document(currentUser.getUid()).collection("my_notes");

    }

    static CollectionReference getCollectionReferenceForWater(){
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("WaterGoals");
//                .document(currentUser.getUid()).collection("my_notes");

    }

    static CollectionReference getCollectionReferenceForUserBlogs(){
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("WaterGoals");
//                .document(currentUser.getUid()).collection("my_notes");

    }

    static CollectionReference getCollectionReferenceForGlass(){
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("Glass");
//                .document(currentUser.getUid()).collection("my_notes");

    }


}
