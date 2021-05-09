package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class Usuarios {
    // COLUMNAS
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "bloqueo", defaultValue = "false")
    @NonNull
    public boolean bloqueo;

    @ColumnInfo(name = "silencio", defaultValue = "false")
    @NonNull
    public boolean silencio;

    public Usuarios(@NonNull String id) {
        this.id = id;
    }

}
