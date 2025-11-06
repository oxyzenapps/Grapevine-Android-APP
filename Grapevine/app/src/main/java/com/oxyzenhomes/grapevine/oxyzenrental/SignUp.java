package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;

import org.json.JSONObject;

import java.util.Arrays;

public class SignUp extends RuntimePermissionsActivity {

    private TextView backtologin;
    private TextView skiptoindex;
    private static final int REQUEST_PERMISSIONS = 20;
    ConnectionDetector cd;
    private GoogleSignInClient googleSignInClient;
    private Button btn;
    private AppPreferences appPreferences;
    private GoogleApiClient mGoogleApiClient;
    private String SourceChannel = "";
    private String UserID = "";
    private String Name = "";
    private String ChannelId = "";
    EditText editName;
    Button buttonSubmit;
//    private CallbackManager callbackManager;
//    private LoginButton loginbutton;
    private static final String EMAIL = "email";
    Button Google_sign_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //backtologin = findViewById(R.id.backtologin);
//        Google_sign_in = (Button) findViewById(R.id.sign_in_button);
        editName = (EditText) findViewById(R.id.username);
        buttonSubmit = (Button) findViewById(R.id.signup);
        //loginbutton = findViewById(R.id.login_button);
        skiptoindex=(TextView) findViewById(R.id.skiptoindex);
        Google_sign_in = (Button) findViewById(R.id.sign_in_button);
//        backtologin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignUp.this, PreActivity.class));
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
                    startActivity(new Intent(SignUp.this, HomeActivity.class));
                    finish();
                }
            }
        });

        SignUp.super.requestAppPermissions(new
                        String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, R.string.runtime_permissions_txt
                , REQUEST_PERMISSIONS);
        appPreferences = new AppPreferences(this);
//
//
        cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet()) {
//
//            // Attaching OnClick listener to the submit button
            buttonSubmit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Name = "dummyy";
                    ChannelId = "dummyy";
                    SourceChannel = "ContinueWithSocial";
                    appPreferences.setschoolname(SourceChannel);
                    if(Google_sign_in.getText()=="Continue With Google")
                    {

                        appPreferences.setGoogleUserName("");
                        appPreferences.setGoogleUserEmail("");
                        appPreferences.setGoogleAppId("");
                    }
//                    if(loginbutton.getText()=="Continue With Facebook"){
//                        appPreferences.setFbUserName("");
//                        appPreferences.setFbUserEmail("");
//                        appPreferences.setFbAppId("");
//                    }


                    startActivity(new Intent(SignUp.this, HomeActivity.class));
                    finish();
                }
            });
//            if (!appPreferences.getStuID().equals("")) {
//                startActivity(new Intent(SignUp.this, HomeActivity.class));
//                finish();
//            } else {
                GooogleLogin();
            //}

        } else {
            //Intent i = new Intent(SignUp.this, noConnection.class);
            //startActivity(i);
       }
//        callbackManager = CallbackManager.Factory.create();
//        loginbutton.setReadPermissions(Arrays.asList(EMAIL));
//        loginbutton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject me, GraphResponse response) {
//
//                                if (response.getError() != null) {
//                                    // handle error
//                                } else {
//
//
//                                    Name = me.optString("first_name") + " " + me.optString("last_name");
//                                    UserID = response.getJSONObject().optString("email");
//                                    ChannelId = me.optString("id");
//                                    SourceChannel = "Facebook";
//                                    appPreferences.setschoolname(SourceChannel);
//                                    appPreferences.setFbUserEmail(UserID);
//                                    appPreferences.setFbAppId(ChannelId);
//                                    appPreferences.setFbUserName(Name);
//                                    if (!UserID.equals("")) {
//                                        loginbutton.setText("Disconnect");
//                                        Toast.makeText(SignUp.this, "Facebook Connection Successfully Done !", Toast.LENGTH_SHORT).show();
//                                    }
//                                    //txtName.setText(user_lastname);
////                            name.setText(user_firstname);
//                                    //txtEmail.setText(user_email);
//
//                                }
//                            }
//                        });
//
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "last_name,first_name,email");
//                request.setParameters(parameters);
//                request.executeAsync();
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });

    }


    @Override
    public void onPermissionsGranted(final int requestCode) {
        //Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(SignUp.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SignUp.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(SignUp.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(SignUp.this, new String[]{permission}, requestCode);
            }
        } else {
            //Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
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
        } else {
            //Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }



    private void GooogleLogin() {
        final Button btnsign=(Button)findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("628034298699-23pcmjq5os13mag2l8u9ck9at03kanhe.apps.googleusercontent.com")
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        printToken(account);
        this.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnsign.getText()=="Disconnect"){
                    signOut();
                }
                else
                {
                    Intent signInIntent = googleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, 3600);
                }

            }
        });
    }

//    private void GooogleLogin() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestIdToken("577914942480-1fsep0lae8kgnsbu771ubaelb1t04rcg.apps.googleusercontent.com")
//                .build();
//        googleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        printToken(account);
//        this.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent signInIntent = googleSignInClient.getSignInIntent();
//                startActivityForResult(signInIntent, 3600);
//            }
//        });
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3600) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                printToken(account);
            } catch (ApiException e) {
                printToken(null);
            }
        }
//        if(loginbutton.getText()=="Disconnect")
//        {
//            LoginManager.getInstance().logOut();
//        }
//        else
//        {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void printToken(GoogleSignInAccount account) {
        if (account == null) {
            Toast.makeText(this, "You Are Not Authenticated", Toast.LENGTH_SHORT).show();
        } else {
            UserID = account.getEmail();
            Name = account.getDisplayName();
            ChannelId = account.getId();
            SourceChannel = "Google";
            appPreferences.setschoolname(SourceChannel);
            appPreferences.setGoogleUserEmail(UserID);
            appPreferences.setGoogleAppId(ChannelId);
            appPreferences.setGoogleUserName(Name);
            if (!UserID.equals("")) {
                Google_sign_in.setText("Disconnect");
                Toast.makeText(SignUp.this, "Google Connection Successfully Done !", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this, "email:"+account.getEmail()+"token:"+account.getIdToken(), Toast.LENGTH_SHORT).show();
        }
    }

    private void signOut() {
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("628034298699-23pcmjq5os13mag2l8u9ck9at03kanhe.apps.googleusercontent.com")
                .build();
        googleSignInClient = GoogleSignIn.getClient( this,gso);
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Google_sign_in.setText("Continue With Google");
                        appPreferences.setGoogleUserName("");
                        appPreferences.setGoogleUserEmail("");
                        appPreferences.setGoogleAppId("");
                        Toast.makeText(SignUp.this,"Logged Out",Toast.LENGTH_SHORT);
                        //txtEmail.setText("Google Logged out");
                    }
                });
    }
}
