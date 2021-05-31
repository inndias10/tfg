package com.tfg.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.R;
import com.tfg.system.Methods;
import com.tfg.system.MyChats;


public class MyCreateConversation extends AppCompatActivity {
    EditText username, aux;
    Button confirmar;

    Methods methods = MyChats.getInstance().meth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_convesation);

        username = (EditText) findViewById(R.id.etNick);
        confirmar = (Button) findViewById(R.id.btnConfirmar);

        aux = (EditText) findViewById(R.id.etAux);

        aux.setText(methods.info);

    }

    public void confirmUser(View v) {
        methods.prepareCreatePrivate(username.getText().toString());
    }
}