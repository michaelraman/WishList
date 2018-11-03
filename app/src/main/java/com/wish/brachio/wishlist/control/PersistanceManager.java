package com.wish.brachio.wishlist.control;

import android.app.Activity;

import com.wish.brachio.wishlist.model.FirebaseUserHandler;

public class PersistanceManager {

    public void signIn(String email, String password, Activity activity){
        FirebaseUserHandler handler = new FirebaseUserHandler();
        handler.signIn(email, password, activity);
    }
}
