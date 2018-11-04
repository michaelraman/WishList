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

import com.wish.brachio.wishlist.control.PersistanceManager;

public class AddingItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        Button friendButton = (Button) findViewById(R.id.Save_Item);
        final Activity activity = this;
        friendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PersistanceManager manager = new PersistanceManager();
                manager.getFriendWishLists( activity, YourWishlistActivity.class);
                attemptViewFriendsWishList();

            }
        });



    }


    private void attemptViewFriendsWishList() {
        Log.e("pls","Adding new item");
        Intent intent = new Intent(this, FriendListsActivity.class);
        startActivity(intent);
    }

}
