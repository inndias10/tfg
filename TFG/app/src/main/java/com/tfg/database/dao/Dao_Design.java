package com.tfg.database.dao;


import androidx.room.Dao;
import androidx.room.Query;

import com.tfg.database.tables.Design;

import java.util.List;

@Dao
public interface Dao_Design {
    @Query("SELECT * FROM Design")
    List<Design> getDesign();
}
