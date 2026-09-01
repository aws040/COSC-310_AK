package main;

import project.P1_SVP;

import java.io.File;

/**
 * @author Alejandro Borroni
 * @version 2026.08.31.11
 */
public class Main {
	public static void main(String[] args) {
        P1_SVP p1 = new P1_SVP();
        File file = new File("../P1_SVP_Input.txt");
        p1.addPolys(file);
        //test
    }
}
