package com.example.pablo.project_contentprovider.DataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Ayudante extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="musicateca.sqlite";
    public static final int DATABASE_VERSION = 1;
    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Intenté usar claves foráneas pero por alguna razon me daban error y tve que desistir.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql="create table "+ Tablas.TablaDisco.TABLA+
                " ("+ Tablas.TablaDisco._ID+
                " integer primary key autoincrement, "+
                Tablas.TablaDisco.NOMBRE+" text, "+
                Tablas.TablaDisco.NUNEROCANCONES+" int, "+
                Tablas.TablaDisco.IDINTERPRETE+" int) ";
        db.execSQL(sql);

        sql="create table "+ Tablas.TablaCancion.TABLA+
                " ("+ Tablas.TablaCancion._ID+
                " integer primary key autoincrement, "+
                Tablas.TablaCancion.TITULO+" text, "+
                Tablas.TablaCancion.DURACION+" text, "+
                Tablas.TablaCancion.RUTA+" text, "+
                Tablas.TablaCancion.IDDISCO+" int, "+
                Tablas.TablaCancion.IDINTERPRETE+" int)";
        db.execSQL(sql);

        sql="create table "+ Tablas.TablaInterprete.TABLA+
                " ("+ Tablas.TablaInterprete._ID+
                " integer primary key autoincrement, "+
                Tablas.TablaInterprete.NOMBRE+" text, "+
                Tablas.TablaInterprete.NUMEROCANCIONES+" int, "+
                Tablas.TablaInterprete.NUMEROALBUMS+" int "+
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists "
                + Tablas.TablaDisco.TABLA+","+
                Tablas.TablaCancion.TABLA+","+
                Tablas.TablaInterprete.TABLA;
        db.execSQL(sql);
        onCreate(db);
    }
}
