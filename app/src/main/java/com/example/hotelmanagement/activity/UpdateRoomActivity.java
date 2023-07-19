package com.example.hotelmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class UpdateRoomActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private CustomDropDownAdapter dropDownAdapter;
    private ImageView ivBack;
    private Button btnUpdate;
    private EditText etRoomNumber;
    private EditText etRoomRate;
    private Room mRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);
        findElement();
        setOnClick();
        if (mRoom != null) {
            int selection = mRoom.getType() == 1 ? 0 : 1;
            etRoomNumber.setText(mRoom.getNumber());
            spinner.setSelection(selection);
            etRoomRate.setText(String.valueOf(mRoom.getRate()));
        }
    }

    public void findElement() {
        ivBack = findViewById(R.id.iv_r_back_to_home);
        btnUpdate = findViewById(R.id.btn_r_add_room);
        etRoomNumber = findViewById(R.id.et_room_number);
        spinner = findViewById(R.id.spn_room_type);
        dropDownAdapter = new CustomDropDownAdapter(this, R.layout.drop_item_selected, getList());
        spinner.setAdapter(dropDownAdapter);
        etRoomRate = findViewById(R.id.et_room_rate);
        mRoom = (Room) getIntent().getExtras().get("roomObject");
    }

    private List<RoomTypeDrop> getList() {
        List<RoomTypeDrop> roomTypeDrops = new ArrayList<>();
        roomTypeDrops.add(new RoomTypeDrop("Single"));
        roomTypeDrops.add(new RoomTypeDrop("Double"));
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
                if (etRoomNumber.getText().toString().isEmpty() || etRoomRate.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Content can not empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                mRoom.setNumber(etRoomNumber.getText().toString());
                mRoom.setType(spinner.getSelectedItemPosition() == 0 ? 1 : 2);
                mRoom.setRate(Integer.parseInt(etRoomRate.getText().toString()));
                RoomDatabase.getInstance(this).roomDAO().updateRoom(mRoom);
                Toast.makeText(this, "Update successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
    }
}