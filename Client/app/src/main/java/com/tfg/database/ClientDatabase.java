package com.tfg.database;

import androidx.room.*;

import com.tfg.database.dao.*;
import com.tfg.database.tables.*;

@Database(entities = {User.class, Usuarios.class, Grupos.class, Design.class, Msg_Privado.class, Msg_Grupal.class}, version = 1)
public abstract class ClientDatabase extends RoomDatabase{

    public abstract Dao_User getUserDao();
    public abstract Dao_Usuarios getUsuariosDao();
    public abstract Dao_Grupos getGruposDao();
    public abstract Dao_Design getDesignDao();
    public abstract Dao_Msg_Privado getMsgPrivadoDao();
    public abstract Dao_Msg_Grupal getMsgGrupalDao();

}
