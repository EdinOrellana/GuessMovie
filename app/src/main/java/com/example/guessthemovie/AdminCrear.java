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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminCrear extends AppCompatActivity {
    private Button btnPelicula1, btnPelicula2, btnPelicula3, btnCancelar, btnGuardar;
    private ImageView mostrar1, mostrar2, mostrar3;
    private boolean bandera1 = false, bandera2 = false, bandera3 = false;
    private String urlimagen1="", urlimagen2="", urlimagen3="", Link1="", Link2="", Link3="";
    private static final int File = 1;

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri paht = data.getData();
                        if (bandera1) {
                            urlimagen1 = paht.toString();
                            mostrar1.setImageURI(paht);
                            Toast.makeText(AdminCrear.this, urlimagen1, Toast.LENGTH_SHORT).show();
                        } else if (bandera2) {
                            urlimagen2 = paht.toString();
                            mostrar2.setImageURI(paht);
                            Toast.makeText(AdminCrear.this, urlimagen2, Toast.LENGTH_SHORT).show();
                        } else if (bandera3) {
                            urlimagen3 = paht.toString();
                            mostrar3.setImageURI(paht);
                            Toast.makeText(AdminCrear.this, urlimagen3, Toast.LENGTH_SHORT).show();
                        }

                        //Lo guarda en STORAGE DE FIREBASE
                        StorageReference Folder = FirebaseStorage.getInstance().getReference().child("Users");
                        final StorageReference file_name = Folder.child("file" + paht.getLastPathSegment());
                        file_name.putFile(paht).addOnSuccessListener(taskSnapshot -> file_name.getDownloadUrl().addOnSuccessListener(uri -> {
                            String Link = uri.toString();
                            if (bandera1) {
                                Link1 = Link;
                                bandera1 = false;
                                Toast.makeText(AdminCrear.this, "Listo", Toast.LENGTH_SHORT).show();
                            } else if (bandera2) {
                                Link2 = Link;
                                bandera2 = false;
                                Toast.makeText(AdminCrear.this, "Listo", Toast.LENGTH_SHORT).show();
                            } else if (bandera3) {
                                Link3 = Link;
                                bandera3 = false;
                                Toast.makeText(AdminCrear.this, "Listo", Toast.LENGTH_SHORT).show();
                            }
                        }));
                    }
                }
            });

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText pista1, pista2, pista3, pelicula, Posiblerespuesta1, Posiblerespuesta2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_crear);

        databaseHelper = new DatabaseHelper(this);

        pista1 = findViewById(R.id.tvPista1);
        pista2 = findViewById(R.id.tvPista2);
        pista3 = findViewById(R.id.tvPista3);

        pelicula = findViewById(R.id.tvPelicualAdivinar);
        Posiblerespuesta1 = findViewById(R.id.tvPosibleRespuesta1);
        Posiblerespuesta2 = findViewById(R.id.tvPosibleRespuesta2);

        btnPelicula1 = findViewById(R.id.btnAgregarimg1);
        btnPelicula2 = findViewById(R.id.btnAgregarimg2);
        btnPelicula3 = findViewById(R.id.btnAgregarimg3);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardar = findViewById(R.id.btnGuardar);

        mostrar1 = findViewById(R.id.imgPelicula1);
        mostrar2 = findViewById(R.id.imgPelicula2);
        mostrar3 = findViewById(R.id.imgPelicula3);

        btnGuardar.setOnClickListener(v -> {
            ContentValues values = new ContentValues();
            String dato1=pelicula.getText().toString().trim();
            String dato2=pista1.getText().toString().trim();
            String dato3=pista2.getText().toString().trim();
            String dato4=pista3.getText().toString().trim();
            String dato5=Posiblerespuesta1.getText().toString().trim();
            String dato6=Posiblerespuesta2.getText().toString().trim();


            if(!dato1.equals("") && !urlimagen1.equals("") && !dato2.equals("")&& !dato3.equals("")&& !dato4.equals("")&& !dato5.equals("")&& !dato6.equals("")&& !urlimagen2.equals("")&& !urlimagen3.equals("")){
                try {

                    database = databaseHelper.getWritableDatabase();

                    values.put(DatabaseHelper.COLUMNADIVINANZA_PELICULA, dato1);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_IMAGEN, urlimagen1);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_PISTA1, dato2);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_PISTA2, dato3);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_PISTA3, dato4);

                    values.put(DatabaseHelper.COLUMNADIVINANZA_RESPUESTA1, dato5);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_RESPUESTA2, dato6);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_IMAGEN1, urlimagen2);
                    values.put(DatabaseHelper.COLUMNADIVINANZA_IMAGEN2, urlimagen3);

                    database.insert(DatabaseHelper.TABLE_ADIVINANZA, null, values);
                    Toast.makeText(AdminCrear.this, "Guardado", Toast.LENGTH_SHORT).show();
                    database.close();

                } catch (Exception e) {
                    Toast.makeText(AdminCrear.this, "Error"+e, Toast.LENGTH_SHORT).show();
                }
                if(!Link1.equals("")&& !Link2.equals("")&& !Link3.equals("")){
                    try {
                        //agregar en REALTIME DATABASE
                        DAOMultijugador DAO = new DAOMultijugador();
                        Peliculas peli = new Peliculas(dato1, dato2, dato3,dato4,dato5, dato6, Link1, Link2, Link3);
                        DAO.addPelicula(peli, pelicula.getText().toString().trim()).addOnSuccessListener(suc -> {
                            Toast.makeText(AdminCrear.this, "Se inserto con exito", Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(er -> {
                            Toast.makeText(AdminCrear.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }catch (Exception e){
                        Toast.makeText(AdminCrear.this, "Intentalo nuevamente", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }else{
                Toast.makeText(AdminCrear.this, "Completa todos los datos", Toast.LENGTH_SHORT).show();
            }
        });

        btnPelicula1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandera1 = true;
                mGetContent.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPelicula2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandera2 = true;
                mGetContent.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            }
        });
        btnPelicula3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bandera3 = true;
                mGetContent.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
            }
        });
    }
}