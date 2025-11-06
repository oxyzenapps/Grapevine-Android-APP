  package com.oxyzenhomes.grapevine.oxyzenrental;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import androidx.core.app.NotificationCompat;
import com.bumptech.glide.Glide;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channel;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

  public class MyFirebaseMessagingService extends FirebaseMessagingService {
      private static final String ACTION_1 = "reject";
      private static final String ACTION_2 = "mark_read";
      private static final String ACTION_3 = "JoinLive";
      private static final String ACTION_4 = "Interested";
      private static final String ACTION_5 = "Attending";
      private static final String ACTION_6 = "MayBe";
      private static final String ACTION_7 = "NotInterested";
      private static final String Action_Dating_Like = "Like";
      private static final String Action_Dating_SuperLike = "Super Like";
      private static final String Action_Dating_Nope = "Nope";
      private static final String Action_Vehicles_AcceptOffer = "Accept Offer";
      private static final String Action_Vehicles_RejectOffer = "Reject Offer";

      public static final int TIME_INTERVAL = 30000;
      NotificationCompat.Builder finalBuilder;
      int notiid;
      Timer timer1 = new Timer();
      TimerTask t1;
      private AppPreferences appPreferences;
      NotificationUtil _NotificationUtil = new NotificationUtil();
      private NotificationManagerCompat mNotificationManagerCompat;
      String KEY_REPLY = "key_reply";
      Delegate _Delegate = new Delegate();

//      public void onTokenRefresh() {
//          // Get updated InstanceID token.
//          String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//          Log.d(TAG, "Refreshed token: " + refreshedToken);
//
//          // If you want to send messages to this application instance or
//          // manage this apps subscriptions on the server side, send the
//          // Instance ID token to your app server.
//          sendRegistrationToServer(refreshedToken);
//      }

      @Override
      public void onNewToken(String s) {
          Log.d(TAG, "Refreshed token: " + s);
         // Toast. makeText (MyFirebaseMessagingService.this, s , Toast.LENGTH_SHORT ).show() ;
          // If you want to send messages to this application instance or
          // manage this apps subscriptions on the server side, send the
          // FCM registration token to your app server.
          sendRegistrationToServer(s);
          super.onNewToken(s);
      }

      private static final String TAG = "MyFirebaseMsgService";

      /**
       * Called when message is received.
       *
       * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
       */
      // [START receive_message]
      @Override
      public void onMessageReceived(RemoteMessage remoteMessage) {
          // [START_EXCLUDE]
          // There are two types of messages data messages and notification messages. Data messages are handled
          // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
          // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
          // is in the foreground. When the app is in the background an automatically generated notification is displayed.
          // When the user taps on the notification they are returned to the app. Messages containing both notification
          // and data payloads are treated as notification messages. The Firebase console always sends notification
          // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
          // [END_EXCLUDE]
          appPreferences = new AppPreferences(getApplicationContext());
          // TODO(developer): Handle FCM messages here.
          // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
          Log.d(TAG, "From: " + remoteMessage.getFrom());

          // Check if message contains a data payload.
          if (remoteMessage.getData().size() > 0) {
              Log.d(TAG, "Message data payload: " + remoteMessage.getData());
          }

          // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
          // Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
          mNotificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
          Map<String, String> params = remoteMessage.getData();
          JSONObject object = new JSONObject(params);
          try {
              String Message = object.getString("message").toString();
              String title = object.getString("title").toString();
              String imageurl = object.getString("ImageUrl").toString();
              String AlertType = object.getString("type").toString();
              String FeedChannelID = object.getString("FeedChannelID").toString();
              String ChannelFeedChannelID = object.getString("ChannelFeedChannelID").toString();
              String FeedID = object.getString("FeedID").toString();
              String LargeImage=object.getString("LargeImage").toString();
              final int NOTIFY_ID = getID();
              String NotificationChannelID = "";
              if (AlertType.contains("101.VCS") || AlertType.contains("201.ACS")) {

                  appPreferences.setIsAccepted(false);
                  NotificationChannelID = "channel_Calls";
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, "Call", "Calls Notification", NotificationManager.IMPORTANCE_HIGH, 1);
              }
              else if (AlertType.contains("Message")) {
                  String ChannelName = "Message";
                  String ChannelDescription = "Groups Notification";
                  NotificationChannelID = "channel_messaging";
                  if (title.contains("@")) {
                      ChannelName = "Groups Message";
                      NotificationChannelID = "channel_group_messaging";
                  }
                  //NotificationChannelGroup group=null;
                  //NotificationChannelID="channel_messaging_"+ChannelFeedChannelID;

//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                         group=new NotificationChannelGroup(NotificationChannelID,ChannelName);
//                    }
//                    group.setDescription(ChannelName);
                  //_NotificationUtil.createNotificationChannel(this,NotificationChannelID,ChannelName,"Message Notification",NotificationManager.IMPORTANCE_HIGH,1);
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, ChannelName, ChannelDescription, NotificationManager.IMPORTANCE_HIGH, 1);
              }
              else if (AlertType.contains("EventInvitation") || AlertType.contains("MEET")) {
                  NotificationChannelID = "channel_activites";
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, "Activites", "Others Notification", NotificationManager.IMPORTANCE_DEFAULT, 0);
              }
              else if (AlertType.contains("LCA") || AlertType.contains("LCV")) {
                  NotificationChannelID = "channel_live";
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, "Live", "Live Notification", NotificationManager.IMPORTANCE_HIGH, 0);
              }
              else if (AlertType.contains("Reactions") || AlertType.contains("Feed Reactions")) {
                  NotificationChannelID = "channel_Reactions";
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, "Reactions", "Reactions Notification", NotificationManager.IMPORTANCE_HIGH, 0);
              }
              else if (AlertType.contains("Dating") || AlertType.contains("Matrimonial")|| AlertType.contains("Matched") ) {

                  String ChannelName="";
                  //if(AlertType.contains("Dating")){
                      ChannelName="Dating & Matrimonial";
//                  }
//                  else{
//                      ChannelName="Matrimonial";
//                  }
                  NotificationChannelID = "channel_"+ChannelName;
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, ChannelName, ChannelName+" Notification", NotificationManager.IMPORTANCE_HIGH, 0);
              }
              else {
                  String ChannelName="";
                  String ChannelDescription="";
                  if(AlertType.contains("HisaabKitaab"))
                  {
                      ChannelName="Hisaab Kitaab";
                      ChannelDescription="Hisaab Kitaab Notifications";
                      NotificationChannelID="channel_hisabkitab";
                  }
                  if(AlertType.contains("Taskboard"))
                  {
                      ChannelName="Taskboard";
                      ChannelDescription="Taskboard Notifications";
                      NotificationChannelID="channel_Taskboard";
                  }
                  else if(AlertType.contains("Vehicles"))
                  {
                      ChannelName="Vehicles";
                      NotificationChannelID="channel_vehicles";
                      ChannelDescription="Vehicles Notifications";
                  }
                  else if(AlertType.contains("Jobs"))
                  {
                      ChannelName="Jobs";
                      NotificationChannelID="channel_Jobs";
                      ChannelDescription="Jobs Notifications";
                  }
                  else if(AlertType.contains("Offers")){
                      ChannelName="Offers";
                      ChannelDescription="Offers Notifications";
                      NotificationChannelID="channel_Offers";
                  }
                  else if(AlertType.contains("News")){
                      ChannelName="News";
                      ChannelDescription="News Notifications";
                      NotificationChannelID="channel_News";
                  }
                  else if(AlertType.contains("Oxy_Homes")){
                      ChannelName="Homes";
                      ChannelDescription="Homes Notifications";
                      NotificationChannelID="channel_Homes";
                  }
                      else {
                      ChannelName="Others";
                      ChannelDescription="Others Notifications";
                      NotificationChannelID="channel_Others";
                  }
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, ChannelName, ChannelDescription, NotificationManager.IMPORTANCE_DEFAULT, 0);
              }
              if (appPreferences.getUserPresenceState().equals("NotLive")) {

                  boolean areNotificationsEnabled = mNotificationManagerCompat.areNotificationsEnabled();
                  if (!areNotificationsEnabled) {

                      return;
                  }
                  sendNotification(Message, title, imageurl, AlertType, FeedChannelID, ChannelFeedChannelID, FeedID, NOTIFY_ID, NotificationChannelID,LargeImage);
                  //PlayRingtone();
              }


          } catch (JSONException e) {
              e.printStackTrace();
          }
