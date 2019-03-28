package com.example.labskeletest;

import java.util.ArrayList;

public class Building {

    private String bldgName;
    private String address;
    private String phoneNumber;
    private int  noOfLabs;
    private boolean isOneLabFree;
    private double lat;
    private double lng;
    ArrayList<Lab> labs;



    //private Building IT_BLDG = new Building("Dept of Information Technology\n(CEIT)" , "P.O. Box 8150 Statesboro, GA 30460" , "(912) 478-4848" , 14 , true ,32.423297, -81.786482 );
//    private  Building COBA = new Building("Parker College of Business\n(COBA)" , "621 C.O.B.A. Dr, Statesboro, GA 30460" , "(912) 478-2622" ,3 , true , 32.422637,  -81.786070);
  //  private  Building ENGR_BLDG = new Building("Engineering Building\n(MENG)" , "847 Plant Dr Statesboro GA 30460 Plant Dr, Statesboro, GA 30460" , "(912) 478-7412" ,4 , true , 32.421791, -81.785665);
  //  private  Building COE = new Building("College of Education\n(COE)" , "275 C.O.E. Drive, Statesboro, GA 30460" , "(912) 478-5648" ,5 , true , 32.422085, -81.788318);



    Building(){}

    Building(String name , String address , String phoneNumber , int noOfLabs , boolean isOneLabFree ,ArrayList<Lab> listOflabs  , double lat , double lng){
        bldgName = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.noOfLabs = noOfLabs;
        this.isOneLabFree = isOneLabFree;
        this.lat = lat;
        this.lng = lng;
        labs = listOflabs;
    }
    Building(String name , String address , String phoneNumber , int noOfLabs , boolean isOneLabFree , double lat , double lng){
        bldgName = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.noOfLabs = noOfLabs;
        this.isOneLabFree = isOneLabFree;
        this.lat = lat;
        this.lng = lng;
    }

    public  double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBldgName() {
        return bldgName;
    }

    public void setBldgName(String bldgName) {
        this.bldgName = bldgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNoOfLabs() {
        return noOfLabs;
    }

    public void setNoOfLabs(int noOfLabs) {
        this.noOfLabs = noOfLabs;
    }

    public boolean isOneLabFree() {
        return isOneLabFree;
    }

    public void setOneLabFree(boolean oneLabFree) {
        isOneLabFree = oneLabFree;
    }



}
