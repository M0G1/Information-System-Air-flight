package flight;

import airport.Airport;
import plane.Plane;

import java.time.DateTimeException;
import java.util.Date;

public class Flight {
    private Plane plane;

    private Airport departureAirport;     //Пункт отправления
    private Airport arrivalAirport;       //Пункт прибытия

    private Date dateDeparture;         //дата отправления рейса
    private Date dateArrival;           //дата прибытия рейса


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
        if (dateArrival.before(current))
            throw new DateTimeException(String.format("%s(%s) is before current moment", fieldName, date.toString()));
    }

    private void checkDates(Date dateDeparture, Date dateArrival) throws DateTimeException {
        checkDate(dateDeparture, "dateDeparture");
        checkDate(dateArrival, "dateArrival");
        if (!dateArrival.after(dateDeparture))
            throw new DateTimeException(String.format("dateArrival(%s) is not after dateDeparture(%s)", dateArrival.toString(), dateDeparture.toString()));
    }

//=======================================Constructions===================================

    public Flight(Plane plane,
                  Airport departureAirport, Airport arrivalAirport,
                  Date dateDeparture, Date dateArrival) throws NullPointerException, IllegalArgumentException, DateTimeException {
        checkOnNull(plane, "plane");
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

//=======================================Setters===================================


    public void setPlane(Plane plane) throws NullPointerException {
        checkOnNull(plane, "plane");
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
}
