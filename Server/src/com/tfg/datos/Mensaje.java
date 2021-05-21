package com.tfg.datos;

import java.io.Serializable;

public class Mensaje implements Serializable {

    private String emisor;
    private String receptor;
    private byte[] mensaje;
    private int tipo;
    private int error;
    private String aux;
    private String timestamp;

    public Mensaje(String emisor, String receptor, byte[] mensaje, int tipo, int error, String aux) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.error = error;
        this.aux = aux;
    }

    public Mensaje(String emisor, String receptor, byte[] mensaje, int tipo, int error) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.error = error;
    }
    
    public Mensaje(String emisor, String receptor, byte[] mensaje, int tipo, int error, String aux, String timestamp) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.tipo = tipo;
        this.error = error;
        this.aux = aux;
        this.timestamp = timestamp;
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

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setMensaje(byte[] mensaje) {
        this.mensaje = mensaje;
    }
}
