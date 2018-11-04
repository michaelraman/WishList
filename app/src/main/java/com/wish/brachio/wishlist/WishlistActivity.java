package com.wish.brachio.wishlist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.wish.brachio.wishlist.model.Item;
import com.wish.brachio.wishlist.model.User;
import com.wish.brachio.wishlist.model.Wishlist;
import com.wish.brachio.wishlist.model.singleton.CurrentUser;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_wishlist );

        ListView list = (ListView) findViewById( R.id.wish_item_list );
        TextView name = (TextView) findViewById( R.id.wishtlist_name );

        Intent intent = getIntent();
        String email = intent.getStringExtra("user");
        String wishname = intent.getStringExtra("wishlist");

        User user = CurrentUser.getInstance().getUser();
        User friend = user.getFriends().get(email);
        Wishlist wish = friend.getWishlist();

        name.setText(wishname);
        ArrayList<Item> items = wish.getItems();

    }
    private static class ItemListAdapter extends RecyclerView.Adapter<WishlistActivity.ItemListAdapter.ItemViewHolder>{
        private ArrayList<Item> items;
        private View view;
        private final OnItemClickListener theItemListener;
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

            public void bind(final Item theItem, final OnItemClickListener listener) {
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
        public ItemListAdapter(ArrayList<Item> array, OnItemClickListener listener) {
            this.items = array;
            this.theItemListener = listener;
        }

        // Create new views (invoked by the layout manager)
        // Create new views (invoked by the layout manager)
        @Override
        public WishlistActivity.ItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                  int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_adapter, parent, false);

            view = v;
            WishlistActivity.ItemListAdapter.ItemViewHolder vh = new WishlistActivity.ItemListAdapter.ItemViewHolder(v);
            return vh;
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(WishlistActivity.ItemListAdapter.ItemViewHolder holder, int position) {
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
