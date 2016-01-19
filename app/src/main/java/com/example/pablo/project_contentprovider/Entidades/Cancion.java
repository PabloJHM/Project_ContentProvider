package com.example.pablo.project_contentprovider.Entidades;

import android.content.ContentValues;

import com.example.pablo.project_contentprovider.DataBases.Tablas;

public class Cancion implements Comparable<Cancion>{
    private long id,idInterprete,idDisco;
    private String titulo, duracion, ruta;

    public Cancion(){}
    public Cancion(long id, String titulo, String duracion, String ruta, long idInterprete, long idDisco) {
        this.id = id;
        this.titulo = titulo;
        this.duracion=duracion;
        this.ruta=ruta;
        this.idInterprete=idInterprete;
        this.idDisco=idDisco;
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

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public long getIdDisco() {
        return idDisco;
    }

    public void setIdDisco(long idDisco) {
        this.idDisco = idDisco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(Tablas.TablaCancion.TITULO,this.titulo);
        cv.put(Tablas.TablaCancion.IDDISCO,this.idDisco);
        cv.put(Tablas.TablaCancion.IDINTERPRETE,this.idInterprete);
        cv.put(Tablas.TablaCancion.DURACION,this.duracion);
        cv.put(Tablas.TablaCancion.RUTA,this.ruta);
        return cv;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + id +
                ", idInterprete=" + idInterprete +
                ", idDisco=" + idDisco +
                ", titulo='" + titulo + '\'' +
                ", duracion='" + duracion + '\'' +
                ", ruta='" + ruta + '\'' +
                '}';
    }

    /****************************************Comparadores*****************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cancion cancion = (Cancion) o;

        return id == cancion.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public int compareTo(Cancion cancion) {
        int r=this.titulo.compareToIgnoreCase(cancion.titulo);
        if(r==0)
            r=(int) (this.id-cancion.id);
        return r;
    }

}

