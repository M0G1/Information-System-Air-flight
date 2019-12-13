package com.company.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Для работы использовать FlightController.
 * Класс будет хранить все существующие рейсы,
 * осуществлять методы доступа,
 * сохраянять и загружать список рейсов.*/
public class FlightList implements Iterable<Flight>{
    private List<Flight> flightList;
    public  FlightList(){
        flightList = new ArrayList<>();
    }
    public Flight get(int index){
        return flightList.get(index);
    }


    public boolean add(Flight flight){
        return flightList.add(flight);
    }

    public boolean remove(Flight flight){
        return flightList.remove(flight);
    }

    public Flight remove(int index){
        return flightList.remove(index);
    }

    public int size(){
        return flightList.size();
    }

    @Override
    public Iterator<Flight> iterator() {
        return flightList.iterator();
    }


}
