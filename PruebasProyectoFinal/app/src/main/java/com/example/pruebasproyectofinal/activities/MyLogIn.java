package com.example.pruebasproyectofinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pruebasproyectofinal.R;

public class MyLogIn extends AppCompatActivity {

    EditText username, password;
    Button btnLogin, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etContrasenna);
        btnLogin = findViewById(R.id.btnAceptar);
        btnExit = findViewById(R.id.btnSalir);
    }

    public void startChatsActivity(View v) {
        Intent i = new Intent(this, MyChats.class);
        startActivity(i);
        this.finish();
    }

    public void exit(View v) {
        this.finish();
    }
}