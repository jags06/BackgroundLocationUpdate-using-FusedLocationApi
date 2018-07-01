package com.locationtracking.arunkumar.ebackgroundlocationtrackingtask;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button mStartService, mStopService;
    private static final int LOCATION_PERMISSIONS_REQUEST = 1100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartService = findViewById(R.id.startservice);
        mStopService = findViewById(R.id.stopService);
        gpsStatusCheck();
        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED)
                        getPermissionToLocationRequest();

                    else {
                        startLocationTracking();
                    }
                } else {
                    startLocationTracking();
                }
            }

        });
        mStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LocationUtil.isServiceRunning(MainActivity.this, BackGroundLocationService.class)) {
                    stopService(new Intent(MainActivity.this, BackGroundLocationService.class));
                }else{
                    Toast.makeText(MainActivity.this, "Location Tracking has been stopped !", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToLocationRequest() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSIONS_REQUEST);
    }

    public void gpsStatusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void startLocationTracking() {
        if (!LocationUtil.isServiceRunning(MainActivity.this, BackGroundLocationService.class)) {
            startService(new Intent(MainActivity.this, BackGroundLocationService.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationTracking();

                } else {
                    Toast.makeText(MainActivity.this, "Location Required Enabled this feature", Toast.LENGTH_LONG).show();
                }
            }

        }

    }
}
