package com.wish.brachio.wishlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        //Todo use to sign in user (manager will take user to main page

    }

    private void attemptLogin() {
        PersistanceManager manager = new PersistanceManager();
        EditText mLoginEmail = (EditText) findViewById(R.id.login_email);
        EditText mLoginPassword = (EditText) findViewById(R.id.login_password);
        manager.signIn(mLoginEmail.getText().toString(), mLoginPassword.getText().toString(), this);
    }
}
