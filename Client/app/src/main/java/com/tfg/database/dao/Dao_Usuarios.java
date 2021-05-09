package com.tfg.database.dao;

import androidx.room.*;

import com.tfg.database.tables.User;
import com.tfg.database.tables.Usuarios;

import java.util.List;

@Dao
public interface Dao_Usuarios {
    @Query("SELECT * FROM Usuarios")
    List<Usuarios> getUsuarios();

    @Query("SELECT * FROM Usuarios WHERE id LIKE :id")
    Usuarios getUsuario(String id);

    @Insert
    void addUsuario(Usuarios usuarios);

    @Delete
    void deleteUsuario(Usuarios usuarios);

    @Query("UPDATE Usuarios SET bloqueo = :block WHERE id = :id")
    void updateBloqueo(String id, boolean block);

    @Query("UPDATE Usuarios SET silencio = :silence WHERE id = :id")
    void updateSilencio(String id, boolean silence);

}
