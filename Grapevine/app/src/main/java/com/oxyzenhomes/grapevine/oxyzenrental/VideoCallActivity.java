package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.appprefrence.VersionChecker;
import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.request.RequestBean;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

public class VideoCallActivity extends AppCompatActivity {
    private Activity mActivity;
    private RequestBean requestBean;
    private AppPreferences appPreferences;
    private WebView webView;
    ConnectionDetector cd;
    private static final String TAG = VideoCallActivity.class.getSimpleName();
    ProgressBar asw_progress;
    Intent intent;
    Bundle bundle;
    String AlertType;
    boolean CallAccepted=false;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest;
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);
        mActivity = VideoCallActivity.this;
        //getCurrentAddress();

        requestBean = new RequestBean();

        requestBean.setActivity(this);
        appPreferences = new AppPreferences(this);
        new VersionChecker(VideoCallActivity.this).execute();
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        webView = (WebView) findViewById(R.id.activity_main_webview);
        asw_progress = findViewById(R.id.msw_progress);
        cd = new ConnectionDetector(getApplicationContext());
        intent = getIntent();
        bundle = intent.getExtras();
        String FeedChannelID="";
        String ChannelFeedChannelID="";
        String FeedID="";
        String ServerType="";

        if(bundle!=null){
            AlertType= bundle.getString("AlertType");

            FeedID=bundle.getString("FeedID");
            ServerType=bundle.getString("ServerType");

            //intent.getStringExtra("AlertType");
            FeedChannelID=bundle.getString("FeedChannelID");
            ChannelFeedChannelID=bundle.getString("ChannelFeedChannelID");
        }

        if(AlertType!=null){
            //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if(AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS") || AlertType.contains("#500.MEET")){
                String[] callResponse=AlertType.split("-");
                if(!callResponse[1].equals("Accepted"))
                {
                    PlayRingtone();
                }
                else
                {
                    CallAccepted=true;
                    appPreferences.setIsAccepted(true);
                    if(RingtonePlay.isplayingAudio){

                        RingtonePlay.stopAudio();
                    }
                }
            }
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(0);


        }

        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.setScrollbarFadingEnabled(false); // Explicitly, however it's a default, I think.
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //webView.getSettings().setAppCacheEnabled(false);
        webView.clearHistory();
        webView.clearCache(true);

        webView.clearFormData();
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setUserAgentString(DESKTOP_USER_AGENT);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        methodInvoke(webSettings, "setPluginsEnabled", new Class[]{boolean.class}, new Object[]{true});
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
        if (cd.isConnectingToInternet()) {

            if (!appPreferences.getStuID().equals("")) {

                    if (appPreferences.getShareLink().trim().equals("")) {
                         if(AlertType!=null){
                            int NOTIFY_ID=bundle.getInt("NOTIFY_ID");
                            String ns = Context.NOTIFICATION_SERVICE;
                            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                            nMgr.cancel(NOTIFY_ID);

                            AlertType=AlertType.replace("#","!");
                            // AlertType = urlParam("AlertType");

                            if (AlertType.contains("!101.VCS") || AlertType.contains("!201.ACS")){
                                FeedID = AlertType;
                                String[] FeedID1 = FeedID.split("\\.");
                                String mediaserver = FeedID1[3];
                                FeedID = FeedID1[4];
                                if (FeedID.contains("Accepted")) {
                                    FeedID = FeedID.replace("-Accepted", "");
                                }
                                String  StartDate = "";

                                String FormatAmPm = "";

                                String  user_FeedChanneID = appPreferences.getUserFeedChannelID();
                                if(user_FeedChanneID==null || user_FeedChanneID.equals("")){
                                    user_FeedChanneID=GetFeedChannelID(appPreferences.getApplicant_id());
                                    appPreferences.setUserFeedChannelID(user_FeedChanneID);
                                }
                                String CallFromFeedChannelID = FeedChannelID;
                                if(AlertType.contains("!201.ACS")){

                                    OnOffSpeaker("earpiece");

                                }
                                if(RingtonePlay.isplayingAudio) {
                                    RingtonePlay.stopAudio();
                                }
                                String VideoCall_Page="https://www.grapevine.work/Grapevine/VideoCall.html?MyFeedChannelID=" + user_FeedChanneID + "&UserFeedChannelID=" + CallFromFeedChannelID + "&MyRole=Publisher&Signal=" + AlertType + "&FeedID=" + FeedID + "&StartDate=" + StartDate + "&mediaserver=" + mediaserver+"&PageFrom=Android";
                                webView.loadUrl(VideoCall_Page);
//                                if (mediaserver == "ms") {
//                                    webView.loadUrl("https://www.grapevine.work/workplace/VideoCallWithServer?MyFeedChannelID=" + user_FeedChanneID + "&UserFeedChannelID=" + CallFromFeedChannelID + "&MyRole=Publisher&Signal=" + AlertType + "&FeedID=" + FeedID + "&StartDate=" + StartDate+"&PageFrom=Android");
//                                }
//                                else {
//                                    webView.loadUrl("https://www.grapevine.work/workplace/VideoCall?MyFeedChannelID=" + user_FeedChanneID + "&UserFeedChannelID=" + CallFromFeedChannelID + "&MyRole=Publisher&Signal=" + AlertType + "&FeedID=" + FeedID + "&StartDate=" + StartDate+"&PageFrom=Android");
//                                }
                            }



                        }



                    }



                // webView.loadUrl("http://103.241.146.105:8082/Hindi/Index?stu_id="+stuId+"~Hindi");

            } else {
                appPreferences.setStuID("");
                appPreferences.setschoolname("");
                appPreferences.setRegNo("");
                appPreferences.setimagePath("");
                appPreferences.setFbUserName("");
                appPreferences.setFbUserEmail("");
                appPreferences.setFbAppId("");
                appPreferences.setGoogleUserName("");
                appPreferences.setGoogleUserEmail("");
                appPreferences.setGoogleAppId("");
                appPreferences.setSessionCount("SendOtp");
                appPreferences.setUserOTP("");
                AlertDialog.Builder builder = new AlertDialog.Builder(VideoCallActivity.this);
                builder.setMessage("OTP Verified Successfully.Please Wait...")
                        .setCancelable(false);


                AlertDialog alert = builder.create();
                alert.show();
                startActivity(new Intent(VideoCallActivity.this, SendOTPMSG91.class));
                finish();
            }

            webView.setWebViewClient(new VideoCallActivity.MyWebViewClient());
            webView.setWebChromeClient(new VideoCallActivity.ChromeClient());
            webView.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    //for downloading directly through download manager
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "download");
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                }
            });
            webView.setWebViewClient(new WebViewClient() {


                public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                    if(view!=null && view.isShown()) {
//                        dialog = ProgressDialog.show(HomeActivity.this, null,
//                                "Please Wait...");
//                    }
//                    dialog = ProgressDialog.show(HomeActivity.this, null,
//                            "Please Wait...");
                    super.onPageStarted(view, url, favicon);
                }

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    if(url.equals("https://www.grapevine.work/workplace/Index"))
                    {
                        Intent i=new Intent(VideoCallActivity.this,HomeActivity.class);
                        startActivity(i);
                    }
                    if (url.contains("StopMediaRingTone")) {
                        if(RingtonePlay.isplayingAudio){
                            RingtonePlay.stopAudio();

                        }
                        if(url.contains("ACS")){
                            OnOffSpeaker("earpiece");
                        }
                        return true;
                    }
                    else if (url.contains("SpeakerOff")) {

                        OnOffSpeaker("earpiece");

                        return true;
                    }
                    else if (url.contains("SpeakerOn")) {

                        OnOffSpeaker("speaker");

                        return true;
                    }
                    else if(url.contains("EndCall")){
                        final Context context = webView.getContext();
                        final AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        am.setMode(AudioManager.MODE_NORMAL);
                        if(RingtonePlay.isplayingAudio){
                            RingtonePlay.stopAudio();
                        }
                        return true;
                    }
                    else if(url.contains("CallingToUser")){
                        if(!RingtonePlay.isplayingAudio){

                            if(url.contains("CallingToUser~ACS")){
                                OnOffSpeaker("earpiece");
                            }
                            RingtonePlay.playTringRingtone(getApplicationContext());
                        }
                        return true;
                    }
                    else if(url.contains("InComingCall")){

//
                        if(!RingtonePlay.isplayingAudio && !appPreferences.getisAccepted()){
                            PlayRingtone();
                        }
//                        if(appPreferences.getUserPresenceState().equals("Live")){
//
//                        }

                        return true;
                    }
                    else if(url.contains("InComingMessageNotification")){
                        if(!appPreferences.getUserPresenceState().equals("NotLive"))
                        {
                            Uri alarmSound =Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.notify);
                            MediaPlayer mediaPlayer = MediaPlayer.create(VideoCallActivity.this, alarmSound);
                            mediaPlayer.start();
                        }

//                        if(appPreferences.getUserPresenceState().equals("Live")){
//
//                        }

                        return true;
                    }
                    else if(url.contains("Signal=!201.ACS")){


                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        boolean useHeadphone = am.isWiredHeadsetOn();
                        am.setMode(AudioManager.MODE_IN_COMMUNICATION);
                        am.setSpeakerphoneOn(!useHeadphone);
                        return true;
                    }
                    else if(url.contains("Signal=!101.VCS"))
                    {
                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        boolean useHeadphone = am.isWiredHeadsetOn();
                        am.setMode(AudioManager.ROUTE_SPEAKER);
                        am.setSpeakerphoneOn(!useHeadphone);
                        return true;
                    }
                    view.loadUrl(url);
                    return true;
                }
            });




        }

    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }


    }
    public class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;


        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }


            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
            // getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }




        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            Log.d(TAG, "onPermissionRequest");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(request.getOrigin().toString().equals("https://www.grapevine.work/")) {
                    request.grant(request.getResources());
                } else {
                    request.deny();
                }
            }
