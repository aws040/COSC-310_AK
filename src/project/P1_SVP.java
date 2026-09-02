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
            int tempPo = Integer.parseInt(line.substring(line.indexOf(" ")+1));
            int tempCoef = Integer.parseInt(line.substring(0, line.indexOf(" ")));
            if (list.getSize() > 0) {
                int size = list.getSize();
                boolean add = false;
                for (int i = 0; i < size; i++) {
                    String item = list.get(i);
                    int power = Integer.parseInt(item.substring(item.indexOf(" ")+1));
                    int coef = Integer.parseInt(item.substring(0, item.indexOf(" ")));
                    if (tempPo < power) {
                        list.add(i, line);
                        add = true;
                        break;
                    } else if (tempPo == power) {
                        list.set(i, (tempCoef + coef) + " " + power);
                        add = true;
                        break;
                    }
                }
                if (!add) {
                    list.add(line);
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
            int size = polyLists.get(i).getSize()-1;
            System.out.printf("P%d: ", i+1);
            StringBuilder poly = new StringBuilder();
            for (int j = 0; j < size; j++) {
                poly.append(toString(i, j));
            }
            poly.append(toString(i, size));
            System.out.println(poly);
        }
    }

    private String toString(int i, int size) {
        String item = polyLists.get(i).get(size);
        int coef = Integer.parseInt(item.substring(0, item.indexOf(" ")));
        int power = Integer.parseInt(item.substring(item.indexOf(" ")+1));
        String end = "";
        if (power == 0) {
            end = end + coef + " ";
        } else if (power == 1) {
            end = end + coef + "x ";
        } else if (power == -1) {
            end = end + coef + "x^-1 ";
        } else if (power > 1) {
            end = end + coef + "x^" + power + " ";
        } else {
            end = end + coef + "x^" + power + " ";
        }
        return end;
    }

}
