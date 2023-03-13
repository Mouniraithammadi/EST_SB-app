package com.project.est_sb;

public class EtudiantItem {
    private String name ;
    private String status ;
    private int roll;
    private long e_id;

    public EtudiantItem( long id ,int  R , String  N){
        name= N ; roll  = R ; status = "";
        e_id = id;
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

    public int getRoll() {
        return roll;
    }

    public void setRoll(int  roll) {
        this.roll = roll;
    }

    public long getE_id() {
        return e_id;
    }

    public void setE_id(long e_id) {
        this.e_id = e_id;
    }
}
