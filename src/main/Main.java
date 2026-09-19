package main;

import project.GreatestCommonDivisor;
import project.P1_SVP;
import static practice.JavaEntropyGenerator.getNextPseudoTrueRandom;

import java.io.File;
import java.util.Random;

/**
 * @author Alejandro Borroni
 * @version 2026.09.2.18
 */
public class Main {
	public static void main(String[] args) {
		GCD_main();
    }

	/**
	 * An attempt at true randomness
	 * Take the random number generated using some chaos and map it to a 1-12 range output, kinda like how
	 * a clock rotates its arms, it will rotate within the range of 1-12 no matter how big the number
	 * and stop at the point. E.g. 13 would be 1, 20 would be 8, and so on.
	 */
	public static void randNum() {
		int rawValue = getNextPseudoTrueRandom(); // Yields any 32-bit int
		int range = 12;

		int result = Math.floorMod(rawValue, range) + 1;
		System.out.println("Roll: " + result);
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
		Random random = new Random();
		long min = 1000;
		long max = 10000;
		for (int i = 1; i <= 3; i++) {
			long m = random.nextLong(max - min + 1) + min;
			long n = random.nextLong(max - min + 1) + min;
			System.out.printf("Iteration %d: m = %d, n = %d%n", i, m, n);
			long startTime = System.nanoTime();
			GCD.GCDEucli(m, n, startTime);
			long endTime = System.nanoTime();
			long durationNs = endTime - startTime;
			double durationMs = durationNs / 1_000_000.0;
			System.out.printf("Euclidean Algorithm: %.3f ms%n", durationMs);
			startTime = System.nanoTime();
			GCD.GCDPrimeFact(m, n, startTime);
			endTime = System.nanoTime();
			durationNs = endTime - startTime;
			durationMs = durationNs / 1_000_000.0;
			System.out.printf("Prime Factorization Algorithm: %.3f ms%n", durationMs);
			startTime = System.nanoTime();
			GCD.GCDBrutal(m, n, startTime);
			endTime = System.nanoTime();
			durationNs = endTime - startTime;
			durationMs = durationNs / 1_000_000.0;
			System.out.printf("Brutal Force Method: %.3f ms%n%n", durationMs);
			min *= 10;
			max *= 10;
		}
	}
}