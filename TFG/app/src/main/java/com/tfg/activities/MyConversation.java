package com.tfg.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.R;

public class MyConversation extends AppCompatActivity {
    // comprobar si es la primera vez que le escribe
    // si escribo { ok: meth.createPrivate(id a quien escribo, me, mensaje, type, timestamp) }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
    }

}