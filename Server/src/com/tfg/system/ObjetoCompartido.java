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
    private HashMap<Integer, ArrayList<String>> grupos;

    public ObjetoCompartido() {
        this.activos = new HashMap<>();
    }

    public boolean addGroup(String nombre_grupo, String id_usuario, String descripcion) {
        int id = BBDD.addGroup(nombre_grupo, id_usuario, descripcion);
        if (id != 0) {
            grupos.put(id, new ArrayList<>());
            grupos.get(id).add(id_usuario);
            return true;
        } else {
            return false;
        }

    }

    public boolean removeGroupUser(String grupo, String usuario) {
        try {
            this.grupos.get(grupo).remove(usuario);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public synchronized void addUser(String id, Socket sck) {
        this.activos.put(id, sck);
        if (noEnviado.containsKey(id)) {
            sendPendingMessages(id);
        }
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
            if (activos.containsKey(m.getReceptor())) {
                oos = new ObjectOutputStream(activos.get(m.getReceptor()).getOutputStream());
                oos.writeObject(m);
                return true;
            } else {
                if (this.noEnviado.containsKey(m.getReceptor())) {
                    this.noEnviado.get(m.getReceptor()).add(m);
                } else {
                    this.noEnviado.put(m.getReceptor(), new ArrayList<>());
                    this.noEnviado.get(m.getReceptor()).add(m);
                }
                return false;
            }

        } catch (Exception e) {
            if (this.noEnviado.containsKey(m.getReceptor())) {
                this.noEnviado.get(m.getReceptor()).add(m);
            } else {
                this.noEnviado.put(m.getReceptor(), new ArrayList<>());
                this.noEnviado.get(m.getReceptor()).add(m);
            }
            return false;
        }
    }

    public void sendGroupMessage(Mensaje m) {
        ObjectOutputStream oos = null;
        ArrayList<String> grupo = grupos.get(m.getReceptor());
        for (String s : grupo) {
            try {
                if (activos.containsKey(s)) {
                    oos = new ObjectOutputStream(activos.get(s).getOutputStream());
                    oos.writeObject(m);
                } else {
                    if (this.noEnviado.containsKey(s)) {
                        this.noEnviado.get(s).add(m);
                    } else {
                        this.noEnviado.put(s, new ArrayList<>());
                        this.noEnviado.get(s).add(m);
                    }
                }
            } catch (Exception e) {
                System.out.println("Se ha producido un error");
                if (this.noEnviado.containsKey(s)) {
                    this.noEnviado.get(s).add(m);
                } else {
                    this.noEnviado.put(s, new ArrayList<>());
                    this.noEnviado.get(s).add(m);
                }
            }
        }
    }

    public void sendPendingMessages(String id) {
        ArrayList<Mensaje> pendientes = this.noEnviado.get(id);
        for (int i = 0; i < pendientes.size(); i++) {
            sendPrivateMessage(pendientes.get(i));
        }
    }
    
    public void checkUser(String id, String id_user){
        if (BBDD.checkUser(id)) {
            sendPrivateMessage(new Mensaje(id, id_user, null, 7, 0));
        }else{
            sendPrivateMessage(new Mensaje(id, id_user, null, 7, 6));
        }
    }
//prueba de cambios
}
