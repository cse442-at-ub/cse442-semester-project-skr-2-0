package com.example.myfirstapp;

import java.io.Serializable;

public class Classinfo implements Serializable {

    private String className;
    private String classRoom;
    private int day;
    private double begin;
    private double over;
    private int mwf;
    private int tt;
    private String note;


    public Classinfo(String className, String classRoom, int day, double begin, double over, int mwf, int tt,String Note){
        this.className = className;this.classRoom = classRoom;this.day = day;this.begin=begin;this.over = over;this.mwf=mwf;this.tt = tt;this.note = Note;
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
    public double getStart(){ return begin; }
    public void setStart( double start){
        begin = start;
    }
    public double getOver(){
        return this.over;
    }
    public void setOver(double over){
        this.over = over;
    }
    public int getMwf(){return this.mwf;}
    public void setMwf(int Mwf){this.mwf = Mwf;}
    public int getTT(){return this.tt;}
    public void setTT(int TT){this.tt = TT;}
    public String getNote(){
        return this.note;
    }
    public void setNote(String Note){
        this.note = Note;
    }

}
