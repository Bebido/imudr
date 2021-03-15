package com.imudr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.imudr.ui.main.BluetoothSelectorFragment;

public class BluetoothSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth_selector_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, BluetoothSelectorFragment.newInstance())
                    .commitNow();
        }
    }
}