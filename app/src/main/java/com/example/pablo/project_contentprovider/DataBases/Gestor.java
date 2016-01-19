package com.example.pablo.project_contentprovider.DataBases;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.pablo.project_contentprovider.R;


public class Gestor extends ContentProvider {
    public static final UriMatcher convierteUri2Int;
    private static int CANCION=0,CANCION_ID=1, DISCO=2,DISCO_ID=3,INTERPRETE=4,INTERPRETE_ID=5;
    static {
        convierteUri2Int = new UriMatcher(UriMatcher.NO_MATCH);
        /********Cancion********/
        convierteUri2Int.addURI(Tablas.TablaCancion.AUTHORITY,
                Tablas.TablaCancion.TABLA, CANCION);
        convierteUri2Int.addURI(Tablas.TablaCancion.AUTHORITY,
                Tablas.TablaCancion.TABLA + "/#", CANCION_ID);
        /********Album********/
        convierteUri2Int.addURI(Tablas.TablaDisco.AUTHORITY,
                Tablas.TablaDisco.TABLA, DISCO);
        convierteUri2Int.addURI(Tablas.TablaDisco.AUTHORITY,
                Tablas.TablaDisco.TABLA + "/#", DISCO_ID);
        /********Interprete********/
        convierteUri2Int.addURI(Tablas.TablaInterprete.AUTHORITY,
                Tablas.TablaInterprete.TABLA, INTERPRETE);
        convierteUri2Int.addURI(Tablas.TablaInterprete.AUTHORITY,
                Tablas.TablaInterprete.TABLA + "/#", INTERPRETE_ID);
    }
    private Ayudante abd;
    private SQLiteDatabase bd;

    public void open() {
        bd = abd.getWritableDatabase();
    }
    public void openRead() {
        bd = abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    @Override
    public boolean onCreate() {
        Ayudante a = new Ayudante(getContext());
        this.abd=a;
        bd=abd.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c=null;
        //Usando un left join obtenemos todos los datos necesarios de las canciones
        if(uri.toString().contains("cancion")){
            String s="Select * from cancion LEFT JOIN disco ON (cancion.iddisco = disco._id) LEFT JOIN interprete ON (disco.idinterprete=interprete._ID)";
            c=bd.rawQuery(s,null);
        }else if(uri.toString().contains("disco")) {
            c = bd.query(Tablas.TablaDisco.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
        }else if(uri.toString().contains("interprete")){
            c = bd.query(Tablas.TablaInterprete.TABLA, projection, selection, selectionArgs, null, null, sortOrder);
        }
        c.setNotificationUri(getContext().getContentResolver(), Tablas.TablaCancion.CONTENT_URI);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    //Metodo para insertar en la base de datos a través del content_provider
    @Override
    public Uri insert(Uri uri,ContentValues values) {
        int conver=convierteUri2Int.match(uri);
        long rowId;
        if (conver != CANCION && conver != CANCION_ID &&
                conver != DISCO && conver != DISCO_ID &&
                conver != INTERPRETE && conver != INTERPRETE_ID) {
            throw new IllegalArgumentException(getContext().getString(R.string.ukURI)+ uri);
            //Mensaje de error si no es correcta la Uri
        }
        if(uri.toString().contains("cancion")){
            rowId = bd.insert(Tablas.TablaCancion.TABLA, null, values);
            if (rowId > 0) {
                Uri uri_actividad = ContentUris.withAppendedId(Tablas.TablaCancion.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        }else if(uri.toString().contains("disco")){
            rowId = bd.insert(Tablas.TablaDisco.TABLA, null, values);
            if (rowId > 0) {
                Uri uri_actividad = ContentUris.withAppendedId(Tablas.TablaDisco.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        }else if(uri.toString().contains("interprete")){
            rowId = bd.insert(Tablas.TablaInterprete.TABLA, null, values);
            if (rowId > 0) {
                Uri uri_actividad = ContentUris.withAppendedId(Tablas.TablaInterprete.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
