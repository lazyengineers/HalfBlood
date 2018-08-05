package com.lazyengineers.dell1.halfblood;

public class Data {
private String name;
    private String email;
    private String password;
    private String bloodGroup;
    public String getName() {
        return name;
    }
    public  String message;
    public String time;
    public String ID;
    public String uniqueName;
    public Data() {
    }

//    public Data(String ID,String message, String time) {
//        this.ID=ID;
//        this.message = message;
//        this.time = time;
//    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
