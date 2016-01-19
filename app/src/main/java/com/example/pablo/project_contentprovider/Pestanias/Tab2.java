package com.example.pablo.project_contentprovider.Pestanias;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pablo.project_contentprovider.Adaptadores.AdaptadorAlbum;
import com.example.pablo.project_contentprovider.ContentResolver;
import com.example.pablo.project_contentprovider.R;

import java.util.List;

public class Tab2 extends Fragment {
    private ContentResolver cr;
    private AdaptadorAlbum ad;
    private ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cr=new ContentResolver(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2, container, false);
        lv=(ListView)v.findViewById(R.id.listViewAlbum);
        List lista=cr.sacaAlbumList();
        ad=new AdaptadorAlbum(getActivity(),R.layout.item_list,lista);
        lv.setAdapter(ad);
        return v;
    }

}