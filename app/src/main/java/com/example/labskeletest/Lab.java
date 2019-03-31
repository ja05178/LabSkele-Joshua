package com.example.labskeletest;

import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Lab implements Serializable {

    private String room;
    private String schedule;
    ArrayList <String> softwareList = new <String> ArrayList();
    ArrayList <String> classStart = new <String> ArrayList();
    ArrayList <String> classEnd = new <String> ArrayList();
    ArrayList <String> classDay = new <String> ArrayList();
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

        occupancy = dba.getClassTimes(room);

        occupancy.next();
        while (occupancy.next()) {
            String class_start = occupancy.getString("start_time");
            String class_end = occupancy.getString("end_time");
            String class_day = occupancy.getString("days");
//            if(class_start.contains("p")){
//                class_start
//            }
            classStart.add(class_start);
            classEnd.add(class_end);
            classDay.add(class_day);

        }



    }

    public String getRoom() {
        return room;
    }

    public ArrayList<String> getSoftware() {
        return softwareList;
    }

    public ArrayList<String> getClassStart() { return classStart; }
    public ArrayList<String> getClassEnd() {
        return classEnd;
    }
    public ArrayList<String> getClassDay() {
        return classDay;
    }

    public boolean checkClassInSession(Calendar currentTime){

        int day = currentTime.get(Calendar.DAY_OF_WEEK);
        String day_of_week="";
        switch (day) {
            case Calendar.SUNDAY:
                day_of_week = "S";
                break;
            case Calendar.MONDAY:
                day_of_week = "M";
            case Calendar.TUESDAY:
                day_of_week = "T";
                break;
            case Calendar.WEDNESDAY:
                day_of_week = "W";
                break;
            case Calendar.THURSDAY:
                day_of_week = "R";
                break;
            case Calendar.FRIDAY:
                day_of_week = "F";
                break;
            case Calendar.SATURDAY:
                day_of_week = "S";
                break;
        }
        System.out.println(day_of_week + "DAY OF WEEK");
        for(int i = 0; i < classEnd.size(); i ++){
//            try {
//
//                Date startTime = new SimpleDateFormat("HH:mm:ss").parse(classStart.get(i) +);
//                Calendar calendar1 = Calendar.getInstance();
//                calendar1.setTime(time1);
//
//                String string2 = "14:49:00";
//                Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
//                Calendar calendar2 = Calendar.getInstance();
//                calendar2.setTime(time2);
//                calendar2.add(Calendar.DATE, 1);
//
//                String someRandomTime = "01:00:00";
//                Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
//                Calendar calendar3 = Calendar.getInstance();
//                calendar3.setTime(d);
//                calendar3.add(Calendar.DATE, 1);
//
//                Date x = calendar3.getTime();
//                if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
//                    //checkes whether the current time is between 14:49:00 and 20:11:13.
//                    System.out.println(true);
//                }
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
            System.out.println("class End----: " +classEnd.get(i).toString());
        }
        for(int i = 0; i < classStart.size(); i ++){
            System.out.println("class Start----: " +classStart.get(i).toString());


        }
        for(int i = 0; i < classDay.size(); i ++){
            System.out.println("class Day****: " +classDay.get(i).toString());


        }



        return true;
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
