package com.company.controllers;

import com.company.model.AirportList;
import com.company.model.FlightList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
//=========================================== Static Fields=========================================================

    // порядок классов в файйле репозитории:
    private static List[] lists = {
            FlightList.getInstance(),
            AirportList.getInstance()
    };

    private static File repos = new File("repos.txt");

//=========================================== Get & Set=========================================================

    public static File setFile(File newFile) {
        File temp = repos;
        repos = newFile;
        return repos;
    }

//=========================================== Methods =========================================================

    private static boolean writeFromList() {
        ObjectOutputStream outObj;

        try {
            outObj = new ObjectOutputStream(new FileOutputStream(repos));
            for (int i = 0; i < lists.length; ++i) {
                outObj.writeObject(lists[i]);
                outObj.writeInt('\n');
            }
            outObj.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    private static List[] readToLists() throws InvalidClassException {
        ObjectInputStream inObj;
        List[] ans = null;
        try {
            ans = new List[lists.length];
            inObj = new ObjectInputStream(new FileInputStream(repos));
//            ans[0] = (FlightList) inObj.readObject();
//            inObj.readInt(); //symbol: '\n'
//            ans[1] = (AirportList) inObj.readObject();
//            inObj.readInt(); //symbol: '\n'
            System.out.println(ans[0]);
            System.out.println(ans[1]);
            for (int i = 0; i < lists.length; ++i) {
                ans[i] = (List) inObj.readObject();
                System.out.println(ans[i].size());
                inObj.readInt(); //symbol: '\n'
            }
            inObj.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return null;
        }
        return ans;
    }

    public static boolean updateRepos() {
        //Можно внести оптимизацию по обновлению конкретного списка, а не сразу всех
        return writeFromList();
    }

    public static boolean uploadRepos() {
        try {
            List[] lsts = readToLists();
            if (lsts == null)
                return false;
            for (int i = 0; i < lsts.length; ++i) {
                if (lsts[i] == null)
                    return false;
                lists[i].addAll(lsts[i]);
            }
            return true;
        } catch (InvalidClassException e) {
            System.out.println("Repository have not the data");
            return false;
        }
    }
}
