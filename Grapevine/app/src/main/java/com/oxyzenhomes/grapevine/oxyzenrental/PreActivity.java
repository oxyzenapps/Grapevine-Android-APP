package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.appprefrence.VersionChecker;
//import com.facebook.CallbackManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;

public class PreActivity extends RuntimePermissionsActivity {
    private static final int REQUEST_PERMISSIONS = 20;
    ConnectionDetector cd;

    private Button btn;
    private AppPreferences appPreferences;
    private GoogleApiClient mGoogleApiClient;
    private String SourceChannel="";
    private String UserID="";
    private  String Name="";
    private String ChannelId="";
    EditText editName;
    Button buttonSubmit;
    //private CallbackManager callbackManager;

    private static final String EMAIL = "email";
    private TextView gotosignup;
    private TextView skiptoindex;
    private String Latitude="";
    private String Longitude="";

    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest;
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private Location location;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);
        appPreferences = new AppPreferences(this);
        new VersionChecker(PreActivity.this).execute();
        editName  = (EditText) findViewById(R.id.username);
        buttonSubmit = (Button) findViewById(R.id.login);
        getSupportActionBar().hide();

      Intent  intent = getIntent();
        Bundle bundle = intent.getExtras();
        skiptoindex=(TextView) findViewById(R.id.skiptoindex);

        skiptoindex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserID="dummyy";
                Name="dummyy";
                ChannelId="dummyy";
                SourceChannel="skipbtn";
                appPreferences.setschoolname(SourceChannel);
                appPreferences.setStuID(UserID);
                appPreferences.setRegNo(ChannelId);
                appPreferences.setimagePath(Name);
                if (!UserID.equals("")) {
                    startActivity(new Intent(PreActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });
        PreActivity.super.requestAppPermissions(new
                        String[]{ Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.RECORD_AUDIO}
                ,R.string.runtime_permissions_txt
                , REQUEST_PERMISSIONS);

      /* GPStracker g=new GPStracker(getApplicationContext());
       if(g!=null){
            double Lat=g.getLocation().getLatitude();

           Latitude=String.valueOf(Lat);
            //txtLatitude.setText(Latitude);
           appPreferences.setSchoolID(Latitude);
           double Long=g.getLocation().getLongitude();
           Longitude=String.valueOf(Long);
           appPreferences.setStatffID(Longitude);
             //txtLongitude.setText(Longitude);
           Toast.makeText(getApplicationContext(),"Lat:"+Lat+"\n Long:"+Long,Toast.LENGTH_LONG).show();
        }*/
//        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        permissionsToRequest = permissionsToRequest(permissions);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (permissionsToRequest.size() > 0) {
//                requestPermissions(permissionsToRequest.toArray(
//                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//            }
//        }
//
//        // we build google api client
//        googleApiClient = new GoogleApiClient.Builder(this).
//                addApi(LocationServices.API).
//                addConnectionCallbacks(this).
//                addOnConnectionFailedListener(this).build();
        cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet()) {

            // Attaching OnClick listener to the submit button
            buttonSubmit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    UserID=editName.getText().toString();
                    Name="dummyy";
                    ChannelId="dummyy";
                    SourceChannel="signinbtn";
                    appPreferences.setschoolname(SourceChannel);
                    appPreferences.setStuID("+91"+UserID);
                    appPreferences.setRegNo(ChannelId);
                    appPreferences.setimagePath(Name);
                    //UpdateToken(UserID);
                    //Intent intent = new Intent(PreActivity.this,VerifyotpActivity.class);
                    //startActivity(intent);
//                    appPreferences.setSessionCount("SendOtp");
//                    if (!UserID.equals("")) {
//                        startActivity(new Intent(PreActivity.this, HomeActivity.class));
//                        finish();
//                    }
//                    else
//                    {
//                        Toast.makeText(PreActivity.this, "Please enter your mobile ", Toast.LENGTH_SHORT).show();
//                    }
                }
            });

//            else
//            {
                if (appPreferences.getSessionCount().equals("LoginVerified")) {
                    startActivity(new Intent(PreActivity.this, HomeActivity.class));
                    finish();
                }
                else
                {
                    //GooogleLogin();
                }
            //}


        }
        else{
            Intent i = new Intent(PreActivity.this,noConnection.class);
            startActivity(i);
        }



    }


