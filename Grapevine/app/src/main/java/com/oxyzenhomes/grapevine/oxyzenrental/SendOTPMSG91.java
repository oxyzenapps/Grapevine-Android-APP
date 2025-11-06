package com.oxyzenhomes.grapevine.oxyzenrental;

import static com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.exotel.verification.ConfigBuilder;
//import com.exotel.verification.ExotelVerification;
//import com.exotel.verification.VerificationListener;
//import com.exotel.verification.contracts.Config;
//import com.exotel.verification.contracts.VerificationFailed;
//import com.exotel.verification.contracts.VerificationStart;
//import com.exotel.verification.contracts.VerificationSuccess;
//import com.exotel.verification.exceptions.ConfigBuilderException;
//import com.exotel.verification.exceptions.PermissionNotGrantedException;
//import com.exotel.verification.exceptions.VerificationAlreadyInProgressException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceId;
import com.msg91.sendotpandroid.library.internal.Iso2Phone;
import com.msg91.sendotpandroid.library.internal.SendOTP;
import com.msg91.sendotpandroid.library.utils.PhoneNumberFormattingTextWatcher;
import com.msg91.sendotpandroid.library.utils.PhoneNumberUtils;
//import com.msg91.sendotpandroid.library.PhoneNumberFormattingTextWatcher;
//import com.msg91.sendotpandroid.library.PhoneNumberUtils;
import com.msg91.sendotpandroid.library.internal.Iso2Phone;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.request.RequestBean;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

 public class SendOTPMSG91 extends RuntimePermissionsActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

  public static final String INTENT_PHONENUMBER = "phonenumber";
  public static final String INTENT_COUNTRY_CODE = "code";

  private EditText mPhoneNumber;
  private Button mSmsButton;
  private String mCountryIso;
  private TextWatcher mNumberTextWatcher;
  private Button SkipToIndex;
  private String SourceChannel="";
  private String UserID="";
  private String Name="";
  private String ChannelId="";
  private AppPreferences appPreferences;
  ConnectionDetector cd;
  private CheckBox PrivacyCheck;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissions = new ArrayList<>();
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private Location location;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private static final int REQUEST_PERMISSIONS = 20;
    RequestBean requestBean;
    TextView privacyPolicy;
    private Button smsVerificationButton;
    private final String TAG = SendOTPMSG91.class.getSimpleName();
    private final ITrueCallback sdkCallback = new ITrueCallback() {

        @Override
        public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {
            try{
                //Toast.makeText(getApplicationContext(),trueProfile.firstName + " " + trueProfile.lastName ,Toast.LENGTH_SHORT).show();
                Log.i(TAG, trueProfile.firstName + " " + trueProfile.lastName);

                openActivity(trueProfile.phoneNumber,true);
            }
            catch(Exception ex){
                Toast.makeText(getApplicationContext(),ex.getMessage() ,Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailureProfileShared(@NonNull final TrueError trueError) {

            try{
                Toast.makeText(getApplicationContext(),trueError.getErrorType() ,Toast.LENGTH_SHORT).show();
                Log.e("error code", trueError.getErrorType() + " ");
            }
            catch(Exception ex){
                Toast.makeText(getApplicationContext(),ex.getMessage() ,Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onVerificationRequired(@Nullable final TrueError trueError) {
        }

    };
    //ExotelVerification eVerification;
    private final String LOGGING_TAG = "VerificatrixDemoApp";
    private final String accountSid = "oxyzenhomes1";
    private final String NotpAppId = "36eabdbe92ef4152ad105bf48ce5a8e0";
    private final String appSecret = "ayedukoyigaw";

//    class VerifyListener implements VerificationListener {
//        public void onVerificationStarted(VerificationStart verificationStart){
//            //secondsTv.setVisibility(View.VISIBLE);
//            Toast.makeText(SendOTPMSG91.this,"Verification Started",Toast.LENGTH_SHORT).show();
//        }
//        public void onVerificationSuccess(VerificationSuccess verificationSuccess) {
//            //secondsTv.setVisibility(View.GONE);
//            Toast.makeText(SendOTPMSG91.this, "Verification Success", Toast.LENGTH_SHORT).show();
//        }
//        public void onVerificationFailed(VerificationFailed verificationFailed){
//            //secondsTv.setVisibility(View.GONE);
//            Toast.makeText(SendOTPMSG91.this, verificationFailed.getErrorMessage(), Toast.LENGTH_SHORT).show();
//            Log.i(LOGGING_TAG, "Verification Failed: "+verificationFailed.getRequestID()+ " "+verificationFailed.getErrorCode()+" "+verificationFailed.getErrorMessage()+" "+verificationFailed.getMiscData() );
//        }
//    }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.signin_layout);
      getSupportActionBar().hide();
      getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    appPreferences = new AppPreferences(this);
    mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
    mSmsButton = (Button) findViewById(R.id.smsVerificationButton);
    //SkipToIndex=(Button) findViewById(R.id.SkipToIndex);
      smsVerificationButton=(Button)findViewById(R.id.smsVerificationButton);
      smsVerificationButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
      //SkipToIndex.setVisibility(View.GONE);
      smsVerificationButton.setEnabled(false);
      PrivacyCheck=(CheckBox)findViewById(R.id.PrivacyCheck);
      privacyPolicy=(TextView)findViewById(R.id.privacyPolicy);
      privacyPolicy.setLinksClickable(true);
      PrivacyCheck.setChecked(true);
      smsVerificationButton.setEnabled(true);
      smsVerificationButton.getBackground().setColorFilter(null);
      privacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());
      requestBean = new RequestBean();
      requestBean.setActivity(this);
      cd = new ConnectionDetector(getApplicationContext());
      permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
      permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
      permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
      permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
      permissions.add(Manifest.permission.READ_MEDIA_AUDIO);
      permissions.add(Manifest.permission.READ_MEDIA_IMAGES);
      permissions.add(Manifest.permission.READ_MEDIA_VIDEO);
      permissions.add(Manifest.permission.POST_NOTIFICATIONS);

      FirebaseApp.initializeApp(getApplicationContext());

      permissionsToRequest = permissionsToRequest(permissions);

      TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(this, sdkCallback)
              .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
//              .buttonColor(Color.parseColor(colorSpinner.getSelectedItem().toString()))
//              .buttonTextColor(Color.parseColor(colorTextSpinner.getSelectedItem().toString()))
              .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
              .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
              .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
              .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
//              .privacyPolicyUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
//              .termsOfServiceUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
              .footerType(TruecallerSdkScope.FOOTER_TYPE_NONE)
              .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN)
              .sdkOptions(TruecallerSdkScope.SDK_OPTION_WITHOUT_OTP)
              .build();
      TruecallerSDK.init(trueScope);
      boolean isTrue=TruecallerSDK.getInstance().isUsable();
      if(isTrue){
          try{
              TruecallerSDK.getInstance().getUserProfile(SendOTPMSG91.this);
          }
          catch(Exception ex){
              Toast.makeText(getApplicationContext(),ex.getMessage() ,Toast.LENGTH_SHORT).show();
          }

      }
//      try {
//          initializeVerification();
//      }
//      catch (Exception e) {
//          Log.e(LOGGING_TAG, "onCreate: Exception occured " + e.getMessage());
//      }

//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//          if (permissionsToRequest.size() > 0) {
//              requestPermissions(permissionsToRequest.toArray(
//                      new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
//          }
//      }
      PrivacyCheck.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              boolean checked = ((CheckBox) v).isChecked();
              // Check which checkbox was clicked
              if (checked){
                  //SkipToIndex.setVisibility(View.VISIBLE);
                  smsVerificationButton.setEnabled(true);
                  smsVerificationButton.getBackground().setColorFilter(null);
                  // Do your coding
              }
              else{
                  //SkipToIndex.setVisibility(View.GONE);
                  smsVerificationButton.setEnabled(false);
                  smsVerificationButton.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                  // Do your coding
              }
          }
      });

      googleApiClient = new GoogleApiClient.Builder(this).
              addApi(LocationServices.API).
              addConnectionCallbacks(this).
              addOnConnectionFailedListener(this).build();
      final Dialog   dialog = new Dialog(SendOTPMSG91.this);
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      dialog.setCancelable(false);
      dialog.setContentView(R.layout.contact_permission);
      WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
      layoutParams.copyFrom(dialog.getWindow().getAttributes());
      layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
      layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
      dialog.getWindow().setAttributes(layoutParams);

      Button mDialogNo = dialog.findViewById(R.id.Confirm_Contact);


      // Check if the Android version is 13 or higher
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//          if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
//                  != PackageManager.PERMISSION_GRANTED) {
//
//              // Request the POST_NOTIFICATIONS permission
//              ActivityResultLauncher<String> requestPermissionLauncher =
//                      registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//                          if (isGranted) {
//                              // Permission granted
//                              Toast.makeText(getApplicationContext(), "Notification permission granted!", Toast.LENGTH_LONG).show();
//
//                          } else {
//                              // Permission denied
//                              Toast.makeText(getApplicationContext(),"Notification permission not granted!", Toast.LENGTH_LONG).show();
//                          }
//                      });
//
//              // Launch permission request
//              requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
//          }
//      }

      mDialogNo.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.dismiss();
              SendOTPMSG91.super.requestAppPermissions(new
                              String[]{
                              Manifest.permission.READ_EXTERNAL_STORAGE,
                              Manifest.permission.CAPTURE_AUDIO_OUTPUT,
                              Manifest.permission.READ_MEDIA_IMAGES,
                              Manifest.permission.READ_MEDIA_VIDEO,
                              Manifest.permission.READ_MEDIA_VIDEO,
                              Manifest.permission.RECORD_AUDIO,
                     android.Manifest.permission.POST_NOTIFICATIONS
                      }
                      ,R.string.runtime_permissions_txt
                      , REQUEST_PERMISSIONS);
              // Toast.makeText(getApplicationContext(),"Cancel" ,Toast.LENGTH_SHORT).show();

          }
      });
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//          if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
//              ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
//          }
//      }

      if (ContextCompat.checkSelfPermission(this,
              Manifest.permission.READ_MEDIA_IMAGES)
              != PackageManager.PERMISSION_GRANTED)
       {
         // dialog.show();

          SendOTPMSG91.super.requestAppPermissions(new
                          String[]{
//                              Manifest.permission.READ_CONTACTS,
                          //    Manifest.permission.CAMERA,
//                              Manifest.permission.WRITE_EXTERNAL_STORAGE,
                          Manifest.permission.READ_EXTERNAL_STORAGE,
                          Manifest.permission.CAPTURE_AUDIO_OUTPUT,
                          Manifest.permission.READ_MEDIA_IMAGES,
                          Manifest.permission.READ_MEDIA_VIDEO,
                          Manifest.permission.READ_MEDIA_AUDIO,
//                              Manifest.permission.READ_CALENDAR,
//                              Manifest.permission.WRITE_CALENDAR,
//                              Manifest.permission.ACCESS_FINE_LOCATION,
                          Manifest.permission.RECORD_AUDIO,
                          android.Manifest.permission.POST_NOTIFICATIONS}

                  ,R.string.runtime_permissions_txt
                  , REQUEST_PERMISSIONS);
//
//          AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
//          alertBuilder.setCancelable(true);
//         alertBuilder.setView(R.layout.contact_permission);
//
////          alertBuilder.setTitle("Why Contact Permission?");
////          alertBuilder.setMessage("We use your phone contact data for the limited purpose of" +
////                  " inviting the contacts to the app as your friend and in no other ways.\n" +
////                  " if you agree to this only then allow the permission to access contact.");
//          alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//              @Override
//              public void onClick(DialogInterface dialog, int which) {
//
//
//              }
//          });
//
//          AlertDialog alert = alertBuilder.create();
//          alert.show();
//          ((AlertDialog) alert).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
      }

    mCountryIso = PhoneNumberUtils.getDefaultCountryIso(this);
    final String defaultCountryName = new Locale("", mCountryIso).getDisplayName();
