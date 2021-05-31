/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tfg.system;

import com.tfg.datos.Mensaje;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author oscar
 */
public class HiloHijo extends Thread {

    private final ObjetoCompartido objComp;
    private final Socket client;

    public HiloHijo(Socket cl, ObjetoCompartido obj) {
        this.client = cl;
        this.objComp = obj;
    }

    public void run() {
        ObjectInputStream entrada = null;
        Mensaje m;
        int tipo;
        boolean error;
        try {
            entrada = new ObjectInputStream(client.getInputStream());
            m = (Mensaje) entrada.readObject();
            tipo = m.getTipo();
            do {
                m = (Mensaje) entrada.readObject();
                tipo = m.getTipo();
                if (tipo == 1 || tipo == 6 || tipo == 14) {// mensaje de texto privado o mensaje de fichero privado
                    error = objComp.sendPrivateMessage(m);
                    if (!error) {
                        objComp.sendPrivateMessage(new Mensaje(null, m.getEmisor(), null, -2, 2));
                    }
                } else if (tipo == 2) {// crear un grupo
                    error = objComp.addGroup(m.getReceptor(), m.getEmisor(), new String(m.getMensaje()),m.getAux(), m.getTimestamp());
                    if (!error) {
                        objComp.sendPrivateMessage(new Mensaje(null, m.getEmisor(), null, -2, 3));
                    }
                } else if (tipo == 3) { // mensaje de texto grupal
                    objComp.sendGroupMessage(m);
                } else if (tipo == 4) {// eliminar un usuario de un grupo
                    if (objComp.removeGroupUser(m.getReceptor(), m.getAux())) {//primero le borro de la base de datos y le borro del hashmap del servidor y luego envio el mensaje a todos los usuarios del grupo
                        objComp.sendPrivateMessage(m);
                        objComp.sendGroupMessage(new Mensaje(null, m.getReceptor(), (m.getEmisor() + " Ha eliminado a: " + m.getAux()).getBytes(), 10, 0));

                    } else {//si ha ocurrido un problema al borrar de la base de datos o al eliminar del hashmap devuelvo el mensaje de error a la persona que ha intentado eliminar
                        objComp.sendPrivateMessage(new Mensaje(null, m.getEmisor(), null, -2, 5));
                    }
                } else if (tipo == 7) {//comprobar un usuario para crear una conversacion privada
                    objComp.checkUser(m.getReceptor(), m.getEmisor());
                } else if (tipo == 5) {
                    if (objComp.checkUser(m.getAux())) {//primero compruebo a ver si el usuario existe
                        if (objComp.addGroupUser(m.getAux(), m.getReceptor())) {//primero le añado a la base de datos y al hashmap del servidor y luego le envio un mensaje a todos
                            objComp.sendPrivateMessage(new Mensaje(m.getAux(), m.getReceptor(), (m.getEmisor() + " Te ha añadido al grupo: " + m.getReceptor()).getBytes(), 11, 0));
                            objComp.sendGroupMessage(new Mensaje(null, m.getReceptor(), (m.getEmisor() + " Ha añadido a: " + m.getAux()).getBytes(), 11, 0));
                        } else {//si ha habido algun problema mientras le añadia ñe envio un mensaje de que se ha producifo un problema al intentar añadir
                            objComp.sendPrivateMessage(new Mensaje(null, m.getEmisor(), null, -2, 7));
                        }
                    } else {//si no existe envio un mensaje con el error 6 que es que el usuario al que quiere hablar no existe
                        objComp.sendPrivateMessage(new Mensaje(null, m.getEmisor(), null, -2, 6));
                    }
                } else if (tipo == 6) {//como es un mensaje en el que envian un fichero el servidor solo lo reenvia no hace nada
                    objComp.sendPrivateMessage(m);

                } else if (tipo == 8) {// como es un mensaje en el que envian un fichero el servidor no hace nada solo lo reenvia al grupo
                    objComp.sendGroupMessage(m);

                } else if (tipo == 9) {
                    if (objComp.removeGroupUser(m.getReceptor(), m.getEmisor())) {//primero intento borrar de la base de datos y del hashmap y luego envio los mensajes de que se ha salido
                        objComp.sendPrivateMessage(new Mensaje(null, m.getReceptor(), ("Te has salido del grupo: " + m.getReceptor()).getBytes(), 10, 0));
                        objComp.sendGroupMessage(new Mensaje(null, m.getReceptor(), (m.getEmisor() + " se ha salido del grupo").getBytes(), 10, 0));
                    } else {
                        objComp.sendPrivateMessage(new Mensaje(null, m.getEmisor(), null, -2, 5));
                    }
                } else if (tipo == 12) {
                    if (objComp.addAdmin(m.getAux(), m.getReceptor())) {
                        objComp.sendGroupMessage(new Mensaje(null, m.getReceptor(), (m.getAux() + " es ahora administrador").getBytes(), 13, 0));
                    }
                }
            } while (tipo != -1);
        } catch (Exception e) {
            System.out.println("Se ha producido un error");
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (Exception e) {
                    System.out.println("se ha producido un error al cerrar el inputstream");
                }
            }
        }
    }
}
