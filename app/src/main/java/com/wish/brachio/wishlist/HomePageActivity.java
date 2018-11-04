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

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home_page);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ImageButton mCreateWishList = (ImageButton) findViewById(R.id.imageButton5);
        mCreateWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateWishList();
            }
        });

        Button mViewFriendsWishList = (Button) findViewById(R.id.button3);
        mViewFriendsWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptViewFriendsWishList();
            }
        });


        Button friendButton = (Button) findViewById(R.id.friends_list_button);
        final Activity activity = this;
        friendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PersistanceManager manager = new PersistanceManager();
                manager.getFriendWishLists( activity);
            }
        });
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
