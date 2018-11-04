package com.wish.brachio.wishlist.model.database;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wish.brachio.wishlist.HomePageActivity;
import com.wish.brachio.wishlist.HubActivity;
import com.wish.brachio.wishlist.model.Item;
import com.wish.brachio.wishlist.model.User;
import com.wish.brachio.wishlist.model.Wishlist;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class FirebaseItemHandler {
    private String TAG = "FirebaseItemHandler";

    //adds items into a wishlist owned by user
    public void addItemstoWishlists(final User user, ArrayList<String> itemIds, final String wishKey, final Activity activity){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task task = db.collection("item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {

                                String key = document.getId();
                                String name = document.getString( "name" );
                                Date date = document.getDate( "date" );
                                HashMap<String, Boolean> itemMap = (HashMap) document.get( "contributors" );
                                //Changed with filled in item
                                Item item = new Item();
                                HashMap<String, Wishlist> wishHash = user.getWishlist();
                                Wishlist wishlist = wishHash.get(wishKey);
                                if (wishlist == null){
                                    wishlist = new Wishlist();
                                }
                                wishlist.addItem(item);
                            }
                            Intent intent = new Intent(activity, HomePageActivity.class);
                            activity.startActivity(intent);
                        } else{
                                Log.w( TAG, "Error getting documents.", task.getException() );
                        }
                    }
                });
        }
    }
