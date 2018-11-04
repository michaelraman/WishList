package com.wish.brachio.wishlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wish.brachio.wishlist.control.PersistanceManager;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wish.brachio.wishlist.model.Item;


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
                PersistanceManager manager = new PersistanceManager();
                manager.getFriendWishLists( activity, YourWishlistActivity.class);
                attemptAddItem();
            }
        });
        */

        RecyclerView recyclerView = findViewById( R.id.your_wishlist );
        ArrayList<Item> items = new ArrayList();
        Item newItem = new Item("somename");
        items.add(newItem);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        UserItemListAdapter adapter = new YourWishlistActivity.UserItemListAdapter(items, new UserItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {

            }
        });

        recyclerView.setAdapter( adapter );

    }

    private void attemptAddItem() {
        Log.e("pls","Opening friends' lists");
        Intent intent = new Intent(this, AddingItems.class);
        startActivity(intent);
    }

    private static  class UserItemListAdapter extends RecyclerView.Adapter<YourWishlistActivity.UserItemListAdapter.ItemViewHolder>{
        private ArrayList<Item> items;
        private View view;
        private final YourWishlistActivity.UserItemListAdapter.OnItemClickListener theItemListener;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            private TextView nameText;
            private TextView contributor;
            private View v;
            public ItemViewHolder(View v) {
                super(v);
                this.v = v;
                nameText = (TextView) v.findViewById(R.id.item_name_adapter);
                contributor = (TextView) v.findViewById(R.id.item_contributor);

            }

            public void bind(final Item theItem, final YourWishlistActivity.UserItemListAdapter.OnItemClickListener listener) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Wish", "To Item");
                        listener.onItemClick(theItem);
                    }
                });
            }
        }

        public interface OnItemClickListener {
            void onItemClick(Item item);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public UserItemListAdapter(ArrayList<Item> array, YourWishlistActivity.UserItemListAdapter.OnItemClickListener listener) {
            this.items = array;
            this.theItemListener = listener;
        }

        // Create new views (invoked by the layout manager)
        // Create new views (invoked by the layout manager)
        @Override
        public YourWishlistActivity.UserItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                             int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_adapter, parent, false);

            view = v;
            YourWishlistActivity.UserItemListAdapter.ItemViewHolder vh = new YourWishlistActivity.UserItemListAdapter.ItemViewHolder(v);
            return vh;
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(YourWishlistActivity.UserItemListAdapter.ItemViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.nameText.setText(items.get(position).getItemName());
            String c = items.get(position).getContributers();
            if (c != null){
                holder.contributor.setText(c);
            } else {
                holder.contributor.setText("None.  Click to add yourself as a contributor.");
            }

            holder.bind(items.get(position), theItemListener);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //TODO add intent to next page
//                }
//            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }

}
