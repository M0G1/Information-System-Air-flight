package com.company.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class Flight implements Serializable {
    private Plane plane;

    private Airport departureAirport;     //Пункт отправления
    private Airport arrivalAirport;       //Пункт прибытия

    private Date dateDeparture;         //дата отправления рейса
    private Date dateArrival;           //дата прибытия рейса

    private UUID id;                    //уникальный индендификатор

//=======================================Checks===================================

    private void checkOnNull(Object obj, String fieldName) throws NullPointerException {
        if (obj == null)
            throw new NullPointerException(fieldName + "  is null");
    }


    private void checkAirportEquals(Airport departureAirport, Airport arrivalAirport) throws IllegalArgumentException {
        if (departureAirport.equals(arrivalAirport))
            throw new IllegalArgumentException(String.format("departureAirport(%s) and arrivalAirport(%s) are equals", departureAirport.toString(), arrivalAirport.toString()));
    }

    private void checkDate(Date date, String fieldName) throws DateTimeException {
        Date current = new Date(System.currentTimeMillis());
        if (date.before(current))
            throw new DateTimeException(String.format("%s(%s) is before current moment", fieldName, date.toString()));
    }

    private void checkDates(Date dateDeparture, Date dateArrival) throws DateTimeException {
        //ЗАККОМЕНТИРОВАЛ ПРОВЕРКУ НА
        //checkDate(dateDeparture, "dateDeparture");
        //checkDate(dateArrival, "dateArrival");
        if (!dateArrival.after(dateDeparture))
            throw new DateTimeException(String.format("dateArrival(%s) is not after dateDeparture(%s)", dateArrival.toString(), dateDeparture.toString()));
    }

//=======================================Constructions===================================

    public Flight(Plane plane,
                  Airport departureAirport, Airport arrivalAirport,
                  Date dateDeparture, Date dateArrival) throws NullPointerException, IllegalArgumentException, DateTimeException {
        checkOnNull(plane, "com/company/plane");
        checkOnNull(departureAirport, "departureAirport");
        checkOnNull(arrivalAirport, "arrivalAirport");
        checkOnNull(dateDeparture, "dateDeparture");
        checkOnNull(dateArrival, "dateArrival");
        checkAirportEquals(departureAirport, arrivalAirport);
        checkDates(dateDeparture, dateArrival);

        this.plane = plane;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.dateDeparture = dateDeparture;
        this.dateArrival = dateArrival;
        this.id = UUID.randomUUID();
    }

//=======================================Getters===================================

    public Plane getPlane() {
        return plane;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public Date getDateDeparture() {
        return dateDeparture;
    }

    public Date getDateArrival() {
        return dateArrival;
    }

    public UUID getId() {
        return id;
    }

    //=======================================Setters===================================


    public void setPlane(Plane plane) throws NullPointerException {
        checkOnNull(plane, "com/company/plane");
        this.plane = plane;
    }

    public void setDepartureAirport(Airport departureAirport) throws NullPointerException, IllegalArgumentException {
        checkOnNull(departureAirport, "departureAirport");
        checkAirportEquals(this.arrivalAirport, departureAirport);
        this.departureAirport = departureAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) throws NullPointerException, IllegalArgumentException {
        checkOnNull(arrivalAirport, "arrivalAirport");
        checkAirportEquals(this.departureAirport, arrivalAirport);
        this.arrivalAirport = arrivalAirport;
    }


    public void setDateDeparture(Date dateDeparture) throws NullPointerException, DateTimeException {
        checkOnNull(dateDeparture, "dateDeparture");
        checkDates(dateDeparture, this.dateArrival);
        this.dateDeparture = dateDeparture;
    }

    public void setDateArrival(Date dateArrival) throws NullPointerException, DateTimeException {
        checkOnNull(dateArrival, "dateArrival");
        checkDates(this.dateDeparture, dateArrival);
        this.dateArrival = dateArrival;
    }


//=======================================Object===================================


    @Override
    public int hashCode() {
        int hash = 31;
        Long[] longID = {
                this.id.getLeastSignificantBits(),
                this.id.getMostSignificantBits()
        };
        Date[] dates = {
                this.dateDeparture,
                this.dateArrival
        };
        Airport[] airports = {
                this.departureAirport,
                this.arrivalAirport
        };
        Object[] fields = {longID, dates, airports};
        hash += this.plane.hashCode();
        hash += Arrays.deepHashCode(fields);
        hash += Arrays.hashCode(dates);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flight))
            return false;
        Flight flight = (Flight) obj;
        //объект равен, если все его атрибуты равны. Начнем с id потому что это уникальное значение
        return (flight.id.compareTo(this.id) == 0) &&
                flight.plane.equals(this.plane) &&
                flight.departureAirport.equals(this.departureAirport) &&
                flight.arrivalAirport.equals(this.arrivalAirport) &&
                flight.dateDeparture.equals(this.dateDeparture) &&
                flight.dateArrival.equals(this.dateArrival);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Flight obj = (Flight) super.clone();
//        obj.plane = (Plane) this.plane.clone();
//        obj.departureAirport = (Airport)this.departureAirport.clone();
//        obj.arrivalAirport = (Airport)this.arrivalAirport.clone();
        obj.dateDeparture = (Date) this.dateDeparture.clone();
        obj.dateArrival = (Date) this.dateArrival.clone();
        //Нужен ли новый id для клона? Или этот сойдет?
        obj.id = UUID.fromString(this.id.toString());
        return obj;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Flight( ");
        //Приводим к строковому представлению все поля кроме id
        Object[] fields = {this.plane,
                this.departureAirport, this.arrivalAirport,
                this.dateDeparture, this.dateArrival
        };
        for (int i = 0; i < fields.length; ++i) {
            str.append(fields[i].toString());
            if (i != fields.length - 1)
                str.append(", ");
        }
        str.append(')');
        return str.toString();
    }
}
