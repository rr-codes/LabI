package com.eecs1021.PartA;

/**
 * Represents the coefficients of some quadratic equation {@code ax^2 + bx + c }
 * @param <N> the type of the coefficients (Integer, Double, ...)
 */
public class QuadraticCoefficients<N extends Number> {
    private final N a, b, c;

    QuadraticCoefficients(N a, N b, N c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public N getA() {
        return a;
    }

    public N getB() {
        return b;
    }

    public N getC() {
        return c;
    }

    @Override
    public String toString() {
        return String.format("QuadraticCoefficients{a=%s, b=%s, c=%s}", a, b, c);
    }
}