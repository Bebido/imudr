package com.imudr.model;

public class Acc2gIMU {
    //raw data is in range +-32768 and acc IMU is set to +/2g, so we need to divide all raw values
    //by (32768/2) = 16384 to get values in g
    private static final Long DIVIDE_VALUE = 16_384L;

    private long elapsedTime;
    private float accX;
    private float accY;
    private float accZ;

    public Acc2gIMU(RawIMU rawIMU) {
        this.elapsedTime = rawIMU.getElapsedTime();
        this.accX = Float.parseFloat(String.valueOf(rawIMU.getAccX())) / DIVIDE_VALUE;
        this.accY = Float.parseFloat(String.valueOf(rawIMU.getAccY())) / DIVIDE_VALUE;
        this.accZ = Float.parseFloat(String.valueOf(rawIMU.getAccZ())) / DIVIDE_VALUE;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public float getAccX() {
        return accX;
    }

    public void setAccX(float accX) {
        this.accX = accX;
    }

    public float getAccY() {
        return accY;
    }

    public void setAccY(float accY) {
        this.accY = accY;
    }

    public float getAccZ() {
        return accZ;
    }

    public void setAccZ(float accZ) {
        this.accZ = accZ;
    }
}
