package com.oxyzenhomes.grapevine.oxyzenrental;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.RemoteInput;

import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.request.RequestBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificationActionService1 extends IntentService  {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "reject";
    private static final String ACTION_BAZ = "com.oxyzenhomes.grapevine.oxyzenrental.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.oxyzenhomes.grapevine.oxyzenrental.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.oxyzenhomes.grapevine.oxyzenrental.extra.PARAM2";

    public NotificationActionService1() {
        super("NotificationActionService1");
    }
    private RequestBean requestBean;


    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationActionService1.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, NotificationActionService1.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(0);
                if(RingtonePlay.isplayingAudio){
                    RingtonePlay.stopAudio();
                }
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

//
//    public void InsertCurrentLocation(String CityID,String LocalityID,String Address,String Pincode,final double Latitude,final double Longitude,String WebsiteID,String MobileNo){
////        RequestQueue queue = Volley.newRequestQueue(this);
////
//
//        if (cd.isConnectingToInternet()) {
//
//
//
//            try {
//
//                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {
//
//                    public void processResponse(Object response) {
//                        if (response != null) {
//
//                            // Bookingdetails(listView);
//                        }
//                    }
//                });
//                List<NameValuePair> args = new ArrayList<>();
//                args.add(new BasicNameValuePair("WebsiteID",WebsiteID));
//                args.add(new BasicNameValuePair("Mobile",MobileNo));
//                args.add(new BasicNameValuePair("Latitude",String.valueOf(Latitude)));
////                args.add(new BasicNameValuePair("callstatus",callstatus));
//                args.add(new BasicNameValuePair("Longitude",String.valueOf(Longitude)));
//                args.add(new BasicNameValuePair("CityID",CityID));
//                args.add(new BasicNameValuePair("LocalityID",LocalityID));
//                args.add(new BasicNameValuePair("Address",Address));
//                args.add(new BasicNameValuePair("Pincode",Pincode));
//                args.add(new BasicNameValuePair("LocationMode","CurrentLocationMode"));
//                String URL = "https://oxyzenhomes.com/";
//                String methodName = "RentalApp/Property/InsertContactCurrentLocation";
//
//                dataFromNetwork.setConfig(URL, methodName, args);
//                Object response = dataFromNetwork.execute().get().toString();
//                if (response != null) {
//
//                    //Bookingdetails(listView);
//                }
//
//
//
//            }
//            catch (Exception e)
//            {
//                System.out.println(e.getMessage());
//            }
//        }
//
//
//    }

}
