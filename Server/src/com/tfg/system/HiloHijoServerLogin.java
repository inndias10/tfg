/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tfg.system;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author oscar
 */
public class HiloHijoServerLogin extends Thread{

    private ObjetoCompartido objComp;

    public HiloHijoServerLogin(ObjetoCompartido objComp) {
        this.objComp = objComp;
    }

    public void run() {
        HiloHijoLogin hijoServ;
        ServerSocket sockServ = null;
        Socket sockClient = null;

        try {
            sockServ = new ServerSocket(6000); //6100
            System.out.println("----- SERVER ARRANCADO -----");

            // bucle infinito recibiendo usuarios
            do {
                // escucho hasta que se conecta un usuario y lo creo
                sockClient = sockServ.accept();

                hijoServ = new HiloHijoLogin(sockClient, objComp);
                hijoServ.start();

            } while (true);

        } catch (IOException ex) {
            System.out.println("Error en IO servidor.");

        } finally {
            if (sockServ != null) {
                try {
                    sockServ.close();
                } catch (IOException ex) {
                    System.out.println("Error al cerrar el Socket server");
                }
            }

            if (sockClient != null) {
                try {
                    sockClient.close();
                } catch (IOException ex) {
                    System.out.println("Error al cerrar el Socket client");
                }
            }
        }
    }

}
