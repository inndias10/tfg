/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tfg.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author oscar
 */
public class BBDD {
    public static boolean addUser(String id, byte[] psw) {
        Connection conex = null;
        Statement sentencia;
        ResultSet rs;
        String pswd;

        try {
            conex = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS);
            sentencia = conex.createStatement();
            rs = sentencia.executeQuery("Select * from usuarios where id = '" + id + "'");

            // si el usuario no existe lo crea
            if (rs.next()) {
                return false;

            } else {
                pswd = new String(psw);
                sentencia.executeUpdate("Insert into usuarios values ('" + id + "', '" + pswd + "')");
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

}
