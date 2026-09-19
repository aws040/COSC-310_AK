package project;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class that will be used for finding the greatest common divisor for two numbers.
 * @author Alejandro Borroni
 * @version 2026.9.16.18
 */
public class GreatestCommonDivisor {
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

    private long GCDEucliRecur(long m, long n, long time) {
        if (n == 0) return 0;
        if (System.currentTimeMillis() - time > 10000) return -1;
        if (m%n == 0) return n;
        return GCDEucliRecur(n, m%n, time);
    }

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
            if (System.currentTimeMillis() - time > 10000) return -1;
            if (factorM.containsKey(entry.getKey())) {
                minExponent = Long.min(entry.getValue(), factorM.get(entry.getKey()));
                gcdResult = gcdResult * (long) Math.pow(entry.getKey(), minExponent);
            }
        }
        return gcdResult;
    }

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
                if (System.currentTimeMillis() - time > 10000) return true;
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
            if (System.currentTimeMillis() - time > 10000) return -1;
            if (m % i == 0 && n % i == 0) {
                return i;
            }
        }
        return 0;
    }
}
