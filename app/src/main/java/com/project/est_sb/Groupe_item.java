package com.project.est_sb;

public class Groupe_item {
    String group ;
    String sujet ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    long id ;

    public Groupe_item(String g , String s) {
    sujet = s; group = g ;
    }
    public Groupe_item(long id,String g , String s  ) {
        sujet = s; group = g ; this.id = id ;
    }

    public String getGroup() {
        return group;
    }

    public String getSujet() {
        return sujet;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }
}
