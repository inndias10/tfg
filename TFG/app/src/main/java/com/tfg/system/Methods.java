package com.tfg.system;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.database.db.ClientLab;
import com.tfg.database.tables.Grupos;
import com.tfg.database.tables.Msg_Grupal;
import com.tfg.database.tables.Msg_Privado;
import com.tfg.database.tables.Usuarios;
import com.tfg.datos.Mensaje;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


    private void setObjectOutput(Socket client) {
        try {
            oos = new ObjectOutputStream(this.client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ---------- FUNCIONALIDADES COMPARTIDAS ---------- */

    // metodo encargado del envio de mensaje de fichero
    private void sendFileMessage(byte[] msg, String receptor, int type, String fileName, String timestamp) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, receptor, msg, type, 0, fileName, timestamp);
            oos.writeObject(msj);

        } catch (IOException e) {
            // error al enviar fichero
            e.printStackTrace();
        }

    }

    // metodo encargado de enviar mensajes individuales y grupales
    private void sendMessage(byte[] msg, String receptor, String chat, int type, String timestamp) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, receptor, msg, type, 0, timestamp);
            oos.writeObject(msj);

        } catch (IOException e) {
            // error al enviar mensaje de texto
            e.printStackTrace();
        }

    }

    // método para silenciar chats (grupales y privados)
    private void silenceChat(String id, boolean silence, String chat) {

        if (chat.equals("private")) {
            // silencio chat privado
            database.updateSilencio(id, silence);

        } else {
            // silencio chat grupal
            database.updateSilencioGrupo(id, silence);

        }

    }


    // método para vaciar una conversación
    private void cleanChat(String id, String chat) {

        // clean chat privado
        if (chat.equals("private")) {
            database.cleanChat(id);

            // clean chat grupal
        } else {
            database.cleanChatGrupal(id);

        }

    }


    // método para eliminar chats
    private void deleteChat(String id, String chat) {
        Usuarios usuarioEliminar;
        Grupos grupoEliminar;

        // eliminar chat privado
        if (chat.equals("private")) {
            cleanChat(id, chat);
            usuarioEliminar = database.getUsuario(id);
            database.deleteUsuario(usuarioEliminar);

            // eliminar chat grupal
        } else {
            // comprobar si ha salido antes de intentar eliminar
            if (database.getEstado(id).equals("out")) {
                cleanChat(id, chat);
                grupoEliminar = database.getGrupo(id);
                database.deleteGroup(grupoEliminar);

            } else {
                // Adv: no puedes eliminar un chat grupal si no has salido.

            }

        }

    }


    // método para bloquear o desbloquear un usuario // completar
    private void blockChat(String id, boolean block) {
        database.updateBloqueo(id, block);
        // se comprobará antes de recibir el mensaje de usuario B si usuario A tiene bloqueado a usuario B
    }

    /* ---------- FIN FUNCIONALIDADES COMPARTIDAS ---------- */


    /* ---------- FUNCIONALIDADES GRUPOS ---------- */
    // método para salir de un grupo
    private void exitGroup(String idGroup) {
        Mensaje msj;

        try {
            database.updateEstado(idGroup, "out");
            msj = new Mensaje(me, idGroup, null, 9, 0);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // método para eliminar un usuario de un grupo
    private void removeUserGroup(String idGroup, String idUser) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, idGroup, null, 4, 0, idUser);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addUserGroup(String idGroup, String idUser) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, idGroup, null, 5, 0, idUser);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addAdmin(String idGroup, String idUser) {
        Mensaje msj;

        try {
            msj = new Mensaje(me, idGroup, null, 12, 0, idUser);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ---------- FIN FUNCIONALIDADES GRUPOS ---------- */


    /* ---------- CREACION CONVERSACIONES ---------- */

    // creación de chat privado
    private void createPrivate() {
        Usuarios nuevoUsuario;
        String nick;
        Mensaje msj;

        nick = "nickname"; //// EditText.getText

        try {
            msj = new Mensaje(me, nick, null, 7, 0);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    // creación de chat grupal
    private void createGrupo(String group, String descripcion, String usuarios) {
        Mensaje msj;
        int idGrupo;

        try {
            msj = new Mensaje(me, group, descripcion.getBytes(), 2, 0, usuarios);
            oos.writeObject(msj);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* ---------- FIN CREACION CONVERSACIONES ---------- */

    /* ----- OTROS ----- */

    // devuelve un hashmap con los ultimos mensajes de cada usuario para el menu "privados"
    private HashMap<String, String[]> lastPrivateMessages() {
        HashMap<String, String[]> mensajes;
        List<Msg_Privado> msjsDatabase;
        String[] datos;

        msjsDatabase = database.getLastPrivateMessages();
        mensajes = new HashMap<>();

        for (Msg_Privado msp : msjsDatabase) {
            mensajes.put(msp.getIdUsuario(), new String[]{msp.getIdEmisor(), msp.getMensaje(), msp.getTimestamp()});
        }

        return mensajes;

    }


    // devuelve un hashmap con los ultimos mensajes grupales para el menu "grupos"
    private HashMap<String, String[]> lastGroupMessages() {
        HashMap<String, String[]> mensajes;
        List<Msg_Grupal> msjsDatabase;
        String[] datos;

        msjsDatabase = database.getLastGroupMessages();
        mensajes = new HashMap<>();

        for (Msg_Grupal msg : msjsDatabase) {
            mensajes.put(msg.getIdGrupo(), new String[]{msg.getIdUsuario(), msg.getMensaje(), msg.getTimestamp()});
        }

        return mensajes;

    }


    private String getMomentFromTime(String date) {
        String hoy, today, aux, output = "";

        hoy = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        // ver como queremos que lo muestre segun dias, fechas pasados x dias...
        return output;
    }


}
