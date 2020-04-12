package com.example.myfirstapp;

import java.io.Serializable;

public class Classinfo implements Serializable {

    private String className;
    private String classRoom;
    private int day;
    private double start;
    private double over;
    private boolean mwf;
    private boolean tt;


    public Classinfo(String className, String classRoom,int day,double start,double over,boolean mwf,boolean tt){
        this.className = className;this.classRoom = classRoom;this.day = day;this.over = over;this.mwf=mwf;this.tt = tt;
    }

    public String getClassName(){
        return this.className;
    }
    public void setClassName(String classname){
        this.className = classname;
    }
    public String getClassRoom(){
        return this.classRoom;
    }
    public void setClassRoom(String classroom){
        this.classRoom = classroom;
    }
    public int getDay(){
        return this.day;
    }
    public void setDay( int day){
        this.day = day;
    }
    public double getStart(){
        return this.start;
    }
    public void setStart( double start){
        this.start = start;
    }
    public double getOver(){
        return this.over;
    }
    public void setOver(double over){
        this.over = over;
    }
    public boolean getMwf(){return this.mwf;}
    public void setMwf(boolean Mwf){this.mwf = Mwf;}
    public boolean getTT(){return this.tt;}
    public void setTT(boolean TT){this.tt = TT;}

}
