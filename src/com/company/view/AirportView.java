package com.company.view;

import com.company.controllers.AirportController;
import com.company.controllers.Repository;
import com.company.model.Airport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class AirportView {

    private static int index;


    public static List<Airport> handler(String[] cmd, int i) {
        index = i;
        ArrayList<Airport> ans = new ArrayList<>();

        if (cmd.length > 1) {
            //листаем до первого непустого(пока пусто)
            for (; index < cmd.length && cmd[index].equals(""); ++index) ;
            //не понец массива
            if (cmd.length == index)
                return null;
        }
        Predicate<Airport> predicate = (airport) -> true;
        if (index < cmd.length) {
            String airportName = cmd[index].toLowerCase();
            Predicate<Airport> predicate1 = (airport) -> airport.getName().toLowerCase().equals(airportName);
            predicate = predicate.and(predicate1);
        }
        ConsoleView.setIndex(++index);
        return AirportController.getIf(predicate);
    }

    public static String newInstance(String[] cmd, int i) {
        /*ловим необходимость обовлять репозиторий*/
        String err = ConsoleView.INCORRECT_CMD + "Can not find name of Airport";
        index = i;
        for (; index < cmd.length && cmd[index].equals(""); ++index) ;
        //не понец массива
        if (cmd.length == index)
            return err;
        Airport temp = AirportController.createAirport(cmd[index]);
        ConsoleView.setIndex(++index);
        StringBuilder ans = new StringBuilder("Instance created ");
        ans.append(temp.toString() + '\n');
        return ans.toString();
    }

    public static String update(String[] cmd, int i) {
        /*ловим необходимость обовлять репозиторий*/
        String err = ConsoleView.INCORRECT_CMD +  "Can not find Airport";
        index = i;
        StringBuilder ans = new StringBuilder();
        for (; index < cmd.length && cmd[index].equals(""); ++index) ;
        //не понец массива
        if (cmd.length == index)
            return err;
        List<Airport> lts = handler(cmd, index);
        int updateValIndex = -1;
        if (lts == null)
            return err;

        Scanner in = new Scanner(System.in);
        if (lts.size() == 1)
            updateValIndex = 0;
        if (lts.size() > 1) {
            System.out.println(ConsoleView.toStringAnswer("Please enter index of updatable value(1,2,3...).", lts, "\n"));
            try {
                updateValIndex = (in.nextInt() - 1);
                if (in.ioException() != null)
                    throw in.ioException();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                return err;
            }
        }

        System.out.println("Enter the new name for Airport");
        Airport airport = lts.get(updateValIndex);
        String valStr = airport.toString();
        airport.setName(in.next());
        ans.append(valStr);
        ans.append(" has changed on ");
        ans.append(airport.toString());
        return ans.toString();
    }

    public static String info() {
        return "Поиск аэропорта. нужно написать Airport и после через пробел его название.\n" +
                " Если такого нет, то выведет соответствующее сообщение";
    }

}
