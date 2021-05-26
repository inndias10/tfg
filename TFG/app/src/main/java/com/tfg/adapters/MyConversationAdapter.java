package com.tfg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tfg.R;

import java.util.ArrayList;

public class MyConversationAdapter extends ArrayAdapter<String> {

    //Comprobar al pasar el ArrayList si tiene mas de un campo o no y en funcion de eso rellenar un campo o dos

    Context context;
    String nombre, mensaje;

    public MyConversationAdapter(Context cont, ArrayList<String> contenido) {
        super(cont, R.layout.chat_element, R.id.campo1, contenido);
        this.context = cont;
        inicializarParametros(contenido);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.chat_preview, parent, false);
            TextView cn = convertView.findViewById(R.id.campo1);
            TextView lm = convertView.findViewById(R.id.campo2);
            cn.setText(nombre);
            lm.setText(mensaje);
        }
        return convertView;
    }

    private void inicializarParametros(ArrayList<String> c) {
        this.nombre = c.get(0);
        this.mensaje = c.get(1);
    }
}
