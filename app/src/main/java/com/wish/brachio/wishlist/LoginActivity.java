package com.wish.brachio.wishlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wish.brachio.wishlist.control.PersistanceManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button registrationButton = (Button) findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });


        //Todo use to sign in user (manager will take user to main page

    }

    private void attemptLogin() {
        PersistanceManager manager = new PersistanceManager();
        TextView mLoginEmail = (TextView) findViewById(R.id.login_email);
        TextView mLoginPassword = (TextView) findViewById(R.id.login_password);
        manager.signIn(mLoginEmail.getText().toString(), mLoginPassword.getText().toString(), this);
    }
}
