package project;


import main.Main;
import structure.LinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Alejandro Borroni
 * @version 2026.09.1.16
 */
public class P1_SVP {
    private LinkedList<LinkedList<String>> polyLists = new LinkedList<>();

    public void addPolys(File file) {
        Scanner sc;
        try {
            sc = new Scanner(file);
            sc.useDelimiter("Polynomial \\d+:\\s*|//\\s*");
            while (sc.hasNext()) {
                String chunk = sc.next();
                if (chunk.isEmpty()) {
                    continue;
                }
                LinkedList<String> list = getStringLinkedList(chunk);
                polyLists.add(list);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            try {
                Path currentPath = Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                Path parentDir = currentPath.getParent().getParent();
                String pathStr = currentPath.toString();
                if (pathStr.contains("out\\production")) {
                    parentDir = currentPath.getParent().getParent().getParent().getParent();
                }
                System.out.println(parentDir.toString());
                System.err.printf("P1_SVP_Input.txt was not found in parent directory. Place file in the %s folder and try again.", parentDir.getFileName().toString());
            }catch (URISyntaxException s) {
                s.printStackTrace();
            }
        }
    }

    private static LinkedList<String> getStringLinkedList(String chunk) {
        Scanner sc2 = new Scanner(chunk);
        sc2.useDelimiter("\r\n");
        LinkedList<String> list = new LinkedList<>();
        while (sc2.hasNext()) {
            String line = sc2.next();
            int temp = Integer.parseInt(line.substring(line.indexOf(" ")+1));
            int tempCoef = Integer.parseInt(line.substring(0, line.indexOf(" ")));
            int index = 0;
            if (list.getSize() > 0) {
                int size = list.getSize();
                for (int i = 0; i < size; i++) {
                    String item = list.get(i);
                    int power = Integer.parseInt(item.substring(item.indexOf(" ")+1));
                    int coef = Integer.parseInt(item.substring(0, item.indexOf(" ")));
                    if (temp > power) {
                        index++;
                    } else if (temp == power) {
                        list.set(index, (tempCoef + coef) + " " + power);
                    } else {
                        list.add(line);
                    }
                }
            } else {
                list.add(line);
            }
        }
        sc2.close();
        return list;
    }

    public void printPolys() {
        for (int i = 0; i < polyLists.getSize(); i++) {
            System.out.print("P1: ");
            for (int j = 1; j < polyLists.get(i).getSize()-1; j++) {
                System.out.print(polyLists.get(i).get(j) + " ");
            }
            System.out.print(polyLists.get(i).get(polyLists.get(i).getSize()-1));
            System.out.println();
        }
    }

}
