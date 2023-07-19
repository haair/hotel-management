package com.example.hotelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hotelmanagement.activity.AddRoomActivity;
import com.example.hotelmanagement.activity.HomeActivity;
import com.example.hotelmanagement.database.RoomDatabase;
import com.example.hotelmanagement.entity.User;
import com.example.hotelmanagement.format.MyFormat;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;
    private ImageView ivAdd;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findElement();
        setOnClick();
//        RoomDatabase.getInstance(this).userDAO().insertUser(new User("admin", "admin"));
    }


    public void setOnClick() {
        btnLogin.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
    }

    public void findElement() {
        btnLogin = findViewById(R.id.btn_login);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        ivAdd = findViewById(R.id.iv_add_user);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Username or password can not empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                List<User> user = RoomDatabase.getInstance(MainActivity.this).userDAO().checkExistUser(username, password);
                if (user.size() == 0) {
                    Toast.makeText(this, "Username or password not correct", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.iv_add_user:
                RoomDatabase.getInstance(MainActivity.this).userDAO().insertUser(new User("admin", "admin"));
                Toast.makeText(this, "Add user admin", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}