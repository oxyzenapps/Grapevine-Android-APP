package com.oxyzenhomes.grapevine.oxyzenrental;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Nirdosh on 10/05/2019.
 */

public class ConnectionDetector extends ContextWrapper {
    private Context context;

    public ConnectionDetector(Context base) {
        super(base);
    }

    public boolean isConnectingToInternet() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;


    }
}

