package Klaseak;

import java.time.LocalDate;

public class Picture {
    private int pictureId;
    private String title;
    private LocalDate date_;
    private String file_;
    private int visist;
    private int photographerID;

    public Picture(int pictureid, String title, LocalDate date_, String file_, int visist, int photographerID) {
        this.pictureId = pictureid;
        this.title = title;
        this.date_ = date_;
        this.file_ = file_;
        this.visist = visist;
        this.photographerID = photographerID;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate_() {
        return date_;
    }

    public void setDate_(LocalDate date_) {
        this.date_ = date_;
    }

    public String getFile_() {
        return file_;
    }

    public void setFile_(String file_) {
        this.file_ = file_;
    }

    public int getVisist() {
        return visist;
    }

    public void setVisist(int visist) {
        this.visist = visist;
    }

    public int getPhotographerID() {
        return photographerID;
    }

    public void setPhotographerID(int photographerID) {
        this.photographerID = photographerID;
    }
}
