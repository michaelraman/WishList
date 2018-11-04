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

public class YourWishlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_wishlist);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Button friendButton = (Button) findViewById(R.id.imageButton6);
        final Activity activity = this;
        friendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PersistanceManager manager = new PersistanceManager();
                manager.getFriendWishLists( activity, YourWishlistActivity.class);
                attemptAddItem();
            }
        });

    }

    private void attemptAddItem() {
        Log.e("pls","Opening friends' lists");
        Intent intent = new Intent(this, AddingItems.class);
        startActivity(intent);
    }

}