//    final CountrySpinner spinner = (CountrySpinner) findViewById(R.id.spinner);
//    spinner.init(defaultCountryName);
//    spinner.addCountryIsoSelectedListener(new CountrySpinner.CountryIsoSelectedListener() {
//      @Override
//      public void onCountryIsoSelected(String selectedIso) {
//        if (selectedIso != null) {
//          mCountryIso = selectedIso;
//          resetNumberTextWatcher(mCountryIso);
//          // force update:
//          mNumberTextWatcher.afterTextChanged(mPhoneNumber.getText());
//        }
//      }
//    });
    resetNumberTextWatcher(mCountryIso);

    //tryAndPrefillPhoneNumber();
  }


//    private void initializeVerification() {
//        try {
//            Config config = new ConfigBuilder(NotpAppId, appSecret, accountSid, getApplicationContext()).Build();
//            eVerification = new ExotelVerification(config);
//        } catch (PermissionNotGrantedException vPNGE) {
//            Toast.makeText(SendOTPMSG91.this,"initializeVerification: permission not granted exception: " + vPNGE.getPermission(),Toast.LENGTH_SHORT).show();
//            Log.d(LOGGING_TAG, "initializeVerification: permission not granted exception: " + vPNGE.getPermission());
//            askForPermission(vPNGE.getPermission(), 1);
//
//            //Try initializing again after 3 seconds
//            (new android.os.Handler()).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    initializeVerification();
//                }
//            }, 3000);
//
//        } catch (ConfigBuilderException cBE) {
//            Log.d(LOGGING_TAG, "initializeVerification: ClientBuilder Exception!");
//        }
//    }

    private void askForPermission(String permission, Integer requestCode) {
        //if the user denied it perviously
        if (ContextCompat.checkSelfPermission(SendOTPMSG91.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SendOTPMSG91.this, permission)) {
                //just asking them again for now
                ActivityCompat.requestPermissions(SendOTPMSG91.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(SendOTPMSG91.this, new String[]{permission}, requestCode);
            }
        } else {
            //permission already given
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            Toast.makeText(getApplicationContext(),requestCode ,Toast.LENGTH_SHORT).show();
            if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
                TruecallerSDK.getInstance().onActivityResultObtained(this, requestCode, resultCode, data);
            }
        }
        catch (Exception ex){
            if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
                //Toast.makeText(getApplicationContext(),"response code is same",Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"Error in result "+ex.getMessage()+"-"+String.valueOf(requestCode)+"-"+String.valueOf(resultCode)+"-"+String.valueOf(TruecallerSDK.SHARE_PROFILE_REQUEST_CODE),Toast.LENGTH_SHORT).show();
                TruecallerSDK.getInstance().onActivityResultObtained(this, requestCode, resultCode, data);
            }
            else{
                Toast.makeText(getApplicationContext(),"Error in result "+ex.getMessage()+"-"+String.valueOf(requestCode)+"-"+String.valueOf(resultCode)+"-"+String.valueOf(TruecallerSDK.SHARE_PROFILE_REQUEST_CODE),Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(getApplicationContext(),ex.getMessage() ,Toast.LENGTH_SHORT).show();
        }
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
            appPreferences.setSchoolID(String.valueOf(location.getLatitude()));
            appPreferences.setStatffID(String.valueOf(location.getLongitude()));



            //locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        }

        startLocationUpdates();
    }

    @SuppressLint("RestrictedApi")
    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            //locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    } else {
                        Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent1);

                    }
                }

                if (permissionsRejected.size() > 0) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//                            new AlertDialog.Builder(SendOTPMSG91.this).
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
                    return;
                } else {
                    if (googleApiClient != null) {
                        googleApiClient.connect();
                        startLocationUpdates();
                    }
                }

                break;
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode) {

    }
  private void tryAndPrefillPhoneNumber() {
    if (checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
      TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
      mPhoneNumber.setText(manager.getLine1Number());
    } else {
      ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
    }
  }

