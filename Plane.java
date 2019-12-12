package com.company.model;

import com.company.model.Aircompany;

import java.io.Serializable;
import java.util.UUID;
import java.util.ArrayList;

public class Plane implements Serializable {
    //Class's fields

    private UUID ID;
    private double maxFlightLength;
    private int maxCapacityLuxe;
    private int maxCapacityEconomy;
    private UUID AIRCOMPANY_ID;
    private static ArrayList <Plane> planes = new ArrayList<>();

    // Checks

    public void checkOnValidCapacityLuxe(int maxCapacityLuxe) {
        if (maxCapacityLuxe <= 0 | maxCapacityLuxe > 100) {
            throw new IllegalArgumentException("Enter valid number (0-99)");
        }
    }

    public void checkOnValidFlightLength(double maxFlightLength) {
        if (maxFlightLength <= 2000.0 | maxFlightLength > 17000.0) {
            throw new IllegalArgumentException("Enter valid number (2000.0 - 17000.0)");
        }
    }

    public void checkOnValidCapacityEconomy(int maxCapacityEconomy) {
        if (maxCapacityEconomy <= 0 | maxCapacityEconomy > 400) {
            throw new IllegalArgumentException("Enter valid number (0-399)");
        }
    }

    // Constructor

    public Plane (double maxFlightLength, int maxCapacityEconomy,
                  int maxCapacityLuxe) {
        checkOnValidCapacityLuxe(maxCapacityLuxe);
        checkOnValidCapacityEconomy(maxCapacityEconomy);
        checkOnValidFlightLength(maxFlightLength);
        this.ID = UUID.randomUUID();
        this.maxFlightLength = maxFlightLength;
        this.maxCapacityLuxe = maxCapacityLuxe;
        this.maxCapacityEconomy = maxCapacityEconomy;
        planes.add(this);
    }

   // Setters and getters

    public double getMaxFlightLength() { return maxFlightLength; }

    public static ArrayList<Plane> getPlanes() { return planes; }

    public int getMaxCapacityEconomy() { return maxCapacityEconomy; }

    public int getMaxCapacityLuxe() { return maxCapacityLuxe; }

    public UUID getID() {
        return ID;
    }

    public UUID getAIRCOMPANY_ID() {
        return AIRCOMPANY_ID;
    }

    public void setMaxCapacityEconomy(int maxCapacityEconomy) {
        checkOnValidCapacityEconomy(maxCapacityEconomy);
        this.maxCapacityEconomy = maxCapacityEconomy;
    }

    public void setMaxCapacityLuxe(int maxCapacityLuxe) {
        checkOnValidCapacityLuxe(maxCapacityLuxe);
        this.maxCapacityLuxe = maxCapacityLuxe;
    }

    public void setMaxFlightLength(double maxFlightLength) {
        checkOnValidFlightLength(maxFlightLength);
        this.maxFlightLength = maxFlightLength;
    }
}


