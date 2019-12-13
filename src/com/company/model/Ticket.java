package Model;

import java.util.UUID;
import java.io.Serializable;

public class Ticket implements Serializable {
    private UUID ID;
    private UUID PASSENGER_ID;
    private int price;

    // private String typeclass;//их определенное кол-во

    //написать коонтроллер

    public void checkOnValidPrice(int price) {
        if (price < 1) {
            throw new IllegalArgumentException("price");
        }
    }

    //конструктор
    public Ticket(int price) {
        checkOnValidPrice(price);
        this.ID = UUID.randomUUID();
        this.price = price;

    }


    public UUID getID() {
        return ID;
    }

    public UUID getPASSENGER_ID() {
        return PASSENGER_ID;
    }

    public int getPrice() {
        return price;
    }


    public void setPrice(int price) {
        this.price = price;
    }

   /* public String getTypeclass() {
        return typeclass;
    }

    //Создайте метод изменения id
    public void setTypeclass(String typeclass) {
        this.typeclass = typeclass;
    }*/
}
