/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tfg.system;

import com.tfg.datos.Mensaje;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author juanj
 */
public class ObjetoCompartido {

    private HashMap<String, Socket> activos;
    private HashMap<String, ArrayList<Mensaje>> noEnviado;

    public ObjetoCompartido() {
        this.activos = new HashMap<>();

    }

    public synchronized void addUser(String id, Socket sck) {
        this.activos.put(id, sck);
        //crear un metodo que compruebe a ver si tiene mensajes pendientes y si los tiene enviarselos todos
    }

    public static boolean checkuser(String id, byte[] psw) {
        if (BBDD.addUser(id, psw)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean sendPrivateMessage(Mensaje m) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(activos.get(m.getReceptor()).getOutputStream());
            oos.writeObject(m);
        } catch (Exception e) {
            if (this.noEnviado.containsKey(m.getReceptor())) {
                this.noEnviado.get(m.getReceptor()).add(m);
            } else {
                this.noEnviado.put(m.getReceptor(), new ArrayList<Mensaje>());
                this.noEnviado.get(m.getReceptor()).add(m);
            }
            return false;
        }
        return true;
    }

}