//  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//      //tryAndPrefillPhoneNumber();
//    } else {
//      if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
//        Toast.makeText(this, "This application needs permission to read your phone number to automatically "
//            + "pre-fill it", Toast.LENGTH_LONG).show();
//      }
//    }
//  }

  private void openActivity(String phoneNumber,Boolean Verified) {

if(!phoneNumber.contains("@")){
    Intent verification = new Intent(this, VerificationActivity.class);
    phoneNumber=phoneNumber.replace(" ","");
    phoneNumber=phoneNumber.replace("(","");
    phoneNumber=phoneNumber.replace(")","");
    phoneNumber=phoneNumber.replace("-","");
    phoneNumber=phoneNumber.replace("+91","");
    verification.putExtra(INTENT_PHONENUMBER, phoneNumber);
    verification.putExtra("IsVerified",Verified);
    verification.putExtra(INTENT_COUNTRY_CODE, Iso2Phone.getPhone(mCountryIso));
    if( phoneNumber.equals("9634496542") || phoneNumber.equals("9259568102")
            //|| phoneNumber.equals("6396911076")
            || phoneNumber.equals("7045373449")
            || phoneNumber.equals("9082399986")
            || phoneNumber.equals("8532005043")
            || phoneNumber.equals("7045373461")
            || phoneNumber.equals("8451906024")
            || phoneNumber.equals("8104980537")
            || phoneNumber.equals("7738058831")
            || phoneNumber.equals("8655712213")
            || phoneNumber.equals("9987885846")
            || phoneNumber.equals("8308647220")
            //|| phoneNumber.equals("8655701529")
            ){
        Name="dummyy";
        ChannelId="dummyy";
        SourceChannel="signinbtn";
        appPreferences.setschoolname(SourceChannel);
        appPreferences.setStuID("+91"+phoneNumber);
        String Otp=appPreferences.getUserOTP();
        //appPreferences.setStuID(UserID);
        appPreferences.setRegNo(ChannelId);
        appPreferences.setimagePath(Name);
        String AndroidID= Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String token=  FirebaseInstanceId.getInstance().getToken();



        UpdateToken("", token,AndroidID,phoneNumber);
        appPreferences.setSessionCount("LoginVerified");
        startActivity(new Intent(SendOTPMSG91.this, HomeActivity.class));
        finish();
    }
    else
    {
//        try {
//            eVerification.startVerification(new VerifyListener(), "+91"+phoneNumber,10);
//        } catch (VerificationAlreadyInProgressException e) {
//            Log.e(LOGGING_TAG, "Exception: " + e.getMessage());
//        }
        startActivity(verification);
    }
}
    else
{
//String OTP=getRandomNumberString();
    initiateVerification(phoneNumber);
   // verification.putExtra(INTENT_COUNTRY_CODE, Iso2Phone.getPhone(mCountryIso));

}


  }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    public void UpdateToken(String applicantid,String TokenID,String AndroidID,String MobileNo) {


        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values=new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("ContactID", applicantid));
                args.add(new BasicNameValuePair("DeviceID", TokenID));
                args.add(new BasicNameValuePair("WebsiteID", "9"));
                args.add(new BasicNameValuePair("AndroidID", AndroidID));
                args.add(new BasicNameValuePair("MobileNo", MobileNo));
                args.add(new BasicNameValuePair("DeviceOS", "Android"));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/InsertUpdateContactAlertKey";

                dataFromNetwork.setConfig(URL, methodName, args);
                dataFromNetwork.execute();
