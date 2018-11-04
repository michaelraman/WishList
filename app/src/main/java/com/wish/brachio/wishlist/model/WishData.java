package com.wish.brachio.wishlist.model;

public class WishData {
    private User user;
    private Wishlist list;

    public User getUser() {
        return user;
    }

    public Wishlist getList() {
        return list;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setList(Wishlist list) {
        this.list = list;
    }
}
