package com.tfg.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tfg.database.tables.User;

import java.util.List;

@Dao
public interface Dao_User {
    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Query("SELECT * FROM User WHERE id LIKE :id")
    User getUser(String id);

    @Insert
    void addUser(User user);

    @Delete
    void deleteUser(User user);

    @Update
    void updateUser(User user);

}
