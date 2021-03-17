package com.imudr.model;

public class Gyro250dpsIMU {
    //raw data is in range +-32768 and gyro IMU is set to +/250dps, so we need to divide all raw values
    //by (32768/250) = 16384 to get values in g
    private static final float DIVIDE_VALUE = 131.072f;

    private float gyroX;
    private float gyroY;
    private float gyroZ;

    public Gyro250dpsIMU(RawIMU rawIMU) {
        this.gyroX = Float.parseFloat(String.valueOf(rawIMU.getGyroX())) / DIVIDE_VALUE;
        this.gyroY = Float.parseFloat(String.valueOf(rawIMU.getGyroY())) / DIVIDE_VALUE;
        this.gyroZ = Float.parseFloat(String.valueOf(rawIMU.getGyroZ())) / DIVIDE_VALUE;
    }

    public float getGyroX() {
        return gyroX;
    }

    public void setGyroX(float gyroX) {
        this.gyroX = gyroX;
    }

    public float getGyroY() {
        return gyroY;
    }

    public void setGyroY(float gyroY) {
        this.gyroY = gyroY;
    }

    public float getGyroZ() {
        return gyroZ;
    }

    public void setGyroZ(float gyroZ) {
        this.gyroZ = gyroZ;
    }
}
