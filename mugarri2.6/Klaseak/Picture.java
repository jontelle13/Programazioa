package Klaseak;

import java.time.LocalDate;

public class Picture {
    private int id;
    private String izena;
    private LocalDate date;
    private String file;
    private int bisitak;
    private int photographerID;

    public Picture(int id, String izena, LocalDate date, String file,int bisitak, int photographerID ) {
        this.id=id;
        this.izena=izena;
        this.date=date;
        this.file=file;
        this.bisitak=bisitak;
        this.photographerID=photographerID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getBisitak() {
        return bisitak;
    }

    public void setBisitak(int bisitak) {
        this.bisitak = bisitak;
    }

    public int getPhotographerID() {
        return photographerID;
    }

    public void setPhotographerID(int photographerID) {
        this.photographerID = photographerID;
    }
}
