package com.company.view;

import com.company.controllers.AirportController;
import com.company.controllers.FlightController;
import com.company.model.Airport;
import com.company.model.Flight;
import com.company.model.FlightList;
import javafx.scene.shape.StrokeLineCap;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class FlightView {
    private static int index;
    private static String[] stopWords = {};

    /**
     * выдает один аэропорт если указать место прибытия или отправления, потому что ищет первый попавшийся самолет, а повторения не исключаются в списке
     */
    public static List<Flight> handler(String[] cmd, int i) {
        index = i;
        ArrayList<Predicate<Flight>> predicates = new ArrayList<>();
        //добавляем предикат
        predicates.add((flight) -> true);
        //убрать двоякие вхождения
        String[] keyWords = {"from", "to", "arrival", "departure", "aircompany"};
        boolean[] usable = {/**/false, false, false, /**/false, /* */false};

        cycle:
        for (; index < cmd.length; ++index) {
            switch (cmd[index].toLowerCase()) {
                case "from": {      //departureAirport
                    if (usable[0]) {
                        System.err.println("key word '" + keyWords[0] + "' is usable");
                        return null;
                    }
                    usable[0] = true;
                    {
                        //листаем до первого непустого(пока пусто)
                        for (; index < cmd.length && cmd[index].equals(""); ++index) ;
                        //не понец массива
                        if (cmd.length == index)
                            return null;
                    }
                    //ищем нужный аэропорт
                    Airport departureAirport = AirportController.find(cmd[++index]);
                    if (departureAirport != null) {
                        Predicate<Flight> predicate = (flight) ->
                                flight.getDepartureAirport().equals(departureAirport);
                        predicates.add(predicate);
                    }
//                  if (departureAirport != null)
                    {
                        //листаем до первого непустого(пока пусто)
                        for (; index < cmd.length && cmd[index].equals(""); ++index) ;
                        //не понец массива
                        if (cmd.length == index)
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
                        for (; index < cmd.length && cmd[index].equals(""); ++index) ;
                        //не понец массива
                        if (cmd.length == index)
                            return null;
                    }
                    //ищем нужный аэропорт
                    Airport arrivalAirport = AirportController.find(cmd[++index]);
                    if (arrivalAirport != null) {
                        Predicate<Flight> predicate = (flight) ->
                                flight.getArrivalAirport().equals(arrivalAirport);
                        predicates.add(predicate);
                    }
                    break;
                }
                //нет случая до или после с включенными границами даты!
                case "arrival": {       //dateArrival
                    if (usable[2]) {
                        System.err.println("key word '" + keyWords[2] + "' is usable");
                        return null;
                    }
                    usable[2] = true;
                    Object[] dateAndType = dateAndTypeParse(cmd, "arrival");
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
                    break;
                }
                case "departure": {         //dateDeparture
                    if (usable[3]) {
                        System.err.println("key word '" + keyWords[3] + "' is usable");
                        return null;
                    }
                    usable[3] = true;
                    Object[] dateAndType = dateAndTypeParse(cmd, "departure");
                    if (dateAndType == null)
                        break;
                    Date dateDeparture = (Date) dateAndType[0];
                    int isBeforeDate = (Integer) dateAndType[1];
                    Predicate<Flight> predicate = null;
                    if (isBeforeDate == 0)  //after
                        predicate = (flight) ->
                                flight.getDateArrival().after(dateDeparture);
                    else
                        predicate = (flight) -> flight.getDateArrival().before(dateDeparture);
                    predicates.add(predicate);
                    break;
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
                    break;
                }
                default: {
                    //листаем до первого непустого(пока пусто)
                    for (int n = 0; stopWords != null && n < stopWords.length; ++n) {
                        //остановка цикла for по метке, если нашли стоп слово. Передача управления обратно.
                        if (stopWords[n].equals(cmd[index].toLowerCase()))
                            break cycle;
                    }
                    boolean isSkip = false;
                    if (cmd[index].equals("")) {
                        isSkip = true;
                    }
                    for (; index < cmd.length && cmd[index].equals(""); ++index) ;


                    if (!isSkip && !(cmd[index].equals(""))) {
                        System.out.println("Unknown command");
                        info();
                    } else {
                        --index;
                    }
                }
            }
        }
        if (predicates.size() > 0) {
            Predicate<Flight> request = predicates.get(0);
            for (int j = 1; j < predicates.size(); ++j) {
                request = request.and(predicates.get(j));
            }
            ConsoleView.setIndex(++index);
            return FlightController.getIf(request);
        }
        return null;
    }

    public static String newInstance(String[] cmd, int i) {
        /*ловим необходимость обовлять репозиторий*/
        String err = ConsoleView.INCORRECT_CMD + "Can not ";
        Scanner in = new Scanner(System.in);
        String[] depArr = {"departure", "arrival"};
        System.out.println("Enter the data of the Flight: " +
                "plane, " + depArr[0] + " Airport," + depArr[1] + "  Airport," +
                " date " + depArr[0] + ", date  " + depArr[1] + ". With spaces between argument");
        /*Plane?*/
        Airport[] airports = new Airport[2];
        for (int j = 0; j < 2; ++j) {
            System.out.println("Enter the " + depArr[j] + " airport name or 'null': ");
            String strAir = in.next();
            Airport airport;
            airport = AirportController.find(strAir);
            if (airport == null) {  // чтобы ввели
                --j;
                continue;
            }
            airports[j] = airport;
        }
        Date[] dates = new Date[2];
        for (int j = 0; j < 2; ++j) {
            System.out.println("Enter the " + depArr[j] + " date or 'null': ");
            String str = in.next();
            Date date = parseData(str);
            date = parseData(str);
            if (date == null) {  // чтобы ввели
                --j;
                continue;
            }
            dates[j] = date;
        }
        Flight flight = FlightController.createFlight(null, airports[0], airports[1], dates[0], dates[1]);
        return "Created " + flight.toString();

    }


    private static Object[] findListAndIndex(String[] cmd, int i) {    //такой же как и в Airport
        index = i;
        for (; index < cmd.length && cmd[index].equals(""); ++index) ;
        //не понец массива
        if (cmd.length == index)
            return null;
        List<Flight> lts = handler(cmd, index);
        int valIndex = -1;
        if (lts == null)
            return null;

        Scanner in = new Scanner(System.in);
        if (lts.size() == 0)
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
        Object[] ansArg = FlightView.findListAndIndex(cmd, i);

        if (ansArg == null)
            return err;

        List<Flight> lts = (List) ansArg[0];
        int valIndex = (Integer) ansArg[1];
        Scanner in = new Scanner(System.in);
        Flight indexFlight = lts.get(valIndex);
        String[] depArr = {"departure", "arrival"};
        System.out.println("Enter the data of the Flight: " +
                "plane, " + depArr[0] + " Airport," + depArr[1] + "  Airport," +
                " date " + depArr[0] + ", date  " + depArr[1] + ". With spaces between argument");
        /*Plane?*/
        Airport[] airports = new Airport[2];
        for (int j = 0; j < 2; ++j) {
            System.out.println("Enter the " + depArr[j] + " airport name or 'null': ");
            String strAir = in.next();
            Airport airport;
            if (!strAir.equals("null"))
                airport = AirportController.find(strAir);
            else
                airport = null;
            if (airport == null && !strAir.equals("null")) {  // чтобы ввели
                --j;
                continue;
            }
            airports[j] = airport;
        }
        Date[] dates = new Date[2];
        for (int j = 0; j < 2; ++j) {
            System.out.println("Enter the " + depArr[j] + " date or 'null': ");
            String str = in.next();
            Date date = parseData(str);
            if (!str.equals("null"))
                date = parseData(str);
            else
                date = null;
            if (date == null && !str.equals("null")) {  // чтобы ввели
                --j;
                continue;
            }
            dates[j] = date;
        }

        String oldFlight = indexFlight.toString();
        if (airports[0] != null)
            indexFlight.setDepartureAirport(airports[0]);
        if (airports[1] != null)
            indexFlight.setArrivalAirport(airports[1]);
        if (dates[0] != null)
            indexFlight.setDateDeparture(dates[0]);
        if (dates[1] != null)
            indexFlight.setDateArrival(dates[0]);

        return oldFlight + " is changed on " + indexFlight.toString();
    }


    public static String delete(String[] cmd, int i) {
        /*ловим необходимость обовлять репозиторий*/
        String err = ConsoleView.INCORRECT_CMD + "Can not find Airport";
        Object[] ansArg = FlightView.findListAndIndex(cmd, i);

        if (ansArg == null)
            return err;

        List<Flight> lts = (List) ansArg[0];
        int valIndex = (Integer) ansArg[1];
        StringBuilder ans = new StringBuilder();
        Flight del = lts.get(valIndex);
        if (FlightList.getInstance().remove(del))   //если успешно удалился
            return del.toString() + " delete\n";
        return ConsoleView.INCORRECT_CMD;
    }

    private static Date parseData(String data) {
        Date date = null;
        String[] patterns = {"dd.mm.yyyy", "dd.mm.yyyy HH:mm"};
        SimpleDateFormat dateFormat;
        for (int j = 0; j < patterns.length; ++j) {
            try {
                dateFormat = new SimpleDateFormat(patterns[j]);
                date = dateFormat.parse(data);
            } catch (ParseException e) {

            }
        }
        return date;
    }

    private static Object[] dateAndTypeParse(String[] cmd, String arrOrDep) {
        int isBeforeDate = -1;
        {
            //листаем до первого непустого(пока пусто)
            for (; index < cmd.length && cmd[index].equals(""); ++index) ;
            //не понец массива
            if (cmd.length == index)
                return null;
        }
        ++index;
        if (cmd[index].toLowerCase().equals("before"))
            isBeforeDate = 1;
        if (cmd[index].toLowerCase().equals("after"))
            isBeforeDate = 0;
        if (isBeforeDate == -1)
            return null;
        {
            //листаем до первого непустого(пока пусто)
            for (; index < cmd.length && cmd[index].equals(""); ++index) ;
            //не понец массива
            if (cmd.length == index)
                return null;
        }

        ++index;
        Object[] ans = null;
        Date date = parseData(cmd[index]);
        if (date != null) {
            ans = new Object[2];
            ans[0] = date;
            ans[1] = isBeforeDate;
        }
        if (ans == null)
            System.err.println("Can not parse date date (" + arrOrDep + " " + (isBeforeDate == 0 ? "after" : "before") + ')');
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
        ans.append(" Создание рейса new Flight, а потом ввести то что потребуется\n");
        ans.append(" Изменение рейса update, а дальше такая же сигнатура как у поиска по рейсам\n");
        ans.append(" Удаление рейса delete, а дальше такая же сигнатура как у поиска по рейсам\n");
        return ans.toString();
    }
}
