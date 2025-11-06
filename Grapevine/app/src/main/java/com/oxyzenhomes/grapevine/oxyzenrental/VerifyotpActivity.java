package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;

import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.appprefrence.VersionChecker;

import java.util.concurrent.TimeUnit;

public class VerifyotpActivity extends RuntimePermissionsActivity {
    private static final int REQUEST_PERMISSIONS = 20;
    ConnectionDetector cd;
    private GoogleSignInClient googleSignInClient;
    private Button btn;
    private AppPreferences appPreferences;
    //private GoogleApiClient mGoogleApiClient;
    private String SourceChannel="";
    private String UserID="";
    private String Name="";
    private String ChannelId="";
    EditText editName;
    Button buttonSubmit;
    //private CallbackManager callbackManager;
//    private LoginButton loginbutton;
    private static final String EMAIL = "email";
    private TextView gotosignup;
    private TextView skiptoindex;
    private String Latitude="";
    private String Longitude="";
    private TextView otplabel;
    private TextView ChangeMobile;
    private TextView GetOTP;

    String count="";
    private String verificationID;
    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        appPreferences = new AppPreferences(this);
        new VersionChecker(VerifyotpActivity.this).execute();
        editName  = (EditText) findViewById(R.id.username);
        buttonSubmit = (Button) findViewById(R.id.login);
//        loginbutton=findViewById(R.id.login_button);
        otplabel=(TextView) findViewById(R.id.otplabel);
//        gotosignup = (TextView) findViewById(R.id.gotosignup);
        skiptoindex=(TextView) findViewById(R.id.skiptoindex);
        GetOTP=(TextView) findViewById(R.id.GetOTP);
        //mAuth=FirebaseAuth.getInstance();
        String Mobile=appPreferences.getStuID();
        getSupportActionBar().hide();
        //sendVerificationCode(Mobile);
        if(Mobile!=null){

            otplabel.setText("OTP has been sent to your mobile no. : "+Mobile.replace("+91",""));
        }
        GetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UpdateToken(appPreferences.getStuID().replace("+91",""));
                //sendVerificationCode(appPreferences.getStuID());
            }
        });
        ChangeMobile=(TextView) findViewById(R.id.ChangeMobile);
        ChangeMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appPreferences.setStuID("");

                startActivity(new Intent(VerifyotpActivity.this, PreActivity.class));
                finish();

            }
        });
//        gotosignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(PreActivity.this, SignUp.class));
//                finish();
//            }
//        });


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
                    startActivity(new Intent(VerifyotpActivity.this, HomeActivity.class));
                    finish();
                }

            }
        });
//        VerifyotpActivity.super.requestAppPermissions(new
//                        String[]{ Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.BROADCAST_SMS,Manifest.permission.RECEIVE_SMS,
//                        Manifest.permission.READ_SMS}
//                ,R.string.runtime_permissions_txt
//                , REQUEST_PERMISSIONS);
//        appPreferences = new AppPreferences(this);
        VerifyotpActivity.super.requestAppPermissions(new
                        String[]{ Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION
                        }
                ,R.string.runtime_permissions_txt
                , REQUEST_PERMISSIONS);
        appPreferences = new AppPreferences(this);
