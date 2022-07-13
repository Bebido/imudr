package com.imudr.calculation;

public class Triangle {
    private double a;
    private double h;

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double calcField() {
        return (a * h) / 2d;
    }
}
