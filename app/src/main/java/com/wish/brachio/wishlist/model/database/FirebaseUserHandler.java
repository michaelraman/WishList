package com.wish.brachio.wishlist.model.database;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class FirebaseUserHandler {
    private String TAG = "FirebaseUserHandler";
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
}
