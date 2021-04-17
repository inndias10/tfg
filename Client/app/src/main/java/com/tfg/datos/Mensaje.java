package com.tfg.datos;

import java.io.Serializable;

public class Mensaje implements Serializable {
    private String emisor;
    private String receptor;
    private byte[] mensaje;
    private int tipo;
    private int error;

    public Mensaje(String emisor, String receptor, byte[] mensaje, int tipo, int error) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.error = error;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public byte[] getMensaje() {
        return mensaje;
    }

    public void setMensaje(byte[] mensaje) {
        this.mensaje = mensaje;
    }
}

