/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tfg.system;


/**
 *
 * @author juanj
 */
public class MainServer {

    public static void main(String[] args) {
        ObjetoCompartido objComp = new ObjetoCompartido();
        HiloHijoServerLogin hhsl = new HiloHijoServerLogin(objComp);
        HiloHijoServer hhs = new HiloHijoServer(objComp);
        hhsl.start();
        hhs.start();
        System.out.println("----- SERVER ARRANCADO -----");

    }

}
