package com.example.labskeletest;

public class Computer {

    /*This class is for the Computer Object*/

    private String ip;
    private String host;
    private Boolean occupied;


    Computer(){}
    Computer(String ip , String host , Boolean occupied){
        this.ip = ip;
        this.host = host;
        this.occupied = occupied;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }



}
