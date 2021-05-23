package com.tfg.database.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tfg.database.dao.Dao_Design;
import com.tfg.database.dao.Dao_Grupos;
import com.tfg.database.dao.Dao_Msg_Grupal;
import com.tfg.database.dao.Dao_Msg_Privado;
import com.tfg.database.dao.Dao_User;
import com.tfg.database.dao.Dao_Usuarios;
import com.tfg.database.tables.Design;
import com.tfg.database.tables.Grupos;
import com.tfg.database.tables.Msg_Grupal;
import com.tfg.database.tables.Msg_Privado;
import com.tfg.database.tables.User;
import com.tfg.database.tables.Usuarios;

@Database(entities = {User.class, Usuarios.class, Grupos.class, Design.class, Msg_Privado.class, Msg_Grupal.class}, version = 1)
public abstract class ClientDatabase extends RoomDatabase {

    public abstract Dao_User getUserDao();
    public abstract Dao_Usuarios getUsuariosDao();
    public abstract Dao_Grupos getGruposDao();
    public abstract Dao_Design getDesignDao();
    public abstract Dao_Msg_Privado getMsgPrivadoDao();
    public abstract Dao_Msg_Grupal getMsgGrupalDao();

}
