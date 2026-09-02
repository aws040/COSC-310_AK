package project;


import main.Main;
import structure.DoublyLinkedList;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Class that can take polynomials, print them and then multiply them and print the results.
 * @author Alejandro Borroni
 * @version 2026.09.2.11
 */
public class P1_SVP {
    private DoublyLinkedList<DoublyLinkedList<String>> polyLists = new DoublyLinkedList<>();

    /**
     * Method to add polynomials from a txt file with a given format.
     * @param file is the file to be read, has to be in a given format or method won't work as intended
     */
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
                DoublyLinkedList<String> list = getStringLinkedList(chunk);
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

    /**
     * This method takes a chunk of String and then uses a scanner and cuts up the string based on the new line pattern in notepad.
     * @param chunk any string
     * @return the list that was made using the string provided
     */
    private static DoublyLinkedList<String> getStringLinkedList(String chunk) {
        Scanner sc2 = new Scanner(chunk);
        sc2.useDelimiter("\r\n");
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
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
                        if (tempCoef + coef == 0) {
                            list.remove(i);
                            add = true;
                            break;
                        }
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

    /**
     * This method with print out the entire list of polynomials separated.
     */
    public void printPolys() {
        for (int i = 0; i < polyLists.getSize(); i++) {
            int size = polyLists.get(i).getSize()-1;
            System.out.printf("P%d: ", i+1);
            StringBuilder poly = new StringBuilder();
            for (int j = 0; j < size; j++) {
                String var = toString(i, j);
                if (j != 0) {
                    if (var.indexOf("-") == 0) {
                        var = "- " + var.substring(1);
                    } else {
                        var = "+ " + var;
                    }
                }
                poly.append(var);
            }
            String var = toString(i, size);
            if (var.indexOf("-") == 0) {
                var = "- " + var.substring(1);
            } else {
                var = "+ " + var;
            }
            poly.append(var);
            System.out.println(poly);
        }
    }

    /**
     * ToString method used by printPolys() to allow an easier time to turn the polys into strings to be printed.
     * @param i taken as index of the poly list to be used.
     * @param size taken as the size of the list that is to be turned into a string
     * @return the string of the poly in the list as a base polynomial
     */
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
