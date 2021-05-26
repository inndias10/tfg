package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    public Design(@NonNull String background, @NonNull String header, @NonNull String fontSize) {
        this.background = background;
        this.header = header;
        this.fontSize = fontSize;
    }

    @NonNull
    public String getBackground() {
        return background;
    }

    public void setBackground(@NonNull String background) {
        this.background = background;
    }

    @NonNull
    public String getHeader() {
        return header;
    }

    public void setHeader(@NonNull String header) {
        this.header = header;
    }

    @NonNull
    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(@NonNull String fontSize) {
        this.fontSize = fontSize;
    }
}
