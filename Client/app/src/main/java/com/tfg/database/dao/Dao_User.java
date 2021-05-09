package com.tfg.database.dao;

import androidx.room.*;

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
