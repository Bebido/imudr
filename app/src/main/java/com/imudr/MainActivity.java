package com.imudr;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.imudr.model.RawIMU;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    List<RawIMU> rawIMUS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice hc05 = adapter.getBondedDevices()
                .stream()
                .filter(bluetoothDevice -> "HC-05".equals(bluetoothDevice.getName()))
                .findFirst()
                .get();

        BluetoothSocket btSocket = null;
        try {
            hc05.getUuids();
            hc05.getBluetoothClass();
            btSocket = hc05.createInsecureRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        do {
            try {
                btSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!btSocket.isConnected());

        InputStream inputStream = null;
        try {
            inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner sc = new Scanner(inputStream);
        int limit = 50;
        int start = 0;

        while (sc.hasNextLine() && start < limit) {
            String rawRead = sc.nextLine();
            Log.i("logging", rawRead + "");
            saveRead(rawRead);
            start++;
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            btSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveRead(String rawRead) {
        if (rawRead.contains("a/g:")) {
            String[] tmp = rawRead.split(":");
            String[] data = tmp[1].split(",");
            RawIMU rawIMU = new RawIMU(
                    Integer.parseInt(data[0].trim()),
                    Integer.parseInt(data[1]),
                    Integer.parseInt(data[2]),
                    Integer.parseInt(data[3]),
                    Integer.parseInt(data[4]),
                    Integer.parseInt(data[5])
            );
            rawIMUS.add(rawIMU);
        }
    }

    public void openBluetoothSelector(View view) {
        Intent intent = new Intent(this, BluetoothSelectActivity.class);
        startActivity(intent);
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            //textView.setText(String.valueOf(msg.arg1));
            return false;
        }
    });

//    BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                deviceList.add(device.getName());
//            }
//        }
//    };

    private class Thread2 extends Thread {
        public void run() {
            for (int i = 0; i < 50; i++) {
                Message message = Message.obtain();
                message.arg1 = i;
                handler.sendMessage(message);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}