package com.example.guessthemovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Ideas extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    rvAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideas);

        swipeRefreshLayout = findViewById(R.id.swip);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new rvAdapter(this);
        recyclerView.setAdapter(adapter);



        OrdenerDatos(numeroaleatoreo());
        OrdenerDatos(numeroaleatoreo());
        OrdenerDatos(numeroaleatoreo());
        OrdenerDatos(numeroaleatoreo());

    }


    void OrdenerDatos(int n1) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page="+n1+"&sort_by=popularity.desc")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1YmE1NjI0Yjk5YjcwODIzMjQ2ZDFmYzk2ZDZhZGZkNyIsInN1YiI6IjY1MTc2ZjgxOWI4NjE2MDBlMjRmOTc2YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.qRWZKlQytq35gPBiauD45cTHJ0-zldfC3JohjxPa1-U")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String respuestaServer = response.body().string();
                    Ideas.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ArrayList<Peliculas> peliculas = new ArrayList<>();

                                JSONObject respuesta = new JSONObject(respuestaServer);
                                JSONArray result = respuesta.getJSONArray("results");
                                for(int i=0;i<respuesta.length();i++){
                                    JSONObject pelicula=result.getJSONObject(i);
                                    String title=pelicula.getString("title");
                                    String imagen=pelicula.getString("poster_path");
                                    Peliculas con = new Peliculas(title,imagen);
                                    peliculas.add(con);
                                }
                                adapter.setItems(peliculas);
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {

                            }
                        }
                    });
                }
            }
        });
    }
    int  numeroaleatoreo() {
        int aleatorio;
            aleatorio = ThreadLocalRandom.current().nextInt(1, (500) + 1);
        return aleatorio;
    }
}