/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trabajoFinal.chat;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author oscar
 */
public class Utils {

    public static void ObtenerHash(String tipo, File f) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(tipo);
            byte[] bytes = ficheroBytes(f);
            md.update(bytes);
            byte[] resumen = md.digest();
            System.out.println("Este es el hash en bytes: " + new String(resumen));
            System.out.println("Este es el hash en Hexadecimal: " + ByteHexadecimal(resumen));
        } catch (Exception e) {
            System.out.println("Se ha producido un error");
        }
    }
    public static byte[] ObtenerHash(String cadena){
        MessageDigest md;
        byte[] bytes = null;
        byte[] resumen = null;
        try {
            md = MessageDigest.getInstance("MD5");
            bytes = cadena.getBytes();
            md.update(bytes);
            resumen = md.digest();
        } catch (Exception e) {
            System.out.println("Se ha producido un error");
        }
        return resumen;
    }

    public static String ByteHexadecimal(byte[] resumen) {
        String hex = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1) {
                hex += "0";
            }
            hex += h;
        }
        return hex.toUpperCase();
    }

    public static byte[] ficheroBytes(File ficheroCifrar) {
        byte[] fichBytes = null;
        FileInputStream ficheroIn = null;
        try {
            ficheroIn = new FileInputStream(ficheroCifrar);
            long bytes = ficheroCifrar.length();
            fichBytes = new byte[(int) bytes];
            int i, j = 0;
            while ((i = ficheroIn.read()) != -1) {
                fichBytes[j] = (byte) i;
                j++;
            }

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado");
        } catch (IOException ex) {
            System.out.println("Error I/O");
        } finally {
            if (ficheroIn != null) {
                try {
                    ficheroIn.close();
                } catch (Exception e) {
                    System.out.println("Se ha producido un error al cerrar el FileInputStream");
                }
            }
        }
        return fichBytes;
    }

    public static void grabarFicheroCifrado(File destino, byte[] ficheroBytes) {
        DataOutputStream fichSalida = null;
        try {
            fichSalida = new DataOutputStream(new FileOutputStream(destino));
            fichSalida.write(ficheroBytes);
            fichSalida.flush();

        } catch (FileNotFoundException ex) {
            System.out.println("Fichero no encontrado");
        } catch (IOException ex) {
            System.out.println("Error I/O");

        } finally {
            try {
                fichSalida.close();
            } catch (IOException ex) {
                System.out.println("Error I/O");
            }
        }
    }

    public static void GenerarClaveSimetrica(File f) {
        ObjectOutputStream os = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);
            SecretKey clave = kg.generateKey();
            os = new ObjectOutputStream(new FileOutputStream(f));
            os.writeObject(clave);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("El algoritmo que has elegido no existe");
        } catch (IOException ex) {
            System.out.println("Se ha producido un error al escribir en el fichero");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    System.out.println("Se ha producido un error al cerrar el ObjectOutputStream");
                }
            }
        }
    }

    public static byte[] cifrarClaveSimetrica(File fichero, File clave) {
        byte[] textoCifrado = null, textoPlano;
        try {
            SecretKey claveSecreta = (SecretKey) recuperarClaveSimetrica(clave);
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, claveSecreta);
            textoPlano = ficheroBytes(fichero);
            textoCifrado = c.doFinal(textoPlano);
        } catch (InvalidKeyException ex) {
            System.out.println("La clave que has introducido no es valida");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("El algoritmo que has introducido no existe");
        } catch (NoSuchPaddingException ex) {
            System.out.println("El padding que has introducido no existe");
        } catch (IllegalBlockSizeException ex) {
            System.out.println("El tamaño del bloque no es correcto");
        } catch (BadPaddingException ex) {
            System.out.println("El padding que has introducido no es correcto");
        }
        return textoCifrado;
    }

    public static byte[] cifrarClaveSimetrica(byte[] bytes, File clave) {
        byte[] textoCifrado = null;
        try {
            SecretKey claveSecreta = (SecretKey) recuperarClaveSimetrica(clave);
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, claveSecreta);
            textoCifrado = c.doFinal(bytes);
        } catch (InvalidKeyException ex) {
            System.out.println("La clave que has introducido no es valida");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("El algoritmo que has introducido no existe");
        } catch (NoSuchPaddingException ex) {
            System.out.println("El padding que has introducido no existe");
        } catch (IllegalBlockSizeException ex) {
            System.out.println("El tamaño del bloque no es correcto");
        } catch (BadPaddingException ex) {
            System.out.println("El padding que has introducido no es correcto");
            ex.printStackTrace();
        }
        return textoCifrado;
    }

    public static byte[] descifrarClaveSimetrica(File fichero, File clave) {
        byte[] textoPlano, textoDescifrado = null;
        try {
            SecretKey claveSecreta = (SecretKey) recuperarClaveSimetrica(clave);
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, claveSecreta);
            textoPlano = ficheroBytes(fichero);
            textoDescifrado = c.doFinal(textoPlano);
        } catch (InvalidKeyException ex) {
            System.out.println("La clave que has introducido no es valida");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("El algoritmo que has introducido no existe");
        } catch (NoSuchPaddingException ex) {
            System.out.println("El padding que has introducido no existe");
        } catch (IllegalBlockSizeException ex) {
            System.out.println("El tamaño del bloque no es correcto");
        } catch (BadPaddingException ex) {
            System.out.println("El padding que has introducido no es correcto");
        }
        return textoDescifrado;
    }

    public static byte[] descifrarClaveSimetrica(byte[] bytesCifrados, File clave) {
        byte[] textoDescifrado = null;
        try {
            SecretKey claveSecreta = (SecretKey) recuperarClaveSimetrica(clave);
            Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, claveSecreta);
            textoDescifrado = c.doFinal(bytesCifrados);
        } catch (InvalidKeyException ex) {
            System.out.println("La clave que has introducido no es valida");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("El algoritmo que has introducido no existe");
        } catch (NoSuchPaddingException ex) {
            System.out.println("El padding que has introducido no existe");
        } catch (IllegalBlockSizeException ex) {
            System.out.println("El tamaño del bloque no es correcto");
        } catch (BadPaddingException ex) {
            System.out.println("El padding que has introducido no es correcto");
            ex.printStackTrace();
        }
        return textoDescifrado;
    }

    public static Object recuperarClaveSimetrica(File clave) {
        ObjectInputStream ois = null;
        SecretKey claveSecreta = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(clave));
            claveSecreta = (SecretKey) ois.readObject();
        } catch (Exception e) {
            System.out.println("Se ha producido un error al recuperar la clave");
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                    System.out.println("Se ha producido un error");
                }
            }
        }
        return claveSecreta;
    }

    public static void generarClaveAsimetrico(File privada, File publica) {
        try {
            KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
            kg.initialize(1024);
            KeyPair par = kg.generateKeyPair();
            PrivateKey clavePrivada = par.getPrivate();
            PublicKey clavePublica = par.getPublic();
            guardarClaveAsimetrica(clavePrivada, privada);
            guardarClaveAsimetrica(clavePublica, publica);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("El algoritmo que has elegido no existe");
        }
    }

    private static void guardarClaveAsimetrica(Object clave, File f) {
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(new FileOutputStream(f));
            os.writeObject(clave);
        } catch (IOException ex) {
            System.out.println("Se ha producido un error al escribir en el fichero");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception e) {
                    System.out.println("Se ha producido un error al cerrar el ObjectOutputStream");
                }
            }
        }
    }

    public static Object recuperarClaveAsimetricaPrivada(File clave) {
        ObjectInputStream ois = null;
        PrivateKey claveSecreta = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(clave));
            claveSecreta = (PrivateKey) ois.readObject();
        } catch (Exception e) {
            System.out.println("Se ha producido un error al recuperar la clave");
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                    System.out.println("Se ha producido un error");
                }
            }
        }
        return claveSecreta;
    }

    public static Object recuperarClaveAsimetricaPublica(File clave) {
        ObjectInputStream ois = null;
        PublicKey claveSecreta = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(clave));
            claveSecreta = (PublicKey) ois.readObject();
        } catch (Exception e) {
            System.out.println("Se ha producido un error al recuperar la clave");
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                    System.out.println("Se ha producido un error");
                }
            }
        }
        return claveSecreta;
    }

    public static byte[] cifrarAsimetrico(byte[] bytes, File clave) {
        byte[] bytesCifrados = null;
        try{
        PrivateKey clavePrivada = (PrivateKey) recuperarClaveAsimetricaPrivada(clave);
        Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c1.init(Cipher.ENCRYPT_MODE, clavePrivada);
        bytesCifrados = c1.doFinal(bytes);
        }catch(Exception e){
            System.out.println("Se ha producido un error");
        }
        return bytesCifrados;
    }
    public static byte[] descifrarAsimetrico(byte[] bytes, File clave){
        byte[] bytesDescifrados = null;
        try{
        PublicKey clavePublica = (PublicKey) recuperarClaveAsimetricaPublica(clave);
        Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c1.init(Cipher.DECRYPT_MODE, clavePublica);
        bytesDescifrados = c1.doFinal(bytes);
        }catch(Exception e){
            System.out.println("Se ha producido un error");
        }
        return bytesDescifrados;
    }
}
