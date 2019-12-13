package com.company.model;

import com.company.model.Passport;
import java.util.UUID;
import java.io.Serializable;

public class Passenger implements Serializable {
    private UUID ID;
    private Passport passport;
    //паспорт седалть отдельным классом
    //сделать стрингом


    //конструктор принимает id и passport
    public Passenger(Passport passport) {
        this.ID = UUID.randomUUID();
        this.passport = passport;
    }

    public UUID getID() {
        return ID;
    }

    //Создайте метод получения чиселок паспорта
    public Passport getPassport() {
        return passport;
    }

    //Создайте метод изменения чисел в паспорте
    public void setPassport(Passport passport) {
        this.passport = passport;
    }


}