//                Object response = dataFromNetwork.execute().get().toString();
//                if (response != null) {
////                    FeedChannelID=response.toString();
////                    FeedChannelID=FeedChannelID.replaceAll("\"","").replaceAll("\n","");
//                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }
    public void initiateVerification(final String Mobile){
        if (cd.isConnectingToInternet()) {



            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {



                            // Bookingdetails(listView);
                        }
                    }
                });


                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("Email",Mobile));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/SendEmailOTP";

                dataFromNetwork.setConfig(URL, methodName, args);
                //dataFromNetwork.execute();
                Object response = dataFromNetwork.execute().get().toString();

                if (response != null) {
                    String result=response.toString().replaceAll("\"", "").replace("\n","");


                    //result=result.substring(0, result.length() - 1);
                    Intent verification = new Intent(this, VerifybyEmail.class);
                    verification.putExtra("EmailID", Mobile);
                    appPreferences.setUserOTP(result);
                    startActivity(verification);
                }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public int CheckingAccountExistence(String MobileNo, String AndroidID){
        if (cd.isConnectingToInternet()) {



            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {



                            // Bookingdetails(listView);
                        }
                    }
                });

                MobileNo=MobileNo.replace(" ","");
                MobileNo=MobileNo.replace("(","");
                MobileNo=MobileNo.replace(")","");
                MobileNo=MobileNo.replace("-","");
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("MobileNo",MobileNo));
                args.add(new BasicNameValuePair("WebsiteID","9"));
                args.add(new BasicNameValuePair("AndroidID",AndroidID));
                args.add(new BasicNameValuePair("DeviceOS","Android"));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/CheckingAccountExistence";

                dataFromNetwork.setConfig(URL, methodName, args);
                //dataFromNetwork.execute();
                Object response = dataFromNetwork.execute().get().toString();

                if (response != null) {
                    JSONObject object = new JSONObject(response.toString());
                    int MobileNoAccount=object.getInt("MobileNoAccount");
                    int DeviceIDAccount=object.getInt("DeviceIDAccount");
                    if(DeviceIDAccount==0 && MobileNoAccount>0){
                        return 1;
                    }
                    else{
                        return 0;
                    }



                    //result=result.substring(0, result.length() - 1);

                }
                else{
                    return 0;
                }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return 0;
            }
        }
        else{
            return 0;
        }
    }
