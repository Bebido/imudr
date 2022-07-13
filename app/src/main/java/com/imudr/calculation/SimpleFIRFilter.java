package com.imudr.calculation;

public class SimpleFIRFilter {
    private static final Integer MAGNITUDE = 8;

    private final double[] h;
    private int hIndex = 0;
    private boolean filled;

    public SimpleFIRFilter() {
        h = new double[MAGNITUDE];
    }

    public double filter(double value) {
        double retVal = 0d;

        h[hIndex] = value;
        hIndex = ++hIndex % MAGNITUDE;

        if (filled) {
            for (int i = 0; i < MAGNITUDE; i++)
                retVal += (h[i] / (double) MAGNITUDE);
        }

        if (!filled && hIndex == MAGNITUDE - 1)
            filled = true;

        return retVal;
    }
}
