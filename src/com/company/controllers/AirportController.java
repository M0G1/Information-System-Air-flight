package com.company.controllers;

import com.company.model.Airport;
import com.company.model.AirportList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AirportController {
    private static AirportList list = AirportList.getInstance();

    public static Airport createAirport(String airportName) {
        Airport airport = new Airport(airportName);
        list.add(airport);
        return airport;
    }

    //======================================= Delete/Remove ===================================

    public static boolean removeAirport(Airport airport) {
        return list.remove(airport);
    }

    public static Airport removeAirport(int index) {
        return list.remove(index);
    }


    //=======================================Getters===================================

    public static AirportList getList() {
        return list;
    }

    public static List<Airport> getIf(Predicate<Airport> predicate) {
        ArrayList<Airport> answer = new ArrayList<>();
        for (Airport airport : list) {
            if (predicate.test(airport))
                answer.add(airport);
        }
        return answer;
    }

    public static Airport find(String airportName){
        for(Airport airport: list){
            if(airport.getName().toLowerCase().equals(airportName.toLowerCase())){
                return airport;
            }
        }
        return null;
    }
}
