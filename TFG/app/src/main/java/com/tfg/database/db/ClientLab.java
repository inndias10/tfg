package com.tfg.database.db;

import android.content.Context;

import androidx.room.Room;

import com.tfg.database.dao.*;
import com.tfg.database.tables.*;

import java.util.ArrayList;
import java.util.List;

public class ClientLab {
    private static ClientLab clientdb;

    // ACCESO A DAOs CON MÉTODOS EN TABLAS
    private Dao_Usuarios usuariosDao;
    private Dao_Grupos gruposDao;
    private Dao_Msg_Privado msgPrivadoDao;
    private Dao_Msg_Grupal msgGrupalDao;
    private Dao_Contacts contactsDao;

    private ClientLab(Context context) {
        Context appContext = context.getApplicationContext();
        ClientDatabase database = Room.databaseBuilder(appContext, ClientDatabase.class, "dbclient").allowMainThreadQueries().build();

        usuariosDao = database.getUsuariosDao();
        gruposDao = database.getGruposDao();
        msgPrivadoDao = database.getMsgPrivadoDao();
        msgGrupalDao = database.getMsgGrupalDao();
        contactsDao = database.getContactsDao();

    }

    public static ClientLab get(Context context) {
        if (clientdb == null) {
            clientdb = new ClientLab(context);
        }
        return clientdb;
    }




    /* ----- CONSULTAS Y MODIFICACIONES DDBB CLIENT ----- */

    /* -- USUARIOS -- */
    public Usuarios getUsuario(String id) {
        return usuariosDao.getUsuario(id);
    }

    public void addUsuario(Usuarios usuario) {
        usuariosDao.addUsuario(usuario);
    }

    public void deleteUsuario(Usuarios usuario) {
        usuariosDao.deleteUsuario(usuario);
    }

    public void updateSilencio(String id, boolean silence) {
        usuariosDao.updateSilencio(id, silence);
    }

    public void updateBloqueo(String id, boolean block) {
        usuariosDao.updateBloqueo(id, block);
    }

    public void cleanChat(String id) {
        msgPrivadoDao.cleanChat(id);
    }

    public boolean getBloqueo(String id) {
        return usuariosDao.getBloqueo(id);
    }


    /* -- GRUPOS -- */
    public Grupos getGrupo(String id) {
        return gruposDao.getGrupo(id);
    }

    public void addGroup(Grupos group) {
        gruposDao.addGrupo(group);
    }

    public void deleteGroup(Grupos gr) {
        gruposDao.deleteGroup(gr);
    }

    public void updateAdmin(String id) {
        gruposDao.updateAdmin(id);
    }

    public void updateSilencioGrupo(String id, boolean silence) {
        gruposDao.updateSilencio(id, silence);
    }

    public void updateEstado(String id, String estado) {
        gruposDao.updateEstado(id, estado);
    }

    public void cleanChatGrupal(String id) {
        msgGrupalDao.cleanChat(id);
    }

    public String getEstado(String id) {
        return gruposDao.getEstado(id);
    }


    /* -- MSJ PRIVADO -- */
    public void addMsgPrivado(Msg_Privado mensaje) {
        msgPrivadoDao.addMsgPrivado(mensaje);
    }

    public List<Msg_Privado> getMessagesUser(String id) {
        return msgPrivadoDao.getMessagesUser(id);
    }

    public List<Msg_Privado> getLastMsgPrivado() {
        return msgPrivadoDao.getLastMessages();
    }


    /* -- MSJ GRUPO -- */
    public void addMsgGrupal(Msg_Grupal mensaje) {
        msgGrupalDao.addMsgGrupal(mensaje);
    }

    public List<Msg_Grupal> getMessagesGroup(String id) {
        return msgGrupalDao.getMessagesGroup(id);
    }

    public List<Msg_Grupal> getLastMsgGrupal() {
        return msgGrupalDao.getLastMessages();
    }


    /* -- CONTACTS -- */
    public List<String> getContacts() {
        return contactsDao.getContactos();
    }

    public void addContact(Contacts contacto) {
        contactsDao.addContact(contacto);
    }


}
