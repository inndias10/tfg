package com.example.pruebasproyectofinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pruebasproyectofinal.CuadrodeDialogo;
import com.example.pruebasproyectofinal.MyAdapter;
import com.example.pruebasproyectofinal.R;

public class MyChats extends AppCompatActivity {

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
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
            Intent i =new Intent(this, MySettings.class);
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
}