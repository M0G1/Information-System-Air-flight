package com.company;


import com.company.model.Flight;

public class Main {

    public static void main(String[] args) {
        testFlightPlane();
    }

    public static void testFlightPlane() {
        Flight flight = new Flight(null,
                null, null,
                null, null);
        System.out.println(flight.toString());
    }
}
