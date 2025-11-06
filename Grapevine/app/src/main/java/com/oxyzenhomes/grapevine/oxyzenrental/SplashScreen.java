package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.FirebaseApp;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.request.RequestBean;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashScreen extends Activity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static int SPLASH_TIMER = 4000;
    private AppPreferences appPreferences;
    Intent intent;
    Bundle bundle;
    Handler handler;
    private RequestBean requestBean;
    NetworkInfo netInfo;
    Boolean HomePageOpened=false;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest;
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private Location location;
    ConnectionDetector cd;
    private Boolean IsLoginOpen;
    /**
     * The used for interval for location updates.
     */
    private static final long UPDATE_INTERVAL = 1 * 1000; // 1 km  interval

    /**
     * The fastest interval to update for active location updates.
     */
    private static final long FASTEST_INTERVAL = UPDATE_INTERVAL / 2;

    /**
     * The max time to wait for location update.
     */
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 3;

    private ArrayList<String> permissionsRejected = new ArrayList<>();
    String AlertType=null;
    String FeedChannelID=null,UserName,ImageUrl,ChannelFeedChannelID,FeedID;
    int NOTIFY_ID;
    Activity mActivity;
    MediaPlayer mMediaPlayer;
    Dialog dialog=null;
    String Message="";
    String WebLink="";

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startActivity(intent);
        finish();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        appPreferences = new AppPreferences(SplashScreen.this);
        setContentView(R.layout.activity_splash);
        mActivity=SplashScreen.this;
        cd = new ConnectionDetector(getApplicationContext());
        intent = getIntent();
        bundle = intent.getExtras();
        FirebaseApp.initializeApp(getApplicationContext());
        Uri uri=intent.getData();
        if(uri!=null){
            WebLink=uri.toString();
        }
        if(requestBean==null){
            requestBean = new RequestBean();

            requestBean.setActivity(this);
        }
        IsLoginOpen=false;

        AlertType=intent.getStringExtra("AlertType");
        FeedChannelID=intent.getStringExtra("FeedChannelID");
        ChannelFeedChannelID=intent.getStringExtra("ChannelFeedChannelID");
        UserName=intent.getStringExtra("UserName");
        ImageUrl=intent.getStringExtra("UserPic");
        FeedID=intent.getStringExtra("FeedID");
        NOTIFY_ID= intent.getIntExtra("NOTIFY_ID",0);

        if(AlertType!=null && AlertType.contains("Accepted")){
            appPreferences.setIsAccepted(true);
            if(RingtonePlay.isplayingAudio){
                RingtonePlay.stopAudio();
            }
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(NOTIFY_ID);
        }
        else if(AlertType!=null && (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS"))){
            //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Uri alarmSound =Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.raw.videocallring);
            //Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(),alarmSound);

            //ringtone.play();
            // Uri alert =  RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(getApplicationContext(), alarmSound);
            } catch (IOException e) {
                e.printStackTrace();
            }
            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                mMediaPlayer.setLooping(true);
                try {
                    mMediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMediaPlayer.start();
            }
        }
        else if(AlertType!=null && AlertType.equals("Message")){
            Message=intent.getStringExtra("message");
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(NOTIFY_ID);
        }
        appPreferences.setCurrentURL("");
        ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = cm.getActiveNetworkInfo();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);



        permissionsToRequest = permissionsToRequest(permissions);
        if (appPreferences.getStuID().equals("")) {
            appPreferences.setLocationPermissionDenied(false);

        }
        if(!appPreferences.getLocationPermissionDenied()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(
                            new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                }
            }
        }


        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API)

                .addConnectionCallbacks(this).
                        addOnConnectionFailedListener(this).build();

        dialog = new Dialog(SplashScreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.accept_reject_call);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(layoutParams);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
        TextView CallFrom=(TextView)dialog.findViewById(R.id.CallFrom);
        CircleImageView UserPic=(CircleImageView) dialog.findViewById(R.id.UserPic);
        CallFrom.setText(UserName);
        Picasso.with(this).load(ImageUrl).into(UserPic);

        mDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.setLooping(false);
                    mMediaPlayer.stop();
                    mMediaPlayer=null;
                }

                finishAffinity();
                // Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.setLooping(false);
                    mMediaPlayer.stop();
                    mMediaPlayer=null;
                }
                AlertType=AlertType+"-Accepted";
                handlerEvent();
                //Toast.makeText(getApplicationContext(),"Okay" ,Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        if(AlertType!=null && (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS")) && !AlertType.contains("Accepted"))
        {
            dialog.show();
        }
        else
        {
            handlerEvent();
        }

    }



    private void handlerEvent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConnectivityManager cm = (ConnectivityManager) getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isConnectedOrConnecting()) {

                    //Intent i = new Intent(SplashScreen.this,PreActivity.class);
                    //startActivity(i);
                    if (ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                       // return;
                    }
                    location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                    if (location != null) {
                        appPreferences.setLatitude(String.valueOf(location.getLatitude()));
                        appPreferences.setLongitude(String.valueOf(location.getLongitude()));
                        if (!appPreferences.getStuID().equals("")) {

                            if (appPreferences.getSessionCount().equals("LoginVerified")) {
                                Intent intent=new Intent(SplashScreen.this,HomeActivity.class);

                                intent.putExtra("AlertType",AlertType);
                                intent.putExtra("FeedChannelID",FeedChannelID);
                                intent.putExtra("ChannelFeedChannelID",ChannelFeedChannelID);
                                intent.putExtra("UserName",UserName);
                                intent.putExtra("ImageUrl",ImageUrl);
                                intent.putExtra("Message",Message);
                                intent.putExtra("FeedID",FeedID);
                                intent.putExtra("WebLink",WebLink);
                                if(AlertType!=null && AlertType.equals("Oxy_Homes")){
                                    String ResponseByFeedChannelID=bundle.getString("ResponseByFeedChannelID");
                                    String ResponseID= bundle.getString("ResponseID");
                                    String ListingID= bundle.getString("ListingID");
                                    String NotiType=bundle.getString("NotiType");
                                    intent.putExtra("ResponseByFeedChannelID",ResponseByFeedChannelID);
                                    intent.putExtra("ResponseID",ResponseID);
                                    intent.putExtra("ListingID",ListingID);
                                    intent.putExtra("NotiType",NotiType);

                                }

                                if(mMediaPlayer!=null){
                                    mMediaPlayer.setLooping(false);
                                    mMediaPlayer.stop();
                                    mMediaPlayer=null;
                                }
                                startActivity(intent);

                            } else {
                                appPreferences.setStuID("");
                                if(!IsLoginOpen){
                                    IsLoginOpen=true;
//                                    startActivity(new Intent(SplashScreen.this, SendOTPMSG91.class));
                                    startActivity(new Intent(SplashScreen.this, SocialAccountsActivity.class));
                                }

                            }
                        } else {
                            if(!IsLoginOpen){
                                IsLoginOpen=true;
                                //startActivity(new Intent(SplashScreen.this, SendOTPMSG91.class));
                                startActivity(new Intent(SplashScreen.this, SocialAccountsActivity.class));
                            }
                            //startActivity(new Intent(SplashScreen.this, PreActivity.class));
                        }

                        finish();
                    }
                    else{
                        GotoHomePage();
                    }
                }
                else {

                    //Intent i = new Intent(SplashScreen.this,noConnection.class);
                    //startActivity(i);
                }

                finish();
            }
        },SPLASH_TIMER);
    }
    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!checkPlayServices()) {
            Toast.makeText(getApplicationContext(), "You need to install Google Play Services to use the App properly", Toast.LENGTH_LONG).show();
            //locationTv.setText("You need to install Google Play Services to use the App properly");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // stop location updates
        if (googleApiClient != null  &&  googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (LocationListener) this);
            googleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }
    private final static int REQUEST_CHECK_SETTINGS_GPS=0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0x2;
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);



        if (location != null) {

            appPreferences.setLatitude(String.valueOf(location.getLatitude()));
            appPreferences.setLongitude(String.valueOf(location.getLongitude()));

//            startActivity(new Intent(SplashScreen.this,PreActivity.class));
            GotoHomePage();
//            if (!appPreferences.getStuID().equals("")) {
//
//                if (appPreferences.getSessionCount().equals("LoginVerified")) {
//                    Intent intent=new Intent(SplashScreen.this,HomeActivity.class);
//                    intent.putExtra("AlertType",AlertType);
//                    intent.putExtra("FeedChannelID",FeedChannelID);
//                    intent.putExtra("ChannelFeedChannelID",ChannelFeedChannelID);
//                    intent.putExtra("UserName",UserName);
//                    intent.putExtra("ImageUrl",ImageUrl);
//                    intent.putExtra("Message",Message);
//                    intent.putExtra("FeedID",FeedID);
////                    if(mMediaPlayer!=null){
////                        mMediaPlayer.setLooping(false);
////                        mMediaPlayer.stop();
////                        mMediaPlayer=null;
////                    }
//                    if(AlertType!=null && (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS")) && !AlertType.contains("Accepted"))
//                    {
//                        dialog.show();
//                    }
//                    else
//                    {
//                        startActivity(intent);
//                    }
//                    //startActivity(new Intent(SplashScreen.this, HomeActivity.class));
//
//                } else {
//                    appPreferences.setStuID("");
//                    startActivity(new Intent(SplashScreen.this, SendOTPMSG91.class));
//                }
//            } else {
//                startActivity(new Intent(SplashScreen.this, SendOTPMSG91.class));
//                //startActivity(new Intent(SplashScreen.this, PreActivity.class));
//            }

            //locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        }
//        else
//        {
//            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent1);
//
//        }

        startLocationUpdates();
    }

    @SuppressLint("RestrictedApi")
    private void startLocationUpdates() {

//        locationRequest.setInterval(UPDATE_INTERVAL);
//        locationRequest.setFastestInterval(FASTEST_INTERVAL);
//
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                &&  ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
//        }
//        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        if (location == null) {
        if(googleApiClient!=null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(SplashScreen.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    //LocationRequest locationRequest = new LocationRequest();
                    locationRequest = new LocationRequest();
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setInterval(UPDATE_INTERVAL);
                    locationRequest.setFastestInterval(FASTEST_INTERVAL);
                    locationRequest.setSmallestDisplacement(1000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    locationRequest.setMaxWaitTime(MAX_WAIT_TIME);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());

                    result.setResultCallback(new ResultCallback() {

                        @Override
                        public void onResult(@NonNull Result result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(SplashScreen.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        location = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(SplashScreen.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. However, we have no way to fix the
                                    // settings so we won't show the dialog.
                                    //finish();
                                    break;
                            }
                        }


                    });

                }
            }
            else
            {
                if(!IsLoginOpen){
                    IsLoginOpen=true;
                    //startActivity(new Intent(SplashScreen.this, SendOTPMSG91.class));
                    startActivity(new Intent(SplashScreen.this, SocialAccountsActivity.class));
                }
            }
        }
//            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent1);
        //}

//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        if (googleApiClient != null) {
                            googleApiClient.connect();
                        }
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
        String abc="";
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        String abc="";
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            //locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
//                    else
//                    {
//                        Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(intent1);
//
//                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        appPreferences.setLocationPermissionDenied(true);
                        GotoHomePage();
