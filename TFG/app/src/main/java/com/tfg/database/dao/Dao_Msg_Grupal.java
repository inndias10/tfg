package com.tfg.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.tfg.database.tables.Msg_Grupal;
import com.tfg.database.tables.Msg_Privado;

import java.util.List;

@Dao
public interface Dao_Msg_Grupal {
    @Query("SELECT * FROM Msg_Grupal")
    List<Msg_Grupal> getMsgGrupal();

    @Query("DELETE FROM Msg_Grupal WHERE id_grupo = :id")
    void cleanChat(String id);

    @Insert
    void addMsgGrupal(Msg_Grupal mensaje);

    @Query("SELECT mg1.* FROM Msg_Grupal mg1 " +
            "LEFT JOIN Msg_Grupal mg2 ON (mg1.id_grupo = mg2.id_grupo AND mg1.id < mg2.id) " +
            "WHERE mg2.id IS NULL ORDER BY id DESC")
    List<Msg_Grupal> getLastMessages();

}
