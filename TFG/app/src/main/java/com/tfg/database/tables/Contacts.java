package com.tfg.database.tables;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contacts {

    /* --- CAMPOS --- */
    @PrimaryKey
    @NonNull
    private String id;



    /* --- CONSTRUCTOR --- */
    public Contacts(@NonNull String id) {
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

}
