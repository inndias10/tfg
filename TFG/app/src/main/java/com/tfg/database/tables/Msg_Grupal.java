package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Msg_Grupal {

    /* --- CAMPOS --- */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "id_grupo") // id auto + nombre
    @NonNull
    private String idGrupo;

    @ColumnInfo(name = "id_usuario")
    @NonNull
    private String idUsuario;

    @ColumnInfo(name = "mensaje")
    @NonNull
    private String mensaje;

    @ColumnInfo(name = "timestamp")
    @NonNull
    private String timestamp;



    /* --- CONSTRUCTOR --- */
    public Msg_Grupal(@NonNull String idGrupo, @NonNull String idUsuario, @NonNull String mensaje, @NonNull String timestamp) {
        this.idGrupo = idGrupo;
        this.idUsuario = idUsuario;
        this.mensaje = mensaje;
        this.timestamp = timestamp;
    }



    /* --- GETTER & SETTER */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(@NonNull String idGrupo) {
        this.idGrupo = idGrupo;
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
