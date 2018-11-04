package com.wish.brachio.wishlist.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wish.brachio.wishlist.R;
import com.wish.brachio.wishlist.model.Item;

import java.util.ArrayList;

public class UserItemListAdapter extends RecyclerView.Adapter<UserItemListAdapter.ItemViewHolder>{
    private ArrayList<Item> items;
    private View view;
    private final UserItemListAdapter.OnItemClickListener theItemListener;
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

        public void bind(final Item theItem, final UserItemListAdapter.OnItemClickListener listener) {
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
    public UserItemListAdapter(ArrayList<Item> array, UserItemListAdapter.OnItemClickListener listener) {
        this.items = array;
        this.theItemListener = listener;
    }

    // Create new views (invoked by the layout manager)
    // Create new views (invoked by the layout manager)
    @Override
    public UserItemListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adapter, parent, false);

        view = v;
        UserItemListAdapter.ItemViewHolder vh = new UserItemListAdapter.ItemViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(UserItemListAdapter.ItemViewHolder holder, int position) {
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