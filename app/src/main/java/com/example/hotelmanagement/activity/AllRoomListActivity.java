package com.example.hotelmanagement.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.database.RoomDatabase;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.adapter.AllRoomListAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class AllRoomListActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView rcvAllRoomList;
    private AllRoomListAdapter allRoomListAdapter;
    private ImageView ivAllRoomBack;
    private ImageView ivSearch;
    private EditText etSearch;
    private TextView tvMain;
    private LinearLayout llEdit;
    private LinearLayout llDelete;
    private List<Room> roomList;
    private int MY_REQUEST_CODE = 10;
    private String ori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_room_list);
        findElement();
        setOnClick();
        setupRecycleView();
        tvMain.setText(ori + " (" + roomList.size() + ")");
    }

    public void findElement() {
        rcvAllRoomList = findViewById(R.id.rcv_all_room);
        ivAllRoomBack = findViewById(R.id.iv_r_all_room_back_to_home);
        ivSearch = findViewById(R.id.iv_search);
        etSearch = findViewById(R.id.et_search);
        tvMain = findViewById(R.id.tv_main);
        ori = tvMain.getText().toString();
    }

    public void setupRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAllRoomList.setLayoutManager(linearLayoutManager);
        allRoomListAdapter = new AllRoomListAdapter(new AllRoomListAdapter.IClickMoreButton() {
            @Override
            public void showBottomDialog(Room room) {
                View view = getLayoutInflater().inflate(R.layout.bot_sheet_layout, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AllRoomListActivity.this);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                setupBotSheetFunction(view, room, bottomSheetDialog);
            }
        });
        loadData();
        rcvAllRoomList.setAdapter(allRoomListAdapter);
    }

    private void setupBotSheetFunction(View view, Room room, BottomSheetDialog bottomSheetDialog) {
        llEdit = view.findViewById(R.id.ll_edit);
        llDelete = view.findViewById(R.id.ll_delete);
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (room.getStatus() == 1) {
                    bottomSheetDialog.dismiss();
                    Toast.makeText(AllRoomListActivity.this, "You can not delete a booked room", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(AllRoomListActivity.this)
                        .setTitle("DELETE ROOM: "+room.getNumber())
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RoomDatabase.getInstance(AllRoomListActivity.this).roomDAO().deleteRoom(room.getNumber());
                                Toast.makeText(AllRoomListActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
                                loadData();
                            }
                        })
                        .setNegativeButton("No", null).show();
                bottomSheetDialog.dismiss();
            }
        });
        llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllRoomListActivity.this, UpdateRoomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("roomObject", room);
                intent.putExtras(bundle);
                startActivityForResult(intent, MY_REQUEST_CODE);
                bottomSheetDialog.dismiss();
            }
        });

    }

    public void setOnClick() {
        ivAllRoomBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                roomList = RoomDatabase.getInstance(AllRoomListActivity.this).roomDAO().searchRoom(etSearch.getText().toString());
                allRoomListAdapter.setData(roomList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_r_all_room_back_to_home:
                finish();
                break;
            case R.id.iv_search:
                if (etSearch.getVisibility() == View.VISIBLE) {
                    etSearch.setVisibility(View.INVISIBLE);
                    etSearch.setText("");
                    tvMain.setVisibility(View.VISIBLE);
                    ivSearch.setImageResource(R.drawable.ic_search);
                }
                else {
                    etSearch.setVisibility(View.VISIBLE);
                    tvMain.setVisibility(View.INVISIBLE);
                    ivSearch.setImageResource(R.drawable.ic_close);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadData();
        }
    }

    public void loadData() {
        roomList = RoomDatabase.getInstance(this).roomDAO().getAllRoom();
        allRoomListAdapter.setData(roomList);
        tvMain.setText(ori + " (" + roomList.size() + ")");
    }
}