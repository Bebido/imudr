package com.imudr;

public class TrapezodialMethod {

    //funkcja dla ktorej obliczamy calke
    private static double func(double x) {
        return x * x + 3;
    }

    public static double calcIntegral(double lowBound, double highBound, int precision) {
        double calka, dx;

        dx = (highBound - lowBound) / (double) precision;

        calka = 0;
        for (int i = 1; i < precision; i++)
            calka += func(lowBound + i * dx);


        calka += (func(lowBound) + func(highBound)) / 2;
        calka *= dx;

        return calka;
    }

}
