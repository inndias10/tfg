package com.tfg.database.dao;

import androidx.room.*;

import com.tfg.database.tables.Grupos;

import java.util.List;

@Dao
public interface Dao_Grupos {
    @Query("SELECT * FROM Grupos")
    List<Grupos> getGrupos();

    @Query("SELECT estado FROM Grupos WHERE id_nombre = :id")
    String estado(String id);

    @Insert
    void addGrupo(Grupos grupo);

    @Delete
    void deleteGroup(Grupos grupo);

    @Update
    void updateGroup(Grupos grupo);

    @Query("UPDATE Grupos SET silencio = :silence WHERE id_nombre = :id")
    void updateSilencio(String id, boolean silence);

    @Query("UPDATE Grupos SET estado = :estado WHERE id_nombre = :id")
    void updateEstado(String id, String estado);

}