//
//
//
//    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
//        ArrayList<String> result = new ArrayList<>();
//
//        for (String perm : wantedPermissions) {
//            if (!hasPermission(perm)) {
//                result.add(perm);
//            }
//        }
//
//        return result;
//    }
//
//    private boolean hasPermission(String permission) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
//        }
//
//        return true;
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//try{
//    if (googleApiClient != null) {
//        googleApiClient.connect();
//    }
//}
//catch(Exception ex){
//
//}
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (!checkPlayServices()) {
//            Toast.makeText(getApplicationContext(), "You need to install Google Play Services to use the App properly", Toast.LENGTH_LONG).show();
//            //locationTv.setText("You need to install Google Play Services to use the App properly");
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//try{
//    if (googleApiClient != null  &&  googleApiClient.isConnected()) {
//        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (LocationListener) this);
//        googleApiClient.disconnect();
//    }
//}
//catch(Exception ex){
//
//}
//        // stop location updates
//
//    }
//
//    private boolean checkPlayServices() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
//            } else {
//                finish();
//            }
//
//            return false;
//        }
//
//        return true;
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                &&  ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
//
//        try{
//            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//
//            if (location != null) {
//
//                appPreferences.setLatitude(String.valueOf(location.getLatitude()));
//                appPreferences.setLongitude(String.valueOf(location.getLongitude()));
//
//                //locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
//            }
//        }
//        catch (Exception ex){
//
//        }
//        // Permissions ok, we get last location
//
//
//        startLocationUpdates();
//    }
//
//    @SuppressLint("RestrictedApi")
//    private void startLocationUpdates() {
//        try{
//            locationRequest = new LocationRequest();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(UPDATE_INTERVAL);
//            locationRequest.setFastestInterval(FASTEST_INTERVAL);
//
//            if (ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    &&  ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
//            }
//
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//
//        }
//        catch(Exception ex){
//
//        }
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        if (location != null) {
//            //locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch(requestCode) {
//            case ALL_PERMISSIONS_RESULT:
//                for (String perm : permissionsToRequest) {
//                    if (!hasPermission(perm)) {
//                        permissionsRejected.add(perm);
//                    }
//                }
//
//                if (permissionsRejected.size() > 0) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//                            new AlertDialog.Builder(PreActivity.this).
//                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
//                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions(permissionsRejected.
//                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
//                                            }
//                                        }
//                                    }).setNegativeButton("Cancel", null).create().show();
//
//                            return;
//                        }
//                    }
//                } else {
//                    try{
//                        if (googleApiClient != null) {
//                            googleApiClient.connect();
//                            //startLocationUpdates();
//                        }
//                    }
//                    catch(Exception ex){
//
//                    }
//
//                }
//
//                break;
//        }
//    }
    @Override
    public void onPermissionsGranted(final int requestCode) {
        //Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(PreActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(PreActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(PreActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(PreActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            //Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED){
            switch (requestCode) {


                //Write external Storage
                case 3:
                    String abs="";
                    break;
                //Read External Storage
                case 4:
                    String ab1s="";
//                    Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(imageIntent, 11);
                    break;
                //Camera
                case 5:
                    String ab12s="";
//                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(takePictureIntent, 12);
//                    }
                    break;
                case 20:
                                GPStracker g=new GPStracker(getApplicationContext());
            Location l=g.getLocation();
            if(l==null){
                Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent1);
            }
            break;

            }

            //Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }else{
            //Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if(requestCode==3600){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try{
//                GoogleSignInAccount account=task.getResult(ApiException.class);
//                printToken(account);
//            }
//            catch(ApiException e){
//                printToken(null);
//            }
//        }
//        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,resultCode,data);
    }

//    private void printToken(GoogleSignInAccount account){
//        if(account==null){
//            Toast.makeText(this, "You Are Not Authenticated", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            UserID=account.getEmail();
//            Name=account.getDisplayName();
//            ChannelId=account.getId();
//            SourceChannel="Google";
//            appPreferences.setschoolname(SourceChannel);
//            appPreferences.setStuID(UserID);
//            appPreferences.setRegNo(ChannelId);
//            appPreferences.setimagePath(Name);
//            if (!UserID.equals("")) {
//                startActivity(new Intent(PreActivity.this, HomeActivity.class));
//                finish();
//            }
//            //Toast.makeText(this, "email:"+account.getEmail()+"token:"+account.getIdToken(), Toast.LENGTH_SHORT).show();
//        }
//    }
//public void UpdateToken(String Mobile) {
//
//    RequestQueue queue = Volley.newRequestQueue(this);
//
//    String url = "https://m.oxyzenhomes.com/web/Sendotpactions?mob="+Mobile;
//
//    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String _response) {
//
//                    String result=String.valueOf(_response.substring(1));
//                    result=result.substring(0, result.length() - 1);
//                    appPreferences.setUserOTP(result);
//                    GotoVerification();
//                    // Log.d("logr=",_response);
//
//                }
//            }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//
//            // Error handling
//            String result=String.valueOf(error);
////                Log.d("log2=", error.toString());
////                Log.e("log3=", error.toString());
//        }
//    });
////excecute your request
//    queue.add(stringRequest);
//
//
//}
    private void GotoVerification(){
        String result=appPreferences.getUserOTP();
        UserID=editName.getText().toString();
        if(result!=""){
            if (!UserID.equals("")) {
                startActivity(new Intent(PreActivity.this, VerifyotpActivity.class));
                finish();
            }
        }
        else
        {
            Toast.makeText(PreActivity.this, "Invalid mobile no.", Toast.LENGTH_SHORT).show();
        }
    }

}
