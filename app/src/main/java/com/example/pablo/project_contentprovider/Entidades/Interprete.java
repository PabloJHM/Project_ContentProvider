package com.example.pablo.project_contentprovider.Entidades;

import android.content.ContentValues;

import com.example.pablo.project_contentprovider.DataBases.Tablas;


public class Interprete implements Comparable<Interprete>{
    private long id;
    private String nombre;
    private int numeroCanciones,numeroAlbums;

    public Interprete(){}
    public Interprete(long id, String nombre, int numeroCanciones, int numeroAlbums) {
        this.id = id;
        this.nombre = nombre;
        this.numeroCanciones=numeroCanciones;
        this.numeroAlbums=numeroAlbums;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getNumeroAlbums() {
        return numeroAlbums;
    }

    public void setNumeroAlbums(int numeroAlbums) {
        this.numeroAlbums = numeroAlbums;
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(Tablas.TablaInterprete.NOMBRE,this.nombre);
        cv.put(Tablas.TablaInterprete.NUMEROCANCIONES,this.numeroCanciones);
        cv.put(Tablas.TablaInterprete.NUMEROALBUMS,this.numeroAlbums);
        return cv;
    }

    @Override
    public String toString() {
        return "Interprete{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", numeroCanciones=" + numeroCanciones +
                ", numeroAlbums=" + numeroAlbums +
                '}';
    }

    /****************************************Comparadores*****************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interprete interprete = (Interprete) o;

        return id == interprete.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Interprete interprete) {
        int r=this.nombre.compareToIgnoreCase(interprete.nombre);
        if(r==0)
            r=(int) (this.id-interprete.id);
        return r;
    }
}