//
      }
      // [END receive_message]

      /**
       * Create and show a simple notification containing the received FCM message.
       *
       * @param messageBody FCM message body received.
       */
      private final static AtomicInteger c = new AtomicInteger(0);

      public static int getID() {
          return c.incrementAndGet();
      }

      private void sendNotification(String messageBody, String Title, String ImageUrl, String AlertType, String FeedChannelID, String ChannelFeedChannelID, String FeedID, final int NOTIFY_ID, String NotificationChannelID,String LargeImage) {


          new sendNotification(getApplicationContext())
                  .execute(messageBody,  Title,  ImageUrl,  AlertType,  FeedChannelID,  ChannelFeedChannelID,  FeedID, String.valueOf(NOTIFY_ID),  NotificationChannelID,LargeImage);

      }
      Bitmap LargeImageBit=null;
      public class DownloadFileFromURL extends AsyncTask<String, String, String> {

          @Override
          protected String doInBackground(String... f_url) {
              String result = null;
              int count;
              try {
                  URL url = new URL(f_url[0]);
                  URLConnection conection = url.openConnection();
                  conection.connect();
                  // this will be useful so that you can show a tipical 0-100% progress bar
                  int lenghtOfFile = conection.getContentLength();

                  // download the file
                  InputStream input = new BufferedInputStream(url.openStream(), 8192);
                  LargeImageBit = BitmapFactory.decodeStream(input);
                  byte data[] = new byte[1024];

                  long total = 0;

                  while ((count = input.read(data)) != -1) {
                      total += count;

                      publishProgress("" + (int) ((total * 100) / lenghtOfFile));


                  }


                  input.close();

              } catch (Exception e) {
                  Log.e("Error: ", e.getMessage());
              }

              return result;
          }


          @Override
          protected void onPreExecute() {
              super.onPreExecute();

          }


          protected void onProgressUpdate(String... progress) {
              // setting progress percentage

          }


          @Override
          protected void onPostExecute(String file_url) {

          }

      }
      class Task extends TimerTask {
          private String name;

          public Task(String name) {
              this.name = name;
          }

          public void run() {
              Boolean response = appPreferences.getisAccepted();

              if (!response) {
                  mNotificationManagerCompat.notify(notiid,
                          finalBuilder.build());
              } else {
                  if (RingtonePlay.isplayingAudio) {
                      RingtonePlay.stopAudio();
                  }
                  timer1.cancel();
                  timer1 = null;
              }
          }
      }

      public void callTimer(TimerTask t1) {
          timer1.schedule(t1
                  , 3000);

      }

      // [START refresh_token]
      public Bitmap getBitmapFromURL(String strURL) {
          try {
              URL url = new URL(strURL);

              HttpURLConnection connection = (HttpURLConnection) url.openConnection();
              connection.setDoInput(true);
              connection.connect();
              InputStream input = connection.getInputStream();

              // Decode Bitmap

              Bitmap myBitmap = BitmapFactory.decodeStream(input);
              myBitmap = getCircleBitmap(myBitmap);
              return myBitmap;
          } catch (IOException e) {
              e.printStackTrace();
              return null;
          }
      }


      private class sendNotification extends AsyncTask<String, Void, Bitmap> {

          Context ctx;
          String message;
          String LiveImageUrl = "";
          String LiveFeedString = "";
          String ServerType = "";
          String MyListingID = "0";
          String ResponseID = "0";
          String ResponseStatusID = "0";
          String SenderListingID = "0";
          String ListingID="0";
          String NotiType="";
          String ListingByFeedChannelID="0";

          String ResponseByFeedChannelID="0";
          String messageBody,  Title,  ImageUrl,ListingImage,ListingTitle,
                  AlertType,  FeedChannelID,  ChannelFeedChannelID,
                  FeedID,ResponseText,SinglePageURL;
          int NOTIFY_ID;
          String NotificationChannelID;
          HttpURLConnection connection=null;
          String LargeImage;
          public sendNotification(Context context) {
              super();
              this.ctx = context;
          }

          @Override
          protected Bitmap doInBackground(String... params) {

              InputStream in=null;
              messageBody=params[0];
              Title=params[1];
              ImageUrl=params[2];
              AlertType=params[3];
              FeedChannelID=params[4];
              ChannelFeedChannelID=params[5];
              FeedID=params[6];
              NotificationChannelID=params[8];
              NOTIFY_ID=Integer.valueOf(params[7]);
              LargeImage=params[9];

              if (AlertType.contains("LCA") || AlertType.contains("LCV")) {
                  if( messageBody.split("!").length>3){
                      LiveImageUrl = messageBody.split("!")[1];
                      LiveFeedString = messageBody.split("!")[2];
                      ServerType = messageBody.split("!")[3];
                      messageBody = messageBody.split("!")[0];
                  }


              }
              else if (AlertType.equals("EventInvitation")) {
                  LiveImageUrl = messageBody.split("!")[1];
                  LiveFeedString = messageBody.split("!")[2];
                  //ServerType=messageBody.split("-")[3];
                  messageBody = messageBody.split("!")[0];
              }
              else if (AlertType.equals("Dating") || AlertType.equals("Matched")) {
                  SenderListingID = messageBody.split("~")[1];
                  MyListingID = messageBody.split("~")[2];
                  if(!AlertType.equals("Matched")){
                      ResponseID=messageBody.split("~")[3];
                      ResponseStatusID=messageBody.split("~")[4];
                      ResponseByFeedChannelID=messageBody.split("~")[5];
                      ListingTitle=messageBody.split("~")[6];
                      ListingImage=messageBody.split("~")[7];
                  }
                  messageBody=messageBody.split("!")[0];
              }
              else if(AlertType.equals("Vehicles")){
                  ListingID=messageBody.split("~")[1];
                  ListingByFeedChannelID=messageBody.split("~")[2];
                  ResponseID=messageBody.split("~")[3];
                  ResponseByFeedChannelID=messageBody.split("~")[4];
                  ResponseText=messageBody.split("~")[5];
                  messageBody=messageBody.split("!")[0];
              }
              else if(AlertType.equals("Oxy_Homes"))
              {
                  ListingID=messageBody.split("~")[1];
                  NotiType=messageBody.split("~")[2];
                  ResponseByFeedChannelID=messageBody.split("~")[3];
                  ResponseID=messageBody.split("~")[4];
                  messageBody=messageBody.split("!")[0];

              }
              else if(AlertType.equals("SinglePageNotification"))
              {
                  if( messageBody.split("!").length>0){
                      SinglePageURL = messageBody.split("!")[1];
                      messageBody = messageBody.split("!")[0];
                  }
              }
              else if(AlertType.contains("MEET"))
              {
                  if( messageBody.split("!").length>0){
                      LiveImageUrl = messageBody.split("!")[1];
                      messageBody = messageBody.split("!")[0];
                      LiveFeedString=messageBody;
                      ServerType="ms";
                  }
              }
              try {
                  URL url=null;
                  if (AlertType.contains("LCA") || AlertType.contains("LCV")|| AlertType.contains("MEET")|| AlertType.contains("LAM") || AlertType.equals("EventInvitation")){
                      url = new URL(LiveImageUrl);
                  }
                  else if(AlertType.equals("Dating")){
                      url=new URL(ListingImage);
                  }
                  else{
                      //ImageUrl="https://www.grapevine.work/workplace_resfile/images/icons/emoji/like.png";
                      url = new URL(ImageUrl);
                  }


                  //from web
                  Bitmap bitmap=null;
                  URLConnection conection = url.openConnection();
                  conection.connect();
                  // this will be useful so that you can show a tipical 0-100% progress bar
                  // download the file
                  InputStream input = new BufferedInputStream(url.openStream(), 8192);
                  bitmap = BitmapFactory.decodeStream(input);
//
//
//                      URL imageUrl = url;
//                      connection = (HttpURLConnection)imageUrl.openConnection();
//                      connection.setConnectTimeout(5000);
//                      connection.setReadTimeout(30000);
//                      connection.setInstanceFollowRedirects(true);
//                      InputStream is=connection.getInputStream();
////                      OutputStream os = new FileOutputStream(f);
////                      Utils.CopyStream(is, os);
////                      os.close();
//                      bitmap = BitmapFactory.decodeStream(is);

                      return bitmap;

//                   connection = (HttpURLConnection) url.openConnection();
//                  connection.setDoInput(true);
//                  connection.connect();
//                  in = connection.getInputStream();
//                  Bitmap myBitmap = BitmapFactory.decodeStream(in);
//                  return myBitmap;
              } catch (MalformedURLException e) {
                  if(connection!=null){
                      connection.disconnect();
                  }
                  if(in!=null){
                      try {
                          in.close();
                      } catch (IOException ex) {
                          ex.printStackTrace();
                      }
                  }
                  e.printStackTrace();
              } catch (IOException e) {
                  if(connection!=null){
                      connection.disconnect();
                  }
                  if(in!=null){
                      try {
                          in.close();
                      } catch (IOException ex) {
                          ex.printStackTrace();
                      }
                  }
                  e.printStackTrace();
              }
              return null;
          }

          @SuppressLint("WrongConstant")
          @Override
          protected void onPostExecute(Bitmap result) {
              Integer reqCode=appPreferences.getRequestCode()+1;
              appPreferences.setRequestCode(reqCode);


              super.onPostExecute(result);
              if(result==null){
                  result = BitmapFactory.decodeResource(getResources(),R.drawable.grapevinelogo);
              }
              try {
                  if(LargeImage!=null && !LargeImage.equals("")){
                      new DownloadFileFromURL().execute(LargeImage).get();


                  }
                  Intent intent = new Intent(ctx, HomeActivity.class);
                  //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  intent.putExtra("AlertType", AlertType);
                  intent.putExtra("FeedChannelID", FeedChannelID);
                  intent.putExtra("UserName", Title);
                  intent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                  intent.putExtra("FeedID", FeedID);
                  intent.putExtra("MyListingID", MyListingID);
                  intent.putExtra("ResponseStatusID", ResponseStatusID);
                  intent.putExtra("ResponseByFeedChannelID", ResponseByFeedChannelID);
                  intent.putExtra("ResponseID", ResponseID);

                  intent.putExtra("SenderListingID", SenderListingID);
                  intent.putExtra("ListingID", ListingID);
                  intent.putExtra("NotiType", NotiType);
                  intent.putExtra("SinglePageURL", SinglePageURL);
                  intent.putExtra("NOTIFY_ID", NOTIFY_ID);
                  intent.putExtra("MessageBody", messageBody);
                  Notification notification = null;

                  NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                  StatusBarNotification[] notifications = manager.getActiveNotifications();
                  String UserName = messageBody;

                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  PendingIntent pendingIntent = null;
//                  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
//                      pendingIntent = PendingIntent.getActivity
//                              (ctx, reqCode /* Request code */, intent,PendingIntent.FLAG_MUTABLE);
//                  }
//                  else
//                  {
                      pendingIntent = PendingIntent.getActivity
                              (ctx, reqCode /* Request code */, intent, PendingIntent.FLAG_MUTABLE);
                  //}
//
//                  PendingIntent pendingIntent = PendingIntent.getActivity(ctx, reqCode /* Request code */, intent,
//                          PendingIntent.FLAG_ONE_SHOT);

                  Intent callIntent = new Intent(MyFirebaseMessagingService.this, IncomingCallActivity.class);
                  callIntent.putExtra("AlertType", AlertType);
                  callIntent.putExtra("FeedChannelID", FeedChannelID);
                  callIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                  callIntent.putExtra("UserName", UserName);
                  callIntent.putExtra("UserPic", ImageUrl);
                  callIntent.putExtra("FeedID", FeedID);
                  callIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                  PendingIntent CallpendingIntent = PendingIntent.getActivity(ctx, 0, callIntent, PendingIntent.FLAG_MUTABLE);


                  String id = appPreferences.getApplicant_id(); // default_channel_i
                  String title = "Grapevine"; // Default Channel
                  NotificationManager notificationManager = null;
                  // ID of notification
                  NotificationCompat.Builder builder = null;


                  Uri alarmSound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.notify);//Here is FILE_NAME is the name of file that you want to play


                  if (notificationManager == null) {
                      notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                  }

                  Bitmap image = null;
                  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                      NotificationChannel notificationChannel = notificationManager.getNotificationChannel(id);
                      if (notificationChannel == null) {
                          int importance = NotificationManager.IMPORTANCE_HIGH; //Set the importance level
                          notificationChannel = new NotificationChannel(id, title, importance);
                          notificationChannel.setLightColor(Color.GREEN); //Set if it is necesssary
                          notificationChannel.enableVibration(true); //Set if it is necesssary
                          notificationChannel.setImportance(importance);
                          notificationChannel.setLockscreenVisibility(1);

                          notificationChannel.setSound(alarmSound, null);

                          notificationManager.createNotificationChannel(notificationChannel);

                      }

                      //Intent acceptIntent = new Intent(MyFirebaseMessagingService.this, VideoCallActivity.class);
                      Intent acceptIntent = new Intent(MyFirebaseMessagingService.this, SplashScreen.class);
                      acceptIntent.putExtra("AlertType", AlertType + "-Accepted");
                      acceptIntent.putExtra("FeedChannelID", FeedChannelID);
                      acceptIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      acceptIntent.putExtra("UserName", UserName);
                      acceptIntent.putExtra("UserPic", ImageUrl);
                      acceptIntent.putExtra("FeedID", FeedID);
                      acceptIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      acceptIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                      String message = acceptIntent.getStringExtra("AlertType");
                      String replyLabel = "Enter your message here";

                      //Initialise RemoteInput
                      RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
                              .setLabel(replyLabel)

                              .build();

                      //PendingIntent that restarts the current activity instance.
                      Intent resultIntent = new Intent(MyFirebaseMessagingService.this, SendMessageOfNotification.class).
                              setAction("Message");
                      resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      resultIntent.putExtra("AlertType", AlertType);
                      resultIntent.putExtra("FeedChannelID", FeedChannelID);
                      resultIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      resultIntent.putExtra("UserName", UserName);
                      resultIntent.putExtra("FeedID", FeedID);
                      resultIntent.putExtra("NOTIFY_ID", NOTIFY_ID);

                      PendingIntent resultPendingIntent = PendingIntent.getActivity(ctx, 0, resultIntent, PendingIntent.FLAG_MUTABLE);


                      Intent resultVehiclesIntent = new Intent(MyFirebaseMessagingService.this, SendMessageOfNotification.class);
                      resultVehiclesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      resultVehiclesIntent.putExtra("AlertType", AlertType);
                      resultVehiclesIntent.putExtra("FeedChannelID", ListingByFeedChannelID);
                      resultVehiclesIntent.putExtra("ChannelFeedChannelID", ResponseByFeedChannelID);
                      resultVehiclesIntent.putExtra("UserName", UserName);
                      resultVehiclesIntent.putExtra("FeedID", "0");
                      resultVehiclesIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      resultVehiclesIntent.putExtra("ResponseByFeedChannelID", ResponseByFeedChannelID);
                      resultVehiclesIntent.putExtra("ResponseID", ResponseID);
                      resultVehiclesIntent.putExtra("ListingID", ListingID);
                      resultVehiclesIntent.putExtra("ResponseStatusID", "18");
                      resultVehiclesIntent.putExtra("ListingByFeedChannelID", ListingByFeedChannelID);
                      resultVehiclesIntent.putExtra("ResponseText", ResponseText);
                      PendingIntent resultVehiclesPendingIntent = PendingIntent.getActivity(ctx, 0, resultVehiclesIntent, PendingIntent.FLAG_MUTABLE);


                      //Notification Action with RemoteInput instance added.
                      NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(

                              R.drawable.ic_baseline_send_24, "Message", resultPendingIntent)
                              .addRemoteInput(remoteInput)

                              .setAllowGeneratedReplies(true)

                              .build();

                      NotificationCompat.Action replyVehicleAction = new NotificationCompat.Action.Builder(

                              R.drawable.ic_baseline_send_24, "Message", resultVehiclesPendingIntent)
                              .addRemoteInput(remoteInput)

                              .setAllowGeneratedReplies(true)

                              .build();

                      //Notification.Action instance added to Notification Builder.
                      // builder.addAction(replyAction);
                      Intent rejectIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(ACTION_1);
                      rejectIntent.putExtra("AlertType", AlertType);
                      rejectIntent.putExtra("FeedChannelID", FeedChannelID);
                      rejectIntent.putExtra("UserName", UserName);
                      rejectIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      rejectIntent.putExtra("FeedID", FeedID);
                      rejectIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      Intent markReadIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(ACTION_2);
                      markReadIntent.putExtra("AlertType", AlertType);
                      markReadIntent.putExtra("FeedChannelID", FeedChannelID);
                      markReadIntent.putExtra("UserName", UserName);
                      markReadIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      markReadIntent.putExtra("FeedID", FeedID);
                      markReadIntent.putExtra("NOTIFY_ID", NOTIFY_ID);

                      Intent JoinLiveIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(ACTION_3);
                      JoinLiveIntent.putExtra("AlertType", AlertType);
                      JoinLiveIntent.putExtra("FeedChannelID", FeedChannelID);
                      JoinLiveIntent.putExtra("UserName", UserName);
                      JoinLiveIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      JoinLiveIntent.putExtra("FeedID", FeedID);
                      JoinLiveIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      JoinLiveIntent.putExtra("ServerType", ServerType);


                      Intent InterestedIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(ACTION_4);
                      InterestedIntent.putExtra("AlertType", AlertType);
                      InterestedIntent.putExtra("FeedChannelID", FeedChannelID);
                      InterestedIntent.putExtra("UserName", UserName);
                      InterestedIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      InterestedIntent.putExtra("FeedID", FeedID);
                      InterestedIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      InterestedIntent.putExtra("OptionID", "1");

                      Intent NotInterestedIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(ACTION_7);
                      NotInterestedIntent.putExtra("AlertType", AlertType);
                      NotInterestedIntent.putExtra("FeedChannelID", FeedChannelID);
                      NotInterestedIntent.putExtra("UserName", UserName);
                      NotInterestedIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      NotInterestedIntent.putExtra("FeedID", FeedID);
                      NotInterestedIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      NotInterestedIntent.putExtra("OptionID", "4");

                      Intent GoingIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(ACTION_5);
                      GoingIntent.putExtra("AlertType", AlertType);
                      GoingIntent.putExtra("FeedChannelID", FeedChannelID);
                      GoingIntent.putExtra("UserName", UserName);
                      GoingIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      GoingIntent.putExtra("FeedID", FeedID);
                      GoingIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      GoingIntent.putExtra("OptionID", "2");

//                      Intent AttendingIntent = new Intent(ctx, SendMessageOfNotification.class)
//                              .setAction(ACTION_5);
//                      AttendingIntent.putExtra("AlertType", AlertType);
//                      AttendingIntent.putExtra("FeedChannelID", FeedChannelID);
//                      AttendingIntent.putExtra("UserName", UserName);
//                      AttendingIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//                      AttendingIntent.putExtra("FeedID", FeedID);
//                      AttendingIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//                      AttendingIntent.putExtra("OptionID", "2");

                      Intent MayBeIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(ACTION_6);
                      MayBeIntent.putExtra("AlertType", AlertType);
                      MayBeIntent.putExtra("FeedChannelID", FeedChannelID);
                      MayBeIntent.putExtra("UserName", UserName);
                      MayBeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      MayBeIntent.putExtra("FeedID", FeedID);
                      MayBeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      MayBeIntent.putExtra("OptionID", "3");


                      //---Dating Pending intents----///
                      Intent DatingLikeIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(Action_Dating_Like);
                      DatingLikeIntent.putExtra("AlertType", AlertType);
                      DatingLikeIntent.putExtra("FeedChannelID", FeedChannelID);
                      DatingLikeIntent.putExtra("UserName", UserName);
                      DatingLikeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      DatingLikeIntent.putExtra("FeedID", FeedID);
                      DatingLikeIntent.putExtra("ResponseStatusID", "12");
                      DatingLikeIntent.putExtra("MyListingID", MyListingID);
                      DatingLikeIntent.putExtra("ResponseByFeedChannelID", ResponseByFeedChannelID);
                      DatingLikeIntent.putExtra("ResponseStatusID", ResponseStatusID);
                      DatingLikeIntent.putExtra("ResponseID", ResponseID);
                      DatingLikeIntent.putExtra("SenderListingID", SenderListingID);
                      DatingLikeIntent.putExtra("ListingTitle", ListingTitle);
                      DatingLikeIntent.putExtra("ListingImage", ListingImage);
                      DatingLikeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);


                      Intent DatingSuperLikeIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(Action_Dating_SuperLike);
                      DatingSuperLikeIntent.putExtra("AlertType", AlertType);
                      DatingSuperLikeIntent.putExtra("UserName", UserName);
                      DatingSuperLikeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      DatingSuperLikeIntent.putExtra("FeedID", FeedID);
                      DatingSuperLikeIntent.putExtra("ResponseStatusID", "15");
                      DatingSuperLikeIntent.putExtra("MyListingID", MyListingID);
                      DatingSuperLikeIntent.putExtra("ResponseByFeedChannelID", ResponseByFeedChannelID);
                      DatingSuperLikeIntent.putExtra("ResponseStatusID", ResponseStatusID);
                      DatingSuperLikeIntent.putExtra("ResponseID", ResponseID);
                      DatingSuperLikeIntent.putExtra("SenderListingID", SenderListingID);
                      DatingSuperLikeIntent.putExtra("ListingTitle", ListingTitle);
                      DatingSuperLikeIntent.putExtra("ListingImage", ListingImage);
                      DatingSuperLikeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);


                      Intent DatingNopeIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(Action_Dating_Nope);
                      DatingNopeIntent.putExtra("AlertType", AlertType);
                      DatingNopeIntent.putExtra("UserName", UserName);
                      DatingNopeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      DatingNopeIntent.putExtra("FeedID", FeedID);
                      DatingNopeIntent.putExtra("ResponseStatusID", "15");
                      DatingNopeIntent.putExtra("MyListingID", MyListingID);
                      DatingNopeIntent.putExtra("ResponseByFeedChannelID", ResponseByFeedChannelID);
                      DatingNopeIntent.putExtra("ResponseStatusID", ResponseStatusID);
                      DatingNopeIntent.putExtra("ResponseID", ResponseID);
                      DatingNopeIntent.putExtra("SenderListingID", SenderListingID);
                      DatingNopeIntent.putExtra("ListingTitle", ListingTitle);
                      DatingNopeIntent.putExtra("ListingImage", ListingImage);
                      DatingNopeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);


                      //---Dating Pending intents----///



                      //---Vehicles Pending Intents---//


                      Intent VehicleAcceptIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(Action_Vehicles_AcceptOffer);
                      VehicleAcceptIntent.putExtra("AlertType", AlertType);
                      VehicleAcceptIntent.putExtra("UserName", UserName);
                      VehicleAcceptIntent.putExtra("ResponseByFeedChannelID", ResponseByFeedChannelID);
                      VehicleAcceptIntent.putExtra("ResponseID", ResponseID);
                      VehicleAcceptIntent.putExtra("ListingID", ListingID);
                      VehicleAcceptIntent.putExtra("ResponseStatusID", "20");
                      VehicleAcceptIntent.putExtra("ListingByFeedChannelID", ListingByFeedChannelID);
                      VehicleAcceptIntent.putExtra("ResponseText", ResponseText);
                      VehicleAcceptIntent.putExtra("NOTIFY_ID", NOTIFY_ID);

                      Intent VehicleRejectIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(Action_Vehicles_RejectOffer);
                      VehicleRejectIntent.putExtra("AlertType", AlertType);
                      VehicleRejectIntent.putExtra("UserName", UserName);
                      VehicleRejectIntent.putExtra("ResponseByFeedChannelID", ResponseByFeedChannelID);
                      VehicleRejectIntent.putExtra("ResponseID", ResponseID);
                      VehicleRejectIntent.putExtra("ListingID", ListingID);
                      VehicleRejectIntent.putExtra("ResponseStatusID", "21");
                      VehicleRejectIntent.putExtra("ListingByFeedChannelID", ListingByFeedChannelID);
                      VehicleRejectIntent.putExtra("ResponseText", ResponseText);
                      VehicleRejectIntent.putExtra("NOTIFY_ID", NOTIFY_ID);



                      PendingIntent pendingIntentAccept = PendingIntent.getActivity(ctx, 0, acceptIntent, PendingIntent.FLAG_MUTABLE);
                      PendingIntent pendingIntentReject = PendingIntent.getActivity(ctx, 0, rejectIntent, PendingIntent.FLAG_MUTABLE);
                      PendingIntent pendingIntentMarkRead = PendingIntent.getActivity(ctx, 0, markReadIntent, PendingIntent.FLAG_MUTABLE);


                      PendingIntent pendingIntentLiveJoin = PendingIntent.getActivity(ctx, 0, JoinLiveIntent, PendingIntent.FLAG_MUTABLE);

                      PendingIntent pendingIntentInterested = PendingIntent.getActivity(ctx, 0, InterestedIntent, PendingIntent.FLAG_MUTABLE);
                      PendingIntent pendingIntentNotInterested = PendingIntent.getActivity(ctx, 0, NotInterestedIntent, PendingIntent.FLAG_MUTABLE);
                      PendingIntent pendingIntentGoing = PendingIntent.getActivity(ctx, 0, GoingIntent, PendingIntent.FLAG_MUTABLE);
                      PendingIntent pendingIntentMayBe = PendingIntent.getActivity(ctx, 0, MayBeIntent, PendingIntent.FLAG_MUTABLE);

                      //---Dating Intents---//
                      PendingIntent pendingIntentLike = PendingIntent.getActivity(ctx, 0, DatingLikeIntent, PendingIntent.FLAG_MUTABLE);
                      PendingIntent pendingIntentSuperLike = PendingIntent.getActivity(ctx, 0, DatingSuperLikeIntent, PendingIntent.FLAG_MUTABLE);
                      PendingIntent pendingIntentNope = PendingIntent.getActivity(ctx, 0, DatingNopeIntent, PendingIntent.FLAG_MUTABLE);

                      //---Vehicles Intents---//
                      PendingIntent pendingIntentVehiclesAccept = PendingIntent.getActivity(ctx, 0, VehicleAcceptIntent, PendingIntent.FLAG_MUTABLE);
                      PendingIntent pendingIntentVehiclesReject = PendingIntent.getActivity(ctx, 0, VehicleRejectIntent, PendingIntent.FLAG_MUTABLE);

                      Log.d(TAG, "Image Created");
                      messageBody=messageBody.split("!")[0];
                      Bitmap liveImage = result;
                      image = result;