//                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//                            new AlertDialog.Builder(SplashScreen.this).
//                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
//                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions(permissionsRejected.
//                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
//                                            }
//                                        }
//                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    GotoHomePage();
//                                }
//                            }).create().show();
//
//                            return;
//                        }
//                        else{
//                            GotoHomePage();
//                        }
                    }
                } else {
                    if (googleApiClient != null) {
                        //googleApiClient.connect();
                        appPreferences.setLocationPermissionDenied(false);
                        startLocationUpdates();
                    }
                }

//                if (permissionsRejected.size() > 0) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//                            new AlertDialog.Builder(SplashScreen.this).
//                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
//                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions(permissionsRejected.
//                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
//                                            }
//                                        }
//                                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            }).create().show();
//
//                            return;
//                        }
//                    }
//                } else {
//                    if (googleApiClient != null) {
//                        //googleApiClient.connect();
//                        startLocationUpdates();
//                    }
//                }

                break;
        }
    }

public void GotoHomePage(){
    if (!appPreferences.getStuID().equals("") && !HomePageOpened) {

        if (appPreferences.getSessionCount().equals("LoginVerified")) {
            HomePageOpened=true;
            Intent intent = new Intent(SplashScreen.this, HomeActivity.class);

            intent.putExtra("AlertType", AlertType);
            intent.putExtra("FeedChannelID", FeedChannelID);
            intent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
            intent.putExtra("UserName", UserName);
            intent.putExtra("ImageUrl", ImageUrl);
            intent.putExtra("Message", Message);
            intent.putExtra("FeedID", FeedID);
            intent.putExtra("WebLink",WebLink);
            if(AlertType!=null && AlertType.equals("Oxy_Homes")){
                String ResponseByFeedChannelID=bundle.getString("ResponseByFeedChannelID");
                String ResponseID= bundle.getString("ResponseID");
                String ListingID= bundle.getString("ListingID");
                String NotiType=bundle.getString("NotiType");
                intent.putExtra("ResponseByFeedChannelID",ResponseByFeedChannelID);
                intent.putExtra("ResponseID",ResponseID);
                intent.putExtra("ListingID",ListingID);
                intent.putExtra("NotiType",NotiType);

            }
            if (mMediaPlayer != null) {
                mMediaPlayer.setLooping(false);
                mMediaPlayer.stop();
                mMediaPlayer = null;
            }
            if(AlertType!=null && (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS")) && !AlertType.contains("Accepted"))
            {
                dialog.show();
            }
            else
            {
                startActivity(intent);
            }

        } else {
            appPreferences.setStuID("");
            if(!IsLoginOpen){
                IsLoginOpen=true;
//                startActivity(new Intent(SplashScreen.this, SendOTPMSG91.class));
                startActivity(new Intent(SplashScreen.this, SocialAccountsActivity.class));

            }
        }
    }
    else if(!HomePageOpened){

        if(!IsLoginOpen){
            IsLoginOpen=true;
//            startActivity(new Intent(SplashScreen.this, SendOTPMSG91.class));
            startActivity(new Intent(SplashScreen.this, SocialAccountsActivity.class));
        }
        //startActivity(new Intent(SplashScreen.this, PreActivity.class));
    }
}

    public void onBackPressed()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you  want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        moveTaskToBack(true);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.show();

    }
}
