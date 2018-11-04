package com.wish.brachio.wishlist.model;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Item {

    private String id;
    private String itemName;
    private int quantity;

    private String contributers;
    private Date creationDate;
    private String link;

    public Item(String n){
        itemName = n;
    }

    public Item(String i, String n, int q, Date d){
        id = i;
        itemName = n;
        quantity = q;
        creationDate = d;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getContributers() {
        return contributers;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getLink() {
        return link;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setContributers(String contributers) {
        this.contributers = contributers;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
