package com.example.pablo.project_contentprovider.Entidades;

import android.content.ContentValues;

import com.example.pablo.project_contentprovider.DataBases.Tablas;

public class Album implements Comparable<Album>{
    private long id, idInterprete;
    private String nombre;
    private int numeroCanciones;

    public Album(){}
    public Album(long id, String nombre,int numeroCanciones) {
        this.id = id;
        this.nombre = nombre;
        this.numeroCanciones=numeroCanciones;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdInterprete() {
        return idInterprete;
    }

    public void setIdInterprete(long idInterprete) {
        this.idInterprete = idInterprete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumeroCanciones() {
        return numeroCanciones;
    }

    public void setNumeroCanciones(int numeroCanciones) {
        this.numeroCanciones = numeroCanciones;
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(Tablas.TablaDisco.NOMBRE,this.nombre);
        cv.put(Tablas.TablaDisco.IDINTERPRETE,this.idInterprete);
        cv.put(Tablas.TablaDisco.NUNEROCANCONES,this.numeroCanciones);
        return cv;
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", idInterprete=" + idInterprete +
                ", nombre='" + nombre + '\'' +
                ", numeroCanciones=" + numeroCanciones +
                '}';
    }

    /****************************************Comparadores*****************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        return id == album.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Album album) {
        int r=this.nombre.compareToIgnoreCase(album.nombre);
        if(r==0)
            r=(int) (this.id-album.id);
        return r;
    }
}
