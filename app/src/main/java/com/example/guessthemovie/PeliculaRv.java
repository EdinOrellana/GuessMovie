package com.example.guessthemovie;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PeliculaRv extends RecyclerView.ViewHolder{
    public TextView txtNombre;
    public ImageView Img_emp;

    public PeliculaRv(@NonNull View itemView) {
        super(itemView);
        txtNombre = itemView.findViewById(R.id.txtNombre);
        Img_emp = itemView.findViewById(R.id.img_emp);
    }
}
