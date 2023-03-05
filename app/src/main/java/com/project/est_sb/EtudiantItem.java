package com.project.est_sb;

public class EtudiantItem {
    private String name ;
    private String status ;
    private String roll;

    public EtudiantItem(String R , String  N){
        name= N ; roll  = R ; status = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
