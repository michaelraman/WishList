package com.wish.brachio.wishlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wish.brachio.wishlist.control.PersistanceManager;
import com.wish.brachio.wishlist.model.User;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );

        final TextView firstname = (TextView) findViewById( R.id.reg_firstname );
        final TextView lastname = (TextView) findViewById(R.id.reg_lastname);
        final TextView email = (TextView) findViewById(R.id.reg_email);
        final TextView phone = (TextView) findViewById(R.id.reg_phone);
        final TextView password = (TextView) findViewById( R.id.reg_password );

        Button regButton = (Button) findViewById( R.id.reg_enter_button );
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = firstname.getText().toString();
                String ln = lastname.getText().toString();
                String e = email.getText().toString();
                String p = phone.getText().toString();
                String pass = password.getText().toString();
                User user = new User(fn, ln, e);
                if (!p.isEmpty()){
                    user.setPhone(p);
                }
                PersistanceManager manager = new PersistanceManager();
                manager.registerUser(user, pass, RegistrationActivity.this);
            }
        });

        Button cancelButton = (Button) findViewById( R.id.reg_cancel_button );
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
