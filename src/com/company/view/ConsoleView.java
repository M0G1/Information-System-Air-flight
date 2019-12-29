package com.company.view;

import com.company.controllers.AirportController;
import com.company.model.Airport;
import com.company.model.Flight;

import java.text.*;
import java.util.*;
import java.util.function.Predicate;

public class ConsoleView {
    /**
     * Метод вызывает другие методы обработчики.
     */
    public static String commandReader(String str) {
        String INCORRECT_CMD = "Incorrect command";
        String[] cmd = str.split(" ");
        System.out.println(Arrays.toString(cmd));
        if (cmd.length == 0)
            return INCORRECT_CMD;
        switch (cmd[0].toLowerCase()) {
            //просто добавить еще случаев
            case "flight": {
                 return toStringAnswer("Flights", FlightView.flightHandler(cmd), "\n");
            }
            case "airport": {
                return toStringAnswer("", AirportView.airportHandler(cmd), "; ");
            }
            case "help": case "h": {
                return info();
            }
            default: {
                return INCORRECT_CMD;
            }
        }
    }


    public static String toStringAnswer(String listName, List list, String separator) {
        StringBuilder ans = new StringBuilder();
        if (list != null) {
            ans.append(listName + "{\n");
            for (int i = 0; i < list.size(); ++i) {
                ans.append(list.get(i).toString());
                if (i != list.size() - 1)
                    ans.append(separator);
            }
            ans.append(" \n}\n");
        } else {
            ans.append("No information\n");
        }
        return ans.toString();
    }
    public static String info(){
        StringBuilder ans = new StringBuilder();
        ans.append(FlightView.info() + '\n');
        ans.append(AirportView.info() + '\n');
        return ans.toString();
    }
}
