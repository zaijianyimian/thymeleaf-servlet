package com.test.beana;

public class Students {
    private int uid;
    private String uname;
    private String gender;
    private int uage;
    public Students() {
    }
    public Students(int uid, String uname, String gender, int uage) {
        this.uid = uid;
        this.uname = uname;
        this.gender = gender;
        this.uage = uage;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUage() {
        return uage;
    }

    public void setUage(int uage) {
        this.uage = uage;
    }
}
