package com.imudr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RawIMU implements Parcelable {
    public RawIMU() {
    }

    public RawIMU(int accX, int accY, int accZ, int gyroX, int gyroY, int gyroZ) {
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.gyroX = gyroX;
        this.gyroY = gyroY;
        this.gyroZ = gyroZ;
    }

    private int accX;
    private int accY;
    private int accZ;
    private int gyroX;
    private int gyroY;
    private int gyroZ;

    protected RawIMU(Parcel in) {
        accX = in.readInt();
        accY = in.readInt();
        accZ = in.readInt();
        gyroX = in.readInt();
        gyroY = in.readInt();
        gyroZ = in.readInt();
    }

    public static final Creator<RawIMU> CREATOR = new Creator<RawIMU>() {
        @Override
        public RawIMU createFromParcel(Parcel in) {
            return new RawIMU(in);
        }

        @Override
        public RawIMU[] newArray(int size) {
            return new RawIMU[size];
        }
    };

    public int getAccX() {
        return accX;
    }

    public void setAccX(int accX) {
        this.accX = accX;
    }

    public int getAccY() {
        return accY;
    }

    public void setAccY(int accY) {
        this.accY = accY;
    }

    public int getAccZ() {
        return accZ;
    }

    public void setAccZ(int accZ) {
        this.accZ = accZ;
    }

    public int getGyroX() {
        return gyroX;
    }

    public void setGyroX(int gyroX) {
        this.gyroX = gyroX;
    }

    public int getGyroY() {
        return gyroY;
    }

    public void setGyroY(int gyroY) {
        this.gyroY = gyroY;
    }

    public int getGyroZ() {
        return gyroZ;
    }

    public void setGyroZ(int gyroZ) {
        this.gyroZ = gyroZ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(accX);
        dest.writeInt(accY);
        dest.writeInt(accZ);
        dest.writeInt(gyroX);
        dest.writeInt(gyroY);
        dest.writeInt(gyroZ);
    }
}
