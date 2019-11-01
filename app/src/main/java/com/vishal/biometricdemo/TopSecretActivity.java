package com.vishal.biometricdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class TopSecretActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_secret);
        String s = getIntent().getStringExtra("KEY_S");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}