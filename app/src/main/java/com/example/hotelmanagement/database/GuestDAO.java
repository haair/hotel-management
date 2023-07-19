package com.example.hotelmanagement.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hotelmanagement.entity.Guest;

import java.util.List;

@Dao
public interface GuestDAO {
    @Insert
    void insertGuest(Guest guest);

    @Query("SELECT * FROM guest")
    List<Guest> getAllGuest();

    @Query("DELETE FROM guest WHERE identify = :guestIdentify")
    void deleteGuest(String guestIdentify);

    @Query("SELECT * FROM guest WHERE name LIKE '%'||:name||'%'")
    List<Guest> searchGuest(String name);

    @Update
    void updateGuest(Guest guest);
}