//                      try {
////                          image = Glide.with(getApplicationContext())
////                                  .asBitmap()
////                                  .load(ImageUrl)
////                                  .submit(512, 512)
////                                  .get();
//
//                      } catch (ExecutionException e) {
//                          e.printStackTrace();
//                      } catch (InterruptedException e) {
//                          e.printStackTrace();
//                      }
                      //final Bitmap LiveImage = null;
                      if (!LiveImageUrl.equals("")) {
                          liveImage=result;
//                          try {
//                              liveImage = Glide.with(getApplicationContext())
//                                      .asBitmap()
//                                      .load(LiveImageUrl)
//                                      .submit(512, 512)
//                                      .get();
//                          } catch (ExecutionException e) {
//                              e.printStackTrace();
//                          } catch (InterruptedException e) {
//                              e.printStackTrace();
//                          }
                          Log.d(TAG, "Live Image Created");
                      } else {
                          //image = getBitmapFromURL(ImageUrl);
                      }

                      NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                      //bigTextStyle.setBigContentTitle("Title");
                      bigTextStyle.bigText(messageBody);
                      Toast. makeText (MyFirebaseMessagingService.this, AlertType , Toast.LENGTH_SHORT ).show() ;
                      if (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS") || (AlertType.contains("MEET") && !AlertType.contains("Started"))) {
                          final String Protocol = AlertType;
                          notiid = NOTIFY_ID;
                          Timer timer = new Timer();
                          timer.schedule(new TimerTask() {
                              @Override
                              public void run() {

                                  Boolean response = appPreferences.getisAccepted();
                                  if (!response) {
                                      String ProtocolID = "";
                                      if (Protocol.contains("VCS")) {
                                          ProtocolID = "104";
                                      } else if (Protocol.contains("ACS")) {
                                          ProtocolID = "204";
                                      }
//
                                      if (RingtonePlay.isplayingAudio) {
                                          RingtonePlay.stopAudio();
                                      }
                                      _Delegate._sendNotification.SendReply(appPreferences.getApplicant_id(), "", FeedChannelID, ProtocolID, ChannelFeedChannelID, FeedID, getApplicationContext());
                                      String ns = Context.NOTIFICATION_SERVICE;
                                      NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                                      nMgr.cancel(notiid);
                                  }
                              }
                          }, TIME_INTERVAL);
                          // Logic to turn on the screen
                          PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);

                          if (!powerManager.isInteractive()) { // if screen is not already on, turn it on (get wake_lock for 10 seconds)
                              @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MH24_SCREENLOCK");
                              wl.acquire(10000);
                              @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MH24_SCREENLOCK");
                              wl_cpu.acquire(10000);
                          }

                          RemoteViews _RemoteViews = new RemoteViews(getPackageName(), R.layout.remote_view_call);

                          _RemoteViews.setImageViewBitmap(R.id.User_Image, image);
                          _RemoteViews.setTextViewText(R.id.title, UserName);
                          _RemoteViews.setOnClickPendingIntent(R.id.button_reject, pendingIntentReject);
                          _RemoteViews.setOnClickPendingIntent(R.id.button_accept, pendingIntentAccept);

                          builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this,
                                  NotificationChannelID);
                          long i = System.currentTimeMillis();
                          String time = "00:30";

                          long min = Integer.parseInt(time.substring(0, 2));
                          long sec = Integer.parseInt(time.substring(3));

                          long t = (min * 60L) + sec;

                          long result1 = TimeUnit.SECONDS.toMillis(t);
                          result1 = i + result1;
                          builder.setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                                  .setCustomContentView(_RemoteViews)
                                  .setAutoCancel(true)
                                  .setContentTitle(Title)
                                  .setLargeIcon(image)
                                  //.setStyle(bigTextStyle)
                                  .setDefaults(Notification.DEFAULT_ALL)
                                  .setCategory(Notification.CATEGORY_CALL)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setSound(null)
                                 // .setFullScreenIntent(CallpendingIntent, true)
                          // .addAction(replyAction)
                          ;

                          finalBuilder = builder;
                          Task t12 = new Task("Task 1");

                          timer1 = new Timer();
                          timer1.schedule(t12, 1000, 2000);




                      }
                      else if (AlertType.contains("#103.VCS") || AlertType.contains("#203.ACS") || (AlertType.contains("MEET") && !AlertType.contains("Started"))) {
                          if (RingtonePlay.isplayingAudio) {
                              RingtonePlay.stopAudio();
                          }
                          String ns = Context.NOTIFICATION_SERVICE;
                          NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                          nMgr.cancel(NOTIFY_ID);

                      }
                      else if (AlertType.contains("Message")) {

                          NotificationCompat.Action replyAction1 = new NotificationCompat.Action.Builder(

                                  R.drawable.ic_baseline_send_24, "Reply", resultPendingIntent)
                                  .addRemoteInput(remoteInput)

                                  .setAllowGeneratedReplies(true)

                                  .build();
                          bigTextStyle.setBigContentTitle(Title);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(Title)
                                  .setLargeIcon(image)
                                  .setStyle(bigTextStyle)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .addAction(replyAction1)
                                  .addAction(0, "Mark as read", pendingIntentMarkRead)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));
                      }
                      else if (AlertType.contains("LCA") || AlertType.contains("LCV") || AlertType.contains("LAM")  || (AlertType.contains("MEET") && AlertType.contains("Started"))) {
                          //bigTextStyle.bigText(LiveFeedString);

                          bigTextStyle.setBigContentTitle(LiveFeedString);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(LiveFeedString)
                                  .setLargeIcon(liveImage)
                                  .setStyle(bigTextStyle)
                                  //.setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.BigPictureStyle()
                                          .bigPicture(liveImage)
                                          .bigLargeIcon((Bitmap) null))
                                  .addAction(0, "Join Now", pendingIntentLiveJoin)
                                  .addAction(0, "Mark as read", pendingIntentMarkRead)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));

