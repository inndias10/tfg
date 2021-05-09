/*
La clase Registro es la encargada de mantener la comunicación con el servidor para registrar un usuario la primera vez que inicia la aplicación en su móvil.
Muestra la activity Login para que el usuario se registre.
Se comunica con el hilo hijo Login del servidor.
*/

package com.tfg.client;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.database.ClientLab;
import com.tfg.database.tables.User;
import com.tfg.datos.Mensaje;
import com.trabajoFinal.chat.R;
import com.tfg.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Registro extends AppCompatActivity {
    public final String HOST = "192.168.1.47";
    public final int PUERTO = 6100;

    EditText etNombre;
    EditText etPassword;

    Socket clRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.registro);

        etNombre = findViewById(R.id.etNombreUsuario);
        etPassword = findViewById(R.id.etPassword);

        try {
            // conexión con hilo hijo Login del servidor
            clRegistro = new Socket(HOST, PUERTO);

        } catch (IOException e) {
            Toast.makeText(this, "Error I/O registro", Toast.LENGTH_LONG).show();
        }

    }

    // onClick del boton
    public void registrarse(View view) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Mensaje msj;
        String nombre;
        byte[] password;

        Toast.makeText(this, clRegistro.toString(), Toast.LENGTH_SHORT).show();
        try {
            oos = new ObjectOutputStream(this.clRegistro.getOutputStream());
            ois = new ObjectInputStream(this.clRegistro.getInputStream());

            nombre = etNombre.getText().toString();
            password = Utils.ObtenerHash(etPassword.getText().toString());

            msj = new Mensaje(nombre, null, password, 0, 0);
            oos.writeObject(msj);

            // envia los datoss al servidor que devuelve en el tipo 1 si el usuario ya existe y 0 si no
            msj = (Mensaje) ois.readObject();

            if (msj.getTipo() == 0) {
                newUser(nombre, password);
                Toast.makeText(this, "Te has dado de alta con éxito", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Nombre de usuario en uso. Introduzca uno diferente.", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Error I/O boton registrar", Toast.LENGTH_LONG).show();

        } catch (ClassNotFoundException e) {
            Toast.makeText(this, "Clase mensaje inexistente en Registro", Toast.LENGTH_LONG).show();

        }

    }

    private void newUser (String nombre, byte[] password) {
        ClientLab database;
        User user;

        database = ClientLab.get(this);
        user = new User(nombre, new String(password));
        database.addUser(user);

    }

}
