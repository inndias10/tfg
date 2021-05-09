package com.tfg.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.tfg.database.tables.Msg_Privado;

import java.util.List;

@Dao
public interface Dao_Msg_Privado {
    @Query("SELECT * FROM Msg_Privado")
    List<Msg_Privado> getMsgPrivados();

    @Query("DELETE FROM Msg_Privado WHERE id_usuario = :id")
    void cleanChat(String id);

}
