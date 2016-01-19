package com.example.pablo.project_contentprovider;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.pablo.project_contentprovider.Entidades.Album;
import com.example.pablo.project_contentprovider.Entidades.Cancion;
import com.example.pablo.project_contentprovider.DataBases.Ayudante;
import com.example.pablo.project_contentprovider.DataBases.Tablas;
import com.example.pablo.project_contentprovider.Entidades.Interprete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ContentResolver extends Application{
    private List<Cancion> lc;
    private List<Album> la;
    private List<Interprete> li;
    private SharedPreferences prefs;
    private Ayudante a;
    private Context c;
    public ContentResolver(Context c){
        this.c=c;
        prefs = c.getSharedPreferences("Check", 0);
        a=new Ayudante(c);
    }

    //Metodo que genera una preferencia que indica si se ha entrado con anteiroridad o no a la lista,
    //para saber de donde recoger los datos. Hay una preferencia para cada agrupacion (canciones, albumnes y interpretes)
    public void generaPreferencia(String tipo){
        SharedPreferences.Editor ed = prefs.edit();
        if(tipo.equals("Canciones")) {
            ed.putBoolean("Song", true);
        }
        if(tipo.equals("Albums")){
            ed.putBoolean("Album", true);
        }
        if(tipo.equals("Interpretes")){
            ed.putBoolean("Artist", true);
        }
        ed.commit();
    }

    // Metodo que saca el cursor que recorre canciones, dependiendo si hay o no hay una preferencia
    // que indique si se ha abierto por primera vez o no. En caso de que se abra por primera vez,
    // el cursor recorre los datos sacados directamente del movil, en caso contrario obtiene los datos
    // de la base de datos.
    public Cursor sacaCancionesCursor(){
        Cursor cur;
        if(!prefs.contains("Song")) {
            cur = c.getContentResolver().query(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);
            generaPreferencia("Canciones");
        } else {
            cur = c.getContentResolver().query(Tablas.TablaCancion.CONTENT_URI, null, null, null, null);
        }
        return cur;
    }

    //Idem con albumnes
    public Cursor sacaAlbumsCursor(){
        Cursor cur;
        if(!prefs.contains("Album")) {
            cur = c.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null);
            generaPreferencia("Albums");
        } else {
            cur = c.getContentResolver().query(Tablas.TablaDisco.CONTENT_URI, null, null, null, null);
        }
        return cur;
    }

    //Idem con Interpretes
    public Cursor sacaInterpreteCursor(){
        Cursor cur;
        if(!prefs.contains("Artist")){
            cur = c.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, null, null, null, null);
            generaPreferencia("Interpretes");
        } else {
            cur = c.getContentResolver().query(Tablas.TablaInterprete.CONTENT_URI, null, null, null, null);
        }
        return cur;
    }

    // Metodo con el que obtengo la lista de canciones. Al igual que con los cursores, dependiendo de
    // si es la primera vez o no recoge los datos del telefono o de la base de datos.
    public List sacaCancionesList() {
        lc = new ArrayList();
        int id, idInterprete, idDisco, dur;
        String title, duracion, ruta;
        Cursor cur;
        Cancion ca;
        //Si no existe su preferencia compartida es que no se ha abierto ninguna vez, por lo que recoge
        // los datos del movil, buscando el nombre de la columna de su base de datos
        if (!prefs.contains("Song")){
            cur=sacaCancionesCursor();
            while (cur.moveToNext()) {
                id = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Media._ID));
                title = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE));
                dur = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String segundos = String.valueOf((dur % 60000) / 1000);
                String minutos = String.valueOf(dur / 60000);
                if (segundos.length() == 1) {
                    if(minutos.length()==1){
                        duracion = "0" + minutos + ":0" + segundos;
                    } else {
                        duracion = minutos + ":0" + segundos;
                    }
                } else {
                    if(minutos.length()==1) {
                        duracion = "0" + minutos + ":" + segundos;
                    } else {
                        duracion = minutos + ":" + segundos;
                    }
                }
                ruta=cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA)); //La ruta la usaremos para el reproductor
                idInterprete = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));
                idDisco = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                ca = new Cancion(id, title, duracion, ruta, idInterprete, idDisco);
                c.getContentResolver().insert(Tablas.TablaCancion.CONTENT_URI, ca.getContentValues());
                lc.add(ca);
            }
            //En caso de que exista, recoge los datos de nuestra base de datos
        } else {
            cur=sacaCancionesCursor();
            while (cur.moveToNext()){
                id = cur.getInt(cur.getColumnIndex("_id"));
                title = cur.getString(cur.getColumnIndex("titulo"));
                String duraciont = cur.getString(cur.getColumnIndex("duracion"));
                ruta = cur.getString(cur.getColumnIndex("ruta"));
                System.out.println("Esta es la ruta: "+ruta);
                idInterprete = cur.getInt(cur.getColumnIndex("idinterprete"));
                idDisco = cur.getInt(cur.getColumnIndex("iddisco"));
                ca = new Cancion(id, title, duraciont, ruta, idInterprete, idDisco);
                lc.add(ca);
            }
        }
        cur.close();
        Collections.sort(lc);
        return lc;
    }

    //Idem con albumnes
    public List sacaAlbumList() {
        la = new ArrayList();
        int id,numeroCanciones;
        Cursor cur;
        String nombre;
        Album al;
        if (!prefs.contains("Album")) {
            cur=sacaAlbumsCursor();
            while (cur.moveToNext()) {
                id = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Albums._ID));
                nombre = cur.getString(cur.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                numeroCanciones = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS));
                al = new Album(id, nombre, numeroCanciones);
                c.getContentResolver().insert(Tablas.TablaDisco.CONTENT_URI, al.getContentValues());
                la.add(al);
            }
        } else {
            cur=sacaAlbumsCursor();
            while (cur.moveToNext()) {
                id = cur.getInt(cur.getColumnIndex("_id"));
                nombre = cur.getString(cur.getColumnIndex("nombre"));
                numeroCanciones = cur.getInt(cur.getColumnIndex("numerocanciones"));
                al = new Album(id, nombre, numeroCanciones);
                la.add(al);
            }
        }
        cur.close();
        Collections.sort(la);
        return la;

    }

    //Idem con Interpretes
    public List sacaInterpreteList() {
        li = new ArrayList();
        int id,numeroCanciones,numeroAlbums;
        String nombre;
        Cursor cur;
        Interprete in;
        if (!prefs.contains("Artist")) {
            cur=sacaInterpreteCursor();
            while (cur.moveToNext()) {
                id = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Artists._ID));
                nombre = cur.getString(cur.getColumnIndex(MediaStore.Audio.Artists.ARTIST));
                numeroCanciones = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));
                numeroAlbums = cur.getInt(cur.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS));
                in = new Interprete(id, nombre, numeroCanciones, numeroAlbums);
                c.getContentResolver().insert(Tablas.TablaInterprete.CONTENT_URI, in.getContentValues());
                li.add(in);
            }
        } else {
            cur=sacaInterpreteCursor();
            while (cur.moveToNext()) {
                id = cur.getInt(cur.getColumnIndex("_id"));
                nombre = cur.getString(cur.getColumnIndex("nombre"));
                numeroCanciones = cur.getInt(cur.getColumnIndex("numerocanciones"));
                numeroAlbums = cur.getInt(cur.getColumnIndex("numeroalbums"));
                in = new Interprete(id, nombre, numeroCanciones, numeroAlbums);
                li.add(in);
            }
        }
        cur.close();
        Collections.sort(li);
        return li;
    }
}
