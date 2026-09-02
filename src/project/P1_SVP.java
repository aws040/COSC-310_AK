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
 * @version 2026.09.2.18
 */
public class P1_SVP {
    private DoublyLinkedList<DoublyLinkedList<String>> polyLists = new DoublyLinkedList<>();
    private DoublyLinkedList<String> multiPolyList = new DoublyLinkedList<>();

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
     * This method to print out the entire list of polynomials separated.
     */
    public void printPolys() {
        for (int i = 0; i < polyLists.getSize(); i++) {
            int size = polyLists.get(i).getSize()-1;
            System.out.printf("P%d: ", i+1);
            StringBuilder poly = new StringBuilder();
            for (int j = 0; j < size; j++) {
                String var = toString(i, j, polyLists);
                if (j != 0) {
                    if (var.indexOf("-") == 0) {
                        var = "- " + var.substring(1);
                    } else {
                        var = "+ " + var;
                    }
                }
                poly.append(var);
            }
            String var = toString(i, size, polyLists);
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
     * @param j taken as the index of the string within the double list
     * @param polyList taken as a DoublyLinkedList within a DoublyLinkedList containing strings in an improper format
     * @return the string of the poly in the list as a base polynomial
     */
    private String toString(int i, int j, DoublyLinkedList<DoublyLinkedList<String>> polyList) {
        String item = polyList.get(i).get(j);
        return getString(item);
    }

    /**
     * toString method used by printMultiPoly() to allow and easier time to turn the poly into a string.
     * @param i taken as the index of the string within the list
     * @param polyList a DoublyLinkedList containing strings in an improper format
     * @return the string in a proper format
     */
    private String toString(int i, DoublyLinkedList<String> polyList) {
        String item = polyList.get(i);
        return getString(item);
    }

    /**
     * Private method for the toString taking a string and turning that string, if it is in teh right format, into a polynomial
     * @param item a string to be turned into proper printable format
     * @return the string in proper printable format
     */
    private String getString(String item) {
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

    /**
     * Multiples the polynomials given to this class together and stores them in a final list as a result.
     */
    public void multiplyPolys() {
        StringBuilder complete = new StringBuilder();
        for (int i = 0; i < polyLists.getSize(); i++) {
            for (int j = i + 1;  j < polyLists.getSize(); j++) {
                for (int k = 0; k < polyLists.get(i).getSize(); k++) {
                    // Getting the coefficient and power and multiplying it by the other polynomials variables
                    String multi = polyLists.get(i).get(k);
                    int multiPower = Integer.parseInt(multi.substring(multi.indexOf(" ")+1));
                    int multiCoef = Integer.parseInt(multi.substring(0, multi.indexOf(" ")));
                    for(int l = 0; l < polyLists.get(j).getSize(); l++) {
                        String item = polyLists.get(j).get(l);
                        int power = Integer.parseInt(item.substring(item.indexOf(" ")+1));
                        int coef = Integer.parseInt(item.substring(0, item.indexOf(" ")));
                        int finCoef = multiCoef * coef;
                        int finPower = multiPower + power;
                        String vari = finCoef + " " + finPower + "\r\n";
                        complete.append(vari);
                    }
                }
            }
        }
        multiPolyList = getStringLinkedList(complete.toString());
    }

    /**
     * Prints the multiplied polynomials out
     */
    public void printMultiPoly() {
        int size = multiPolyList.getSize()-1;
        System.out.print("Product: ");
        StringBuilder poly = new StringBuilder();
        String var;
        for (int j = 0; j < size; j++) {
                var = toString(j, multiPolyList);
                if (j != 0) {
                    if (var.indexOf("-") == 0) {
                        var = "- " + var.substring(1);
                    } else {
                        var = "+ " + var;
                    }
                }
                poly.append(var);
            }
            var = toString(size, multiPolyList);
            if (var.indexOf("-") == 0) {
                var = "- " + var.substring(1);
            } else {
                var = "+ " + var;
            }
            poly.append(var);
            System.out.println(poly);
    }
}