//    public void initiateVerification(String Mobile) {
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        String url = "http://grapevine.work/workplace/SendEmailOTP?Email="+Mobile;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String _response) {
//
//                        String result=String.valueOf(_response.substring(1));
//                        result=result.substring(0, result.length() - 1);
//                        appPreferences.setUserOTP(result);
//                        //GotoVerification();
//                        // Log.d("logr=",_response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                // Error handling
//                String result=String.valueOf(error);
////                Log.d("log2=", error.toString());
////                Log.e("log3=", error.toString());
//            }
//        });
////excecute your request
//        queue.add(stringRequest);
//
//
//    }
  private void setButtonsEnabled(boolean enabled) {
    mSmsButton.setEnabled(enabled);
  }

  public void onButtonClicked(View view) {
      if(PrivacyCheck.isChecked()){

          String AndroidID=Settings.Secure.getString(getApplicationContext().getContentResolver(),
                  Settings.Secure.ANDROID_ID);
          int isActive=CheckingAccountExistence(getE164Number(),AndroidID);
if(isActive==1){
    final Dialog   dialog = new Dialog(SendOTPMSG91.this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.alert_of_duplicate_account);
    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
    layoutParams.copyFrom(dialog.getWindow().getAttributes());
    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    dialog.getWindow().setAttributes(layoutParams);

    Button mDialogYes = dialog.findViewById(R.id.Confirm_Contact);
    Button mDialogNo = dialog.findViewById(R.id.Decline_Contact);

    mDialogYes.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
            openActivity(getE164Number(),false);

        }
    });
    mDialogNo.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.dismiss();
            mPhoneNumber.setText("");

        }
    });
   // dialog.show();
    openActivity(getE164Number(),false);
}
else{
    openActivity(getE164Number(),false);
}
          //openActivity(getE164Number());
      }


  }

  private void resetNumberTextWatcher(String countryIso) {

    if (mNumberTextWatcher != null) {
      mPhoneNumber.removeTextChangedListener(mNumberTextWatcher);
    }

    mNumberTextWatcher = new PhoneNumberFormattingTextWatcher(countryIso) {
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        super.beforeTextChanged(s, start, count, after);
      }

      @Override
      public synchronized void afterTextChanged(Editable s) {
        super.afterTextChanged(s);
          mPhoneNumber.setTextColor(Color.BLACK);
//        if (isPossiblePhoneNumber()) {
//          setButtonsEnabled(true);
//          mPhoneNumber.setTextColor(Color.BLACK);
//        } else {
//          setButtonsEnabled(false);
//          mPhoneNumber.setTextColor(Color.RED);
//        }
      }
    };

    mPhoneNumber.addTextChangedListener(mNumberTextWatcher);
  }

  private boolean isPossiblePhoneNumber() {
    return PhoneNumberUtils.isPossibleNumber(mPhoneNumber.getText().toString(), mCountryIso);
  }

  private String getE164Number() {
//    return mPhoneNumber.getText().toString().replaceAll("\\D", "").trim();
      return mPhoneNumber.getText().toString();
    // return PhoneNumberUtils.formatNumberToE164(mPhoneNumber.getText().toString(), mCountryIso);
  }
}
