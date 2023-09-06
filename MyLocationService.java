package com.example.projcopy;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;

public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATE="com.example.projcopy.UPDATE_LOCATION";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!=null){
            final String action=intent.getAction();
            if(ACTION_PROCESS_UPDATE.equals(action)){
                LocationResult result =LocationResult.extractResult(intent);
                if(result!=null){
                    Location location=result.getLastLocation();
                    String location_string=new StringBuilder(""+location.getLatitude())
                            .append("/")
                            .append(location.getLongitude())
                            .toString();
                    try{
                        DonationsActivity.getInstance().updateTextView(location_string);

                    }catch (Exception ex){
                        Toast.makeText(context, location_string, Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }

    }
}