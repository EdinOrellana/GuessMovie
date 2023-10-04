package com.example.guessthemovie;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOMultijugador {
    private final DatabaseReference databaseReference;
    public DAOMultijugador(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        databaseReference =database.getReference("GuessTheMovie/Peliculas");

    }

    public Task<Void> addPelicula(Peliculas pelicula, String nombrePelicula){
        //return databaseReference.setValue(contacto);
        return databaseReference.child(nombrePelicula).setValue(pelicula);
    }
    public Task<Void>update(String Key, HashMap<String, Object> hashMap){
        return databaseReference.child(Key).updateChildren(hashMap);
    }
    public Task<Void>remove(String Key){
        return databaseReference.child(Key).removeValue();
    }
    public Query get(){
        return databaseReference.orderByKey();
    }


}
