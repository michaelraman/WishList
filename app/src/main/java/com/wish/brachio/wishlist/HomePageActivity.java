package com.wish.brachio.wishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.wish.brachio.wishlist.control.PersistanceManager;
import com.wish.brachio.wishlist.model.Item;
import com.wish.brachio.wishlist.model.User;
import com.wish.brachio.wishlist.model.Wishlist;
import com.wish.brachio.wishlist.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        setContentView(R.layout.content_home_page);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        });  */

        ImageButton mCreateWishList = (ImageButton) findViewById(R.id.imageButton5);
        mCreateWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateWishList();
            }
        });


        Button friendButton = (Button) findViewById(R.id.friends_list_button);
        final Activity activity = this;
        friendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PersistanceManager manager = new PersistanceManager();
                manager.getFriendWishLists( activity, FriendWishListActivity.class);
            }
        });

        User user = CurrentUser.getInstance().getUser();
        HashMap<String, User> friend = user.getFriends();

        for (Map.Entry<String, User> item : friend.entrySet()) {
            String e = item.getKey();
            HashMap<String, Wishlist> value = item.getValue().getWishlist();
            for (Map.Entry<String, Wishlist> wish : value.entrySet()) {
                String name = wish.getKey();
                ArrayList<Item> items = wish.getValue().getItems();
                Log.d("pls", name);
                //Log.d("pls", items);
            }
            Log.d("pls", item.getKey());
        }
    }

    private void attemptCreateWishList() {
        Log.e("pls","Opening wishlist");
        Intent intent = new Intent(this, YourWishlistActivity.class);
        startActivity(intent);
    }

    private void attemptViewFriendsWishList() {
        Log.e("pls","Opening friends' lists");
        Intent intent = new Intent(this, FriendListsActivity.class);
        startActivity(intent);
    }

}
