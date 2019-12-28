package com.company.controllers;

import java.io.*;

public class AircompanyController implements Serializable, Controller {

    public AircompanyController () {}

    public void refreshModelData(File file, Object aircompanies) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(aircompanies);
        objectOutputStream.close();
    }

    @Override
    public void refreshAllData() {}
}
