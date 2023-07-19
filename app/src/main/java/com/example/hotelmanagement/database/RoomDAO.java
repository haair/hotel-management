package com.example.hotelmanagement.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hotelmanagement.R;
import com.example.hotelmanagement.entity.Room;

import java.util.List;

@Dao
public interface RoomDAO {
    @Insert
    void insertRoom(Room room);

    @Query("SELECT * FROM room")
    List<Room> getAllRoom();

    @Query("SELECT * FROM room WHERE status = 1")
    List<Room> getBookedRoom();

    @Query("SELECT * FROM room WHERE status = 0")
    List<Room> getRemainingRoom();

    @Query("SELECT * FROM room WHERE status = 0 AND type = 1")
    List<Room> getRemainingSingleRoom();

    @Query("SELECT * FROM room WHERE status = 0 AND type = 2")
    List<Room> getRemainingDoubleRoom();

    @Query("DELETE FROM room WHERE number = :roomNumber")
    int deleteRoom(String roomNumber);

    @Query("UPDATE room SET status = 1 WHERE number = :roomNumber")
    void setBookedRoom(String roomNumber);

    @Query("UPDATE room SET status = 0 WHERE number = :roomNumber")
    void setNotBookedRoom(String roomNumber);

    @Query("SELECT * FROM room WHERE number LIKE '%' || :roomNumber || '%'")
    List<Room> searchRoom(String roomNumber);

    @Query("SELECT * FROM room WHERE status = 1 AND number LIKE '%' || :roomNumber || '%'")
    List<Room> searchBookedRoom(String roomNumber);

    @Query("SELECT * FROM room WHERE status = 0 AND number LIKE '%' || :roomNumber || '%'")
    List<Room> searchRemainingRoom(String roomNumber);

    @Update
    void updateRoom(Room room);
}
