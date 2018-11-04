package com.wish.brachio.wishlist.control;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.QuerySnapshot;
import com.wish.brachio.wishlist.LoginActivity;
import com.wish.brachio.wishlist.model.database.FirebaseUserHandler;
import com.wish.brachio.wishlist.model.User;

public class PersistanceManager {
    private String TAG = "PersistanceManager";
    public void signIn(String email, String password, final Activity activity){
        final FirebaseUserHandler handler = new FirebaseUserHandler();
        Task task1 = handler.signIn(email, password, activity);
        task1.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //this will take user to main page on complete
                    handler.getSignedInUserInfo( activity );
                }
            }
        });
    }

    public void registerUser(User user, String password, final Activity activity){
        FirebaseUserHandler handler = new FirebaseUserHandler();
        Task task = handler.registerUser( user, password, activity );
        task.addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                }
            }
        } );
    }



}
