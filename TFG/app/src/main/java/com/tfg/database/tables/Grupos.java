package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class Grupos {

    /* --- CAMPOS --- */
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private String id;

    @ColumnInfo(name = "fecha")
    @NonNull
    private String fecha;

    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "administrador", defaultValue = "false")
    private boolean administrador;

    @ColumnInfo(name = "silencio", defaultValue = "false")
    private boolean silencio;

    @ColumnInfo(name = "estado", defaultValue = "in")
    private String estado;



    /* --- CONSTRUCTOR --- */
    public Grupos(@NonNull String id, @NonNull String fecha, String descripcion, boolean administrador) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.administrador = administrador;
    }



    /* GETTER & SETTER */
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isSilencio() {
        return silencio;
    }

    public void setSilencio(boolean silencio) {
        this.silencio = silencio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
