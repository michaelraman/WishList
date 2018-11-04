package com.wish.brachio.wishlist.control;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.wish.brachio.wishlist.HubActivity;
import com.wish.brachio.wishlist.model.FirebaseUserHandler;
import com.wish.brachio.wishlist.model.User;

public class PersistanceManager {

    public void signIn(String email, String password, final Activity activity){
        FirebaseUserHandler handler = new FirebaseUserHandler();
        Task task = handler.signIn(email, password, activity);
        task.addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //TODO way to get get all User info?
                    Intent intent = new Intent(activity, HubActivity.class);
                    activity.startActivity(intent);
                } 
            }
        } );
    }

    public void registerUser(User user){

    }
}
