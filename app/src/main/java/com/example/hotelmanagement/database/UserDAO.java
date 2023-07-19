package com.example.hotelmanagement.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.hotelmanagement.entity.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    List<User> checkExistUser(String username, String password);

    @Query("DELETE FROM user WHERE username = :username")
    void deleteUser(String username);

    @Query("INSERT INTO user VALUES ('admin', 'admin')")
    void insertDemo();
}
