package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Msg_Grupal {
    @PrimaryKey
    @ColumnInfo(name = "id_grupo")
    @NonNull
    public int idGrupo;

    @ColumnInfo(name = "id_usuario")
    @NonNull
    public String idUsuario;

    @ColumnInfo(name = "mensaje")
    @NonNull
    public String mensaje;

     @ColumnInfo(name = "timestamp")
    @NonNull
    public String timestamp;

}
