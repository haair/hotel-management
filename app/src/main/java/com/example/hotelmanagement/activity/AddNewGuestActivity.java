package com.example.hotelmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.example.hotelmanagement.format.MyFormat;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddNewGuestActivity extends AppCompatActivity implements View.OnClickListener {


    private Spinner spinner;
    private CustomDropDownAdapter customDropDownAdapter;
    private ImageView ivAddGuestBackHome;
    private EditText etName;
    private EditText etPhone;
    private EditText etIdentify;
    private EditText etRoomNumber;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_guest);
        findElement();
        setOnClick();
    }

    public void findElement() {
        ivAddGuestBackHome = findViewById(R.id.iv_g_back_to_home);
        etName = findViewById(R.id.et_guest_name);
        etPhone = findViewById(R.id.et_guest_phone);
        etIdentify = findViewById(R.id.et_guest_identify);
        btnAdd = findViewById(R.id.btn_g_add_guest);
        spinner = findViewById(R.id.spn_room_number);
        customDropDownAdapter = new CustomDropDownAdapter(this, R.layout.drop_item_selected, getList());
        spinner.setAdapter(customDropDownAdapter);
    }

    private List<RoomTypeDrop> getList() {
        List<RoomTypeDrop> roomTypeDrops = new ArrayList<>();
        List<Room> roomList = RoomDatabase.getInstance(this).roomDAO().getRemainingRoom();
        for (Room list : roomList) {
            roomTypeDrops.add(new RoomTypeDrop(list.getNumber()));
        }
        return roomTypeDrops;
    }

    public void setOnClick() {
        ivAddGuestBackHome.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_g_back_to_home:
                finish();
                break;
            case R.id.btn_g_add_guest:
                addGuest();
                break;
        }
    }

    private void addGuest() {
        if (etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || etIdentify.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please input full field", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String identify = etIdentify.getText().toString();
        int indexRoom = spinner.getSelectedItemPosition();
        List<Room> roomList = RoomDatabase.getInstance(this).roomDAO().getRemainingRoom();
        String rNumber = roomList.get(indexRoom).getNumber();

        Guest guest = new Guest(name, phone, identify, rNumber, MyFormat.getDateTimeNow());
        try {
            RoomDatabase.getInstance(this).guestDAO().insertGuest(guest);
        }
        catch (Exception e) {
            Toast.makeText(this, "Guest identify exist", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Add guest successfully", Toast.LENGTH_SHORT).show();
        RoomDatabase.getInstance(this).roomDAO().setBookedRoom(rNumber);
        etIdentify.setText("");
        etPhone.setText("");
        etName.setText("");
        hideKeyboard();
        finish();
        startActivity(getIntent());
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}