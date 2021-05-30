package com.tfg.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tfg.database.tables.Contacts;

import java.util.List;

@Dao
public interface Dao_Contacts {
    @Query("SELECT id FROM Contacts")
    List<String> getContactos();

    @Insert
    void addContact(Contacts contacts);

}
