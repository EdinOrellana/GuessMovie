package com.example.guessthemovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class PlayerOpciones extends AppCompatActivity {
    private static final int READ_EXTERNAL_STORAGE =1001;
    private ImageButton btnSoloJugador, btnMultijugdaor, btnSalir,btnMispeliculas;

    private String NombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_opciones);

        requiestcamaraPermission();
        btnMispeliculas=findViewById(R.id.btnMispeliculas);
        btnSoloJugador =findViewById(R.id.btnSolounJugador);
        btnMultijugdaor =findViewById(R.id.btnMultijugador);
        btnSalir =findViewById(R.id.btnSalir);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            NombreUsuario = bundle.getString("Message_key");
        }

        btnSoloJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(PlayerOpciones.this, UnJugador.class);
                    startActivity(intent);
            }
        });
        btnMultijugdaor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PlayerOpciones.this, MultiPlayerOpciones.class);
                intent.putExtra("Message_key",NombreUsuario);
                startActivity(intent);
            }
        });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayerOpciones.this, "Saliste", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        });

        btnMispeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerOpciones.this, AdminOpciones.class);
                startActivity(intent);
            }
        });
    }
    private void requiestcamaraPermission(){
        ActivityCompat.requestPermissions(this,new String[]{permission.READ_EXTERNAL_STORAGE},READ_EXTERNAL_STORAGE);
    }
}