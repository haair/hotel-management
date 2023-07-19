package com.example.hotelmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "guest")
public class Guest implements Serializable {
    private String name;
    private String phone;

    @PrimaryKey
    @NonNull
    private String identify;
    private String room;
    private String dateIn;

    public Guest(String name, String phone, String identify, String room, String dateIn) {
        this.name = name;
        this.phone = phone;
        this.identify = identify;
        this.room = room;
        this.dateIn = dateIn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDateIn() {
        return dateIn;
    }

    public void setDateIn(String dateIn) {
        this.dateIn = dateIn;
    }
}
