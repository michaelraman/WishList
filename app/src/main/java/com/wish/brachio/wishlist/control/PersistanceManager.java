package com.wish.brachio.wishlist.control;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.wish.brachio.wishlist.FriendWishListActivity;
import com.wish.brachio.wishlist.LoginActivity;
import com.wish.brachio.wishlist.model.Item;
import com.wish.brachio.wishlist.model.Wishlist;
import com.wish.brachio.wishlist.model.database.FirebaseItemHandler;
import com.wish.brachio.wishlist.model.database.FirebaseUserHandler;
import com.wish.brachio.wishlist.model.User;
import com.wish.brachio.wishlist.model.singleton.CurrentUser;
import com.wish.brachio.wishlist.model.singleton.FoundFriend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class PersistanceManager {
    private String TAG = "PersistanceManager";
    public static ArrayList<String> itemIDCallback = new ArrayList();
    public static int friendCount = 0;
    public static int itemCount = 0;

    public void signIn(String email, String password, final Activity activity){
        final FirebaseUserHandler handler = new FirebaseUserHandler();
        Task task1 = handler.signIn(email, password, activity);
        task1.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    //this will take user to main page on complete
                    handler.getSignedInUserInfo( activity );
                }
            }
        });
    }

    public void registerUser(User user, String password, final Activity activity){
        FirebaseUserHandler handler = new FirebaseUserHandler();
        handler.registerUser( user, password, activity );
    }

    public void getFriendWishLists(final Activity activity, final Class nextActivity){
        FirebaseItemHandler itemHandler = new FirebaseItemHandler();
        FirebaseUserHandler userHandler = new FirebaseUserHandler();
        User currentUser = CurrentUser.getInstance().getUser();
        HashMap<String, User> map = currentUser.getFriends();
        ArrayList<User> friends = new ArrayList(map.values());
        friendCount = friends.size();
        for (User user : friends){
            itemIDCallback.clear();
            Task task = userHandler.getWishList( user, this );
            task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    friendCount--;
                    if (friendCount == 0){
                        Intent intent = new Intent(activity, nextActivity);
                        activity.startActivity(intent);
                    }

                }
            });

        }
    }

    public void addWishList(final User user, final Wishlist wishlist, final Activity currentActivity, final Class nextActivity){
        ArrayList<Item> items = wishlist.getItems();
        FirebaseItemHandler itemHandler = new FirebaseItemHandler();
        final FirebaseUserHandler userHandler = new FirebaseUserHandler();
        itemCount = items.size();
        for (Item item : items){
            Task task1 = itemHandler.addItem(item);
            task1.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    itemCount--;
                    if (itemCount == 0){
                        Task task2 = userHandler.addWishList( user, wishlist );
                        task2.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Intent intent = new Intent(currentActivity, nextActivity);
                                currentActivity.startActivity(intent);
                            }
                        });

                    }

                }
            });
        }
    }

    public void getUserByEmail(String email){
        final FirebaseUserHandler handler = new FirebaseUserHandler();
        Task task = handler.getUserByEmail( email );
        task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                FoundFriend.getInstance().setUser( handler.userCallback);
            }
        });
    }

    public void addContributator(Item item, String email){
        FirebaseItemHandler handler = new FirebaseItemHandler();
        handler.addContributor( item, email );
    }


    public void addFriend(final User friend1, final User friend2) {
        final FirebaseUserHandler handler = new FirebaseUserHandler();
        HashMap<String, User> friends1 = friend1.getFriends();
        friends1.put(friend2.getEmail(), friend2);

        HashMap<String, User> friends2 = friend1.getFriends();
        friends2.put(friend1.getEmail(), friend1);

        Task task1 = handler.updateUser( friend1 );
        task1.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Task task2 = handler.updateUser( friend2);
                task2.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    }
                });
            }
        });
    }







}
