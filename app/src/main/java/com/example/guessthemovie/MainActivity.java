package com.example.guessthemovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Button btnIniciar;
    private EditText tvUsuario, tvContras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     databaseHelper = new DatabaseHelper(this);
        tvUsuario = findViewById(R.id.tvUsuario);
        tvContras = findViewById(R.id.tvContra);
        btnIniciar=findViewById(R.id.btnIniciarSesion);



        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    database = databaseHelper.getWritableDatabase();

                    String[] columnas = {DatabaseHelper.COLUMNLOGIN_NAME, DatabaseHelper.COLUMNLOGIN_PASSWORD};
                    String[] parametros = {tvUsuario.getText().toString().trim(), tvContras.getText().toString().trim()};
                    Cursor cursor = database.query(DatabaseHelper.TABLE_LOGIN, columnas,DatabaseHelper.COLUMNLOGIN_NAME + "=? and " + DatabaseHelper.COLUMNLOGIN_PASSWORD + "=?",parametros, null, null, null);
                   if(cursor.moveToFirst()){
                       String Usuario = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNLOGIN_NAME));
                       Toast.makeText(MainActivity.this, "Bienvenido " + Usuario, Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(MainActivity.this, PlayerOpciones.class);
                           startActivity(intent);
                   }else{
                       Toast.makeText(MainActivity.this, "Usuario y/o contraseña incorrectos ", Toast.LENGTH_SHORT).show();
                   }
                    tvContras.setText("");
                    tvUsuario.setText("");
                    database.close();
                    cursor.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}