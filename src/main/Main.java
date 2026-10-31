package main;

import org.jetbrains.annotations.NotNull;
import practice.SolarEntropyGenerator;
import project.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Alejandro Borroni
 * @version 2026.10.31.10
 */
public class Main {
	public static void main(String[] args) {
		p4_sorting();
    }

	/**
	 * An attempt at true randomness
	 */
	public static void randNum() {
		try {
			SolarEntropyGenerator data = new SolarEntropyGenerator();
			System.out.println("Fetching live solar radiation data from NOAA...");
			String solarData = data.fetchSolarData();

            /* Combine space-weather data with nanosecond timing */
			long nanoTime = System.nanoTime();
			String rawEntropySource = solarData + "|nano:" + nanoTime;

            /* Generate the SHA-256 raw byte array */
			byte[] entropyBytes = data.generateSHA256Bytes(rawEntropySource);

            /* Initialize SecureRandom and inject the solar/time entropy as seed material */
			SecureRandom secureRandom = new SecureRandom();
			secureRandom.setSeed(entropyBytes);

            /* Generate a secure random number between 1 and 12 */
			int random1to12 = secureRandom.nextInt(12) + 1;

			System.out.println("\n--- Generation Successful ---");
			System.out.println("Timestamp Nanoseconds: " + nanoTime);
			System.out.println("Final Random Number (1-12): " + random1to12);

		} catch (Exception e) {
			System.err.println("Failed to execute entropy generation: " + e.getMessage());
			e.printStackTrace();
		}
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

	/**
	 * GCD main method
	 */
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

	/**
	 * main method for the TennisServingPuzzle
	 */
	public static void TSP_main(){
		double aProb = 0.6;
		double bProb = 0.75;
		TennisServingPuzzle TSP1 = new TennisServingPuzzle(aProb, bProb, 3);
		TSP1.printGame();
		/*
		* Video Url: https://frostburg.instructuremedia.com/embed/633cc225-e0b5-4b32-900a-4fe80db013ee
		*/
	}

	/**
	 * main method for P4_Sorting project
	 */
	public static void p4_sorting(){
		P4_Sorting p4 = new P4_Sorting();
		File file = new File("../P4_Sorting_Input.txt");
		int[] numArr = new int[100];
		Scanner sc;
		try {
			sc = new Scanner(file);
			for (int i = 0; sc.hasNextInt() || i < numArr.length; i++) {
				numArr[i] = sc.nextInt();
			}
			sc.close();
			int[] temp = numArr.clone();
			p4.InsertionSort(numArr);
			System.out.print("Insertion Sorting result – first 5 numbers: ");
			p4.get5LastnFirst(0, 4, numArr);
			System.out.print("Insertion Sorting result – last 5 numbers: ");
			p4.get5LastnFirst(95, 99, numArr);
			System.out.println("\nInsertionSort comparisons: " + p4.getInsSortComp());
			numArr = temp.clone();
            p4.mergeSort(numArr);
			System.out.print("\nMerge Sorting result – first 5 numbers: ");
			p4.get5LastnFirst(0, 4, numArr);
			System.out.print("Merge Sorting result – last 5 numbers: ");
			p4.get5LastnFirst(95, 99, numArr);
			System.out.println("\nMergeSort comparisons: " + p4.getMergeSortComp());
			numArr = temp.clone();
            p4.quickSort(numArr);
			System.out.print("\nQuick Sorting result – first 5 numbers: ");
			p4.get5LastnFirst(0, 4, numArr);
			System.out.print("Quick Sorting result – last 5 numbers: ");
			p4.get5LastnFirst(95, 99, numArr);
			System.out.println("\nQuickSort comparisons: " + p4.getQuickSortComp());
			numArr = temp.clone();
            p4.heapSort(numArr);
			System.out.print("\nHeap Sorting result – first 5 numbers: ");
			p4.get5LastnFirst(0, 4, numArr);
			System.out.print("Heap Sorting result – last 5 numbers: ");
			p4.get5LastnFirst(95, 99, numArr);
			System.out.println("\nHeapSort comparisons: " + p4.getHeapSortComp());
		} catch (FileNotFoundException e) {
			try {
				Path parentDir = localPath();
				System.err.printf("P4_Sorting_Input.txt was not found in parent directory. Place file in the %s folder and try again.", parentDir.getFileName().toString());
			}catch (URISyntaxException s) {
				s.printStackTrace();
			}
		}
	}

	/**
	 * Since I had taken the code from my P1_SVP class for finding the parent directory and just copy and pasted it, my IDE
	 * had recommended I instead extract the code into a method, as such this is here now for easier use
	 * @return the parent directory of the folder containing the project files
	 * @throws URISyntaxException if it was unable to properly get the URI and use it
	 */
	public static @NotNull Path localPath() throws URISyntaxException {
		Path currentPath = Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		Path parentDir = currentPath.getParent().getParent();
		String pathStr = currentPath.toString();
		if (pathStr.contains("out\\production")) {
			parentDir = currentPath.getParent().getParent().getParent().getParent();
		}
		System.out.println(parentDir.toString());
		return currentPath;
	}
}