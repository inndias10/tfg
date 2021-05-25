package com.tfg.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tfg.database.tables.Grupos;

import java.util.List;

@Dao
public interface Dao_Grupos {
    @Query("SELECT * FROM Grupos")
    List<Grupos> getGrupos();

    @Query("SELECT estado FROM Grupos WHERE id = :id")
    String getEstado(String id);

    @Query("SELECT * FROM Grupos WHERE id = :id")
    Grupos getGrupo(String id);

    @Insert
    void addGrupo(Grupos grupo);

    @Delete
    void deleteGroup(Grupos grupo);

    @Update
    void updateGroup(Grupos grupo);

    @Query("UPDATE Grupos SET silencio = :silence WHERE id = :id")
    void updateSilencio(String id, boolean silence);

    @Query("UPDATE Grupos SET estado = :estado WHERE id = :id")
    void updateEstado(String id, String estado);

}
