package com.example.pablo.project_contentprovider.DataBases;

import android.net.Uri;
import android.provider.BaseColumns;


public class Tablas {
    private Tablas(){}

    public static abstract class TablaDisco implements BaseColumns {
        public static final String TABLA = "disco";
        public static final String NOMBRE = "nombre";
        public static final String IDINTERPRETE = "idinterprete";
        public static final String NUNEROCANCONES = "numerocanciones";
        public final static String AUTHORITY = "com.example.pablo.project_contentprovider";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);

    }

    public static abstract class TablaCancion implements BaseColumns {
        public static final String TABLA = "cancion";
        public static final String TITULO = "titulo";
        public static final String IDDISCO = "iddisco";
        public static final String IDINTERPRETE = "idinterprete";
        public static final String DURACION = "duracion";
        public static final String RUTA = "ruta";
        public final static String AUTHORITY = "com.example.pablo.project_contentprovider";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);

    }

    public static abstract class TablaInterprete implements BaseColumns {
        public static final String TABLA = "interprete";
        public static final String NOMBRE = "nombre";
        public static final String NUMEROCANCIONES = "numerocanciones";
        public static final String NUMEROALBUMS = "numeroalbums";
        public final static String AUTHORITY = "com.example.pablo.project_contentprovider";
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLA);
    }
}
