package com.example.hotelmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hotelmanagement.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AboutActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        findElement();
    }

    private void findElement() {
        bottomNavigationView = findViewById(R.id.bot_nav);
        bottomNavigationView.setSelectedItemId(R.id.person);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.services:
                        startActivity(new Intent(getApplicationContext(),ServiceActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.person:
                        return true;
                }
                return false;
            }
        });
    }
}