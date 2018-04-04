package com.example.sara.tripplanner;

/**
 * Created by sara on 31/03/18.
 */

public class Trip2way {


    private   String name;
    private   String date;
    private   String time;
    private   String date2;
    private   String time2;
//    private   String status1;
//    private   String statuse2;
//    private   String enable;
    private   String placeFrom;
    private   String placeTo;
    private   String placeFrom2;
    private   String placeTo2;


    public Trip2way(String date2, String time2, String placeForm2, String placeTo2) {
    }

    public Trip2way(String name, String date, String time, String date2, String placeTo) {
        this.name = name;
        this.date=date;
        this.time=time;
        this.date2=date2;
        this.time2=time2;

//,String status1,String statuse2,
//        this.status1=status1;
//        this.statuse2=statuse2;

        this.placeFrom=placeFrom;
        this.placeTo= this.placeTo;
        this.placeFrom2=placeFrom2;
        this.placeTo2=placeTo2;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }


    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getPlaceFrom2() {
        return placeFrom2;
    }

    public void setPlaceFrom2(String placeFrom2) {
        this.placeFrom2 = placeFrom2;
    }


    public String getPlaceTo2() {
        return placeTo2;
    }

    public void setPlaceTo2(String placeTo2) {
        this.placeTo2= placeTo2;
    }


    public String getPlaceFrom() {
        return placeFrom;
    }

    public void setPlaceFrom(String placeFrom) {
        this.placeFrom = placeFrom;
    }


    public String getPlaceTo() {
        return placeTo;
    }

    public void setPlaceTo(String placeTo) {
        this.placeTo = placeTo;
    }



//    public String getStatus1() {
//        return status1;
//    }
//
//    public void setStatus1(String status1) {
//        this.status1 = status1;
//    }
//
//
//    public String getStatus2() {
//        return statuse2;
//    }
//
//    public void setStatus2(String status1) {
//        this.statuse2 = statuse2;
//    }
//
//
//    public String getEnable() {
//        return enable;
//    }
//
//    public void setEnable(String enable) {
//        this.enable = enable;
//    }



}




