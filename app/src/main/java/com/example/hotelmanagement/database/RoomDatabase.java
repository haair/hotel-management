package com.example.hotelmanagement.database;

import android.content.Context;

import androidx.room.Database;

import com.example.hotelmanagement.entity.Guest;
import com.example.hotelmanagement.entity.Room;
import com.example.hotelmanagement.entity.User;

@Database(entities = {Guest.class, Room.class, User.class}, version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    private static final String DATABASE_NAME = "hair.db";
    private static RoomDatabase instance;

    public static synchronized RoomDatabase getInstance(Context context) {
        if (instance == null) {
           instance = androidx.room.Room.databaseBuilder(context.getApplicationContext(), RoomDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract RoomDAO roomDAO();
    public abstract GuestDAO guestDAO();
    public abstract UserDAO userDAO();
}
