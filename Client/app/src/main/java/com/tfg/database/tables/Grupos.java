package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.sql.Timestamp;

@Entity(indices = {@Index(value = {"id_nombre"}, unique = true)})
public class Grupos {
    /* id;nombre, fecha, descripcion, administrador, silencio, estado */
    @PrimaryKey
    @ColumnInfo(name = "id_nombre")
    @NonNull
    public String id;

    @ColumnInfo(name = "fecha")
    @NonNull
    public String fecha;

    @ColumnInfo(name = "descripcion")
    public String descripcion;

    @ColumnInfo(name = "administrador", defaultValue = "false")
    public boolean administrador;

    @ColumnInfo(name = "silencio", defaultValue = "false")
    public boolean silencio;

    @ColumnInfo(name = "estado", defaultValue = "in")
    public String estado;

    public Grupos(@NonNull String id, @NonNull String fecha, String descripcion, boolean administrador) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.administrador = administrador;
    }

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
