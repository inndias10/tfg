package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity
public class Design {
    // COLUMNAS
    @PrimaryKey
    @ColumnInfo(name = "background")
    @NonNull
    public String background;

    @ColumnInfo(name = "header")
    @NonNull
    public String header;

    @ColumnInfo(name = "font_size")
    @NonNull
    public String fontSize;

}
