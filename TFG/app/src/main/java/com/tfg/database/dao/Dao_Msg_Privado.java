package com.tfg.database.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tfg.database.tables.Msg_Privado;

import java.util.List;

@Dao
public interface Dao_Msg_Privado {
    @Query("SELECT * FROM Msg_Privado")
    List<Msg_Privado> getMsgPrivados();

    @Query("DELETE FROM Msg_Privado WHERE id_usuario = :id")
    void cleanChat(String id);

    @Insert
    void addMsgPrivado(Msg_Privado mensaje);

    @Query("SELECT mp1.* FROM Msg_Privado mp1 " +
            "LEFT JOIN Msg_Privado mp2 ON (mp1.id_usuario = mp2.id_usuario AND mp1.id < mp2.id) " +
            "WHERE mp2.id IS NULL ORDER BY id DESC")
    List<Msg_Privado> getLastMessages();


}
