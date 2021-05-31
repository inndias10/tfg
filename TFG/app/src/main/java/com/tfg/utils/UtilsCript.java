package com.tfg.utils;

import java.io.File;
import java.security.*;

import javax.crypto.*;

import static com.tfg.utils.UtilsFiles.*;

public class UtilsCript {
    public static String hexadecimal(byte[] resumen) {
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

    public static byte[] cifrarHash(byte[] data, String algoritmo) {
        byte[] resumen = null;
        MessageDigest md;

        try {
            md = MessageDigest.getInstance(algoritmo);
            md.update(data);
            resumen = md.digest();

        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Algoritmo " + algoritmo + " inexistente");
        }

        return resumen;
    }

    public static void criptografiaSimetrica(boolean cifrar, SecretKey key, File fileTratar, String newFile) {
        byte[] descifrado;
        byte[] contenido;
        Cipher c;

        try {
            c = Cipher.getInstance("AES/ECB/PKCS5Padding");

            if (cifrar) {
                c.init(Cipher.ENCRYPT_MODE, key);
            } else {
                c.init(Cipher.DECRYPT_MODE, key);
            }

            contenido = ficheroBytes(fileTratar);
            descifrado = c.doFinal(contenido);
            grabarFicheroCifrado(newFile, descifrado);

        } catch (NoSuchPaddingException ex) {
            System.out.println("Error padding");
        } catch (IllegalBlockSizeException ex) {
            System.out.println("Tamaño de bloques no válido");
        } catch (BadPaddingException ex) {
            System.out.println("Error mal padding");
        } catch (InvalidKeyException ex) {
            System.out.println("Clave no válida");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Algoritmo inexistente");
        }

    }

    public static void cifradoAsmietrico(PrivateKey privKey, File fileTratar, String newFile) {
        Cipher c;
        byte[] descifrado;
        byte[] cifrado;

        try {
            c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.ENCRYPT_MODE, privKey);
            descifrado = ficheroBytes(fileTratar);
            cifrado = c.doFinal(descifrado);
            grabarFicheroCifrado(newFile, cifrado);

        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Algoritmo inexistente");
        } catch (NoSuchPaddingException ex) {
            System.out.println("Error padding");
        } catch (InvalidKeyException ex) {
            System.out.println("Clave no válida");
        } catch (IllegalBlockSizeException ex) {
            System.out.println("Tamaño de bloque no válido");
        } catch (BadPaddingException ex) {
            System.out.println("Mal padding");
        }

    }

    public static void descifrdoAsimetrico(PublicKey pubKey, File fileTratar, String newFile) {

        Cipher c;
        byte[] descifrado;
        byte[] cifrado;

        try {
            c = Cipher.getInstance("RSA/ECB/PCKS1Padding");
            c.init(Cipher.DECRYPT_MODE, pubKey);
            cifrado = ficheroBytes(fileTratar);
            descifrado = c.doFinal(cifrado);
            grabarFicheroCifrado(newFile, descifrado);

        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Algoritmo inexistente");
        } catch (NoSuchPaddingException ex) {
            System.out.println("Error padding");
        } catch (InvalidKeyException ex) {
            System.out.println("Clave no válida");
        } catch (IllegalBlockSizeException ex) {
            System.out.println("Tamaño de bloque no válido");
        } catch (BadPaddingException ex) {
            System.out.println("Mal padding");
        }

    }
}
