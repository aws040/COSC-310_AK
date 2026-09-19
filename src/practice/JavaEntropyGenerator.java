package practice;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicBoolean;

public class JavaEntropyGenerator {

    /**
     * Takes the randomness and the slight variable of the CPU since it is never exact 1 millisecond,
     * it is a slight range, and it uses that slight range to get its number.
     * Then using the system time, it gets the first point it started, and then the difference of the
     * times is taken to the power of the counter.
     * @return the number that was produced by the slight jitter of the CPU
     */
    private static long harvestThreadJitter() {
        AtomicBoolean stop = new AtomicBoolean(false);
        long[] counter = new long[1];

        Thread worker = new Thread(() -> {
            while (!stop.get()) {
                counter[0]++;
            }
        });

        worker.start();

        long start = System.nanoTime();
        try {
            Thread.sleep(1);
        } catch (InterruptedException ignored) {}

        stop.set(true);
        try {
            worker.join();
        } catch (InterruptedException ignored) {}

        long end = System.nanoTime();

        return (end - start) ^ counter[0];
    }

    /**
     * Takes the number made by the randomness of the CPU and the among of free memory and total memory
     * along with runtime as they are almost impossible to replicate at any given moment
     * and then feed that data into SHA-256 as it will then get an int from all of that and return it.
     * @return the int that was run through SHA-256
     */
    public static int getNextPseudoTrueRandom() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            long jitter = harvestThreadJitter();
            long nanoTime = System.nanoTime();

            Runtime runtime = Runtime.getRuntime();
            long freeMemory = runtime.freeMemory();
            long totalMemory = runtime.totalMemory();

            ByteBuffer buffer = ByteBuffer.allocate(32);
            buffer.putLong(jitter);
            buffer.putLong(nanoTime);
            buffer.putLong(freeMemory);
            buffer.putLong(totalMemory);

            byte[] hash = digest.digest(buffer.array());

            return ByteBuffer.wrap(hash).getInt();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm missing", e);
        }
    }
}