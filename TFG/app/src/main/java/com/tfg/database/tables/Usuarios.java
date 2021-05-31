package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class Usuarios {

    /* --- CAMPOS --- */
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "bloqueo", defaultValue = "false")
    @NonNull
    private boolean bloqueo;

    @ColumnInfo(name = "silencio", defaultValue = "false")
    @NonNull
    private boolean silencio;



    /* --- CONSTRUCTOR --- */
    public Usuarios(@NonNull String id) {
        this.id = id;
    }



    /* --- GETTER & SETTER --- */
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public boolean isBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(boolean bloqueo) {
        this.bloqueo = bloqueo;
    }

    public boolean isSilencio() {
        return silencio;
    }

    public void setSilencio(boolean silencio) {
        this.silencio = silencio;
    }

}
