package com.tfg.client;

import com.tfg.database.ClientLab;
import com.tfg.database.tables.Msg_Privado;
import com.tfg.database.tables.Usuarios;
import com.tfg.datos.Mensaje;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class HiloClient extends Thread {

    private Socket cliente;
    private ClientLab database;

    public HiloClient(Socket c, ClientLab db) {
        this.cliente = c;
        this.database = db;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        Mensaje msj;
        String emisor, mensaje;
        int msgType = 0;
        Usuarios usuario;
        Msg_Privado msjPrivado;

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

                        msjPrivado = new Msg_Privado(emisor, mensaje, "timestamp");
                        database.addMsgPrivado(msjPrivado);
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

}