//                          bigTextStyle.bigText(messageBody);
//                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
//                                  .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
//                                  .setAutoCancel(true)
//                                  .setContentTitle(LiveFeedString)
//                                  .setLargeIcon(liveImage)
//                                  .setStyle(bigTextStyle)
//
//                                  .setPriority(Notification.PRIORITY_MIN)
//                                  .setSmallIcon(R.drawable.grapevinelogo)
//                                  .setStyle(new NotificationCompat.BigPictureStyle()
//                                          .bigPicture(liveImage)
//                                          .bigLargeIcon(null))
//                                  .addAction(0, "Join Now", pendingIntentLiveJoin)
//                                  .addAction(0, "Mark as read", pendingIntentMarkRead)
//                                  //.setChannelId(id)
//                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                                  .setContentIntent(pendingIntent)
//                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
//                                          setColor(getResources().getColor(R.color.green_btn));
                      }
                      else if (AlertType.contains("Dating") ) {
                          //liveImage = getBitmapFromURL(ImageUrl);
                          liveImage=result;
                          bigTextStyle.setBigContentTitle(ListingTitle);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(ListingTitle)
                                  .setLargeIcon(liveImage)
                                  .setStyle(bigTextStyle)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.BigPictureStyle()
                                          .bigPicture(liveImage)
                                          .bigLargeIcon((Bitmap) null))
                                  .addAction(0, "Like", pendingIntentLike)
                                  .addAction(0, "Super Like", pendingIntentSuperLike)
                                  .addAction(0, "Nope", pendingIntentNope)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));
                      }
                      else if (AlertType.contains("Matched") || AlertType.contains("Oxy_Homes")) {
//                          liveImage = getBitmapFromURL(ImageUrl);

                          liveImage=result;
                          bigTextStyle.setBigContentTitle(Title);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(Title)
                                  .setLargeIcon(liveImage)
                                  .setStyle(bigTextStyle)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.BigPictureStyle()
                                          .bigPicture(liveImage)
                                          .bigLargeIcon((Bitmap) null))
                                  .addAction(0, "Mark as read", pendingIntentMarkRead)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));
                      }
                      else if(AlertType.equals("Vehicles") || AlertType.equals("VechicleAccept")){
                          liveImage=result;
                          bigTextStyle.setBigContentTitle(Title);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(Title)
                                  .setLargeIcon(liveImage)
                                  .setStyle(bigTextStyle)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.BigPictureStyle()
                                          .bigPicture(liveImage)
                                          .bigLargeIcon((Bitmap) null))
