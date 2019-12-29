package com.company.view;

import com.company.controllers.AirportController;
import com.company.controllers.FlightController;
import com.company.model.Airport;
import com.company.model.Flight;
import javafx.scene.shape.StrokeLineCap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class FlightView {
    public static List<Flight> flightHandler(String[] cmd) {
        ArrayList<Predicate<Flight>> predicates = new ArrayList<>();
        //добавляем предикат
        predicates.add((flight) -> true);
        //убрать двоякие вхождения
        String[] keyWords = {"from", "to", "arrival", "departure", "aircompany"};
        boolean[] usable = {/**/false, false, false, /**/false, /* */false};
        for (int i = 1; i < cmd.length; ++i) {
            switch (cmd[i].toLowerCase()) {
                case "from": {      //departureAirport
                    if (usable[0]) {
                        System.err.println("key word '" + keyWords[0] + "' is usable");
                        return null;
                    }
                    usable[0] = true;
                    {
                        //листаем до первого непустого(пока пусто)
                        for (; i < cmd.length && cmd[i].equals(""); ++i) ;
                        //не понец массива
                        if (cmd.length == i)
                            return null;
                    }
                    //ищем нужный аэропорт
                    Airport departureAirport = AirportController.find(cmd[++i]);
                    if (departureAirport != null) {
                        Predicate<Flight> predicate = (flight) ->
                                flight.getDepartureAirport().equals(departureAirport);
                        predicates.add(predicate);
                    }
//                  if (departureAirport != null)
                    {
                        //листаем до первого непустого(пока пусто)
                        for (; i < cmd.length && cmd[i].equals(""); ++i) ;
                        //не понец массива
                        if (cmd.length == i)
                            return null;
                    }
                    break;
                }
                case "to": {        //arrivalAirport
                    if (usable[1]) {
                        System.err.println("key word '" + keyWords[1] + "' is usable");
                        return null;
                    }
                    usable[1] = true;
                    {
                        //листаем до первого непустого(пока пусто)
                        for (; i < cmd.length && cmd[i].equals(""); ++i) ;
                        //не понец массива
                        if (cmd.length == i)
                            return null;
                    }
                    //ищем нужный аэропорт
                    Airport arrivalAirport = AirportController.find(cmd[++i]);
                    if (arrivalAirport != null) {
                        Predicate<Flight> predicate = (flight) ->
                                flight.getArrivalAirport().equals(arrivalAirport);
                        predicates.add(predicate);
                    }
                    break;
                }
                //нет случаем до после с включительно!
                case "arrival": {       //dateArrival
                    if (usable[2]) {
                        System.err.println("key word '" + keyWords[2] + "' is usable");
                        return null;
                    }
                    usable[2] = true;
                    Object[] dateAndType = dateAndTypeParse(cmd, i, "arrival");
                    if (dateAndType == null)
                        break;
                    Date dateArrival = (Date) dateAndType[0];
                    int isBeforeDate = (Integer) dateAndType[1];
                    Predicate<Flight> predicate;
                    if (isBeforeDate == 0)  //after
                        predicate = (flight) ->
                                flight.getDateArrival().after(dateArrival);
                    else
                        predicate = (flight) -> flight.getDateArrival().before(dateArrival);
                    predicates.add(predicate);
                }
                case "departure": {         //dateDeparture
                    if (usable[3]) {
                        System.err.println("key word '" + keyWords[3] + "' is usable");
                        return null;
                    }
                    usable[3] = true;
                    Object[] dateAndType = dateAndTypeParse(cmd, i, "departure");
                    if (dateAndType == null)
                        break;
                    Date dateDeparture = (Date) dateAndType[0];
                    int isBeforeDate = (Integer) dateAndType[1];
                    Predicate<Flight> predicate;
                    if (isBeforeDate == 0)  //after
                        predicate = (flight) ->
                                flight.getDateArrival().after(dateDeparture);
                    else
                        predicate = (flight) -> flight.getDateArrival().before(dateDeparture);
                    predicates.add(predicate);
                }
                case "aircompany": {
                    if (usable[4]) {
                        System.err.println("key word '" + keyWords[4] + "' is usable");
                        return null;
                    }
                    usable[4] = true;
                    // рейс конкретной авиакомпании
                    //найти список самолетов этой авиакомпании.
                    //составить предикат на принадлежность самелета авиакомпании
                    // (циклом сравнить с самолетами авиакомпании, если нашли, то вернуи true, а если нет, то false)
                }
                default: {
                    if (!(cmd[i].equals(""))) {
                        //листаем до первого непустого(пока пусто)
                        for (; i < cmd.length && cmd[i].equals(""); ++i) ;
                    }
                }
            }
        }
        if (predicates.size() > 0) {
            Predicate<Flight> request = predicates.get(0);
            for (int i = 1; i < predicates.size(); ++i) {
                request = request.and(predicates.get(i));
            }
            return FlightController.getIf(request);
        }
        return null;
    }

    private static Object[] dateAndTypeParse(String[] cmd, int i, String arrOrDep) {
        int isBeforeDate = -1;
        {
            //листаем до первого непустого(пока пусто)
            for (;i < cmd.length && cmd[i].equals(""); ++i);
            //не понец массива
            if (cmd.length == i)
                return null;
        }
        ++i;
        if (cmd[i].toLowerCase().equals("before"))
            isBeforeDate = 1;
        if (cmd[i].toLowerCase().equals("after"))
            isBeforeDate = 0;
        if (isBeforeDate == -1)
            return null;
        {
            //листаем до первого непустого(пока пусто)
            for (;i < cmd.length && cmd[i].equals(""); ++i);
            //не понец массива
            if (cmd.length == i)
                return null;
        }
        Date date;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        try {
            date = dateFormat.parse(cmd[++i]);
        } catch (ParseException e) {
            System.err.println("Can not parse date date (" + arrOrDep + (isBeforeDate == 0 ? "after" : "before") + ')');
            return null;
        }
        Object[] ans = {date, isBeforeDate};
        return ans;
    }

    public static String info() {
        StringBuilder ans = new StringBuilder();
        ans.append("Команды рейсов начинаются с ключевого слова Flight(далее все ключевые слова на английском),\n" +
                " регистр не важен и далее везде тоже, однако не должно быть никаких символов перед ним.\n");
        ans.append(" Поиск в рейсах можно вести по датам (Arrival,Departure), Аэропортам (From, To) и по авиокомпании(Aircompany)\n");
        ans.append(" Порядок в котором будут следовать ключевые слова после Flight не важен.\n");
        ans.append(" Однако не допускается повторений одного и тоже главного ключевего слова.\n");
        ans.append(" И между ключевыми словами должен быть хотя бы один пробел\n");
        ans.append(" Поиск по времени прибытия и взлета. ");
        ans.append(" После (Arrival,Departure) должно идти (Before,After), что означает до и после времени прилета или прибытия.\n");
        ans.append(" Завершается датой (прмер: 12.13.52 or 3:30pm)\n");
        ans.append(" Поиск по аэропортам. ");
        ans.append(" После (From, To) стоит написать название аэропорта");
        ans.append(" Поиск по авиакомпании. ");
        ans.append(" После (Aircompany) стоит написать название авиакомпании\n");
        return ans.toString();
    }
}
