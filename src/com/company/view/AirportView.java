package com.company.view;

import com.company.controllers.AirportController;
import com.company.model.Airport;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AirportView {

    public static List<Airport> airportHandler(String[] cmd) {
        ArrayList<Airport> ans = new ArrayList<>();
        int i = 1;
        if (cmd.length > 1) {
            //листаем до первого непустого(пока пусто)
            for (;i < cmd.length && cmd[i].equals(""); ++i);
            //не понец массива
            if (cmd.length == i)
                return null;
        }
        Predicate<Airport> predicate = (airport) -> true;
        if (i < cmd.length) {
            String airportName = cmd[i].toLowerCase();
            Predicate<Airport> predicate1 = (airport) -> airport.getName().toLowerCase().equals(airportName);
            predicate = predicate.and(predicate1);
        }
        return AirportController.getIf(predicate);
    }

    public static String info() {
        return "Поиск аэропорта. нужно написать Airport и после через пробел его название.\n" +
                " Если такого нет, то выведет соответствующее сообщение";
    }
}
