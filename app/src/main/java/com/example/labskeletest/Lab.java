package com.example.labskeletest;

import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Lab implements Serializable {

    private String room;
    private String schedule;
    ArrayList<String> softwareList = new <String>ArrayList();
    ArrayList<String> classStart = new <String>ArrayList();
    ArrayList<String> classEnd = new <String>ArrayList();
    ArrayList<String> classDay = new <String>ArrayList();
    private int inUseComputers;
    private int totalComputers;
    private int presetTotalComputers;
    private boolean printerStatus;


    Lab(String labRoom) {
        room = labRoom;

        try {
            getItemStatus();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getItemStatus() throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Time stamp before: " + timestamp);
        DBAccess dba = new DBAccess();

        ResultSet occupancy = dba.getLabStatus(room);
        occupancy.next();
        inUseComputers = occupancy.getInt("InUse");
        totalComputers = occupancy.getInt("Total");
        presetTotalComputers = occupancy.getInt("PresetTotal");

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

            class_start = formatTime(class_start);
            class_end = formatTime(class_end);

            classStart.add(class_start);
            classEnd.add(class_end);
            classDay.add(class_day);
        }
        timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Time stamp after: " + timestamp);

    }

    public String formatTime(String correctFormat) {
        if (!(correctFormat.length() == 6)) {
            String addZero = "0";
            correctFormat = addZero + correctFormat;
        }
        return correctFormat;
    }

    public String getRoom() {
        return room;
    }

    public ArrayList<String> getSoftware() {
        return softwareList;
    }

    public ArrayList<String> getClassStart() {
        return classStart;
    }

    public ArrayList<String> getClassEnd() {
        return classEnd;
    }

    public ArrayList<String> getClassDay() {
        return classDay;
    }

    public boolean checkClassInSession(Calendar currentTime) {
        int day = currentTime.get(Calendar.DAY_OF_WEEK);
        String day_of_week = "";
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
//        System.out.println(day_of_week + "DAY OF WEEK");
        for (int i = 0; i < classEnd.size(); i++) {
            System.out.println("Day of Week of Class: " + classDay.get(i).toString());
            System.out.println("Current Day: " + day_of_week);
            if (classDay.get(i).contains(day_of_week)) {
                try {
                    System.out.println("Same day, checking times");
                    String trimmedStart = classStart.get(i).substring(0, classStart.get(i).length() - 1);
                    String ampmStart = classStart.get(i).substring(classStart.get(i).length() - 1, classStart.get(i).length());

                    String trimmedEnd = classEnd.get(i).substring(0, classEnd.get(i).length() - 1);
                    String ampmEnd = classEnd.get(i).substring(classEnd.get(i).length() - 1, classEnd.get(i).length());

                    if (ampmStart.equals("p")) {
//                    System.out.println("Before + 12 " + trimmedStart);
                        String firstTwo = trimmedStart.substring(0, 2);
                        int firstTwoNum = Integer.parseInt(firstTwo);
                        if (firstTwoNum != 12) {
                            firstTwoNum = firstTwoNum + 12;
                            String parsed = String.valueOf(firstTwoNum);
                            trimmedStart = parsed + trimmedStart.substring(2);
//                        System.out.println("After + 12 " + trimmedStart);
                        }
                    }
                    if (ampmEnd.equals("p")) {
//                    System.out.println("Before + 12 " + trimmedEnd);
                        String firstTwo = trimmedEnd.substring(0, 2);
                        int firstTwoNum = Integer.parseInt(firstTwo);
                        if (firstTwoNum != 12) {
                            firstTwoNum = firstTwoNum + 12;
                            String parsed = String.valueOf(firstTwoNum);
                            trimmedEnd = parsed + trimmedEnd.substring(2);
//                        System.out.println("After + 12 " + trimmedEnd);
                        }
                    }
                    Date startTime = new SimpleDateFormat("HH:mm").parse(trimmedStart);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(startTime);


                    Date endTime = new SimpleDateFormat("HH:mm").parse(trimmedEnd);
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(endTime);


                    String currentTimeStringTest = new SimpleDateFormat("HH:mm").format(new Date());

                    Date checkTime = new SimpleDateFormat("HH:mm").parse(currentTimeStringTest);
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(checkTime);


                    System.out.println("Start Time: " + trimmedStart);
                    System.out.println("Current Time: " + currentTimeStringTest);
                    System.out.println("End Time: " + trimmedEnd);

                    if (endTime.compareTo(startTime) < 0) {
                        calendar2.add(Calendar.DATE, 1);
                        calendar3.add(Calendar.DATE, 1);
                    }

                    java.util.Date actualTime = calendar3.getTime();
                    if ((actualTime.after(calendar1.getTime()) ||
                            actualTime.compareTo(calendar1.getTime()) == 0) &&
                            actualTime.before(calendar2.getTime())) {
                        System.out.println("Class is in Sessiion");
                        long differenceInMinutes = endTime.getTime() - startTime.getTime();
                        differenceInMinutes= differenceInMinutes/  (60 * 1000) % 60;
                        System.out.println("Class Room open in " + differenceInMinutes + " minutes!");
                        return true;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public String getPercentage() {

        String percentage = inUseComputers + "/" + totalComputers;
        return percentage;
    }

    public int getInUseComputers() {
        return inUseComputers;
    }

    public int getTotalComputers() {
        return totalComputers;
    }

    public int getPresetTotalComputers() {
        return presetTotalComputers;
    }

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
