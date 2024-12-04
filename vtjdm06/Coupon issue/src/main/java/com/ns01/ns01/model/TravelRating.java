package com.ns01.ns01.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TravelRating {

    @Id
    @GeneratedValue
    int id;
    String dest1;
    String dest2;
    double rating;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDest1() {
        return dest1;
    }
    public void setDest1(String dest1) {
        this.dest1 = dest1;
    }
    public String getDest2() {
        return dest2;
    }
    public void setDest2(String dest2) {
        this.dest2 = dest2;
    }
    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    
    
}
