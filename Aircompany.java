package com.company.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Aircompany implements Serializable {
    private UUID ID;
    private String name;
    private ArrayList<Plane> planes = new ArrayList<>();
    private static ArrayList<Aircompany> aircompanies = new ArrayList<>();

    public void checkOnValidName (String name) {
        if (name.length() < 2 | name.length() > 20) {
            throw new IllegalArgumentException("Enter valid company name");
        }
    }

    public void checkOnValidList (ArrayList<Plane> planes) {
        if (planes.size() < 1) {
            throw new IllegalArgumentException("Air company can't consist of two or less planes");
        }
    }

    public Aircompany (String name, ArrayList<Plane> planes) {
        checkOnValidName(name);
        checkOnValidList(planes);
        this.ID = UUID.randomUUID();
        this.name = name;
        this.planes = planes;
        aircompanies.add(this);
    }

    public UUID getID() {
        return ID;
    }

    public void setPlanes(ArrayList<Plane> planes) {
        checkOnValidList(planes);
        this.planes = planes;
    }

    public ArrayList<Plane> getPlanes() {
        return planes;
    }

    public void setName(String name) {
        checkOnValidName(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Aircompany> getAircompanies() { return aircompanies; }
}
