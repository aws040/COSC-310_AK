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
 * @version 2026.09.1.13
 */
public class P1_SVP {
    LinkedList<LinkedList<String>> polyLists = new LinkedList<>();

    public void addPolys(File file) {
        Scanner sc;
        try {
            sc = new Scanner(file);
            sc.useDelimiter("Polynomial \\d+:\\s*|//\\s*");
            while (sc.hasNext()) {
                String chunk =sc.next();
                if (chunk.isEmpty()) {
                    continue;
                }
                Scanner sc2 = new Scanner(chunk);
                sc2.useDelimiter("\r\n");
                LinkedList<String> list = new LinkedList<>();
                while (sc2.hasNext()) {
                    list.add(sc2.next());
                }
                polyLists.add(list);
            }
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



}
