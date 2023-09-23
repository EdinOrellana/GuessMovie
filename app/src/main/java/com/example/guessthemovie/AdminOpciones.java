package com.example.guessthemovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AdminOpciones extends AppCompatActivity {
private ImageButton btnCrear,btnEditar,btnElimnar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_opciones);

        btnCrear=findViewById(R.id.btnNueva);
        btnEditar=findViewById(R.id.btneditar);
        btnElimnar=findViewById(R.id.btneliminar);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminOpciones.this, AdminCrear.class);
                startActivity(intent);
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnElimnar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}