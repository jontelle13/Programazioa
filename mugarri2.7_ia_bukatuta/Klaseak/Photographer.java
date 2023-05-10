package Klaseak;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Photographer {
    private String name;
    private int id;
    private boolean awarded;

    public Photographer(int id, String name, boolean awarded) {
        this.id = id;
        this.name = name;
        this.awarded = awarded;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAwarded() {
        return awarded;
    }

    public void setAwarded(boolean awarded) {
        this.awarded = awarded;
    }
}

