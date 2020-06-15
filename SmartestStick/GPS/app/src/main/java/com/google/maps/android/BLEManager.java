package com.google.maps.android;


import android.content.Context;
import android.media.MediaPlayer;

public interface BLEManager {
    void log(String message);
    void onConnectionStateChange(int newState);
    Context getContext();
    void enableBluetooth();
    void onConnected();
    void onDisconnected();
    boolean checkPermission();
    MediaPlayer getSound(int param);
}
