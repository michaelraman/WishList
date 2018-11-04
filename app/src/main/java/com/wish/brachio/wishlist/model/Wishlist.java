package com.wish.brachio.wishlist.model;

import java.util.ArrayList;

public class Wishlist {
    private String name;
    private ArrayList<Item> items = new ArrayList();

    public String getName() {
        return name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
