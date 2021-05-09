package com.tfg.database;

import android.content.Context;

import androidx.room.Room;

import com.tfg.database.dao.*;
import com.tfg.database.tables.*;

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
    public List<User> getUsers() {
        return userDao.getUsers();
    }
    public List<Usuarios> getUsuarios() {
        return usuariosDao.getUsuarios();
    }
    public List<Grupos> getGrupos() {
        return gruposDao.getGrupos();
    }
    public List<Design> getDesign() {
        return designDao.getDesign();
    }
    public List<Msg_Privado> getMsgPrivado () {
        return msgPrivadoDao.getMsgPrivados();
    }
    public List<Msg_Grupal> getMsgGrupal () {
        return msgGrupalDao.getMsgGrupal();
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public User getUser(String id) {
        return userDao.getUser(id);
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

}
