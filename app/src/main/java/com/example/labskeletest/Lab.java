package com.example.labskeletest;

import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Lab implements Serializable {

    private String room;
    private String schedule;
    ArrayList <String> softwareList = new <String> ArrayList();
    private int inUseComputers;
    private int totalComputers;
    private int presetTotalComputers;



    Lab(String labRoom){
        room = labRoom;

        try {
            getItemStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getItemStatus() throws SQLException {

        DBAccess dba = new DBAccess();
        ResultSet occupancy = dba.getOccupancy(room);

        occupancy.next();
        inUseComputers = occupancy.getInt("InUse");

        occupancy = dba.getTotalComputers(room);

        occupancy.next();
        totalComputers = occupancy.getInt("Total");

        occupancy = dba.getPreSetTotalComputers(room);

        occupancy.next();
        presetTotalComputers = occupancy.getInt("Total");

        occupancy = dba.getSoftware(room);

        occupancy.next();
        while (occupancy.next()) {
            String software = occupancy.getString("Name");
            softwareList.add(software);
        }
    }

    public String getRoom() {
        return room;
    }

    public ArrayList<String> getSoftware() {
        return softwareList;
    }

    public String getPercentage() {

        String percentage = inUseComputers + "/" + totalComputers;
        return percentage;
    }

    public int getInUseComputers(){return inUseComputers;}
    public int getTotalComputers(){return totalComputers;}
    public int getPresetTotalComputers(){return presetTotalComputers;}

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }



}
