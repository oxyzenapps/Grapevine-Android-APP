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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.RemoteViews;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import androidx.core.app.NotificationCompat;
import com.bumptech.glide.Glide;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

  public class MyFirebaseMessagingService24Dec2020 extends FirebaseMessagingService {
      private static final String ACTION_1 = "reject";
      private static final String ACTION_2 = "mark_read";
      private static final String ACTION_3 = "JoinLive";
      private static final String ACTION_4 = "Interested";
      private static final String ACTION_5 = "Attending";
      private static final String ACTION_6 = "MayBe";
      private static final String Action_Dating_Like = "Like";
      private static final String Action_Dating_SuperLike = "Super Like";
      private static final String Action_Dating_Nope = "Nope";

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
              final int NOTIFY_ID = getID();
              String NotificationChannelID = "";
              if (AlertType.contains("101.VCS") || AlertType.contains("201.ACS")) {

                  appPreferences.setIsAccepted(false);
                  NotificationChannelID = "channel_Calls";
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, "Calls", "Calls Notification", NotificationManager.IMPORTANCE_HIGH, 1);
              } else if (AlertType.contains("Message")) {
                  String ChannelName = "Message Notifications";
                  String ChannelDescription = "Groups Notification";
                  NotificationChannelID = "channel_messaging";
                  if (title.contains("@")) {
                      ChannelName = "Groups Notifications";
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
              } else if (AlertType.contains("EventInvitation")) {
                  NotificationChannelID = "channel_others";
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, "Others", "Others Notification", NotificationManager.IMPORTANCE_DEFAULT, 0);
              } else if (AlertType.contains("LCA") || AlertType.contains("LCV")) {
                  NotificationChannelID = "channel_live";
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, "Live", "Live Notification", NotificationManager.IMPORTANCE_HIGH, 0);
              } else {
                  NotificationChannelID = "channel_others";
                  _NotificationUtil.createNotificationChannel(this, NotificationChannelID, "Others", "Others Notification", NotificationManager.IMPORTANCE_DEFAULT, 0);
              }

              if (appPreferences.getUserPresenceState().equals("NotLive")) {

                  boolean areNotificationsEnabled = mNotificationManagerCompat.areNotificationsEnabled();
                  if (!areNotificationsEnabled) {

                      return;
                  }
                  sendNotification(Message, title, imageurl, AlertType, FeedChannelID, ChannelFeedChannelID, FeedID, NOTIFY_ID, NotificationChannelID);
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

      private void sendNotification(String messageBody, String Title, String ImageUrl, String AlertType, String FeedChannelID, String ChannelFeedChannelID, String FeedID, final int NOTIFY_ID, String NotificationChannelID) {
          Intent intent = new Intent(this, HomeActivity.class);
//        intent.putExtra("AlertType",AlertType);
//        intent.putExtra("FeedChannelID",FeedChannelID);
          String LiveImageUrl = "";
          String LiveFeedString = "";
          String ServerType = "";
          String MyListingID = "0";
          String SenderListingID = "0";
          if (AlertType.contains("LCA") || AlertType.contains("LCV")) {
              LiveImageUrl = messageBody.split("-")[1];
              LiveFeedString = messageBody.split("-")[2];
              ServerType = messageBody.split("-")[3];
              messageBody = messageBody.split("-")[0];

          } else if (AlertType.equals("EventInvitation")) {
              LiveImageUrl = messageBody.split("-")[1];
              LiveFeedString = messageBody.split("-")[2];
              //ServerType=messageBody.split("-")[3];
              messageBody = messageBody.split("-")[0];
          } else if (AlertType.equals("Dating")) {
              SenderListingID = messageBody.split("~")[1];
              MyListingID = messageBody.split("~")[2];
          }

          new sendNotification(getApplicationContext())
                  .execute(messageBody,  Title,  ImageUrl,  AlertType,  FeedChannelID,  ChannelFeedChannelID,  FeedID, String.valueOf(NOTIFY_ID),  NotificationChannelID);

//
//          intent.putExtra("AlertType", AlertType);
//          intent.putExtra("FeedChannelID", FeedChannelID);
//          intent.putExtra("UserName", Title);
//          intent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//          intent.putExtra("FeedID", FeedID);
//          intent.putExtra("MyListingID", MyListingID);
//          intent.putExtra("SenderListingID", SenderListingID);
//          intent.putExtra("NOTIFY_ID", NOTIFY_ID);
//          Notification notification = null;
//
//          NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//          StatusBarNotification[] notifications = manager.getActiveNotifications();
//          String UserName = messageBody;
//
//          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//          PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                  PendingIntent.FLAG_ONE_SHOT);
//
//          Intent callIntent = new Intent(MyFirebaseMessagingService.this, IncomingCallActivity.class);
//          callIntent.putExtra("AlertType", AlertType);
//          callIntent.putExtra("FeedChannelID", FeedChannelID);
//          callIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//          callIntent.putExtra("UserName", UserName);
//          callIntent.putExtra("UserPic", ImageUrl);
//          callIntent.putExtra("FeedID", FeedID);
//          callIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//          PendingIntent CallpendingIntent = PendingIntent.getActivity(this, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//          String id = appPreferences.getApplicant_id(); // default_channel_i
//          String title = "Grapevine"; // Default Channel
//          NotificationManager notificationManager = null;
//          // ID of notification
//          NotificationCompat.Builder builder = null;
//
//
//          Uri alarmSound = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.notify);//Here is FILE_NAME is the name of file that you want to play
//
//
//          if (notificationManager == null) {
//              notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//          }
//
//          Bitmap image = null;
//          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//              NotificationChannel notificationChannel = notificationManager.getNotificationChannel(id);
//              if (notificationChannel == null) {
//                  int importance = NotificationManager.IMPORTANCE_HIGH; //Set the importance level
//                  notificationChannel = new NotificationChannel(id, title, importance);
//                  notificationChannel.setLightColor(Color.GREEN); //Set if it is necesssary
//                  notificationChannel.enableVibration(true); //Set if it is necesssary
//                  notificationChannel.setImportance(importance);
//                  notificationChannel.setLockscreenVisibility(1);
//
//                  notificationChannel.setSound(alarmSound, null);
//
//                  notificationManager.createNotificationChannel(notificationChannel);
//
//              }
//
//              Intent acceptIntent = new Intent(MyFirebaseMessagingService.this, SplashScreen.class);
//              acceptIntent.putExtra("type", AlertType + "-Accepted");
//              acceptIntent.putExtra("FeedChannelID", FeedChannelID);
//              acceptIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              acceptIntent.putExtra("UserName", UserName);
//              acceptIntent.putExtra("UserPic", ImageUrl);
//              acceptIntent.putExtra("FeedID", FeedID);
//              acceptIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//              acceptIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//              String message = acceptIntent.getStringExtra("AlertType");
//              String replyLabel = "Enter your message here";
//
//              //Initialise RemoteInput
//              RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
//                      .setLabel(replyLabel)
//
//                      .build();
//
//              //PendingIntent that restarts the current activity instance.
//              Intent resultIntent = new Intent(MyFirebaseMessagingService.this, SendMessageOfNotification.class);
//              resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//              resultIntent.putExtra("AlertType", AlertType);
//              resultIntent.putExtra("FeedChannelID", FeedChannelID);
//              resultIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              resultIntent.putExtra("UserName", UserName);
//              resultIntent.putExtra("FeedID", FeedID);
//              resultIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//              PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//              //Notification Action with RemoteInput instance added.
//              NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
//
//                      R.drawable.ic_baseline_send_24, "Message", resultPendingIntent)
//                      .addRemoteInput(remoteInput)
//
//                      .setAllowGeneratedReplies(true)
//
//                      .build();
//
//              //Notification.Action instance added to Notification Builder.
//              // builder.addAction(replyAction);
//              Intent rejectIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(ACTION_1);
//              rejectIntent.putExtra("AlertType", AlertType);
//              rejectIntent.putExtra("FeedChannelID", FeedChannelID);
//              rejectIntent.putExtra("UserName", UserName);
//              rejectIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              rejectIntent.putExtra("FeedID", FeedID);
//              rejectIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//              Intent markReadIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(ACTION_2);
//              markReadIntent.putExtra("AlertType", AlertType);
//              markReadIntent.putExtra("FeedChannelID", FeedChannelID);
//              markReadIntent.putExtra("UserName", UserName);
//              markReadIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              markReadIntent.putExtra("FeedID", FeedID);
//              markReadIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//
//              Intent JoinLiveIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(ACTION_3);
//              JoinLiveIntent.putExtra("AlertType", AlertType);
//              JoinLiveIntent.putExtra("FeedChannelID", FeedChannelID);
//              JoinLiveIntent.putExtra("UserName", UserName);
//              JoinLiveIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              JoinLiveIntent.putExtra("FeedID", FeedID);
//              JoinLiveIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//              JoinLiveIntent.putExtra("ServerType", ServerType);
//
//
//              Intent InterestedIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(ACTION_4);
//              InterestedIntent.putExtra("AlertType", AlertType);
//              InterestedIntent.putExtra("FeedChannelID", FeedChannelID);
//              InterestedIntent.putExtra("UserName", UserName);
//              InterestedIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              InterestedIntent.putExtra("FeedID", FeedID);
//              InterestedIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//              InterestedIntent.putExtra("OptionID", "1");
//
//              Intent AttendingIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(ACTION_5);
//              AttendingIntent.putExtra("AlertType", AlertType);
//              AttendingIntent.putExtra("FeedChannelID", FeedChannelID);
//              AttendingIntent.putExtra("UserName", UserName);
//              AttendingIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              AttendingIntent.putExtra("FeedID", FeedID);
//              AttendingIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//              AttendingIntent.putExtra("OptionID", "2");
//
//              Intent MayBeIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(ACTION_6);
//              MayBeIntent.putExtra("AlertType", AlertType);
//              MayBeIntent.putExtra("FeedChannelID", FeedChannelID);
//              MayBeIntent.putExtra("UserName", UserName);
//              MayBeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              MayBeIntent.putExtra("FeedID", FeedID);
//              MayBeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//              MayBeIntent.putExtra("OptionID", "3");
//
//
//              //---Dating Pending intents----///
//              Intent DatingLikeIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(Action_Dating_Like);
//              DatingLikeIntent.putExtra("AlertType", AlertType);
//              DatingLikeIntent.putExtra("FeedChannelID", FeedChannelID);
//              DatingLikeIntent.putExtra("UserName", UserName);
//              DatingLikeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              DatingLikeIntent.putExtra("FeedID", FeedID);
//              DatingLikeIntent.putExtra("ResponseStatusID", "12");
//              DatingLikeIntent.putExtra("MyListingID", MyListingID);
//              DatingLikeIntent.putExtra("SenderListingID", SenderListingID);
//              DatingLikeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//
//
//              Intent DatingSuperLikeIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(Action_Dating_SuperLike);
//              DatingSuperLikeIntent.putExtra("AlertType", AlertType);
//              DatingSuperLikeIntent.putExtra("UserName", UserName);
//              DatingSuperLikeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              DatingSuperLikeIntent.putExtra("FeedID", FeedID);
//              DatingSuperLikeIntent.putExtra("ResponseStatusID", "15");
//              DatingLikeIntent.putExtra("MyListingID", MyListingID);
//              DatingLikeIntent.putExtra("SenderListingID", SenderListingID);
//              DatingSuperLikeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//
//
//              Intent DatingNopeIntent = new Intent(this, SendMessageOfNotification.class)
//                      .setAction(Action_Dating_Nope);
//              DatingNopeIntent.putExtra("AlertType", AlertType);
//              DatingNopeIntent.putExtra("UserName", UserName);
//              DatingNopeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
//              DatingNopeIntent.putExtra("FeedID", FeedID);
//              DatingNopeIntent.putExtra("ResponseStatusID", "15");
//              DatingLikeIntent.putExtra("MyListingID", MyListingID);
//              DatingLikeIntent.putExtra("SenderListingID", SenderListingID);
//              DatingNopeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
//
//
////---Dating Pending intents----///
//
//              PendingIntent pendingIntentAccept = PendingIntent.getActivity(this, 0, acceptIntent, PendingIntent.FLAG_ONE_SHOT);
//              PendingIntent pendingIntentReject = PendingIntent.getActivity(this, 0, rejectIntent, PendingIntent.FLAG_ONE_SHOT);
//              PendingIntent pendingIntentMarkRead = PendingIntent.getActivity(this, 0, markReadIntent, PendingIntent.FLAG_ONE_SHOT);
//              PendingIntent pendingIntentLiveJoin = PendingIntent.getActivity(this, 0, JoinLiveIntent, PendingIntent.FLAG_ONE_SHOT);
//
//              PendingIntent pendingIntentInterested = PendingIntent.getActivity(this, 0, InterestedIntent, PendingIntent.FLAG_ONE_SHOT);
//              PendingIntent pendingIntentAttending = PendingIntent.getActivity(this, 0, AttendingIntent, PendingIntent.FLAG_ONE_SHOT);
//              PendingIntent pendingIntentMayBe = PendingIntent.getActivity(this, 0, MayBeIntent, PendingIntent.FLAG_ONE_SHOT);
//
//              //---Dating Intents---//
//              PendingIntent pendingIntentLike = PendingIntent.getActivity(this, 0, DatingLikeIntent, PendingIntent.FLAG_ONE_SHOT);
//              PendingIntent pendingIntentSuperLike = PendingIntent.getActivity(this, 0, DatingSuperLikeIntent, PendingIntent.FLAG_ONE_SHOT);
//              PendingIntent pendingIntentNope = PendingIntent.getActivity(this, 0, DatingNopeIntent, PendingIntent.FLAG_ONE_SHOT);
//
//
//              Log.d(TAG, "Image Created");
//              Bitmap liveImage = null;
//              try {
//                  image = Glide.with(getApplicationContext())
//                          .asBitmap()
//                          .load(ImageUrl)
//                          .submit(512, 512)
//                          .get();
//              } catch (ExecutionException e) {
//                  e.printStackTrace();
//              } catch (InterruptedException e) {
//                  e.printStackTrace();
//              }
//              //final Bitmap LiveImage = null;
//              if (!LiveImageUrl.equals("")) {
//
//                  try {
//                      liveImage = Glide.with(getApplicationContext())
//                              .asBitmap()
//                              .load(LiveImageUrl)
//                              .submit(512, 512)
//                              .get();
//                  } catch (ExecutionException e) {
//                      e.printStackTrace();
//                  } catch (InterruptedException e) {
//                      e.printStackTrace();
//                  }
//                  Log.d(TAG, "Live Image Created");
//              } else {
//                  //image = getBitmapFromURL(ImageUrl);
//              }
//              if (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS") || AlertType.contains("MEET")) {
//                  final String Protocol = AlertType;
//                  notiid = NOTIFY_ID;
//                  Timer timer = new Timer();
//                  timer.schedule(new TimerTask() {
//                      @Override
//                      public void run() {
//
//                          Boolean response = appPreferences.getisAccepted();
//                          if (!response) {
//                              String ProtocolID = "";
//                              if (Protocol.contains("VCS")) {
//                                  ProtocolID = "104";
//                              } else if (Protocol.contains("ACS")) {
//                                  ProtocolID = "204";
//                              }
////
//                              if (RingtonePlay.isplayingAudio) {
//                                  RingtonePlay.stopAudio();
//                              }
//                              _Delegate._sendNotification.SendReply(appPreferences.getApplicant_id(), "", FeedChannelID, ProtocolID, ChannelFeedChannelID, FeedID, getApplicationContext());
//                              String ns = Context.NOTIFICATION_SERVICE;
//                              NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
//                              nMgr.cancel(notiid);
//                          }
//                      }
//                  }, TIME_INTERVAL);
//                  // Logic to turn on the screen
//                  PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
//
//                  if (!powerManager.isInteractive()) { // if screen is not already on, turn it on (get wake_lock for 10 seconds)
//                      @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "MH24_SCREENLOCK");
//                      wl.acquire(10000);
//                      @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MH24_SCREENLOCK");
//                      wl_cpu.acquire(10000);
//                  }
//
//                  RemoteViews _RemoteViews = new RemoteViews(getPackageName(), R.layout.remote_view_call);
//
//                  _RemoteViews.setImageViewBitmap(R.id.User_Image, image);
//                  _RemoteViews.setTextViewText(R.id.title, UserName);
//                  _RemoteViews.setOnClickPendingIntent(R.id.button_reject, pendingIntentReject);
//                  _RemoteViews.setOnClickPendingIntent(R.id.button_accept, pendingIntentAccept);
//
//                  builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this,
//                          NotificationChannelID);
//                  long i = System.currentTimeMillis();
//                  String time = "00:30";
//
//                  long min = Integer.parseInt(time.substring(0, 2));
//                  long sec = Integer.parseInt(time.substring(3));
//
//                  long t = (min * 60L) + sec;
//
//                  long result = TimeUnit.SECONDS.toMillis(t);
//                  result = i + result;
//                  builder.setSmallIcon(R.drawable.grapevinelogo)
//                          .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
//                          .setCustomContentView(_RemoteViews)
//                          .setAutoCancel(true)
//                          .setDefaults(Notification.DEFAULT_ALL)
//                          .setCategory(Notification.CATEGORY_CALL)
//                          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                          .setSound(null)
//                          .setFullScreenIntent(CallpendingIntent, true)
//                  // .addAction(replyAction)
//                  ;
//
//                  finalBuilder = builder;
//                  Task t12 = new Task("Task 1");
//
//                  timer1 = new Timer();
//                  timer1.schedule(t12, 1000, 2000);
//
//
//
//                  //notificationManager.notify(/*notification id*/1, notificationBuilder.build());
////                builder  = new NotificationCompat.Builder(this,NotificationChannelID)
////                        .setContentTitle(Title)
////                        .setLargeIcon(image)
////                        .setContentText(messageBody)
////                        .setSmallIcon(R.drawable.grapevinelogo)
////                        //.setChannelId(id)
////                        .setContentIntent(CallpendingIntent)
////                        .addAction(R.drawable.ic_baseline_call_24,"Reject",pendingIntentReject)
////                        .addAction(replyAction)
////                        .addAction(R.drawable.ic_baseline_call_end_24,"Accept",pendingIntentAccept)
////                        //.addAction(R.id.accept_call,"Accept",pendingIntentAccept)
////                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
////                        .setPriority(NotificationCompat.PRIORITY_MAX)
////                        //.setWhen(0)
////                        .setCategory(NotificationCompat.CATEGORY_CALL)
////                        .setOngoing(true)
////
////                        .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
////                        .setDefaults(NotificationCompat.DEFAULT_VIBRATE).setColor(getResources().getColor(R.color.green_btn));
//
//              } else if (AlertType.contains("#103.VCS") || AlertType.contains("#203.ACS") || AlertType.contains("MEET")) {
//                  if (RingtonePlay.isplayingAudio) {
//                      RingtonePlay.stopAudio();
//                  }
//                  String ns = Context.NOTIFICATION_SERVICE;
//                  NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
//                  nMgr.cancel(NOTIFY_ID);
//
//              } else if (AlertType.contains("Message")) {
//
//                  NotificationCompat.Action replyAction1 = new NotificationCompat.Action.Builder(
//
//                          R.drawable.ic_baseline_send_24, "Reply", resultPendingIntent)
//                          .addRemoteInput(remoteInput)
//
//                          .setAllowGeneratedReplies(true)
//
//                          .build();
//                  builder = new NotificationCompat.Builder(this, NotificationChannelID)
//                          .setContentTitle(Title)
//                          .setLargeIcon(image)
//                          .setContentText(messageBody)
//                          .setPriority(Notification.PRIORITY_MIN)
//                          .setSmallIcon(R.drawable.grapevinelogo)
//                          .addAction(replyAction1)
//                          .addAction(0, "Mark as read", pendingIntentMarkRead)
//                          //.setChannelId(id)
//                          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                          .setContentIntent(pendingIntent)
//                          .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
//                                  setColor(getResources().getColor(R.color.green_btn));
//              } else if (AlertType.contains("LCA") || AlertType.contains("LCV")) {
//
//                  builder = new NotificationCompat.Builder(this, NotificationChannelID)
//                          .setContentTitle(LiveFeedString)
//                          .setLargeIcon(liveImage)
//                          .setContentText(messageBody)
//                          .setPriority(Notification.PRIORITY_MIN)
//                          .setSmallIcon(R.drawable.grapevinelogo)
//                          .setStyle(new NotificationCompat.BigPictureStyle()
//                                  .bigPicture(liveImage)
//                                  .bigLargeIcon(null))
//                          .addAction(0, "Join Now", pendingIntentLiveJoin)
//                          .addAction(0, "Mark as read", pendingIntentMarkRead)
//                          //.setChannelId(id)
//                          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                          .setContentIntent(pendingIntent)
//                          .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
//                                  setColor(getResources().getColor(R.color.green_btn));
//              } else if (AlertType.contains("Dating")) {
//                  liveImage = getBitmapFromURL(ImageUrl);
//                  builder = new NotificationCompat.Builder(this, NotificationChannelID)
//                          .setContentTitle(Title)
//                          .setLargeIcon(liveImage)
//                          .setContentText(messageBody)
//                          .setPriority(Notification.PRIORITY_MIN)
//                          .setSmallIcon(R.drawable.grapevinelogo)
//                          .setStyle(new NotificationCompat.BigPictureStyle()
//                                  .bigPicture(liveImage)
//                                  .bigLargeIcon(null))
//                          .addAction(0, "Like", pendingIntentLike)
//                          .addAction(0, "Super Like", pendingIntentSuperLike)
//                          .addAction(0, "Nope", pendingIntentNope)
//                          //.setChannelId(id)
//                          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                          .setContentIntent(pendingIntent)
//                          .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
//                                  setColor(getResources().getColor(R.color.green_btn));
//              } else if (AlertType.equals("EventInvitation")) {
//                  builder = new NotificationCompat.Builder(this, NotificationChannelID)
//                          .setContentTitle(LiveFeedString)
//                          .setLargeIcon(liveImage)
//                          .setContentText(messageBody)
//                          .setPriority(Notification.PRIORITY_MIN)
//                          .setSmallIcon(R.drawable.grapevinelogo)
//                          .setStyle(new NotificationCompat.BigPictureStyle()
//                                  .bigPicture(liveImage)
//                                  .bigLargeIcon(null))
//                          .addAction(0, "Interested", pendingIntentInterested)
//                          .addAction(0, "Attending", pendingIntentAttending)
//                          .addAction(0, "May Be", pendingIntentMayBe)
//                          //.setChannelId(id)
//                          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                          .setContentIntent(pendingIntent)
//                          .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
//                                  setColor(getResources().getColor(R.color.green_btn));
//              } else {
//
//                  builder = new NotificationCompat.Builder(this, NotificationChannelID)
//                          .setContentTitle(Title)
//                          .setLargeIcon(image)
//                          .setContentText(messageBody)
//                          .setPriority(Notification.PRIORITY_MIN)
//                          .setSmallIcon(R.drawable.grapevinelogo)
//                          //.setChannelId(id)
//                          .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                          .setContentIntent(pendingIntent)
//                          .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
//                                  setColor(getResources().getColor(R.color.green_btn));
//              }
//
//              notificationManager =
//                      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//          } else {
//              //Bitmap image = getBitmapFromURL(ImageUrl);
//              NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NotificationChannelID)
//
//                      .setLargeIcon(image)
//                      .setContentTitle(Title)
//                      .setStyle(new NotificationCompat.BigTextStyle()
//                              .bigText(messageBody))
//                      .setPriority(NotificationManager.IMPORTANCE_MIN)
//
////                    .setDefaults(Notification.DEFAULT_SOUND)
//                      .setContentText(messageBody)
//                      .setAutoCancel(true)
//                      .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                      .setContentIntent(pendingIntent);
//
//
//              mBuilder.setDefaults(0);
//              mBuilder.setSound(null);
//
//              notificationManager =
//                      (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//              Intent i = getPackageManager().getLaunchIntentForPackage("com.oxyzenhomes.grapevine");
//              i.putExtra("AlertType", AlertType);
//              i.putExtra("FeedChannelID", FeedChannelID);
//              i.putExtra("UserName", UserName);
//              i.putExtra("UserPic", ImageUrl);
//              if (appPreferences.getUserPresenceState().equals("NotLive")) {
//                  startActivity(i);
//              }
//
//              //}
//
//          }
////        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//          if (!AlertType.contains("#103.VCR") && !AlertType.contains("#203.ACR")) {
//              mNotificationManagerCompat.notify(NOTIFY_ID,
//                      builder.build());
//          }
//
//          if (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS")) {
//              if (!RingtonePlay.isplayingAudio) {
//                  RingtonePlay.playAudio(this);
//              }
//          }
          //Notification notification = builder.build();
          //notificationManager.notify(NOTIFY_ID, notification);
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
          String SenderListingID = "0";
          String messageBody,  Title,  ImageUrl,  AlertType,  FeedChannelID,  ChannelFeedChannelID,  FeedID;
          int NOTIFY_ID;
          String NotificationChannelID;
          public sendNotification(Context context) {
              super();
              this.ctx = context;
          }

          @Override
          protected Bitmap doInBackground(String... params) {

              InputStream in;
              messageBody=params[0];
              Title=params[1];
              ImageUrl=params[2];
              AlertType=params[3];
              FeedChannelID=params[4];
              ChannelFeedChannelID=params[5];
              FeedID=params[6];
              NotificationChannelID=params[8];
              NOTIFY_ID=Integer.valueOf(params[7]);

              if (AlertType.contains("LCA") || AlertType.contains("LCV")) {
                  LiveImageUrl = messageBody.split("-")[1];
                  LiveFeedString = messageBody.split("-")[2];
                  ServerType = messageBody.split("-")[3];
                  messageBody = messageBody.split("-")[0];

              } else if (AlertType.equals("EventInvitation")) {
                  LiveImageUrl = messageBody.split("-")[1];
                  LiveFeedString = messageBody.split("-")[2];
                  //ServerType=messageBody.split("-")[3];
                  messageBody = messageBody.split("-")[0];
              } else if (AlertType.equals("Dating")) {
                  SenderListingID = messageBody.split("~")[1];
                  MyListingID = messageBody.split("~")[2];
              }
              try {
                  URL url=null;
                  if (AlertType.contains("LCA") || AlertType.contains("LCV") || AlertType.equals("EventInvitation")){
                      url = new URL(LiveImageUrl);
                  }
                  else{
                      url = new URL(ImageUrl);
                  }

                  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                  connection.setDoInput(true);
                  connection.connect();
                  in = connection.getInputStream();
                  Bitmap myBitmap = BitmapFactory.decodeStream(in);
                  return myBitmap;
              } catch (MalformedURLException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              }
              return null;
          }

          @Override
          protected void onPostExecute(Bitmap result) {

              super.onPostExecute(result);
              try {
                  Intent intent = new Intent(ctx, HomeActivity.class);
                  intent.putExtra("AlertType", AlertType);
                  intent.putExtra("FeedChannelID", FeedChannelID);
                  intent.putExtra("UserName", Title);
                  intent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                  intent.putExtra("FeedID", FeedID);
                  intent.putExtra("MyListingID", MyListingID);
                  intent.putExtra("SenderListingID", SenderListingID);
                  intent.putExtra("NOTIFY_ID", NOTIFY_ID);
                  Notification notification = null;

                  NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                  StatusBarNotification[] notifications = manager.getActiveNotifications();
                  String UserName = messageBody;

                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0 /* Request code */, intent,
                          PendingIntent.FLAG_ONE_SHOT);

                  Intent callIntent = new Intent(MyFirebaseMessagingService24Dec2020.this, IncomingCallActivity.class);
                  callIntent.putExtra("AlertType", AlertType);
                  callIntent.putExtra("FeedChannelID", FeedChannelID);
                  callIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                  callIntent.putExtra("UserName", UserName);
                  callIntent.putExtra("UserPic", ImageUrl);
                  callIntent.putExtra("FeedID", FeedID);
                  callIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                  PendingIntent CallpendingIntent = PendingIntent.getActivity(ctx, 0, callIntent, PendingIntent.FLAG_UPDATE_CURRENT);


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

                      Intent acceptIntent = new Intent(MyFirebaseMessagingService24Dec2020.this, SplashScreen.class);
                      acceptIntent.putExtra("type", AlertType + "-Accepted");
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
                      Intent resultIntent = new Intent(MyFirebaseMessagingService24Dec2020.this, SendMessageOfNotification.class);
                      resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      resultIntent.putExtra("AlertType", AlertType);
                      resultIntent.putExtra("FeedChannelID", FeedChannelID);
                      resultIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      resultIntent.putExtra("UserName", UserName);
                      resultIntent.putExtra("FeedID", FeedID);
                      resultIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      PendingIntent resultPendingIntent = PendingIntent.getActivity(ctx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


                      //Notification Action with RemoteInput instance added.
                      NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(

                              R.drawable.ic_baseline_send_24, "Message", resultPendingIntent)
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

                      Intent AttendingIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(ACTION_5);
                      AttendingIntent.putExtra("AlertType", AlertType);
                      AttendingIntent.putExtra("FeedChannelID", FeedChannelID);
                      AttendingIntent.putExtra("UserName", UserName);
                      AttendingIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      AttendingIntent.putExtra("FeedID", FeedID);
                      AttendingIntent.putExtra("NOTIFY_ID", NOTIFY_ID);
                      AttendingIntent.putExtra("OptionID", "2");

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
                      DatingLikeIntent.putExtra("SenderListingID", SenderListingID);
                      DatingLikeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);


                      Intent DatingSuperLikeIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(Action_Dating_SuperLike);
                      DatingSuperLikeIntent.putExtra("AlertType", AlertType);
                      DatingSuperLikeIntent.putExtra("UserName", UserName);
                      DatingSuperLikeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      DatingSuperLikeIntent.putExtra("FeedID", FeedID);
                      DatingSuperLikeIntent.putExtra("ResponseStatusID", "15");
                      DatingLikeIntent.putExtra("MyListingID", MyListingID);
                      DatingLikeIntent.putExtra("SenderListingID", SenderListingID);
                      DatingSuperLikeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);


                      Intent DatingNopeIntent = new Intent(ctx, SendMessageOfNotification.class)
                              .setAction(Action_Dating_Nope);
                      DatingNopeIntent.putExtra("AlertType", AlertType);
                      DatingNopeIntent.putExtra("UserName", UserName);
                      DatingNopeIntent.putExtra("ChannelFeedChannelID", ChannelFeedChannelID);
                      DatingNopeIntent.putExtra("FeedID", FeedID);
                      DatingNopeIntent.putExtra("ResponseStatusID", "15");
                      DatingLikeIntent.putExtra("MyListingID", MyListingID);
                      DatingLikeIntent.putExtra("SenderListingID", SenderListingID);
                      DatingNopeIntent.putExtra("NOTIFY_ID", NOTIFY_ID);


//---Dating Pending intents----///

                      PendingIntent pendingIntentAccept = PendingIntent.getActivity(ctx, 0, acceptIntent, PendingIntent.FLAG_ONE_SHOT);
                      PendingIntent pendingIntentReject = PendingIntent.getActivity(ctx, 0, rejectIntent, PendingIntent.FLAG_ONE_SHOT);
                      PendingIntent pendingIntentMarkRead = PendingIntent.getActivity(ctx, 0, markReadIntent, PendingIntent.FLAG_ONE_SHOT);
                      PendingIntent pendingIntentLiveJoin = PendingIntent.getActivity(ctx, 0, JoinLiveIntent, PendingIntent.FLAG_ONE_SHOT);

                      PendingIntent pendingIntentInterested = PendingIntent.getActivity(ctx, 0, InterestedIntent, PendingIntent.FLAG_ONE_SHOT);
                      PendingIntent pendingIntentAttending = PendingIntent.getActivity(ctx, 0, AttendingIntent, PendingIntent.FLAG_ONE_SHOT);
                      PendingIntent pendingIntentMayBe = PendingIntent.getActivity(ctx, 0, MayBeIntent, PendingIntent.FLAG_ONE_SHOT);

                      //---Dating Intents---//
                      PendingIntent pendingIntentLike = PendingIntent.getActivity(ctx, 0, DatingLikeIntent, PendingIntent.FLAG_ONE_SHOT);
                      PendingIntent pendingIntentSuperLike = PendingIntent.getActivity(ctx, 0, DatingSuperLikeIntent, PendingIntent.FLAG_ONE_SHOT);
                      PendingIntent pendingIntentNope = PendingIntent.getActivity(ctx, 0, DatingNopeIntent, PendingIntent.FLAG_ONE_SHOT);


                      Log.d(TAG, "Image Created");
                      Bitmap liveImage = null;
                      try {
                          image = Glide.with(getApplicationContext())
                                  .asBitmap()
                                  .load(ImageUrl)
                                  .submit(512, 512)
                                  .get();
                      } catch (ExecutionException e) {
                          e.printStackTrace();
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                      //final Bitmap LiveImage = null;
                      if (!LiveImageUrl.equals("")) {

                          try {
                              liveImage = Glide.with(getApplicationContext())
                                      .asBitmap()
                                      .load(LiveImageUrl)
                                      .submit(512, 512)
                                      .get();
                          } catch (ExecutionException e) {
                              e.printStackTrace();
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }
                          Log.d(TAG, "Live Image Created");
                      } else {
                          //image = getBitmapFromURL(ImageUrl);
                      }
                      if (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS") || AlertType.contains("MEET")) {
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

                          builder = new NotificationCompat.Builder(MyFirebaseMessagingService24Dec2020.this,
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
                                  .setDefaults(Notification.DEFAULT_ALL)
                                  .setCategory(Notification.CATEGORY_CALL)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setSound(null)
                                  .setFullScreenIntent(CallpendingIntent, true)
                          // .addAction(replyAction)
                          ;

                          finalBuilder = builder;
                          Task t12 = new Task("Task 1");

                          timer1 = new Timer();
                          timer1.schedule(t12, 1000, 2000);




                      } else if (AlertType.contains("#103.VCS") || AlertType.contains("#203.ACS") || AlertType.contains("MEET")) {
                          if (RingtonePlay.isplayingAudio) {
                              RingtonePlay.stopAudio();
                          }
                          String ns = Context.NOTIFICATION_SERVICE;
                          NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                          nMgr.cancel(NOTIFY_ID);

                      } else if (AlertType.contains("Message")) {

                          NotificationCompat.Action replyAction1 = new NotificationCompat.Action.Builder(

                                  R.drawable.ic_baseline_send_24, "Reply", resultPendingIntent)
                                  .addRemoteInput(remoteInput)

                                  .setAllowGeneratedReplies(true)

                                  .build();
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(Title)
                                  .setLargeIcon(image)
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
                      } else if (AlertType.contains("LCA") || AlertType.contains("LCV")) {

                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(LiveFeedString)
                                  .setLargeIcon(liveImage)
                                  .setContentText(messageBody)
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
                      } else if (AlertType.contains("Dating")) {
                          liveImage = getBitmapFromURL(ImageUrl);
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(Title)
                                  .setLargeIcon(liveImage)
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
                      } else if (AlertType.equals("EventInvitation")) {
                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(LiveFeedString)
                                  .setLargeIcon(liveImage)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
                                  .setStyle(new NotificationCompat.BigPictureStyle()
                                          .bigPicture(liveImage)
                                          .bigLargeIcon((Bitmap) null))
                                  .addAction(0, "Interested", pendingIntentInterested)
                                  .addAction(0, "Attending", pendingIntentAttending)
                                  .addAction(0, "May Be", pendingIntentMayBe)
                                  //.setChannelId(id)
                                  .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                  .setContentIntent(pendingIntent)
                                  .setDefaults(NotificationCompat.DEFAULT_VIBRATE).
                                          setColor(getResources().getColor(R.color.green_btn));
                      } else {

                          builder = new NotificationCompat.Builder(ctx, NotificationChannelID)
                                  .setContentTitle(Title)
                                  .setLargeIcon(image)
                                  .setContentText(messageBody)
                                  .setPriority(Notification.PRIORITY_MIN)
                                  .setSmallIcon(R.drawable.grapevinelogo)
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