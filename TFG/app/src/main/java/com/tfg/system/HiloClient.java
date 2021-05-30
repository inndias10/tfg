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
    private final Methods meth;

    public HiloClient(Socket cl, Methods mt) {
        this.cliente = cl;
        this.meth = mt;
    }

    @Override
    public void run() {
        Mensaje msj;
        ObjectInputStream ois = null;
        int msgType = 0;

        try {

            // RECIBE MENSAJES DEL HH SERVIDOR
            do {
                ois = new ObjectInputStream(cliente.getInputStream());
                msj = (Mensaje) ois.readObject();
                msgType = msj.getTipo();

                switch (msgType) {
                    // mensaje de texto privado
                    case 1:
                        meth.showTextMessage(1, msj.getEmisor(), msj.getEmisor(), new String(msj.getMensaje()), msj.getTimestamp());
                        break;


                    // mensaje fichero privado
                    case 6:
                        meth.showOtherMessage(1, msj.getEmisor(), msj.getEmisor(), msj.getAux(), msj.getTimestamp());
                        break;


                    // consulta de usuario para creacion conversacion privada
                    case 7:
                        meth.createPrivateConversation(msj.getError(), msj.getEmisor());
                        break;

                    case 14:
                        meth.createPrivate(msj.getEmisor(), msj.getEmisor(), new String(msj.getMensaje()), msj.getAux(), msj.getTimestamp());
                        break;


                    // creacion (2) o adicion (11) a grupo
                    case 2:
                    case 11:
                        meth.createGroup(msj.getEmisor(), msj.getReceptor(), msj.getTimestamp(), new String(msj.getMensaje()), msj.getAux());
                        break;


                    // mensaje texto grupal
                    case 3:
                        meth.showTextMessage(2, msj.getReceptor(), msj.getEmisor(), new String(msj.getMensaje()), msj.getTimestamp());
                        break;


                    // mensaje fichero grupal
                    case 8:
                        meth.showOtherMessage(2, msj.getReceptor(), msj.getEmisor(), msj.getAux(), msj.getTimestamp());
                        break;

                    // expulsion de usuario
                    case 10:
                        meth.exitGroup(msj.getReceptor(), msj.getEmisor(), new String(msj.getMensaje()));
                        break;

                    // nuevo administrador
                    case 13:
                        meth.newAdmin(msj.getReceptor(), msj.getEmisor(), new String(msj.getMensaje()));
                        break;


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
