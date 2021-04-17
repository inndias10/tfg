/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tfg.system;

import java.util.HashMap;
import java.net.Socket;
import java.sql.*;

/**
 *
 * @author juanj
 */
public class ObjetoCompartido {

    private HashMap<String, Socket> activos;
    

    public ObjetoCompartido() {
        this.activos = new HashMap<>();
    }

    public synchronized boolean addUser(String id, byte[] psw, Socket sck){
        if(BBDD.addUser(id, psw, sck)){
            this.activos.put(id, sck);
            return true;
        }else{
            return false;
        }
    }
    public void sendPrivateMessage(String id) {

    }

}
