package com.trabajoFinal.chat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CreateDB extends SQLiteOpenHelper {
    public static final String NOMBRE = "dbclient.sdb";
    public static final int VERSION = 1;

    public CreateDB(Context context) {
        super(context, NOMBRE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS conversacion (id_usuario VARCHAR(40) NOT NULL, mensaje LONGTEXT NOT NULL, fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (id_usuario))");
        db.execSQL("CREATE TABLE IF NOT EXISTS design (background VARCHAR(50) NOT NULL, header VARCHAR(50) NOT NULL, font_size VARCHAR(50) NOT NULL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS grupos (id INT NOT NULL UNIQUE, nombre VARCHAR(45) NOT NULL, fecha  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, descripcion VARCHAR(300), administrador VARCHAR(2) NOT NULL DEFAULT 'no', silenciado VARCHAR(2) NOT NULL DEFAULT 'no', PRIMARY KEY (id));");
        db.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id VARCHAR(40) NOT NULL UNIQUE, bloqueo VARCHAR(2) NOT NULL DEFAULT 'no', silencio VARCHAR(2) NOT NULL DEFAULT 'no', PRIMARY KEY (id))");
        db.execSQL("CREATE TABLE IF NOT EXISTS user (id varchar(40) not null, password varchar(100), PRIMARY KEY(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
