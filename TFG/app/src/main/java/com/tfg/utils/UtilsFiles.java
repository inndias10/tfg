package com.tfg.utils;

import java.io.*;

public class UtilsFiles {
    public static byte[] ficheroBytes(File ficheroCifrar) {
        byte[] fichBytes = null;
        try {
            FileInputStream ficheroIn;
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
        }

        return fichBytes;
    }

    public static void grabarFicheroCifrado(String fileName, byte[] ficheroBytes) {
        File ficheroCifrado;
        BufferedOutputStream fichSalida = null;

        try {
            ficheroCifrado = new File(fileName);
            fichSalida = new BufferedOutputStream(new FileOutputStream(ficheroCifrado));
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
}
