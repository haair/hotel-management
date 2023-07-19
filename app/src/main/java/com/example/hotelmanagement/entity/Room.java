package com.example.hotelmanagement.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "room")
public class Room implements Serializable {

    @PrimaryKey()
    @NonNull
    private String number;
    private int type;
    private int rate;
    private int status;

    public Room(String number, int type, int rate, int status) {
        this.number = number;
        this.type = type;
        this.rate = rate;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

