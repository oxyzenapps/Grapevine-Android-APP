package com.oxyzenhomes.grapevine.oxyzenrental;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.request.RequestBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class VerifybyEmail extends AppCompatActivity {
    private AppPreferences appPreferences;
    ConnectionDetector cd;
    RequestBean requestBean;
    private Button ResendOTP;
    private Button ChangeMobile;
    private Button SkipToIndex;
    private String SourceChannel="";
    private String UserID="";
    private String Name="";
    private String ChannelId="";
    String EmailID="";
    EditText phoneNumber1;
    Button codeInputButton;
    EditText inputCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.verfitybyemail_layout);
        appPreferences = new AppPreferences(this);
        String OTP=appPreferences.getUserOTP();
        requestBean = new RequestBean();
        requestBean.setActivity(this);
        cd = new ConnectionDetector(getApplicationContext());
        Intent intent = getIntent();
        if (intent != null) {
             EmailID = intent.getStringExtra("EmailID");
        }
         phoneNumber1=(EditText)findViewById(R.id.phoneNumber1);
        phoneNumber1.setText(EmailID);
        codeInputButton=(Button)findViewById(R.id.codeInputButton);
        inputCode=(EditText)findViewById(R.id.inputCode);
        codeInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP=inputCode.getText().toString();
                int otp=Integer.parseInt(OTP);
                int setotp=Integer.parseInt(appPreferences.getUserOTP());
                if(otp==setotp){
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerifybyEmail.this);
                    builder.setMessage("OTP Verified Successfully.Please Wait...")
                            .setCancelable(false);


                    AlertDialog alert = builder.create();
                    alert.show();
                    Name="dummyy";
                    ChannelId="dummyy";
                    SourceChannel="signinbtn";
                    appPreferences.setschoolname(SourceChannel);
                    appPreferences.setStuID(phoneNumber1.getText().toString());
                    String Otp=appPreferences.getUserOTP();
                    //appPreferences.setStuID(UserID);
                    appPreferences.setRegNo(ChannelId);
                    appPreferences.setimagePath(Name);

                    appPreferences.setSessionCount("LoginVerified");
                    startActivity(new Intent(VerifybyEmail.this, HomeActivity.class));
                    finish();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(VerifybyEmail.this);
                    builder.setMessage("Incorrect OTP, try agian.")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
       // resend_timer = (TextView) findViewById(R.id.resend_timer);
        SkipToIndex=(Button) findViewById(R.id.SkipToIndex);
        SkipToIndex.setOnClickListener(new View.OnClickListener() {
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
                    startActivity(new Intent(VerifybyEmail.this, HomeActivity.class));
                    finish();
                }
            }
        });
        ChangeMobile=(Button) findViewById(R.id.ChangeMobile);
        ChangeMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(VerifybyEmail.this, SendOTPMSG91.class);
                startActivity(i);
            }
        });

        ResendOTP=(Button) findViewById(R.id.ResendOTP);
        ResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmailID=phoneNumber1.getText().toString();
                initiateVerification(EmailID);
            }

        });

    }
    public void initiateVerification(String Mobile){
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

                String URL = "http://13.126.193.5:50101/";
                String methodName = "workplace/SendEmailOTP";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    String result=response.toString().replaceAll("\"", "");


                    //result=result.substring(0, result.length() - 1);
                    appPreferences.setUserOTP(result);
                }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
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
}

