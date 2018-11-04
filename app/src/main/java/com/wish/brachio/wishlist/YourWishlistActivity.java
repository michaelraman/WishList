package com.wish.brachio.wishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wish.brachio.wishlist.control.PersistanceManager;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.wish.brachio.wishlist.model.Item;
import com.wish.brachio.wishlist.model.adapter.UserItemListAdapter;

import java.util.ArrayList;

public class YourWishlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_wishlist);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


/*        Button fab = (Button) findViewById(R.id.imageButton6);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersistanceManager manager = new PersistanceManager();
                manager.getFriendWishLists(this, YourWishlistActivity.class);
                attemptAddItem();
            }
        });*/


        ImageButton friendButton = (ImageButton) findViewById(R.id.imageButton6);
        final Activity activity = this;
        friendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddingItems();
            }
        });


        Button saveList = (Button) findViewById(R.id.button4);
        //final Activity activity = this;
        saveList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SaveItem();
            }
        });



        RecyclerView list = findViewById( R.id.your_wishlist );
        ArrayList<Item> items = new ArrayList();
        Item newItem = new Item("somename");
        items.add(newItem);
        UserItemListAdapter adapter = (UserItemListAdapter) new UserItemListAdapter( items, new UserItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {

            }
        } );
        list.setAdapter( adapter);

    }

/*    private void attemptAddItem() {
        Log.e("pls","Opening friends' lists");
        Intent intent = new Intent(this, AddingItems.class);
        startActivity(intent);
    }*/


    private void AddingItems() {
        Log.e("pls","Opening friends' lists");
        Intent intent = new Intent(this, AddingItems.class);
        startActivity(intent);
    }


    private void SaveItem() {
        Log.e("pls","Opening friends' lists");
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }


}
