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

import com.example.hotelmanagement.database.RoomDatabase;
import com.example.hotelmanagement.adapter.GuestBookingListAdapter;
import com.example.hotelmanagement.R;
import com.example.hotelmanagement.entity.Guest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class GuestBookingListActivity extends AppCompatActivity  implements View.OnClickListener{

    RecyclerView rcvAllGuestList;
    GuestBookingListAdapter guestBookingListAdapter;
    ImageView ivGuestBookingListBackHome;
    ImageView ivSearch;
    EditText etSearch;
    TextView tvMain;
    List<Guest> guestList;
    LinearLayout llEdit;
    LinearLayout llDelete;
    LinearLayout llPayment;
    private int MY_REQUEST_CODE = 11;
    private String ori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_booking_list);
        findElement();
        setOnClick();
        setupRecycleView();
        tvMain.setText(ori + " (" + guestList.size() + ")");
    }

    public void findElement() {
        rcvAllGuestList = findViewById(R.id.rcv_all_guest);
        ivGuestBookingListBackHome = findViewById(R.id.iv_g_all_guest_back_to_home);
        ivSearch = findViewById(R.id.iv_search);
        etSearch = findViewById(R.id.et_search);
        tvMain = findViewById(R.id.tv_main);
        ori = tvMain.getText().toString();
    }

    public void setupRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvAllGuestList.setLayoutManager(linearLayoutManager);
        guestList = RoomDatabase.getInstance(this).guestDAO().getAllGuest();
        guestBookingListAdapter = new GuestBookingListAdapter(new GuestBookingListAdapter.IClickMoreButton() {
            @Override
            public void showBottomDialog(Guest guest) {
                View view = getLayoutInflater().inflate(R.layout.bot_sheet_guest_layout, null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(GuestBookingListActivity.this);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();
                setupBotSheetFunction(view, guest, bottomSheetDialog);
            }
        });
        guestBookingListAdapter.setData(guestList);
        rcvAllGuestList.setAdapter(guestBookingListAdapter);
    }

    private void setupBotSheetFunction(View view, Guest guest, BottomSheetDialog bottomSheetDialog) {
        llEdit = view.findViewById(R.id.ll_edit);
        llDelete = view.findViewById(R.id.ll_delete);
        llPayment = view.findViewById(R.id.ll_payment);
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                new AlertDialog.Builder(GuestBookingListActivity.this)
                        .setTitle("DELETE GUEST: "+guest.getName())
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RoomDatabase.getInstance(GuestBookingListActivity.this).guestDAO().deleteGuest(guest.getIdentify());
                                RoomDatabase.getInstance(GuestBookingListActivity.this).roomDAO().setNotBookedRoom(guest.getRoom());
                                Toast.makeText(GuestBookingListActivity.this, "Delete successful", Toast.LENGTH_SHORT).show();
                                loadData();
                            }
                        })
                        .setNegativeButton("No", null).show();
            }
        });
        llEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuestBookingListActivity.this, UpdateGuestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("guestObject", guest);
                intent.putExtras(bundle);
                startActivityForResult(intent, MY_REQUEST_CODE);
                bottomSheetDialog.dismiss();
            }
        });
        llPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                Intent intent = new Intent(GuestBookingListActivity.this, PaymentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("guestPayment", guest);
                intent.putExtras(bundle);
                startActivityForResult(intent, MY_REQUEST_CODE);
            }
        });
    }

    public void setOnClick() {
        ivGuestBookingListBackHome.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                guestList = RoomDatabase.getInstance(GuestBookingListActivity.this).guestDAO().searchGuest(etSearch.getText().toString());
                guestBookingListAdapter.setData(guestList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_g_all_guest_back_to_home:
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

    private void loadData() {
        guestList = RoomDatabase.getInstance(this).guestDAO().getAllGuest();
        guestBookingListAdapter.setData(guestList);
        tvMain.setText(ori + " (" + guestList.size() + ")");
    }
}