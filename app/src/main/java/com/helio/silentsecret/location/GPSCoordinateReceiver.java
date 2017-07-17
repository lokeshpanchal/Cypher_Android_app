package com.helio.silentsecret.location;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import com.helio.silentsecret.R;
import com.helio.silentsecret.callbacks.GPSCallback;

public class GPSCoordinateReceiver implements LocationListener {

    private static int MIN_DISTANCE = 50000;
    private static int MIN_TIME = 100000;
    private LocationManager locationManager;
    private Location currentLocation;
    boolean isGpsEnabled = true;
    boolean haveGPS = false;
    private Context mContext;
    private GPSCallback callbackData;
    private static AlertDialog connectDialog;

    private boolean emulator = false;

    public GPSCoordinateReceiver(Context context, GPSCallback callback) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mContext = context;
        callbackData = callback;

        if (emulator) {
            Location zero = new Location("ZERO");
            zero.setLatitude(0.0);
            zero.setLongitude(0.0);
            callback.onReceive(zero);
            return;
        }

        subscribe();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null && currentLocation == null) {
            currentLocation = location;
            callbackData.onReceive(location);
        } else if (currentLocation != null) {
            callbackData.onReceive(currentLocation);
        } else {
            callbackData.onReceive(null);
        }
    }


    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    private void initBestProvider() {
        Location location = null;

        boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!gpsIsEnabled)
            enableGPS();



        if (currentLocation == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                haveGPS = false;
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            } else {
                haveGPS = true;
            }
        }

        currentLocation = location;
    }

    private void enableGPS() {

        if (connectDialog != null)
            if (connectDialog.isShowing())
                return;

        connectDialog = new AlertDialog.Builder(mContext).create();
        connectDialog.setTitle(mContext.getString(R.string.warning));
        connectDialog.setMessage(mContext.getString(R.string.please_enable_gps));

        connectDialog.setButton(DialogInterface.BUTTON_POSITIVE, mContext.getString(R.string.settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(myIntent);
            }
        });

        connectDialog.setButton(DialogInterface.BUTTON_NEGATIVE, mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callbackData.onReceive(currentLocation);
                dialog.dismiss();
            }
        });

        connectDialog.show();
    }

    public void removeCallback() {
        if (locationManager != null)
            locationManager.removeUpdates(this);
    }

    public void subscribe() {
        initBestProvider();
        try {
            if (isGpsEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        } catch (IllegalArgumentException e) {

        }
    }

}
