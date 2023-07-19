package com.example.hotelmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.adapter.CustomDropDownAdapter;
import com.example.hotelmanagement.database.RoomDatabase;
import com.example.hotelmanagement.entity.Guest;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.entity.RoomTypeDrop;

import java.util.ArrayList;
import java.util.List;

public class UpdateGuestActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private EditText etName;
    private EditText etPhone;
    private EditText etIdentify;
    private CustomDropDownAdapter dropDownAdapter;
    private Guest guest;
    private ImageView ivBack;
    private Button btnUpdate;
    private List<RoomTypeDrop> roomTypeDrops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_guest);
        findElement();
        setOnClick();
        if (guest != null) {
            etName.setText(guest.getName());
            etPhone.setText(guest.getPhone());
            etIdentify.setText(guest.getIdentify());
            spinner.setSelection(0);
        }
    }

    public void findElement() {
        guest = (Guest) getIntent().getExtras().get("guestObject");
        spinner = findViewById(R.id.spn_room);
        etName = findViewById(R.id.et_guest_name);
        etPhone = findViewById(R.id.et_guest_phone);
        etIdentify = findViewById(R.id.et_guest_identify);
        ivBack = findViewById(R.id.iv_r_back_to_home);
        btnUpdate = findViewById(R.id.btn_r_add_room);
        dropDownAdapter = new CustomDropDownAdapter(this, R.layout.drop_item_selected, getList());
        spinner.setAdapter(dropDownAdapter);
    }

    private List<RoomTypeDrop> getList() {
        List<Room> roomList = RoomDatabase.getInstance(this).roomDAO().getRemainingRoom();
        roomTypeDrops.add(new RoomTypeDrop(guest.getRoom()));
        for (Room room : roomList) {
            roomTypeDrops.add(new RoomTypeDrop(room.getNumber()));
        }
        return roomTypeDrops;
    }

    public void setOnClick() {
        ivBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_r_back_to_home:
                finish();
                break;
            case R.id.btn_r_add_room:
                if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etIdentify.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Content can not empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                guest.setName(etName.getText().toString());
                guest.setPhone(etPhone.getText().toString());
                guest.setIdentify(etIdentify.getText().toString());
                String rNumber = roomTypeDrops.get(spinner.getSelectedItemPosition()).getName();
                RoomDatabase.getInstance(UpdateGuestActivity.this).roomDAO().setBookedRoom(rNumber);
                RoomDatabase.getInstance(UpdateGuestActivity.this).roomDAO().setNotBookedRoom(guest.getRoom());
                guest.setRoom(rNumber);
                RoomDatabase.getInstance(UpdateGuestActivity.this).guestDAO().updateGuest(guest);
                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }
}