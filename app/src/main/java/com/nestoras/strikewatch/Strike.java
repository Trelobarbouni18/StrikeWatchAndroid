package com.nestoras.strikewatch;


import java.io.Serializable;

public class Strike implements Serializable, Comparable {

    private String name;
    private String date;
    private String hour;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    private String Location;

    public Strike(String name, String date, String hour, String location) {
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.Location = location;

    }


    public void setLocation(String location) {
        Location = location;
    }

    public String getLocation() {
        return Location;
    }

    @Override
    public int compareTo(Object o) {
        Strike otherStrike = (Strike)o;
        return (this.date.compareTo(otherStrike.date));
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
}
