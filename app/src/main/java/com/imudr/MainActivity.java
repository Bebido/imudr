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
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.imudr.model.RawIMU;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    private static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String BT_DEVICE_NAME = "HC-05";
    ArrayList<RawIMU> rawIMUS = new ArrayList<>();
    private Button presentDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presentDataButton = findViewById(R.id.presentDataButton);
        presentDataButton.setEnabled(false);
    }

    public void readIMU(View view) {
        presentDataButton.setEnabled(false);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice hc05 = getNamedBtDevice(adapter, BT_DEVICE_NAME);
        if (hc05 == null) {
            Toast.makeText(this, "Device with name " + BT_DEVICE_NAME + " not found", LENGTH_LONG).show();
            return;
        }

        BluetoothSocket btSocket = createConnectedBtSocket(hc05);

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

        while (sc.hasNextLine() && start <= limit) {
            String rawRead = sc.nextLine();
            Log.i("logging", rawRead + "");
            saveRead(rawRead);
            start++;
        }

        try {
            inputStream.close();
            btSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        presentDataButton.setEnabled(true);
    }

    private BluetoothSocket createConnectedBtSocket(BluetoothDevice hc05) {
        int limit = 5;
        int connectionCounter = 0;

        BluetoothSocket btSocket = null;
        do {
            try {
                btSocket = hc05.createInsecureRfcommSocketToServiceRecord(mUUID);
                btSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectionCounter++;
        } while (!btSocket.isConnected() && limit > connectionCounter);

        if (!btSocket.isConnected()) {
            try {
                btSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return btSocket.isConnected() ? btSocket : null;
    }

    private BluetoothDevice getNamedBtDevice(BluetoothAdapter adapter, String btDeviceName) {
        Optional<BluetoothDevice> btDevice = adapter.getBondedDevices()
                .stream()
                .filter(bluetoothDevice -> btDeviceName.equals(bluetoothDevice.getName()))
                .findFirst();

        return btDevice.orElse(null);
    }

    public void presentData(View view) {
        Intent intent = new Intent(this, ChartRawActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("rawIMUS", rawIMUS);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void presentAcc2g(View view) {
        Intent intent = new Intent(this, ChartAcc2gActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("rawIMUS", rawIMUS);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void presentGyro250dps(View view) {
        Intent intent = new Intent(this, ChartGyro250dpsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("rawIMUS", rawIMUS);
        intent.putExtras(bundle);
        startActivity(intent);
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