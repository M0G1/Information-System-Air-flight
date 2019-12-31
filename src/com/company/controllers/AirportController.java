package com.company.controllers;

import com.company.model.Airport;
import com.company.model.AirportList;
import com.company.model.Flight;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Date;
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
        ArrayList<Airport> answer = new ArrayList<>(0);
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

    public static boolean isAirportHaveFlight(Airport airport){
        if(airport == null)
            return false;
        Date curDate = new Date(System.currentTimeMillis());
        Predicate<Flight> predicate = (flight) ->   //есть рейс и еще не вылетел из аэропорта. Или же не прилетел.
                (flight.getArrivalAirport().equals(airport) && (curDate.before(flight.getDateDeparture()))) ||
                        (flight.getDepartureAirport().equals(airport) && curDate.before(flight.getDateArrival()));
        List<Flight> lst = FlightController.getIf(predicate);
        return lst.size() != 0;
    }
}

