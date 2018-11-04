package com.wish.brachio.wishlist.model.database;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wish.brachio.wishlist.HubActivity;
import com.wish.brachio.wishlist.LoginActivity;
import com.wish.brachio.wishlist.control.PersistanceManager;
import com.wish.brachio.wishlist.model.User;
import com.wish.brachio.wishlist.model.singleton.CurrentUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirebaseUserHandler {
    private String TAG = "FirebaseUserHandler";
    private User userCallback;
    private HashMap<String, User> friendsCallback = new LinkedHashMap<>(  );
    public Task signIn(String email, String password, final Activity activity) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Task task = null;
        if (!email.isEmpty() && !password.isEmpty()) {
            task = auth.signInWithEmailAndPassword( email, password )
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d( TAG, "signInWithEmail:success" );
                                //getSignedInUser(login);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                //Toast.makeText(activity, "Username or password is incorrect.", Toast.LENGTH_LONG).show();
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    Toast.makeText( activity, e.getReason(), Toast.LENGTH_LONG ).show();
                                } catch (FirebaseAuthInvalidUserException e) {
                                    Toast.makeText( activity, "User does not exist.", Toast.LENGTH_LONG ).show();
                                } catch (Exception e) {
                                    Toast.makeText( activity, e.getMessage(), Toast.LENGTH_LONG ).show();
                                }
                            }
                        }
                    } );
        }
        return task;
    }


    public Task registerUser(User appUser, String password, final Activity activity) {
        // Write a message to the database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        final Map<String, Object> userMap = new HashMap<>();
        Task task = null;
        if (!appUser.isNull() && !password.isEmpty()) {
            userMap.put( "firstname", appUser.getFirstName() );
            userMap.put( "lastname", appUser.getLastName() );
            userMap.put( "email", appUser.getEmail() );
            userMap.put( "phone", appUser.getPhone() );
            userMap.put( "friends", appUser.getFriends() );
            task = auth.createUserWithEmailAndPassword( appUser.getEmail(), password )
                    .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            try {
                                //check if successful
                                if (task.isSuccessful()) {
                                    Log.d( TAG, "Registered" );
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    if (auth.getUid() != null) {
                                        db.collection( "users" ).document( auth.getCurrentUser().getUid() ).set( userMap )
                                                .addOnSuccessListener( new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d( TAG, "Added extra data" );
                                                    }
                                                } );
                                        Intent intent = new Intent( activity, LoginActivity.class );
                                        activity.startActivity( intent );
                                    } else {
                                        Log.e( TAG, "Cannot access current user" );
                                    }
                                } else {
                                    Log.e( TAG, "Not registered" );
                                    Toast.makeText( activity, "User with email entered already exists.", Toast.LENGTH_LONG ).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } );

        } else {
            Log.e( TAG, "Entered null value" );
        }
        return task;
    }

    public Task getSignedInUserInfo(final Activity activity) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d( TAG, "email " + user.getEmail() );
        Task task = null;
        if (user == null) {
            Log.d( TAG, "onFailure: Not signed it" );
        } else {
            task = db.collection( "user" ).whereEqualTo( "email", user.getEmail() )
                    .get().addOnSuccessListener( new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.d( TAG, "onSuccess: LIST EMPTY" );
                                return;
                            } else {
                                // Convert the whole Query Snapshot to a list
                                // of objects directly! No need to fetch each
                                // document.
                                List<DocumentSnapshot> retDocs = documentSnapshots.getDocuments();
                                Log.d( TAG, "onSuccess: " + retDocs.get( 0 ).getId() );

                                String firstName = "";
                                String lastName = "";
                                String email = "";
                                String phone = "";
                                HashMap<String, Boolean> friendHash = new LinkedHashMap<>(  );
                                for (DocumentSnapshot doc : retDocs) {
                                    firstName = (String) doc.get( "fname" );
                                    lastName = (String) doc.get( "lname" );
                                    email = (String) doc.get( "email" );
                                    phone = (String) doc.get( "phone" );
                                    friendHash = (HashMap<String, Boolean>) doc.get("friends");
                                }
                                userCallback = new User(firstName, lastName, email);
                                if (!phone.isEmpty()){
                                    userCallback.setPhone(phone);
                                }

                                CurrentUser.getInstance().setUser( userCallback );
                                if (friendHash != null){
                                    ArrayList<String> emails = new ArrayList(friendHash.keySet());
                                    getFriends(emails, activity);
                                } else {
                                    Intent intent = new Intent(activity, HubActivity.class);
                                    activity.startActivity(intent);
                                }


                            }
                        }
                    } );
        }
        return task;
    }

    //gets friends of user
    public void getFriends(ArrayList<String> emails, Activity activity){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        int count = 0;
        friendsCallback.clear();
        while (count < emails.size()) {
            db.collection("user")
                    .whereEqualTo( "email", emails.get(count) )
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            String firstName = "";
                            String lastName = "";
                            String email = "";
                            String phone = "";
                            HashMap<String, Boolean> friendHash = new LinkedHashMap<>(  );
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot doc : task.getResult()) {
                                    firstName = (String) doc.get( "fname" );
                                    lastName = (String) doc.get( "lname" );
                                    email = (String) doc.get( "email" );
                                    phone = (String) doc.get( "phone" );
                                    User user = new User(firstName, lastName, email);
                                    if (!phone.isEmpty()) {
                                        user.setPhone(phone);
                                    }
                                    friendsCallback.put(email, user);
                                }

                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
            count++;

            if (count == emails.size()){
                userCallback.setFriends(friendsCallback);
                Intent intent = new Intent(activity, HubActivity.class);
                activity.startActivity(intent);
            }

        }


    }


}
