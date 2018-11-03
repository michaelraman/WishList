package com.wish.brachio.wishlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        //Todo use to sign in user (manager will take user to main page
        //PersistanceManager manager = new PersistanceManager();
        //manager.signIn(email, password, this);
    }
}
