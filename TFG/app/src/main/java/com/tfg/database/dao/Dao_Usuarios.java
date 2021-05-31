package com.tfg.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.tfg.database.tables.Usuarios;

import java.util.List;

@Dao
public interface Dao_Usuarios {
    @Query("SELECT * FROM Usuarios WHERE id LIKE :id")
    Usuarios getUsuario(String id);

    @Query("SELECT bloqueo FROM Usuarios WHERE id LIKE :id")
    boolean getBloqueo(String id);

    @Query("UPDATE Usuarios SET bloqueo = :block WHERE id = :id")
    void updateBloqueo(String id, boolean block);

    @Query("UPDATE Usuarios SET silencio = :silence WHERE id = :id")
    void updateSilencio(String id, boolean silence);


    @Insert
    void addUsuario(Usuarios usuarios);

    @Delete
    void deleteUsuario(Usuarios usuarios);


}
