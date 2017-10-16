package com.vachoompo.mysqlkeycreator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            String command = args[0];
            if (command.equalsIgnoreCase("help")) {
                Helper.getOptions();
            } else if (command.equalsIgnoreCase("export")) {
                Exporter.exportCsv();
            } else if (command.equalsIgnoreCase("import")) {
                System.out.print("Are you sure you want create primary keys? ");
                Scanner in = new Scanner(System.in);
                String input = in.next();
                if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                    Importer.parseCsv();
                } else {
                    System.out.println("Oki doki, review the csv and come back later!");
                }
            } else {
                System.out.println("Wrong command, please try to pass help to see the available options");
            }
        } else {
            System.out.println("Please provide arguments, or pass help to see the available options");
        }
    }

}
