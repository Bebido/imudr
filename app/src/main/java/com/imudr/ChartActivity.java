package com.imudr;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.imudr.model.RawIMU;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private ArrayList<RawIMU> rawIMUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Bundle bundle = getIntent().getExtras();
        rawIMUS = bundle.getParcelableArrayList("rawIMUS");
    }
}