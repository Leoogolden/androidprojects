package com.example.tp5_goldenberg_wainberg;

public class Peliculas {
    private String Id;
    private String Titulo;
    private int Año;
    private String Poster;

    public Peliculas(String id, String titulo, int año, String poster) {
        Id = id;
        Titulo = titulo;
        Año = año;
        Poster = poster;
    }
    public Peliculas() {
        Id = "";
        Titulo = "";
        Año = 0;
        Poster = "";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public int getAño() {
        return Año;
    }

    public void setAño(int año) {
        Año = año;
    }


    public String getPoster() {
        return Poster;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }
}
