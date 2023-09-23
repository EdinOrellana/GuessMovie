package com.example.guessthemovie;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AdminCrear extends AppCompatActivity {
    private Button btnPelicula1,btnPelicula2,btnPelicula3,btnCancelar,btnGuardar;
    private ImageView mostrar1,mostrar2,mostrar3;
    private boolean bandera1=false,bandera2=false,bandera3=false;
    private String urlimagen1,urlimagen2,urlimagen3;


    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        /*Uri myUri = Uri.parse("http://stackoverflow.com")*/
                        Intent data = result.getData();
                        Uri paht=data.getData();
                        if(bandera1){
                            urlimagen1=paht.toString();
                            mostrar1.setImageURI(paht);
                            Toast.makeText(AdminCrear.this,  urlimagen1, Toast.LENGTH_SHORT).show();
                            bandera1=false;
                        } else if (bandera2) {
                            urlimagen2=paht.toString();
                            mostrar2.setImageURI(paht);
                            Toast.makeText(AdminCrear.this,  urlimagen2, Toast.LENGTH_SHORT).show();
                            bandera2=false;
                        } else if (bandera3) {
                            urlimagen3=paht.toString();
                            mostrar3.setImageURI(paht);
                            Toast.makeText(AdminCrear.this,  urlimagen3, Toast.LENGTH_SHORT).show();
                            bandera3=false;
                        }

                    }
                }
            });
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText pista1,pista2,pista3,pelicula,Posiblerespuesta1,Posiblerespuesta2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_crear);

        databaseHelper = new DatabaseHelper(this);

        pista1=findViewById(R.id.tvPista1);
        pista2=findViewById(R.id.tvPista2);
        pista3=findViewById(R.id.tvPista3);

        pelicula=findViewById(R.id.tvPelicualAdivinar);
        Posiblerespuesta1=findViewById(R.id.tvPosibleRespuesta1);
        Posiblerespuesta2=findViewById(R.id.tvPosibleRespuesta2);

        btnPelicula1 = findViewById(R.id.btnAgregarimg1);
        btnPelicula2 = findViewById(R.id.btnAgregarimg2);
        btnPelicula3 = findViewById(R.id.btnAgregarimg3);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardar=findViewById(R.id.btnGuardar);

        mostrar1=findViewById(R.id.imgPelicula1);
        mostrar2=findViewById(R.id.imgPelicula2);
        mostrar3=findViewById(R.id.imgPelicula3);

        btnGuardar.setOnClickListener(v -> {
            try {
              /*  if (!BanderaGuardar_Actualizar) {
                    database = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_TITLE, tvContenidoTitulo.getText().toString());
                    values.put(DatabaseHelper.COLUMN_DESCRIPCION, tvContenidoDescripcion.getText().toString());
                    String[] parametros = {id};
                    database.update(DatabaseHelper.TABLE_NAME, values, DatabaseHelper.COLUMN_ID + "=?", parametros);
                    Toast.makeText(contenido.this, "Acttualizado", Toast.LENGTH_SHORT).show();
                    database.close();
                } */
                    database = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMNADIVINANZA_PELICULA, pelicula.getText().toString().trim());
                    values.put(DatabaseHelper.COLUMNADIVINANZA_IMAGEN, urlimagen1);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_PISTA1, pista1.getText().toString().trim());
                    values.put(DatabaseHelper.COLUMNADIVINANZA_PISTA2, pista2.getText().toString().trim());
                    values.put(DatabaseHelper.COLUMNADIVINANZA_PISTA3, pista3.getText().toString().trim());

                    values.put(DatabaseHelper.COLUMNADIVINANZA_RESPUESTA1, Posiblerespuesta1.getText().toString().trim());
                    values.put(DatabaseHelper.COLUMNADIVINANZA_RESPUESTA2, Posiblerespuesta2.getText().toString().trim());
                    values.put(DatabaseHelper.COLUMNADIVINANZA_IMAGEN1, urlimagen2);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_IMAGEN2, urlimagen3);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_NUBE,"0");

                    database.insert(DatabaseHelper.TABLE_ADIVINANZA, null, values);
                    Toast.makeText(AdminCrear.this, "Guardado", Toast.LENGTH_SHORT).show();
                    database.close();
            } catch (Exception e) {
                Toast.makeText(AdminCrear.this, "Error", Toast.LENGTH_SHORT).show();
            }
            finish();
        });


        btnPelicula1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandera1=true;
                mGetContent.launch(new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            }
        });

        btnPelicula2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandera2=true;
                mGetContent.launch(new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            }
        });
        btnPelicula3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandera3=true;
                mGetContent.launch(new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            }
        });
    }
}