//                                  .addAction(0, "Accept Offer", pendingIntentVehiclesAccept)
//                                  .addAction(0, "Reject Offer", pendingIntentVehiclesReject)
//                                  .addAction(replyVehicleAction)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));
                          if(AlertType.equals("Vehicles")){
                              builder.addAction(0, "Accept Offer", pendingIntentVehiclesAccept)
                                      .addAction(0, "Reject Offer", pendingIntentVehiclesReject)
                                      .addAction(0, "Mark as read", pendingIntentMarkRead)
                                      .addAction(replyVehicleAction);
                          }
                      }
                      else if (AlertType.equals("EventInvitation")) {
                          bigTextStyle.setBigContentTitle(LiveFeedString);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(LiveFeedString)
                                  .setLargeIcon(liveImage)
                                  .setStyle(bigTextStyle)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.BigPictureStyle()
                                          .bigPicture(liveImage)
                                          .bigLargeIcon((Bitmap) null))
                                  .addAction(0, "Interested", pendingIntentInterested)
                                  .addAction(0, "Going", pendingIntentGoing)
                                  .addAction(0, "Not Interested", pendingIntentNotInterested)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));
                      }
                      else if(AlertType.equals("Offers") || AlertType.equals("Jobs")){
                          bigTextStyle.setBigContentTitle(Title);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(Title)
                                  .setLargeIcon(liveImage)
                                  .setStyle(bigTextStyle)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.BigPictureStyle()
                                          .bigPicture(liveImage)
                                          .bigLargeIcon((Bitmap) null))
                                  .addAction(0, "Mark as read", pendingIntentMarkRead)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));
                      }
                      else {
                          bigTextStyle.setBigContentTitle(Title);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(Title)
                                  .setLargeIcon(image)
                                  .setStyle(bigTextStyle)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.BigPictureStyle()
                                          .bigPicture(LargeImageBit)
                                          .bigLargeIcon((Bitmap) null))
                                  .addAction(0, "Mark as read", pendingIntentMarkRead)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));
                      }

                      notificationManager =
                              (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


                  } else {

                      //Bitmap image = getBitmapFromURL(ImageUrl);
                      NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx, NotificationChannelID)

                              .setLargeIcon(image)
                              .setContentTitle(Title)
                              .setStyle(new NotificationCompat.BigTextStyle()
                                      .bigText(messageBody))
                              .setPriority(NotificationManager.IMPORTANCE_MIN)

