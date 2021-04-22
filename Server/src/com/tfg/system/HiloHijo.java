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
public class HiloHijo extends Thread{
    private final ObjetoCompartido objComp;
    private final Socket client;

    public HiloHijo(Socket cl, ObjetoCompartido obj) {
        this.client = cl;
        this.objComp = obj;
    }
    public void run(){
        ObjectInputStream entrada = null;
        Mensaje m;
        int tipo = -2;
        boolean error;
        try{
            entrada = new ObjectInputStream(client.getInputStream());
            m = (Mensaje) entrada.readObject();
            tipo = m.getTipo();
            do{
                m = (Mensaje) entrada.readObject();
                tipo = m.getTipo();
                if (tipo == 1) {
                    error = objComp.sendPrivateMessage(m);
                    if (!error) {
                        objComp.sendPrivateMessage(new Mensaje(null,m.getEmisor(),null,1,2));
                    }
                }
            }while(tipo != -1);
        }catch(Exception e){
            System.out.println("Se ha producido un error");
        }finally{
            if (entrada != null) {
                try{
                    entrada.close();
                }catch(Exception e){
                    System.out.println("se ha producido un error al cerrar el inputstream");
                }
            }
        }
    }
}
