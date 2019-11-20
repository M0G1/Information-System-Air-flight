package flight;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Для работы использовать FlightController.
 * Класс будет хранить все существующие рейсы,
 * осуществлять методы доступа,
 * сохраянять и загружать список рейсов.
 */
public class FlightList implements Iterable<Flight> {
    private List<Flight> flightList;

    /*Статический блок иницилизации для создания списка рейсов. Для корректной работы.
     * Добавлю потом чтение из файла(БД мб потом) */ {
        flightList = new ArrayList<>();
    }

    public FlightList() {
    }

    public Flight get(int index) {
        return flightList.get(index);
    }


    public boolean add(Flight flight) {
        return flightList.add(flight);
    }

    public boolean remove(Flight flight) {
        return flightList.remove(flight);
    }

    public Flight remove(int index) {
        return flightList.remove(index);
    }

    public int size() {
        return flightList.size();
    }

    public Object[] toArray() {
        return flightList.toArray();
    }

    @Override
    public Iterator<Flight> iterator() {
        return flightList.iterator();
    }


}
