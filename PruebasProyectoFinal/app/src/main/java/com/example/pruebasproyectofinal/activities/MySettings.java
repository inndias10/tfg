package com.example.pruebasproyectofinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.pruebasproyectofinal.R;

import java.util.ArrayList;

public class MySettings extends AppCompatActivity {

    ArrayAdapter adp;

    //ConstraintLayout c;
    RadioGroup rdgFondo;
    RadioButton rbtnColor, rbtnImagen;
    Spinner spnFondo, spnTamanyo, spnTipo;
    ArrayList<String> fondo, tamanyos, tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        rbtnColor = findViewById(R.id.rbtnColor);
        rbtnImagen = findViewById(R.id.rbtnImagen);
        spnFondo = findViewById(R.id.selectordefondo);
        spnTamanyo = findViewById(R.id.tamanyos);
        //spnTipo = findViewById(R.id.tipografias);
        rdgFondo = findViewById(R.id.radioGroup);
        //c = findViewById(R.id.LayoutConfiguracion);
        fondo = new ArrayList<>();
        tamanyos = new ArrayList<>();
        tipo = new ArrayList<>();
        rdgFondo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbtnColor) {
                    color();
                } else if (checkedId == R.id.rbtnImagen) {
                    imagen();
                }
            }
        });
        tamanyo();
        spnTamanyo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Pequeño")) {

                } else if (parent.getItemAtPosition(position).equals("Mediano")) {

                } else if (parent.getItemAtPosition(position).equals("Grande")) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void color() {
        if (fondo != null) fondo.clear();
        fondo.add("Blanco");
        fondo.add("Azul");
        fondo.add("Verde");
        fondo.add("Rojo");
        fondo.add("Negro");
        agregarCampos(spnFondo);
        spnFondo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Rojo")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.red));
                } else if (parent.getItemAtPosition(position).equals("Azul")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.blue));
                } else if (parent.getItemAtPosition(position).equals("Verde")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.green));
                } else if (parent.getItemAtPosition(position).equals("Blanco")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.white));
                } else if (parent.getItemAtPosition(position).equals("Negro")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void imagen() {
        if (fondo != null) fondo.clear();
        fondo.add("Imagen 1");
        fondo.add("Imagen 2");
        fondo.add("Imagen 3");
        fondo.add("Imagen 4");
        fondo.add("Imagen 5");
        agregarCampos(spnFondo);
        spnFondo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Imagen 1")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.red));
                } else if (parent.getItemAtPosition(position).equals("Imagen 2")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.blue));
                } else if (parent.getItemAtPosition(position).equals("Imagen 3")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.green));
                } else if (parent.getItemAtPosition(position).equals("Imagen 4")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.white));
                } else if (parent.getItemAtPosition(position).equals("Imagen 5")) {
                    //c.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void tamanyo() {
        tamanyos.add("Pequeño");
        tamanyos.add("Mediano");
        tamanyos.add("Grande");
        agregarCampos(spnTamanyo);
    }

    public void agregarCampos(Spinner spn) {
        adp = new ArrayAdapter(MySettings.this, R.layout.spinner_element, R.id.txtElemento, fondo);
        spn.setAdapter(adp);
    }
}