package com.company.controllers;

import com.company.model.Airport;
import com.company.model.Flight;
import com.company.model.FlightList;
import com.company.model.Plane;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class FlightController {
    /*Нужно будет реализовать синглетон для листа и контроллера*/
    private static FlightList list;

    /*Статический блок иницилизации для создания списка рейсов. Для корректной работы.
     * Добавлю потом чтение из файла(БД мб потом) */
    static {
        list = new FlightList();
    }


    public static Flight createFlight(Plane plane,
                                      Airport departureAirport, Airport arrivalAirport,
                                      Date dateDeparture, Date dateArrival) {
        Flight temp = new Flight(plane,
                departureAirport, arrivalAirport,
                dateDeparture, dateArrival);
        list.add(temp);
        return temp;
    }

//=======================================Getters===================================

    /**
     * Предикат - функция ставящая набору целочисленных аргументов булевское значение.
     * Проход по списку с выбором элементов по условию преликата.
     * @return список с элементами удовлетворяющими предиату
     */
    public static List<Flight> getIf(Predicate<Flight> predicate) {
        ArrayList<Flight> answer = new ArrayList<>();
        for (Flight flight : list) {
            if (predicate.test(flight))
                answer.add(flight);
        }
        return answer;
    }

    /**
     * @return список рейсов, которые будут
     * совершаться после определенного времени.
     * Сравнивается по времени вылета.
     */
    public static List<Flight> getFlightsAfterDepartureDate(Date dateDeparture) {
        /*Анонимный класс (описывает абстрактный класс или полностью абстрактный класс(интерфейс)
          со всеми его методами без создания его в отдельном файле(для количества переопределяемых методов больше чем 1))*/
        Predicate<Flight> predicate = new Predicate<Flight>() {
            @Override
            public boolean test(Flight flight) {
                return flight.getDateDeparture().after(dateDeparture);
            }
        };
        return getIf(predicate);
    }

    /**
     * @return список рейсов, которые будут
     * совершаться после определенного времени,
     * вылетать из определенного аэропорта.
     * Сравнивается по времени вылета и аэропорту вылета.
     */
    public static List<Flight> getFlightsAfterDepartureDate(Date dateDeparture, Airport departureAirport) {
        //лямбда функции(когда в интерфейсе надо переопределить один метод, то так проще(меньше кода))
        //добавить рейс, если дата вылета после ОПРЕДЕЛЕННОЙ даты и аэропорт вылета равен Запрашиваему
        Predicate<Flight> predicate = (flight) ->
                flight.getDateDeparture().after(dateDeparture) && flight.getDepartureAirport().equals(departureAirport);
        return getIf(predicate);
    }

    /**
     * @return список рейсов, которые будут
     * совершаться после определенного времени,
     * вылетать из определенного аэропорта,
     * прилетать в определенный аэропорт.
     * Сравнивается по времени вылета и аэропорту вылета и назначения.
     */
    public static List<Flight> getFlightsAfterDepartureDate(Date dateDeparture, Airport departureAirport, Airport arrivalAirport) {
        //лямбда функции(когда в интерфейсе надо переопределить один метод, то так проще(меньше кода))
        /*добавить рейс, если дата вылета после ОПРЕДЕЛЕННОЙ даты и
              аэропорт вылета равен Запрашиваему аэропорту вылета
              аэропорт назначения равен Запрашивему аэропорту назначения*/
        Predicate<Flight> predicate = (flight) -> flight.getDateDeparture().after(dateDeparture) &&
                flight.getDepartureAirport().equals(departureAirport) &&
                flight.getArrivalAirport().equals(arrivalAirport);
        return getIf(predicate);
    }

    /**
     * Возвращает список рейсов, которые будут
     * завершены до определенного времени.
     * Сравнивается по времени прилета.
     */
    public static List<Flight> getBeforeArrivalDate(Date dateArrival) {
        //лямбда функции(когда в интерфейсе надо переопределить один метод, то так проще(меньше кода))
        //добавить рейс, если дата прилета до ОПРЕДЕЛЕННОЙ даты
        Predicate<Flight> predicate = (flight) -> flight.getDateArrival().before(dateArrival);
        return getIf(predicate);
    }

    /**
     *@return  список рейсов, которые будут
     * совершены до определенного времени,
     * прилетев в определенный аэропорт.
     * Сравнивается по времени вылета и аэропорту назначения.
     */
    public static List<Flight> getBeforeArrivalDate(Date dateArrival, Airport arrivalAirport) {
        //лямбда функции(когда в интерфейсе надо переопределить один метод, то так проще(меньше кода))
        /*добавить рейс, если дата вылета после ОПРЕДЕЛЕННОЙ даты и
              аэропорт вылета равен Запрашиваему аэропорту вылета
              аэропорт назначения равен Запрашивему аэропорту назначения*/
        Predicate<Flight> predicate = (flight) ->
                flight.getDateArrival().before(dateArrival) && flight.getArrivalAirport().equals(arrivalAirport);
        return getIf(predicate);
    }

    /**
     *@return Возвращает список рейсов, которые будут
     * совершены до определенного времени,
     * вылетев из определенного аэропорта,
     * прилетев в определенный аэропорт.
     * Сравнивается по времени вылета, аэропорту вылета и назначения.
     */
    public static List<Flight> getBeforeArrivalDate(Date dateArrival, Airport departureAirport, Airport arrivalAirport) {
        //лямбда функции(когда в интерфейсе надо переопределить один метод, то так проще(меньше кода))
        /*добавить рейс, если дата прилета до ОПРЕДЕЛЕННОЙ даты и
              аэропорт вылета равен Запрашиваему аэропорту вылета и
              аэропорт назначения равен Запрашивему аэропорту назначения*/
        Predicate<Flight> predicate = (flight) ->
                flight.getDateArrival().before(dateArrival) &&
                        flight.getArrivalAirport().equals(arrivalAirport) &&
                        flight.getDepartureAirport().equals(departureAirport);
        return getIf(predicate);
    }

    /**
     * @return Возвращает список рейсов, которые будут
     * совершены до определенного времени,
     * вылетев из определенного аэропорта,
     * прилетев в определенный аэропорт.
     * Сравнивается по времени вылета, аэропорту вылета и назначения.
     */
    public static List<Flight> getByDatesAirports(Date dateDeparture, Date dateArrival, Airport departureAirport, Airport arrivalAirport) {
        //лямбда функции(когда в интерфейсе надо переопределить один метод, то так проще(меньше кода))
        /*добавить рейс, если дата вылета после ОПРЕДЕЛЕННОЙ дата и
        прилета до ОПРЕДЕЛЕННОЙ даты и
        аэропорт вылета равен Запрашиваему аэропорту вылета и
        аэропорт назначения равен Запрашивему аэропорту назначения*/
        Predicate<Flight> predicate = (flight) ->
                flight.getDateDeparture().after(dateDeparture) && flight.getDateArrival().before(dateArrival) &&
                        flight.getArrivalAirport().equals(arrivalAirport) && flight.getDepartureAirport().equals(departureAirport);
        return getIf(predicate);
    }
}
