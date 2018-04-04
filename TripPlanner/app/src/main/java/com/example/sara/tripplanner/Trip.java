package com.example.sara.tripplanner;

/**
 * Created by sara on 22/03/18.
 */

public class Trip {

   private   String name;
   private   String date;
   private   String time;
   private   String note;
   private   String placeFrom;
   private   String placeTo;
   private   String Status;
   private   String key;

    private   String date2;
    private   String time2;
    private   String note2;
    private   String Status2;
    private   String key2;


    public Trip() {
    }

    public Trip(String name,String date,String time,String note,String placeFrom,String placeTo,String Status) {
        this.name = name;
        this.date=date;
        this.time=time;
        this.note=note;
        this.placeFrom=placeFrom;
        this.placeTo=placeTo;
        this.Status=Status;


    }

    public Trip(String name,String date2,String time2,String note2,String Status2) {

        this.name = name;
        this.date2=date2;
        this.time2=time2;
        this.note2=note2;
        this.Status=Status2;


    }


    public String getName() {
        return this.name;
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



    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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


    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }




    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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







}
