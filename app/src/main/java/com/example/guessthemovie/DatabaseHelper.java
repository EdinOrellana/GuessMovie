package com.example.guessthemovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "GuessTheMovie.db";
    public static final String TABLE_LOGIN = "Login";
    public static final String COLUMNLOGIN_ID = "idUsuarioc";
    public static final String COLUMNLOGIN_NAME = "Nombre";
    public static final String COLUMNLOGIN_PASSWORD="Password";
    public static final String TABLE_ADIVINANZA = "Adivinanza";
    public static final String COLUMNADIVINANZA_ID="IdAdivinanza";

    public static final String COLUMNADIVINANZA_PELICULA="Pelicula";
    public static final String COLUMNADIVINANZA_IMAGEN="Imagen";

    public static final String COLUMNADIVINANZA_PISTA1="Pista1";
    public static final String COLUMNADIVINANZA_PISTA2="Pista2";
    public static final String COLUMNADIVINANZA_PISTA3="Pista3";

    public static final String COLUMNADIVINANZA_RESPUESTA1="Respuesta1";
    public static final String COLUMNADIVINANZA_RESPUESTA2="Respuesta2";

    public static final String COLUMNADIVINANZA_IMAGEN1="Imagen1";
    public static final String COLUMNADIVINANZA_IMAGEN2="Imagen2";
    public static final String COLUMNADIVINANZA_NUBE="Nube";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String query = "CREATE TABLE " + TABLE_LOGIN + " (" +
                    COLUMNLOGIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMNLOGIN_NAME + " TEXT, "+
                    COLUMNLOGIN_PASSWORD+" TEXT);";
            db.execSQL(query);
            String query1= "INSERT INTO "+TABLE_LOGIN+" ("+COLUMNLOGIN_NAME+","+COLUMNLOGIN_PASSWORD+") VALUES('Edin','Edin');";
            db.execSQL(query1);
            String query3= " CREATE TABLE  "+ TABLE_ADIVINANZA +" ( "+
                    COLUMNADIVINANZA_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMNADIVINANZA_PELICULA + " TEXT, "+
                    COLUMNADIVINANZA_IMAGEN+" TEXT,"+
                    COLUMNADIVINANZA_PISTA1 + " TEXT, "+
                    COLUMNADIVINANZA_PISTA2 + " TEXT, "+
                    COLUMNADIVINANZA_PISTA3 + " TEXT, "+
                    COLUMNADIVINANZA_RESPUESTA1 + " TEXT, "+
                    COLUMNADIVINANZA_RESPUESTA2 + " TEXT, "+
                    COLUMNADIVINANZA_IMAGEN1 + " TEXT, "+
                    COLUMNADIVINANZA_NUBE + " INTEGER, "+
                    COLUMNADIVINANZA_IMAGEN2 + " TEXT);";
            db.execSQL(query3);
        }catch (Exception e){
            Log.d( "someOtherMethod()", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADIVINANZA);
        onCreate(db);
    }
}
