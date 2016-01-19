package com.example.pablo.project_contentprovider.Pestanias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pablo.project_contentprovider.Adaptadores.Adaptador;
import com.example.pablo.project_contentprovider.Entidades.Cancion;
import com.example.pablo.project_contentprovider.ContentResolver;
import com.example.pablo.project_contentprovider.DataBases.Tablas;
import com.example.pablo.project_contentprovider.R;

import java.util.List;

public class Tab1 extends Fragment {
    private ContentResolver cr;
    private List<Cancion> lista;
    private Adaptador ad;
    private ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cr=new ContentResolver(getContext());
    }

    //Genero el listvew en el fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1, container, false);
        lv=(ListView)v.findViewById(R.id.listView);
        lista=cr.sacaCancionesList();
        ad=new Adaptador(getActivity(),R.layout.item_list,lista);
        lv.setAdapter(ad);
        //Este listener lanza el intent del reproductor de música del movil, pasandole la ruta de
        //las canciones será capaz de reproducirlas.
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                        Uri data = Uri.parse("file://"+lista.get(position).getRuta());
                        intent.setDataAndType(data, "audio/mp3");
                        startActivity(intent);
                    }
                }
        );
        return v;
    }



}
