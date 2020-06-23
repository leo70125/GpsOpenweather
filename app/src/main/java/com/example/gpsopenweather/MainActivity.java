package com.example.gpsopenweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity implements LocationListener {
    static final int MIN_TIME = 5000;
    static final float MIN_DIST = 0;
    LocationManager mgr;
    TextView tv_1;
    TextView tv_2;
    boolean isGPSEnabled;
    boolean isNETworkEnabled;
    String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);

        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);

        checkPermission();

    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_2.setText("未取得");
        enableLocationUpdates(true);

        String str = "GPS定位:" + (isGPSEnabled?"開啟":"關閉");
        str += "\n網路定位:" + (isNETworkEnabled?"開啟":"關閉");

        tv_1.setText(str);
    }

    @Override
    protected void onPause() {
        super.onPause();
        enableLocationUpdates(false);
    }

    @Override
    public void onLocationChanged(Location location) {
//        String str = "定位提供者:"+location.getProvider();
//        String str2 = "定位提供者:"+location.getProvider();
////        str += String.format("\n緯度:%.5f\n經度:%.5f\n高度:%.2f公尺",location.getLatitude(), location.getLongitude(), location.getAltitude());
//        str += String.format("\n緯度:%.5f\n高度:%.2f公尺",location.getLatitude(), location.getAltitude());
//        str2 += String.format("經度:%.5f\n高度:%.2f公尺", location.getLongitude(), location.getAltitude());
//        Intent intent2 = new Intent(MainActivity.this,Openweather.class);
//        intent2.putExtra("str",str);
//        intent2.putExtra("str2",str2);
        String str = "定位提供者:"+location.getProvider();
        str += String.format("\n緯度:%.5f\n經度:%.5f\n高度:%.2f公尺",location.getLatitude(), location.getLongitude(), location.getAltitude());
        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());

        Intent intent2 = new Intent(MainActivity.this,Openweather.class);
        intent2.putExtra("lat", lat);
        Log.e(TAG,"lat =" + lat);
        intent2.putExtra("lon",lon);
        Log.e(TAG,"lon =" + lon);
        startActivity(intent2);
        tv_2.setText(str);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void setup(View v){
        Intent it = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(it);
    }

    private  void checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},200);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==200){
            if(grantResults.length >= 1 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"需要定位權限",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void enableLocationUpdates(boolean isTurnOn){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(isTurnOn){
                isGPSEnabled = mgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNETworkEnabled = mgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (!isGPSEnabled && !isNETworkEnabled)
                {
                    Toast.makeText(this,"GPS未開啟,麻煩請打開GPS",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(this,"取得定位中",Toast.LENGTH_SHORT).show();
                    if(isGPSEnabled)
                        mgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,this);

                }
            }
            else {
                mgr.removeUpdates(this);
            }


        }
    }
}
