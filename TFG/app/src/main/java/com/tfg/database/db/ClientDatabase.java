package com.tfg.database.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tfg.database.dao.*;
import com.tfg.database.tables.*;

@Database(entities = {Usuarios.class, Grupos.class, Msg_Privado.class, Msg_Grupal.class, Contacts.class}, version = 1)
public abstract class ClientDatabase extends RoomDatabase {

    public abstract Dao_Usuarios getUsuariosDao();

    public abstract Dao_Grupos getGruposDao();

    public abstract Dao_Msg_Privado getMsgPrivadoDao();

    public abstract Dao_Msg_Grupal getMsgGrupalDao();

    public abstract Dao_Contacts getContactsDao();

}
