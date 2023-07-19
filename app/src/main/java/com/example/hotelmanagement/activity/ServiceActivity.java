package com.example.hotelmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.adapter.ServiceAdapter;
import com.example.hotelmanagement.entity.ServiceItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ServiceAdapter serviceAdapter;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        findElement();
    }

    public void findElement() {
        bottomNavigationView = findViewById(R.id.bot_nav);
        recyclerView = findViewById(R.id.rcv_service);
        serviceAdapter = new ServiceAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        serviceAdapter.setData(getListService());
        recyclerView.setAdapter(serviceAdapter);
        bottomNavigationView.setSelectedItemId(R.id.services);
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
                        return true;
                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(),AboutActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private List<ServiceItem> getListService() {
        List<ServiceItem> serviceItems = new ArrayList<>();
        serviceItems.add(new ServiceItem(R.drawable.ic_drop_down, "Service 1", "acb"));
        serviceItems.add(new ServiceItem(R.drawable.ic_drop_down, "Service 2", "acqeqwe"));
        serviceItems.add(new ServiceItem(R.drawable.ic_drop_down, "Service 3", "acbcfwe"));
        serviceItems.add(new ServiceItem(R.drawable.ic_drop_down, "Service 5", "v83421"));
        return serviceItems;
    }
}