//                    .setDefaults(Notification.DEFAULT_SOUND)
                              .setContentText(messageBody)
                              .setAutoCancel(true)
                              .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                              .setContentIntent(pendingIntent);


                      mBuilder.setDefaults(0);
                      mBuilder.setSound(null);

                      notificationManager =
                              (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                      Intent i = getPackageManager().getLaunchIntentForPackage("com.oxyzenhomes.grapevine");
                      i.putExtra("AlertType", AlertType);
                      i.putExtra("FeedChannelID", FeedChannelID);
                      i.putExtra("UserName", UserName);
                      i.putExtra("UserPic", ImageUrl);
                      if (appPreferences.getUserPresenceState().equals("NotLive")) {
                          startActivity(i);
                      }

                      //}

                  }
//        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                  if (!AlertType.contains("#103.VCR") && !AlertType.contains("#203.ACR")) {
                      mNotificationManagerCompat.notify(NOTIFY_ID,
                              builder.build());
                  }

                  if (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS")) {
                      if (!RingtonePlay.isplayingAudio) {
                          RingtonePlay.playAudio(ctx);
                      }
                  }

              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      }


      private Bitmap getCircleBitmap(Bitmap bitmap) {
          final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                  bitmap.getHeight(), Bitmap.Config.ARGB_8888);
          final Canvas canvas = new Canvas(output);

          final int color = Color.RED;
          final Paint paint = new Paint();
          final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
          final RectF rectF = new RectF(rect);

          paint.setAntiAlias(true);
          canvas.drawARGB(0, 0, 0, 0);
          paint.setColor(color);
          canvas.drawOval(rectF, paint);

          paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
          canvas.drawBitmap(bitmap, rect, rect, paint);

          bitmap.recycle();

          return output;
      }

      private void sendRegistrationToServer(String token) {
          // TODO: Implement this method to send token to your app server.
      }
  }