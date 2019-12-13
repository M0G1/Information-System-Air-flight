package com.company.model;

import java.util.UUID;

public class Airport {
    private String name;
    private UUID id;


//=======================================Object===================================

    public Airport(String AirportName){
        this.name = AirportName;
        this.id = UUID.randomUUID();
    }


//=======================================Getters===================================

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    //=======================================Setters===================================


    public void setName(String name) {
        this.name = name;
    }


//=======================================Object===================================


    @Override
    public int hashCode() {
        return this.id.hashCode() + this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Airport))
            return false;
        Airport airport = (Airport)obj;
        return (this.id.compareTo(airport.id) == 0) && this.name.equals(airport.name);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Airport airport = (Airport) super.clone();
        airport.id = UUID.fromString(this.id.toString());
        return airport;
    }

    @Override
    public String toString() {
        return String.format("Airport '%s'", this.name);
    }
}
