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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.adapter.RemainingRoomListAdapter;
import com.example.hotelmanagement.database.RoomDatabase;
import com.example.hotelmanagement.entity.Room;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class RemainingRoomListActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_REQUEST_CODE = 2003;
    private RecyclerView rcvRemainingRoomList;
    private RemainingRoomListAdapter remainingRoomListAdapter;
    private ImageView ivRemainingRoomListBackHome;
    private ImageView ivSearch;
    private EditText etSearch;
    private TextView tvMain;
    private LinearLayout llEdit;
    private LinearLayout llDelete;
    private List<Room> roomList;
    private String ori;
    private CheckBox chkAll;
    private CheckBox chkSingle;
    private CheckBox chkDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remaining_room_list);
        findElement();
        setOnClick();
        setupRecycleView();
        tvMain.setText(ori + " (" + roomList.size() + ")");
        chkAll.setChecked(true);
    }

    public void findElement() {
        rcvRemainingRoomList = findViewById(R.id.rcv_remaining_room_list);
        ivRemainingRoomListBackHome = findViewById(R.id.iv_r_remaining_room_list_back_to_home);
        ivSearch = findViewById(R.id.iv_search);
        etSearch = findViewById(R.id.et_search);
        tvMain = findViewById(R.id.tv_main);
        ori = tvMain.getText().toString();
        chkAll = findViewById(R.id.chk_all);
        chkSingle = findViewById(R.id.chk_single);
        chkDouble = findViewById(R.id.chk_double);
    }

    public void setOnClick() {
        ivRemainingRoomListBackHome.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        chkAll.setOnClickListener(this);
        chkSingle.setOnClickListener(this);
        chkDouble.setOnClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                roomList = RoomDatabase.getInstance(RemainingRoomListActivity.this).roomDAO().searchRemainingRoom(etSearch.getText().toString());
                remainingRoomListAdapter.setData(roomList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setupRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvRemainingRoomList.setLayoutManager(linearLayoutManager);
        roomList = RoomDatabase.getInstance(this).roomDAO().getRemainingRoom();
        remainingRoomListAdapter = new RemainingRoomListAdapter(new RemainingRoomListAdapter.IClickMoreButton() {
            @Override
            public void showBottomDialog(Room room) {
                View view = getLayoutInflater().inflate(R.layout.bot_sheet_layout, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(RemainingRoomListActivity.this);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                setupBotSheetFunction(view, room, bottomSheetDialog);
            }
        });
        remainingRoomListAdapter.setData(roomList);
        rcvRemainingRoomList.setAdapter(remainingRoomListAdapter);
    }

    private void setupBotSheetFunction(View view, Room room, BottomSheetDialog bottomSheetDialog) {
        llEdit = view.findViewById(R.id.ll_edit);
        llDelete = view.findViewById(R.id.ll_delete);
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                new AlertDialog.Builder(RemainingRoomListActivity.this)
                        .setTitle("DELETE ROOM: "+room.getNumber())
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RoomDatabase.getInstance(RemainingRoomListActivity.this).roomDAO().deleteRoom(room.getNumber());
                                Toast.makeText(RemainingRoomListActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
                                loadData();
                            }
                        })
                        .setNegativeButton("No", null).show();
            }
        });
        llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RemainingRoomListActivity.this, UpdateRoomActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("roomObject", room);
                intent.putExtras(bundle);
                startActivityForResult(intent, MY_REQUEST_CODE);
                bottomSheetDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_r_remaining_room_list_back_to_home:
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
            case R.id.chk_all:
                if (chkAll.isChecked()) {
                    chkSingle.setChecked(false);
                    chkDouble.setChecked(false);
                    loadData();
                }
                break;
            case R.id.chk_single:
                if (chkSingle.isChecked()) {
                    chkAll.setChecked(false);
                    chkDouble.setChecked(false);
                    roomList = RoomDatabase.getInstance(RemainingRoomListActivity.this).roomDAO().getRemainingSingleRoom();
                    tvMain.setText(ori + " (" + roomList.size() + ")");
                    remainingRoomListAdapter.setData(roomList);
                }
                break;
            case R.id.chk_double:
                if (chkDouble.isChecked()) {
                    chkAll.setChecked(false);
                    chkSingle.setChecked(false);
                    roomList = RoomDatabase.getInstance(RemainingRoomListActivity.this).roomDAO().getRemainingDoubleRoom();
                    tvMain.setText(ori + " (" + roomList.size() + ")");
                    remainingRoomListAdapter.setData(roomList);
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

    private void loadData() {
        roomList = RoomDatabase.getInstance(this).roomDAO().getRemainingRoom();
        remainingRoomListAdapter.setData(roomList);
        tvMain.setText(ori + " (" + roomList.size() + ")");
    }
}