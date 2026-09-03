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
 * @version 2026.09.2.22
 */
public class P1_SVP {
    private DoublyLinkedList<DoublyLinkedList<Term>> polyLists = new DoublyLinkedList<>();
    private DoublyLinkedList<Term> multiPolyList = new DoublyLinkedList<>();

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
                DoublyLinkedList<Term> list = getStringLinkedList(chunk);
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
     * Method extracted from a different method to allow better reuse of code. Compares two terms and then adds termA ti
     * the list provided at the given index IF its power is less than. Otherwise, combines both coefficients if the powers are equals
     * and removes both terms from the list if the coefficients combined equal 0. Will return a boolean based on if it added/removed from the list
     * @param termA the first term to be compared to the one in the list
     * @param termB the second term coming from the list to be compared
     * @param list the list itself to be modified
     * @param index the index of termB to help decrease search time
     * @return true if termA was added, or if termB was removed from the list. false if nothing was done as termA's power was greater.
     */
    private static boolean compareTerms(Term termA, Term termB, DoublyLinkedList<Term> list, int index) {
        int tempPo = termA.exponent();
        int power = termB.exponent();
        int tempCoef = termA.coefficient();
        int coef = termB.coefficient();
        if (tempPo < power) {
            list.add(index, termA);
            return true;
        } else if (tempPo == power) {
            if (tempCoef + coef == 0) {
                list.remove(index);
                return true;
            }
            Term termC = new Term(tempCoef + coef, tempPo);
            list.set(index, termC);
            return true;
        }
        return false;
    }

    /**
     * This method takes a chunk of String and then uses a scanner and cuts up the string based on the new line pattern in notepad.
     * @param chunk any string
     * @return the list that was made using the string provided
     */
    private static DoublyLinkedList<Term> getStringLinkedList(String chunk) {
        DoublyLinkedList<Term> list = new DoublyLinkedList<>();
        String[] sc2 = chunk.split("\r?\n");
        for (String line : sc2) {
            String[] split = line.split(" ");
            int tempPo = Integer.parseInt(split[1]);
            int tempCoef = Integer.parseInt(split[0]);
            Term termA;
            try {
                termA = new Term(tempCoef, tempPo);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid coefficient: 0, skipping coefficient");
                continue;
            }
            if (list.getSize() > 0) {
                boolean add = false;
                int index = 0;
                for (Term termB : list) {
                    add = compareTerms(termA, termB, list, index);
                    if (add) {
                        break;
                    }
                    index++;
                }
                if (!add) {
                    list.add(termA);
                }
            } else {
                list.add(termA);
            }
        }
        return list;
    }

    /**
     * This method to print out the entire list of polynomials separated.
     */
    public void printPolys() {
        int index = 0;
        for (DoublyLinkedList<Term> term : polyLists) {
            System.out.printf("P%d: ", index+1);
            StringBuilder poly = new StringBuilder();
            int loc = 0;
            for (Term termB : term) {
                String var = toString(termB);
                if (loc != 0) {
                    if (var.indexOf("-") == 0) {
                        var = "- " + var.substring(1);
                    } else {
                        var = "+ " + var;
                    }
                }
                poly.append(var);
                loc++;
            }
            System.out.println(poly);
            index++;
        }
    }

    /**
     * Private method for the toString taking a string and turning that string, if it is in teh right format, into a polynomial
     * @param item a string to be turned into proper printable format
     * @return the string in proper printable format
     */
    private String toString(Term item) {
        int coef = item.coefficient();
        int power = item.exponent();
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
        if (polyLists.getSize() <= 0) {
            return;
        }
        if (polyLists.getSize() == 1) {
            multiPolyList = polyLists.getLast();
            return;
        }
        DoublyLinkedList<Term> result = null;
        boolean isFirst = true;
        for (DoublyLinkedList<Term> currentPoly : polyLists) {
            if (isFirst) {
                result = currentPoly;
                isFirst = false;
            } else {
                result = multiplyPolys(result, currentPoly);
            }
        }
        multiPolyList = result;
    }

    /**
     * Takes two list of polynomials and multiples them together and then returns that result
     * @param poly1 the first polynomial
     * @param poly2 the second polynomial
     * @return the result of the two polynomials
     */
    private DoublyLinkedList<Term> multiplyPolys(DoublyLinkedList<Term> poly1, DoublyLinkedList<Term> poly2) {
        DoublyLinkedList<Term> result = new DoublyLinkedList<>();
        for (Term termA : poly1) {
            int coefA = termA.coefficient();
            int powerA = termA.exponent();
            for (Term termB : poly2) {
                int coefB = termB.coefficient();
                int powerB = termB.exponent();
                int newCoef = coefA * coefB;
                int newPower = powerA + powerB;
                Term termC = new Term(newCoef, newPower);
                if (result.getSize() > 0) {
                    int index = 0;
                    boolean add  = false;
                    for (Term termD : result) {
                        add = compareTerms(termC, termD, result, index);
                        if (add) {
                            break;
                        }
                        index++;
                    }
                    if (!add) {
                        result.add(termC);
                    }
                } else {
                    result.add(termC);
                }
            }
        }
        return result;
    }

    /**
     * Prints the multiplied polynomials out
     */
    public void printMultiPoly() {
        System.out.print("Product: ");
        StringBuilder poly = new StringBuilder();
        String var;
        int index = 0;
        for (Term term : multiPolyList) {
            var = toString(term);
            if (index != 0) {
                if (var.indexOf("-") == 0) {
                    var = "- " + var.substring(1);
                } else {
                    var = "+ " + var;
                }
            }
            poly.append(var);
            index++;
        }
        System.out.println(poly);
    }
}
