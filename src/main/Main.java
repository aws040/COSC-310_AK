package main;

import project.GreatestCommonDivisor;
import project.P1_SVP;

import java.io.File;

/**
 * @author Alejandro Borroni
 * @version 2026.09.2.18
 */
public class Main {
	public static void main(String[] args) {
		GCD_main();
    }
	
	/**
	 * P1_SVP main method.
	 */
	public static void p1_svp() {
		P1_SVP p1 = new P1_SVP();
        File file = new File("../P1_SVP_Input.txt");
        p1.addPolys(file);
        p1.printPolys();
        p1.multiplyPolys();
        p1.printMultiPoly();
        /*
         * Video Url: https://frostburg.instructuremedia.com/embed/3e30e244-fc38-48e0-af28-44f56bfc4149
         */
	}

	public static void GCD_main(){
		GreatestCommonDivisor GCD = new GreatestCommonDivisor();
		long m = 57398969; //(long) (Math.random() * 10000);
		long n = 480593799;
		System.out.printf("Iteration 1: m = %d, n = %d%n", m, n);
		long time = System.currentTimeMillis();
		GCD.GCDEucli(m, n, time);
		System.out.printf("Euclidean Algorithm: %d milliseconds%n", System.currentTimeMillis() - time);
		time = System.currentTimeMillis();
	}
}
