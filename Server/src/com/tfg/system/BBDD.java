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

    public static int addGroup(String nombre, String id_usuario, String descripcion) {
        Connection conex = null;
        Statement sentencia;
        ResultSet rs;
        String sql;
        int id = 0;
        try {
            conex = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS);
            sentencia = conex.createStatement();
            sql = "select id from grupos order by id desc limit 1;";
            rs = sentencia.executeQuery(sql);

            // si el usuario no existe lo crea
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                id = 1;
            }
            sql = "insert into grupos(id,nombre,descripcion) values(" + id + ",'" + nombre + "','" + descripcion + "');";
            sentencia.executeUpdate(sql);
            sql = "insert into usuarios_has_grupos(grupos_id,usuarios_id,administrador) values(" + id + ",'" + id_usuario + "','si');";
            sentencia.executeUpdate(sql);
            return id;
        } catch (SQLException ex) {
            System.out.println("Error SQL al añadir el grupo");

        } finally {
            if (conex != null) {
                try {
                    conex.close();
                } catch (SQLException ex) {
                    System.out.println("Error cerrando conexión");
                }
            }

        }
        return id;
    }

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

    public static boolean checkUser(String id) {
        Connection conex = null;
        Statement sentencia;
        ResultSet rs;

        try {
            conex = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS);
            sentencia = conex.createStatement();
            rs = sentencia.executeQuery("Select * from usuarios where id = '" + id + "'");

            if (rs.next()) {
                return true;

            } else {
                return false;
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

    public static boolean remove_user_group(String user, String group) {
        Connection conex = null;
        Statement sentencia;
        ResultSet rs;
        String sql;
        int id = 0;
        try {
            conex = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS);
            sentencia = conex.createStatement();
            sql = "delete from usuarios_has_grupos where usuarios_id = '" + user + "' and grupos_id = " + Integer.parseInt(group) + ";";
            sentencia.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            System.out.println("Error SQL al añadir el grupo");
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
    
    public static boolean add_user_group(String user, String group) {
        Connection conex = null;
        Statement sentencia;
        ResultSet rs;
        String sql;
        int id = 0;
        try {
            conex = DriverManager.getConnection(Config.URL, Config.USER, Config.PASS);
            sentencia = conex.createStatement();
            sql = "insert into usuarios_has_grupos(usuarios_id, grupos_id, administrador) values('"+user+"', "+Integer.parseInt(group)+",'no');";
            sentencia.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            System.out.println("Error SQL al añadir el grupo");
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
