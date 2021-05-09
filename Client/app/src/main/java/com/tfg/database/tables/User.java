package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity
public class User {
    // COLUMNAS
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo (name = "password")
    public String password;

    // CONSTRUCTOR
    public User(@NonNull String id, String password) {
        this.id = id;
        this.password = password;
    }

    // G & S
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
