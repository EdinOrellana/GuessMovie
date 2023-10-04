package com.example.guessthemovie;

public class Peliculas {
    private String Pelicula;
    private String Pista1;
    private String Pista2;
    private String Pista3;
    private String Respuesta1;
    private String Respuesta2;
    private String Imagen1;
    private String Imagen2;
    private String Imagen3;

    public Peliculas(String pelicula, String pista1, String pista2, String pista3, String respuesta1, String respuesta2, String imagen1, String imagen2, String imagen3) {
        Pelicula = pelicula;
        Pista1 = pista1;
        Pista2 = pista2;
        Pista3 = pista3;
        Respuesta1 = respuesta1;
        Respuesta2 = respuesta2;
        Imagen1 = imagen1;
        Imagen2 = imagen2;
        Imagen3 = imagen3;
    }
    public Peliculas() {

    }

    public Peliculas(String pelicula, String imagen1) {
        Pelicula = pelicula;
        Imagen1 = imagen1;
    }

    public String getPelicula() {
        return Pelicula;
    }

    public void setPelicula(String pelicula) {
        Pelicula = pelicula;
    }

    public String getPista1() {
        return Pista1;
    }

    public void setPista1(String pista1) {
        Pista1 = pista1;
    }

    public String getPista2() {
        return Pista2;
    }

    public void setPista2(String pista2) {
        Pista2 = pista2;
    }

    public String getPista3() {
        return Pista3;
    }

    public void setPista3(String pista3) {
        Pista3 = pista3;
    }

    public String getRespuesta1() {
        return Respuesta1;
    }

    public void setRespuesta1(String respuesta1) {
        Respuesta1 = respuesta1;
    }

    public String getRespuesta2() {
        return Respuesta2;
    }

    public void setRespuesta2(String respuesta2) {
        Respuesta2 = respuesta2;
    }

    public String getImagen3() {
        return Imagen3;
    }

    public void setImagen3(String imagen3) {
        Imagen3 = imagen3;
    }

    public String getImagen1() {
        return Imagen1;
    }

    public void setImagen1(String imagen1) {
        Imagen1 = imagen1;
    }

    public String getImagen2() {
        return Imagen2;
    }

    public void setImagen2(String imagen2) {
        Imagen2 = imagen2;
    }
}
