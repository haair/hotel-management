package com.example.hotelmanagement.activity;

import static android.widget.AdapterView.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.adapter.CustomDropDownAdapter;
import com.example.hotelmanagement.database.RoomDatabase;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.entity.RoomTypeDrop;

import java.util.ArrayList;
import java.util.List;

public class AddRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivAddRoomBackHome;
    private EditText etRoomNumber;
    private Spinner spnRoomType;
    private EditText etRoomRate;
    private Button btnAdd;
    private CustomDropDownAdapter dropDownAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        findElement();
        setOnClick();
    }

    public void findElement() {
        ivAddRoomBackHome = findViewById(R.id.iv_r_back_to_home);
        etRoomNumber = findViewById(R.id.et_room_number);
        spnRoomType = findViewById(R.id.spn_room_type);
        etRoomRate = findViewById(R.id.et_room_rate);
        btnAdd = findViewById(R.id.btn_r_add_room);
        dropDownAdapter = new CustomDropDownAdapter(this, R.layout.drop_item_selected, getList());
        spnRoomType.setAdapter(dropDownAdapter);
    }

    private List<RoomTypeDrop> getList() {
        List<RoomTypeDrop> roomTypeDrops = new ArrayList<>();
        roomTypeDrops.add(new RoomTypeDrop("Single"));
        roomTypeDrops.add(new RoomTypeDrop("Double"));
        return roomTypeDrops;
    }

    public void setOnClick() {
        ivAddRoomBackHome.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_r_back_to_home:
                finish();
                break;
            case R.id.btn_r_add_room:
                addRoom();
                break;
        }
    }

    private void addRoom() {
        String rNumber = etRoomNumber.getText().toString();
        int rType = spnRoomType.getSelectedItemPosition();
        String rRate = etRoomRate.getText().toString();
        if (rNumber.isEmpty() || rRate.isEmpty()) {
            Toast.makeText(this, "Please input full field", Toast.LENGTH_SHORT).show();
            return;
        }

        int rate = Integer.parseInt(rRate);
        int type = rType == 0 ? 1 : 2;

        Room room = new Room(rNumber, type, rate, 0);
        try {
            RoomDatabase.getInstance(this).roomDAO().insertRoom(room);
        }
        catch (Exception e){
            Toast.makeText(this, "Room number exist", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Add room successfully", Toast.LENGTH_SHORT).show();
        etRoomNumber.setText("");
        etRoomRate.setText("");
        hideKeyboard();
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