package com.google.maps.android;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Timer;
import java.util.TimerTask;

public class ConnectActivity extends AppCompatActivity implements BLEManager {

    private static final String TAG = "ConnectActivity";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private static int REQUEST_ENABLE_BT = 10001;

    private static final String DEVICE_ID = "4afb720a-5214-4337-841b-d5f954214877";
    private static final String CHARACTERISTIC_ID = "8bacc104-15eb-4b37-bea6-0df3ac364199";//"53b3d959-7dd3-4839-94e1-7b0eaea9aac2";

    private BLEPeripheral mPeripheral;

    private EditText mInput;
    private TextView mLogger;
    private Button mSendData;
    private Button mReadButton;
    private MediaPlayer salamis;

    private MediaPlayer salamis2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

       salamis = MediaPlayer.create(this, R.raw.salamisound);
       salamis2 = MediaPlayer.create(this, R.raw.salamisound1);





        mInput = (EditText) findViewById(R.id.input);
        mLogger = (TextView) findViewById(R.id.log);
        mLogger.setMovementMethod(new ScrollingMovementMethod());
        mSendData = (Button) findViewById(R.id.send);
        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPeripheral.writeCharacteristic(CHARACTERISTIC_ID, mInput.getText().toString());
                mInput.setText("");
            }
        });

        mReadButton = (Button) findViewById(R.id.read);
        mReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPeripheral.readCharacteristic(CHARACTERISTIC_ID);


            }
        });

        mPeripheral = new BLEPeripheral(this, DEVICE_ID);
    }
    public void waitOnBluetoothConnection() {
        (new Timer()).scheduleAtFixedRate(new TimerTask() {
            public void run() { ConnectActivity.this.mPeripheral.readCharacteristic("8bacc104-15eb-4b37-bea6-0df3ac364199"); }
        },  0L, 1000L);
        this.mPeripheral.readCharacteristic("8bacc104-15eb-4b37-bea6-0df3ac364199");
    }

    public MediaPlayer getSound(int param){

        if (param==1){
            return salamis;
        }
        if (param==2){
            return salamis2;
        }else{
            return null;
        }}


    @Override
    public void log(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                android.util.Log.d(TAG, message);
                mLogger.setText(String.format("%s\n%s", message, mLogger.getText().toString()));
            }
        });
    }

    @Override
    public void onConnectionStateChange(int newState) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void enableBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    @Override
    public void onConnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //mSendData.setEnabled(true);
                //mReadButton.setEnabled(true);
                waitOnBluetoothConnection();
            }
        });
        mPeripheral.subscribe(CHARACTERISTIC_ID, valueChanged);
    }

    private Command<String> valueChanged = new Command<String>() {
        @Override
        public void execute(String data) {
            log("Value result: " + data);
        }
    };

    @Override
    public void onDisconnected() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mSendData.setEnabled(false);
                mReadButton.setEnabled(false);
            }
        });
    }

    @Override
    public boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPeripheral.scanForDevice();
                } else {
                    log("Permission denied. App is a brick");
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
