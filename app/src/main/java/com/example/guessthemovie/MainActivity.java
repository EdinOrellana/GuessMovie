package com.example.guessthemovie;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.Nullable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Button btnIniciar;

    private ImageView btngoogle;
    private EditText tvUsuario, tvContras;
    private GoogleSignInClient client;

   private FirebaseAuth auth;
    private FirebaseDatabase database1;
   private  GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        auth = FirebaseAuth.getInstance();
        database1 = FirebaseDatabase.getInstance();

        databaseHelper = new DatabaseHelper(this);
        tvUsuario = findViewById(R.id.tvUsuario);
        tvContras = findViewById(R.id.tvContra);
        btnIniciar = findViewById(R.id.btnIniciarSesion);
        btngoogle = findViewById(R.id.btngogle);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    database = databaseHelper.getWritableDatabase();

                    String[] columnas = {DatabaseHelper.COLUMNLOGIN_NAME, DatabaseHelper.COLUMNLOGIN_PASSWORD};
                    String[] parametros = {tvUsuario.getText().toString().trim(), tvContras.getText().toString().trim()};
                    Cursor cursor = database.query(DatabaseHelper.TABLE_LOGIN, columnas, DatabaseHelper.COLUMNLOGIN_NAME + "=? and " + DatabaseHelper.COLUMNLOGIN_PASSWORD + "=?", parametros, null, null, null);
                    if (cursor.moveToFirst()) {
                        String Usuario = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMNLOGIN_NAME));
                        Toast.makeText(MainActivity.this, "Bienvenido " + Usuario, Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(MainActivity.this, PlayerOpciones.class);
                        intent.putExtra("Message_key",Usuario);
                        startActivity(intent);
                    } else {
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
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client= GoogleSignIn.getClient(this,options);

        btngoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = client.getSignInIntent();
                startActivityForResult(intent,1234);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential (account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete (@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = auth.getCurrentUser();
                                    //users.setUserId(user.getUid());
                                    String NombreUsuario=(user.getDisplayName());
                                    //users.setProfile(user.getPhotoUrl().toString());

                                    Toast.makeText(MainActivity.this, "Bienvenido: "+NombreUsuario, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(MainActivity.this, PlayerOpciones.class);
                                    intent.putExtra("Message_key",NombreUsuario);
                                    startActivity(intent);

                                }
                                else{
                                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } catch (ApiException e) {
                Toast.makeText(MainActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            }
        }}
}