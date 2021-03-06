package com.company.model;

import com.company.controllers.Repository;

import java.io.Serializable;
import java.util.*;

/**
 * Для работы использовать FlightController.
 * Класс будет хранить все существующие рейсы,
 * осуществлять методы доступа,
 * сохраянять и загружать список рейсов.
 */
public class FlightList implements Serializable, List<Flight> {
    /*
        transient модификатор доступа, чтобы не сериализовалось.
        Потому что иначе при сериализации FlightList ему нужно сериализовать FlightList((подсказка)бесконечная рекурсия )
    */
    private static transient FlightList uniqueInstance = null;
    private List<Flight> flightList;

    /*Статический блок иницилизации для создания списка рейсов. Для корректной работы.
     * Добавлю потом чтение из файла(БД мб потом) */ {
        flightList = new ArrayList<>();
    }

    private FlightList() {
    }

    public static FlightList getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new FlightList();
        return uniqueInstance;
    }

    //========================================== Methods of List ======================================================

    public Flight get(int index) {
        return flightList.get(index);
    }


    public boolean add(Flight flight) {
        boolean ans = flightList.add(flight),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    public boolean remove(Flight flight) {
        boolean ans = flightList.remove(flight),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    public Flight remove(int index) {
        Flight temp = flightList.remove(index);
        if (temp != null)
            if (Repository.updateRepos())
                return temp;
        //trows? Exception
        return temp;
    }

    public int size() {
        return flightList.size();
    }

    public Object[] toArray() {
        return flightList.toArray();
    }

    @Override
    public boolean isEmpty() {
        return flightList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return flightList.contains(o);
    }

    @Override
    public Iterator<Flight> iterator() {
        return flightList.iterator();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) flightList.toArray();
    }

    @Override
    public boolean remove(Object o) {
        boolean ans = flightList.remove(o),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return flightList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Flight> c) {
        boolean ans = flightList.addAll(c),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Flight> c) {
        boolean ans = flightList.addAll(index, c),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean ans = flightList.removeAll(c),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean ans = flightList.retainAll(c),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public void clear() {
        flightList.clear();
        boolean upd = Repository.updateRepos();
    }

    @Override
    public Flight set(int index, Flight element) {
        Flight temp = flightList.set(index, element);
        if (temp != null)
            if (Repository.updateRepos())
                return temp;
        //trows? Exception
        return temp;

    }

    @Override
    public void add(int index, Flight element) {
        flightList.add(index, element);
        boolean upd = Repository.updateRepos();
    }

    @Override
    public int indexOf(Object o) {
        return flightList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return flightList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Flight> listIterator() {
        return flightList.listIterator();
    }

    @Override
    public ListIterator<Flight> listIterator(int index) {
        return flightList.listIterator(index);
    }

    @Override
    public List<Flight> subList(int fromIndex, int toIndex) {
        return flightList.subList(fromIndex, toIndex);
    }

}
