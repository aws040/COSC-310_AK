package project;

/**
 * Round for better holding and storing data of the rounds data
 *
 * @author Alejandro Borroni
 * @version 2026.10.16.10
 */
public record Round(int aScore, int bScore, String server, double probability, boolean aWin, String sequenceWon) {

    public Round {
        if (probability < 0 || probability > 1) {
            throw new IllegalArgumentException("probability must be between 0 and 1");
        }
    }
}
