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
    private final String URL = "jdbc:mysql://localhost:3306/dbserver?serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "root";

    public ObjetoCompartido() {
        this.activos = new HashMap<>();
    }

    public synchronized boolean addUser(String id, byte[] psw, Socket sck) {
        Connection conex = null;
        Statement sentencia;
        ResultSet rs;
        String pswd;

        try {
            conex = DriverManager.getConnection(URL, USER, PASS);
            sentencia = conex.createStatement();
            rs = sentencia.executeQuery("Select * from usuarios where id = '" + id + "'");

            // si el usuario no existe lo crea
            if (rs.next()) {
                return false;

            } else {
                pswd = new String(psw);
                sentencia.executeUpdate("Insert into usuarios values ('" + id + "', '" + pswd + "')");
                this.activos.put(id, sck);
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Error SQL al añadir usuario");
            return false;

        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException ex) {
                    System.out.println("Error cerrando conexión");
                }
            }

        }

    }

    public void sendPrivateMessage(String id) {

    }

}
