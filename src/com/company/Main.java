package com.company;

import com.company.controllers.AirportController;
import com.company.controllers.FlightController;
import com.company.controllers.Repository;
import com.company.model.*;
import com.company.view.ConsoleView;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        //нажмите два раза ctrl + Q, наведя на объект курсор, чтобы увидеть документацию класса в среде разработки
        //testFlightPlane();
        //testFlightController();
        //testSerializableMuslim();
        //reposTest();
        viewTest();
    }

    public static void testFlightPlane() {
        //подготовительные действия для создания объекта Flight
        Airport departureAirport = new Airport("Kurumoch"),
                arrivalAirport = new Airport("Smishlyaevka-2");
        Plane plane = new Plane(2300, 20, 5);
        //форматированное чтение даты, формат задается строкой \/
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        Date dateDeparture, dateArrival;
        try {
            dateArrival = dateFormat.parse("01-01-2077");
            // отмем один день в миллисекундах
            dateDeparture = new Date(dateArrival.getTime() - 1 * 24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }
        Flight flight1 = new Flight(plane, departureAirport, arrivalAirport, dateDeparture, dateArrival);
        try {
            System.out.println("flight1.equals(flight1) " + flight1.equals(flight1));
            System.out.println("flight1.hashCode() " + flight1.hashCode());
            //System.out.println("" + flight1.);
            //Проверки работы объекта
            System.out.println(flight1.toString());
        } catch (Exception e) {
            System.err.println(e.getClass().getName());
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void fillFlightListInController() {

    }

    public static void testFlightController() {

        Airport[] airports = {
                AirportController.createAirport("A"),
                AirportController.createAirport("B"),
                AirportController.createAirport("C"),
                AirportController.createAirport("D"),
                AirportController.createAirport("E"),
                AirportController.createAirport("F")
        };
        Random rnd = new Random();
        long currTime = System.currentTimeMillis();
        System.out.println(((new Date(currTime)).toString()));
        for (int i = 0; i < airports.length - 1; ++i) {
            Date departure = new Date(currTime + 1000L * 60L * 60L * (1L + (long) rnd.nextInt(1000))),
                    arrival = new Date(departure.getTime() + 1000L * 60L * 60L * (1L + (long) rnd.nextInt(1000)));
            FlightController.createFlight(new Plane(2300, 20, 5), airports[i], airports[i + 1], departure, arrival);
        }

        Date departureDate = new Date(currTime + 1000L * 60L * 60L * (1L + (long) rnd.nextInt(100))),
                arrivalDate = new Date(departureDate.getTime() + 1000L * 60L * 60L * (500L + (long) rnd.nextInt(500)));
        System.out.println("arrivalDate: " + arrivalDate.toString() + " m: " + arrivalDate.getTime());
        System.out.println("departureDate: " + departureDate.toString() + " m: " + departureDate.getTime());


        Object[] beforeArrivalD = FlightController.getBeforeArrivalDate(arrivalDate).toArray();
        Object[] afterDepartureD = FlightController.getAfterDepartureDate(departureDate).toArray();

        System.out.println("beforeArrivalD: \n" + Arrays.toString(beforeArrivalD));
        System.out.println("afterDepartureD: \n" + Arrays.toString(afterDepartureD));

        System.out.println("FlightList: ");
        for (Flight f : FlightController.getList()) {
            System.out.println(f.toString());
        }
    }

    public static void testSerializableMuslim() {
        //создаем список рейсов по паттерну одиночка
        testFlightController();
        System.out.println("Serializable test");
        try {
            File serFile = new File("reposF.bin");
            ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(serFile));
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(serFile));

            objOut.writeObject(FlightList.getInstance());
            FlightList fl2 = (FlightList) objIn.readObject();

            System.out.println(Arrays.toString(fl2.toArray()));

        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void reposTest() {
        System.out.println("Repos upload: " + Repository.uploadRepos());
        testFlightController();
        Repository.updateRepos();
        System.out.println("Repos TEST");
        printList("Airports", AirportList.getInstance(), "\n");
        printList("Flights", FlightList.getInstance(), "\n");
    }

    public static void viewTest() {
        System.out.println("Repos upload: " + Repository.uploadRepos());
        printList("Airports", AirportList.getInstance(), "\n");
        printList("Flight", FlightList.getInstance(), "\n");
        for (int i = 0; true; ++i) {
            System.out.println("Write command or help");
            Scanner scan = new Scanner(System.in);
            String fromCons = scan.nextLine();
            //System.out.println("FromCons " + fromCons);
            if (fromCons.toLowerCase().equals("f")) {
                return;
            }
            String toCons = ConsoleView.commandReader(fromCons);
            System.out.println(toCons);
        }
    }

    public static void experiments() {
    }

    public static void printList(String listName, List list, String separator) {
        System.out.println(listName + "{");
        for (int i = 0; i < list.size(); ++i) {
            System.out.print(list.get(i));
            if (i != list.size() - 1)
                System.out.print(separator);
        }
        System.out.println("\n}");
    }
}
