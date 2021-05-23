package com.tfg.system;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.database.db.ClientLab;
import com.tfg.database.tables.*;
import com.tfg.datos.Mensaje;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HiloClient extends Thread {

    private final Socket cliente;
    private final ClientLab database;
    private final String me;

    public HiloClient(Socket cl, ClientLab db, String yo) {
        this.cliente = cl;
        this.database = db;
        this.me = yo;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        Mensaje msj;
        String emisor, mensaje, fileName, grupo, descripcion, time;
        int msgType = 0;
        int error;

        Usuarios usuario;
        Grupos group;
        Msg_Grupal msgGroup;

        try {

            // RECIBE MENSAJES DEL HH SERVIDOR
            do {
                ois = new ObjectInputStream(cliente.getInputStream());
                msj = (Mensaje) ois.readObject();
                msgType = msj.getTipo();

                switch (msgType) {
                    // mensaje de texto privado
                    case 1:
                        emisor = msj.getEmisor();
                        mensaje = new String(msj.getMensaje());
                        // print(mensaje) en conversacion si esta dentro / en menu si esta en menu / solo notificacion si no esta dentro
                        // comprueba si el usuario que envia el mensaje ya existia para el cliente y si no lo crea
                        if (database.getUsuario(emisor) == null) {
                            usuario = new Usuarios(emisor);
                            database.addUsuario(usuario);

                        }

                        newPrivateMessage(emisor, mensaje, "timestamp");
                        break;


                    // mensaje fichero privado
                    case 6:
                        emisor = msj.getEmisor();
                        fileName = msj.getAux();

                        // UtilsFiles.createFile(fileName, msj.getMensaje());
                        newPrivateMessage(emisor, fileName, "timestamp");
                        break;


                    // creacion de conversacion privada
                    case 7:
                        emisor = msj.getEmisor();
                        error = msj.getError();

                        // existe un usuario
                        if (error == 0) {
                            usuario = new Usuarios(emisor);
                            database.addUsuario(usuario);

                        } else if (error == 6) {
                            // Adv: no existe el usuario a la hora de crear la conversacion
                        }

                        // tiene que aceptar este usuario para continuar la conversacion? revisar linea 49
                        break;

                    case 2:
                        emisor = msj.getEmisor();
                        grupo = msj.getReceptor(); // tiene que tener concatenado id/nombre
                        descripcion = new String(msj.getMensaje());
                        mensaje = msj.getAux();
                        time = msj.getTimestamp();

                        msgGroup = new Msg_Grupal(grupo, "system", mensaje, time);

                        if (me.equals(emisor)) {
                            group = new Grupos(grupo, time, descripcion, true);

                        } else {
                            group = new Grupos(grupo, time, descripcion, false);

                        }

                        // introduce en ddbb nuevo grupo y el mensaje de bienvenida
                        database.addGroup(group);
                        database.addMsgGrupal(msgGroup);

                        break;


                    // mensaje texto grupal
                    case 3:
                        emisor = msj.getEmisor();
                        grupo = msj.getReceptor();
                        mensaje = new String(msj.getMensaje());

                        // print(mensaje)
                        newGroupMessage(grupo, emisor, mensaje, "timestamp");
                        break;

                    // mensaje fichero grupal
                    case 8:
                        emisor = msj.getEmisor();
                        grupo = msj.getReceptor();
                        fileName = msj.getAux();

                        // UtilsFiles.createFile(fileName, msj.getMensaje());
                        newGroupMessage(grupo, emisor, fileName, "timestamp");
                        break;

                    // usuario añadido a grupo
                    case 11:
                        grupo = msj.getReceptor();
                        mensaje = new String(msj.getMensaje());

                        // print(mensaje);
                        newGroupMessage(grupo, "info", mensaje, "timestamp");
                        break;

                    // usuario abandona grupo (por salir o expulsión)
                    case 10:
                        mensaje = new String(msj.getMensaje());
                        grupo = msj.getReceptor();

                        // print(mensaje);
                        newGroupMessage(grupo, "info", mensaje, "timestamp");
                        break;

                    case 13:
                        mensaje = new String(msj.getMensaje());
                        grupo = msj.getReceptor();

                        newGroupMessage(grupo, "info", mensaje, "timestamp");
                        break;

                    default:
                }


            } while (msgType != -1);


        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Se ha cerrado la conexión.");

        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException ex) {
                    System.out.println("Error cerrando el ObjectInputStream HiloClient");
                }

            }

            if (cliente != null) {
                try {
                    cliente.close();
                } catch (IOException ex) {
                    System.out.println("Error cerrando Socket HiloCliente");
                }
            }

        }

    }

    public void newPrivateMessage(String idUser, String mensaje, String timestamp) {
        Msg_Privado msg;

        //msg = new Msg_Privado(idUser, mensaje, timestamp);
        //database.addMsgPrivado(msg);
    }

    public void newGroupMessage(String idGrupo, String idUser, String mensaje, String timestamp) {
        Msg_Grupal msg;

        msg = new Msg_Grupal(idGrupo, idUser, mensaje, timestamp);
        database.addMsgGrupal(msg);
    }

}
