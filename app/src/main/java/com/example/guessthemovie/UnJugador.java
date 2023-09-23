package com.example.guessthemovie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class UnJugador extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private TextView TituloPista,DescripcionPista,Mensaje;
    private ImageView imgPelicula1,imgPelicula2,imgPelicula3;
    private ToggleButton toggleButton1,toggleButton2,toggleButton3;
    private ArrayList<String>Pistas=new ArrayList<>(3);
    private ImageButton btnConfirmar, btnSiguiente, btnAnterior;
    String pelicula1="",seleccion1;
    ArrayList<String> id=new ArrayList<>();
    private int aleatorioAnterior=-1;
   private int n1,ContadorPista=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_jugador);

        databaseHelper = new DatabaseHelper(this);
        TituloPista=findViewById(R.id.tvTituloPista);
        DescripcionPista=findViewById(R.id.tvDescripcionPista);
        Mensaje=findViewById(R.id.tvMensaje);

        toggleButton1=findViewById(R.id.toggleButton1);
        toggleButton2=findViewById(R.id.toggleButton2);
        toggleButton3=findViewById(R.id.toggleButton3);

        btnConfirmar=findViewById(R.id.btnConfirmar);

        imgPelicula1=findViewById(R.id.imgPelicula1);
        imgPelicula2=findViewById(R.id.imgPelicula2);
        imgPelicula3=findViewById(R.id.imgPelicula3);

        NuevoJuego();

        btnSiguiente=findViewById(R.id.btnSiguiente);
        pista1(ContadorPista);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContadorPista+=1;
                if(ContadorPista>2){
                    ContadorPista=0;
                }
                pista1(ContadorPista);
            }
        });

        btnAnterior=findViewById(R.id.btnAnterior);
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContadorPista-=1;
                if(ContadorPista<0){
                    ContadorPista=2;
                }
                pista1(ContadorPista);
            }
        });
        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccion1=toggleButton1.getText().toString().trim();
            }
        });
        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccion1=toggleButton2.getText().toString().trim();
            }
        });
        toggleButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccion1=toggleButton3.getText().toString().trim();
            }
        });
