package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.List;

public class GPStracker extends ActivityCompat implements LocationListener {
    Context context;

    public GPStracker(Context c) {
        context = c;
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();

    }

    public Location getLocation() {
        LocationManager lm;
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context,"Permission Not Granted", Toast.LENGTH_SHORT).show();
            return  null;
        }
            Location l = lm.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }
//    public Location getLocation(){
//        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
//        {
//            Toast.makeText(context,"Permission Not Granted", Toast.LENGTH_SHORT).show();
//            return  null;
//        }
//        LocationManager lm;
//        lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
//        boolean isGPSEnabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        if(isGPSEnabled){
//            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
//            Location l= lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            return l;
//        }
//        else{
//            Toast.makeText(context,"Please enable GPS", Toast.LENGTH_LONG).show();
//
//        }
//        return null;
//    }
    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
