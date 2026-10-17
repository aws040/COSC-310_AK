package project;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class that will be used for finding the greatest common divisor for two numbers.
 * @author Alejandro Borroni
 * @version 2026.9.16.18
 */
public class GreatestCommonDivisor {
	/**
	 * Will return the GCD using the euclidean method. It will also return -1 if it ever goes over 10 seconds
	 * @param m taken as a long
	 * @param n taken as a long
	 * @param time taken as the current system time.
	 * @return the GCD
	 */
    public long GCDEucli(long m, long n, long time) {
        if (m < n) {
            long temp = n;
            n = m;
            m = temp;
        }
        if (n == 0) return m;
        if (m == 0) return n;
        if (n == m) return n;
        return GCDEucliRecur(n, m%n, time);
    }

    /**
     * The private version made for recursion
     * @param m taken as a long
     * @param n taken as a long
     * @param time taken as the current system time.
     * @return the GCD
     */
    private long GCDEucliRecur(long m, long n, long time) {
        if (n == 0) return 0;
        if (System.nanoTime() - time > 10000000000L) return -1;
        if (m%n == 0) return n;
        return GCDEucliRecur(n, m%n, time);
    }

    /**
     * Will return the GCD using the prime factorization method. It will also return -1 if it ever goes over 10 seconds
     * @param m taken as a long
     * @param n taken as a long
     * @param time taken as the current system time.
     * @return the GCD
     */
    public long GCDPrimeFact(long m, long n, long time) {
        if (n == 0) return m;
        if (m == 0) return n;
        if (n == m) return n;

        LinkedHashMap<Long, Long> factorN = new LinkedHashMap<>();
        if (getPrimeFactor(n, time, factorN)) return -1;
        LinkedHashMap<Long, Long> factorM = new LinkedHashMap<>();
        if (getPrimeFactor(m, time, factorM)) return -1;
        long minExponent;
        long gcdResult = 1;
        for (Map.Entry<Long, Long> entry : factorN.entrySet()) {
            if (System.nanoTime() - time > 10000000000L) return -1;
            if (factorM.containsKey(entry.getKey())) {
                minExponent = Long.min(entry.getValue(), factorM.get(entry.getKey()));
                gcdResult = gcdResult * (long) Math.pow(entry.getKey(), minExponent);
            }
        }
        return gcdResult;
    }

    /**
     * The private method for the prime factorization method, it takes a long and the time, and it a hashmap to store the primes
     * and with that all if gets all the lowest primes possible out from a number and returns true if it was able to, and false if it ran
     * over 10 seconds.
     * @param n taken as a long
     * @param time taken as the current system time
     * @param factorN take as an emtpy hashmap for storing the primes.
     * @return a boolean if it added them to the entire map
     */
    private boolean getPrimeFactor(long n, long time, LinkedHashMap<Long, Long> factorN) {
        while (n % 2 == 0) {
            if (factorN.containsKey(2L)) {
                factorN.put(2L, factorN.get(2L) + 1);
            }else {
                factorN.put(2L, 1L);
            }
            n = n / 2;
        }
        long d = 3;
        while (d * d <= n) {
            while (n % d == 0) {
                if (System.nanoTime() - time > 10000000000L) return true;
                if (factorN.containsKey(d)) {
                    factorN.put(d, factorN.get(d) + 1);
                } else {
                    factorN.put(d, 1L);
                }
                n = n / d;
            }
            d += 2;
        }

        if (n > 1 && factorN.containsKey(n)) {
            factorN.put(n, factorN.get(n) + 1);
        } else if (n > 1) {
            factorN.put(n, 1L);
        }
        return false;
    }

    /**
     * The brutal force method, it counts down from the lowest prime and tries to see if both are divisible
     * by that number
     * @param m taken as a long
     * @param n taken as a long
     * @param time taken as teh current time
     * @return the GCD
     */
    public long GCDBrutal(long m, long n, long time) {
        if (n == 0) return m;
        if (m == 0) return n;
        if (n == m) return n;
        if (n > m) {
            long temp = n;
            n = m;
            m = temp;
        }
        for (long i = n; i > 0; i--) {
            if (System.nanoTime() - time > 10000000000L) return -1;
            if (m % i == 0 && n % i == 0) {
                return i;
            }
        }
        return 0;
    }
}