btnConfirmar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(seleccion1.equals(pelicula1)){
            Toast.makeText(UnJugador.this,"Adivinaste",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(UnJugador.this,"Fallaste intena de nuevo",Toast.LENGTH_SHORT).show();
        }
        AlertDialog.Builder alerta=new AlertDialog.Builder(UnJugador.this);
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
        AlertDialog titulo=alerta.create();
        titulo.setTitle("Single player");
        titulo.show();
    }
});




    }

    void NuevoJuego(){
        pelicula1="";
        seleccion1="";
        id.clear();
        ContadorPista=0;
        CargarDatos();
        datos();
        toggleButton1.setChecked(true);
        toggleButton2.setChecked(true);
        toggleButton3.setChecked(true);
    }
    void CargarDatos(){
        database = databaseHelper.getWritableDatabase();
        String[] columnas1 = {DatabaseHelper.COLUMNADIVINANZA_ID};
        try {
            Cursor cursor = database.query(DatabaseHelper.TABLE_ADIVINANZA, columnas1,null , null, null, null, null);
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                 n1=cursor.getCount();
                do {
                    id.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_ID)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            database.close();
        }catch (Exception e){
            Toast.makeText(UnJugador.this,""+e,Toast.LENGTH_SHORT).show();
        }
    }
    void datos(){
        database = databaseHelper.getWritableDatabase();
        String[] columnas = {
                DatabaseHelper.COLUMNADIVINANZA_PELICULA,
                DatabaseHelper.COLUMNADIVINANZA_IMAGEN,
                DatabaseHelper.COLUMNADIVINANZA_PISTA1,
                DatabaseHelper.COLUMNADIVINANZA_PISTA2,
                DatabaseHelper.COLUMNADIVINANZA_PISTA3,
                DatabaseHelper.COLUMNADIVINANZA_RESPUESTA1,
                DatabaseHelper.COLUMNADIVINANZA_RESPUESTA2,
                DatabaseHelper.COLUMNADIVINANZA_IMAGEN1,
                DatabaseHelper.COLUMNADIVINANZA_IMAGEN2};
        try {
            String[] parametros={id.get(numeroaleatoreo(n1-1))};
            Cursor cursor = database.query(DatabaseHelper.TABLE_ADIVINANZA, columnas, DatabaseHelper.COLUMNADIVINANZA_ID + "=?", parametros, null, null, null);
            cursor.moveToFirst();

            String imagen1=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_IMAGEN));
            String imagen2=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_IMAGEN1));
            String imagen3=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_IMAGEN2));

            Uri uri1 = Uri.parse(imagen1);
            Uri uri2 = Uri.parse(imagen2);
            Uri uri3 = Uri.parse(imagen3);

            pelicula1=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_PELICULA));
            String pelicula2=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_RESPUESTA1));
            String pelicula3=cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_RESPUESTA2));

            Pistas.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_PISTA1)));
            Pistas.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_PISTA2)));
            Pistas.add(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNADIVINANZA_PISTA3)));

            int orden=ThreadLocalRandom.current().nextInt(0, 5 + 1);
            ordenarRespuestas( uri1,  uri2,  uri3, pelicula1, pelicula2, pelicula3,  orden);

            cursor.close();
            database.close();
        }catch (Exception e){
            Toast.makeText(UnJugador.this,""+e,Toast.LENGTH_SHORT).show();
        }
    }

    int numeroaleatoreo(int numero){
        int aleatorio,salir=-2;
        do{
             aleatorio = ThreadLocalRandom.current().nextInt(0, numero + 1);
             if(aleatorio!=aleatorioAnterior){
                 salir=aleatorioAnterior;
             }
        }while(salir != aleatorioAnterior);
        aleatorioAnterior=aleatorio;
        return aleatorio;
    }
    void ordenarRespuestas(Uri uri1, Uri uri2, Uri uri3,String pelicula1,String pelicula2,String pelicula3, int orden){
        switch (orden){

            case 0:
                imgPelicula1.setImageURI(uri1);
                imgPelicula2.setImageURI(uri2);
                imgPelicula3.setImageURI(uri3);

                toggleButton1.setTextOn(pelicula1);
                toggleButton2.setTextOn(pelicula2);
                toggleButton3.setTextOn(pelicula3);
                toggleButton1.setTextOff(pelicula1);
                toggleButton2.setTextOff(pelicula2);
                toggleButton3.setTextOff(pelicula3);
                break;
            case 1:
               imgPelicula1.setImageURI(uri1);
                   imgPelicula2.setImageURI(uri3);
                  imgPelicula3.setImageURI(uri2);

                toggleButton1.setTextOn(pelicula1);
                toggleButton2.setTextOn(pelicula3);
                toggleButton3.setTextOn(pelicula2);
                toggleButton1.setTextOff(pelicula1);
                toggleButton2.setTextOff(pelicula3);
                toggleButton3.setTextOff(pelicula2);
                break;
            case 2:
                imgPelicula1.setImageURI(uri2);
                   imgPelicula2.setImageURI(uri1);
                  imgPelicula3.setImageURI(uri3);

                toggleButton1.setTextOn(pelicula2);
                toggleButton2.setTextOn(pelicula1);
                toggleButton3.setTextOn(pelicula3);
                toggleButton1.setTextOff(pelicula2);
                toggleButton2.setTextOff(pelicula1);
                toggleButton3.setTextOff(pelicula3);
                break;
            case 3:
                   imgPelicula1.setImageURI(uri2);
                 imgPelicula2.setImageURI(uri3);
                    imgPelicula3.setImageURI(uri1);

                toggleButton1.setTextOn(pelicula2);
                toggleButton2.setTextOn(pelicula3);
                toggleButton3.setTextOn(pelicula1);
                toggleButton1.setTextOff(pelicula2);
                toggleButton2.setTextOff(pelicula3);
                toggleButton3.setTextOff(pelicula1);
                break;
            case 4:
                 imgPelicula1.setImageURI(uri3);
                imgPelicula2.setImageURI(uri1);
               imgPelicula3.setImageURI(uri2);

                toggleButton1.setTextOn(pelicula3);
                toggleButton2.setTextOn(pelicula1);
                toggleButton3.setTextOn(pelicula2);
                toggleButton1.setTextOff(pelicula3);
                toggleButton2.setTextOff(pelicula1);
                toggleButton3.setTextOff(pelicula2);
                break;
            case 5:
                 imgPelicula1.setImageURI(uri3);
                  imgPelicula2.setImageURI(uri2);
                imgPelicula3.setImageURI(uri1);

                toggleButton1.setTextOn(pelicula3);
                toggleButton2.setTextOn(pelicula2);
                toggleButton3.setTextOn(pelicula1);
                toggleButton1.setTextOff(pelicula3);
                toggleButton2.setTextOff(pelicula2);
                toggleButton3.setTextOff(pelicula1);
                break;
        }

    }
    void pista1(int n1){

    switch (n1){
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