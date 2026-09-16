package project;

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
        if (m == 0 || n == 0) return 0;
        if (m < 0 || n < 0) return 0;
        return GCDEucliRecur(n, m%n, time);
    }

    private long GCDEucliRecur(long m, long n, long time) {
        if (n == 0) return 0;
        if (System.currentTimeMillis() - time > 10000) return -1;
        if (m%n == 0) return n;
        return GCDEucliRecur(n, m%n, time);
    }

    public long GCDPrimeFact(long m, long n, long time) {
        return 0;
    }
}