//        GPStracker g=new GPStracker(getApplicationContext());
//        Location l=g.getLocation();
//        if(l!=null){
//            double Lat=l.getLatitude();
//
//            Latitude=String.valueOf(Lat);
//            //txtLatitude.setText(Latitude);
//            appPreferences.setSchoolID(Latitude);
//
//
//            double Long=l.getLongitude();
//
//            Longitude=String.valueOf(Long);
//            appPreferences.setStatffID(Longitude);
//           // txtLongitude.setText(Longitude);
//            Toast.makeText(getApplicationContext(),"Lat:"+Lat+"\n Long:"+Long,Toast.LENGTH_LONG).show();
//        }



        count=appPreferences.getSessionCount();
                    if(count!="LoginVerified") {
                        new CountDownTimer(60000, 1000) {

                            public void onTick(long millisUntilFinished) {

                            }

                            public void onFinish() {

                                    count=appPreferences.getSessionCount();
                                    if(count!="LoginVerified") {
                                        Toast.makeText(VerifyotpActivity.this, "Your login session has been expired, Please Login again.", Toast.LENGTH_SHORT);
                                        appPreferences.setSessionCount("ResendOtp");
                                        appPreferences.setStuID("");
                                        Intent i = new Intent(VerifyotpActivity.this, PreActivity.class);
                                        startActivity(i);
                                        finish();
                                    }



                            }
                        }.start();
                    }



        cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet()) {

            // Attaching OnClick listener to the submit button
            buttonSubmit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String UserOTP=editName.getText().toString();
                    Name="dummyy";
                    ChannelId="dummyy";
                    SourceChannel="signinbtn";
                    appPreferences.setschoolname(SourceChannel);

String Otp=appPreferences.getUserOTP();
                    //appPreferences.setStuID(UserID);
                    appPreferences.setRegNo(ChannelId);
                    appPreferences.setimagePath(Name);

                    if(UserOTP.isEmpty() || UserOTP.length()<6){
                        editName.setError("Enter code...");
                        editName.requestFocus();
                        return;
                    }

                    //verifycode(UserOTP);
if(UserOTP.equals(Otp))
                    {
                        appPreferences.setSessionCount("LoginVerified");
                        startActivity(new Intent(VerifyotpActivity.this, HomeActivity.class));
                        finish();
                    }

                    else
                    {
                        Toast.makeText(VerifyotpActivity.this, "Invalid OTP. ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            if (!appPreferences.getStuID().equals("")) {
//                startActivity(new Intent(VerifyotpActivity.this, HomeActivity.class));
//                finish();
//            }
//            else
//            {
////                GooogleLogin();
//            }

        }
        else{
            Intent i = new Intent(VerifyotpActivity.this,noConnection.class);
            startActivity(i);
        }



    }


//    private void verifycode(String code){
//        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationID,code);
//        signInWithCredential(credential);
//    }

//    private void signInWithCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    appPreferences.setSessionCount("LoginVerified");
//                    Toast.makeText(VerifyotpActivity.this,"OTP verified",Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(VerifyotpActivity.this, HomeActivity.class));
//                    finish();
//
//                }
//                else
//                {
//
//                    Toast.makeText(VerifyotpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void sendVerificationCode(String number){
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                number,
//                60,
//                TimeUnit.SECONDS,
//                TaskExecutors.MAIN_THREAD,
//                mCallBack
//        );
//    }
//
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
//            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            verificationID=s;
//        }
//
//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//            String code = phoneAuthCredential.getSmsCode();
//            if(code!=null){
//                editName.setText(code);
//                //verifycode(code);
//            }
//        }
//
//
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//            UpdateToken1(appPreferences.getStuID().replace("+91",""));
//            //Toast.makeText(VerifyotpActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
//        }
//    };


    @Override
    public void onPermissionsGranted(final int requestCode) {
        //Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(VerifyotpActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(VerifyotpActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(VerifyotpActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(VerifyotpActivity.this, new String[]{permission}, requestCode);
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
                    break;
                //Read External Storage
                case 4:
//                    Intent imageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(imageIntent, 11);
                    break;
                //Camera
                case 5:
//                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivityForResult(takePictureIntent, 12);
//                    }
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

//    public void UpdateToken1(String Mobile) {
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        String url = "https://m.oxyzenhomes.com/web/Sendotpactions?mob="+Mobile;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String _response) {
//
//                        String result= String.valueOf(_response.substring(1));
//                        result=result.substring(0, result.length() - 1);
//                        appPreferences.setUserOTP(result);
//                        otplabel.setText("OTP has been send to your mobile no.: "+appPreferences.getStuID().replace("+91",""));
//                        //GotoVerification();
//                        // Log.d("logr=",_response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                // Error handling
//                String result= String.valueOf(error);
////                Log.d("log2=", error.toString());
////                Log.e("log3=", error.toString());
//            }
//        });
////excecute your request
//        queue.add(stringRequest);
//
//
//    }
//    public void UpdateToken(String Mobile) {
//
//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        String url = "https://m.oxyzenhomes.com/web/Sendotpactions?mob="+Mobile;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String _response) {
//
//                        String result= String.valueOf(_response.substring(1));
//                        result=result.substring(0, result.length() - 1);
//                        appPreferences.setUserOTP(result);
//                        otplabel.setText("OTP has been re-sent to your mobile no.: "+appPreferences.getStuID().replace("+91",""));
//                        //GotoVerification();
//                        // Log.d("logr=",_response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                // Error handling
//                String result= String.valueOf(error);
////                Log.d("log2=", error.toString());
////                Log.e("log3=", error.toString());
//            }
//        });
////excecute your request
//        queue.add(stringRequest);
//
//
//    }
    private void printToken(GoogleSignInAccount account){
        if(account==null){
            Toast.makeText(this, "You Are Not Authenticated", Toast.LENGTH_SHORT).show();
        }
        else{
            UserID=account.getEmail();
            Name=account.getDisplayName();
            ChannelId=account.getId();
            SourceChannel="Google";
            appPreferences.setschoolname(SourceChannel);
            appPreferences.setStuID(UserID);
            appPreferences.setRegNo(ChannelId);
            appPreferences.setimagePath(Name);
            if (!UserID.equals("")) {
                startActivity(new Intent(VerifyotpActivity.this, HomeActivity.class));
                finish();
            }
            //Toast.makeText(this, "email:"+account.getEmail()+"token:"+account.getIdToken(), Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public void onResume() {
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
//    }
//
//    private BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equalsIgnoreCase("otp")) {
//                final String message = intent.getStringExtra("message");
//                editName.setText(message);
//                //your_edittext.setText(message);
//                //Do whatever you want with the code here
//            }
//        }
//    };

}
