package com.oxyzenhomes.grapevine.oxyzenrental;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.connectivity.HTTPURLConnection;
import com.oxyzenhomes.grapevine.request.RequestBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SendMessageOfNotification extends AppCompatActivity {
    String KEY_REPLY = "key_reply";
    private static final String ACTION_1 ="reject" ;
    private static final String ACTION_3 ="JoinLive" ;
    private static final String ACTION_4 ="Interested" ;
    private static final String ACTION_5 ="Attending" ;
    private static final String ACTION_6 ="MayBe" ;
    private static final String ACTION_7 ="NotInterested" ;
    private static final String Action_Dating_Like="Like";
    private static final String Action_Dating_SuperLike="Super Like";
    private static final String Action_Dating_Nope="Nope";
    private static final String Action_Vehicles_AcceptOffer = "Accept Offer";
    private static final String Action_Vehicles_RejectOffer = "Reject Offer";
Intent intent;
Bundle bundle;
    private RequestBean requestBean;
    ConnectionDetector cd;
    AppPreferences appPreferences;
    String data = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestBean = new RequestBean();
        requestBean.setActivity(this);
        intent = getIntent();
        bundle = intent.getExtras();
        appPreferences = new AppPreferences(this);
        cd = new ConnectionDetector(getApplicationContext());
        String AlertType=intent.getStringExtra("AlertType");

//        if(AlertType.contains("104") || AlertType.contains("204")){
//            String FeedChannelID=intent.getStringExtra("FeedChannelID");
//            String ProtocolID=intent.getStringExtra("ProtocolID");
//            String ChannelFeedChannelID=intent.getStringExtra("ChannelFeedChannelID");
//            String FeedID=intent.getStringExtra("FeedID");
//            int notiid=intent.getIntExtra("NOTIFY_ID",0);
//            SendReply(appPreferences.getApplicant_id(),"",FeedChannelID,ProtocolID,ChannelFeedChannelID,FeedID);
//            String ns = Context.NOTIFICATION_SERVICE;
//            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
//            nMgr.cancel(notiid);
//        }
//        else
//        {
            processInlineReply(intent);
       // }

//        String Message=bundle.getString("Message");
//        String title=bundle.getString("title");
//        String imageurl=bundle.getString("imageurl");
//        String AlertType=bundle.getString("AlertType");
//        String FeedChannelID=bundle.getString("FeedChannelID");
//        sendNotification(Message,title,imageurl,AlertType,FeedChannelID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processInlineReply(intent);
    }

    public void crm_insert_Feed_Activity_Response(String ResponsebyFeedChannelID,String OptionID,String FeedID,Context context){
        if(cd==null){
            requestBean=new RequestBean();
            requestBean.setContext(context);
            cd=new ConnectionDetector(context);
        }
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values=new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("FeedChannelID", "0"));
                args.add(new BasicNameValuePair("ActivityID", "0"));
                args.add(new BasicNameValuePair("ResponsebyFeedChannelID", ResponsebyFeedChannelID));
                args.add(new BasicNameValuePair("OptionID", OptionID));
                args.add(new BasicNameValuePair("type", "Add"));
                args.add(new BasicNameValuePair("FeedID", FeedID));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/crm_insert_Feed_Activity_Response";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {

                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void SendReply(String MyFeedChannelID, String Message, String UserFeedChannelID, String Signal,String ChannelFeedChannelID,String FeedID,Context context) {

        if(cd==null){
            requestBean=new RequestBean();
            requestBean.setContext(context);
            cd=new ConnectionDetector(context);
        }
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values=new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("ChannelFeedChannelID", ChannelFeedChannelID));
                args.add(new BasicNameValuePair("MyFeedChannelID", MyFeedChannelID));
                args.add(new BasicNameValuePair("Message", Message));
                args.add(new BasicNameValuePair("UserFeedChannelID", UserFeedChannelID));
                args.add(new BasicNameValuePair("Signal", Signal));
                args.add(new BasicNameValuePair("FeedID", FeedID));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/SendCallbackResponse";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {

                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String GetFeedChannelID(String ContactID,Context context) {
        String FeedChannelID="";
        if(cd==null){
            requestBean=new RequestBean();
            requestBean.setContext(context);
            cd=new ConnectionDetector(context);
        }
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values=new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("ssn", ContactID));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/GetFeedChannelID";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    FeedChannelID=response.toString();
                    FeedChannelID=FeedChannelID.replaceAll("\"","").replaceAll("\n","");
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return FeedChannelID;
    }

    // [START refresh_token]
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            // myBitmap=getCircleBitmap(myBitmap);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void processInlineReply(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        String action=intent.getAction();
        String UserFeedChannelID=intent.getStringExtra("FeedChannelID");
        String ChannelFeedChannelID=intent.getStringExtra("ChannelFeedChannelID");
        String Signal=intent.getStringExtra("AlertType");
        String FeedID=intent.getStringExtra("FeedID");
        String ServerType=intent.getStringExtra("ServerType");
        String OptionID=intent.getStringExtra("OptionID");
        int NOTIFY_ID=intent.getIntExtra("NOTIFY_ID",0);
        String MyFeedChannelID=appPreferences.getApplicant_id();
        if(Signal.contains("#101.VCS")){
            Signal="!103.VCR."+UserFeedChannelID;
        }
        else if(Signal.contains("#201.ACS"))
        {
            Signal="!203.ACR."+UserFeedChannelID;
        }
        else if(Signal.contains("MEET") && !Signal.contains("Started"))
        {
            Signal="!501.MEETR."+UserFeedChannelID;
        }
        if(Signal.contains("Accepted")){
            appPreferences.setIsAccepted(true);
        }
        if (remoteInput != null && Signal.equals("Message")) {

            String reply = remoteInput.getCharSequence(
                    KEY_REPLY).toString();


            //Set the inline reply text in the TextView
            //txtReplied.setText("Reply is "+reply);
            SendReply(MyFeedChannelID, reply, UserFeedChannelID,  Signal,ChannelFeedChannelID,FeedID,getApplicationContext());
            remoteInput.putCharSequence(KEY_REPLY,"");
            //Update the notification to show that the reply was received.
            NotificationCompat.Builder repliedNotification =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(
                                    android.R.drawable.stat_notify_chat)
                            .setContentText("Inline Reply received");

            NotificationManager notificationManager =
                    (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFY_ID,
                    repliedNotification.build());
            clearExistingNotifications();
            if(appPreferences.getUserPresenceState().equals("NotLive")){
                finishAffinity();
            }

        }
        if (remoteInput != null && Signal.equals("Vehicles")) {

            String reply = remoteInput.getCharSequence(
                    KEY_REPLY).toString();


            //Set the inline reply text in the TextView
            //txtReplied.setText("Reply is "+reply);
            SendReply(MyFeedChannelID, reply, UserFeedChannelID,  Signal,ChannelFeedChannelID,FeedID,getApplicationContext());
            remoteInput.putCharSequence(KEY_REPLY,"");
            String ResponseByFeedChannelID=intent.getStringExtra("ResponseByFeedChannelID");
            String ResponseID=intent.getStringExtra("ResponseID");
            String ListingID =intent.getStringExtra("ListingID");
            String ResponseStatusID=intent.getStringExtra("ResponseStatusID");
            String ListingByFeedChannelID=intent.getStringExtra("ListingByFeedChannelID");


            //MyFeedChannelID=GetFeedChannelID(MyFeedChannelID,getApplicationContext());
            SaveUserResponse(ListingID, ResponseByFeedChannelID, "26",
                    ResponseStatusID,"0",
                    "1","", "",
                    "","",
                    "0","0",ResponseID,getApplicationContext(),ListingByFeedChannelID);
            //Update the notification to show that the reply was received.
            NotificationCompat.Builder repliedNotification =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(
                                    android.R.drawable.stat_notify_chat)
                            .setContentText("Inline Reply received");

            NotificationManager notificationManager =
                    (NotificationManager)
                            getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFY_ID,
                    repliedNotification.build());
            clearExistingNotifications();
            if(appPreferences.getUserPresenceState().equals("NotLive")){
                finishAffinity();
            }
        }
        else if(action!=null && action.equals("reject")){
            appPreferences.setIsAccepted(true);
            if(RingtonePlay.isplayingAudio){
                RingtonePlay.stopAudio();
            }
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(NOTIFY_ID);

            if(appPreferences.getUserPresenceState().equals("NotLive")){
                finishAffinity();
            }
        }
        else if(action!=null && action.equals("mark_read")){
            SendReply(MyFeedChannelID, "mark_read", UserFeedChannelID,  Signal,ChannelFeedChannelID,FeedID,getApplicationContext());
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(NOTIFY_ID);

            if(appPreferences.getUserPresenceState().equals("NotLive")){
                finishAffinity();
            }
        }
        else if(action!=null && action.equals(ACTION_3)){

            Intent i=new Intent(this,HomeActivity.class);
            i.putExtra("AlertType",Signal);
            i.putExtra("FeedChannelID",UserFeedChannelID);
            i.putExtra("ChannelFeedChannelID",ChannelFeedChannelID);
            i.putExtra("FeedID",FeedID);
            i.putExtra("NOTIFY_ID",NOTIFY_ID);
            i.putExtra("ServerType",ServerType);
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(NOTIFY_ID);
            startActivity(i);

        }

        else if(Signal.equals("EventInvitation"))
        {
            MyFeedChannelID=GetFeedChannelID(MyFeedChannelID,getApplicationContext());
            crm_insert_Feed_Activity_Response(MyFeedChannelID,OptionID,FeedID,getApplicationContext());
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(NOTIFY_ID);
            if(appPreferences.getUserPresenceState().equals("NotLive")){
                finishAffinity();
            }
        }
        else if(action!=null && (action.equals(Action_Dating_Like) || action.equals(Action_Dating_SuperLike) || action.equals(Action_Dating_Nope))){
            String MyListingID=intent.getStringExtra("MyListingID");
            String SenderListingID=intent.getStringExtra("SenderListingID");
            String ResponseByResponseStatusID =intent.getStringExtra("ResponseStatusID");
            String ResponseID=intent.getStringExtra("ResponseID");
            String ResponseByFeedChannelID=intent.getStringExtra("ResponseByFeedChannelID");
            String ListingTitle=intent.getStringExtra("ListingTitle");
            String ListingImage=intent.getStringExtra("ListingImage");
            String ResponseStatusID="0";
            if(action.equals(Action_Dating_Like)){
                ResponseStatusID="12";
            }
            else if(action.equals(Action_Dating_SuperLike)){
                ResponseStatusID="15";
            }
            else {
                ResponseStatusID="13";
            }

            //MyFeedChannelID=GetFeedChannelID(MyFeedChannelID,getApplicationContext());
            SaveUserResponse(MyListingID, ResponseByFeedChannelID, "23",
                    ResponseStatusID,"0",
                    "1","", "",
                    "","",
                    ResponseByResponseStatusID,SenderListingID,ResponseID,getApplicationContext(),"0");
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(NOTIFY_ID);
            if(appPreferences.getUserPresenceState().equals("NotLive")){
                finishAffinity();
            }
        }
        else if(action!=null &&  (action.equals(Action_Vehicles_AcceptOffer) || action.equals(Action_Vehicles_RejectOffer))){

            String ResponseByFeedChannelID=intent.getStringExtra("ResponseByFeedChannelID");
            String ResponseID=intent.getStringExtra("ResponseID");
            String ListingID =intent.getStringExtra("ListingID");
            String ResponseStatusID=intent.getStringExtra("ResponseStatusID");
            String ListingByFeedChannelID=intent.getStringExtra("ListingByFeedChannelID");
            String ResponseText=intent.getStringExtra("ResponseText");


            //MyFeedChannelID=GetFeedChannelID(MyFeedChannelID,getApplicationContext());
            SaveUserResponse(ListingID, ResponseByFeedChannelID, "26",
                    ResponseStatusID,"0",
                    "1",ResponseText, "",
                    "","",
                    "0","0",ResponseID,getApplicationContext(),ListingByFeedChannelID);
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(NOTIFY_ID);
            if(appPreferences.getUserPresenceState().equals("NotLive")){
                finishAffinity();
            }
        }
    }
    public void SaveUserResponse(String ListingID, String ResponseByFeedChannelID, String ResponseButtonID,
                                 String ResponseStatusID, String Starred,
                                 String LabelID, String ResponseText, String ResponseName,
                                 String ResponseEmail, String ResponseMobile,
                                 String ResponseByResponseStatusID,String ResponsebyListingID,String ResponseID,
                                 Context context,String ListingByFeedChannelID) {

        if(cd==null){
            requestBean=new RequestBean();
            requestBean.setContext(context);
            cd=new ConnectionDetector(context);
        }
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values=new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("ListingID", ListingID));
                args.add(new BasicNameValuePair("ResponseByFeedChannelID", ResponseByFeedChannelID));
                args.add(new BasicNameValuePair("ResponseButtonID", ResponseButtonID));
                args.add(new BasicNameValuePair("ResponseStatusID", ResponseStatusID));
                args.add(new BasicNameValuePair("Starred", Starred));
                args.add(new BasicNameValuePair("LabelID", LabelID));
                args.add(new BasicNameValuePair("ResponseText", ResponseText));
                args.add(new BasicNameValuePair("ResponseName", ResponseName));
                args.add(new BasicNameValuePair("ResponseEmail", ResponseEmail));
                args.add(new BasicNameValuePair("ResponseMobile", ResponseMobile));
                args.add(new BasicNameValuePair("ResponseByResponseStatusID", ResponseByResponseStatusID));
                args.add(new BasicNameValuePair("ResponsebyListingID", ResponsebyListingID));
                args.add(new BasicNameValuePair("ResponseID", ResponseID));
                args.add(new BasicNameValuePair("ListingByFeedChannelID", ListingByFeedChannelID));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/SaveUserResponseNotification";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {

                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void clearExistingNotifications() {
        int notificationId = getIntent().getIntExtra("NOTIFY_ID", 0);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
        if(RingtonePlay.isplayingAudio){
            RingtonePlay.stopAudio();
        }

    }
}
