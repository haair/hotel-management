package com.example.hotelmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.database.RoomDatabase;
import com.example.hotelmanagement.entity.Guest;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.format.MyFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack;
    private Guest guest;
    private TextView tvName;
    private TextView tvPhone;
    private TextView tvIdentify;
    private TextView tvRoom;
    private TextView tvDateIn;
    private TextView tvPaytime;
    private TextView tvMustPay;
    private Button btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        findElement();
        setTextHolder();
        setOnClick();
    }

    public void findElement() {
        guest = (Guest) getIntent().getExtras().get("guestPayment");
        ivBack = findViewById(R.id.iv_back);
        tvName = findViewById(R.id.tv_pay_name);
        tvPhone = findViewById(R.id.tv_pay_phone);
        tvIdentify = findViewById(R.id.tv_pay_identify);
        tvRoom = findViewById(R.id.tv_pay_room);
        tvDateIn = findViewById(R.id.tv_pay_date_in);
        tvPaytime = findViewById(R.id.tv_pay_total_hours);
        tvMustPay = findViewById(R.id.tv_pay_must_pay);
        btnComplete = findViewById(R.id.btn_complete);
    }

    public void setTextHolder() {
        String mustPay;
        if (guest != null) {
            tvName.setText(guest.getName());
            tvPhone.setText(guest.getPhone());
            tvIdentify.setText(guest.getIdentify());
            tvRoom.setText(guest.getRoom());
            tvDateIn.setText(guest.getDateIn());
            tvPaytime.setText(String.valueOf(calTime()) + "h");
            if (calTime() == 0) {
                mustPay = MyFormat.VND(moneyMustPay()) + " đ";
            }
            else {
                mustPay = MyFormat.VND(moneyMustPay()*calTime()) + " đ";
            }
            tvMustPay.setText(mustPay);
        }
    }

    public long calTime() {
        String dateStart = guest.getDateIn();
        String dateEnd = MyFormat.getDateTimeNow();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormat.parse(dateStart);
            d2 = dateFormat.parse(dateEnd);
        }
        catch (ParseException e) {

        }

        long diff = d2.getTime() - d1.getTime();
        long diffHours = diff / (60 * 60 * 1000);
        return diffHours;
    }

    public long moneyMustPay() {
        List<Room> room = RoomDatabase.getInstance(this).roomDAO().searchRoom(guest.getRoom());
        int rate = room.get(0).getRate();
        return rate;
    }

    public void setOnClick() {
        ivBack.setOnClickListener(this);
        btnComplete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_complete:
                new AlertDialog.Builder(PaymentActivity.this)
                        .setTitle("DO YOU WANT PAYMENT?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                RoomDatabase.getInstance(PaymentActivity.this).guestDAO().deleteGuest(guest.getIdentify());
                                RoomDatabase.getInstance(PaymentActivity.this).roomDAO().setNotBookedRoom(guest.getRoom());
                                Toast.makeText(PaymentActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                setResult(Activity.RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null).show();

                break;
        }
    }
}