package com.example.myfirstapp.ui.home;

class Exam {

    private int month;
    private String cName;
    private int day;
    private int year;

    public Exam(String name, int m, int d, int y){
        cName = name;
        month = m;
        day = d;
        year = y;
    }

    public String toString(){
        return cName + ": " + numToMonth(month) + " " + day + " " + year;
    }
    /*
        //implement if we have time
        public boolean comparator(Exam a, Exam b){
            return true;
        }
    */
    //getter and setters for each of the functions used in "edit page"
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String numToMonth(int i){
        if (i == 1){
            return "Jan";
        }
        if (i == 2){
            return "Feb";
        }
        if (i == 3){
            return "Mar";
        }
        if (i == 4){
            return "Apr";
        }
        if (i == 5){
            return "May";
        }
        if (i == 6){
            return "Jun";
        }
        if (i == 7){
            return "Jul";
        }
        if (i == 8){
            return "Aug";
        }
        if (i == 9){
            return "Sep";
        }
        if (i == 10){
            return "Oct";
        }
        if (i == 11){
            return "Nov";
        }
        if (i == 12){
            return "Dec";
        }
        return "";
    }
}
