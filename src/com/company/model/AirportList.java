package com.company.model;

import com.company.controllers.Repository;

import java.io.Serializable;
import java.util.*;

public class AirportList implements Serializable, List<Airport> {
    /*
        transient модификатор доступа, чтобы не сериализовалось.
        Потому что иначе при сериализации AirportList ему нужно сериализовать AirportList((подсказка)бесконечная рекурсия )
    */
    private static transient AirportList uniqueInstance = null;
    private List<Airport> airportList;

    /*Статический блок иницилизации для создания списка рейсов. Для корректной работы.
     * Добавлю потом чтение из файла(БД мб потом) */ {
        airportList = new ArrayList<>();
    }

    private AirportList() {
    }

    public static AirportList getInstance() {
        if (uniqueInstance == null)
            uniqueInstance = new AirportList();
        return uniqueInstance;
    }

    //========================================== Methods of List ======================================================

    public Airport get(int index) {
        return airportList.get(index);
    }


    public boolean add(Airport flight) {
        boolean ans = airportList.add(flight),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    public boolean remove(Airport flight) {
        boolean ans = airportList.remove(flight),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    public Airport remove(int index) {
        Airport temp = airportList.remove(index);
        if (temp != null)
            if (Repository.updateRepos())
                return temp;
        //trows? Exception
        return temp;
    }

    public int size() {
        return airportList.size();
    }

    public Object[] toArray() {
        return airportList.toArray();
    }

    @Override
    public boolean isEmpty() {
        return airportList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return airportList.contains(o);
    }

    @Override
    public Iterator<Airport> iterator() {
        return airportList.iterator();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) airportList.toArray();
    }

    @Override
    public boolean remove(Object o) {
        boolean ans = airportList.remove(o),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return airportList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Airport> c) {
        boolean ans = airportList.addAll(c),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Airport> c) {
        boolean ans = airportList.addAll(index, c),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean ans = airportList.removeAll(c),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean ans = airportList.retainAll(c),
                upd = false;
        if (ans) {
            upd = Repository.updateRepos();
        }
        //trows? Exception
        return ans && upd;
    }

    @Override
    public void clear() {
        airportList.clear();
        boolean upd = Repository.updateRepos();
    }

    @Override
    public Airport set(int index, Airport element) {
        Airport temp = airportList.set(index, element);
        if (temp != null)
            if (Repository.updateRepos())
                return temp;
        //trows? Exception
        return temp;

    }

    @Override
    public void add(int index, Airport element) {
        airportList.add(index, element);
        boolean upd = Repository.updateRepos();
    }

    @Override
    public int indexOf(Object o) {
        return airportList.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return airportList.lastIndexOf(o);
    }

    @Override
    public ListIterator<Airport> listIterator() {
        return airportList.listIterator();
    }

    @Override
    public ListIterator<Airport> listIterator(int index) {
        return airportList.listIterator(index);
    }

    @Override
    public List<Airport> subList(int fromIndex, int toIndex) {
        return airportList.subList(fromIndex, toIndex);
    }
}
