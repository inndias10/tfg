package com.tfg.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.tfg.activities.MySettings;
import com.tfg.adapters.*;
import com.tfg.R;
import com.tfg.database.db.ClientLab;
import com.tfg.system.Methods;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MyChats extends AppCompatActivity {
    public final String HOST = "192.168.1.42";
    public final int PORT = 6000;

    Methods meth;
    MyAdapter adapter;
    ClientLab database;

    Socket client;
    String me;
    boolean fromPause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_chats);

        me = getAppData();

        if (me.isEmpty()) {
            // inicia el registro
            startLoginActivity();

        } else {
            initialize();
        }

    }

    private void initialize() {
        HiloClient hc;
        DataOutputStream dos = null;

        try {
            client = new Socket(HOST, PORT);
            database = ClientLab.get(this);
            meth = new Methods(database, client, me);
            hc = new HiloClient(client, meth);
            hc.start();

            dos = new DataOutputStream(this.client.getOutputStream());
            dos.writeUTF(me);

        } catch (IOException e) {
            Toast.makeText(this, "Error al conectar cliente con servidor", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                // error cerrando dos
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        fromPause = true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (fromPause) {
            Toast.makeText(this, "despues de iniciar registro", Toast.LENGTH_SHORT).show();

            me = getAppData();
            initialize();

        } else {
            Toast.makeText(this, "antes de iniciar registro", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new MyAdapter(this, null, null);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chats_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.configuracion) {
            Intent i = new Intent(this, MySettings.class);
            startActivity(i);
        } else if (id == R.id.crearConversacion) {
            DialogFragment dialogo = new CuadrodeDialogo();
            dialogo.show(getSupportFragmentManager(), "Nueva conversación");
        } else if (id == R.id.logout) {
            Toast.makeText(this, "f", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.eliminarChat) {
            Toast.makeText(this, "g", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void startLoginActivity() {
        Intent i = new Intent(this, com.tfg.activities.MyLogIn.class);
        startActivity(i);
    }


    // devuelve el nickname guardado en preferencia (empty si nadie se ha registrado)
    public String getAppData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return preferences.getString("nickname", "");

    }

    public void prueba() {
    }

}