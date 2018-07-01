package com.locationtracking.arunkumar.ebackgroundlocationtrackingtask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       /* if (intent.getAction().matches("android.location.PROVIDERS_CHANGED") && !LocationUtil.isServiceRunning(context,BackGroundLocationService.class)) {
            Intent pushIntent = new Intent(context, BackGroundLocationService.class);
            context.startService(pushIntent);
            }*/
    }
}

