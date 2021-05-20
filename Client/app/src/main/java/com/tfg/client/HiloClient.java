package com.tfg.client;

import com.tfg.database.ClientLab;
import com.tfg.database.tables.Msg_Grupal;
import com.tfg.database.tables.Msg_Privado;
import com.tfg.database.tables.Usuarios;
import com.tfg.datos.Mensaje;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.tfg.utils.UtilsFiles;

public class HiloClient extends Thread {

    private final Socket cliente;
    private final ClientLab database;

    public HiloClient(Socket c, ClientLab db) {
        this.cliente = c;
        this.database = db;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        Mensaje msj;
        String emisor, mensaje, fileName, grupo, descripcion, time;
        int msgType = 0;
        int error;
        Usuarios usuario;

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
                        grupo = msj.getReceptor();
                        descripcion = new String(msj.getMensaje());
                        time = msj.getTimestamp();
                        // administrador
                        // comprobar si el administrador = usuario del dispositivo

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

        msg = new Msg_Privado(idUser, mensaje, timestamp);
        database.addMsgPrivado(msg);
    }

    public void newGroupMessage(String idGrupo, String idUser, String mensaje, String timestamp) {
        Msg_Grupal msg;

        msg = new Msg_Grupal(idGrupo, idUser, mensaje, timestamp);
        database.addMsgGrupal(msg);
    }

}
