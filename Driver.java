import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String [] args) throws IOException {
        // constructor (no args)
        Polynomial defaultPoly = new Polynomial();
        System.out.println("Default Constructor Polynomial (expected: 0.0 + 0.0x1 + 0.0x2 + 0.0x3 + 0.0x4):");
        printPolynomial(defaultPoly);

        // custom constructor (coeff + exp)
        double[] coeff = {5, -3, 7};
        int[] exponents = {0, 2, 8};
        Polynomial customPoly = new Polynomial(coeff, exponents);
        System.out.println("\nCustom Constructor Polynomial (expected: 5.0 - 3.0x2 + 7.0x8):");
        printPolynomial(customPoly);

        // multiplication
        double[] coeff1 = {3, 4}; 
        int[] exp1 = {0, 1}; 
        Polynomial poly1 = new Polynomial(coeff1, exp1);

        double[] coeff2 = {1, 2}; 
        int[] exp2 = {0, 1}; 
        Polynomial poly2 = new Polynomial(coeff2, exp2);

        Polynomial multipliedPoly = poly1.multiply(poly2);
        System.out.println("\nMultiplication Result (expected: 3.0 + 10.0x1 + 8.0x2):");
        printPolynomial(multipliedPoly);

        // addition
        double[] coeff3 = {5, 3};
        int[] exp3 = {0, 1};
        Polynomial poly3 = new Polynomial(coeff3, exp3);

        double[] coeff4 = {2, 3, 4};
        int[] exp4 = {0, 1, 2};
        Polynomial poly4 = new Polynomial(coeff4, exp4);

        Polynomial addedPoly = poly3.add(poly4);
        System.out.println("\nAddition Result (expected: 7.0 + 6.0x1 + 4.0x2):");
        printPolynomial(addedPoly);
        // evaluation
        double x = 2.0;
        double evalResult = customPoly.evaluate(x);
        System.out.println("\nEvaluation of polynomial at x = " + x + " (expected: 1785.0): " + evalResult);

        // root detection
        double[] coeffRoot = {1, -2}; // Corresponds to 1 - 2x
        int[] expRoot = {0, 1};
        Polynomial rootPoly = new Polynomial(coeffRoot, expRoot);
        System.out.println("\nDoes polynomial have root at x = 1/2? (expected: true): " + rootPoly.hasRoot(0.5));
        System.out.println("Does polynomial have root at x = 3? (expected: false): " + rootPoly.hasRoot(3));

        // saveToFile
        String testFileName = "testPolynomial.txt";
        try {
            customPoly.saveToFile(testFileName);
            System.out.println("\nPolynomial saved to file " + testFileName);

            // testing constructor from file
            Polynomial filePoly = new Polynomial(new File(testFileName));
            System.out.println("Polynomial loaded from file (expected: 5.0 - 3.0x2 + 7.0x8):");
            printPolynomial(filePoly);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void printPolynomial(Polynomial p) {
        String polyString = p.polynomialToString(p);
        System.out.println(polyString);
    }
}