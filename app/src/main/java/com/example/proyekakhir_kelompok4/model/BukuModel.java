package com.example.proyekakhir_kelompok4.model;

public class BukuModel {

    String judul, penulis, genre, bookurl;

    BukuModel()
    {

    }

    public BukuModel(String judul, String penulis, String genre, String bookurl) {
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.bookurl = bookurl;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBookurl() {
        return bookurl;
    }

    public void setBookurl(String bookurl) {
        this.bookurl = bookurl;
    }
}
