package com.example.madproject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;


public class Utilities {
    static void createToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static DocumentReference getUserCollectionReference() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
        return firestore.collection("users").document(currentUser.getUid());
    }

    static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    static DocumentReference getPostCollectionReference() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        return firestore.collection("posts").document();
    }

    static DocumentReference getExistingCollectionReference(String docId) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        return firestore.collection("posts").document(docId);
    }

    static String timeStampToString(Timestamp timestamp){
        return new SimpleDateFormat("mm/dd/yy").format(timestamp.toDate());
    }

}
