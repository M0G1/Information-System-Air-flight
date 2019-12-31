package com.company.view;

import com.company.controllers.AirportController;
import com.company.controllers.Repository;
import com.company.model.Airport;
import com.company.model.Flight;

import java.text.*;
import java.util.*;
import java.util.function.Predicate;

public class ConsoleView {
    /**
     * Метод вызывает другие методы обработчики.
     */

    public static final String INCORRECT_CMD = "Incorrect command. ";

    private static boolean isNewInstance = false;
    private static boolean isUpdate = false;
    private static boolean isDelete = false;
    private static int index;

    public static void setIndex(int index) {
        ConsoleView.index = index;
    }

    public static String commandReader(String str) {
        String[] cmd = str.split(" ");
        /**/
        System.out.println(Arrays.toString(cmd));

        if (cmd.length == 0)
            return INCORRECT_CMD;
        index = 0;
        return cmdHandler(cmd);
    }

    private static String cmdHandler(String[] cmd) {
        switch (cmd[index].toLowerCase()) {
            //просто добавить еще случаев
            case "flight": {
                ++index;
                if (isNewInstance)
                    return FlightView.newInstance(cmd, index);
                else if (isUpdate)
                    return FlightView.update(cmd, index);
                else if (isDelete)
                    return FlightView.delete(cmd, index);
                return toStringAnswer("Flights", FlightView.handler(cmd, index), "\n");
            }
            case "airport": {
                ++index;
                if (isNewInstance)
                    return AirportView.newInstance(cmd, index);
                else if (isUpdate)
                    return AirportView.update(cmd, index);
                else if (isDelete)
                    return AirportView.delete(cmd, index);

                return toStringAnswer("", AirportView.handler(cmd, index), "; ");
            }
            case "help":
            case "h":
            case "info": {
                return info();
            }
            case "new":
            case "update":
            case "delete": {
                /*
                newInstance() and update(), when returning string with error message.
                 Must begin with str INCORRECT_CMD.
                 Чтобы словить необходимость в перезагрузке репозитория.
                 */
                String cmdLowerCaseIndex = cmd[index].toLowerCase();
                if (cmdLowerCaseIndex.equals("new"))
                    isNewInstance = true;
                else if (cmdLowerCaseIndex.equals("update"))
                    isUpdate = true;
                else
                    isDelete = true;
                ++index;
                String ansStr = cmdHandler(cmd);
                resetFlags();
                int find = ansStr.indexOf(INCORRECT_CMD);
                if (find == -1)
                    Repository.updateRepos();
                return ansStr;
            }
            default: {
                return INCORRECT_CMD + "! Enter 'h' or 'help' for instruction.";
            }
        }
    }

    private static void resetFlags() {
        isNewInstance = false;
        isUpdate = false;
        isDelete = false;
    }


    public static String toStringAnswer(String listName, List list, String separator) {
        StringBuilder ans = new StringBuilder();
        if (list != null) {
            ans.append(listName + "{\n");
            for (int i = 0; i < list.size(); ++i) {
                ans.append(list.get(i).toString());
                if (i != list.size() - 1)
                    ans.append(separator);
            }
            ans.append(" \n}\n");
        } else {
            ans.append("No information\n");
        }
        return ans.toString();
    }

    public static String info() {
        StringBuilder ans = new StringBuilder();
        ans.append(FlightView.info() + '\n');
        ans.append(AirportView.info() + '\n');
        return ans.toString();
    }
}
