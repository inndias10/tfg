package com.tfg.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.R;
import com.tfg.datos.Mensaje;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MyLogIn extends AppCompatActivity {
    public final String HOST = "192.168.1.77";
    public final int PORT = 6100;

    EditText username, password;
    Button btnLogin, btnExit;

    Socket sckLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etContrasenna);
        btnLogin = findViewById(R.id.btnAceptar);
        btnExit = findViewById(R.id.btnSalir);

        /*try {
            sckLogin = new Socket(HOST, PORT);

        } catch (IOException e) {
            // fallo conexion con server
            e.printStackTrace();
            Toast.makeText(this, "fallo en la conexion", Toast.LENGTH_SHORT).show();
        }*/

    }

    // onClick boton registro
    public void register(View view) {
        Mensaje msj;

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        String nick, psswd;

        nick = username.getText().toString();
        psswd = password.getText().toString();

        if (nick.isEmpty() || psswd.isEmpty()) {
            Toast.makeText(this, "Rellena los campos para registrarte", Toast.LENGTH_SHORT).show();

        } else {

            try {
                oos = new ObjectOutputStream(this.sckLogin.getOutputStream());
                ois = new ObjectInputStream(this.sckLogin.getInputStream());

                // envia los datos al server que devuelve en el error: 1 si el usuario ya existe y 0 si no
                msj = new Mensaje(nick, null, psswd.getBytes(), 0, 0);
                oos.writeObject(msj);
                msj = (Mensaje) ois.readObject();

                if (msj.getError() == 0) {
                    saveData(nick, new String(psswd));
                    Toast.makeText(this, "Te has registrado", Toast.LENGTH_SHORT).show();
                    this.finish();

                } else {
                    Toast.makeText(this, "Nombre de usuario en uso. Introduzca uno diferente.", Toast.LENGTH_LONG).show();
                }


            } catch (IOException e) {
                Toast.makeText(this, "Error I/O boton registrar", Toast.LENGTH_LONG).show();

            } catch (ClassNotFoundException e) {
                Toast.makeText(this, "Clase mensaje inexistente para Registro", Toast.LENGTH_LONG).show();

            }

        }

    }


    // método para registrar los datos del usuario
    private void saveData(String nick, String psswd) {
        SharedPreferences preferences;
        SharedPreferences.Editor editor;

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = preferences.edit();

        editor.putString("nickname", nick);
        editor.putString("password", psswd);

        editor.commit();

    }

    public void exit(View v) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.sckLogin.getOutputStream());
            Mensaje msj = new Mensaje(null, null, null, -1, 0);
            oos.writeObject(msj);

        } catch (IOException e) {
            // error en comunicacion con socket en exit
            e.printStackTrace();
        }

        this.finish();
        // cerrar toda la aplicacion
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(this.sckLogin.getOutputStream());
            Mensaje msj = new Mensaje(null, null, null, -1, 0);
            oos.writeObject(msj);

        } catch (IOException e) {
            // error en comunicacion con socket en exit
            e.printStackTrace();
        }

        this.finish();
        //cerrar toda la aplicacion
    }

}