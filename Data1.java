package com.lazyengineers.dell1.halfblood;

/**
 * Created by dell1 on 12/22/2017.
 */
public class Data1 {
public  String message;
    public String time;
    public String ID;
public String name;
    public Data1() {
    }

    public Data1(String ID,String message, String time,String name) {
        this.ID=ID;
        this.message = message;
        this.time = time;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
