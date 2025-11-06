package com.oxyzenhomes.grapevine.oxyzenrental;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.oxyzenhomes.grapevine.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SocialAccountsActivity extends Activity {
    CallbackManager callbackManager;
    LoginButton loginButton;
    public static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_accounts);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this.getApplication());
        // Initialize callbackManager after Facebook SDK
        callbackManager = CallbackManager.Factory.create();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash", keyHash);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        ImageButton customFbButton = findViewById(R.id.login_button);
        customFbButton.setOnClickListener(v -> {
            LoginManager.getInstance().logInWithReadPermissions(
                    this,
                    Arrays.asList("email", "public_profile")
            );
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("FBLogin", "Success: " + loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("FBLogin", "Login Cancelled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("FBLogin", "Error: " + error.getMessage());
                    }
                });


//        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("FBLogin", "Success: " + loginResult.getAccessToken().getToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d("FBLogin", "Login Cancelled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("FBLogin", "Error: " + error.getMessage());
                    }
                });

    }


    public void onLoginBtnClicked(View v) {
        startActivity(new Intent(SocialAccountsActivity.this, SendOTPMSG91.class));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