//            mActivity.runOnUiThread(new Runnable() {
//               // @TargetApi(Build.VERSION_CODES.L)
//                @Override
//                public void run() {
//                    if(request.getOrigin().toString().equals("https://www.grapevine.work/")) {
//                        request.grant(request.getResources());
//                    } else {
//                        request.deny();
//                    }
//                }
//            });
        }
        @Override
        public void onProgressChanged(WebView view, int p) {
            String Url=view.getUrl();
            asw_progress.setProgress(p);
            if (p == 100) {
                asw_progress.setProgress(0);

                if(Url.startsWith("https://www.grapevine.work/workplace/Index")) {
                    //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffcf01")));
//
                    asw_progress.setVisibility(View.GONE);
                    findViewById(R.id.msw_welcome).setVisibility(View.GONE);
                    findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
                }

            }
            else
            {

                if(Url.startsWith("https://www.grapevine.work/workplace/Index")) {
                    asw_progress.setVisibility(View.VISIBLE);
                    //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffcf01")));
                    //setTheme(R.style.SplashActionBar);
//                        getTheme().applyStyle(R.style.SplashActionBar, true);
//
//                        setTheme(R.style.SplashActionBar);
//                        getTheme().applyStyle(R.style.SplashActionBar, true);
                    findViewById(R.id.msw_welcome).setVisibility(View.VISIBLE);
                    findViewById(R.id.activity_main_webview).setVisibility(View.GONE);
                }
                else{
                    asw_progress.setVisibility(View.GONE);
                    // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffcf01")));
//                        setTheme(R.style.SplashActionBar);
//                        getTheme().applyStyle(R.style.SplashActionBar, true);
//                        setTheme(R.style.SplashActionBar);
//                        getWindow().setNavigationBarColor(R.style.SplashActionBar);
                    findViewById(R.id.msw_welcome).setVisibility(View.GONE);
                    findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
                }

            }

        }
