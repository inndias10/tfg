package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Msg_Privado {

    /* --- CAMPOS --- */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_usuario")
    @NonNull
    private String idUsuario;

    @ColumnInfo(name = "id_emisor")
    @NonNull
    private String idEmisor;

    @ColumnInfo(name = "mensaje")
    @NonNull
    private String mensaje;

    @ColumnInfo(name = "type")
    @NonNull
    private String type;

    @ColumnInfo(name = "timestamp")
    private String timestamp;


    /* --- CONSTRUCTOR --- */
    public Msg_Privado(@NonNull String idUsuario, @NonNull String idEmisor, @NonNull String mensaje, @NonNull String type, @NonNull String timestamp) {
        this.idUsuario = idUsuario;
        this.idEmisor = idEmisor;
        this.mensaje = mensaje;
        this.type = type;
        this.timestamp = timestamp;
    }

    /* --- GETTER & SETTER --- */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(@NonNull String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @NonNull
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(@NonNull String mensaje) {
        this.mensaje = mensaje;
    }

    @NonNull
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull String timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public String getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(@NonNull String idEmisor) {
        this.idEmisor = idEmisor;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }
}
