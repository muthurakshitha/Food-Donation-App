package com.example.projcopy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class DonationsActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button btn;
    private ImageView button;
    private TextView text;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    static DonationsActivity instance;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;


    public static DonationsActivity getInstance() {
        return instance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);
        text=findViewById(R.id.txt_location);
        btn=findViewById(R.id.click);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DonationsActivity.this,MainActivity4.class);
                startActivity(intent);
            }
        });
        instance = this;
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        updateLocation();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(DonationsActivity.this, "Permission Denied!!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

//        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                start();
//                if (ContextCompat.checkSelfPermission(
//                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(DonationsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                            REQUEST_CODE_LOCATION_PERMISSION);
//
//
//                }else{
//                    startLocationService();
//                }
//            }
//        });
//        findViewById(R.id.end).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                stopLocationService();
//            }
//        });
        button = findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonationsActivity.this, AddPostactivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Donation selected
        bottomNavigationView.setSelectedItemId(R.id.nav_donations);
        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_donations:
                        return true;
                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_adddonations:
                        startActivity(new Intent(getApplicationContext(),AddPostactivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    private void updateLocation() {
        buildLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingIntent());
    }

    private PendingIntent getPendingIntent() {
        Intent intent=new Intent(this,MyLocationService.class);
        intent.setAction(MyLocationService.ACTION_PROCESS_UPDATE);
        return PendingIntent.getBroadcast(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void buildLocationRequest() {
        locationRequest=new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

    public void updateTextView(String value){
        DonationsActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);

            }
        });
    }

//    public void start(){
//        Toast.makeText(this,"Location Service Started",Toast.LENGTH_SHORT).show();
//    }

// @Override
//    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==REQUEST_CODE_LOCATION_PERMISSION&&grantResults.length>0){
//            startLocationService();
//        }
//        else{
//            Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
//        }
//    }

//    private boolean isLocationServiceRunning(){
//        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        if(activityManager!=null){
//            for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
//                if(LocationService.class.getName().equals(service.service.getClassName())){
//                    if(service.foreground){
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//        return false;
//    }
//    private void startLocationService(){
//        if(!isLocationServiceRunning()){
//            Intent intent=new Intent(getApplicationContext(),LocationService.class);
//            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
//            startService(intent);
//            Toast.makeText(this,"Location Service Started",Toast.LENGTH_SHORT).show();
//        }
//    }
//    private void stopLocationService(){
//        if(!isLocationServiceRunning()){
//            Intent intent=new Intent(getApplicationContext(),LocationService.class);
//            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
//            startService(intent);
//            Toast.makeText(this,"Location service stopped",Toast.LENGTH_SHORT).show();
//        }
//    }


}