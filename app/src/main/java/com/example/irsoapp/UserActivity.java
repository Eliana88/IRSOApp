package com.example.irsoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    public static final String user="names";
    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        txtUser = findViewById(R.id.txtUser);
        String user = getIntent().getStringExtra("names");
        txtUser.setText("Bienvenido " + user + "!");
    }
}
