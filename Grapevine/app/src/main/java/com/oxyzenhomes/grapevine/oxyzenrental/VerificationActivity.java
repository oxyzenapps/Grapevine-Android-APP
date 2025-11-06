package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.google.firebase.iid.FirebaseInstanceId;
//import com.msg91.sendotpandroid.library.IPConverter;
//import com.msg91.sendotpandroid.library.SendOTPConfig;
//import com.msg91.sendotpandroid.library.SendOtpVerification;
//import com.msg91.sendotpandroid.library.Verification;
//import com.msg91.sendotpandroid.library.VerificationListener;
import com.msg91.sendotpandroid.library.internal.SendOTP;
import com.msg91.sendotpandroid.library.listners.VerificationListener;
import com.msg91.sendotpandroid.library.roots.RetryType;
import com.msg91.sendotpandroid.library.roots.SendOTPConfigBuilder;
import com.msg91.sendotpandroid.library.roots.SendOTPResponseCode;
import com.msg91.sendotpandroid.library.utils.IPConverter;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.request.RequestBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import in.aabhasjindal.otptextview.OTPListener;
import in.aabhasjindal.otptextview.OtpTextView;


public class VerificationActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, VerificationListener {
    private static final String TAG = "VerificationActivity";
   // private static final String TAG = Verification.class.getSimpleName();
    TextView resend_timer;
   // private Verification mVerification;
    private String SourceChannel="";
    private String UserID="";
    private String Name="";
    private String ChannelId="";
    private AppPreferences appPreferences;
    private Button ResendOTP;
    private Button ChangeMobile;
    private Button SkipToIndex;
    ConnectionDetector cd;
    private RequestBean requestBean;
    //private EditText mOtpEditText;
    private OtpTextView mOtpEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        requestBean = new RequestBean();
        requestBean.setActivity(this);
        setContentView(R.layout.verfity_layout);
        appPreferences = new AppPreferences(this);
        resend_timer = (TextView) findViewById(R.id.resend_timer);
        cd = new ConnectionDetector(getApplicationContext());
        //SkipToIndex=(Button) findViewById(R.id.SkipToIndex);
        mOtpEditText = findViewById(R.id.inputCode);

//        SkipToIndex.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UserID="dummyy";
//                Name="dummyy";
//                ChannelId="dummyy";
//                SourceChannel="skipbtn";
//                appPreferences.setschoolname(SourceChannel);
//                appPreferences.setStuID(UserID);
//                appPreferences.setRegNo(ChannelId);
//                appPreferences.setimagePath(Name);
//                if (!UserID.equals("")) {
//                    startActivity(new Intent(VerificationActivity.this, HomeActivity.class));
//                    finish();
//                }
//            }
//        });
        ChangeMobile=(Button) findViewById(R.id.ChangeMobile);
        ChangeMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(VerificationActivity.this, SendOTPMSG91.class);
                startActivity(i);
            }
        });
        mOtpEditText.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(@NotNull String otp) {
                if (!otp.isEmpty()) {
                    hideKeypad();
                    Boolean response=verifyOtp(otp);
                    if(response){
                        onVerified();
                        DataManager.getInstance().showProgressMessage(VerificationActivity.this, "");
//            TextView messageText = (TextView) findViewById(R.id.textView);
//            messageText.setText("Verification in progress");
                        enableInputField(false);
                        mOtpEditText.showSuccess();
                    }
                    else{
                        mOtpEditText.setOTP("");
                        mOtpEditText.requestFocusOTP();
                        Animation example= AnimationUtils.loadAnimation(VerificationActivity.this, R.anim.shake);
                        mOtpEditText.startAnimation(example);
                        mOtpEditText.showError();
                        Toast.makeText(VerificationActivity.this,"Wrong OTP",Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });
//        ResendOTP=(Button) findViewById(R.id.ResendOTP);
//        ResendOTP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initiateVerification();
//            }
//
//        });
        resend_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendCode();
            }
        });
        Intent intent = getIntent();
        Boolean Verified=false;
        if (intent != null) {
            UserID = intent.getStringExtra(SendOTPMSG91.INTENT_PHONENUMBER);


             Verified = intent.getBooleanExtra("IsVerified",false);
        }
        if(!Verified){
            startTimer();
            enableInputField(true);
            initiateVerification();
        }
        else{
            GotoHome();
        }

    }
    public String SendOTP(String MobileNo){
        String result="";
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
                args.add(new BasicNameValuePair("MobileNo", MobileNo));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/SendOTP";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();

                if (response != null) {
                    result=response.toString().replaceAll("\"", "").replace("\n","");


                    //result=result.substring(0, result.length() - 1);
//                    Intent verification = new Intent(this, VerifybyEmail.class);
//                    verification.putExtra("EmailID", MobileNo);
                    appPreferences.setUserOTP(result);
//                    startActivity(verification);
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }
    void createVerification(String phoneNumber, boolean skipPermissionCheck,Integer countryCode) {
//        if (!skipPermissionCheck && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) ==
//                PackageManager.PERMISSION_DENIED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 0);
//            //hideProgressBar();
//        } else {
            boolean withoutOtp = false;
            if (NetworkConnectivity.isConnectedMobileNetwork(getApplicationContext())) {
                withoutOtp = true;
            } else {

            }
        new SendOTPConfigBuilder()
                .setCountryCode(countryCode)
                .setMobileNumber(phoneNumber)
                //////////////////direct verification while connect with mobile network/////////////////////////
                .setVerifyWithoutOtp(withoutOtp)

                //////////////////Auto read otp from Sms And Verify///////////////////////////
                .setAutoVerification(VerificationActivity.this)
                .setOtpExpireInMinute(5)//default value is one day
                .setOtpHits(3) //number of otp request per number
                .setOtpHitsTimeOut(0L)//number of otp request time out reset in milliseconds default is 24 hours
                .setSenderId("GRAPEV")
                .setMessage("Your OTP Verification Code is ##OTP##. Do not share it with anyone.\n" +
                        "Thanks Grapevine.")
                .setOtpLength(6)
                .setVerificationCallBack(this).build();

        SendOTP.getInstance().getTrigger().initiate();

//            SendOTPConfig otpConfig = SendOtpVerification
//                    .config(countryCode + phoneNumber)
//                    .context(this)
//                    .httpsConnection(false)//use false currently https is under maintenance
//                    //////////////////direct verification while connect with mobile network/////////////////////////
//                    .autoVerification(true)
//
//                    //.setAutoVerification(VerificationActivity.this)
//                    .setIp(getIp(withoutOtp))
//                    .verifyWithoutOtp(withoutOtp)
//
//
//                    //////////////////////////////////////////////////////////////////////////////////////////////////
//                    .unicode(false) // set true if you want to use unicode (or other language) in sms
//                    .expiry("5")//value in minutes
//                    .senderId("GRAPEV") //where ABCDEF is any string
//                    .otplength("6") //length of your otp max length up to 9 digits
//                    //--------case 1-------------------
//                            .message("Your OTP Verification Code is ##OTP##. Do not share it with anyone.\n" +
//                                    "Thanks Grapevine.")//##OTP## use for default generated OTP
//                    //--------case 2-------------------
////                      .otp("1234")// Custom Otp code, if want to add yours
////                      .message("1234 is Your verification digits.")//Here 1234 same as above Custom otp.
//                    //-------------------------------------
//                    //use single case at a time either 1 or 2
//                    .build();
//            mVerification = SendOtpVerification.createSmsVerification
//                    (otpConfig, this);
//            mVerification.initiate();
//        }
    }


    /**
     * This work is done  by me rajendra verma
     * if moiblenetwork is true than device is in mobile network
     */
    private String getIp(boolean moibleNetwork) {
        if (moibleNetwork) {
            try {

                return IPConverter.getIPAddress(true);
            } catch (Exception ex) {
            }

        } else {
            WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            return Formatter.formatIpAddress(ip);
        }
        return "";
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "This application needs permission to read your SMS to automatically verify your "
                                + "phone, you may disable the permission once you have been verified.", Toast.LENGTH_LONG)
                        .show();
            }
            enableInputField(true);
        }
        initiateVerificationAndSuppressPermissionCheck();
    }

    void initiateVerification() {
        initiateVerification(false);
    }

    void initiateVerificationAndSuppressPermissionCheck() {
        initiateVerification(true);
    }

    void initiateVerification(boolean skipPermissionCheck) {
        Intent intent = getIntent();
        if (intent != null) {
            //DataManager.getInstance().showProgressMessage(this, "");
            String phoneNumber = intent.getStringExtra(SendOTPMSG91.INTENT_PHONENUMBER);
            Integer countryCode = intent.getIntExtra(SendOTPMSG91.INTENT_COUNTRY_CODE,0);
//            EditText phoneText = (EditText) findViewById(R.id.phoneNumber1);
//            phoneText.setText(phoneNumber);
            //phoneText.setEnabled(false);
            SendOTP(phoneNumber);
           // createVerification(phoneNumber,skipPermissionCheck, countryCode);

//            UserID = intent.getStringExtra(SendOTPMSG91.INTENT_PHONENUMBER);
//
//            String countryCode = intent.getStringExtra(SendOTPMSG91.INTENT_COUNTRY_CODE);
//            EditText phoneNumber=(EditText) findViewById(R.id.phoneNumber1);
//            phoneNumber.setText(UserID);
//            phoneNumber.setEnabled(false);
//            createVerification(UserID, skipPermissionCheck, countryCode);
        }
    }


    public void ResendCode() {
        startTimer();
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra(SendOTPMSG91.INTENT_PHONENUMBER);
        SendOTP(phoneNumber);
        //SendOTP.getInstance().getTrigger().resend(RetryType.VOICE);
    }
    public Boolean verifyOtp(String otp) {
        String SentOTP =  appPreferences.getUserOTP();
        Boolean result=false;
        if(SentOTP.equals(otp)){
            result=true;
        }
        //SendOTP.getInstance().getTrigger().verify(otp);
        return result;
    }
    public void onSubmitClicked(View view) {
        ResendCode();
//        String code = ((EditText) findViewById(R.id.inputCode)).getText().toString();
//        if (!code.isEmpty()) {
//            hideKeypad();
//            Boolean response=verifyOtp(code);
//            if(response){
//                onVerified();
//            }
//
//            DataManager.getInstance().showProgressMessage(this, "");
////            TextView messageText = (TextView) findViewById(R.id.textView);
////            messageText.setText("Verification in progress");
//            enableInputField(false);
//
//        }

//        String code = ((EditText) findViewById(R.id.inputCode)).getText().toString();
//        if (!code.isEmpty()) {
//            hideKeypad();
//            if (mVerification != null) {
//                //mVerification.verify(code);
//               // showProgress();
////                TextView messageText = (TextView) findViewById(R.id.textView);
////                messageText.setText("Verification in progress");
//                //enableInputField(false);
//            }
//        }
    }

    public void onVerified(){
        enableInputField(false);
        Log.d(TAG, "Verified!\n");
        hideKeypad();
        //hideProgressBarAndShowMessage(R.string.verified);
        // showCompleted();
        AlertDialog.Builder builder = new AlertDialog.Builder(VerificationActivity.this);
        builder.setMessage("OTP Verified Successfully.Please Wait...")
                .setCancelable(false);
        String AndroidID= Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //String token = FirebaseInstanceId.getInstance().getToken();
        String MobileNo=appPreferences.getStuID().replace("+91","");


        //UpdateToken("", token,AndroidID,UserID);


        AlertDialog alert = builder.create();
        alert.show();

        GotoHome();


        DataManager.getInstance().hideProgressMessage();
    }

    void enableInputField(boolean enable) {
        //View container = findViewById(R.id.inputContainer);
        if (enable) {
            //container.setVisibility(View.VISIBLE);
            OtpTextView input = (OtpTextView) findViewById(R.id.inputCode);
            input.requestFocusOTP();
        } else {
            //container.setVisibility(View.GONE);
        }
        TextView resend_timer = (TextView) findViewById(R.id.resend_timer);
        resend_timer.setClickable(false);
    }

    void hideProgressBarAndShowMessage(int message) {
        hideProgressBar();
        TextView messageText = (TextView) findViewById(R.id.textView);
        messageText.setText(message);
    }

    void hideProgressBar() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
        progressBar.setVisibility(View.INVISIBLE);
        TextView progressText = (TextView) findViewById(R.id.progressText);
        progressText.setVisibility(View.INVISIBLE);
    }

    void showProgress() {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressIndicator);
        progressBar.setVisibility(View.VISIBLE);
    }

    void showCompleted() {
        ImageView checkMark = (ImageView) findViewById(R.id.checkmarkImage);
        checkMark.setVisibility(View.VISIBLE);
    }

    void showCompleted(boolean isDirect) {
        ImageView checkMark = (ImageView) findViewById(R.id.checkmarkImage);
        if (isDirect) {
            checkMark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_magic));
        } else {
            checkMark.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
        }
        checkMark.setVisibility(View.VISIBLE);
    }

    public void GotoHome(){
        Name="dummyy";
        ChannelId="dummyy";
        SourceChannel="signinbtn";
        appPreferences.setschoolname(SourceChannel);
        appPreferences.setStuID("+91"+UserID);
        String Otp=appPreferences.getUserOTP();
        //appPreferences.setStuID(UserID);
        appPreferences.setRegNo(ChannelId);
        appPreferences.setimagePath(Name);

        appPreferences.setSessionCount("LoginVerified");
        startActivity(new Intent(VerificationActivity.this, HomeActivity.class));
        finish();
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
    private void startTimer() {
        resend_timer.setClickable(false);
        resend_timer.setTextColor(ContextCompat.getColor(VerificationActivity.this, R.color.sendotp_grey));
        new CountDownTimer(30000, 1000) {
            int secondsLeft = 0;

            public void onTick(long ms) {
                if (Math.round((float) ms / 1000.0f) != secondsLeft) {
                    secondsLeft = Math.round((float) ms / 1000.0f);
                    resend_timer.setText("Resend OTP( " + secondsLeft + " )");
                }
            }

            public void onFinish() {
                resend_timer.setClickable(true);
                resend_timer.setText("Resend OTP");
                resend_timer.setTextColor(ContextCompat.getColor(VerificationActivity.this, R.color.resend_timer));
            }
        }.start();
    }

    private void hideKeypad() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
/*        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pkgAppsList = context.getPackageManager().queryIntentActivities(mainIntent, 0)*/
    }

    @Override
    public void onSendOtpResponse(final SendOTPResponseCode responseCode, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "onSendOtpResponse: " + responseCode.getCode() + "=======" + message);
                if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {

                    enableInputField(false);
                    Log.d(TAG, "Verified!\n" + responseCode);
                    hideKeypad();
                    //hideProgressBarAndShowMessage(R.string.verified);
                    // showCompleted();
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerificationActivity.this);
                    builder.setMessage("OTP Verified Successfully.Please Wait...")
                            .setCancelable(false);
                    String AndroidID= Settings.Secure.getString(getApplicationContext().getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    //String token = FirebaseInstanceId.getInstance().getToken();
                    String MobileNo=appPreferences.getStuID().replace("+91","");


                    //UpdateToken("", token,AndroidID,UserID);


                    AlertDialog alert = builder.create();
                    alert.show();

                    GotoHome();


                    DataManager.getInstance().hideProgressMessage();
//                    enableInputField(false);
//                    hideKeypad();
//                    TextView textView2 = (TextView) findViewById(R.id.textView2);
//                    TextView textView1 = (TextView) findViewById(R.id.textView1);
//                    TextView messageText = (TextView) findViewById(R.id.textView);
//                    ImageView topImg = (ImageView) findViewById(R.id.topImg);
//                    TextView phoneText = (TextView) findViewById(R.id.numberText);
//                    RelativeLayout topLayout = findViewById(R.id.topLayout);
//                    if (android.os.Build.VERSION.SDK_INT > 16)
//                        topLayout.setBackgroundDrawable(ContextCompat.getDrawable(VerificationActivity.this, R.drawable.gradient_bg_white));
//                    else
//                        topLayout.setBackgroundResource(R.drawable.gradient_bg_white);
//                    messageText.setVisibility(View.GONE);
//                    phoneText.setVisibility(View.GONE);
//                    topImg.setVisibility(View.INVISIBLE);
//                    textView1.setVisibility(View.VISIBLE);
//                    textView2.setVisibility(View.VISIBLE);
//                    if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER)
//                        textView2.setText("Mobile verified using Invisible OTP.");
//                    else textView2.setText("Your Mobile number has been successfully verified.");

                    //hideProgressBarAndShowMessage(R.string.verified);
                    //showCompleted(responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER);
                } else if (responseCode == SendOTPResponseCode.READ_OTP_SUCCESS) {
                    DataManager.getInstance().hideProgressMessage();
                    mOtpEditText.setOTP(message);

                } else if (responseCode == SendOTPResponseCode.SMS_SUCCESSFUL_SEND_TO_NUMBER || responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_FAILED_SMS_SUCCESSFUL_SEND_TO_NUMBER) {
                    DataManager.getInstance().hideProgressMessage();
                } else if (responseCode == SendOTPResponseCode.NO_INTERNET_CONNECTED) {
                    DataManager.getInstance().hideProgressMessage();
                } else {
                    DataManager.getInstance().hideProgressMessage();
                    hideKeypad();
                    //hideProgressBarAndShowMessage(R.string.failed);
                    enableInputField(true);
                }
            }
        });
    }
}
