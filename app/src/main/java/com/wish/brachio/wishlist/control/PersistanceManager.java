package com.wish.brachio.wishlist.control;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wish.brachio.wishlist.FriendWishListActivity;
import com.wish.brachio.wishlist.LoginActivity;
import com.wish.brachio.wishlist.model.database.FirebaseItemHandler;
import com.wish.brachio.wishlist.model.database.FirebaseUserHandler;
import com.wish.brachio.wishlist.model.User;
import com.wish.brachio.wishlist.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PersistanceManager {
    private String TAG = "PersistanceManager";
    public static ArrayList<String> itemIDCallback = new ArrayList();
    public static int friendCount = 0;

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

    public void getFriendWishLists(final Activity activity){
        FirebaseItemHandler itemHandler = new FirebaseItemHandler();
        FirebaseUserHandler userHandler = new FirebaseUserHandler();
        User currentUser = CurrentUser.getInstance().getUser();
        HashMap<String, User> map = currentUser.getFriends();
        ArrayList<User> friends = new ArrayList(map.values());
        friendCount = friends.size();
        for (User user : friends){
            itemIDCallback.clear();
            Task task = userHandler.getWishList( user, this );
            task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    friendCount--;
                    if (friendCount == 0){
                        Intent intent = new Intent(activity, FriendWishListActivity.class );
                        activity.startActivity(intent);
                    }

                }
            });

        }
    }







}
