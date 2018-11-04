package com.wish.brachio.wishlist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
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
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        RecyclerView list = findViewById( R.id.your_wishlist );
        ArrayList<Item> items = new ArrayList();
        Item newItem = new Item("somename");

        UserItemListAdapter adapter = (UserItemListAdapter) new UserItemListAdapter( items, new UserItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {

            }
        } );
        list.setAdapter( adapter);

    }

}