//        @Override
//        public void onPermissionRequest(PermissionRequest request) {
//            //super.onPermissionRequest(request);
//            request.grant(request.getResources());
//        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

            Log.e("Error",consoleMessage.message());
            return true;
        }



        //Creating video file for upload
        private File create_video() throws IOException {
            @SuppressLint("SimpleDateFormat")
            String file_name    = new SimpleDateFormat("yyyy_mm_ss").format(new Date());
            String new_name     = "file_"+file_name+"_";
            File sd_directory   = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            return File.createTempFile(new_name, ".3gp", sd_directory);
        }
        private  boolean hasPermissions(Context context, String... permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
            return true;
        }

        // openFileChooser for Android 3.0+
//
        private String getTitleFromUrl(String url) {
            String title = url;
            try {
                URL urlObj = new URL(url);
                String host = urlObj.getHost();
                if (host != null && !host.isEmpty()) {
                    return urlObj.getProtocol() + "://" + host;
                }
                if (url.startsWith("file:")) {
                    String fileName = urlObj.getFile();
                    if (fileName != null && !fileName.isEmpty()) {
                        return fileName;
                    }
                }
            } catch (Exception e) {
                // ignore
            }

            return title;
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            String newTitle = getTitleFromUrl(url);

            new AlertDialog.Builder(VideoCallActivity.this).setTitle(newTitle).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setCancelable(false).create().show();
            return true;
            // return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

            String newTitle = getTitleFromUrl(url);

            new AlertDialog.Builder(VideoCallActivity.this).setTitle(newTitle).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }
            }).setCancelable(false).create().show();
            return true;

            // return super.onJsConfirm(view, url, message, result);
        }



        // Android 3.0

        // Android 4.1


    }
    public void PlayRingtone() {
        if (!CallAccepted) {
            RingtonePlay.playAudio(this);
        } else {
            CallAccepted = false;
        }
    }
    private final static Object methodInvoke(Object obj, String method, Class<?>[] parameterTypes, Object[] args) {
        try {
            Method m = obj.getClass().getMethod(method, new Class[] { boolean.class });
            m.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }
    public String GetFeedChannelID(String ContactID) {
        String FeedChannelID="";
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

    public  void OnOffSpeaker(String Mode){
        final Context context = webView.getContext();
        final AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //boolean useHeadphone = am.isWiredHeadsetOn();
        if(Mode.equals("earpiece")){
            am.setMode(AudioManager.MODE_IN_COMMUNICATION);
            //am.stopBluetoothSco();
            //am.setBluetoothScoOn(false);
            am.setSpeakerphoneOn(false);
        }
        else if (Mode.equals("speaker")) {
            am.setMode(AudioManager.MODE_IN_COMMUNICATION);
            //am.stopBluetoothSco();
            //am.setBluetoothScoOn(false);
            am.setSpeakerphoneOn(true);

        }
    }
}
