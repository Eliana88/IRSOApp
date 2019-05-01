package com.example.irsoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity {

    public static final String user="names";
    TextView txtAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        txtAdmin = findViewById(R.id.txtAdmin);
        String user = getIntent().getStringExtra("names");
        txtAdmin.setText("Admin: " + user + "!");
    }
}
