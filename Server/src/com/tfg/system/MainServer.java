/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tfg.system;

import java.io.*;
import java.net.*;

/**
 *
 * @author juanj
 */
public class MainServer {

    public static void main(String[] args) {
        ObjetoCompartido objComp = new ObjetoCompartido();
        HiloHijoServerLogin hhsl = new HiloHijoServerLogin(objComp);
        hhsl.start();

    }
}
