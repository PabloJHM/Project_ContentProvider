package com.example.pablo.project_contentprovider.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pablo.project_contentprovider.Entidades.Album;
import com.example.pablo.project_contentprovider.R;

import java.util.List;

public class AdaptadorAlbum extends ArrayAdapter {
    List<Album> aux;
    private Context ctx;
    private LayoutInflater i;
    int res;

    public AdaptadorAlbum(Context context, int resource, List<Album> aux) {
        super(context, resource, aux);
        this.aux = aux;
        this.res = resource;
        this.ctx=context;
        i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    class ViewHolder {
        public TextView tv1, tv2, tv3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        if (convertView == null) {
            convertView = i.inflate(res,null);
            TextView tv = (TextView) convertView.findViewById(R.id.tvTitulo);
            vh.tv1 = tv;
            tv = (TextView) convertView.findViewById(R.id.tvDuracion);
            vh.tv2 = tv;
            tv = (TextView) convertView.findViewById(R.id.tvAlbumnes);
            vh.tv3 = tv;
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }
        vh.tv1.setText(aux.get(position).getNombre());
        vh.tv2.setText(aux.get(position).getNumeroCanciones() + " " + ctx.getString(R.string.canciones));
        vh.tv3.setVisibility(View.GONE);
        return convertView;
    }
}
