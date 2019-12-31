package com.company.view;

import com.company.controllers.AirportController;
import com.company.controllers.Repository;
import com.company.model.Airport;
import com.company.model.AirportList;

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

    private static Object[] findListAndIndex(String[] cmd, int i) {
        index = i;
        for (; index < cmd.length && cmd[index].equals(""); ++index) ;
        //не понец массива
        if (cmd.length == index)
            return null;
        List<Airport> lts = handler(cmd, index);
        int valIndex = -1;
        if (lts == null)
            return null;

        Scanner in = new Scanner(System.in);
        if(lts.size() == 0)
            return null;
        if (lts.size() == 1)
            valIndex = 0;
        if (lts.size() > 1) {
            System.out.println(ConsoleView.toStringAnswer("Please enter index of needed value(1,2,3...).", lts, "\n"));
            try {
                valIndex = (in.nextInt() - 1);
                if (in.ioException() != null)
                    throw in.ioException();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                return null;
            }
        }
        Object[] ans = {lts, valIndex};
        return ans;
    }

    public static String update(String[] cmd, int i) {
        /*ловим необходимость обовлять репозиторий*/
        String err = ConsoleView.INCORRECT_CMD + "Can not find Airport";
        Object[] ansArg = AirportView.findListAndIndex(cmd, i);

        if (ansArg == null)
            return err;

        List<Airport> lts = (List) ansArg[0];
        int valIndex = (Integer) ansArg[1];
        Scanner in = new Scanner(System.in);
        StringBuilder ans = new StringBuilder();
        Airport airport;
        String valStr;

        airport = lts.get(valIndex);

        System.out.println("Enter the new name for Airport");
        valStr = airport.toString();
        airport.setName(in.next());

        ans.append(valStr);
        ans.append(" has changed on ");
        ans.append(airport.toString());
        return ans.toString();
    }


    public static String delete(String[] cmd, int i) {
        /*ловим необходимость обовлять репозиторий*/
        String err = ConsoleView.INCORRECT_CMD + "Can not find Airport";
        Object[] ansArg = AirportView.findListAndIndex(cmd, i);

        if (ansArg == null)
            return err;

        List<Airport> lts = (List) ansArg[0];
        int valIndex = (Integer) ansArg[1];
        StringBuilder ans = new StringBuilder();

        Airport airport = lts.get(valIndex);
        if(AirportController.isAirportHaveFlight(airport))  //аэропорт имеет рейсы
            return ConsoleView.INCORRECT_CMD + "Airport have a flight";
        if( AirportList.getInstance().remove(airport))  //если успешно удалился
            return airport.toString() + " delete\n";
        return ConsoleView.INCORRECT_CMD;
    }

    public static String info() {
        return "Поиск аэропорта. нужно написать Airport и после через пробел его название.\n" +
                " Если такого нет, то выведет соответствующее сообщение\n"+
                " Удаление аэропорта возможно только если у него нет рейсов в будущем или активных"+
                "  delete, а дальше такая же сигнатура как у поиска по рейсам\n"+
                " Изменение названия аэропорта update, а дальше такая же сигнатура как у поиска по рейсам\n"+
                " Создание аэропорта new Airport и название аэропорта\n";
    }

}
