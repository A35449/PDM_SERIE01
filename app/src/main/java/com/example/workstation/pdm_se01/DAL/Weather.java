package com.example.workstation.pdm_se01.DAL;

/**
 * Created by workstation on 25/10/2016.
 */

public class Weather {
    private int id;
    private String main;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String description;
    private String icon;
}
