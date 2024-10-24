package org.example;

public class FunctionCalculator {

    public static double calculate(String functionType, double x) {
        return switch (functionType) {
            case "e^(0.2x)" -> Math.exp(0.2 * x);
            case "2^(-1x)" -> Math.pow(2, -x);
            case "cos(x)" -> Math.cos(x);
            default -> 0.0;
        };
    }
}
