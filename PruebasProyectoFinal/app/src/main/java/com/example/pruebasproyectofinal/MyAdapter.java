package com.example.pruebasproyectofinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {

    Context context;
    ArrayList<String>  conversaciones, mensajesFinales;

    public MyAdapter(Context cont, ArrayList<String> con, ArrayList<String> msj) {
        super(cont, R.layout.chat_preview, R.id.nombreConversacion, con);
        this.context = cont;
        this.conversaciones = con;
        this.mensajesFinales = msj;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.chat_preview, parent, false);
            TextView cn = convertView.findViewById(R.id.nombreConversacion);
            TextView lm = convertView.findViewById(R.id.ultimoMensaje);
            cn.setText(conversaciones.get(position));
            lm.setText(mensajesFinales.get(position));
        }
        return convertView;
    }
}
