package com.imudr;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.imudr.model.RawIMU;

import java.util.ArrayList;

public class ChartRawActivity extends AppCompatActivity {

    private ArrayList<RawIMU> rawIMUS;
    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chart);

        Bundle bundle = getIntent().getExtras();
        rawIMUS = bundle.getParcelableArrayList("rawIMUS");

        chart = findViewById(R.id.chart1);
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
        ArrayList<Entry> valuesAccX = new ArrayList<>();
        ArrayList<Entry> valuesAccY = new ArrayList<>();
        ArrayList<Entry> valuesAccZ = new ArrayList<>();
        ArrayList<Entry> valuesGyroX = new ArrayList<>();
        ArrayList<Entry> valuesGyroY = new ArrayList<>();
        ArrayList<Entry> valuesGyroZ = new ArrayList<>();

        for (int i = 0; i < rawIMUS.size(); i++) {
            RawIMU rawIMU = rawIMUS.get(i);
            valuesAccX.add(new Entry(i, rawIMU.getAccX()));
            valuesAccY.add(new Entry(i, rawIMU.getAccY()));
            valuesAccZ.add(new Entry(i, rawIMU.getAccZ()));
            valuesGyroX.add(new Entry(i, rawIMU.getGyroX()));
            valuesGyroY.add(new Entry(i, rawIMU.getGyroY()));
            valuesGyroZ.add(new Entry(i, rawIMU.getGyroZ()));
        }

        LineDataSet accX = new LineDataSet(valuesAccX, "AccX");
        LineDataSet accY = new LineDataSet(valuesAccY, "AccY");
        LineDataSet accZ = new LineDataSet(valuesAccZ, "AccZ");
        LineDataSet gyroX = new LineDataSet(valuesGyroX, "GyroX");
        LineDataSet gyroY = new LineDataSet(valuesGyroY, "GyroY");
        LineDataSet gyroZ = new LineDataSet(valuesGyroZ, "GyroZ");

        setLineAndCircleColor(accX, Color.BLUE);
        setLineAndCircleColor(accY, Color.GREEN);
        setLineAndCircleColor(accY, Color.MAGENTA);
        setLineAndCircleColor(gyroX, Color.RED);
        setLineAndCircleColor(gyroY, Color.YELLOW);
        setLineAndCircleColor(gyroZ, Color.CYAN);

        dataSets.add(accX);
        dataSets.add(accY);
        dataSets.add(accZ);
        dataSets.add(gyroX);
        dataSets.add(gyroY);
        dataSets.add(gyroZ);
    }

    private void setLineAndCircleColor(LineDataSet dataSet, int color) {
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
    }
}