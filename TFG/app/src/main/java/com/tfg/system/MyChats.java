package com.tfg.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.tabs.TabLayout;
import com.tfg.activities.MySettings;
import com.tfg.adapters.*;
import com.tfg.R;
import com.tfg.database.db.ClientLab;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class MyChats extends AppCompatActivity {
    public final String HOST = "192.168.1.77";
    public final int PORT = 6000;

    Methods meth;
    MyAdapter adapter;
    ClientLab database;
    ListView lv;

    Socket client;
    String me;
    boolean fromPause = false;

    TabLayout tbl;
    ArrayList<String> conversaciones;
    ArrayList<String> mensajes;

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
        tbl = (TabLayout) findViewById(R.id.tipoConversacion);
        lv = (ListView) findViewById(R.id.listadoConversaciones);
        conversaciones = new ArrayList<>();
        mensajes = new ArrayList<>();
        tbl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals("Privados")) {
                    if (adapter != null) {
                        adapter.clear();
                    } else {
                        //Método que llama a la base de datos para sacar la info de los privados y guardarlo en el ArrayList
                        mostrarPrivados();
                        actualizarListado();
                    }
                } else if (tab.getText().toString().equals("Grupos")) {
                    if (adapter != null) {
                        adapter.clear();
                    } else {
                        //Método que llama a la base de datos para sacar la info de los grupos y guardarlo en el ArrayList
                        mostrarGrupos();
                        actualizarListado();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initialize() {
        HiloClient hc;

        try {
            client = new Socket(HOST, PORT);
            database = ClientLab.get(this);
            meth = new Methods(database, client, me);
            hc = new HiloClient(client, database, me);
            hc.start();

        } catch (IOException e) {
            Toast.makeText(this, "Error al conectar cliente con servidor", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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

    /*public void prueba() {
    }*/

    public void actualizarListado() {
        adapter = new MyAdapter(this, conversaciones, mensajes);
        lv.setAdapter(adapter);
    }

    public void mostrarPrivados() {

    }

    public void mostrarGrupos() {

    }
}