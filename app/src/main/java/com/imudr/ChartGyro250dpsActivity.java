package com.imudr;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.imudr.model.Acc2gIMU;
import com.imudr.model.Gyro250dpsIMU;
import com.imudr.model.RawIMU;

import java.util.ArrayList;

public class ChartGyro250dpsActivity extends AppCompatActivity {

    private ArrayList<RawIMU> rawIMUS;
    private LineChart chart;
    private ArrayList<Gyro250dpsIMU> gyro250dpsIMUS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chart_gyro250dps);

        Bundle bundle = getIntent().getExtras();
        rawIMUS = bundle.getParcelableArrayList("rawIMUS");

        for (RawIMU rawIMU : rawIMUS)
            gyro250dpsIMUS.add(new Gyro250dpsIMU(rawIMU));

        chart = findViewById(R.id.chart);
        fillChartWithRawIMUData();
    }

    private void fillChartWithRawIMUData() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        fillDataSetsWithRawIMUS(dataSets);

        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    private void fillDataSetsWithRawIMUS(ArrayList<ILineDataSet> dataSets) {
        ArrayList<Entry> valuesGyroX = new ArrayList<>();
        ArrayList<Entry> valuesGyroY = new ArrayList<>();
        ArrayList<Entry> valuesGyroZ = new ArrayList<>();

        for (int i = 0; i < gyro250dpsIMUS.size(); i++) {
            Gyro250dpsIMU gyro250dpsIMU = gyro250dpsIMUS.get(i);
            valuesGyroX.add(new Entry(i, gyro250dpsIMU.getGyroX()));
            valuesGyroY.add(new Entry(i, gyro250dpsIMU.getGyroY()));
            valuesGyroZ.add(new Entry(i, gyro250dpsIMU.getGyroZ()));
        }

        LineDataSet gyroX = new LineDataSet(valuesGyroX, "GyroX");
        LineDataSet gyroY = new LineDataSet(valuesGyroY, "GyroY");
        LineDataSet gyroZ = new LineDataSet(valuesGyroZ, "GyroZ");

        setLineAndCircleColor(gyroX, Color.RED);
        setLineAndCircleColor(gyroY, Color.YELLOW);
        setLineAndCircleColor(gyroZ, Color.CYAN);

        dataSets.add(gyroX);
        dataSets.add(gyroY);
        dataSets.add(gyroZ);
    }

    private void setLineAndCircleColor(LineDataSet dataSet, int color) {
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
    }
}