package com.wish.brachio.wishlist;

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
import android.widget.TextView;

import com.wish.brachio.wishlist.model.Item;
import com.wish.brachio.wishlist.model.User;
import com.wish.brachio.wishlist.model.WishData;
import com.wish.brachio.wishlist.model.Wishlist;
import com.wish.brachio.wishlist.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendListsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_lists);

        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.recycle_friend );

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<WishData> data = new ArrayList();

        User user = CurrentUser.getInstance().getUser();
        HashMap<String, User> friends = user.getFriends();
        ArrayList<User> users = new ArrayList(friends.values());
        for (User u : users){
            Wishlist wishlist = user.getWishlist();
            WishData d = new WishData();
            d.setList(wishlist);
            d.setUser( u );
            data.add(d);

        }

        FriendListsActivity.WishDataListAdapter adapter = new FriendListsActivity.WishDataListAdapter(data, new WishDataListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WishData data) {

            }
        });

        recyclerView.setAdapter( adapter );
    }

    private static  class WishDataListAdapter extends RecyclerView.Adapter<FriendListsActivity.WishDataListAdapter.ItemViewHolder>{
        private ArrayList<WishData> items;
        private View view;
        private final FriendListsActivity.WishDataListAdapter.OnItemClickListener theItemListener;
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

            public void bind(final WishData theItem, final FriendListsActivity.WishDataListAdapter.OnItemClickListener listener) {
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
            void onItemClick(WishData item);
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public WishDataListAdapter(ArrayList<WishData> array, FriendListsActivity.WishDataListAdapter.OnItemClickListener listener) {
            this.items = array;
            this.theItemListener = listener;
        }

        // Create new views (invoked by the layout manager)
        // Create new views (invoked by the layout manager)
        @Override
        public FriendListsActivity.WishDataListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                          int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_adapter, parent, false);

            view = v;
            FriendListsActivity.WishDataListAdapter.ItemViewHolder vh = new FriendListsActivity.WishDataListAdapter.ItemViewHolder(v);
            return vh;
        }


        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(FriendListsActivity.WishDataListAdapter.ItemViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.nameText.setText(items.get(position).getUser().getEmail());
            holder.contributor.setText( items.get(position).getList().getName());
            //String c = items.get(position).getList().getName();

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
