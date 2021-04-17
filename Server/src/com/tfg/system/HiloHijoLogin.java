/* Su función es escuchar los mensajes que le llegan del Cliente
y que después envía al HiloHijoServidor
 */
package com.tfg.system;

import com.tfg.datos.Mensaje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        boolean resul;

        try {
            do {
                ois = new ObjectInputStream(this.client.getInputStream());
                msj = (Mensaje) ois.readObject();
                tipo = msj.getTipo();

                if (tipo != -1) {
                    switch (tipo) {
                        case 0:
                            resul = this.objComp.addUser(msj.getEmisor(), msj.getMensaje(), this.client);
                            oos = new ObjectOutputStream(this.client.getOutputStream());

                            if (resul) {
                                msj = new Mensaje(null, null, null, 0, 0);
                                oos.writeObject(msj);
                            } else {
                                msj = new Mensaje(null, null, null, 0, 1);
                                oos.writeObject(msj);
                            }

                            break;

                        case 1:

                            break;

                        default:
                            // tipo de mensaje inexistente
                            break;
                    }
                }

            } while (tipo != -1);

        } catch (IOException ex) {
            System.out.println("Error I/O HHServer");
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {
            System.out.println("Clase Mensaje inexistente HHServer");

        }

    }

}
