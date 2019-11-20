
import airport.Airport;
import flight.Flight;
import plane.Plane;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        //нажмите два раза ctrl + Q, наведя на объект курсор, чтобы увидеть документацию класса в среде разработки
        testFlightPlane();
    }

    public static void testFlightPlane() {
        //подготовительные действия для создания объекта Flight
        Airport departureAirport = new Airport("Kurumoch"),
                arrivalAirport = new Airport("Smishlyaevka-2");
        Plane plane = new Plane();
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
        Flight flight = new Flight(plane, departureAirport, arrivalAirport, dateDeparture, dateArrival);
        //Проверки работы объекта
        System.out.println(flight.toString());
    }
}
