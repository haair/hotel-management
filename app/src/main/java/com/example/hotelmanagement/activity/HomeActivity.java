package com.example.hotelmanagement.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.database.RoomDatabase;
import com.example.hotelmanagement.entity.Guest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cvAddRoom;
    private CardView cvAddNewGuest;
    private CardView cvAllRoomList;
    private CardView cvAllGuest;
    private CardView cvBookedRoomList;
    private CardView cvRemainingRoomList;
    private ImageView ivLogout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findElement();
        setOnClick();
    }

    public void setOnClick() {
        cvAddRoom.setOnClickListener(this);
        cvAddNewGuest.setOnClickListener(this);
        cvAllRoomList.setOnClickListener(this);
        cvAllGuest.setOnClickListener(this);
        cvBookedRoomList.setOnClickListener(this);
        cvRemainingRoomList.setOnClickListener(this);
        ivLogout.setOnClickListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        return true;
                    case R.id.services:
                        startActivity(new Intent(getApplicationContext(),ServiceActivity.class));
                        overridePendingTransition(0,0);
                        finish();
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

    public void findElement() {
        cvAddRoom = findViewById(R.id.cv_add_room);
        cvAddNewGuest = findViewById(R.id.cv_add_guest);
        cvAllRoomList = findViewById(R.id.cv_all_room_list);
        cvAllGuest = findViewById(R.id.cv_guest_booking_list);
        cvBookedRoomList = findViewById(R.id.cv_booked_room_list);
        cvRemainingRoomList = findViewById(R.id.cv_remaining_room_list);
        ivLogout = findViewById(R.id.iv_logout);
        bottomNavigationView = findViewById(R.id.bot_nav);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cv_add_room:
                startActivity(new Intent(this, AddRoomActivity.class));
                break;
            case R.id.cv_add_guest:
                startActivity(new Intent(this, AddNewGuestActivity.class));
                break;
            case R.id.cv_guest_booking_list:
                startActivity(new Intent(this, GuestBookingListActivity.class));
                break;
            case R.id.cv_booked_room_list:
                startActivity(new Intent(this, BookedRoomListActivity.class));
                break;
            case R.id.cv_remaining_room_list:
                startActivity(new Intent(this, RemainingRoomListActivity.class));
                break;
            case R.id.cv_all_room_list:
//                cvAllRoomList.setBackgroundResource(R.color.grey);
                startActivity(new Intent(this, AllRoomListActivity.class));
                break;
            case R.id.iv_logout:
                finish();
                break;
        }
    }
}