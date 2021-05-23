package com.tfg.database.db;

import android.content.Context;

import androidx.room.Room;

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

import java.util.List;

public class ClientLab {
    private static ClientLab clientdb;

    // ACCESO A DAOs CON MÉTODOS EN TABLAS
    private Dao_User userDao;
    private Dao_Usuarios usuariosDao;
    private Dao_Grupos gruposDao;
    private Dao_Design designDao;
    private Dao_Msg_Privado msgPrivadoDao;
    private Dao_Msg_Grupal msgGrupalDao;

    private ClientLab(Context context) {
        Context appContext = context.getApplicationContext();
        ClientDatabase database = Room.databaseBuilder(appContext, ClientDatabase.class, "dbclient").allowMainThreadQueries().build();

        userDao = database.getUserDao();
        usuariosDao = database.getUsuariosDao();
        gruposDao = database.getGruposDao();
        designDao = database.getDesignDao();
        msgPrivadoDao = database.getMsgPrivadoDao();
        msgGrupalDao = database.getMsgGrupalDao();

    }

    public static ClientLab get(Context context) {
        if (clientdb == null) {
            clientdb = new ClientLab(context);
        }
        return clientdb;
    }

    // MÉTODOS PARA CONSULTAR Y MODIFICAR BASE DE DATOS CLIENT

    /* -- MSJ PRIVADO -- */

    public List<Msg_Privado> getLastPrivateMessages() {
        return msgPrivadoDao.getLastMessages();
    }


    public Usuarios getUsuario(String id) {
        return usuariosDao.getUsuario(id);
    }

    public void updateBloqueo(String id, boolean block) {
        usuariosDao.updateBloqueo(id, block);
    }

    public void cleanChat(String id) {
        msgPrivadoDao.cleanChat(id);
    }

    public void cleanChatGrupal(String id) {
        msgGrupalDao.cleanChat(id);
    }

    public void deleteUsuario(Usuarios usuario) {
        usuariosDao.deleteUsuario(usuario);
    }

    public void updateSilencio(String id, boolean silence) {
        usuariosDao.updateSilencio(id, silence);
    }

    public void updateSilencioGrupo(String id, boolean silence) {
        gruposDao.updateSilencio(id, silence);
    }

    public void addUsuario(Usuarios usuario) {
        usuariosDao.addUsuario(usuario);
    }

    public void addMsgPrivado(Msg_Privado mensaje) {
        msgPrivadoDao.addMsgPrivado(mensaje);
    }

    public String getEstado(String id) {
        return gruposDao.getEstado(id);
    }

    public Grupos getGrupo(String id) {
        return gruposDao.getGrupo(id);
    }

    public void deleteGroup(Grupos gr) {
        gruposDao.deleteGroup(gr);
    }

    public void updateEstado(String id, String estado) {
        gruposDao.updateEstado(id, estado);
    }

    public void addMsgGrupal(Msg_Grupal mensaje) {
        msgGrupalDao.addMsgGrupal(mensaje);
    }

    public void addGroup(Grupos group) {
        gruposDao.addGrupo(group);
    }

    public List<Msg_Grupal> getLastGroupMessages() {
        return msgGrupalDao.getLastMessages();
    }

}
