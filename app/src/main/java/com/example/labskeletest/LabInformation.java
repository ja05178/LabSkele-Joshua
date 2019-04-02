package com.example.labskeletest;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.example.labskeletest.MainActivity.listOfLabs;

public class LabInformation extends AppCompatActivity {
    private ExpandableListView listView;
    private ExpandableListLabInfoAdapater listAdapter;
    private ArrayList<String> listBuildingHeader;
    private HashMap<String, List<String>> listHashMap;
    private String passedLab;
    private int inUseComputers;
    private int totalComputers;
    private int presetTotalComputers;
    private Lab lab;
    //DB TEST
    DBConfiguration dbc = new DBConfiguration();
    DBAccess db = new DBAccess();
    ResultSet computersRS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_information);
        Intent i = getIntent();
        passedLab = i.getStringExtra("labClicked");
        listView = findViewById(R.id.listViewLabInfo);
        lab = (Lab) i.getSerializableExtra("object");

        loadLabOccupancy();

        initData();
        listAdapter = new ExpandableListLabInfoAdapater(this, listBuildingHeader, listHashMap, lab);
        listView.setAdapter(listAdapter);

    }
    public void initData(){

        listHashMap = new HashMap<String, java.util.List<String>>();
        listBuildingHeader = new ArrayList<>();

        listBuildingHeader.add("Lab " + passedLab);
        ArrayList<String> listLabHeader = populateLabHeader();
        listHashMap.put(listBuildingHeader.get(0),listLabHeader);

        listBuildingHeader.add("Occupancy");
        ArrayList<String> listOccupancy = populateLabList(listBuildingHeader.get(1));
        listHashMap.put(listBuildingHeader.get(1),listOccupancy);

        listBuildingHeader.add("Printer:");
        ArrayList<String> listPrinter = populatePrinter();
        listHashMap.put(listBuildingHeader.get(2),listPrinter);

        listBuildingHeader.add("Class Hours:");
        ArrayList<String> listOfHours = populateHours();
        listHashMap.put(listBuildingHeader.get(3), listOfHours);

        listBuildingHeader.add("Software Available:");
        ArrayList<String> listOfSoftware = populateSoftware();
        listHashMap.put(listBuildingHeader.get(4), listOfSoftware);
    }
    public ArrayList<String> populateSoftware( ){

        ArrayList<String> softwareList = lab.getSoftware();
        return softwareList;
    }
    public ArrayList<String> populateHours( ){
        ArrayList<String> listOfHours = new ArrayList<>();
        ArrayList<String> listOfStart = lab.getClassStart();
        ArrayList<String> listOfEnd = lab.getClassEnd();
        ArrayList<String> listOfDay = lab.getClassDay();
        Calendar currentTime = Calendar.getInstance();
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
        for(int i =0; i < listOfStart.size();i++){
            if(listOfDay.get(i).contains(day_of_week)){
                listOfHours.add(listOfStart.get(i) + " - "+ listOfEnd.get(i));
            }
        }
        return listOfHours;
    }
    public ArrayList<String> populateLabHeader( ){
        ArrayList<String> listOfHeader = new ArrayList<String>();
        listOfHeader.add("The IT Building opens at 8:00 am and closes at 12:00 Midnight. Call 111-222-3333 for questions.");
        return listOfHeader;
    }
    public ArrayList<String> populatePrinter( ){
        ArrayList<String> listOfPrinter = new ArrayList<String>();
        listOfPrinter.add("This lab has a double sided printer!");
        return listOfPrinter;
    }
    public ArrayList<String> populateLabList(String building){
        ArrayList<String> listOfLabs = new ArrayList<String>();
        String a = Integer.toString(inUseComputers);
        String b = Integer.toString(totalComputers);
        String c = "In Use: " + a + "/" + b;
        listOfLabs.add(c);
        return listOfLabs;
    }
    public void loadLabOccupancy() {
        inUseComputers = lab.getInUseComputers();
        presetTotalComputers = lab.getPresetTotalComputers();
        totalComputers = lab.getTotalComputers();
    }
}