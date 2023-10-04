package com.example.guessthemovie;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class rvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Peliculas> list = new ArrayList<>();

    public rvAdapter(Context context){
        this.context = context;
    }

    public void setItems(ArrayList<Peliculas> listC){
        this.list.addAll(listC);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new PeliculaRv(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PeliculaRv vh = (PeliculaRv) holder;
        Peliculas c = list.get(position);
        vh.txtNombre.setText(c.getPelicula());
        Glide.with(context).load("https://image.tmdb.org/t/p/w220_and_h330_face"+c.getImagen1()).into(vh.Img_emp);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
