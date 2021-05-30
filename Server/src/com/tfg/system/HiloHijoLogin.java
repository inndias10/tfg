/* Su función es escuchar los mensajes que le llegan del Cliente
y que después envía al HiloHijoServidor
 */
package com.tfg.system;

import com.tfg.datos.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloHijoLogin extends Thread {

    private final ObjetoCompartido objComp;
    private final Socket client;

    public HiloHijoLogin(Socket cl, ObjetoCompartido obj) {
        this.client = cl;
        this.objComp = obj;
    }

    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        Mensaje msj;
        int tipo;
        boolean resul = true;
        boolean salir = false;

        System.out.println("Socket LOGIN conectado: " + this.client);

        try {

            ois = new ObjectInputStream(this.client.getInputStream());
            oos = new ObjectOutputStream(this.client.getOutputStream());

            do {

                msj = (Mensaje) ois.readObject();
                System.out.println("Mensaje recibido");
                tipo = msj.getTipo();

                if (tipo != -1) {

                    switch (tipo) {
                        case 0:
                            resul = this.objComp.checkuser(msj.getEmisor(), msj.getMensaje());

                            if (resul) {
                                msj = new Mensaje(null, null, null, 0, 0);
                                oos.writeObject(msj);
                                BBDD.addUser(msj.getEmisor(), msj.getMensaje());
                                salir = false;
                            } else {
                                msj = new Mensaje(null, null, null, 0, 1);
                                oos.writeObject(msj);
                                salir = true;
                            }

                            System.out.println("Mensaje enviado");

                            break;

                        default:
                            // tipo de mensaje inexistente
                            break;
                    }
                }

            } while (salir);

        } catch (IOException ex) {
            System.out.println("Error I/O HHServer");
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
            System.out.println("Clase Mensaje inexistente HHServer");

        }

    }

}
