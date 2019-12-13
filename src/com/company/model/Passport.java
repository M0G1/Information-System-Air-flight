package com.company.model;

import java.io.Serializable;


//написать экьюалс
public class Passport implements Serializable {
    private String number;//сделать стрингом
    //разбить на фам имя отчество
    private String surname;
    private String name;
    private String patronymic;//отчество

    public void checkOnValidNumber(String number) {
        if (number.length() < 2 | number.length() > 20) {//посмотреть каки ограничения на пасспорт есть
            throw new IllegalArgumentException("Enter valid number of passport");
        }
    }
    //нужно ли делать проверки на фио?

    //конструктор принимает id и passport
    public Passport(String number, String surname, String name, String patronymic) {
        checkOnValidNumber(number);
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    //Создайте метод получения номера поспорта
    public String getNumber() {
        return number;
    }

    //Создайте метод изменения номера поспорта
    public void setNumber(String number) {
        this.number = number;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }


}
