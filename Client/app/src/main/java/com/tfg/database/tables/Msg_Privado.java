package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Msg_Privado {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "id_usuario")
    @NonNull
    public String idUsuario;

    @ColumnInfo(name = "mensaje")
    @NonNull
    public String mensaje;

    @ColumnInfo(name = "timestamp")
    @NonNull
    public String timestamp;

    public Msg_Privado(@NonNull String idUsuario, @NonNull String mensaje, @NonNull String timestamp) {
        this.idUsuario = idUsuario;
        this.mensaje = mensaje;
        this.timestamp = timestamp;
    }

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
}
