package com.example.guessthemovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MultiPlayerOpciones extends AppCompatActivity {
ImageButton btnJugar,btnRanking,btnAtras;
private  String NombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player_opciones);

        btnJugar=findViewById(R.id.btnJugar);
        btnRanking=findViewById(R.id.btnranking);
        btnAtras=findViewById(R.id.btnAtras);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            NombreUsuario = bundle.getString("Message_key");
        }
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MultiPlayerOpciones.this, PlayOnline.class);
                intent.putExtra("Message_key",NombreUsuario);
                startActivity(intent);
            }
        });
        btnRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MultiPlayerOpciones.this, PlayOnline.class);
                intent.putExtra("Message_key",NombreUsuario);
                startActivity(intent);
            }
        });
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}