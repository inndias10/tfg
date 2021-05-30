package com.tfg.system;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.database.db.ClientLab;
import com.tfg.database.tables.Contacts;
import com.tfg.database.tables.Grupos;
import com.tfg.database.tables.Msg_Grupal;
import com.tfg.database.tables.Msg_Privado;
import com.tfg.database.tables.Usuarios;
import com.tfg.datos.Mensaje;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Methods extends AppCompatActivity {
    private ClientLab database;
    private Socket client;
    private ObjectOutputStream oos;
    private String me;

    public Methods(ClientLab database, Socket client, String me) {
        this.database = database;
        this.client = client;
        this.me = me;
        setObjectOutput(client);

    }


    /* ---------- FUNCIONALIDADES COMPARTIDAS ---------- */

    // envío de mensaje de fichero privados (type 6) y grupales (type 8) // terminar comprobar si el usuario está bloqueado y si SÍ no enviar
    public void sendFileMessage(int type, byte[] content, String receptor, String fileName, String timestamp) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, receptor, content, type, 0, fileName, timestamp);
            oos.writeObject(msj);
            showOtherMessage(type, receptor, me, fileName, timestamp);

        } catch (IOException e) {
            // error al enviar fichero
            e.printStackTrace();
        }

    }

    // envío mensajes de texto privados (type 1) y grupales (type 3) // terminar
    public void sendMessage(int type, String mensaje, String receptor, String timestamp) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, receptor, mensaje.getBytes(), type, 0, timestamp);
            oos.writeObject(msj);
            showTextMessage(type, receptor, me, mensaje, timestamp);

        } catch (IOException e) {
            // error al enviar mensaje de texto
            e.printStackTrace();
        }

    }

    // silenciar chats // completo
    public void silenceChat(int chat, String id, boolean silence) {

        // privados
        if (chat == 1) {
            database.updateSilencio(id, silence);

            // grupales
        } else {
            database.updateSilencioGrupo(id, silence);

        }

    }

    // vaciar chats // completo
    public void cleanChat(int chat, String id) {

        // privados
        if (chat == 1) {
            database.cleanChat(id);

            // grupales
        } else {
            database.cleanChatGrupal(id);

        }

    }

    // eliminar chats // completo
    public void deleteChat(int chat, String id) {
        Usuarios usuarioEliminar;
        Grupos grupoEliminar;

        // privados
        if (chat == 1) {
            cleanChat(chat, id);
            usuarioEliminar = database.getUsuario(id);
            database.deleteUsuario(usuarioEliminar);

            // grupales
        } else {
            // comprobar si ha salido antes de intentar eliminar
            if (database.getEstado(id).equals("out")) {
                cleanChat(chat, id);
                grupoEliminar = database.getGrupo(id);
                database.deleteGroup(grupoEliminar);

            } else {
                // Adv: no puedes eliminar un chat grupal si no has salido.

            }

        }

    }

    // bloquear o desbloquear un usuario // completo
    public void blockChat(String id, boolean block) {
        database.updateBloqueo(id, block);
    }

    /* ---------- FIN FUNCIONALIDADES COMPARTIDAS ---------- */




    /* ---------- FUNCIONALIDADES GRUPOS ---------- */

    // type = (4: expulsar, 5: añadir, 12: asignar admin)
    public void adminFunctions(int type, String idGroup, String idUser) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, idGroup, null, type, 0, idUser);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // salir de un grupo
    private void communicateExitGroup(String idGroup) {
        Mensaje msj;

        try {

            msj = new Mensaje(me, idGroup, null, 9, 0);
            oos.writeObject(msj);
            exitGroup(idGroup, me, "Has abandonado el grupo");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void exitGroup(String idGroup, String idUser, String mensaje) {
        groupalInfoMsgRcv(idGroup, mensaje);

        if (idUser.equals(me)) {
            database.updateEstado(idGroup, "out");

        }

    }

    public void newAdmin(String idGroup, String idUser, String mensaje) {
        groupalInfoMsgRcv(idGroup, mensaje);

        if (idUser.equals(me)) {
            database.updateAdmin(idGroup);
        }

    }


    /* ---------- FIN FUNCIONALIDADES GRUPOS ---------- */


    /* ---------- CREACION CONVERSACIONES ---------- */

    // comprueba con el servidor si puede crear el privado // completo
    private void prepareCreatePrivate(String nick) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, nick, null, 7, 0);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // envia al server datos para crear grupo
    private void prepareCreateGroup(String group, String descripcion, String usuarios) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, group, descripcion.getBytes(), 2, 0, usuarios);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // creación de chat privado una vez existe el nick // completo
    public void createPrivateConversation(int error, String nick) {
        if (error == 0) {
            database.addContact(new Contacts(nick));
            // timeout con mensaje de que existe y mostrar activity conversacion privada

        } else {
            // Adv no existe el usuario
        }

    }

    // crea el nuevo usuario con el mensaje de inicio de conversacion
    public void createPrivate(String id, String emisor, String mensaje, String type, String timestamp) {

        // 'me' crea la conversacion
        if (emisor.equals(me)) {
            database.addUsuario(new Usuarios(id));
            if (type.equals("text")) {
                showTextMessage(1, id, me, mensaje, timestamp);
            } else {
                showOtherMessage(1, id, me, type, timestamp);
            }

            // otro ha creado una conversacion privada con 'me'
        } else {
            database.addUsuario(new Usuarios(emisor));
            if (type.equals("text")) {
                showTextMessage(1, emisor, emisor, mensaje, timestamp);
            } else {
                showOtherMessage(1, emisor, emisor, type, timestamp);
            }

        }

    }

    public void createGroup(String admin, String id, String fecha, String descripcion, String mensaje) {

        // 'me' es el creador
        if (me.equals(admin)) {
            database.addGroup(new Grupos(id, fecha, descripcion, true));
            groupalInfoMsgRcv(id, mensaje);
            // crear act conversacion grupal

            // alguien crea un grupo con 'me'
        } else {
            database.addGroup(new Grupos(id, fecha, descripcion, false));
            groupalInfoMsgRcv(id, mensaje);

        }

    }


    /* ---------- FIN CREACION CONVERSACIONES ---------- */




    /* ----- DATABASE QUERIES ----- */

    // devuelve un hashmap con los ultimos mensajes de las conversaciones privadas '1' y grupales '2' // completo
    public HashMap<String, ArrayList<String>> lastMessages(int chat) {
        List<Msg_Privado> msjsPrivados;
        List<Msg_Grupal> msjsGrupales;
        HashMap<String, ArrayList<String>> data;
        ArrayList<String> header, content, time;

        data = new HashMap<>();
        header = new ArrayList<>();
        content = new ArrayList<>();
        time = new ArrayList<>();

        if (chat == 1) {
            msjsPrivados = database.getLastMsgPrivado();
            for (Msg_Privado msp : msjsPrivados) {
                header.add(msp.getIdUsuario());
                content.add(msp.getIdEmisor() + ": " + msp.getMensaje());
                time.add(msp.getTimestamp());
            }

        } else if (chat == 2) {
            msjsGrupales = database.getLastMsgGrupal();
            for (Msg_Grupal msg : msjsGrupales) {
                header.add(msg.getIdGrupo());
                content.add(msg.getMensaje());
                time.add(msg.getTimestamp());
            }

        }

        data.put("header", header);
        data.put("content", content);
        data.put("time", time);

        return data;

    }

    // devuelve hashmap con arraylist para rellenar la activity de la conversacion con los mensajes
    public HashMap<String, ArrayList<String>> allMessages(int chat, String id) {
        List<Msg_Privado> msjsPrivado;
        List<Msg_Grupal> msjsGrupo;
        HashMap<String, ArrayList<String>> data;
        ArrayList<String> emisor, mensaje, time;

        data = new HashMap<>();
        emisor = new ArrayList<>();
        mensaje = new ArrayList<>();
        time = new ArrayList<>();

        if (chat == 1) {
            msjsPrivado = database.getMessagesUser(id);
            for (Msg_Privado msp : msjsPrivado) {
                emisor.add(msp.getIdUsuario());
                mensaje.add(msp.getMensaje());
                time.add(msp.getTimestamp());

            }

        } else if (chat == 2) {
            msjsGrupo = database.getMessagesGroup(id);
            for (Msg_Grupal msg : msjsGrupo) {
                emisor.add(msg.getIdUsuario());
                mensaje.add(msg.getMensaje());
                time.add(msg.getTimestamp());

            }

        }

        data.put("emisor", emisor);
        data.put("mensajes", mensaje);
        data.put("time", time);

        return data;

    }

    // devuelve al con todos los contactos para cargarlos a la hora de crear un grupo
    public ArrayList<String> allContacts() {
        return new ArrayList<>(database.getContacts());
    }


    /* ----- FIN DATABASE QUERIES ----- */




    /* ----- ENLACE CON INTERFAZ ----- */
    /* -- Métodos para mostrar mensajes en la interfaz -- */

    // muestra el mensaje texto donde haga falta y lo guarda en ddbb
    public void showTextMessage(int chat, String conver, String emisor, String mensaje, String timestamp) {
        Msg_Privado msp;
        Msg_Grupal msg;

        // msj privado
        if (chat == 1) {
            // print mensaje
            newPrivateMessage(conver, emisor, mensaje, "text", timestamp);

            // msj grupal
        } else if (chat == 3) {
            // print mensaje
            newGroupMessage(conver, emisor, mensaje, "text", timestamp);

        }

    }

    /* muestra el mensaje fichero donde haga falta y lo guarda en ddbb */
    public void showOtherMessage(int chat, String conver, String emisor, String filename, String timestamp) {
        Msg_Privado msp;
        Msg_Grupal msg;
        String ext;

        ext = filename.split(".")[filename.split(".").length - 1];
        // privado
        if (chat == 6) {
            // print mensaje
            newPrivateMessage(conver, emisor, filename, ext, timestamp);

            // grupal
        } else if (chat == 8) {
            // print message
            newGroupMessage(conver, emisor, filename, ext, timestamp);

        }

    }

    /* mensajes informacion sobre grupo */
    public void groupalInfoMsgRcv(String grupo, String mensaje) {
        // print mensaje
        newGroupMessage(grupo, "info", mensaje, null, null);

    }


    /* ----- FIN ENLACE CON INTERFAZ ----- */




    /* ----- NEW MESSAGES ----- */
    /* -- Almacena nuevo mensaje en tablas Msg_Privado y Msg_Grupal -- */

    // privado
    public synchronized void newPrivateMessage(String idUser, String idEmisor, String mensaje, String ext, String timestamp) {
        Msg_Privado msg;

        msg = new Msg_Privado(idUser, idEmisor, mensaje, ext, timestamp);
        database.addMsgPrivado(msg);
    }

    // grupal
    public synchronized void newGroupMessage(String idGrupo, String idUser, String mensaje, String ext, String timestamp) {
        Msg_Grupal msg;

        msg = new Msg_Grupal(idGrupo, idUser, mensaje, ext, timestamp);
        database.addMsgGrupal(msg);
    }

    /* ----- FIN NEW MESSAGES ----- */




    /* OTHER */

    // returns true si la tabla Usuarios tiene 'id'
    public boolean checkUser(String id) {
        if (database.getUsuario(id) == null) {
            return false;

        } else {
            return true;

        }
    }

    private String getMomentFromTime(String date) {
        String hoy, today, aux, output = "";

        hoy = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        // ver como queremos que lo muestre segun dias, fechas pasados x dias...
        return output;
    }

    // setter ObjectOutputStream
    private void setObjectOutput(Socket client) {
        try {
            oos = new ObjectOutputStream(this.client.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
