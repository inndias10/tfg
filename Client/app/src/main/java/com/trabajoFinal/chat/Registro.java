package com.trabajoFinal.chat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.datos.Mensaje;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Registro extends AppCompatActivity {
    public final String HOST = "192.168.1.42";
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
                // newUser(nombre, password);
                Toast.makeText(this, "Te has dado de alta con éxito", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Nombre de usuario en uso. Introduzca uno diferente.", Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Error I/O boton registrar", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // envío los datos al activity main para almacenarlos en tabla USER
    private void newUser(String nombre, byte[] password) {
        Intent actMain;

        actMain = new Intent();
        actMain.putExtra("name", nombre);
        actMain.putExtra("psswd", password);
        setResult(RESULT_OK, actMain);
        this.finish();
    }

}
