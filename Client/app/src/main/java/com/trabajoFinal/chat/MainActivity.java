package com.trabajoFinal.chat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public final String HOST = "192.168.1.42";
    public final String ADDRESS = "localhost";
    public final int PUERTO = 6000;

    SQLiteDatabase database;
    Socket cliente;
    CreateDB crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        crear = new CreateDB(this);

        if (!comprobarRegistro()) {
            iniciarRegistro();
        }

        Toast.makeText(this, "Ya he activado el registro", Toast.LENGTH_LONG).show();

        /* try {
            cliente = new Socket(HOST, PUERTO);

        } catch (IOException e) {
            Toast.makeText(this, "Error I/O main", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } */

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.onResume();

        String nombre, password;

        database = crear.getWritableDatabase();

        if ((resultCode == RESULT_CANCELED) || (data.getStringExtra("name").isEmpty())) {
            nombre = data.getStringExtra("name");
            password = new String(data.getByteArrayExtra("psswd"));
            database.execSQL("Insert into user (id, password) values ('" + nombre + "', '" + password + "')");

        } else {
            Toast.makeText(this, "Problemas al recibir registros", Toast.LENGTH_LONG).show();
        }

        database.close();

    }

    // comprueba si el usuario ya se ha registrado en el dispositivo
    private boolean comprobarRegistro() {
        Cursor datos;

        database = crear.getReadableDatabase();
        datos = database.rawQuery("Select * from user", null);

        if (datos.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    // Inicia la pantalla de registro
    private void iniciarRegistro() {
        Intent i = new Intent(this, Registro.class);
        //startActivity(i);
        startActivityForResult(i, 1);
    }

}