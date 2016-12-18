package com.example.workstation.pdm_se01.model.LocationListModel;

/**
 * Created by Tiago on 15/12/2016.
 */

public class FavLocationModel {
    private String location;
    private int isChecked=0;

    public String getLocation()
    {
        return this.location;

    }
    public void setLocation(String location)
    {
        this.location = location;
    }


    public int getCheck()
    {
        return this.isChecked;
    }
    public void setCheck(int ischecked){
    this.isChecked=ischecked;
    }
}
