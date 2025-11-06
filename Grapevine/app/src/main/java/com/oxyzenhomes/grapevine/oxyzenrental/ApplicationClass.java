package com.oxyzenhomes.grapevine.oxyzenrental;

import android.app.Application;

import com.msg91.sendotpandroid.library.internal.SendOTP;

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SendOTP.initializeApp(this,"309610AW1BkNULe5e005d39P1", "1207164793259808907");
    }
}
