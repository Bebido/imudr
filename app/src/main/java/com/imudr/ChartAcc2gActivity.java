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
import com.imudr.model.RawIMU;

import java.util.ArrayList;

public class ChartAcc2gActivity extends AppCompatActivity {

    private ArrayList<RawIMU> rawIMUS;
    private LineChart chart;
    private ArrayList<Acc2gIMU> acc2gIMUS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chart_acc2g);

        Bundle bundle = getIntent().getExtras();
        rawIMUS = bundle.getParcelableArrayList("rawIMUS");

        for (RawIMU rawIMU : rawIMUS)
            acc2gIMUS.add(new Acc2gIMU(rawIMU));

        chart = findViewById(R.id.chart2);
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

        for (int i = 0; i < acc2gIMUS.size(); i++) {
            Acc2gIMU acc2gIMU = acc2gIMUS.get(i);
            valuesAccX.add(new Entry(i, acc2gIMU.getAccX()));
            valuesAccY.add(new Entry(i, acc2gIMU.getAccY()));
            valuesAccZ.add(new Entry(i, acc2gIMU.getAccZ()));
        }

        LineDataSet accX = new LineDataSet(valuesAccX, "AccX");
        LineDataSet accY = new LineDataSet(valuesAccY, "AccY");
        LineDataSet accZ = new LineDataSet(valuesAccZ, "AccZ");

        setLineAndCircleColor(accX, Color.BLUE);
        setLineAndCircleColor(accY, Color.GREEN);
        setLineAndCircleColor(accY, Color.MAGENTA);

        dataSets.add(accX);
        dataSets.add(accY);
        dataSets.add(accZ);
    }

    private void setLineAndCircleColor(LineDataSet dataSet, int color) {
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
    }
}