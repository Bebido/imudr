package com.imudr.calculation;

import android.util.Log;

import com.imudr.model.Acc2gIMU;

import java.util.ArrayList;
import java.util.List;

public class MovementCalc {
    private static final double G = 9.80665d;

    public static String calcMovement(List<Acc2gIMU> acc2gIMU) {
        long timeOffset = acc2gIMU.get(0).getElapsedTime();
        long lastMeasurementTime = 0;
        double lastAcceleration, lastVelocity, velocity, distance;
        lastAcceleration = velocity = distance = 0d;
        SimpleFIRFilter simpleFIRFilter = new SimpleFIRFilter();
        List<Double> filtred = new ArrayList<>();

        for (Acc2gIMU measurement : acc2gIMU) {
            long timeDiff = measurement.getElapsedTime() - timeOffset - lastMeasurementTime;
            double currentAcceleration = simpleFIRFilter.filter(measurement.getAccX()) * G;
            filtred.add(currentAcceleration);
            Log.i("currentAcceleration", String.valueOf(currentAcceleration));

            lastVelocity = velocity;
            velocity += calcVelocityChange(currentAcceleration, lastAcceleration, (timeDiff / 1000d));
            if (timeDiff / 1000d > 1) {
                lastVelocity = velocity = 0.0d;
            }

            distance += ((velocity + lastVelocity) * (timeDiff / 1000d)) / 2.0d;
            lastAcceleration = currentAcceleration;
            lastMeasurementTime += timeDiff;
        }

        StringBuilder a = new StringBuilder();
        for (int i = 0; i < filtred.size(); i++) {
            a.append(filtred.get(i)).append("\t").append(acc2gIMU.get(i).getAccX() * G).append("\n");
        }
        a.toString();

        return distance + "[m]";
    }

    private static double calcVelocityChange(double currentAcceleration, double lastAcceleration, double timeDiff) {
        double velocity;

        if (sameAccelerationDirection(currentAcceleration, lastAcceleration))
            velocity = ((currentAcceleration + lastAcceleration) * timeDiff) / 2.0d;
        else {
            double accDiff = Math.abs(lastAcceleration - currentAcceleration);
            double baseRatio = Math.abs(lastAcceleration) / accDiff;

            Triangle lastTriangle = new Triangle();
            Triangle currentTriangle = new Triangle();

            lastTriangle.setH(Math.abs(lastAcceleration));
            lastTriangle.setA(baseRatio * timeDiff);

            currentTriangle.setH(Math.abs(currentAcceleration));
            currentTriangle.setA(timeDiff - lastTriangle.getA());

            if (lastAcceleration < 0)
                velocity = currentTriangle.calcField() - lastTriangle.calcField();
            else
                velocity = lastTriangle.calcField() - currentTriangle.calcField();
        }

        return velocity;
    }

    private static boolean sameAccelerationDirection(double currentAcceleration, double lastAcceleration) {
        return (currentAcceleration >= 0 && lastAcceleration >= 0)
                || (currentAcceleration <= 0 && lastAcceleration <= 0);
    }
}
