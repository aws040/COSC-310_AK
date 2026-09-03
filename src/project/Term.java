package project;

/**
 * Term for better holding and storing data of the polynomials
 *
 * @author Alejandro Borroni
 * @version 2026.09.2.22
 */
public record Term(int coefficient, int exponent) {
    /**
     * Constructor for the term, must provide coefficients and exponents
     *
     * @param coefficient of the term
     * @param exponent    of the term
     */
    public Term {
        if (coefficient == 0) {
            throw new IllegalArgumentException("The coefficient cannot be 0");
        }
    }
}
