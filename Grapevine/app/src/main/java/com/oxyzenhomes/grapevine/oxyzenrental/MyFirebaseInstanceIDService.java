package com.oxyzenhomes.grapevine.oxyzenrental;

import android.content.Intent;
import android.util.Log;

//import com.google.firebase.iid.FirebaseInstanceId;

//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private AppPreferences appPreferences;
    private static final String TAG = "MyFirebaseIIDService";
//
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        AppPreferences appPreferences=new AppPreferences(getApplicationContext());
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            try {
                String imageurl=object.getString("ImageUrl").toString();
                String AlertType=object.getString("type").toString();
                String FeedChannelID=object.getString("FeedChannelID").toString();
               //sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),imageurl,AlertType,FeedChannelID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            appPreferences.setProjectID(Integer.parseInt(object.get("project_id").toString()));
//             appPreferences.setContactID(object.get("applicant_id").toString());

        }
        super.onMessageReceived(remoteMessage);
    }


    @Override
    public void onNewToken(String s) {
        //String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Refreshed token: " + s);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(s);
        super.onNewToken(s);
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]

//    @Override
//    public void OnN() {
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken);
//    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
