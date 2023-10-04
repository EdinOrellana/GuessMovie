package com.example.guessthemovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PlayOnline extends AppCompatActivity {
    private final ArrayList<Peliculas> ArryPeliculas = new ArrayList<>();

    private TextView TituloPista, DescripcionPista, Mensaje;
    private ImageView imgPelicula1, imgPelicula2, imgPelicula3;
    private ToggleButton toggleButton1, toggleButton2, toggleButton3;
    private final ArrayList<String> Pistas = new ArrayList<>(3);
    private ImageButton btnConfirmar, btnSiguiente, btnAnterior;
    private String pelicula1, seleccion1;
    private int aleatorioAnterior = -1;
    private int ContadorPista = 0;

    private String NombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_online);
        TituloPista = findViewById(R.id.tvTituloPista);
        DescripcionPista = findViewById(R.id.tvDescripcionPista);

        Mensaje = findViewById(R.id.tvMensaje);
        toggleButton1 = findViewById(R.id.toggleButton1);
        toggleButton2 = findViewById(R.id.toggleButton2);
        toggleButton3 = findViewById(R.id.toggleButton3);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        imgPelicula1 = findViewById(R.id.imgPelicula1);
        imgPelicula2 = findViewById(R.id.imgPelicula2);
        imgPelicula3 = findViewById(R.id.imgPelicula3);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnAnterior = findViewById(R.id.btnAnterior);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            NombreUsuario = bundle.getString("Message_key");
        }

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContadorPista += 1;
                if (ContadorPista > 2) {
                    ContadorPista = 0;
                }
                pista1(ContadorPista);
            }
        });
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(PlayOnline.this);
                alerta.setMessage("¿Deseas seguir jugando?").setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NuevoJuego();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Single player");
                titulo.show();

                AlertDialog.Builder alerta2 = new AlertDialog.Builder(PlayOnline.this);
                alerta2.setMessage("").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                if (seleccion1.equals(pelicula1)) {
                    AlertDialog titulo2 = alerta2.create();
                    titulo2.setTitle("Adivinaste");
                    titulo2.show();
                    Toast.makeText(PlayOnline.this, "Adivinaste", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog titulo2 = alerta2.create();
                    titulo2.setTitle("Fallaste");
                    titulo2.show();
                    Toast.makeText(PlayOnline.this, "Fallaste intena de nuevo", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContadorPista -= 1;
                if (ContadorPista < 0) {
                    ContadorPista = 2;
                }
                pista1(ContadorPista);
            }
        });
        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleButton2.setChecked(true);
                toggleButton3.setChecked(true);
                seleccion1 = toggleButton1.getText().toString().trim();
            }
        });
        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccion1 = toggleButton2.getText().toString().trim();
                toggleButton1.setChecked(true);
                toggleButton3.setChecked(true);
            }
        });
        toggleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccion1 = toggleButton3.getText().toString().trim();
                toggleButton1.setChecked(true);
                toggleButton2.setChecked(true);
            }
        });
        toggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton1.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.sincolor));
                } else {
                    toggleButton1.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.morado));
                }
            }
        });
        toggleButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton2.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.sincolor));
                } else {
                    toggleButton2.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.morado));
                }
            }
        });
        toggleButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleButton3.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.sincolor));
                } else {
                    toggleButton3.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.morado));
                }
            }
        });

        NuevoJuego();

    }

    void NuevoJuego() {
        ArryPeliculas.clear();
        Pistas.clear();
        pelicula1 = "";
        seleccion1 = "";
        ContadorPista = 0;
        CargarDatos();
    }

    private void CargarDatos() {

        DAOMultijugador dao = new DAOMultijugador();
        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Peliculas peli = data.getValue(Peliculas.class);
                    ArryPeliculas.add(peli);
                }
                int No = numeroaleatoreo();

                ordenarRespuestas(ArryPeliculas.get(No).getImagen1(), ArryPeliculas.get(No).getImagen2(), ArryPeliculas.get(No).getImagen3(), ArryPeliculas.get(No).getPelicula(), ArryPeliculas.get(No).getRespuesta1(), ArryPeliculas.get(No).getRespuesta2());

                pelicula1 = ArryPeliculas.get(No).getPelicula();
                Pistas.add(ArryPeliculas.get(No).getPista1());
                Pistas.add(ArryPeliculas.get(No).getPista2());
                Pistas.add(ArryPeliculas.get(No).getPista3());

                TituloPista.setText("Pista 1");
                DescripcionPista.setText(Pistas.get(0));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    int numeroaleatoreo() {
        int aleatorio, salir = -2;
        do {
            aleatorio = ThreadLocalRandom.current().nextInt(0, (ArryPeliculas.size() - 1) + 1);
            if (aleatorio != aleatorioAnterior) {
                salir = aleatorioAnterior;
            }
        } while (salir != aleatorioAnterior);
        aleatorioAnterior = aleatorio;
        return aleatorio;
    }

    void ordenarRespuestas(String img1, String img2, String img3, String pelicula1, String pelicula2, String pelicula3) {
        int orden = ThreadLocalRandom.current().nextInt(0, 5 + 1);
        switch (orden) {

            case 0:
                Glide.with(PlayOnline.this).load(img1).into(imgPelicula1);
                Glide.with(PlayOnline.this).load(img2).into(imgPelicula2);
                Glide.with(PlayOnline.this).load(img3).into(imgPelicula3);

                toggleButton1.setTextOn(pelicula1);
                toggleButton2.setTextOn(pelicula2);
                toggleButton3.setTextOn(pelicula3);
                toggleButton1.setTextOff(pelicula1);
                toggleButton2.setTextOff(pelicula2);
                toggleButton3.setTextOff(pelicula3);
                break;
            case 1:

                Glide.with(PlayOnline.this).load(img1).into(imgPelicula1);
                Glide.with(PlayOnline.this).load(img3).into(imgPelicula2);
                Glide.with(PlayOnline.this).load(img2).into(imgPelicula3);

                toggleButton1.setTextOn(pelicula1);
                toggleButton2.setTextOn(pelicula3);
                toggleButton3.setTextOn(pelicula2);
                toggleButton1.setTextOff(pelicula1);
                toggleButton2.setTextOff(pelicula3);
                toggleButton3.setTextOff(pelicula2);
                break;
            case 2:
                Glide.with(PlayOnline.this).load(img2).into(imgPelicula1);
                Glide.with(PlayOnline.this).load(img1).into(imgPelicula2);
                Glide.with(PlayOnline.this).load(img3).into(imgPelicula3);

                toggleButton1.setTextOn(pelicula2);
                toggleButton2.setTextOn(pelicula1);
                toggleButton3.setTextOn(pelicula3);
                toggleButton1.setTextOff(pelicula2);
                toggleButton2.setTextOff(pelicula1);
                toggleButton3.setTextOff(pelicula3);
                break;
            case 3:
                Glide.with(PlayOnline.this).load(img2).into(imgPelicula1);
                Glide.with(PlayOnline.this).load(img3).into(imgPelicula2);
                Glide.with(PlayOnline.this).load(img1).into(imgPelicula3);

                toggleButton1.setTextOn(pelicula2);
                toggleButton2.setTextOn(pelicula3);
                toggleButton3.setTextOn(pelicula1);
                toggleButton1.setTextOff(pelicula2);
                toggleButton2.setTextOff(pelicula3);
                toggleButton3.setTextOff(pelicula1);
                break;
            case 4:
                Glide.with(PlayOnline.this).load(img3).into(imgPelicula1);
                Glide.with(PlayOnline.this).load(img1).into(imgPelicula2);
                Glide.with(PlayOnline.this).load(img2).into(imgPelicula3);

                toggleButton1.setTextOn(pelicula3);
                toggleButton2.setTextOn(pelicula1);
                toggleButton3.setTextOn(pelicula2);
                toggleButton1.setTextOff(pelicula3);
                toggleButton2.setTextOff(pelicula1);
                toggleButton3.setTextOff(pelicula2);
                break;
            case 5:
                Glide.with(PlayOnline.this).load(img3).into(imgPelicula1);
                Glide.with(PlayOnline.this).load(img2).into(imgPelicula2);
                Glide.with(PlayOnline.this).load(img1).into(imgPelicula3);

                toggleButton1.setTextOn(pelicula3);
                toggleButton2.setTextOn(pelicula2);
                toggleButton3.setTextOn(pelicula1);
                toggleButton1.setTextOff(pelicula3);
                toggleButton2.setTextOff(pelicula2);
                toggleButton3.setTextOff(pelicula1);
                break;
        }
        toggleButton1.setChecked(true);
        toggleButton2.setChecked(true);
        toggleButton3.setChecked(true);
    }

    void pista1(int n1) {
        switch (n1) {
            case 0:
                TituloPista.setText("Pista 1");
                DescripcionPista.setText(Pistas.get(0));
                break;
            case 1:
                TituloPista.setText("Pista 2");
                DescripcionPista.setText(Pistas.get(1));
                break;
            case 2:
                TituloPista.setText("Pista 3");
                DescripcionPista.setText(Pistas.get(2));
                break;
        }
    }

}