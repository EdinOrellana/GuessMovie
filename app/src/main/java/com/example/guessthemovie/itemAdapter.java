package com.example.guessthemovie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
/*
public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder> implements View.OnClickListener{
 /*   private Lis
    t<ListItem>itemList;
    private View.OnClickListener listener;

    itemAdapter(List<ListItem> itemList){
        this.itemList=itemList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ListItem item= itemList.get(position);
    holder.textViewTitle.setText(item.getTitle());
    holder.textViewId.setText(item.getId());
    holder.textViewDescripion.setText(item.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }



    @Override
    public void onClick(View v) {
    if(listener!=null){
    listener.onClick(v);
}
    }
public  void setOnClickListener (View.OnClickListener listener){
        this.listener=listener;
}
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTitle;
        public TextView textViewId;
        public TextView textViewDescripion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle=itemView.findViewById(R.id.TextViewTitulo);
            textViewId =itemView.findViewById(R.id.TextViewId);
            textViewDescripion=itemView.findViewById(R.id.TextViewDescripcion);
        }
    }
}*/
