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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FirebaseUserHandler {
    private String TAG = "FirebaseUserHandler";
    private User userCallback;
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
            task = db.collection( "users" ).whereEqualTo( "email", user.getEmail() )
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
                                HashMap<String, Boolean> friendHash;
                                for (DocumentSnapshot doc : retDocs) {
                                    firstName = (String) doc.get( "fname" );
                                    lastName = (String) doc.get( "lname" );
                                    email = (String) doc.get( "email" );
                                    phone = (String) doc.get( "phone" );
                                    friendHash = (HashMap<String, Boolean>) doc.get( "friends" );
                                }

                                CurrentUser.getInstance().setUser( userCallback );
                            }
                        }
                    } );
        }
        return task;
    }
}
