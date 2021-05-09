package com.tfg.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.tfg.database.ClientLab;
import com.tfg.database.tables.User;
import com.tfg.database.tables.Usuarios;
import com.tfg.datos.Mensaje;
import com.trabajoFinal.chat.R;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final String HOST = "192.168.1.42";
    public final String ADDRESS = "localhost";
    public final int PUERTO = 6000;

    ClientLab database;

    Socket cliente;
    ObjectOutputStream oos;

    String me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        if (!comprobarRegistro()) {
            iniciarRegistro();
            // necesitamos parar la actividad main cuando se lanza la actividad registro
        }

        // Toast.makeText(this, "Ya he activado el registro", Toast.LENGTH_LONG).show();

        // comentar en ejecucion
        /* me = getActualUser();

        try {
            cliente = new Socket(HOST, PUERTO);
            database = ClientLab.get(this);
            oos = new ObjectOutputStream(cliente.getOutputStream());


        } catch (IOException e) {
            Toast.makeText(this, "Error I/O MainActivity Socket", Toast.LENGTH_SHORT).show();
        }
        */

    }


    @Override
    protected void onStart() {
        // lo que se ejecuta cuando se inicia la aplicación después del onCreate
        super.onStart();
    }


    // comprueba si el usuario ya se ha registrado en el dispositivo
    private boolean comprobarRegistro() {
        // probar a utilizar variable generica antes de lanzar Registro
        ClientLab database;

        database = ClientLab.get(this);
        if (database.getUsers().isEmpty()) {
            return false;

        } else {
            return true;

        }

    }


    // Inicia la pantalla de registro
    private void iniciarRegistro() {
        Intent i = new Intent(this, Registro.class);
        // startActivityForResult(i, 1);
        startActivity(i);
    }


    /* ---------- GET ---------- */

    // devuelve el nick del usuario que está registrado en el dispositivo
    private String getActualUser() {
        List<User> users;

        database = ClientLab.get(this);
        users = database.getUsers();

        return users.get(0).getId();

    }


    /* ---------- FUNCIONALIDADES COMPARTIDAS ---------- */

    // metodo encargado de enviar mensajes individuales y grupales
    private void sendMessage(byte[] msg, String receptor, String chat, int type) {
        Mensaje msj;

        // mensaje privado
        if (chat.equals("private")) {
            if (type == 1) {
                // mensaje texto privado
                try {
                    msj = new Mensaje(me, receptor, msg, 1, 0);
                    oos.writeObject(msj);

                } catch (IOException e) {
                    Log.e("sendMessage", "Error al enviar mensaje de texto privado");
                }

            } else if (type == 6) {
                // mensaje fichero privado
                try {
                    msj = new Mensaje(me, receptor, msg, 6, 0);
                    oos.writeObject(msj);

                } catch (IOException e) {
                    Log.e("sendMessage", "Error al enviar mensaje de fichero privado");
                }

            }

            // mensaje grupal
        } else {
            try {
                msj = new Mensaje(me, receptor, msg, 3, 0);
                oos.writeObject(msj);

            } catch (IOException e) {
                // error al enviar mensaje grupal
                e.printStackTrace();
            }

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
        Usuarios userEliminar;

        // eliminar chat privado
        if (chat.equals("private")) {
            cleanChat(id, chat);
            userEliminar = database.getUsuario(id);
            database.deleteUsuario(userEliminar);

            // eliminar chat grupal
        } else {
            cleanChat(id, chat);
            // comprobar si ha salido antes de intentar eliminar

        }

    }


    // método para bloquear o desbloquear un usuario // completar
    private void blockChat(String id, boolean block) {
        database.updateBloqueo(id, block);
        // se comprobará antes de recibir el mensaje de usuario B si usuario A tiene bloqueado a usuario B
    }

    /* ---------- FIN FUNCIONALIDADES COMPARTIDAS ---------- */


    /* ---------- CREACION CONVERSACIONES ---------- */

    // creación de chat privado // completar
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


    // creación de chat grupal // completar
    private void createGrupo() {
        Mensaje msj;
        String groupName, descripcion;
        int idGrupo;

        groupName = "nombre grupo"; //// EditText.getText
        descripcion = "descripcion";  //// EditText.getText

        try {
            msj = new Mensaje(me, groupName, descripcion.getBytes(), 2, 0);
            oos.writeObject(msj);
            // envía datos del grupo al server y me devuelve el id del grupo generado por el servidor
            // ois.readObject(msj);
            // id = ois.getId...

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* ---------- FIN CREACION CONVERSACIONES ---------- */

}
