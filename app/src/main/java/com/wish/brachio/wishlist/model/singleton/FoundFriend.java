package com.wish.brachio.wishlist.model.singleton;

import com.wish.brachio.wishlist.model.User;

public class FoundFriend {
    // static variable single_instance of type CurrentUser
    private static FoundFriend single_instance = null;

    // current User
    private User user;

    // private constructor restricted to this class itself
    private FoundFriend()
    {
        user = new User();
    }

    // static method to create instance of CurrentUser class
    public static FoundFriend getInstance()
    {
        if (single_instance == null)
            single_instance = new FoundFriend();

        return single_instance;
    }

    public void setUser(User u) {
        user = u;
    }

    public User getUser(){
        return user;
    }
}
