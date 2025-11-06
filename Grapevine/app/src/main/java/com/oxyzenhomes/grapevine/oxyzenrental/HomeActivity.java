package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.pm.Signature;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.database.Cursor;
//import com.google.api.services.drive.Drive;
//import com.dropbox.chooser.android.DbxChooser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import androidx.exifinterface.media.ExifInterface;
import android.media.MediaCodecInfo;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.compatibility.WebAddress;
import android.net.http.SslError;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;

import com.google.firebase.BuildConfig;
//import com.arthenica.ffmpegkit.FFmpegKit;
//import com.arthenica.ffmpegkit.FFmpegSession;
//import com.arthenica.ffmpegkit.ReturnCode;
import com.dropbox.core.v2.clouddocs.Content;
//import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
//import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
//import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
//import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
//import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveClient;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.OpenFileActivityOptions;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;


import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Range;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.MimeTypeMap;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
//import androidx.core.util.Consumer;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.drive.DriveScopes;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.microsoft.onedrivesdk.picker.IPicker;
import com.microsoft.onedrivesdk.picker.IPickerResult;
import com.microsoft.onedrivesdk.picker.LinkType;
import com.microsoft.onedrivesdk.picker.Picker;
//import com.oxyzenhomes.grapevine.BuildConfig;
import com.oxyzenhomes.grapevine.ContactPicker.Contact;
import com.oxyzenhomes.grapevine.ContactPicker.ContactsPickerActivity;
//import com.oxyzenhomes.grapevine.CreateContact.AndroidCustomFieldScribe;
import com.oxyzenhomes.grapevine.CreateContact.ContactOperations;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.Recorder.AudioEncodeConfig;
import com.oxyzenhomes.grapevine.Recorder.Notifications;
import com.oxyzenhomes.grapevine.Recorder.ScreenRecorder;
import com.oxyzenhomes.grapevine.Recorder.Utils;
import com.oxyzenhomes.grapevine.Recorder.VideoEncodeConfig;
import com.oxyzenhomes.grapevine.Recorder.view.NamedSpinner;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.appprefrence.VersionChecker;
import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.fxn.adapters.MyAdapterJava;
import com.oxyzenhomes.grapevine.fxn.pix.Options;
import com.oxyzenhomes.grapevine.fxn.pix.Pix;
import com.oxyzenhomes.grapevine.request.RequestBean;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.firebase.iid.FirebaseInstanceIdReceiver;
//import com.google.firebase.iid.FirebaseInstanceId;
//iid.FirebaseInstanceId;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.vincent.videocompressor.VideoCompress;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;
//import com.werb.pickphotoview.PickPhotoView;
//import com.werb.pickphotoview.util.PickConfig;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datanapps.mediapicker.ui.DNAGalleryPickerActivity;
import datanapps.mediapicker.utils.AppConstants;
import ezvcard.Ezvcard;
import ezvcard.VCard;
//import ezvcard.android.AndroidCustomFieldScribe;
import ezvcard.android.AndroidCustomFieldScribe;
import ezvcard.io.text.VCardReader;
import kotlin.jvm.internal.Intrinsics;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static com.google.android.gms.drive.Drive.getDriveResourceClient;
import static com.oxyzenhomes.grapevine.Recorder.ScreenRecorder.AUDIO_AAC;
import static com.oxyzenhomes.grapevine.Recorder.ScreenRecorder.VIDEO_AVC;
import static com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE;
import static org.apache.commons.net.io.Util.closeQuietly;
import com.oxyzenhomes.grapevine.utils.ProgressCalculator;
import com.oxyzenhomes.grapevine.utils.Util;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    //implements View.OnTouchListener,
    //Handler.Callback{
    public boolean IsFullScreen = false;
    private boolean GOOGLE_DRIVE_LOGGED_IN=false;
    private boolean IS_ENABLE_PIP_MODE=false;
    public static final String ACTION_STOP = BuildConfig.APPLICATION_ID + ".action.STOP";
    private static final int REQUEST_MEDIA_PROJECTION = 1;
    private static final int REQUEST_PERMISSIONS = 2;
    Integer PICK_PHOTO_DATA = 0x5521;

    String INTENT_IMG_LIST_SELECT = "intent_img_list_select";
    // members below will be initialized in onCreate()
    private MediaProjectionManager mMediaProjectionManager;
    private final int requestCodePicker = 500;
    final int CONTACT_PICK_REQUEST = 1000;
    final int CONTACT_PICK_REQUEST_INVITATION = 1001;
    final int CONTACT_PICK_REQUEST_ADD_PHONE = 1002;
    final int CONTACT_ADD_TO_EXISTING = 1003;
    final int CONTACT_PICK_REQUEST_ADD_PHONE_MESSAGE = 1004;
    private MyAdapterJava myAdapter;
    private Options options;
    private HashMap _$_findViewCache;
    private ArrayList returnValue = new ArrayList();
    private Button mButton;
    boolean doubleBackToExitPressedOnce = false;
    private ToggleButton mAudioToggle;
    private NamedSpinner mVieoResolution;
    private NamedSpinner mVideoFramerate;
    private NamedSpinner mIFrameInterval;
    private NamedSpinner mVideoBitrate;
    private NamedSpinner mAudioBitrate;
    private NamedSpinner mAudioSampleRate;
    private NamedSpinner mAudioChannelCount;
    private NamedSpinner mVideoCodec;
    private NamedSpinner mAudioCodec;
    private NamedSpinner mVideoProfileLevel;
    private NamedSpinner mAudioProfile;
    private NamedSpinner mOrientation;
    private MediaCodecInfo[] mAvcCodecInfos; // avc codecs
    private MediaCodecInfo[] mAacCodecInfos; // aac codecs
    private Notifications mNotifications;
    private DbxChooser mChooser;
    static final int DBX_CHOOSER_REQUEST = 421;
    private IPicker mPicker;
    private String ONEDRIVE_APP_ID = "e2480e40-920f-4b28-8792-b5942b22c1bd";
    private ArrayList<ContactResult> ContactsResults = new ArrayList<>();
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.PROCESS_OUTGOING_CALLS};
    /**
     * <b>NOTE:</b>
     * {@code ScreenRecorder} should run in background Service
     * instead of a foreground Activity in this demonstrate.
     */
    private ScreenRecorder mRecorder;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;


    boolean CallAccepted = false;
    int mediaplayer = 0;
    ArrayList<ContactList> _ContactList = new ArrayList<ContactList>();
    private static final String DESKTOP_USER_AGENT = "Mozilla/5.0 (Android WebView; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36";
    LocationManager locationManager;
    private Activity mActivity;
    private String asw_cam_message;
    private static final String FILE_PROVIDER_AUTHORITY = "com.otaliastudios.transcoder.demo.fileprovider";
    private static final String GRAPEVINE_FILE_PROVIDER_AUTHORITY = "com.oxyzenhomes.grapevine.fileprovider";
    private FrameLayout mTargetView;
    private FrameLayout mContentView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private View mCustomView;
    private String mAccessToken;
    public static final String CLIENT_ID = "599518b71a844f6392123d175f0b4d3a";
    public static final String REDIRECT_URI = "https://www.grapevine.work/workplace/Music";
    public static final int AUTH_TOKEN_REQUEST_CODE = 0x10;
    int FileCounter=0;
    Uri[] FileResults = null;
    static final Integer LOCATION = 0x1;
    static final Integer CALL = 0x2;
    static final Integer WRITE_EXST = 0x3;
    static final Integer READ_EXST = 0x4;
    static final Integer CAMERA = 0x5;
    static final Integer ACCOUNTS = 0x6;
    static final Integer GPS_SETTINGS = 0x7;
    private static final int MY_PERMISSION_REQUEST_CODE = 123;
    private WebView webView;
    ConnectionDetector cd;
    ProgressDialog dialog;
    private AppPreferences appPreferences;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final String TAG = HomeActivity.class.getSimpleName();
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private GoogleSignInClient googleSignInClient;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 100;
    private static int SIGN = 0;
    String CheckUser = "";
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    //    private final Handler handler = new Handler(this);
    String stuId = "";
    String currentUrl;
    String SourceChannel = "";
    String ChannelId = "";
    String Name = "";
    private String Latitude = "";
    private String Longitude = "";
    private Location location = null;
    String ChatUrl;
    Intent intent;
    Bundle bundle;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    String FbUserEmail = "";
    String FbUserName = "";
    String FbAppId = "";

    String GoogleUserEmail = "";
    String GoogleUserName = "";
    String GoogleAppId = "";
    String SessionCount = "";
    String count = "";
    String UserOtp = "";
    String WebLink="";
    private RequestBean requestBean;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<String> permissionsToRequest;
    private static final int ALL_PERMISSIONS_RESULT = 1011;
    private GoogleApiClient googleApiClient;
    private DriveServiceHelper mDriveServiceHelper;
    private LocationRequest locationRequest;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    int REQ_CODE_OPEN = 420;
    /**
     * The used for interval for location updates.
     */
    private static final long UPDATE_INTERVAL = 1 * 1000; // 1 km  interval

    /**
     * The fastest interval to update for active location updates.
     */
    private static final long FASTEST_INTERVAL = UPDATE_INTERVAL / 2;

    /**
     * The max time to wait for location update.
     */
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 3;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    int SELECT_VIDEO_REQUEST = 100;
    int SELECT_PHOTO_REQUEST = 90;
    int CAMERA_REQUEST = 1;
    int REQUEST_CODE = 2;
    String selectedVideoPath;
    private static final String VCF_DIRECTORY = "/vcf_demonuts";
    private File vcfFile;
    public static final int REQUEST_CODE_PICK_CONTACT = 1;
    public static final int MAX_PICK_CONTACT = 10;
    ProgressBar asw_progress;

    private MediaProjectionManager mediaProjectionManager;
    private static MediaProjection mediaProjection = null;
    private boolean mResolvingError = false;
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private static final String DIALOG_ERROR = "dialog_error";
    private static final int PERMISSION_REQUEST = 100;
    private TelephonyManager mTelephonyManager;
    private PowerManager powerManager;
    private PowerManager.WakeLock wakeLock;
    private int field = 0x00000020;
    //private FFmpeg ffmpeg;

    public GetCurrentLocation gpsTrack;
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), "errordialog");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (connectionResult.hasResolution()) {
//            try {
//                mResolvingError = true;
//                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
//            } catch (IntentSender.SendIntentException e) {
//                // There was an error with the resolution intent. Try again.
//                googleApiClient.connect();
//            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(connectionResult.getErrorCode());
            mResolvingError = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    class ContactList {
        public String ContactName = "";
        public String ContactNo = "";
        public String ContactEmail = "";

    }

    MediaRecorder mediaRecorder;
    private static String audioFilePath;
    private int startrecord = 0;
    private static final String INJECTION_TOKEN = "**injection**";

    private void startRecording() {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File outputFolder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MediaMaster/Dub/");
        Log.i(TAG, "startRecording: creating output file " + outputFolder.mkdirs());
        File output = new File(outputFolder.getAbsolutePath() + "out" + new Date().getTime() + ".mp3");
        mediaRecorder.setOutputFile(output.getAbsolutePath());

        mediaRecorder.setMaxDuration(3000);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "startRecording: ", e);
        }
        startrecord = 1;
        mediaRecorder.start();
    }

    MediaPlayer mMediaPlayer;

    public class Record {
        public String NAME;
        public String DESCRIPTION;
    }

    static final String APP_KEY = "97hr2f3uj6pk3eq";
    String AlertType;
    String SinglePageURL;
    RecyclerView rclView;
    Options rclOptions;

    private static final String TAG_DRIVE = "Google drive";
    private static final String SIGN_IN = "Sign In";
    private static final String DOWNLOAD_FILE = "Download file";

    private static final int REQUEST_CODE_SIGN_IN = 0;
    private static final int REQUEST_CODE_OPEN_ITEM = 1;
    private static final int REQUEST_WRITE_STORAGE = 112;


    private GoogleSignInAccount signInAccount;
    private Set<Scope> requiredScopes;
    private DriveClient mDriveClient;
    private DriveResourceClient mDriveResourceClient;

    private OpenFileActivityOptions openOptions;
    private TaskCompletionSource<DriveId> mOpenItemTaskSource;
    private File storageDir;
    // SwipeRefreshLayout mySwipeRefreshLayout;
    /* String  LoadURL=""; */
    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mActivity = HomeActivity.this;
        gpsTrack=new GetCurrentLocation(this);

//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.BLUE);
//        setTheme(R.style.Theme_AppCompat_Light_NoActionBar_FullScreen);
        try {
            // Yeah, this is hidden field.
            field = PowerManager.class.getClass().getField("PROXIMITY_SCREEN_OFF_WAKE_LOCK").getInt(null);
        } catch (Throwable ignored) {
        }

        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(field, getLocalClassName());

        requestBean = new RequestBean();
       Get_SHA_KEY_BASE64();
        //LoadFFmpegLibrary();
        requestBean.setActivity(this);
        appPreferences = new AppPreferences(this);
        Integer UpdateLaterCount=appPreferences.getUpdateLaterCount();
        Boolean IsUpdateLater=appPreferences.getIsUPdateLater();
        if(!IsUpdateLater)
        {

            appPreferences.setUpdateLaterCount(0);
            new VersionChecker(HomeActivity.this).execute();
        }
        else{
            UpdateLaterCount=UpdateLaterCount+1;
            appPreferences.setUpdateLaterCount(UpdateLaterCount);
            if(UpdateLaterCount==5){
                appPreferences.setIsUPdateLater(false);
            }
        }
        initialize();

//        requestPermission();
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null && signInAccount.getGrantedScopes().containsAll(requiredScopes)) {
            initializeDriveClient(signInAccount);

        }

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        webView = (WebView) findViewById(R.id.activity_main_webview);

        asw_progress = findViewById(R.id.msw_progress);
        FirebaseApp.initializeApp(getApplicationContext());

        //---New gallery Layout---///
//        rclView = (RecyclerView)findViewById(R.id.recyclerView);
//
//        rclView.setLayoutManager((RecyclerView.LayoutManager)(new StaggeredGridLayoutManager(2, 1)));
        this.myAdapter = new MyAdapterJava((Context)this);
        rclOptions = Options.init().setRequestCode(this.requestCodePicker).setCount(5).setFrontfacing(false).setPreSelectedUrls(this.returnValue).setMode(Options.Mode.All).setSpanCount(4).setVideoDurationLimitinSeconds(30).setScreenOrientation(1).setPath("pix/akshay");
//
        this.options = rclOptions;
//        rclView = (RecyclerView)findViewById(R.id.recyclerView);
//        Intrinsics.checkNotNullExpressionValue(rclView, "recyclerView");
//        MyAdapterJava var2 = this.myAdapter;
//        if (var2 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("myAdapter");
//        }
//
//        rclView.setAdapter((RecyclerView.Adapter)var2);
        //---New gallery Layout---///

        // checkPermissions();

//                Window window = getWindow();
//        WindowManager.LayoutParams winParams = window.getAttributes();
//        winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        window.setAttributes(winParams);
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        cd = new ConnectionDetector(getApplicationContext());
        intent = getIntent();
        bundle = intent.getExtras();
        String FeedChannelID = "";
        String ChannelFeedChannelID = "";
        String FeedID = "";
        String ServerType = "";
        mChooser = new DbxChooser(APP_KEY);
        if (bundle != null) {
            AlertType = bundle.getString("AlertType");
            //AlertType=intent.getStringExtra("AlertType");
            FeedID = bundle.getString("FeedID");
            ServerType = bundle.getString("ServerType");
            SinglePageURL=bundle.getString("SinglePageURL");

            //intent.getStringExtra("AlertType");
            FeedChannelID = bundle.getString("FeedChannelID");
            ChannelFeedChannelID = bundle.getString("ChannelFeedChannelID");
            WebLink=bundle.getString("WebLink");
        }
//        GetLoginInfo();
        if (AlertType != null) {
            //Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            if (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS") || (AlertType.contains("MEET") && !AlertType.contains("Started"))) {
                String[] callResponse = AlertType.split("-");
                if (!callResponse[1].equals("Accepted")) {
                    PlayRingtone();
                } else {
                    CallAccepted = true;
                    appPreferences.setIsAccepted(true);
                    if (RingtonePlay.isplayingAudio) {

                        RingtonePlay.stopAudio();
                    }
                }
            }
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(0);


        }
//        else if(AlertType!=null && !AlertType.contains("#101.VCS") && !AlertType.contains("#201.ACS") && !AlertType.contains("#500.MEET")){
//            Message=bundle.getString("Message");
//        }

        //final String[] token = {""};
        String AndroidID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //String token = FirebaseInstanceId.getInstance().getToken();
        FirebaseApp.initializeApp(getApplicationContext());
        String token= FirebaseInstanceId.getInstance().getToken();


        String FirebaseTokenID = appPreferences.getFirebaseTokenID();
        if (!appPreferences.getApplicant_id().equals("")) {
            appPreferences.setFirebaseTokenID(token);

            String MobileNo = appPreferences.getStuID().replace("+91", "");
            int isActive = CheckingAccountExistence(MobileNo, AndroidID);
            if (isActive == 0) {
                // Log.e("Update Token", token);
                UpdateToken(appPreferences.getApplicant_id());
            }
            else {
                DeleteCookies();


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
                appPreferences.setCurrentURL("");
                appPreferences.setApplicant_id("");
                appPreferences.setLoginUserMobileNo("");

//                        LoginManager.getInstance().logOut();
//
//                        signOut();
                startActivity(new Intent(HomeActivity.this, SendOTPMSG91.class));
                finish();
            }

        }




        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        String sharedText = appLinkIntent.getStringExtra(Intent.EXTRA_TEXT);
        String[] website;
        ArrayList links = new ArrayList();
        if (sharedText != null && !sharedText.equals("PostPage")) {


            String regex = "\\(?\\b(https://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(sharedText);
            while (m.find()) {
                String urlStr = m.group();
                if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                    urlStr = urlStr.substring(1, urlStr.length() - 1);
                }
                links.add(urlStr);
            }
            sharedText = links.get(0).toString();
        }
        String Query = "";
        String host = "";
        String path = "";
        String openUrl = "";
        if (appLinkData != null) {
            Query = appLinkData.getQuery();
            host = appLinkData.getHost();
            path = appLinkData.getPath();
            openUrl = "https://" + host + path + "?" + Query;
        }


        //will hide the title
        //getSupportActionBar().hide(); // hide the title bar
        //enable full screen

        WebSettings webSettings = webView.getSettings();
        //webView.loadUrl(getIntent().getDataString());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webView.setScrollbarFadingEnabled(false); // Explicitly, however it's a default, I think.
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setEnableSmoothTransition(true);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            webView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_IMPORTANT,false);
//        }
        //webView.setRendererPriorityPolicy(RENDERER_PRIORITY_BOUND, true);
        webView.clearHistory();
        webView.clearCache(true);

        webView.clearFormData();
        //webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setUserAgentString(DESKTOP_USER_AGENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webView.getSettings().setPluginsEnabled(true);
        methodInvoke(webSettings, "setPluginsEnabled", new Class[]{boolean.class}, new Object[]{true});
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        //webView.clearCache(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        if (Build.VERSION.SDK_INT >= 11){
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(0);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        SourceChannel = appPreferences.getschoolname().toString();

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (!appPreferences.getLocationPermissionDenied()) {
            if (Build.VERSION.SDK_INT >= M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(
                            new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST);
            }
        }
//        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                //.addApi(Drive.API)
                .addConnectionCallbacks(this).
                        addOnConnectionFailedListener(this).build();
        googleApiClient.connect();

        if (cd.isConnectingToInternet()) {
            stuId = appPreferences.getStuID();
            Latitude = appPreferences.getSchoolID();
            Longitude = appPreferences.getStatffID();
            UserOtp = appPreferences.getUserOTP();
            if (!appPreferences.getStuID().isEmpty()) {
                ChannelId = appPreferences.getRegNo();
                Name = appPreferences.getimagePath();

                FbUserEmail = appPreferences.getFbUserEmail();
                FbUserName = appPreferences.getFbUserName();
                FbAppId = appPreferences.getFbAppId();

                GoogleUserEmail = appPreferences.getGoogleUserEmail();
                GoogleUserName = appPreferences.getGoogleUserName();
                GoogleAppId = appPreferences.getGoogleAppId();
                SessionCount = appPreferences.getSessionCount();


                if (SessionCount.isEmpty()) {
                    SessionCount = "SendOtp";
                }
//
                else {
                    if (appPreferences.getShareLink().trim().isEmpty()) {
                        if (!openUrl.isEmpty() && openUrl != null) {
                            webView.loadUrl(openUrl);
                        }
//                        else if(AlertType!=null && (AlertType.contains("#101.VCS") || AlertType.contains("#201.ACS") || AlertType.contains("MEET")) ){
                        else if (AlertType != null) {
                            int NOTIFY_ID = bundle.getInt("NOTIFY_ID");
                            String ns = Context.NOTIFICATION_SERVICE;
                            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                            nMgr.cancel(NOTIFY_ID);
                            Toast. makeText (HomeActivity.this, AlertType , Toast. LENGTH_SHORT ).show() ;
                            AlertType = AlertType.replace("#", "!");
                            // AlertType = urlParam("AlertType");

                            if (AlertType.contains("!101.VCS")
                                    || AlertType.contains("!201.ACS")
                                    || AlertType.contains("LCA")
                                    || AlertType.contains("LCV")
                                    || AlertType.contains("MEET")
                                    || AlertType.contains("LAM")
                            ) {
                                String mediaserver="";
                                String MyRole="Publisher";
                                if(AlertType.contains("!101.VCS")
                                        || AlertType.contains("!201.ACS")){
                                    FeedID = AlertType;
                                    String[] FeedID1 = FeedID.split("\\.");
                                    mediaserver = FeedID1[3];
                                    FeedID = FeedID1[4];
                                }
                                else{
                                    mediaserver=ServerType;
                                    if(AlertType.contains("LCA")
                                            || AlertType.contains("LCV")){
                                        MyRole="Subscriber";
                                    }
                                }

                                if (FeedID.contains("Accepted")) {
                                    FeedID = FeedID.replace("-Accepted", "");
                                }
                                String StartDate = "";

                                String FormatAmPm = "";

                                String user_FeedChanneID = appPreferences.getUserFeedChannelID();
                                if (user_FeedChanneID == null || user_FeedChanneID.equals("")) {
                                    user_FeedChanneID = GetFeedChannelID(appPreferences.getApplicant_id());
                                    appPreferences.setUserFeedChannelID(user_FeedChanneID);
                                }
                                String CallFromFeedChannelID = FeedChannelID;
                                if (AlertType.contains("!201.ACS")) {

                                    OnOffSpeaker("earpiece");

                                }
                                else if(AlertType.contains("!101.VCS")){
                                    OnOffSpeaker("speaker");
                                }
                                if (RingtonePlay.isplayingAudio) {
                                    RingtonePlay.stopAudio();
                                }
                                String VideoCall_Page="https://www.grapevine.work/Grapevine/VideoCall.html?MyFeedChannelID=" + user_FeedChanneID + "&UserFeedChannelID=" + CallFromFeedChannelID + "&MyRole="+MyRole+"&Signal=" + AlertType + "&FeedID=" + FeedID + "&StartDate=" + StartDate + "&media_server=" + mediaserver+"&PageFrom=Android";
                                Log.e(TAG,VideoCall_Page);
                                webView.loadUrl(VideoCall_Page);
//                                if (mediaserver == "ms") {
//
//                                    webView.loadUrl("https://www.grapevine.work/workplace/VideoCallWithServer?MyFeedChannelID=" + user_FeedChanneID + "&UserFeedChannelID=" + CallFromFeedChannelID + "&MyRole=Publisher&Signal=" + AlertType + "&FeedID=" + FeedID + "&StartDate=" + StartDate + "&PageFrom=Android");
//                                } else {
//                                    webView.loadUrl("https://www.grapevine.work/workplace/VideoCall?MyFeedChannelID=" + user_FeedChanneID + "&UserFeedChannelID=" + CallFromFeedChannelID + "&MyRole=Publisher&Signal=" + AlertType + "&FeedID=" + FeedID + "&StartDate=" + StartDate + "&PageFrom=Android");
//                                }
                            }
                            else if (AlertType.equals("Dating") || AlertType.equals("Matched")) {
                                String SenderListingID = bundle.getString("SenderListingID");
                                String MyListingID = bundle.getString("MyListingID");
                                String ResponseID = bundle.getString("ResponseID");
                                String ResponseByFeedChannelID = bundle.getString("ResponseByFeedChannelID");
                                webView.loadUrl("https://www.grapevine.work/workplace/Dating?ListingID=" + SenderListingID + "&ResponseByFeedChannelID=" + ResponseByFeedChannelID + "&ResponseID=" + ResponseID + "&MyListingID=" + MyListingID + "&backpage=https://www.grapevine.work/Grapevine/Index.html&ViewFrom=Android&From=" + AlertType);
                            }
                            else if (AlertType.equals("Vehicles") || AlertType.equals("VechicleAccept")) {
                                String ListingID = bundle.getString("ListingID");
                                webView.loadUrl("https://www.grapevine.work/workplace/Vehicles?ListingID=" + ListingID + "&From=Share");
                            }
                            else if (AlertType.equals("Feed Reactions") || AlertType.equals("Post")) {
                                webView.loadUrl("https://www.grapevine.work/workplace/SingleFeed?AlertType=" + AlertType + "&FeedID=" + FeedID);
                            }
                            else if(AlertType.equals("SinglePageNotification")){
                                if(SinglePageURL!=null){
                                    SinglePageURL= SinglePageURL.replace("%","&");
                                    //SinglePageURL =crm_insert_feed_Short_url( "Get", SinglePageURL, "");
                                    webView.loadUrl(SinglePageURL);
                                }

                            }
                            else if(AlertType.equals("Groups")){
                                String MessageBody=bundle.getString("MessageBody");
                                String GroupFeedChannelID=MessageBody.split("!")[1];
                                webView.loadUrl("https://www.grapevine.work/workplace/Feeds_UserGroup?FeedChannelID="+GroupFeedChannelID+"&ObjectTypeID=0&ParticipantTypeID=7&FeedTypeID=0&From=Notification");
                            }
                            else if(AlertType.equals("Workteams")){
                                String MessageBody=bundle.getString("MessageBody");
                                String GroupFeedChannelID=MessageBody.split("!")[1];
                                webView.loadUrl("https://www.grapevine.work/workplace/Feeds_Groups?FeedChannelID="+GroupFeedChannelID+"&ObjectTypeID=0&ParticipantTypeID=7&FeedTypeID=0&From=Notification");
                            }
                            else if(AlertType.equals("Pages")){
                                String MessageBody=bundle.getString("MessageBody");
                                String GroupFeedChannelID=MessageBody.split("!")[1];
                                webView.loadUrl("https://www.grapevine.work/workplace/SinglePageUser?FeedChannelID="+GroupFeedChannelID+"&UrlType=SinglePageUser&From=Notification");
                            }
                            else if(AlertType.equals("Birthday")){
                                String MessageBody=bundle.getString("MessageBody");
                                String GroupFeedChannelID=MessageBody.split("!")[1];
                                String Title=MessageBody.split("!")[2];
                                String Applicant_ID=appPreferences.getApplicant_id();
                                webView.loadUrl("https://www.grapevine.work/workplace/Post_Feed?FeedChannelID=" + GroupFeedChannelID + "&friendname=" + Title + "&Page=birthday&ContactID=" + Applicant_ID);
                            }
                            else if(AlertType.equals("Oxy_Homes")){
                                String ResponseByFeedChannelID=bundle.getString("ResponseByFeedChannelID");
                                String ResponseID= bundle.getString("ResponseID");
                                String ListingID= bundle.getString("ListingID");
                                String NotiType=bundle.getString("NotiType");
                                webView.loadUrl("https://www.grapevine.work/workplace/realstate?MyListingID="+ListingID+"&AlertType="+NotiType+"&ResponseByFeedChannelID="+ResponseByFeedChannelID+"&ResponseID="+ResponseID);

                            }
                            else {

                                    webView.loadUrl("https://www.grapevine.work/Grapevine/Index.html?AlertType=" + AlertType + "&CallFromFeedChannelID=" + FeedChannelID + "&ChannelFeedChannelID=" + ChannelFeedChannelID + "&FeedID=" + FeedID + "&ServerType=" + ServerType);


                            }


                        }
//                        else if(AlertType!=null && !AlertType.contains("#101.VCS") && !AlertType.contains("#201.ACS") && !AlertType.contains("MEET") ){
//
//
//                            webView.loadUrl("https://www.grapevine.work/workplace/Index?AlertType="+AlertType+"&Message="+Message+"&CallFromFeedChannelID="+FeedChannelID);
//                        }
                        else {
                            if(WebLink!=null && !WebLink.equals("")){
                                WebLink =crm_insert_feed_Short_url( "Get", WebLink, "");
                                webView.loadUrl(WebLink);
                            }
                            else{
//                                if (savedInstanceState != null) {
//                                    webView.restoreState(savedInstanceState);
//
//                                }
//                                else{
                                    webView.loadUrl("https://www.grapevine.work/workplace/userlogin?email_id=" + stuId.replace("+91", "") + "&OTP=" + UserOtp + "&type=" + SourceChannel + "&UserName=" + Name + "&ChannelId=" + ChannelId + "&Longitude=" + Longitude + "&Latitude=" + Latitude + "&DeviceID=" + token + "&AndroidID=" + AndroidID + "&DeviceOS=Android");
                                //}

                            }

                            //webView.loadUrl("https://www.grapevine.work/workplace/Index?AndroidID="+AndroidID);
                        }


                    } else {
                        String ApplicantID = appPreferences.getApplicant_id();
                        String Link = appPreferences.getShareLink();
                        appPreferences.setShareLink("");
                        if (sharedText != null && sharedText.equals("PostPage")) {
                            FeedChannelID = appPreferences.getUserFeedChannelID();
                            String ContactID = appPreferences.getApplicant_id();
                            //webView.loadUrl("https://www.grapevine.work/workplace/Post_Feed?FeedChannelID=" + FeedChannelID + "&Page=Index&ContactID=" + ContactID + "&backpage=https://www.grapevine.work/workplace/index");;
                            webView.loadUrl("https://www.grapevine.work/workplace/SharePost?FeedChannelID=" + FeedChannelID + "&Page=Profile&type=ShareImage&Link=&ContactID=" + ApplicantID + "&backpage=https://www.grapevine.work/Grapevine/Index.html");
                        } else {
                            webView.loadUrl("https://www.grapevine.work/workplace/SharePost?FeedChannelID=0&Page=Profile&type=ShareLink&Link=" + Link.replace("&", "!") + "&ContactID=" + ApplicantID + "&backpage=https://www.grapevine.work/Grapevine/Index.html");
                        }

                    }


                }
                // webView.loadUrl("http://103.241.146.105:8082/Hindi/Index?stu_id="+stuId+"~Hindi");

            }
            else {
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
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("OTP Verified Successfully.Please Wait...")
                        .setCancelable(false);


                AlertDialog alert = builder.create();
                alert.show();
                startActivity(new Intent(HomeActivity.this, SendOTPMSG91.class));
                finish();
            }

            webView.setWebViewClient(new MyWebViewClient());
            webView.setWebChromeClient(new ChromeClient());
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

//            webView.addJavascriptInterface( new Object() {
//                @JavascriptInterface // For API 17+
//                public void performClick (String strl) {
//                    Toast. makeText (HomeActivity.this, strl , Toast. LENGTH_SHORT ).show() ;
//                }
//                public void performClick1 (String strl) {
//                    Toast. makeText (HomeActivity.this, strl , Toast. LENGTH_SHORT ).show() ;
//                }
//            } , "ok" ) ;
Boolean Write_Contacts=check_permission(10);
Boolean Read_Contacts=check_permission(6);
            if (check_permission(6) && check_permission(10))
            {
                CreateContactFolder("","","","","");
            }

                //get_file();

//            else{
//                String[] perms = {Manifest.permission.READ_CONTACTS,
//                        Manifest.permission.WRITE_CONTACTS};
//                ActivityCompat.requestPermissions(HomeActivity.this, perms, MY_PERMISSION_REQUEST_CODE);
//            }

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

//                    view.addJavascriptInterface(new Object()
//                    {
//                        @JavascriptInterface
//                        public void performClick() throws Exception
//                        {
//                            Log.d("LOGIN::", "ClickSed");
//                            Toast.makeText(HomeActivity.this, "Login clicked", Toast.LENGTH_LONG).show();
//                        }
//                    }, "login");
//----Link Start--///
                    if(url.contains("formatdate")){
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy_HH_mm_ss_SSS", Locale.getDefault());
                        String formattedDate = df.format(c);
                        //android.icu.text.SimpleDateFormat formatter = new android.icu.text.SimpleDateFormat("ddMMyyyy_HH_mm_ss_ffffff");
                        Log.e(TAG, formattedDate);
                        return true;
                    }
                    if(url.startsWith("truecallersdk://truesdk/web_verify")){
                        //return true;
                    }
                    if (url.equals("https://www.grapevine.work/Grapevine/Index")) {
                        String AndroidID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        String MobileNo = appPreferences.getStuID().replace("+91", "");
                        int isActive = CheckingAccountExistence(MobileNo, AndroidID);

                        if (isActive == 1) {
                            DeleteCookies();
                            //String token = FirebaseInstanceId.getInstance().getToken();

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
                            appPreferences.setCurrentURL("");
                            appPreferences.setApplicant_id("");
                            appPreferences.setLoginUserMobileNo("");

//                        LoginManager.getInstance().logOut();
//
//                        signOut();
                            startActivity(new Intent(HomeActivity.this, SendOTPMSG91.class));
                            finish();
                        }
                    }
                    if(url.contains("createcontact")){
                        if (check_permission(6) && check_permission(10))
                        {
                            CreateContactFolder("","","","","");
                        }
                        else{
                            String[] perms = {Manifest.permission.READ_CONTACTS,
                                    Manifest.permission.WRITE_CONTACTS};
                            ActivityCompat.requestPermissions(HomeActivity.this, perms, MY_PERMISSION_REQUEST_CODE);
                        }
                        return true;
                    }
                        if(url.contains("UpdateContactInPreference")){
                            String[] values;
                            String ContactID="";


                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                values = bkpUrl.split("&");
                                for (int i = 0; i < values.length; i++) {
                                    if (values[i].contains("ContactID")) {
                                        ContactID = values[i].split("=")[1];
                                        appPreferences.setApplicant_id(ContactID);
                                        JSONObject obj=GetloginDetailsOverContactID(ContactID);
                                        try {
                                            if(obj!=null){
                                                String LoginUserMobileNo=  obj.getString("mobile");
                                                appPreferences.setLoginUserMobileNo(LoginUserMobileNo);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        UpdateToken(ContactID);
                                    }

                                }

                            }
                            return true;
                        }
                    if (url.contains("https://wa.me")) {
                        if(check_permission(2) && check_permission(3) && check_permission(4) && check_permission(5) && check_permission(12)) {
                        String WhatsAppPackageName = "";
                        Boolean isInstalled = isPackageInstalled("com.whatsapp.w4b", HomeActivity.this);
                        if (!isInstalled) {
                            isInstalled = isPackageInstalled("com.whatsapp", HomeActivity.this);
                            if (isInstalled) {
                                WhatsAppPackageName = "com.whatsapp";
                            }
                        } else {
                            WhatsAppPackageName = "com.whatsapp.w4b";
                        }

                        String[] value = url.split("/");
                        String Mobile = value[3];
                        String Message="Hi";
                        if(url.contains("?")){
                            //String val=url.split("=")[1];
                            String[] MessageArr=url.split("=");
                            if(MessageArr.length>1){
                                Message=MessageArr[1].replace("!","=");
                            }
                        }
                        Mobile = Mobile.replace("+", "").replace("-", "");
                        String FeedChannelID = value[4];
                        JSONObject object = Get_User_DetailOverFeedChannelID(FeedChannelID);
                        CreateUserContact(object);



                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, Message);
                        sendIntent.putExtra("jid", Mobile + "@s.whatsapp.net"); //phone number without "+" prefix
                        sendIntent.setPackage(WhatsAppPackageName);

                        if (isPackageInstalled(WhatsAppPackageName, HomeActivity.this)) {
                            startActivity(sendIntent);
                        }
                    }
                        else{
                            get_file();
                            return false;

                        }
                        return true;
                    }
                    if (url.contains("CloseApp")) {
                        if (!doubleBackToExitPressedOnce) {
                            doubleBackToExitPressedOnce = true;
                            Toast.makeText(getApplicationContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    doubleBackToExitPressedOnce = false;
                                }
                            }, 2000);
                        }

                        return true;
                    }
                    if (url.contains("GooglePicker")) {

                        Intent intent = new Intent(getBaseContext(), RetrieveContentsActivity.class);
                        startActivity(intent);

                        return true;
                    }

                    if (url.contains("OpenCallIntent")) {

                        Intent intent = new Intent(getBaseContext(), IncomingCallActivity.class);
                        startActivity(intent);

                        return true;
                    }
                    else if(url.contains("GetAllPermissions")){
                        get_file();
                        return true;
                    }
                    else if (url.contains("CheckPermission")) {
                        if (!check_permission(3) || !check_permission(5)) {
                            get_file();
                        }
                        return true;
                    }
                    else  if (url.contains("Go_PubnubActivity")) {
                        Intent i=new Intent(HomeActivity.this, Pubnub.class);
                        startActivity(i);
                        return true;
                    }
                    else  if (url.contains("RTMP_Live")) {
                        String[] values;
                        String ObjectID="0";
                        String MyFeedChannelID="";
                        String UserFeedChannelID="";
                        String MyRole="";
                        String Signal="";
                        String FeedID="";
                        String StartDate="";
                        String LiveSessionDescription="";

                        url = Uri.decode(url);
                        String bkpUrl = null;
                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                        Matcher regexMatcherBkp = regexBkp.matcher(url);
                        if (regexMatcherBkp.find()) {
                            bkpUrl = regexMatcherBkp.group(1);
                            values = bkpUrl.split("&");
                            for (int i = 0; i < values.length; i++) {
                                if (values[i].contains("ObjectID")) {
                                    ObjectID = values[i].split("=")[1];
                                }
                                else if (values[i].contains("MyFeedChannelID")) {
                                    MyFeedChannelID = values[i].split("=")[1];
                                }
                                else if (values[i].contains("UserFeedChannelID")) {
                                    UserFeedChannelID = values[i].split("=")[1];
                                }
                                else if (values[i].contains("MyRole")) {
                                    MyRole = values[i].split("=")[1];
                                }
                                else if (values[i].contains("Signal")) {
                                    Signal = values[i].split("=")[1];
                                }
                                else if (values[i].contains("FeedID")) {
                                    FeedID = values[i].split("=")[1];
                                }
                                else if (values[i].contains("StartDate")) {
                                    StartDate = values[i].split("=")[1];
                                }
                                else if (values[i].contains("LiveSessionDescription")) {
                                    LiveSessionDescription = values[i].split("=")[1];
                                }
                            }
//                            if(!FeedID.equals("0"))
//                            {
//                                Intent i=new Intent(HomeActivity.this, RtmpActivity.class);
//                                i.putExtra("ObjectID",ObjectID);
//                                i.putExtra("MyFeedChannelID",MyFeedChannelID);
//                                i.putExtra("UserFeedChannelID",UserFeedChannelID);
//                                i.putExtra("MyRole",MyRole);
//                                i.putExtra("Signal",Signal);
//                                i.putExtra("FeedID",FeedID);
//                                i.putExtra("StartDate",StartDate);
//                                i.putExtra("LiveSessionDescription",LiveSessionDescription);
//                                startActivity(i);
//                            }

                        }
                        return true;
                    }
                    else if (url.contains("SetFullScreen")) {
                        if (url.contains("true")) {
                            IsFullScreen = true;
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                        } else {
                            IsFullScreen = false;
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        }
                        return true;
                    }
                    else if(url.contains("LoadHomePage")){
                        webView.loadUrl("https://www.grapevine.work/Grapevine/Index.html");
                        return true;
                    }
                    else if(url.contains("OpenFullScreenMode")){
                        if (url.contains("true")) {
                            //for notification bar
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            //for bottom button
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


                        }
                        else{
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                        }
                        return true;
                    }
                    else if (url.contains("WhatsapppMedia")) {
                        String FeedChannelID = appPreferences.getUserFeedChannelID();
                        if (FeedChannelID == null || FeedChannelID.equals("")) {
                            FeedChannelID = GetFeedChannelID(appPreferences.getApplicant_id());
                            appPreferences.setUserFeedChannelID(FeedChannelID);
                        }
                        ArrayList<String> result = new ArrayList<String>(); //ArrayList cause you don't know how many files there is
                        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/WhatsApp Stickers");
                        //File folder = new File("PATH/TO/YOUR/FOLDER/AS/STRING"); //This is just to cast to a File type since you pass it as a String
                        File[] filesInFolder = folder.listFiles(); // This returns all the folders and files in your path
                        for (File file : filesInFolder) { //For each of the entries do:

                            if (!file.isDirectory()) { //check that it's not a dir
                                UploadImagesToFtp(file, FeedChannelID);
                                result.add(new String(file.getName())); //push the filename as a string
                            }
                        }

                    }
                    else if (url.contains("GetCurrentLocation")) {
                        String[] values;
                        String FeedID="0";
                        String ApplicantID="";

                        url = Uri.decode(url);
                        String bkpUrl = null;
                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                        Matcher regexMatcherBkp = regexBkp.matcher(url);
                        if (regexMatcherBkp.find()) {
                            bkpUrl = regexMatcherBkp.group(1);
                            values = bkpUrl.split("&");
                            for (int i = 0; i < values.length; i++) {
                                if (values[i].contains("FeedID")) {
                                    FeedID = values[i].split("=")[1];

                                }
                                else  if (values[i].contains("ContactID")) {
                                    ApplicantID = values[i].split("=")[1];
                                 appPreferences.setApplicant_id(ApplicantID);
                                }
                            }

                        }
                        getCurrentAddress(FeedID);
                        return true;
                    }
                    else if(url.contains("CallToLeadUser")){
                        String[] values;
                        String MobileNumber="";
                        String FeedChannelID="";
                        String LeadThreadID="";
                        String Associate_ID="";
                        String ProjectName="";
                        url = Uri.decode(url);
                        String bkpUrl = null;
                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                        Matcher regexMatcherBkp = regexBkp.matcher(url);
                        if (regexMatcherBkp.find()) {
                            bkpUrl = regexMatcherBkp.group(1);
                            values = bkpUrl.split("&");
                            for (int i = 0; i < values.length; i++) {
                                if (values[i].contains("Mobile")) {
                                    String[] val = values[i].split("=");
                                    if(val.length>1){
                                        MobileNumber = val[1];
                                    }

                                }
                                else if(values[i].contains("FeedChannelID")){

                                    String[] val = values[i].split("=");
                                    if(val.length>1){
                                        FeedChannelID = val[1];
                                    }
                                }
                                else if(values[i].contains("LeadThreadID")){
                                    String[] val = values[i].split("=");
                                    if(val.length>1){
                                        LeadThreadID = val[1];
                                    }
                                }
                                else if(values[i].contains("Associate_ID")){
                                    String[] val = values[i].split("=");
                                    if(val.length>1){
                                        Associate_ID = val[1];
                                    }

                                }
                                else if(values[i].contains("ProjectName")){
                                    String[] val = values[i].split("=");
                                    if(val.length>1){
                                        ProjectName = val[1];
                                    }
                                }
                            }

                        }
                        if (check_permission(6) && check_permission(10)) {
                            CreateContactFolder(FeedChannelID, LeadThreadID, Associate_ID, ProjectName,"");

                            String uri = "tel:" + MobileNumber;
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
                            intent.setData(Uri.parse(uri));
                            startActivity(intent);
                        }
                        else{
                            get_file();
                        }
                        //view.reload();
                        return true;
                    }
//if(url.contains("com.dropbox.android")){
//                        URL url1 = null;
//                        try {
//                            url1 = new URL(url);
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        }
//                        String bkpUrl = null;
//                        String[] values;
//                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
//                        Matcher regexMatcherBkp = regexBkp.matcher(url);
//                        if (regexMatcherBkp.find()) {
//                            bkpUrl = regexMatcherBkp.group(1);
//                        }
//                        values = bkpUrl.split("&");
//                        File f = new File(values[1]);
//
//                        MimeTypeMap myMime = MimeTypeMap.getSingleton();
//                        Intent newIntent = new Intent(Intent.ACTION_VIEW);
//                        String mimeType = myMime.getMimeTypeFromExtension(fileExt(f.toString()));
//                        Uri uri = Uri.fromFile(f);
//
//                        newIntent.setDataAndType(uri, mimeType);
//                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        try {
//                            startActivity(newIntent);
//                        } catch (ActivityNotFoundException e) {
//                            Toast.makeText(getApplicationContext(), "No handler for this type of file.", Toast.LENGTH_LONG).show();
//                        }
//
//                        return true;
//                    }
//                   else
                    if(url.contains("GDRIVE")){

                        if(!GOOGLE_DRIVE_LOGGED_IN){
                            requestSignIn();
                            //signIn();
                        }
                        else{
                            onDriveClientReady();
                          // openFilePicker();
                        }

                        return true;
                    }
                    if(url.contains("ONEDRIVE"))
                    {
                        String[] values;
                        String[] FeedChannelID;
                        String[] ActivityID;
                        url = Uri.decode(url);
                        String bkpUrl = null;
                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                        Matcher regexMatcherBkp = regexBkp.matcher(url);
                        if (regexMatcherBkp.find()) {
                            bkpUrl = regexMatcherBkp.group(1);
                            values = bkpUrl.split("&");
                            for (int i = 0; i < values.length; i++) {
                                if (values[i].contains("ActivityID")) {
                                    ActivityID = values[i].split("=");
                                    appPreferences.setActivityID(ActivityID[1]);
                                } else if (values[i].contains("FeedChannelID")) {
                                    FeedChannelID = values[i].split("=");
                                    appPreferences.setFriendFeedChannelID(FeedChannelID[1]);
                                }
                            }

                        }
                        mPicker = Picker.createPicker(ONEDRIVE_APP_ID);
                        mPicker.startPicking(mActivity, LinkType.DownloadLink);
                                //.WebViewLink);
                        return true;
                    }
                    if(url.contains("EnablePIPMode")){
                        if(!IS_ENABLE_PIP_MODE){
                            IS_ENABLE_PIP_MODE=true;
                            mActivity.enterPictureInPictureMode();
                        }

                        return true;
                    }
                    if (url.contains("DropBoxFile")) {

                        String[] values;
                        String[] FeedChannelID;
                        String[] ActivityID;
                        url = Uri.decode(url);
                        String bkpUrl = null;
                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                        Matcher regexMatcherBkp = regexBkp.matcher(url);
                        if (regexMatcherBkp.find()) {
                            bkpUrl = regexMatcherBkp.group(1);
                            values = bkpUrl.split("&");
                            for (int i = 0; i < values.length; i++) {
                                if (values[i].contains("ActivityID")) {
                                    ActivityID = values[i].split("=");
                                    appPreferences.setActivityID(ActivityID[1]);
                                } else if (values[i].contains("FeedChannelID")) {
                                    FeedChannelID = values[i].split("=");
                                    appPreferences.setFriendFeedChannelID(FeedChannelID[1]);
                                }
                            }

                        }


                        DbxChooser.ResultType resultType = DbxChooser.ResultType.DIRECT_LINK;


                        mChooser.forResultType(resultType)
                                .launch(HomeActivity.this, DBX_CHOOSER_REQUEST);

                        return true;
                    }
                    if (url.contains("StartRecording")) {
                        final Dialog dialog = new Dialog(HomeActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.activity_main);

                        dialog.show();
                        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                        layoutParams.copyFrom(dialog.getWindow().getAttributes());
                        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
                        dialog.getWindow().setAttributes(layoutParams);
                        mMediaProjectionManager = (MediaProjectionManager) getApplicationContext().getSystemService(MEDIA_PROJECTION_SERVICE);
                        mNotifications = new Notifications(getApplicationContext());
                        bindViews(dialog);

                        Utils.findEncodersByTypeAsync(VIDEO_AVC, infos -> {
                            logCodecInfos(infos, VIDEO_AVC);
                            mAvcCodecInfos = infos;
                            SpinnerAdapter codecsAdapter = createCodecsAdapter(mAvcCodecInfos);
                            mVideoCodec.setAdapter(codecsAdapter);
                            restoreSelections(mVideoCodec, mVieoResolution, mVideoFramerate, mIFrameInterval, mVideoBitrate);

                        });
                        Utils.findEncodersByTypeAsync(AUDIO_AAC, infos -> {
                            logCodecInfos(infos, AUDIO_AAC);
                            mAacCodecInfos = infos;
                            SpinnerAdapter codecsAdapter = createCodecsAdapter(mAacCodecInfos);
                            mAudioCodec.setAdapter(codecsAdapter);
                            restoreSelections(mAudioCodec, mAudioChannelCount);
                        });
                        mAudioToggle.setChecked(
                                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                        .getBoolean(getResources().getResourceEntryName(mAudioToggle.getId()), true));
                        return true;
                    }
                    if (url.contains("StopRecording")) {
                        if (mRecorder != null) {
                            stopRecordingAndOpenFile(getApplicationContext());
                        }
                        return true;
                    }
                    if (url.contains("openspotify")) {
                        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
                        AuthorizationClient.openLoginActivity(HomeActivity.this, AUTH_TOKEN_REQUEST_CODE, request);

                    }
                    else if (url.contains("geo:")) {
//                        Uri gmmIntentUri = Uri.parse(url);
//                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
//                        mapIntent.setPackage("com.google.android.apps.maps");
//                        if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                            startActivity(mapIntent);
//                        }
                        return true;
                    }
                    else if (url.contains("StopMediaRingTone")) {
                        if (RingtonePlay.isplayingAudio) {
                            RingtonePlay.stopAudio();

                        }
                        if (url.contains("ACS")) {
                            OnOffSpeaker("earpiece");

                        }
                        else if(url.contains("VCS")){
                            OnOffSpeaker("speaker");
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
                    else if (url.contains("EndCall")) {
                        final Context context = webView.getContext();
                        final AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        am.setMode(AudioManager.MODE_NORMAL);
                        if (RingtonePlay.isplayingAudio) {
                            RingtonePlay.stopAudio();
                        }
                        return true;
                    }
                    else if(url.contains("StartWakeLock")){
                        if(url.contains("true")){
                            if(!wakeLock.isHeld()) {
                                wakeLock.acquire();
                            }
                        }
                        else{
                            if(wakeLock.isHeld()) {
                                wakeLock.release();
                            }
                        }
                        return true;
                    }
                    else if (url.contains("CallingToUser")) {
                        if (url.contains("CallingToUser~ACS")) {
                            OnOffSpeaker("earpiece");

                        }
                        else if(url.contains("VCS")){
                            OnOffSpeaker("speaker");
                        }
                        if (!RingtonePlay.isplayingAudio) {
                            RingtonePlay.playTringRingtone(getApplicationContext());
                        }
                        return true;
                    }
                    else if (url.contains("InComingCall")) {

//
                        if (!RingtonePlay.isplayingAudio && !appPreferences.getisAccepted()) {
                            PlayRingtone();
                        }
//                        if(appPreferences.getUserPresenceState().equals("Live")){
//
//                        }

                        return true;
                    }
                    else if (url.contains("InComingMessageNotification")) {
                        if (!appPreferences.getUserPresenceState().equals("NotLive")) {
                            Uri alarmSound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notify);
                            MediaPlayer mediaPlayer = MediaPlayer.create(HomeActivity.this, alarmSound);
                            mediaPlayer.start();
                        }

//                        if(appPreferences.getUserPresenceState().equals("Live")){
//
//                        }

                        return true;
                    }
                    else if (url.startsWith("date:")) {

                        Log.d(this.getClass().getCanonicalName(), url);

                        Calendar beginCal = Calendar.getInstance();

                        Calendar endCal = Calendar.getInstance();

                        Date beginDate = new Date(0, 0, 0);

                        Date endDate = new Date(0, 0, 0);

                        String parsed = url.substring(5);

                        String[] components = parsed.split(",");

                        beginDate.setMonth(Integer.parseInt(components[0]));

                        beginDate.setDate(Integer.parseInt(components[1]));

                        beginDate.setYear(Integer.parseInt(components[2]));

                        beginCal.setTime(beginDate);

                        endDate.setMonth(Integer.parseInt(components[3]));

                        endDate.setDate(Integer.parseInt(components[4]));

                        endDate.setYear(Integer.parseInt(components[5]));

                        endCal.setTime(endDate);

                        //calendarevent(beginCal, endCal, components[6]);
                        ContentResolver cr = getApplicationContext().getContentResolver();
                        //addEventToCalender();
                        //AddEventIntoGoogleCalendar(cr,"Testing Event","XYZ","",0,beginCal.getTimeInMillis(),true);
                        //addEventToCalender(cr,"Testing Event","XYZ","",0,beginCal.getTimeInMillis(),true,endCal.getTimeInMillis());
                        return true;

                    }
                    else if(url.contains("AddToExisting")){
                        if (!check_permission(6)) {
                            String[] perms = {Manifest.permission.READ_CONTACTS};
                            ActivityCompat.requestPermissions(HomeActivity.this, perms, MY_PERMISSION_REQUEST_CODE);
                        } else {
                            String[] values;
                            String[] ContactNumber;
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                values = bkpUrl.split("&");
                                for (int i = 0; i < values.length; i++) {
                                    if (values[i].contains("ContactToAdd")) {
                                        ContactNumber = values[i].split("=");
                                        appPreferences.setContactNumberToAdd(ContactNumber[1]);
                                    }
                                    else if(values[i].contains("ContactEmail")) {
                                        ContactNumber = values[i].split("=");
                                        appPreferences.setContactEmailToAdd(ContactNumber[1]);

                                    }
                                }

                            }
                            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                            File photoFile = null;
                            try {
                                photoFile = CreateVCFFile();
                                if (photoFile != null) {
                                    mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                                }

                                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        mCameraPhotoPath);
                                //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                intent.putExtra("return-data", true);
                            } catch (IOException ex) {
                                // Error occurred while creating the File
                                Log.e(TAG, "Unable to create Image File", ex);
                            }
                            startActivityForResult(intent, CONTACT_ADD_TO_EXISTING);

                        }
                        return true;
                    }
                    else if(url.contains("AddToNewContact")){
                        if (!check_permission(6)) {
                            String[] perms = {Manifest.permission.READ_CONTACTS};
                            ActivityCompat.requestPermissions(HomeActivity.this, perms, MY_PERMISSION_REQUEST_CODE);
                        } else {
                            String[] values;
                            String[] ContactNumber;
                            String Number="";
                            String Name="";
                            String Email="";
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                values = bkpUrl.split("&");
                                for (int i = 0; i < values.length; i++) {
                                    if (values[i].contains("ContactToAdd")) {
                                        ContactNumber = values[i].split("=");
                                        Number=ContactNumber[1];
                                    }
                                    else if(values[i].contains("ContactEmail")) {
                                        ContactNumber = values[i].split("=");
                                        Email=ContactNumber[1];
                                    }
                                    else if(values[i].contains("ContactName")) {
                                        ContactNumber = values[i].split("=");
                                        Name=ContactNumber[1];
                                    }
                                }

                            }
                            Intent intent = new Intent(Intent.ACTION_INSERT);
                            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, Number);
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, Email);
                            intent.putExtra(ContactsContract.Intents.Insert.NAME, Name);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent);

                        }
                        return true;
                    }
                    else if (url.contains("opencontact")) {
                        String Vcard = "";
                        url = Uri.decode(url);
                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                        Matcher regexMatcherBkp = regexBkp.matcher(url);
                        String FeedID="";
                        String ContactNo="";
                        String BtnType="";
                        if (regexMatcherBkp.find()) {
                            Vcard = regexMatcherBkp.group(1);

                            String[] array;
                            String array1;


                            Vcard=Vcard.replace("opencontact&","");
                            array = Vcard.split("&");
                            for (int i = 0; i < array.length; i++) {
                                if (array[i].contains("FeedID")) {
                                    FeedID = array[i].split("=")[1];

                                } else if (array[i].contains("ContactNo")) {
                                    ContactNo = array[i].split("=")[1];
                                }
                                else if(array[i].contains("BtnType")){
                                    BtnType=array[i].split("=")[1];
                                }
                            }
                        }
                        JSONArray arr=GetAllFeedContacts(FeedID,ContactNo);
                        String Vcard1="";
                        for (int i = 0; i < arr.length(); i++) {
                            try {
                                JSONObject obj = null;

                                obj = new JSONObject(arr.get(i).toString());
                                 Vcard1=obj.getString("VcardString");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        File vdfdirectory = new File(
                                getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Grapevine/Sent/" + VCF_DIRECTORY);
                        // have the object build the directory structure, if needed.
                        if (!vdfdirectory.exists()) {
                            vdfdirectory.mkdirs();
                        }

                        vcfFile = new File(vdfdirectory, "android_"+ Calendar.getInstance().getTimeInMillis() + ".vcf");

                        FileWriter fw = null;
                        try {
                            fw = new FileWriter(vcfFile);
                            fw.write(Vcard1);

                            fw.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        Uri path = Uri.fromFile(vcfFile);
//                        Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", vcfFile);
                        VCardReader reader = null;
                        try {
                            /*Create Contact by selection of account by using vcf file*/
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.setDataAndType(uri,"text/x-vcard"); //storage path is path of your vcf file and vFile is name of that file.
//
//                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            startActivity(intent);
                            /*Create Contact by selection of account by using vcf file*/
/*Create Contact by current account by using vcf file*/
//                            Cursor _cursor = null;
//                            ContentResolver _contentresolver = getContentResolver();
//                            try {
//                                _cursor = _contentresolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
//                            } catch (Exception ex) {
//                                Log.e("Error on contact", ex.getMessage());
//                            }
//                            String AccountType="";
//                            String AccountName="";
//                            if (_cursor.getCount() > 0) {
//                                while (_cursor.moveToNext()) {
//                                    String Contact_id = _cursor.getString(_cursor.getColumnIndex(ContactsContract.Contacts._ID));
//
//
//                                    Cursor emailCur = _contentresolver.query(ContactsContract.RawContacts.CONTENT_URI,
//                                            new String[]{ContactsContract.RawContacts.ACCOUNT_NAME, ContactsContract.RawContacts.ACCOUNT_TYPE},
//                                            ContactsContract.RawContacts.CONTACT_ID +"=?",
//                                            new String[]{String.valueOf(Contact_id)},
//                                            null);
//                                    while (emailCur.moveToNext()) {
//                                        AccountName = emailCur.getString(emailCur.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME));
//                                         AccountType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE));
//                                         if(!AccountType.equals("")){
//                                             break;
//                                         }
//
//
//                                    }
//                                    emailCur.close();
//                                    if(!AccountType.equals("")){
//                                        break;
//                                    }
//
//                                }
//                                _cursor.close();
//                            }
                            /*Create Contact by current account by suing vcf file*/

                            /*Create Contact by Contact Intent by suing vcf file*/
                            reader = new VCardReader(vcfFile);
                            reader.registerScribe(new AndroidCustomFieldScribe());
                            List<VCard> vcards=Ezvcard.parse(vcfFile).all();
                            ContactOperations operations = new ContactOperations(getApplicationContext(), "", "");
                            String ContactEmail="";
                            String ContactName="";
                            Boolean IsName=false;
                            Boolean IsEmail=false;
                            for (VCard vcard : vcards){
                                ArrayList<ContentValues> data=   operations.insertContact(vcard);



                                Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);
                                if(BtnType.equals("Create")){
                                    startActivity(intent);
                                }
                                else{
                                    for(int i=0;i<data.size();i++){
                                        ContentValues cv=data.get(i);
                                        Set<Map.Entry<String, Object>> s=cv.valueSet();
                                        Iterator itr = s.iterator();

                                        Log.d("DatabaseSync", "ContentValue Length :: " +cv.size());

                                        while(itr.hasNext())
                                        {
                                            Map.Entry me = (Map.Entry)itr.next();
                                            String key = me.getKey().toString();
                                            Object value =  me.getValue();
                                            if(value.toString().toLowerCase().contains("email")){
                                                IsEmail=true;
                                            }
                                            if(value.toString().toLowerCase().contains("name")){
                                                IsName=true;
                                            }
                                            if(IsEmail && key.equals("data1")){
                                                ContactEmail=value.toString();
                                                IsEmail=false;
                                            }
                                            if(IsName && key.equals("data1")){
                                                ContactName=value.toString();
                                                IsName=false;
                                            }
                                            Log.d("DatabaseSync", "Key:"+key+", values:"+(String)(value == null?null:value.toString()));
                                        }
                                    }

                                    //Toast.makeText(getApplicationContext(), "Contact Saved.", Toast.LENGTH_LONG).show();
                                    appPreferences.setContactEmailToAdd(ContactEmail);
                                    appPreferences.setContactNumberToAdd(ContactNo);
                                    appPreferences.setContactNameToAdd(ContactName);

                                    HomeActivity.this.startActivityForResult(intent,CONTACT_PICK_REQUEST_ADD_PHONE_MESSAGE);
                                }

                            }
                            /*Create Contact by Contact Intent by suing vcf file*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            closeQuietly(reader);
                        }
//                        try
//                        {
//                            Intent intent =new Intent(Intent.ACTION_PICK);
//                            MimeTypeMap myMime = MimeTypeMap.getSingleton();
//                            String mimeType = myMime.getMimeTypeFromExtension(fileExt(vcfFile.toString()));
//                            intent.setDataAndType(uri,mimeType);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            startActivity(intent);

////                        Intent intent = new Intent(Intent.ACTION_INSERT);
////                        //intent.setType(ContactsContract.Contacts.CONTENT_VCARD_TYPE);
////                        intent.setDataAndType(uri,ContactsContract.Contacts.CONTENT_VCARD_TYPE);
////
////                        //intent.putExtra(ContactsContract.Intents.Insert.DATA, uri);
//////                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
//////                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, Email);
////                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                        getApplicationContext().startActivity(intent);
//
////                            String vcfMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("vcf");
////                            Intent openVcfIntent = new Intent(Intent.ACTION_VIEW);
////                            openVcfIntent.setDataAndType(uri, vcfMimeType);
////                            // Try to explicitly specify activity in charge of opening the vCard so that the user doesn't have to choose
////                            // https://stackoverflow.com/questions/6827407/how-to-customize-share-intent-in-android/9229654#9229654
////                            try
////                            {
////                                if (getApplicationContext().getPackageManager() != null)
////                                {
////                                    openVcfIntent.setPackage("com.android.contacts");
//////                                    List<ResolveInfo> resolveInfos = getApplicationContext().getPackageManager().queryIntentActivities(openVcfIntent, 0);
//////                                    if (resolveInfos != null)
//////                                    {
//////                                        for (ResolveInfo resolveInfo : resolveInfos)
//////                                        {
//////                                            ActivityInfo activityInfo = resolveInfo.activityInfo;
//////                                            if (activityInfo != null)
//////                                            {
//////                                                String packageName = activityInfo.packageName;
//////                                                String name = activityInfo.name;
//////                                                // Find the needed Activity based on Android source files: http://grepcode.com/search?query=ImportVCardActivity&start=0&entity=type&n=
//////                                                if (packageName != null && packageName.equals("com.android.contacts") && name != null && name.contains("ImportVCardActivity"))
//////                                                {
//////                                                    openVcfIntent.setPackage(packageName);
//////                                                    break;
//////                                                }
//////                                            }
//////                                        }
//////                                    }
////                                }
////                            }
////                            catch (Exception ignored)
////                            {
////                            }
////
////                            startActivity(openVcfIntent);
//                        }
//                        catch (Exception exception)
//                        {
//                            // No app for openning .vcf files installed (unlikely)
//                        }
//                        Intent i = new Intent(); //this will import vcf in contact list
//                    i.setAction(android.content.Intent.ACTION_VIEW);
//                    i.setDataAndType(uri, ContactsContract.Contacts.CONTENT_VCARD_TYPE);
//                    startActivity(i);


//                        Intent intent = new Intent(Intent.ACTION_INSERT);
//                        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

////                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, number);
////                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, Email);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        getApplicationContext().startActivity(intent);
                        return true;
                    }
                    else if (url.contains("ShareToHome")) {
                        url = Uri.decode(url);
                        String bkpUrl = null;
                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                        Matcher regexMatcherBkp = regexBkp.matcher(url);
                        if (regexMatcherBkp.find()) {
                            bkpUrl = regexMatcherBkp.group(1);
                            bkpUrl = bkpUrl.replace("ShareToHome&", "");
                            Intent myIntent = new Intent(Intent.ACTION_SEND);
                            myIntent.setType("text/plain");
                            myIntent.putExtra(Intent.EXTRA_TEXT, "Share From Webview App: " + bkpUrl);
                            startActivity(myIntent);
                            //addShourcut(bkpUrl);
                        }
                    }
                    else if (url.contains("Signal=!201.ACS")) {


                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        boolean useHeadphone = am.isWiredHeadsetOn();
                        am.setMode(AudioManager.MODE_IN_COMMUNICATION);
                        am.setSpeakerphoneOn(!useHeadphone);
                        //return true;
                    }
                    else if (url.contains("Signal=!101.VCS")) {
                        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

                        boolean useHeadphone = am.isWiredHeadsetOn();
                        //am.setMode(AudioManager.ROUTE_SPEAKER);
                        am.setMode(AudioManager.MODE_NORMAL);
                        am.setSpeakerphoneOn(!useHeadphone);
                       // return true;
                    }
                    else if (url.contains("GetDirection")) {

                        url = Uri.decode(url);
                        String bkpUrl = null;
                        Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                        Matcher regexMatcherBkp = regexBkp.matcher(url);
                        if (regexMatcherBkp.find()) {
                            bkpUrl = regexMatcherBkp.group(1);
                            bkpUrl = bkpUrl.replace("GetDirection&", "");
                        }


                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(bkpUrl));
                        startActivity(intent);

                    }
                    else if (url.contains("CheckInFlag=0")) {
                        String[] arr = url.split("=");
                        String FeedID = arr[2].replace("#Intent;scheme", "");
                        Uri uri = Uri.parse(url);
                        String server = uri.getAuthority();
                        String path = uri.getPath();
                        String protocol = uri.getScheme();
                        Set<String> args = uri.getQueryParameterNames();
                        //String FeedID = uri.getQueryParameter("FeedID");
                        getCurrentAddress(FeedID);
                        if(!url.startsWith("https://www.grapevine.work/Grapevine/Index.html")){
                            return true;
                        }

                    }
                    else if(url.contains("CheckInFlag=1")){
                        return true;
                    }
                    else if (url.startsWith("intent://") && url.contains("scheme=http")) {
//
                        if (url.contains("downloadfile") || url.contains("downloadreceipt")) {
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("downloadfile&", "");
                                bkpUrl = bkpUrl.replace("downloadreceipt&", "");
                            }
                            File file = downloadfile(bkpUrl);

                            URL url1 = null;
                            try {
                                url1 = new URL(bkpUrl);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            File f = new File(url1.getFile());

                            MimeTypeMap myMime = MimeTypeMap.getSingleton();
                            Intent newIntent = new Intent(Intent.ACTION_VIEW);
                            String mimeType = myMime.getMimeTypeFromExtension(fileExt(url1.getFile()));
                            Uri path = Uri.fromFile(file);
                            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", file);

                            newIntent.setDataAndType(uri, mimeType);
                            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            try {
                                if(url.contains("downloadreceipt")){
                                    String[] fileUrl=bkpUrl.split("/");
                                    bkpUrl=fileUrl[fileUrl.length-1];
                                    DeletePaymentReceipt(bkpUrl);
                                }
                                startActivity(newIntent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(getApplicationContext(), "No handler for this type of file.", Toast.LENGTH_LONG).show();
                            }


                            return true;
                        }
                        else if (url.contains("CopyImageToClipboard")) {
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("CopyImageToClipboard&", "");

                            }
                            File file = downloadfile(bkpUrl);

                            URL url1 = null;
                            try {
                                url1 = new URL(bkpUrl);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            File f = new File(url1.getFile());

                            MimeTypeMap myMime = MimeTypeMap.getSingleton();
                            Intent newIntent = new Intent(Intent.ACTION_VIEW);
                            String mimeType = myMime.getMimeTypeFromExtension(fileExt(url1.getFile()));
                            Uri path = Uri.fromFile(file);
                            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", file);

                            Intent i = getIntent();
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                            clipboard.setPrimaryClip(ClipData.newUri(getContentResolver(),"",uri));
                            Toast.makeText(getApplicationContext(), "Image copied to clipboard!", Toast.LENGTH_SHORT).show();


//                            newIntent.setDataAndType(uri, mimeType);
//                            newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            newIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            try {
//                                if(url.contains("downloadreceipt")){
//                                    String[] fileUrl=bkpUrl.split("/");
//                                    bkpUrl=fileUrl[fileUrl.length-1];
//                                    DeletePaymentReceipt(bkpUrl);
//                                }
//                                startActivity(newIntent);
//                            } catch (ActivityNotFoundException e) {
//                                Toast.makeText(getApplicationContext(), "No handler for this type of file.", Toast.LENGTH_LONG).show();
//                            }


                            return true;
                        }
                        else if (url.contains("ShareFileToOtherApp")) {
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("ShareFileToOtherApp&", "");
                            }
                            File file = downloadfile(bkpUrl);

                            Uri uri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", file);

                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                            sharingIntent.setType("*/*");
                            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            try {
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                                // startActivity(share);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(getApplicationContext(), "No handler for this type of file.", Toast.LENGTH_LONG).show();
                            }

                            return true;
                        }
                        else if (url.contains("open=sharedlink")) {

                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bkpUrl));
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.setPackage("com.android.chrome");
//                                launchGoogleChrome.putExtra("com.android.chrome.EXTRA_OPEN_NEW_INCOGNITO_TAB", true);
                                startActivity(intent);


                                return true;
                            } else {
                                return false;
                            }

                        }
                        else if (url.contains("showfile")) {
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("showfile&", "");
                                // File myFile = new File(bkpUrl);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.setPackage("com.android.chrome");
//                                launchGoogleChrome.putExtra("com.android.chrome.EXTRA_OPEN_NEW_INCOGNITO_TAB", true);
                                try {
                                    URL url1 = new URL(bkpUrl);
                                    File f = new File(url1.getFile());
                                    MimeTypeMap myMime = MimeTypeMap.getSingleton();
                                    Intent newIntent = new Intent(Intent.ACTION_VIEW);
                                    String mimeType = myMime.getMimeTypeFromExtension(fileExt(url1.getFile()));
                                    newIntent.setDataAndType(Uri.fromFile(f), mimeType);
                                    newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    try {
                                        getApplicationContext().startActivity(newIntent);
                                    } catch (ActivityNotFoundException e) {
                                        Toast.makeText(getApplicationContext(), "No handler for this type of file.", Toast.LENGTH_LONG).show();
                                    }
                                    //FileOpen.openFile(getApplicationContext(), myFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                return true;
                            } else {
                                return false;
                            }


                        }
                        else if(url.contains("InvitationLink")){
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            String MobileNo="";
                            String SenderName="";
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("InvitationLink&", "");
                                String[] values = bkpUrl.split("&");
                                for (int i = 0; i < values.length; i++) {
                                    if (values[i].contains("MobileNo")) {
                                        MobileNo = values[i].split("=")[1];

                                    }
                                    else if (values[i].contains("SenderName")) {
                                        SenderName = values[i].split("=")[1];

                                    }

                                }
                                //MobileNo=MobileNo.replaceAll("++","+");
                                Toast.makeText(HomeActivity.this, MobileNo, Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "MobileNo: " + MobileNo);
                                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                                //smsIntent.setType("vnd.android-dir/mms-sms");
                                smsIntent.setData(Uri.parse("sms:"));
                                smsIntent.putExtra("address",MobileNo);
                                smsIntent.putExtra("sms_body",SenderName+" has invited you to join Grapevine, please click on link below.\n http://play.google.com/store/apps/details?id=com.oxyzenhomes.grapevine");
                                startActivity(smsIntent);
                            }
                            return true;
                        }
                        else if(url.contains("GetContactsList")){
                            _ContactList.clear();

                            ContentResolver cr = getContentResolver();
                            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                                    null, null, null, null);

                            if ((cur != null ? cur.getCount() : 0) > 0) {
                                while (cur != null && cur.moveToNext()) {
                                    ContactList _List = new ContactList();
                                    String id = cur.getString(
                                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                                    String name = cur.getString(cur.getColumnIndex(
                                            ContactsContract.Contacts.DISPLAY_NAME));
                                    _List.ContactName=name;

                                    String Email = "";
                                    Cursor emailCur = cr.query(
                                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                            null,
                                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                            new String[]{id}, null);
                                    while (emailCur.moveToNext()) {
                                        Email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                                        _List.ContactEmail=Email;

                                    }


                                    if (cur.getInt(cur.getColumnIndex(
                                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                                        Cursor pCur = cr.query(
                                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                                null,
                                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                                new String[]{id}, null);
                                        while (pCur.moveToNext()) {
                                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                                            _List.ContactNo=phoneNo;
//                                            Log.i(TAG, "Name: " + name);
//                                            Log.i(TAG, "Phone Number: " + phoneNo);
                                        }
                                        pCur.close();
                                    }
                                    int i = _ContactList.indexOf(_List.ContactNo);
                                    if (i == -1) {
                                        _ContactList.add(_List);
                                    }
                                    if(_ContactList.size()>50){
                                        Gson gson = new Gson();
                                        String json = gson.toJson(_ContactList);

                                        Log.i(TAG, "Contact Json: " + json);
                                        String Applicant_id=appPreferences.getApplicant_id();
                                        GetGrapevineContacts(json, Applicant_id);
                                        _ContactList.clear();
                                    }
                                }
                            }
                            if(cur!=null){
                                cur.close();
                            }
                            //if(_ContactList.size()>50){
                                Gson gson = new Gson();
                                String json = gson.toJson(_ContactList);

                                Log.i(TAG, "Contact Json: " + json);
                                String Applicant_id=appPreferences.getApplicant_id();
                                GetGrapevineContacts(json, Applicant_id);
                                _ContactList.clear();
                            //}
                            return true;
                        }
                        else if (url.contains("AddPhoneContacts")) {
                            if (check_permission(6)) {
                                Intent intent = new Intent(Intent.ACTION_INSERT);
                                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

//                                intent.putExtra(ContactsContract.Intents.Insert.NAME, person.name);
//                                intent.putExtra(ContactsContract.Intents.Insert.PHONE, person.mobile);
//                                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, person.email);

                                startActivity(intent);

//                                ContactsResults.clear();
////                                Intent intentContactPick = new Intent(HomeActivity.this, ContactsPickerActivity.class);
////                                HomeActivity.this.startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST);
//                                new MultiContactPicker.Builder(HomeActivity.this) //Activity/fragment context
//                                        .theme(R.style.Theme_AppCompat_Light_NoActionBar_FullScreen) //Optional - default: MultiContactPicker.Azure
//                                        .hideScrollbar(false) //Optional - default: false
//                                        .showTrack(true) //Optional - default: true
//                                        .clearSelectedContact(true)
//                                        .searchIconColor(Color.WHITE) //Optional - default: White
//                                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
//                                        .handleColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
//                                        .bubbleColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
//                                        .bubbleTextColor(Color.WHITE) //Optional - default: White
//                                        .setTitleText("Select Contacts") //Optional - only use if required
//                                        .setSelectedContacts(ContactsResults) //Optional - will pre-select contacts of your choice. String... or List<ContactResult>
//                                        .setLoadingType(MultiContactPicker.LOAD_ASYNC) //Optional - default LOAD_ASYNC (wait till all loaded vs stream results)
//                                        .limitToColumn(LimitColumn.NONE) //Optional - default NONE (Include phone + email, limiting to one can improve loading time)
//                                        .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
//                                                android.R.anim.fade_in,
//                                                android.R.anim.fade_out) //Optional - default: No animation overrides
//                                        .showPickerForResult(CONTACT_PICK_REQUEST_ADD_PHONE);

//                                Intent intentContactPick = new Intent(HomeActivity.this, ContactsPickerActivity.class);
//                                HomeActivity.this.startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST_ADD_PHONE);



                            } else {
                                String[] perms = {Manifest.permission.READ_CONTACTS};
                                ActivityCompat.requestPermissions(HomeActivity.this, perms, MY_PERMISSION_REQUEST_CODE);
                            }
                            return true;
                        }
                        else if (url.contains("GetAllContacts")) {
                            if (check_permission(6)) {
                                ContactsResults.clear();
//                                Intent intentContactPick = new Intent(HomeActivity.this, ContactsPickerActivity.class);
//                                HomeActivity.this.startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST_INVITATION);
                                new MultiContactPicker.Builder(HomeActivity.this) //Activity/fragment context
                                        .theme(R.style.Theme_AppCompat_Light_NoActionBar_FullScreen) //Optional - default: MultiContactPicker.Azure
                                        .hideScrollbar(false) //Optional - default: false
                                        .clearSelectedContact(true)
                                        .showTrack(true) //Optional - default: true
                                        .searchIconColor(Color.WHITE) //Optional - default: White
                                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                                        .handleColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                                        .bubbleColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                                        .setTitleText("Select Contacts") //Optional - only use if required
                                        .setSelectedContacts(ContactsResults) //Optional - will pre-select contacts of your choice. String... or List<ContactResult>
                                        .setLoadingType(MultiContactPicker.LOAD_ASYNC) //Optional - default LOAD_ASYNC (wait till all loaded vs stream results)
                                        .limitToColumn(LimitColumn.NONE) //Optional - default NONE (Include phone + email, limiting to one can improve loading time)
                                        .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                                android.R.anim.fade_in,
                                                android.R.anim.fade_out) //Optional - default: No animation overrides
                                        .showPickerForResult(CONTACT_PICK_REQUEST_INVITATION);

                            } else {
                                String[] perms = {Manifest.permission.READ_CONTACTS};
                                ActivityCompat.requestPermissions(HomeActivity.this, perms, MY_PERMISSION_REQUEST_CODE);
                            }
                            return true;
                        }
                        else if (url.contains("getcontacts")) {
//

                            if (!check_permission(6)) {
                                String[] perms = {Manifest.permission.READ_CONTACTS};
                                ActivityCompat.requestPermissions(HomeActivity.this, perms, MY_PERMISSION_REQUEST_CODE);
                            } else {
                                String FriendFeedChannelID = "";
                                String UserFeedChannelID = "";
                                String PubnubChannel = "";
                                String AgentMode = "";
                                String FilterOnFeedChannelID = "";
                                String CRMActivityChannelID = "";
                                String EntityFeedChannelID = "";
                                String[] values;
                                String[] FeedChannelID;
                                url = Uri.decode(url);
                                String bkpUrl = null;
                                Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                                Matcher regexMatcherBkp = regexBkp.matcher(url);
                                if (regexMatcherBkp.find()) {
                                    bkpUrl = regexMatcherBkp.group(1);
                                    values = bkpUrl.split("&");
                                    for (int i = 0; i < values.length; i++) {
                                        if (values[i].contains("FriendFeedChannelID")) {
                                            FeedChannelID = values[i].split("=");
                                            if(FeedChannelID.length>1){
                                                appPreferences.setFriendFeedChannelID(FeedChannelID[1]);
                                            }

                                        } else if (values[i].contains("UserFeedChannelID")) {
                                            FeedChannelID = values[i].split("=");
                                            if(FeedChannelID.length>1) {
                                                appPreferences.setUserFeedChannelID(FeedChannelID[1]);
                                            }
                                        } else if (values[i].contains("PubnubChannel")) {
                                            FeedChannelID = values[i].split("=");
                                            if(FeedChannelID.length>1) {
                                                appPreferences.setPubnubChannel(FeedChannelID[1]);
                                            }
                                        }
                                        else if (values[i].contains("AgentMode")) {
                                            FeedChannelID = values[i].split("=");
                                            if(FeedChannelID.length>1) {
                                                AgentMode=FeedChannelID[1];
                                                appPreferences.setAgentMode(FeedChannelID[1]);
                                            }
                                        }
                                        else if (values[i].contains("FilterOnFeedChannelID")) {
                                            FeedChannelID = values[i].split("=");
                                            if(FeedChannelID.length>1) {
                                                appPreferences.setFilterOnFeedChannelID(FeedChannelID[1]);
                                            }
                                        }
                                        else if (values[i].contains("CRMActivityChannelID")) {
                                            FeedChannelID = values[i].split("=");
                                            if(FeedChannelID.length>1) {
                                                appPreferences.setCRMActivityChannelID(FeedChannelID[1]);
                                            }
                                        }
                                        else if (values[i].contains("EntityFeedChannelID")) {
                                            FeedChannelID = values[i].split("=");
                                            if(FeedChannelID.length>1) {
                                                appPreferences.setEntityFeedChannelID(FeedChannelID[1]);
                                            }
                                        }
                                    }

                                }
                                //Toast.makeText(HomeActivity.this, "Contact Detail:"+AgentMode, Toast.LENGTH_SHORT).show();
                                ContactsResults.clear();
//                                Intent intentContactPick = new Intent(HomeActivity.this, ContactsPickerActivity.class);
//                                HomeActivity.this.startActivityForResult(intentContactPick,CONTACT_PICK_REQUEST);
                                new MultiContactPicker.Builder(HomeActivity.this) //Activity/fragment context
                                        .theme(R.style.Theme_AppCompat_Light_NoActionBar_FullScreen) //Optional - default: MultiContactPicker.Azure
                                        .hideScrollbar(false) //Optional - default: false
                                        .showTrack(true) //Optional - default: true
                                        .clearSelectedContact(true)
                                        .searchIconColor(Color.WHITE) //Optional - default: White
                                        .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                                        .handleColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                                        .bubbleColor(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                                        .setTitleText("Select Contacts") //Optional - only use if required
                                        .setSelectedContacts(ContactsResults) //Optional - will pre-select contacts of your choice. String... or List<ContactResult>
                                        .setLoadingType(MultiContactPicker.LOAD_ASYNC) //Optional - default LOAD_ASYNC (wait till all loaded vs stream results)
                                        .limitToColumn(LimitColumn.NONE) //Optional - default NONE (Include phone + email, limiting to one can improve loading time)
                                        .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                                                android.R.anim.fade_in,
                                                android.R.anim.fade_out) //Optional - default: No animation overrides
                                        .showPickerForResult(CONTACT_PICK_REQUEST);
                                return true;
                            }


                            return true;

                        } 
                        else if (url.contains("AddToCalender")) {
                            if (!check_permission(13) || !check_permission(14)) {
                                get_file();
                            }

                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("AddToCalender&", "");
                                String[] values = bkpUrl.split("&");
                                String FeedChannelID = "";
                                String FeedID = "";
                                String ActivityTypeID = "";
                                String ActivityID = "";
                                for (int i = 0; i < values.length; i++) {
                                    if (values[i].contains("FeedChannelID")) {
                                        FeedChannelID = values[i].split("=")[1];

                                    } else if (values[i].contains("FeedID")) {
                                        FeedID = values[i].split("=")[1];

                                    } else if (values[i].contains("ActivityTypeID")) {
                                        ActivityTypeID = values[i].split("=")[1];

                                    } else if (values[i].contains("ActivityID")) {
                                        ActivityID = values[i].split("=")[1];

                                    }
                                }
                                GetAllActivities(FeedChannelID, FeedID, ActivityTypeID, ActivityID);
                            }
                            return true;
                        } 
                        else if (url.contains("ShareToAnother")) {
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("ShareToAnother&", "");
                                if (bkpUrl.contains("AddOnHomeScreen")) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                                        addShourcut(bkpUrl);
                                    }
                                } else {
                                    Intent clipboardIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                    clipboardIntent.setData(Uri.parse(bkpUrl));
                                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                                    myIntent.setType("text/plain");
                                    myIntent.putExtra(Intent.EXTRA_TEXT, bkpUrl);
                                    //startActivity(myIntent);
                                    Intent chooserIntent = Intent.createChooser(myIntent, "Share with");
                                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{clipboardIntent});

                                    startActivity(chooserIntent);

                                    // startActivity(Intent.createChooser(myIntent, "Share with"));
                                }
                                return true;
                            } else {
                                return false;
                            }

                        }
                        else if(url.contains("richsms")){
                            File file = downloadfile("https://cdn.grapevine.work/grapevine.work/feedChannelImages/25/Logo/image/829ac7f4-94d7-4840-b08f-621cce8fef1c.jpg");
                            Intent sendIntent = new Intent(Intent.ACTION_SEND);
                            //sendIntent.setType("vnd.android-dir/mms-sms");
                            //sendIntent.setClassName("com.android.mms", "com.android.mms.ui.ComposeMessageActivity");
                            sendIntent.putExtra("sms_body", "some text");
                            sendIntent.putExtra("address","6396911076");
                            sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getAbsolutePath()));
                            sendIntent.setType("image/png");
                            startActivity(sendIntent);;
                            return true;
                        }
                        else if(url.contains("CreateNewLeadContact")){
                            if(!check_permission(2) || !check_permission(4)) {
                                get_file();
                            }
                            else {
                                String FeedChannelID = "";
                                String LeadID = "";
                                String AssociateID = "";
                                String Project_Name = "";

                                String[] arr;
                                url = Uri.decode(url);
                                String bkpUrl = null;
                                Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                                Matcher regexMatcherBkp = regexBkp.matcher(url);
                                if (regexMatcherBkp.find()) {
                                    bkpUrl = regexMatcherBkp.group(1);
                                    bkpUrl = bkpUrl.replace("CreateNewLeadContact&", "");
                                    String[] values = bkpUrl.split("&");

                                    for (int i = 0; i < values.length; i++) {
                                        if (values[i].contains("LeadID")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                LeadID = arr[1];
                                            }

                                        }  else if (values[i].contains("AssociateID")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                AssociateID = arr[1];
                                            }
                                        } else if (values[i].contains("UserFeedChannelID")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                FeedChannelID = arr[1];
                                            }
                                        } else if (values[i].contains("Project_Name")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                Project_Name = arr[1];
                                            }
                                        }

                                    }
                                }

                                CreateContactFolder(FeedChannelID, LeadID, AssociateID, Project_Name, "Share");
                            }
                            return true;
                        }
                        else if (url.contains("ShareCRMFile")) {
                            if(!check_permission(2) || !check_permission(4)) {
                                get_file();
                            }
                            else {


                                String MediaType = "";
                                String LeadID = "";
                                String Items = "";
                                String mobile = "";
                                String ProjectID = "";
                                String FacebookUserID = "";
                                String ShareWhat = "1";
                                String AssociateID = "";
                                String Project_Name = "";
                                String FeedChannelID = "";
                                String EntityFeedChannelID="";
                                String[] arr;
                                url = Uri.decode(url);
                                String bkpUrl = null;
                                Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                                Matcher regexMatcherBkp = regexBkp.matcher(url);
                                if (regexMatcherBkp.find()) {
                                    bkpUrl = regexMatcherBkp.group(1);
                                    bkpUrl = bkpUrl.replace("ShareCRMFile&", "");
                                    String[] values = bkpUrl.split("&");

                                    for (int i = 0; i < values.length; i++) {
                                        if (values[i].contains("LeadID")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                LeadID = arr[1];
                                            }

                                        } else if (values[i].contains("Items")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                Items = arr[1];
                                            }

                                        } else if (values[i].contains("mobile")) {
                                            mobile = values[i].split("=")[1];

                                        } else if (values[i].contains("MediaType")) {
                                            MediaType = values[i].split("=")[1];

                                        } else if (values[i].contains("ProjectID")) {
                                            ProjectID = values[i].split("=")[1];

                                        } else if (values[i].contains("FacebookUserID")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                FacebookUserID = arr[1];
                                            }
                                        } else if (values[i].contains("ShareWhat")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                ShareWhat = arr[1];
                                            }
                                        } else if (values[i].contains("AssociateID")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                AssociateID = arr[1];
                                            }
                                        } else if (values[i].contains("UserFeedChannelID")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                FeedChannelID = arr[1];
                                            }
                                        } else if (values[i].contains("Project_Name")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                Project_Name = arr[1];
                                            }
                                        }
                                        else if (values[i].contains("EntityFeedChannelID")) {
                                            arr = values[i].split("=");
                                            if (arr.length > 1) {
                                                EntityFeedChannelID = arr[1];
                                            }
                                        }
                                    }
                                }
                                //CreateContactFolder(FeedChannelID, LeadID, AssociateID, Project_Name, "Share");
                                String ActivitySubTypeID = "AST-17";
                                String ActivityChannelID = "";
                                String ActivityDescription = "Project Info, Images, Documents  Shared";
                                if (MediaType.equals("Mail")) {
                                    ActivityChannelID = "CHN-21";
                                    ActivitySubTypeID = "AST-5";
                                    treemenumail(LeadID, Items);
                                    Toast.makeText(HomeActivity.this, "Mail Sucessfully Sent !!", Toast.LENGTH_SHORT).show();

                                }
                                if (MediaType.equals("WhatsApp")) {
                                    ActivityChannelID = "CHN-4";
                                    PackageManager pm = getPackageManager();
                                    ProgressDialog progressdialog = new ProgressDialog(HomeActivity.this);
                                    progressdialog.setMessage("Please Wait....");
                                    progressdialog.show();
                                    try {
                                        mobile = mobile.replace("+", "").replace("-", "");
                                        //mobile=mobile.replace("-","");
                                        //intent.putExtra("jid", mobile + "@s.whatsapp.net");
                                        // intent.setType("application/*"); /* This example is sharing jpeg images. */
                                        String WhatsAppType = "";
                                        try {
                                            PackageInfo info = pm.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_META_DATA);
                                            WhatsAppType = "Business";
                                        } catch (PackageManager.NameNotFoundException e) {
                                            e.printStackTrace();

                                            try {
                                                PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                                                WhatsAppType = "Normal";
                                            } catch (PackageManager.NameNotFoundException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
//                                    intent.setPackage("com.whatsapp");
//                                    intent.setType("*/*");

                                        if (!ShareWhat.equals("5")) {
                                            List<File> entity = createPdf(LeadID, Items, ProjectID, ShareWhat,EntityFeedChannelID);
                                            ArrayList<Uri> files = new ArrayList<Uri>();


                                            for (File path : entity) {
                                                Uri uri = FileProvider.getUriForFile(HomeActivity.this, GRAPEVINE_FILE_PROVIDER_AUTHORITY, path);
                                                files.add(uri);
                                            }

                                            shareImageWhatsApp(files, mobile, WhatsAppType);
                                        } else {
                                            List<String> data = GetTextToShare(LeadID, Items, ProjectID, ShareWhat,EntityFeedChannelID);
                                            String Message = "";
                                            for (String text : data) {
                                                if (Message.equals("")) {
                                                    Message = text;
                                                } else {
                                                    Message = Message + "," + text;
                                                }

                                            }
                                            String PackageName = "com.whatsapp";
                                            if (WhatsAppType.equals("Business")) {
                                                PackageName = "com.whatsapp.w4b";
                                            }
//                                        if(Message.equals("")){
//                                            Message="Test";
//                                        }

                                            //Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                                            //sendIntent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+mobile +"&text="+ URLEncoder.encode(Message, "UTF-8")));


                                            if (isPackageInstalled(PackageName, HomeActivity.this)) {
                                                String text = URLEncoder.encode(Message, "UTF-8");

                                                Intent waIntent = new Intent(Intent.ACTION_SEND);

                                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" + mobile + "?text=" + text));

                                                intent.setPackage(PackageName);
                                                startActivity(intent);
//                                                waIntent.setType("text/plain");
//                                                //Check if package exists or not. If not then code
//                                                //in catch block will be called
//                                                waIntent.setPackage(PackageName);
//
//                                                waIntent.putExtra(Intent.EXTRA_TEXT, text);
//
//                                                startActivity(waIntent);

//                                            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                                            sendIntent.setPackage(PackageName);
//
//
                                                // startActivity(sendIntent);

                                            } else {

                                                Toast.makeText(getApplicationContext(), "Please Install Whatsapp", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        progressdialog.hide();


                                    } catch (IOException e) {

                                    }


                                }
                                if (MediaType.equals("Messenger")) {
                                    ActivityChannelID = "CHN-5";

                                    PackageManager pm = getPackageManager();
                                    ProgressDialog progressdialog = new ProgressDialog(HomeActivity.this);
                                    progressdialog.setMessage("Please Wait....");
                                    progressdialog.show();


                                    try {

                                        List<File> entity = createPdf(LeadID, Items, ProjectID, ShareWhat,EntityFeedChannelID);
                                        List<String> data = messengermail(LeadID, Items, ProjectID);
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                        // intent.setType("application/*"); /* This example is sharing jpeg images. */

                                        try {
                                            PackageInfo info = pm.getPackageInfo("com.facebook.orca", PackageManager.GET_META_DATA);
                                        } catch (PackageManager.NameNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                        intent.setPackage("com.facebook.orca");
                                        intent.setType("*/*");
                                        ArrayList<Uri> files = new ArrayList<Uri>();


                                        for (File path : entity) {

                                            sendMMS("", path.toString(), FacebookUserID, data);

                                        }

                                        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
                                        //startActivity(intent);

                                        progressdialog.hide();

                                    } catch (IOException e) {

                                    }


                                }

                                String ActivityThreadID = Crm_Add_Activity(LeadID, "", ActivitySubTypeID, ActivityChannelID,
                                        ActivityDescription, "", "", AssociateID, "", "",
                                        "", "", "");
                                Crm_Add_Activity(LeadID, ActivityThreadID, "", ActivityChannelID,
                                        ActivityDescription, "", "", AssociateID, "", "",
                                        "", "", "");
                                String ThreadStageID = "LTS12";
                                crm_updateleadstage(ThreadStageID, "", LeadID);
                            }

                            return true;
                        }
                        else if (url.contains("ShareCrmQuote")) {
                            String MediaType = "";
                            String LeadID = "";
                            String QuoteDetail = "";
                            String mobile = "";
                            String ProjectID = "";
                            String FacebookUserID = "";
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            String AssociateID="";
                            String Project_Name="";
                            String FeedChannelID="";
                            String[] arr;
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("ShareCrmQuote&", "");
                                String[] values = bkpUrl.split("&");

                                for (int i = 0; i < values.length; i++) {
                                    if (values[i].contains("LeadID")) {
                                        LeadID = values[i].split("=")[1];

                                    } else if (values[i].contains("QuoteDetail")) {
                                        QuoteDetail = values[i].split("=")[1];

                                    } else if (values[i].contains("mobile")) {
                                        arr = values[i].split("=");
                                        if(arr.length>1){
                                            mobile = arr[1];
                                        }

                                    } else if (values[i].contains("MediaType")) {
                                        MediaType = values[i].split("=")[1];

                                    } else if (values[i].contains("ProjectID")) {
                                        ProjectID = values[i].split("=")[1];

                                    }
                                    else if (values[i].contains("AssociateID")) {
                                        arr=values[i].split("=");
                                        if(arr.length>1){
                                            AssociateID = arr[1];
                                        }
                                    }
                                    else if (values[i].contains("FeedChannelID")) {
                                        arr=values[i].split("=");
                                        if(arr.length>1){
                                            FeedChannelID = arr[1];
                                        }
                                    }
                                    else if (values[i].contains("Project_Name")) {
                                        arr=values[i].split("=");
                                        if(arr.length>1){
                                            Project_Name = arr[1];
                                        }
                                    }
//                                    else if (values[i].contains("FacebookUserID")) {
//                                        FacebookUserID = values[i].split("=")[1];
//
//                                    }
                                }

                                CreateContactFolder(FeedChannelID, LeadID, AssociateID, Project_Name,"Share");
                                PackageManager pm = getPackageManager();
                                String WhatsAppType="";
                                try {
                                    PackageInfo info = pm.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_META_DATA);
                                    WhatsAppType="Business";
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();

                                    try {
                                        PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                                        WhatsAppType="Normal";
                                    } catch (PackageManager.NameNotFoundException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                                if (MediaType.equals("WhatsApp")) {
                                    String PackageName="com.whatsapp";
                                    if(WhatsAppType.equals("Business")){
                                        PackageName="com.whatsapp.w4b";
                                    }



                                    mobile = mobile.replace("+", "").replace("-", "");
                                    if (mobile.length() == 10) {
                                        mobile = "91" + mobile;
                                    }
                                    QuoteDetail = QuoteDetail.replace("!", "\n").replace("^", "\t").replace("~", "%");


                                    if (isPackageInstalled(PackageName, HomeActivity.this)) {
                                        try{
                                            String text = URLEncoder.encode(QuoteDetail, "UTF-8");

                                            Intent waIntent = new Intent(Intent.ACTION_SEND);

                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" + mobile + "?text=" + text));

                                            intent.setPackage(PackageName);
                                            startActivity(intent);
                                        }
                                        catch(Exception ex){
                                            Log.e("Whatsapp error", ex.getMessage());
                                        }

                                    }

//                                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
//                                    sendIntent.setType("text/plain");
//                                    sendIntent.putExtra(Intent.EXTRA_TEXT, QuoteDetail);
//                                    sendIntent.putExtra("jid", mobile + "@s.whatsapp.net"); //phone number without "+" prefix
//                                    sendIntent.setPackage("com.whatsapp");
//
//                                    if (isPackageInstalled("com.whatsapp", HomeActivity.this)) {
//                                        startActivity(sendIntent);
//                                    }
                                }
                            }
                            return true;
                        }
                        else if (url.contains("getLocation")) {
                            if (appPreferences.getLocationPermissionDenied()) {
                                if (Build.VERSION.SDK_INT >= M) {
                                    if (permissionsToRequest.size() > 0) {
                                        requestPermissions(permissionsToRequest.toArray(
                                                new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                                    }
                                }
                            }
                            return true;
                        }
                        else if(url.contains("AddLeadReminders")){

                            String ReminderDateTime="";
                            String ReminderTitle="";
                            String[] arr;
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);
                                bkpUrl = bkpUrl.replace("AddLeadReminders&", "");
                                String[] values = bkpUrl.split("&");

                                for (int i = 0; i < values.length; i++) {
                                    if (values[i].contains("ReminderDateTime")) {
                                        arr = values[i].split("=");
                                        if (arr.length > 1) {
                                            ReminderDateTime = arr[1];
                                        }

                                    }
                                    else if (values[i].contains("ReminderTitle")) {
                                        arr = values[i].split("=");
                                        if (arr.length > 1) {
                                            ReminderTitle = arr[1];
                                        }

                                    }

                                }
                                addEventToCalender(ReminderTitle, ReminderTitle, ReminderDateTime, "",null);
                            }
                            return true;
                        }
                        else {
                            url = Uri.decode(url);
                            String bkpUrl = null;
                            Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                            Matcher regexMatcherBkp = regexBkp.matcher(url);
                            if (regexMatcherBkp.find()) {
                                bkpUrl = regexMatcherBkp.group(1);

                                Intent myIntent = new Intent(Intent.ACTION_SEND);
                                myIntent.setType("text/plain");
                                myIntent.putExtra(Intent.EXTRA_TEXT, "Share From Webview App: " + bkpUrl);
                                startActivity(myIntent);


                                return true;
                            } else {
                                return false;
                            }
                        }

                    }
                    else {

                        if (url.contains("backtologin")) {
//                            String token = FirebaseInstanceId.getInstance().getToken();
                            String token ="";

                            DeleteAppToken(appPreferences.getApplicant_id(), token);
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
                            appPreferences.setCurrentURL("");
                            appPreferences.setApplicant_id("");
//                        LoginManager.getInstance().logOut();
//
//                        signOut();
                            startActivity(new Intent(HomeActivity.this, SendOTPMSG91.class));
                            finish();
                        }
                        if (url.contains("Index") || url.contains("UserSignin") || url.contains("GrapevineLogin")|| url.contains("main.html")) {
                            Uri url1 = Uri.parse(url);
                            Set<String> paramNames = url1.getQueryParameterNames();
                            for (String key : paramNames) {
                                if (key.equals("ContactID") || key.equals("applicant_id")) {
                                    appPreferences.setApplicant_id(url1.getQueryParameter(key));
                                    JSONObject obj=GetloginDetailsOverContactID(url1.getQueryParameter(key));
                                    try {

                                        if(obj!=null){
                                            String LoginUserMobileNo=  obj.getString("mobile");
                                            appPreferences.setLoginUserMobileNo(LoginUserMobileNo);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    UpdateToken(appPreferences.getApplicant_id());
                                    getCurrentAddress("0");
//                                    FirebaseMessaging.getInstance().getToken()
//                                            .addOnCompleteListener(new OnCompleteListener<String>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<String> task) {
//                                                    if (!task.isSuccessful()) {
//                                                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                                                        return;
//                                                    }
//
//                                                    // Get new FCM registration token
//                                                    String token = task.getResult();
//                                                    //String token = FirebaseInstanceId.getInstance().getToken();
//                                                    String AndroidID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                                                            Settings.Secure.ANDROID_ID);
//                                                    UpdateToken(appPreferences.getApplicant_id(), token, AndroidID);
//
//                                                    // Log and toast
////                        String msg = getString(R.string.msg_token_fmt, token);
////                        Log.d(TAG, msg);
////                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
//                                                }
//                                            });

                                }
                                String value = url1.getQueryParameter(key);
                            }
                        }
//                        if (url.contains("backtosignup")) {
//                            appPreferences.setStuID("");
//                            appPreferences.setschoolname("");
//                            appPreferences.setRegNo("");
//                            appPreferences.setimagePath("");
//                            appPreferences.setFbUserName("");
//                            appPreferences.setFbUserEmail("");
//                            appPreferences.setFbAppId("");
//                            appPreferences.setGoogleUserName("");
//                            appPreferences.setGoogleUserEmail("");
//                            appPreferences.setGoogleAppId("");
////                        LoginManager.getInstance().logOut();
////                        googleSignInClient.signOut();
//                            startActivity(new Intent(HomeActivity.this, SignUp.class));
//                            finish();
//                        }
                        if (url.contains(".xls") || url.contains(".xlsx") || url.contains(".pdf") || url.contains(".doc")) {
                            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);
                        }
                        if (url.contains("GotoMobile")) {
                            Toast.makeText(HomeActivity.this, "Your login session has been expired, Please Login again.", Toast.LENGTH_SHORT).show();
                            ;
                            appPreferences.setStuID("");
                            appPreferences.setSessionCount("SendOtp");
                            Intent i = new Intent(HomeActivity.this, SendOTPMSG91.class);
                            startActivity(i);
                            finish();

                        }
                        if (url.contains("LoginVerified")) {

                            appPreferences.setSessionCount("LoginVerified");
                        }
//                        if(url.contains("socialconnection")){
//                            //dialog.dismiss();
//                            startActivity(new Intent(HomeActivity.this, SignUp.class));
//                            finish();
//                        }

                        if (url.contains("getLocation")) {
                            if (!appPreferences.getLocationPermissionDenied()) {
                                if (Build.VERSION.SDK_INT >= M) {
                                    if (permissionsToRequest.size() > 0) {
                                        requestPermissions(permissionsToRequest.toArray(
                                                new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                                    }
                                }
                            }
                            return true;
                        }

                        //LoadURL

                        if (!url.contains("getcontacts")) {
                            if (url.startsWith("tel:")) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                                //view.reload();
                                return true;
                            } else if (url.startsWith("fb") || url.startsWith("sms:") || url.startsWith("whatsapp:") || url.startsWith("maps:") || url.startsWith("geo:") || url.startsWith("skype:") || url.startsWith("mailto:")) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                //view.loadUrl(url);
                                return true;
                            } else if (url.contains("Goto=Chrome")) {
                                url = url.replace("&Goto=Chrome", "");
                                Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                                startActivity(browserIntent);
                                return true;
                            } else if (!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
                                if (!url.contains("opencontact")) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);
                                    finish();
                                }

                                //view.loadUrl(url);
                                return true;
                            }
                        }
//
                    }
//----Link End--///

                    view.loadUrl(url);
                    return true;
                    //return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                               SslError error) {
                    super.onReceivedSslError(view, handler, error);
                    handler.proceed();
                }

                public void shareImageWhatsApp(ArrayList<Uri> uri, String Mobile,String WhatsAppType) {

                    Intent share = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    share.setType("*/*");
                    //share.setData(Uri.parse("http://api.whatsapp.com/send?phone="+Mobile));
                    share.putExtra("jid", Mobile + "@s.whatsapp.net");
                    share.putParcelableArrayListExtra(Intent.EXTRA_STREAM,
                            uri);
                    String PackageName="com.whatsapp";
                    if(WhatsAppType.equals("Business")){
                        PackageName="com.whatsapp.w4b";
                    }
                    if (isPackageInstalled(PackageName, HomeActivity.this)) {
                        List<ResolveInfo> resInfoList = mActivity.getPackageManager().queryIntentActivities(share, PackageManager.MATCH_DEFAULT_ONLY);
                        for (ResolveInfo resolveInfo : resInfoList) {
                            for (Uri path : uri) {
                                mActivity.grantUriPermission(GRAPEVINE_FILE_PROVIDER_AUTHORITY, path, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            }

                        }
                        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        share.setPackage(PackageName);
                        startActivity(share);

                    } else {

                        Toast.makeText(getApplicationContext(), "Please Install Whatsapp", Toast.LENGTH_LONG).show();
                    }

                }

                private boolean isPackageInstalled(String packagename, Context context) {
                    PackageManager pm = context.getPackageManager();
                    try {
                        pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
                        return true;
                    } catch (PackageManager.NameNotFoundException e) {
                        return false;
                    }
                }

                private byte[] streamToBytes(InputStream is) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = -1;
                    try {
                        byte[] bytes = new byte[1024];
                        while ((i = is.read(bytes)) != -1) {
                            baos.write(bytes, 0, i);
                            bytes = new byte[1024];
                        }
                        return baos.toByteArray();
                    } catch (Exception e) {
                    }
                    return null;
                }

                private void UploadImagesToFtp(File imgFile, String FeedChannelID) {
                    String server = "36.255.3.139";
                    String user = "cdn.grapevine.work";
                    String password = "11BYRV^#C!@%^#R1";
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                    StrictMode.setThreadPolicy(policy);


                    String serverRoad = FeedChannelID;
                    // String filePath=FileUtil.getPath(this,fileUri);
                    int index = imgFile.getName().lastIndexOf("/");
                    String FileOriginalName = "";
                    String ImageFileLength = "";
//                    if(index!=-1) {
                    FileOriginalName = imgFile.getName().substring(index + 1);
                    //}
                    //File imgFile = new  File(filePath);
                    ImageFileLength = String.valueOf(imgFile.length());

                    String exten = getFileExtension(FileOriginalName);
                    exten = exten.replace(".", "");
                    String FeedObjectTypeID = "";
                    if (exten.equals("jpg") || exten.equals("jpeg") || exten.equals("png") || exten.equals("webp")) {

                        FeedObjectTypeID = "5";
                    } else if (exten.equals("mp4") || exten.equals("mkv") || exten.equals("gif") || exten.equals("3gp") || exten.equals("wmv") || exten.equals("webm")) {
                        FeedObjectTypeID = "6";
                    } else if (exten.equals("xlsx") || exten.equals("xls") || exten.equals("doc") || exten.equals("docx") || exten.equals("ppt") || exten.equals("pptx") || exten.equals("txt") || exten.equals("pdf")) {
                        FeedObjectTypeID = "10";

                    } else if (exten.equals("mp3") || exten.equals("m4a")) {
                        FeedObjectTypeID = "13";
                    }

                    try {
                        InputStream fileInputStream = new FileInputStream(imgFile);
                        byte[] bytes = streamToBytes(fileInputStream);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


                        Uri contentUri = Uri.parse(imgFile.getPath());
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        InputStream in = getContentResolver().openInputStream(contentUri);

                        FileOutputStream out = new FileOutputStream(imgFile.getPath());
                        Bitmap image = BitmapFactory.decodeStream(in, null, options);
                        image.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    FTPClient ftpClient = new FTPClient();
                    try {
                        ftpClient.connect(InetAddress.getByName(server));
                        ftpClient.login(user, password);

                        ftpClient.changeWorkingDirectory("/grapevine.work");
                        ftpClient.makeDirectory(serverRoad);
                        ftpClient.changeWorkingDirectory("/grapevine.work/" + serverRoad + "_Stickers");
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                        BufferedInputStream buffIn = null;
                        buffIn = new BufferedInputStream(new FileInputStream(imgFile));
                        ftpClient.enterLocalPassiveMode();
//            ftpClient.setPassiveLocalIPAddress("36.255.3.139");
//            ftpClient.noop();
                        ftpClient.storeFile(FileOriginalName, buffIn);
                        buffIn.close();
                        ftpClient.logout();
                        ftpClient.disconnect();
                        String url = "https://cdn.grapevine.work/grapevine.work/" + FeedChannelID + "_Stickers/" + FileOriginalName;
                        Insert_FeebObjects(FeedChannelID, url, ImageFileLength, FeedObjectTypeID);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                public void Insert_FeebObjects(String FeedChannelID, String url, String ImageFileLength, String FeedObjectTypeID) {

                    if (cd.isConnectingToInternet()) {


                        try {

                            final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                                public void processResponse(Object response) {
                                    if (response != null) {

                                        // Bookingdetails(listView);
                                    }
                                }
                            });
                            ContentValues values = new ContentValues();

                            List<NameValuePair> args = new ArrayList<>();
                            args.add(new BasicNameValuePair("FeedChannelID", FeedChannelID));
                            args.add(new BasicNameValuePair("url", url));
                            args.add(new BasicNameValuePair("ImageFileLength", ImageFileLength));
                            args.add(new BasicNameValuePair("FeedObjectTypeID", FeedObjectTypeID));

                            String URL = "https://www.grapevine.work/";
                            String methodName = "workplace/UpdateTempFile";

                            dataFromNetwork.setConfig(URL, methodName, args);
                            Object response = dataFromNetwork.execute().get().toString();
                            if (response != null) {
                                FeedChannelID = response.toString();
                                FeedChannelID = FeedChannelID.replaceAll("\"", "").replaceAll("\n", "");
                            }


                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                }


                private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
                    return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                            .setShowDialog(false)
                            .setScopes(new String[]{"user-read-email"})
                            .setCampaign("your-campaign-token")
                            .build();
                }

                private Uri getRedirectUri() {
                    return Uri.parse(REDIRECT_URI);
                }

                public void onPageFinished(WebView view, String url) {
                    CookieSyncManager.getInstance().sync();
                    findViewById(R.id.msw_welcome).setVisibility(View.GONE);
                    findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
//                    if(view!=null && view.isShown()){
//                        if(dialog.isShowing()){
//                            dialog.dismiss();
//                        }
//                    }
//                    if (null != mySwipeRefreshLayout) {
//                        mySwipeRefreshLayout.setRefreshing(false);
//                    }
                    super.onPageFinished(view, url);
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    //dialog.dismiss();
//                    Intent i = new Intent(HomeActivity.this, errorActivity.class);
//                    startActivity(i);
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }

                public boolean onConsoleMessage(ConsoleMessage cm) {

                    onConsoleMessage(cm.message(), cm.lineNumber(), cm.sourceId());
                    return true;
                }

                public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                    //Log.d("androidruntime", "Show console messages, Used for debugging: " + message);

                }


                private SharedPreferences appSettings;

                @RequiresApi(api = Build.VERSION_CODES.N_MR1)
                private void addShourcut(String url) {

                    ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
                    ShortcutInfo shortcut = new ShortcutInfo.Builder(getApplicationContext(), "second_shortcut")
                            .setShortLabel("GroupPage")
                            .setLongLabel("GroupPage")
                            .setIcon(Icon.createWithResource(getApplicationContext(), R.mipmap.ic_launcher))
                            .setIntent(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.google.co.in")))
                            .build();
                    shortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));
                    appSettings = getSharedPreferences("GrapeVine", MODE_PRIVATE);
                    if (!appSettings.getBoolean("shortcut", false)) {
                        String abc = "";
                    }
                    //appSettings = getSharedPreferences("APP_NAME", MODE_PRIVATE);
                    Intent shortcutIntent = new Intent(getApplicationContext(), HomeActivity.class);
                    shortcutIntent.setAction(Intent.ACTION_MAIN);

                    Intent addIntent = new Intent();
                    addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
                    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "GroupPage");
                    addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                            Intent.ShortcutIconResource.fromContext(getApplicationContext(),
                                    R.drawable.grapevinelogo));
                    addIntent.putExtra("duplicate", false);
                    addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                    getApplicationContext().sendBroadcast(addIntent);

                    SharedPreferences.Editor prefEditor = appSettings.edit();
                    prefEditor.putBoolean("shortcut", true);
                    prefEditor.commit();
//
//
//                    Intent shortCutIntent = new Intent(getApplicationContext() ,HomeActivity.class);
//
//                    shortCutIntent.setAction(Intent.ACTION_MAIN);
//
//                    Intent addIntent = new Intent();
//                    addIntent.putExtra("ShortCutUrl",url);
//                    addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT , shortCutIntent);
//                    addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME , "Grouppage");
//                    addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE ,
//                            Intent.ShortcutIconResource.fromContext(getApplicationContext() , R.mipmap.ic_launcher));
//                    addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
//                    addIntent.putExtra("duplicate" , false);
//                    getApplicationContext().sendBroadcast(addIntent);

                }

                public void calendarevent(Calendar begintime, Calendar endtime,
                                          String eventName) {
                    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
                    Calendar dt = Calendar.getInstance();

// Where untilDate is a date instance of your choice, for example 30/01/2012
                    dt.setTime(endtime.getTime());

// If you want the event until 30/01/2012, you must add one day from our day because UNTIL in RRule sets events before the last day
                    dt.add(Calendar.DATE, 1);
                    String dtUntill = yyyyMMdd.format(dt.getTime());
                    ContentResolver cr = getApplicationContext().getContentResolver();
                    ContentValues values = new ContentValues();

                    values.put(CalendarContract.Events.DTSTART, begintime.getTimeInMillis());
                    values.put(CalendarContract.Events.DTEND, endtime.getTimeInMillis());
                    values.put(CalendarContract.Events.TITLE, eventName);
                    values.put(CalendarContract.Events.DESCRIPTION, "XYZ");

                    TimeZone timeZone = TimeZone.getDefault();
                    values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

// Default calendar
                    values.put(CalendarContract.Events.CALENDAR_ID, 1);

                    values.put(CalendarContract.Events.RRULE, "FREQ=DAILY;UNTIL="
                            + dtUntill);
// Set Period for 1 Hour


                    values.put(CalendarContract.Events.HAS_ALARM, 1);

// Insert event to calendar
                    Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

//                    Intent intent = new Intent(Intent.ACTION_INSERT);
//                    intent.setType("vnd.android.cursor.item/event");
//
//                    Calendar cal = Calendar.getInstance();
//                    long startTime = begintime.getTimeInMillis();
//                    long endTime = endtime.getTimeInMillis();
//
//                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
//                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
//                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
//
//
//                    intent.putExtra(CalendarContract.Events.TITLE, eventName);
//                    intent.putExtra(CalendarContract.Events.DESCRIPTION,  "This is a sample description");
//                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "My Guest House");
//                    intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");
//                    intent.putExtra(CalendarContract.Events.ALLOWED_REMINDERS,1);
//                    intent.putExtra(CalendarContract.Events.MAX_REMINDERS,3);
//
//                    startActivity(intent);


                }



                @RequiresApi(api = Build.VERSION_CODES.N)
                public void GetAllContactList(GridView _ContactListItem) {
                    _ContactList.clear();
//ArrayList<ContactList> _ContactList=new ArrayList<ContactList>();
                    Cursor _cursor = null;
                    ContentResolver _contentresolver = getContentResolver();
                    try {
                        _cursor = _contentresolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                    } catch (Exception ex) {
                        Log.e("Error on contact", ex.getMessage());
                    }
                    if (_cursor.getCount() > 0) {
                        while (_cursor.moveToNext()) {
                            ContactList _List = new ContactList();
                            String Contact_id = _cursor.getString(_cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            String Contact_Name = _cursor.getString(_cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            _List.ContactName = Contact_Name;
                            String Email = "";
                            Cursor emailCur = _contentresolver.query(
                                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{Contact_id}, null);
                            while (emailCur.moveToNext()) {
                                Email = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                _List.ContactEmail = Email;


                            }

                            int hasPhoneNumber = Integer.parseInt(_cursor.getString(_cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                            if (hasPhoneNumber > 0) {
                                Cursor _cursornumber = _contentresolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                                        , null
                                        , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                                        , new String[]{Contact_id}, null);
                                while (_cursornumber.moveToNext()) {
                                    String PhoneNumber = _cursornumber.getString(_cursornumber.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    _List.ContactNo = PhoneNumber;
                                }
                                _cursornumber.close();

                            }
                            int i = _ContactList.indexOf(_List.ContactNo);
                            if (i == -1) {
                                _ContactList.add(_List);
                            }


                        }
                        Collections.sort(_ContactList, new Comparator<ContactList>() {
                            public int compare(ContactList p1, ContactList p2) {
                                return p1.ContactName.compareTo(p2.ContactName);
                            }
                        });
//
                        Adapter_For_Contacts _Contacts = new Adapter_For_Contacts(getApplicationContext(), _ContactList, 0);
                        _ContactListItem.setAdapter(_Contacts);
                        _Contacts.notifyDataSetChanged();
                    }

                }


            });
            webView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {




                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    DownloadManager   downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//                    String fileName=URLUtil.guessFileName(url, contentDisposition, mimeType);
//                    long downloadReference = 0;
//                    try {
//
//
//                        //DownloadManager.Request request = new DownloadManager.Request(uri);
//
//                        //Setting title of request
//                        request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
//
//                        //Setting description of request
//                        request.setDescription("Your file is downloading");
//
//                        //set notification when download completed
//                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//
//                        //Set the local destination for the downloaded file to a path within the application's external files directory
//                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//
//                        request.allowScanningByMediaScanner();
//
//                        //Enqueue download and save the referenceId
//                        downloadReference = downloadManager.enqueue(request);
//                    } catch (IllegalArgumentException | MalformedURLException e) {
//                        Toast.makeText(getApplicationContext(), "Download link is broken or not availale for download", Toast.LENGTH_LONG).show();
//                        //BaseUtils.showToast(mContext, "Download link is broken or not availale for download");
//                        Log.e(TAG, "Line no: 455,Method: downloadFile: Download link is broken");
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

//                    request.setMimeType(mimeType);
//                    request.addRequestHeader("User-Agent", userAgent);
//                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "name of your file");
//                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//                    request.allowScanningByMediaScanner();
//                    dm.enqueue(request);

                    request.setMimeType(mimeType);
                    //------------------------COOKIE!!------------------------
                    String cookies = CookieManager.getInstance().getCookie(url);
                    request.addRequestHeader("cookie", cookies);
                    //------------------------COOKIE!!------------------------
                    request.addRequestHeader("User-Agent", userAgent);
                    request.setDescription("Downloading file...");
                    request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();


                }
            });
        } else {
            Intent i = new Intent(HomeActivity.this, noConnection.class);
            startActivity(i);
            finish();
        }

        // ATTENTION: This was auto-generated to handle app links.

    }

    public void CreateUserContact(JSONObject object){
        String name="";
        String mobile="";
        String email="";
        try {
            name = object.getString("full_name");
            mobile = object.getString("mobile");
            email = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int contactID= contactExists(this,mobile);
        if(contactID == 0) {


            ContentProviderResult[] results = null;
            ArrayList<ContentProviderOperation> contact = new ArrayList<ContentProviderOperation>();
            contact.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            // first and last names
            contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)

                    .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, name)
                    .build());

            // Contact No Mobile
            contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobile)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
            // Contact No Email
            contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());


            try {
                results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, contact);
                Uri myContactUri = results[0].uri;
                int lastSlash = myContactUri.toString().lastIndexOf("/");
                int length = myContactUri.toString().length();
                contactID = Integer.parseInt((String) myContactUri.toString().subSequence(lastSlash + 1, length));



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String encodePath(String path) {
        char[] chars = path.toCharArray();

        boolean needed = false;
        for (char c : chars) {
            if (c == '[' || c == ']' || c == '|') {
                needed = true;
                break;
            }
        }
        if (needed == false) {
            return path;
        }

        StringBuilder sb = new StringBuilder("");
        for (char c : chars) {
            if (c == '[' || c == ']' || c == '|') {
                sb.append('%');
                sb.append(Integer.toHexString(c));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
    @Override
    public void onPictureInPictureModeChanged (boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (isInPictureInPictureMode) {
            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
        } else {
            // Restore the full-screen UI.

        }
        IS_ENABLE_PIP_MODE = isInPictureInPictureMode;
    }
    public String SendInvitation(String Mobile, String ApplicantID, String Email, String UserName) {
        String Apl_ID = "";
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("MobileNo", Mobile));
                args.add(new BasicNameValuePair("ApplicantID", ApplicantID));
                args.add(new BasicNameValuePair("Email", Email));
                args.add(new BasicNameValuePair("UserName", UserName));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/SendInvitation";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    Apl_ID = response.toString();
                    Apl_ID = Apl_ID.replaceAll("\"", "").replaceAll("\n", "");
                    String ContactID=appPreferences.getApplicant_id();
                    JSONObject object=GetloginDetailsOverContactID(ContactID);

                    String name="";
                    try {
                        name = object.getString("name");

                        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                        smsIntent.setType("vnd.android-dir/mms-sms");
                        smsIntent.putExtra("address",Mobile);
                        smsIntent.putExtra("sms_body",name+" has invited you to join Grapevine, please click on link below.\n http://play.google.com/store/apps/details?id=com.oxyzenhomes.grapevine");
                        startActivity(smsIntent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return Apl_ID;
    }
    private void Get_SHA_KEY_BASE64(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {

        }
        catch (NoSuchAlgorithmException e) {

        }
    }
    private void requestSignIn() {
        Log.d(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE)

                        .build();
//        (GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        //.requestScopes(new Scope("https://www.googleapis.com/auth/drive"))
//                        .requestScopes(Drive.SCOPE_FILE)
////                        .requestScopes(Drive.SCOPE_APPFOLDER)
//                        .requestEmail()
//                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
//                        .build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(client.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    private void initialize() {

        requiredScopes = new HashSet<>(2);

        requiredScopes.add(Drive.SCOPE_FILE);
        requiredScopes.add(Drive.SCOPE_APPFOLDER);
        String[] documentMimeTypes= {
                "application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"};
        openOptions = new OpenFileActivityOptions.Builder()
                .setSelectionFilter(Filters.eq(SearchableField.MIME_TYPE, "application/pdf"))
                .setActivityTitle("Select file")
                .build();
    }
//
    private void signIn() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope("https://www.googleapis.com/auth/drive"))
                .requestScopes(Drive.SCOPE_FILE)
                .requestScopes(Drive.SCOPE_APPFOLDER)
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, signInOptions);
        startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    private void onDriveClientReady() {
        //initialize();
        mOpenItemTaskSource = new TaskCompletionSource<>();
        mDriveClient.newOpenFileActivityIntentSender(openOptions)
                .continueWith(new Continuation<IntentSender, Void>() {
                    @Override
                    public Void then(@NonNull Task<IntentSender> task) throws Exception {

                        startIntentSenderForResult(
                                task.getResult(), REQUEST_CODE_OPEN_ITEM, null, 0, 0, 0);
                        return null;
                    }
                });

        Task<DriveId> tasks = mOpenItemTaskSource.getTask();
        tasks.addOnSuccessListener(this,
                new OnSuccessListener<DriveId>() {
                    @Override
                    public void onSuccess(DriveId driveId) {
                        retrieveContents(driveId.asDriveFile());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("File not selected.");
                    }
                });
    }

    private void retrieveContents(final DriveFile file) {
        // [START open_file]
        final Task<DriveContents> openFileTask = mDriveResourceClient.openFile(file, DriveFile.MODE_READ_ONLY);
        // [END open_file]
        // [START read_contents]


        openFileTask.continueWithTask(new Continuation<DriveContents, Task<Void>>() {
            @Override
            public Task<Void> then(@NonNull Task<DriveContents> task) throws Exception {
                DriveContents contents = task.getResult();

                Log.v(TAG, "File name : " + contents.toString());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    InputStream input = contents.getInputStream();

                    try {
                        File file = new File(getExternalFilesDir(null), "umesh.pdf");
                        Log.v(TAG, storageDir + "");
                        OutputStream output = new FileOutputStream(file);
                        try {
                            try {
                                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                                int read;

                                while ((read = input.read(buffer)) != -1) {
                                    output.write(buffer, 0, read);
                                }
                                output.flush();
                            } finally {
                                output.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            input.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                showMessage("Download file successfully.");
                return mDriveResourceClient.discardContents(contents);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showMessage("Unable to download file.");
                    }
                });
        // [END read_contents]
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    private void requestPermission() {
        String dirPath = getFilesDir().getAbsolutePath() + File.separator + "PDF";
        storageDir = new File(dirPath);
        if (!storageDir.exists())
            storageDir.mkdirs();}

    private void initializeDriveClient(GoogleSignInAccount signInAccount) {
        mDriveClient =  Drive.getDriveClient(getApplicationContext(), signInAccount);
        mDriveResourceClient = Drive.getDriveResourceClient(getApplicationContext(), signInAccount);
    }
    public void CreateContactFolder(String FeedChannelID,String LeadThreadID,String Associate_ID,String ProjectName,String CreateType){
        // Check the Group is available or not

        Cursor groupCursor = null;
        if(ProjectName.equals("")){
            ProjectName="Grapevine-CRM";
        }
        String[] GROUP_PROJECTION = new String[] { ContactsContract.Groups._ID,     ContactsContract.Groups.TITLE };
        groupCursor = this.getContentResolver().query(ContactsContract.Groups.CONTENT_URI,

                GROUP_PROJECTION, ContactsContract.Groups.TITLE+ "=?", new String[]{ProjectName}, ContactsContract.Groups.TITLE + " ASC");
        Log.d("*** Here Counts: ","** "+groupCursor.getCount());
        Boolean isCreated=false;
        if(groupCursor.getCount() > 0){
            //Toast.makeText(HomeActivity.this, "Available", Toast.LENGTH_SHORT).show();
            isCreated=true;
            String groupID = null;
            Cursor getGroupID_Cursor = null;
            getGroupID_Cursor = this.getContentResolver().query(ContactsContract.Groups.CONTENT_URI,
                    GROUP_PROJECTION, ContactsContract.Groups.TITLE+ "=?", new String[]{ProjectName}, null);
            Log.d("Now Empty Cursor size:","** "+getGroupID_Cursor.getCount());
            getGroupID_Cursor.moveToFirst();
            groupID = (getGroupID_Cursor.getString(getGroupID_Cursor.getColumnIndex("_id")));
            Log.d(" **** Group ID is: ","** "+groupID);

            getGroupID_Cursor.close();
            getGroupID_Cursor = null;
            Long obj = new Long(groupID);
            if(!FeedChannelID.equals("")){
                JSONObject object= Get_User_DetailOverFeedChannelID(FeedChannelID);
                //Toast.makeText(HomeActivity.this, "Creating Contact", Toast.LENGTH_SHORT).show();
                addContact( obj.longValue(),object,LeadThreadID,Associate_ID,CreateType);
            }

//                    Toast.makeText(HomeActivity.this, " Added Successfully",   Toast.LENGTH_SHORT).show();
            groupCursor.close();
            groupCursor=null;
            return;
        }
        else {
            isCreated=false;
            //Toast.makeText(HomeActivity.this, "Not available", Toast.LENGTH_SHORT).show();

            //Here we create a new Group
            try {
                ContentValues groupValues = null;
                ContentResolver cr = this.getContentResolver();
                groupValues = new ContentValues();
                groupValues.put(ContactsContract.Groups.TITLE, ProjectName);
                cr.insert(ContactsContract.Groups.CONTENT_URI, groupValues);
                Log.d("Group Creation Finished","###### Success");
            }
            catch(Exception e){
                Log.d("########### Exception :",""+e.getMessage());
            }
            //Toast.makeText(HomeActivity.this, "Created Successfully",    Toast.LENGTH_SHORT).show();
        }
        groupCursor.close();
        groupCursor=null;
        if(!isCreated && !ProjectName.equals("")){
            CreateContactFolder(FeedChannelID,LeadThreadID,Associate_ID,ProjectName,CreateType);
        }
    }
    public int contactExists(Context context, String number) {
        /// number is the phone number
        boolean result=false;
        int ContactID=0;
        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
        Cursor cur = context.getContentResolver().query(lookupUri,mPhoneNumberProjection, null, null, null);

        try {
            if (cur.moveToFirst()) {
                String contactName = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.PhoneLookup.DISPLAY_NAME));
                ContactID= Integer.parseInt(cur.getString(cur.getColumnIndexOrThrow(ContactsContract.PhoneLookup._ID)));

            }
        }
        finally {
            if (cur != null){
                cur.close();
            }
            return ContactID;
        }
    }
    private void addContact(long GroupID,JSONObject object,String LeadThreadID,String Associate_ID,String CreateType) {
        String name="";
        String mobile="";
        String email="";
        try {
            name = object.getString("full_name");
            mobile = object.getString("mobile");
            email = object.getString("email");
//            Toast.makeText(HomeActivity.this, "Contact Name:-"+name, Toast.LENGTH_SHORT).show();
//            Toast.makeText(HomeActivity.this, "Contact mobile:-"+mobile, Toast.LENGTH_SHORT).show();
            //Toast.makeText(HomeActivity.this, "Contact email:-"+email, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
       int contactID= contactExists(this,mobile);
        //Toast.makeText(HomeActivity.this, "Contact ID:-"+contactID, Toast.LENGTH_SHORT).show();
        if(contactID == 0) {


            ContentProviderResult[] results = null;
            ArrayList<ContentProviderOperation> contact = new ArrayList<ContentProviderOperation>();
            contact.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            // first and last names
            contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)

                    .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, name)
                    .build());

            // Contact No Mobile
            contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobile)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
            // Contact No Email
            contact.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());


            try {
                results = getContentResolver().applyBatch(ContactsContract.AUTHORITY, contact);
                Uri myContactUri = results[0].uri;
                int lastSlash = myContactUri.toString().lastIndexOf("/");
                int length = myContactUri.toString().length();
                 contactID = Integer.parseInt((String) myContactUri.toString().subSequence(lastSlash + 1, length));
                //Toast.makeText(HomeActivity.this, "Contact Created", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        addToGroup(Long.parseLong(String.valueOf(contactID)), GroupID);

        if (!CreateType.equals("Share")) {
            //String MyMobile=GetUserMobile();

            String MyMobile = appPreferences.getLoginUserMobileNo();
            if (MyMobile == null || MyMobile.equals("")) {
                JSONObject obj = GetloginDetailsOverContactID(appPreferences.getApplicant_id());
                try {
                    if (obj != null) {
                        String LoginUserMobileNo = obj.getString("mobile");
                        appPreferences.setLoginUserMobileNo(LoginUserMobileNo);
                        MyMobile = LoginUserMobileNo;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //MyMobile="8452937372";
            String LeadThreadID1 = crm_feed_Get_leadthread_from_mobile(MyMobile, mobile);
            if (!LeadThreadID.equals("")) {
                LeadThreadID1 = LeadThreadID;
            }
            if (!LeadThreadID1.equals("")) {
                String AssociateID = appPreferences.getApplicant_id();
                String ActivityThreadID = Crm_Add_Activity(LeadThreadID1, "", "AST-2", "CHN-1",
                        "Outgoing Call", "", "", AssociateID, "", "",
                        "", "", "");
                Crm_Add_Activity(LeadThreadID, ActivityThreadID, "", "CHN-1",
                        "Outgoing Call", "", "", AssociateID, "", "",
                        "", "", "");
            }

        }
    }

    public Uri addToGroup(long personId, long groupId) {

        ContentValues values = new ContentValues();
        values.put(ContactsContract.CommonDataKinds.GroupMembership.RAW_CONTACT_ID,
                personId);
        values.put(
                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
                groupId);
        values
                .put(
                        ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
                        ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);

        return getApplicationContext().getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

    }
    //    public Uri addToGroup(long groupId,String Name,String Number) {
//
//        ContentValues values = new ContentValues();
//        values.put(ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME,
//                Name);
//        values.put(ContactsContract.CommonDataKinds.GroupMembership.HAS_PHONE_NUMBER,
//                Number);
//        values.put(
//                ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID,
//                groupId);
//        values
//                .put(
//                        ContactsContract.CommonDataKinds.GroupMembership.MIMETYPE,
//                        ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
//
//        return getApplicationContext().getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
//
//    }
    public void OnOffSpeaker(String Mode) {
        final Context context = webView.getContext();
         AudioManager mAudioMgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if(Mode.equals("speaker")){
            //mAudioMgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
            mAudioMgr.setWiredHeadsetOn(false);
            mAudioMgr.setSpeakerphoneOn(true);
            mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);

            //Toast.makeText(getApplicationContext(), "SpeakerPhone On", Toast.LENGTH_LONG).show();
        }else{

            mAudioMgr.setMode(AudioManager.MODE_IN_COMMUNICATION);
//            boolean useHeadphone = mAudioMgr.isWiredHeadsetOn();
//
//            mAudioMgr.setSpeakerphoneOn(!useHeadphone);


            mAudioMgr.setSpeakerphoneOn(false);
            mAudioMgr.setWiredHeadsetOn(true);
            //Toast.makeText(getApplicationContext(), "Wired Headset On", Toast.LENGTH_LONG).show();
        }

        //boolean useHeadphone = am.isWiredHeadsetOn();
//        if (Mode.equals("earpiece")) {
//            am.setMode(AudioManager.MODE_IN_COMMUNICATION);
//            //am.stopBluetoothSco();
//            //am.setBluetoothScoOn(false);
//            am.setSpeakerphoneOn(false);
//        } else if (Mode.equals("speaker")) {
//            am.setMode(AudioManager.MODE_IN_COMMUNICATION);
//            //am.stopBluetoothSco();
//            //am.setBluetoothScoOn(false);
//            am.setSpeakerphoneOn(true);
//
//        }
    }

    public JSONObject GetloginDetailsOverContactID(String ContactID) {

        JSONObject jsonobject=null;
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values = new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("ssn", ContactID));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/GetloginDetailsOverContactID";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();

                 jsonobject = new JSONObject(response.toString());

//                String ActivityDetails = jsonobject.getString("ActivityDetails");
//                if (response != null) {
//                    FeedChannelID = response.toString();
//                    FeedChannelID = FeedChannelID.replaceAll("\"", "").replaceAll("\n", "");
//                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return jsonobject;
    }

    public String GetFeedChannelID(String ContactID) {
        String FeedChannelID = "";
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values = new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("ssn", ContactID));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/GetFeedChannelID";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();

                if (response != null) {
                    FeedChannelID = response.toString();
                    FeedChannelID = FeedChannelID.replaceAll("\"", "").replaceAll("\n", "");
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return FeedChannelID;
    }

    public String GetDeviceipMobileData() {
        try {
            for (java.util.Enumeration<java.net.NetworkInterface> en = java.net.NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                java.net.NetworkInterface networkinterface = en.nextElement();
                for (java.util.Enumeration<java.net.InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    java.net.InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }


    public String GetDeviceipWiFiData() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        @SuppressWarnings("deprecation")
        String ip = android.text.format.Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    public static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info.getTypeName();
    }

    public class CalendarItem {
        private int id;
        private String name;


        public void setId(int anInt) {

        }

        public void setName(String string) {
        }
    }

    public List<CalendarItem> getCalendars(Context ctx) {
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        List<CalendarItem> result = new ArrayList();

        final String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.NAME
        };

        final ContentResolver cr = ctx.getContentResolver();
        final Uri uri = CalendarContract.Calendars.CONTENT_URI;
        Cursor cur = cr.query(uri, EVENT_PROJECTION, null, null, null);

        while (cur.moveToNext()) {
            CalendarItem item = new CalendarItem();
            item.id = cur.getInt(cur.getColumnIndex(CalendarContract.Calendars._ID));
            item.name = cur.getString(cur.getColumnIndex(CalendarContract.Calendars.NAME));
            result.add(item);
        }
        cur.close();
        return result;
    }

    //user calendar name == email
    private boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) return false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    private String getCalendarUriBase(Activity act) {
        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = act.managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
        }
        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = act.managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }
        }
        return calendarUriBase;
    }

    public void GetAllActivities(String FeedChannelID, String FeedID, String ActivityTypeID, String ActivityID) {
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("FeedChannelID", FeedChannelID));
                args.add(new BasicNameValuePair("FeedID", FeedID));
                args.add(new BasicNameValuePair("ActivityTypeID", ActivityTypeID));
                args.add(new BasicNameValuePair("ActivityID", ActivityID));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/GetAllActivities";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();

                JSONArray object = null;
                if (response != null) {

                    object = new JSONArray(response.toString());
                    for (int i = 0; i < object.length(); i++) {
                        JSONObject jsonobject = object.getJSONObject(i);
                        String ActivityDetails = jsonobject.getString("ActivityDetails");
                        String ActivityDescription = jsonobject.getString("ActivityDescription");
                        String StartDate = jsonobject.getString("ScheduleDateTimeFormatforCalendar");
                        String EndDate = jsonobject.getString("ExpiryDateTimeFormatforCalendar");
                        String Cohost = jsonobject.getString("_EventCo_Hosts");
                        JSONArray _EventCo_Hosts = new JSONArray(jsonobject.getString("_EventCo_Hosts"));
                        addEventToCalender(ActivityDetails, ActivityDescription, StartDate, EndDate, _EventCo_Hosts);
                    }
                    String Result = response.toString();
                    //Bookingdetails(listView);
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void addEventToCalender(String ActivityDetails, String ActivityDescription, String StartDate, String EndDate, JSONArray _EventCo_Hosts) {

        DateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy, h:mm a");
        String dateStarts = StartDate;
        String dateEnds = EndDate;
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(HomeActivity.this) + "events");
        ContentResolver cr = this.getContentResolver();
        // event insert
        ContentValues values = new ContentValues();
        Date startDate = null, endDate = null;
        try {
            startDate = formatter.parse(dateStarts);
            if(!dateEnds.equals("")){
                endDate = formatter.parse(dateEnds);
            }
            else{
                endDate=startDate;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        values.put("calendar_id", 1);
        values.put("title", ActivityDetails);
        values.put("allDay", 0);
        values.put("dtstart", startDate.getTime());
        if(endDate!=null){
            values.put("dtend", endDate.getTime());
        }
        values.put("description", ActivityDescription);

//        values.put("visibility", 0);
        values.put("hasAlarm", 1);// now
        try {
            //listCalendarId();

            Uri eventUri = cr.insert(EVENTS_URI, values);

//            Uri newEvent ;
//            if (Integer.parseInt(Build.VERSION.SDK) >= 8 )
//                newEvent = cr.insert(Uri.parse("content://com.android.calendar/events"), values);
//            else
//                newEvent = cr.insert(Uri.parse("content://calendar/events"), values);

            long id = Long.parseLong( eventUri.getLastPathSegment() );
            if (eventUri != null) {

                values = new ContentValues();
                values.put( "event_id", id );
                values.put( "method", 1 );
                values.put( "minutes", 15 ); // 15 minutes
                if (Integer.parseInt(Build.VERSION.SDK) >= 8 )
                    cr.insert( Uri.parse( "content://com.android.calendar/reminders" ), values );
                else
                    cr.insert( Uri.parse( "content://calendar/reminders" ), values );

            }



            Toast.makeText(getApplicationContext(), "Activity is added to the calendar.", Toast.LENGTH_SHORT).show();
//            Long eventID = Long.parseLong(eventUri.getLastPathSegment());
//            // reminder insert
//            Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(HomeActivity.this)
//                    + "reminders");
//            values = new ContentValues();
//            values.put(CalendarContract.Reminders.MINUTES, 10);
//            values.put(CalendarContract.Reminders.EVENT_ID, 1);
//            values.put(CalendarContract.Reminders.METHOD,
//
//                    CalendarContract.Reminders.METHOD_ALERT);
//            //values.put(CalendarContract.Events.CALENDAR_ID, calendarId);
//            Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);

//            values.put("event_id", 1);
//            values.put("method", 1);
//            values.put("minutes", 10);
//            cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
            if (_EventCo_Hosts !=null && _EventCo_Hosts.length() > 0) {
                Uri attendees_URI = Uri.parse(getCalendarUriBase(HomeActivity.this)
                        + "attendees");
                for (int i = 0; i < _EventCo_Hosts.length(); i++) {
                    JSONObject jsonobject = _EventCo_Hosts.getJSONObject(i);
                    String FeedChannelTitle = jsonobject.getString("FeedChannelTitle");
                    String Email = jsonobject.getString("Email");
                    values = new ContentValues();

                    values.put(CalendarContract.Attendees.ATTENDEE_NAME, FeedChannelTitle);
                    values.put(CalendarContract.Attendees.ATTENDEE_EMAIL, Email);
                    values.put(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, CalendarContract.Attendees.RELATIONSHIP_ATTENDEE);
                    values.put(CalendarContract.Attendees.ATTENDEE_TYPE, CalendarContract.Attendees.TYPE_OPTIONAL);
                    values.put(CalendarContract.Attendees.ATTENDEE_STATUS, CalendarContract.Attendees.ATTENDEE_STATUS_INVITED);
                    values.put(CalendarContract.Attendees.EVENT_ID, id);

                    if (Integer.parseInt(Build.VERSION.SDK) >= 8 )
                        cr.insert( Uri.parse( "content://com.android.calendar/attendees" ), values );
                    else
                        cr.insert( Uri.parse( "content://calendar/attendees" ), values );

                    //cr.insert(attendees_URI, values);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Hashtable listCalendarId() {

        try {


                String projection[] = {"_id", "calendar_displayName"};
                Uri calendars;
                calendars = Uri.parse("content://com.android.calendar/calendars");

                ContentResolver contentResolver = this.getContentResolver();
                Cursor managedCursor = contentResolver.query(calendars, projection, null, null, null);

                if (managedCursor.moveToFirst()) {
                    String calName;
                    String calID;
                    int cont = 0;
                    int nameCol = managedCursor.getColumnIndex(projection[1]);
                    int idCol = managedCursor.getColumnIndex(projection[0]);
                    Hashtable<String, String> calendarIdTable = new Hashtable<>();
                    do {
                        calName = managedCursor.getString(nameCol);
                        calID = managedCursor.getString(idCol);
                        Log.v(TAG, "CalendarName:" + calName + " ,id:" + calID);
                        calendarIdTable.put(calName, calID);
                        cont++;
                    } while (managedCursor.moveToNext());

                    managedCursor.close();

                    return calendarIdTable;
                }

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

    public void pull_fresh() {
        webView.reload();
    }

    UploadHandler mUploadHandler;

    private final static Object methodInvoke(Object obj, String method, Class<?>[] parameterTypes, Object[] args) {
        try {
            Method m = obj.getClass().getMethod(method, new Class[]{boolean.class});
            m.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String fileExt(String url) {
        if (url.indexOf("?") > -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (url.lastIndexOf(".") == -1) {
            return null;
        } else {
            String ext = url.substring(url.lastIndexOf(".") + 1);
            if (ext.indexOf("%") > -1) {
                ext = ext.substring(0, ext.indexOf("%"));
            }
            if (ext.indexOf("/") > -1) {
                ext = ext.substring(0, ext.indexOf("/"));
            }
            return ext.toLowerCase();

        }
    }


    private File file;
    String directory = "";

    public File downloadfile(String data) {
        try {


            String extstoragedir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Grapevine";
            File fol = new File(extstoragedir);

            if (!fol.exists()) {
                fol.mkdir();
            }


            String filename = data;
            String[] sn = filename.split("/");
            file = new File(fol, sn[sn.length - 1]);
            if (!file.exists()) {
                file.createNewFile();
                fOut = new FileOutputStream(file);
                directory = filename;
                new DownloadFileFromURL().execute(filename).get();
                Toast.makeText(HomeActivity.this, "File Downloaded.", Toast.LENGTH_SHORT).show();
                //new FtpTask().execute().get().toString();
            }


        } catch (IOException e) {
            Toast.makeText(this, "Please give the permission for creating PDF..", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }
        return file;
    }

    private FileOutputStream fOut;
    public static final int progress_bar_type = 0;
    private ProgressDialog pDialog;

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

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    fOut.write(data, 0, count);
                }

                fOut.flush();

                fOut.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return result;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }


        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(String file_url) {
            dismissDialog(progress_bar_type);


        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }

    public static class FileOpen {

        public static void openFile(Context context, File url) throws IOException {
            // Create URI
            File file = url;
            Uri uri = Uri.fromFile(file);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if (url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if (url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    class Adapter_For_Contacts extends ArrayAdapter implements CompoundButton.OnCheckedChangeListener {
        private SparseBooleanArray mCheckStates;
        LayoutInflater mInflater;
        ImageView imageview1, imageview;
        CheckBox cb;
        List<ContactList> mList_Contacts;
        Context mcontext;
        int Flag;

        public Adapter_For_Contacts(Context context, List<ContactList> mList_Contacts, int flag) {

            super(context, 0);

            this.mList_Contacts = mList_Contacts;
            this.mcontext = context;
            this.Flag = flag;
            mCheckStates = new SparseBooleanArray(mList_Contacts.size());


        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mList_Contacts.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub

            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub


            View listViewItem = null;
            if (convertView == null) {
                mInflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                listViewItem = mInflater.inflate(R.layout.item_contact_list, null, true);
            } else {
                listViewItem = convertView;
            }
            final TextView checkBox2 = (TextView) listViewItem.findViewById(R.id.items_contacts);
            CheckBox contacts = (CheckBox) listViewItem.findViewById(R.id.contacts);
            checkBox2.setText(mList_Contacts.get(position).ContactName);
            contacts.setContentDescription(mList_Contacts.get(position).ContactName);


            contacts.setTag(position);
            contacts.setChecked(mCheckStates.get(position, false));
            contacts.setOnCheckedChangeListener(this);
            contacts.setContentDescription(mList_Contacts.get(position).ContactName);
//            contacts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if(isChecked){
//
//
//                        String Mobile=mList_Contacts.get(position).ContactNo;
//                        String Name=mList_Contacts.get(position).ContactName;
//                        String Email=mList_Contacts.get(position).ContactEmail;
//                        String Recorduser=Mobile+"-"+Name+"-"+Email;
//                        String AllMobile=appPreferences.getIniviteUserRecord();
//                        if(AllMobile.equals("")){
//                            appPreferences.setIniviteUserRecord(Recorduser);
//                        }
//                        else
//                        {
//                            AllMobile=AllMobile+","+Recorduser;
//                            appPreferences.setIniviteUserRecord(AllMobile);
//                        }
//
////                        }
//
//                    }else
//                    {
//                        String Mobile=mList_Contacts.get(position).ContactNo;
//                        String Name=mList_Contacts.get(position).ContactName;
//                        String Email=mList_Contacts.get(position).ContactEmail;
//                        String Recorduser=Mobile+"-"+Name+"-"+Email;
//                        String AllMobile=appPreferences.getIniviteUserRecord();
//                        AllMobile=AllMobile.replace(","+Recorduser,"");
//                        appPreferences.setIniviteUserRecord(AllMobile);
////
//                    }
//                }
//            });
            if (Flag == 1) {
                contacts.setChecked(true);
            } else {
                contacts.setChecked(false);
            }
            return listViewItem;
        }

        public boolean isChecked(int position) {
            return mCheckStates.get(position, false);
        }

        public void setChecked(int position, boolean isChecked) {
            mCheckStates.put(position, isChecked);

        }

        public void toggle(int position) {
            setChecked(position, !isChecked(position));
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub
            int position = (Integer) buttonView.getTag();
            mCheckStates.put((Integer) buttonView.getTag(), isChecked);
            if (isChecked) {
                String Mobile = mList_Contacts.get(position).ContactNo;
                String Name = mList_Contacts.get(position).ContactName;
                String Email = mList_Contacts.get(position).ContactEmail;
                String Recorduser = Mobile + "-" + Name + "-" + Email;
                String AllMobile = appPreferences.getIniviteUserRecord();
                if (AllMobile.equals("")) {
                    appPreferences.setIniviteUserRecord(Recorduser);
                } else {
                    AllMobile = AllMobile + "," + Recorduser;
                    appPreferences.setIniviteUserRecord(AllMobile);
                }

//                        }

            } else {
                String Mobile = mList_Contacts.get(position).ContactNo;
                String Name = mList_Contacts.get(position).ContactName;
                String Email = mList_Contacts.get(position).ContactEmail;
                String Recorduser = Mobile + "-" + Name + "-" + Email;
                String AllMobile = appPreferences.getIniviteUserRecord();
                AllMobile = AllMobile.replace("," + Recorduser, "");
                appPreferences.setIniviteUserRecord(AllMobile);
//
            }

        }

    }

    //    public class Adapter_For_Contacts extends BaseAdapter{
//Context mContext;
//List<ContactList> mList_Contacts;
//public Adapter_For_Contacts(Context mContext,List<ContactList> mList_Contacts){
//
//    this.mContext=mContext;
//    this.mList_Contacts=mList_Contacts;
//
//
//
//}
//
//        @Override
//        public int getCount() {
//            return mList_Contacts.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            final View listViewItem = inflater.inflate(R.layout.item_contact_list, null, true);
//            final TextView checkBox2 = (TextView) listViewItem.findViewById(R.id.items_contacts);
//            CheckBox contacts=(CheckBox)listViewItem.findViewById(R.id.contacts);
//            checkBox2.setText(mList_Contacts.get(position).ContactName);
//            contacts.setContentDescription(mList_Contacts.get(position).ContactName);
//            contacts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if(isChecked){
//
//                        contacts.setChecked(true);
//                        String Mobile=mList_Contacts.get(position).ContactNo;
//                        String Name=mList_Contacts.get(position).ContactName;
//                        String Email=mList_Contacts.get(position).ContactEmail;
//                        String Recorduser=Mobile+"-"+Name+"-"+Email;
//                        String AllMobile=appPreferences.getIniviteUserRecord();
//                        if(AllMobile.equals("")){
//                            appPreferences.setIniviteUserRecord(Recorduser);
//                        }
//                        else
//                        {
//                            AllMobile=AllMobile+","+Recorduser;
//                            appPreferences.setIniviteUserRecord(AllMobile);
//                        }
//
////                        }
//
//                    }else
//                    {
//                        String Mobile=mList_Contacts.get(position).ContactNo;
//                        String Name=mList_Contacts.get(position).ContactName;
//                        String Email=mList_Contacts.get(position).ContactEmail;
//                        String Recorduser=Mobile+"-"+Name+"-"+Email;
//                        String AllMobile=appPreferences.getIniviteUserRecord();
//                        AllMobile=AllMobile.replace(","+Recorduser,"");
//                        appPreferences.setIniviteUserRecord(AllMobile);
////
//                    }
//                }
//            });
//
//            return listViewItem;
//        }
//    }
//
//
    @Override
    protected void onStart() {
        super.onStart();
        appPreferences.setUserPresenceState("Live");
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    //
    @Override
    protected void onResume() {
        super.onResume();
        appPreferences.setUserPresenceState("Live");
        if (googleApiClient != null) {
            googleApiClient.connect();
        }

    }

    //
    @Override
    protected void onPause() {
        super.onPause();
        try {
            appPreferences.setUserPresenceState("NotLive");
            if (googleApiClient != null && googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (LocationListener) this);
                googleApiClient.disconnect();
            }
        } catch (Exception ex) {

        }
        // stop location updates

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);
            } else {
                finish();
            }

            return false;
        }

        return true;
    }


    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            if (location != null) {

                appPreferences.setLatitude(String.valueOf(location.getLatitude()));
                appPreferences.setLongitude(String.valueOf(location.getLongitude()));
                appPreferences.setSchoolID(String.valueOf(location.getLatitude()));
                appPreferences.setStatffID(String.valueOf(location.getLongitude()));
                getCurrentAddress("0");
                //locationTv.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
            }

        } catch (Exception ex) {

        }
        // Permissions ok, we get last location

        startLocationUpdates();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onDestroy() {
        appPreferences.setUserPresenceState("NotLive");

        super.onDestroy();
    }

    @SuppressLint("RestrictedApi")
    private void startLocationUpdates() {


        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    //LocationRequest locationRequest = new LocationRequest();
                    locationRequest = new LocationRequest();
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setInterval(UPDATE_INTERVAL);
                    locationRequest.setFastestInterval(FASTEST_INTERVAL);
                    locationRequest.setSmallestDisplacement(1000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    locationRequest.setMaxWaitTime(MAX_WAIT_TIME);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());

                    result.setResultCallback(new ResultCallback() {


                        @Override
                        public void onResult(@NonNull Result result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(HomeActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        location = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(HomeActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                        // Ignore the error.
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied. However, we have no way to fix the
                                    // settings so we won't show the dialog.
                                    //finish();
                                    break;
                            }
                        }


                    });

                }
            }

        }
//
    }

    private void isPermissionGranted(int[] grantResults) {
        if (grantResults.length > 0) {
            Boolean permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (permissionGranted) {
                //callPhoneManager();
            } else {
                // PermissionUtils.alertAndFinish(this);
            }
        }
    }

    private void checkPermissions() {
        // Checks the Android version of the device.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Boolean canWriteExternalStorage = check_permission(7);
            Boolean canReadExternalStorage = check_permission(1);
            Boolean canReadCallLog = check_permission(8);
            Boolean canReadOutGoingCalls = check_permission(9);
            Boolean ModifyAudioSetting = check_permission(12);
            if (!canWriteExternalStorage || !canReadExternalStorage || !canReadCallLog || !canReadOutGoingCalls || !ModifyAudioSetting) {
                requestPermissions(PERMISSIONS, PERMISSION_REQUEST);
            } else {
                // Permission was granted.
                callPhoneManager();
            }
        } else {
            // Version is below Marshmallow.
            callPhoneManager();
        }
    }

//    public String GetUserMobile(){
//        TextView textView = (TextView) findViewById(R.id.id_text_view);
//        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            get_file();
//        }
//        String PhoneNumber = mTelephonyManager.getLine1Number();
//        return PhoneNumber;
//    }

    private void callPhoneManager() {
        TextView textView = (TextView) findViewById(R.id.id_text_view);
        mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String PhoneNumber = mTelephonyManager.getLine1Number();
        mTelephonyManager.listen(new PhoneCallListener(textView,getApplicationContext()), PhoneStateListener.LISTEN_CALL_STATE
                | PhoneStateListener.LISTEN_CELL_INFO // Requires API 17
                | PhoneStateListener.LISTEN_CELL_LOCATION
                | PhoneStateListener.LISTEN_DATA_ACTIVITY
                | PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                | PhoneStateListener.LISTEN_SERVICE_STATE
                | PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                | PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR
                | PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST: {
                isPermissionGranted(grantResults);
                return;
            }
            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }

                if (permissionsRejected.size() > 0) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    appPreferences.setLocationPermissionDenied(true);
//                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//                            new AlertDialog.Builder(HomeActivity.this).
//                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").
//                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions(permissionsRejected.
//                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
//                                            }
//                                        }
//                                    }).setNegativeButton("Cancel", null).create().show();
//
//                            return;
//                        }
//                        else{
//                            buildAlertMessageNoGps();
//                            return;
//                        }
//                    }
                    return;
                } else {
                    try {
                        if (googleApiClient != null) {
                            appPreferences.setLocationPermissionDenied(false);
                            googleApiClient.connect();
                            // startLocationUpdates();
                        }
                    } catch (Exception ex) {

                    }

                }

                break;
        }
    }
    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }
//    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getCurrentAddress(String FeedID) {
        // Get the location manager
//        dialog = ProgressDialog.show(HomeActivity.this, null,
//                "Please Wait...");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            return;
        }
        Location L = gpsTrack.getLocation();

        Location LocationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        final Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location Locationpassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        if(LocationNetwork != null){
            double lat=LocationNetwork.getLatitude();
            double longitude=LocationNetwork.getLongitude();
        }
        if (locationManager != null) {

            try {

                if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);

            } catch (Exception ex) {
                //Log.i("msg", "fail to request location update, ignore", ex);
            }


            try {
//                final double Latitude=Double.parseDouble(appPreferences.getLatitude());
//                final double Longitude=Double.parseDouble(appPreferences.getLongitude());

                final double Latitude=Double.parseDouble(String.valueOf(L.getLatitude()));
                final double Longitude=Double.parseDouble(String.valueOf(L.getLongitude()));

                GPStracker g=new GPStracker(getApplicationContext());
//            GeoApiContext context = new GeoApiContext.Builder()
//                    .apiKey("AIza...")
//                    .build();
//            GeocodingResult[] results =  GeocodingApi.newRequest(context)
//                    .latlng(new LatLng(52.2641, 76.9597)).await();


                final Location l=g.getLocation();
                Geocoder gcd = new Geocoder(getBaseContext(),
                        Locale.getDefault());
                List<Address> addresses;
                addresses = gcd.getFromLocation(Latitude,
                    Longitude, 1);

//                addresses = gcd.getFromLocation(Latitude,
//                        Longitude, 1);
                if (addresses.size() > 0) {

                    final String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    final String City = addresses.get(0).getLocality();
                    final String subLocality = addresses.get(0).getSubLocality();
                    final String state = addresses.get(0).getAdminArea();
                    final String country = addresses.get(0).getCountryName();
                    final String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    Insert_Location(String.valueOf(Latitude),String.valueOf(Longitude), FeedID);
                    //FindCityLocality(country,state,City,subLocality,postalCode,address,Locationpassive.getLatitude(),Locationpassive.getLongitude(),FeedID);

//
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
    public void GetPlaceID(String Latitude, String Longitude,String Place,String FeedID,String LocalityID,String CheckInFlag) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//

        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("Latitude", Latitude));
                args.add(new BasicNameValuePair("Longitude", Longitude));
                args.add(new BasicNameValuePair("Place", Place));
                args.add(new BasicNameValuePair("FeedID", FeedID));
                args.add(new BasicNameValuePair("LocalityID", LocalityID));
                args.add(new BasicNameValuePair("CheckInFlag", CheckInFlag));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/GetPlaceID";

                dataFromNetwork.setConfig(URL, methodName, args);
                dataFromNetwork.execute();
//                Object response = dataFromNetwork.execute().get().toString();
//                if (response != null) {
//                    String result = response.toString();
//                    String abc = "";
//                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void DeleteAppToken(final String ApplicantID, final String AppToken){
//        RequestQueue queue = Volley.newRequestQueue(this);
//

        if (cd.isConnectingToInternet()) {



            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("AppToken",AppToken));
                args.add(new BasicNameValuePair("ApplicantID",ApplicantID));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/DeleteToken";

                dataFromNetwork.setConfig(URL, methodName, args);
                dataFromNetwork.execute();
//                Object response = dataFromNetwork.execute().get().toString();
//                if (response != null) {
//
//                }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }


    }

    public String GetLoginUserApplicantID(){
        String ContactID="";
        if (cd.isConnectingToInternet()) {

            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/GetCustomerAppID";

                dataFromNetwork.setConfig(URL, methodName, args);

                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    ContactID=response.toString();
                    ContactID=ContactID.replaceAll("\"","").replaceAll("\n","");
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return ContactID;
    }

    public void GetGrapevineContacts(String ContactLists,String Applicant_id){

        if (cd.isConnectingToInternet()) {
            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("Applicant_id",Applicant_id));
                args.add(new BasicNameValuePair("ContactLists",ContactLists));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/GetGrapevineContacts";

                dataFromNetwork.setConfig(URL, methodName, args);
                dataFromNetwork.execute();
//            Object response = dataFromNetwork.execute().get().toString();
//            if (response != null) {
//                JSONObject object = new JSONObject(response.toString());
//
//                //Bookingdetails(listView);
//            }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void Insert_Location(String Latitude,String Longitude,String FeedID){

        if (cd.isConnectingToInternet()) {
            String NetworkType= getNetworkType(getApplicationContext());
            String IP="";
            if(NetworkType.equals("MOBILE")){
                IP=GetDeviceipMobileData();
            }

            else if(NetworkType.equals("WIFI")){
                IP=GetDeviceipWiFiData();
            }

            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                String Applicant_ID=appPreferences.getApplicant_id();

                //if(Applicant_ID.equals("")){
//                    Applicant_ID = GetLoginUserApplicantID();
//                    appPreferences.setApplicant_id(Applicant_ID);
               // }
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("PlaceID",""));
                args.add(new BasicNameValuePair("Formatted_address",""));
                args.add(new BasicNameValuePair("Placename",""));
                args.add(new BasicNameValuePair("CityID",""));
                args.add(new BasicNameValuePair("LocalityID","0"));
                args.add(new BasicNameValuePair("ContactID",Applicant_ID));
                args.add(new BasicNameValuePair("Latitude",Latitude));
                args.add(new BasicNameValuePair("Longitude",Longitude));
                args.add(new BasicNameValuePair("Distance","0"));
                args.add(new BasicNameValuePair("LocationMode","CurrentLocationMode"));
                args.add(new BasicNameValuePair("PlaceIDjson",""));
                args.add(new BasicNameValuePair("FeedID",FeedID));
                args.add(new BasicNameValuePair("CheckINFlag","0"));
                args.add(new BasicNameValuePair("WebsiteID","9"));
                args.add(new BasicNameValuePair("IPAddress",IP));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/ode_Insert_Contact_CurrentLocation";

                dataFromNetwork.setConfig(URL, methodName, args);
                dataFromNetwork.execute();
//            Object response = dataFromNetwork.execute().get().toString();
//            if (response != null) {
//                JSONObject object = new JSONObject(response.toString());
//
//                //Bookingdetails(listView);
//            }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    public void FindCityLocality(final String country, final String state, final String City, String Locality, final String Pincode, final String address, final double Latitude, final double Longitude,final String FeedID){

        if (cd.isConnectingToInternet()) {



            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("country",country));
                args.add(new BasicNameValuePair("state",state));
                args.add(new BasicNameValuePair("City",City));
//                args.add(new BasicNameValuePair("callstatus",callstatus));
                args.add(new BasicNameValuePair("Locality",Locality));
                args.add(new BasicNameValuePair("Pincode",Pincode));
                args.add(new BasicNameValuePair("address",address));
                String URL = "https://oxyzenhomes.com/";
                String methodName = "RentalApp/Property/FindLocalityCity";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    JSONObject object = new JSONObject(response.toString());
                    final String CityID = object.get("CityID").toString();
                    final String LocalityID=object.get("LocalityID").toString();
                    if(!FeedID.equals("0")){
                        GetPlaceID(String.valueOf(Latitude),String.valueOf(Longitude),address,FeedID,LocalityID,"0");
                    }
                    InsertCurrentLocation(CityID,LocalityID,address,Pincode,Latitude,Longitude,"9",appPreferences.getStuID().replace("+91",""));
                    //Bookingdetails(listView);
                }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }


    }


    public void InsertCurrentLocation(String CityID,String LocalityID,String Address,String Pincode,final double Latitude,final double Longitude,String WebsiteID,String MobileNo){
//        RequestQueue queue = Volley.newRequestQueue(this);
//

        if (cd.isConnectingToInternet()) {



            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("WebsiteID",WebsiteID));
                args.add(new BasicNameValuePair("Mobile",MobileNo));
                args.add(new BasicNameValuePair("Latitude",String.valueOf(Latitude)));
//                args.add(new BasicNameValuePair("callstatus",callstatus));
                args.add(new BasicNameValuePair("Longitude",String.valueOf(Longitude)));
                args.add(new BasicNameValuePair("CityID",CityID));
                args.add(new BasicNameValuePair("LocalityID",LocalityID));
                args.add(new BasicNameValuePair("Address",Address));
                args.add(new BasicNameValuePair("Pincode",Pincode));
                args.add(new BasicNameValuePair("LocationMode","CurrentLocationMode"));
                String URL = "https://oxyzenhomes.com/";
                String methodName = "RentalApp/Property/InsertContactCurrentLocation";

                dataFromNetwork.setConfig(URL, methodName, args);
                dataFromNetwork.execute();
//                Object response = dataFromNetwork.execute().get().toString();
//                if (response != null) {
//
//                    //Bookingdetails(listView);
//                }



            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }


    }


    protected void checkPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    // show an alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                    builder.setMessage("Write external storage permission is required.");
                    builder.setTitle("Please grant permission");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(
                                    mActivity,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSION_REQUEST_CODE
                            );
                        }
                    });
                    builder.setNeutralButton("Cancel",null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    // Request permission
                    ActivityCompat.requestPermissions(
                            mActivity,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_REQUEST_CODE
                    );
                }
            }else {
                // Permission already granted
            }
        }
    }

    private Location getLastKnownLocation() {
        Location l=null;
        LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                l = mLocationManager.getLastKnownLocation(provider);
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    private void buildAlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //final AlertDialog.Builder builder = new AlertDialog.Builder(this).create();
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent1);
                        //startActivity(intent1);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    private  File CreateVCFFile() throws IOException{
        File vdfdirectory = new File(
                Environment.getExternalStorageDirectory() + VCF_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!vdfdirectory.exists()) {
            vdfdirectory.mkdirs();
        }

        vcfFile = new File(vdfdirectory, "android_"+ Calendar.getInstance().getTimeInMillis() + ".vcf");
        return vcfFile;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            //Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
    class Controller {
        final static int FILE_SELECTED = 4;

        Activity getActivity() {
            return HomeActivity.this;
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

//        @Override
//        public void onShowCustomView(View view, CustomViewCallback callback) {
//
//            mCustomViewCallback = callback;
//            mTargetView.addView(view);
//            mCustomView = view;
//            mContentView.setVisibility(View.GONE);
//            mTargetView.setVisibility(View.VISIBLE);
//            mTargetView.bringToFront();
//        }
//
//        @Override
//        public void onHideCustomView() {
//            if (mCustomView == null)
//                return;
//
//            mCustomView.setVisibility(View.GONE);
//            mTargetView.removeView(mCustomView);
//            mCustomView = null;
//            mTargetView.setVisibility(View.GONE);
//            mCustomViewCallback.onCustomViewHidden();
//            mContentView.setVisibility(View.VISIBLE);
//        }


        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            Log.d(TAG, "onPermissionRequest");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(request.getOrigin().toString().equals("https://www.grapevine.work/")) {

                    String[] PERMISSIONS = {
                            PermissionRequest.RESOURCE_AUDIO_CAPTURE,
                            PermissionRequest.RESOURCE_VIDEO_CAPTURE
                    };
                    request.grant(PERMISSIONS);
//                    request.grant(request.getResources());
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

//                    if(Url.startsWith("https://www.grapevine.work/Grapevine/Index")) {
//                        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffcf01")));
////
//                        asw_progress.setVisibility(View.GONE);
//                        findViewById(R.id.msw_welcome).setVisibility(View.GONE);
//                    findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
//                    }

            }
            else if(!IsFullScreen)
            {

//                    if(Url.startsWith("https://www.grapevine.work/Grapevine/Index")) {
//                        asw_progress.setVisibility(View.VISIBLE);
//
//                        findViewById(R.id.msw_welcome).setVisibility(View.VISIBLE);
//                        findViewById(R.id.activity_main_webview).setVisibility(View.GONE);
//                    }
//                    else{
//                        asw_progress.setVisibility(View.GONE);
//
//                        findViewById(R.id.msw_welcome).setVisibility(View.GONE);
//                        findViewById(R.id.activity_main_webview).setVisibility(View.VISIBLE);
//                    }

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

        // overload the geoLocations permissions prompt to always allow instantly as app permission was granted previously
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            if(Build.VERSION.SDK_INT < 23 || check_permission(1)){
                // location permissions were granted previously so auto-approve
                callback.invoke(origin, true, false);
            } else {
                // location permissions not granted so request them
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


        //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams){
            Toast.makeText(getApplicationContext(),"File Uploader",Toast.LENGTH_SHORT);

            if(check_permission(2) && check_permission(3) && check_permission(4) && check_permission(5)&& check_permission(15) && check_permission(12)) {
                String[] value = fileChooserParams.getAcceptTypes();
                Toast.makeText(getApplicationContext(),"File Uploader Called",Toast.LENGTH_SHORT);
                mUMA = filePathCallback;
                Intent takePhotoIntent = null;
                Intent takePictureIntent=null;
                Intent takeVideoIntent = null;
                Intent takeAudioIntent=null;
                Intent takeContact=null;
                Intent takeRecordIntent=null;
                Intent takeDocument=null;

                boolean includeVideo = false;
                boolean includePhoto = false;
                boolean includeMicrophone=false;
                boolean includeAudio=false;
                boolean includeContact=false;
                boolean includeCapture=false;
                boolean includeDocument=false;

                // Check the accept parameter to determine which intent(s) to include.
                paramCheck:
                for (String acceptTypes : fileChooserParams.getAcceptTypes()) {
                    // Although it's an array, it still seems to be the whole value.
                    // Split it out into chunks so that we can detect multiple values.
                    String[] splitTypes = acceptTypes.split(", ?+");
                    for (String acceptType : splitTypes) {
                        switch (acceptType) {
                            case "*/*":
                                includePhoto = true;
                                includeVideo = true;
                                break paramCheck;
                            case "image/*":
                                includePhoto = true;
                                break;
                            case "video/*":
                                includeVideo = true;
                                break;
                            case "microphone/*":
                                includeMicrophone=true;
                                break;
                            case "audio/*":
                                includeAudio=true;
                                break;
                            case "capture/*":
                                includeCapture=true;
                                break;
                            case "contact/*":
                                includeContact=true;
                                break;
                            case "application/pdf":
                                includeDocument=true;
                        }
                    }
                }
                Toast.makeText(getApplicationContext(),"File Uploader Image:"+includePhoto,Toast.LENGTH_SHORT);
                Toast.makeText(getApplicationContext(),"File Uploader Video:"+includeVideo,Toast.LENGTH_SHORT);
                Toast.makeText(getApplicationContext(),"File Uploader Audio:"+includeAudio,Toast.LENGTH_SHORT);
                // If no `accept` parameter was specified, allow both photo and video.
                if (fileChooserParams.getAcceptTypes().length == 0) {
                    includePhoto = true;
                    includeVideo = true;
                }
                else if(fileChooserParams.getAcceptTypes().length<=2 && (includePhoto || includeVideo || includeAudio)){

                    Intent intent = new Intent(HomeActivity.this, DNAGalleryPickerActivity.class);
                    intent.putExtra("includePhoto",includePhoto);
                    intent.putExtra("includeVideo",includeVideo);
                    intent.putExtra("includeAudio",includeAudio);

//                    Intent intentImg = new Intent();
//                    intentImg.setAction(android.content.Intent.ACTION_VIEW);
//                    intentImg.setType("image/*");
//                    intentImg.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intentImg);

                    //intent.putExtra(AppConstants.MODE, AppConstants.OPEN_FULL_MODE);
                    //intent.putExtra(AppConstants.MAX_SELECTION, AppConstants.MAX_MEDIA_COUNT); // default 5


                    startActivityForResult(intent, AppConstants.OPEN_MEDIA_PICKER);
                    //someActivityResultLauncher.launch(intent);
//                    new PickPhotoView.Builder(HomeActivity.this)
//                            .setPickPhotoSize(9)
//                            .setHasPhotoSize(7)
//                            .setAllPhotoSize(10)
//                            .setShowCamera(true)
//                            .setShowVideo(true)
//                            .setSpanCount(4)
//                            .setLightStatusBar(false)
//                            .setStatusBarColor(R.color.colorPrimaryDark)
//                            .setToolbarColor(R.color.colorPrimary)
//                            .setToolbarTextColor(R.color.white)
//                            .setSelectIconColor(R.color.colorPrimary)
//                            .start();
//                    HomeActivity.access$getOptions$p(HomeActivity.this).setPreSelectedUrls(HomeActivity.this.returnValue);
//                    Pix.start((FragmentActivity)HomeActivity.this, HomeActivity.access$getOptions$p(HomeActivity.this));
                    return true;
                }

                if (includePhoto || includeVideo) {
                    String[] MIMEType= new String[0];
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                        MIMEType = fileChooserParams.getAcceptTypes();
                    }

                    if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                    {
//        takeVideoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        takeVideoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    else
                    {
//        takeVideoIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                        takeVideoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    }
                    if(MIMEType.length==0){
                        MIMEType= new String[]{"*/*"};
                    }
                    //intent.setType(MIMEType);
//                    takeVideoIntent.setType(MIMEType[0]);
                    takeVideoIntent.setType("image/*");
                    takeVideoIntent.putExtra(Intent.EXTRA_MIME_TYPES, MIMEType);
                    takeVideoIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

//            intent.setAction(Intent.ACTION_GET_CONTENT);
                    takeVideoIntent.setAction(Intent.ACTION_GET_CONTENT);
                    takeVideoIntent.putExtra("return-data", true);



                }
                if(includeMicrophone){
                    takeRecordIntent =new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);

                }

                if(includeAudio){


                    String[] MIMEType=fileChooserParams.getAcceptTypes();



                    if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                    {
                        takeDocument = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    }
                    else
                    {
                        takeDocument = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                    }
                    if(MIMEType.length==0){
                        MIMEType= new String[]{"*/*"};
                    }
                    //intent.setType(MIMEType);
                    takeDocument.putExtra(Intent.EXTRA_MIME_TYPES, MIMEType);
                    takeDocument.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

//            intent.setAction(Intent.ACTION_GET_CONTENT);
                    takeDocument.setAction(Intent.ACTION_GET_CONTENT);
                    takeDocument.putExtra("return-data", true);

                }
                if(includeContact){
                    takeContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    File photoFile = null;
                    try {
                        photoFile = CreateVCFFile();
                        if (photoFile != null) {
                            mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        }
                        takeContact.putExtra(MediaStore.EXTRA_OUTPUT,
                                mCameraPhotoPath);
                        takeContact.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//            intent.setAction(Intent.ACTION_GET_CONTENT);

                        takeContact.putExtra("return-data", true);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e(TAG, "Unable to create Image File", ex);
                    }
                }
                if(includeCapture){
                    takePhotoIntent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                }
                if(includeDocument){

                    String[] MIMEType=fileChooserParams.getAcceptTypes();



                    if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                    {
                        takeDocument = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    }
                    else
                    {
                        takeDocument = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
                    }
                    if(MIMEType.length==0){
                        MIMEType= new String[]{"*/*"};
                    }
                    //intent.setType(MIMEType);
                    takeDocument.putExtra(Intent.EXTRA_MIME_TYPES, MIMEType);
                    takeDocument.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

//            intent.setAction(Intent.ACTION_GET_CONTENT);
                    takeDocument.setAction(Intent.ACTION_GET_CONTENT);
                    takeDocument.putExtra("return-data", true);

                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent[] intentArray;
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Select File");


                if ( takeVideoIntent != null) {

                    intentArray = new Intent[]{takePictureIntent, takeVideoIntent};
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(takeVideoIntent, SELECT_VIDEO_REQUEST);
                    return true;
                }
                if (takePictureIntent != null ) {

                    intentArray = new Intent[]{takePictureIntent, takeVideoIntent};
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(takePictureIntent, SELECT_PHOTO_REQUEST);

                    return true;
                }
                else if (takePhotoIntent != null) {

                    intentArray = new Intent[]{takePhotoIntent};
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                    startActivityForResult(chooserIntent, CAMERA_REQUEST);
                    return true;
                }

                else if(takeAudioIntent!=null){

                    intentArray = new Intent[]{takeAudioIntent};
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(chooserIntent, REQUEST_CODE);
                    return true;
                }
                else if(takeRecordIntent!=null){

                    intentArray = new Intent[]{takeRecordIntent};
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(chooserIntent, Controller.FILE_SELECTED);
                    return true;
                }
                else if(takeContact!=null){

                    intentArray = new Intent[]{takeContact};
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(chooserIntent, REQUEST_CODE);
                    return true;
                }
                else if(takeDocument!=null){

                    intentArray = new Intent[]{takeDocument};
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(takeDocument, SELECT_VIDEO_REQUEST);
                    return true;
                }
                else {

                    intentArray = new Intent[0];
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    startActivityForResult(chooserIntent, REQUEST_CODE);
//return true;
                }





            }
            else{
                get_file();
                return false;

            }
            return true;
        }

        //Creating video file for upload
        private File create_video() throws IOException {
            @SuppressLint("SimpleDateFormat")
            String file_name    = new SimpleDateFormat("yyyy_mm_ss").format(new Date());
            String new_name     = "file_"+file_name+"_";
            File sd_directory   = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
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

            new AlertDialog.Builder(HomeActivity.this).setTitle(newTitle).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

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

            new AlertDialog.Builder(HomeActivity.this).setTitle(newTitle).setMessage(message).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

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

        // Android 2.x
        public void openFileChooser(ValueCallback<Uri[]> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        // Android 3.0
        public void openFileChooser(ValueCallback<Uri[]> uploadMsg, String acceptType) {
            openFileChooser(uploadMsg, "", "filesystem");
        }

        // Android 4.1
        public void openFileChooser(ValueCallback<Uri[]> uploadMsg, String acceptType, String capture) {
            mUploadHandler = new UploadHandler(new Controller());
            mUploadHandler.openFileChooser(uploadMsg, acceptType, capture);
        }

    }
    public static final Options access$getOptions$p(HomeActivity $this) {
        Options var10000 = $this.options;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("options");
        }

        return var10000;
    }

    // $FF: synthetic method
    public final void access$setOptions$p(HomeActivity $this, Options var1) {
        $this.options = var1;
    }

    // $FF: synthetic method
    public final void access$setReturnValue$p(HomeActivity $this, ArrayList var1) {
        $this.returnValue = var1;
    }
    public void get_file(){
        String[] perms = {
                Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,//Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, RECORD_AUDIO
                , Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
                //Manifest.permission.READ_PHONE_NUMBERS,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,Manifest.permission.READ_PHONE_STATE};
        ActivityCompat.requestPermissions(HomeActivity.this, perms, MY_PERMISSION_REQUEST_CODE);

        //Checking for storage permission to write images for upload
//            if (!check_permission(2) && !check_permission(3)) {
//                ActivityCompat.requestPermissions(HomeActivity.this, perms,MY_PERMISSION_REQUEST_CODE);
//
//                //Checking for WRITE_EXTERNAL_STORAGE permission
//            } else
//                if (!check_permission(2)) {
//                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_CODE);
//
//                //Checking for CAMERA permissions
//            } else if (!check_permission(3)) {
//                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CODE);
//            } else if (!check_permission(4)) {
//                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_CODE);
//
//                //Checking for CAMERA permissions
//            }
    }
    public boolean check_permission(int permission){
        switch(permission){
            case 1:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            case 2:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;

            case 3:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
            case 4:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED;
            case 5:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
            case 6:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
            case 7:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
            case 8:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED;
            case 9:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_GRANTED;
            case 10:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED;
            case 11:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_NUMBERS) == PackageManager.PERMISSION_GRANTED;
            case 12:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.MODIFY_AUDIO_SETTINGS) == PackageManager.PERMISSION_GRANTED;
            case 13:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED;
            case 14:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED;
            case 15:
                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;



//            case 15:
//                return ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;


        }
        return false;
    }

    public void selectVideoFromGallery(String[] MIMEType)
    {
        Intent intent;
        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        }
        else
        {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
        }
        if(MIMEType.length==0){
            MIMEType= new String[]{"*/*"};
        }
        //intent.setType(MIMEType);
        intent.putExtra(Intent.EXTRA_MIME_TYPES, MIMEType);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

//            intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(intent,SELECT_VIDEO_REQUEST);
    }


    private void handleSignInResult(Intent result) {
        Task<GoogleSignInAccount> getAccountTask= GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());

                    onDriveClientReady();
//                    Drive googleDriveService =
//                            new Drive.Builder(
//                                    AndroidHttp.newCompatibleTransport(),
//                                    new GsonFactory(),
//                                    credential)
//                                    .setApplicationName("Drive API Migration")
//                                    .build();
//
//                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
//                    // Its instantiation is required before handling any onClick actions.
//                    mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                })
                .addOnFailureListener(exception -> Log.e(TAG, "Unable to sign in.", exception));
        if(getAccountTask.isSuccessful()){
            initializeDriveClient(getAccountTask.getResult());
        }

    }
    private void openFilePicker() {
        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Opening file picker.");

            Intent pickerIntent = mDriveServiceHelper.createFilePickerIntent();

            // The result of the SAF Intent is handled in onActivityResult.
            startActivityForResult(pickerIntent, REQUEST_CODE_OPEN_ITEM);
        }
    }

    private void openFileFromFilePicker(Uri uri) {
        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Opening " + uri.getPath());

            mDriveServiceHelper.openFileUsingStorageAccessFramework(getContentResolver(), uri)
                    .addOnSuccessListener(nameAndContent -> {
                        String name = nameAndContent.first;
                        String content = nameAndContent.second;

                       // mFileTitleEditText.setText(name);
                       // mDocContentEditText.setText(content);

                        // Files opened through SAF cannot be modified.
                       // setReadOnlyMode();
                    })
                    .addOnFailureListener(exception ->
                            Log.e(TAG, "Unable to open file from picker.", exception));
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        //doSomeOperations();
                    }
                }
            });

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {


        super.onActivityResult(requestCode, resultCode, data);
        FileCounter=0;
        FileResults=null;
        final AuthorizationResponse response1 = AuthorizationClient.getResponse(resultCode, data);
        if(mPicker!=null){
            IPickerResult oresult = mPicker.getPickerResult(requestCode, resultCode, data);
            // Handle the case if nothing was picked
            if (oresult != null) {
                // Do something with the picked file
                Log.d("main", "Link to file '" + oresult.getName() + ": " + oresult.getLink());
                String Link=oresult.getLink().toString().split("\\?")[0];
                String Name=oresult.getName().toString();
                String Size=String.valueOf(oresult.getSize());
                CreateFeedForApplication( Link, Size,"8",Name);
                return;
            }
        }



        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response1.getAccessToken();
            appPreferences.setSpotifyToken(mAccessToken);
            webView.loadUrl("https://www.grapevine.work/workplace/Music?access_token="+mAccessToken);
            //updateTokenView();
        }
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == RESULT_OK) {
                    handleSignInResult(data);
                    GOOGLE_DRIVE_LOGGED_IN=true;
                    showMessage("Sign in successfully.");

                //openFilePicker();
//                    Task<GoogleSignInAccount> getAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
//                    if (getAccountTask.isSuccessful()) {
//
//                        initializeDriveClient(getAccountTask.getResult());
//                        GOOGLE_DRIVE_LOGGED_IN=true;
//                        showMessage("Sign in successfully.");
//                        onDriveClientReady();
//                        //binding.btnSubmit.setText(DOWNLOAD_FILE);
//                    } else {
//                        showMessage("Sign in failed.");
//                    }
                } else {
                    showMessage("Sign in failed.");
                }
                break;
            case REQUEST_CODE_OPEN_ITEM:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        openFileFromFilePicker(uri);
                    }
                }

                break;
        }
        if (requestCode == DBX_CHOOSER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

                DbxChooser.Result result = new DbxChooser.Result(data);
                Log.d("main", "Link to selected file: " + result.getLink());

                String Link=result.getLink().toString();
                String Name=result.getName().toString();
                String Size=String.valueOf(result.getSize());
                CreateFeedForApplication( Link, Size,"7",Name);

            }

        }
        if(resultCode==REQUEST_MEDIA_PROJECTION){

        }
        if (Build.VERSION.SDK_INT >= 21) {


            if (resultCode == Activity.RESULT_CANCELED) {
                if (requestCode == REQUEST_CODE || requestCode==CAMERA_REQUEST || requestCode == SELECT_VIDEO_REQUEST || requestCode == Controller.FILE_SELECTED || requestCode==requestCodePicker) {

                    // If the file request was cancelled (i.e. user exited camera),
                    // we must still send a null value in order to ensure that future attempts
                    // to pick files will still work.
                    mUMA.onReceiveValue(FileResults);
                    return;
                }

            }
            if(resultCode==REQ_CODE_OPEN)
            {
                String ABC="";
            }
            if (requestCode == AppConstants.OPEN_MEDIA_PICKER) {
                // Make sure the request was successful
                //Toast.makeText(getApplicationContext(),resultCode,Toast.LENGTH_SHORT);
                if (resultCode == Activity.RESULT_OK && data != null) {
                    ArrayList arr = data.getStringArrayListExtra(AppConstants.RESULT);

                    if(arr!=null){
                        FileResults = new Uri[arr.size()];
                        for(int i=0;i<arr.size();i++){
                            String path= arr.get(i).toString();
                            Uri uri=Uri.fromFile(new File(path));
                            String mime = getMimeType(uri);

                            if(mime.contains("image/")) {

                                String imagestring= compressImage(uri.toString());
                                FileResults[i]=Uri.fromFile(new File(imagestring));
                                FileCounter=FileCounter+1;

                            }
                            else if(!mime.contains("video/")){
                                FileResults[i] = uri;
                                FileCounter=FileCounter+1;
                            }
                            else{
                                String mInputPath=FileUtil.getPath(this,uri);
                                File inputFile = new File(mInputPath);
                                String FileName=inputFile.getName();


                                //LoadFFmpegLibrary();
                                String outputPath = "";
                                outputPath = getAppDir() + "/"+FileName;
                                String tv_output= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/Grapevine/sent";
                                String tv_input= null;
                                try {
                                    tv_input = Util.getFilePath(this, uri);
                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                }
                                //FileName = FileName.substring(0, FileName.lastIndexOf("."))+ "_"+ new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale()).format(new Date()) + "."+FileName.lastIndexOf(".");
                                Toast.makeText(getApplicationContext(),FileName,Toast.LENGTH_LONG).show();
                                //String destPath = tv_output + File.separator + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale()).format(new Date()) + ".mp4";
                                String destPath=tv_output+File.separator+FileName;
                                File file = new File(destPath);
                                if(file.exists())
                                {
                                    FileResults[i] = Uri.fromFile(file);
                                    FileCounter=FileCounter+1;
                                }
                                else{
                                    VideoCompression(destPath,tv_input,i,FileName);
                                }




                                //String cmd= "-y!-i!"+inputPath+"!-c:v!libx264!-c:a!copy!-b:v:1000k!-fmp4!"+outputPath;
                                String cmd="-y!-i!"+mInputPath+"!-vf!scale=240:-2!-c:v!libx264!-preset!ultrafast!-c:a!copy!-me_method!zero!-tune!fastdecode!-strict!-2!-pix_fmt!yuv420p!"+outputPath;
                                //cmd="-y!-i!"+inputPath+"!"+outputPath;
                                Log.e("input path", mInputPath);
                                Log.e("output path", outputPath);
                                //Toast.makeText(context,cmd.split("\\s+").length,Toast.LENGTH_LONG).show();
                                try{
                                    String[] commandParams = new String[cmd.split("!").length];
                                    commandParams=cmd.split("!");
                                    //Toast.makeText(context, commandParams.length, Toast.LENGTH_SHORT).show();
//                                    FFmpegSession session = FFmpegKit.execute("-i "+mInputPath+" -c:v mpeg4 "+outputPath);
//                                    if (ReturnCode.isSuccess(session.getReturnCode())) {
//                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
//                                        // SUCCESS
//
//                                    } else if (ReturnCode.isCancel(session.getReturnCode())) {
//                                        Toast.makeText(getApplicationContext(), "CANCEL", Toast.LENGTH_SHORT).show();
//                                        // CANCEL
//
//                                    } else {
//                                        Toast.makeText(getApplicationContext(), String.format("Command failed with state %s and rc %s.%s", session.getState(), session.getReturnCode(), session.getFailStackTrace()), Toast.LENGTH_SHORT).show();
//                                        // FAILURE
//                                        Log.d(TAG, String.format("Command failed with state %s and rc %s.%s", session.getState(), session.getReturnCode(), session.getFailStackTrace()));
//
//                                    }

                                    //compressVideo(commandParams, outputPath,i);
                                }
                                catch (Exception ex){
                                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }


                        DataManager.getInstance().showProgressMessage(HomeActivity.this, "Files are loading");
                       Timer timer= new Timer();
                        int arrSize=arr.size();


                        //Toast.makeText(getApplicationContext(),"arr Size:"+arrSize+", fileSize:"+FileCounter,Toast.LENGTH_LONG).show();


                        timer.scheduleAtFixedRate(new TimerTask(){
                            @Override
                            public void run(){

                                if(FileResults!=null && FileResults.length== FileCounter){
                                    timer.cancel();
                                    DataManager.getInstance().hideProgressMessage();
                                    mUMA.onReceiveValue(FileResults);
                                    mUMA = null;
                                    SIGN=1;
                                }
                            }
                        },0,1000);

                    }
                }

//                else{
//                    if (null == mUM) return;
//
//                    Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
//                    mUM.onReceiveValue(result);
//                    mUM = null;
//                    SIGN=1;
//                }
            }
//            if (requestCode == PICK_PHOTO_DATA) {
//                ArrayList arr = data != null ? data.getStringArrayListExtra(INTENT_IMG_LIST_SELECT) : null;
////                val selectPaths = data.getSerializableExtra(PickConfig.INTENT_IMG_LIST_SELECT) as ArrayList<String>
////                        adapter!!.updateData(selectPaths)
//if(arr!=null){
//     results = new Uri[arr.size()];
//                for(int i=0;i<arr.size();i++){
//                    String path= arr.get(i).toString();
//                    Uri uri=Uri.fromFile(new File(path));
//                    String mime = getMimeType(uri);
//
//                    if(!mime.contains("image/")) {
//                        results[i] = uri;
//
//                    }
//                    else{
//                        String imagestring= compressImage(uri.toString());
//                        results[i]=Uri.fromFile(new File(imagestring));
//                    }
//                }
//                mUMA.onReceiveValue(results);
//                mUMA = null;
//                SIGN=1;
//}
//
//            }
//            if (requestCode == this.requestCodePicker && resultCode == -1) {
//                ArrayList arr = data != null ? data.getStringArrayListExtra(Pix.IMAGE_RESULTS) : null;
//                results = new Uri[arr.size()];
//                for(int i=0;i<arr.size();i++){
//                    String path= arr.get(i).toString();
//                    Uri uri=Uri.fromFile(new File(path));
//                    String mime = getMimeType(uri);
//
//                    if(!mime.contains("image/")) {
//                        results[i] = uri;
//
//                    }
//                    else{
//                        String imagestring= compressImage(uri.toString());
//                        results[i]=Uri.fromFile(new File(imagestring));
//                    }
//                }
//                mUMA.onReceiveValue(results);
//                mUMA = null;
//                SIGN=1;
//
//            }
            if (requestCode == SELECT_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            FileResults = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {

                        if (data.getClipData() != null) {
                            final int numSelectedFiles = data.getClipData().getItemCount();
                            FileResults = new Uri[numSelectedFiles];

                            for (int i = 0; i < numSelectedFiles; i++) {
                                String mime = getMimeType(data.getClipData().getItemAt(i).getUri());
                                if(!mime.contains("image/")) {
                                    FileResults[i] = data.getClipData().getItemAt(i).getUri();

                                }
                                else
                                {
                                    String imagestring= compressImage( data.getClipData().getItemAt(i).getUri().toString());
                                    FileResults[i] = Uri.fromFile(new File(imagestring));

                                    if(imagestring.equals("")){
                                        FileResults[i] = data.getClipData().getItemAt(i).getUri();

                                    }

                                }
                                //
                            }
                        }
                        else
                        {
                            if (data.getDataString() != null) {


                                String mime = getMimeType(Uri.parse(data.getDataString()));

                                if(!mime.contains("image/")){
                                    FileResults = new Uri[] {
                                            Uri.parse(data.getDataString())
                                    };
                                }
                                else
                                {
                                    String imagestring= compressImage(data.getDataString());
                                    if(imagestring.equals("")){
                                        FileResults = new Uri[] {
                                                Uri.parse(data.getDataString())
                                        };
                                    }
                                    else{
                                        FileResults = new Uri[] {
                                                Uri.fromFile(new File(imagestring))
                                        };
                                    }

                                }


                            }
                        }


                    }
                }
                mUMA.onReceiveValue(FileResults);
                mUMA = null;
                SIGN=1;

            }
            if (requestCode == SELECT_PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR-10) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            FileResults = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {


                        if (data.getClipData() != null) {
                            final int numSelectedFiles = data.getClipData().getItemCount();
                            FileResults = new Uri[numSelectedFiles];
                            for (int i = 0; i < numSelectedFiles; i++) {
                                String imagestring= compressImage( data.getClipData().getItemAt(i).getUri().toString());
                                if(imagestring.equals("")) {
                                    FileResults[i] = data.getClipData().getItemAt(i).getUri();

                                }
                                else
                                {
                                    FileResults[i] = Uri.fromFile(new File(imagestring));
                                }
                                //
                            }
                        }
                        else
                        {
                            if (data.getDataString() != null) {
                                String imagestring= compressImage(data.getDataString());
                                if(imagestring.equals("")){
                                    FileResults = new Uri[] {
                                            Uri.parse(data.getDataString())
                                    };
                                }
                                else
                                {
                                    FileResults = new Uri[] {
                                            Uri.fromFile(new File(imagestring))
                                    };
                                }


                            }
                        }

                        //}
                    }
                }
                //----Compression of image----//

                //----Compression of image----//
                mUMA.onReceiveValue(FileResults);
                mUMA = null;
                SIGN=1;
            }
            else if (requestCode == Controller.FILE_SELECTED && resultCode == Activity.RESULT_OK) {

                if (null == mUMA) {
                    return;
                }
                if (intent == null) {
                    //Capture Photo if no image available
                    if (mCM != null) {
                        FileResults = new Uri[]{Uri.parse(mCM)};
                    }
                } else {


                    if (data.getClipData() != null) {
                        final int numSelectedFiles = data.getClipData().getItemCount();
                        FileResults = new Uri[numSelectedFiles];
                        for (int i = 0; i < numSelectedFiles; i++) {
                            FileResults[i] = data.getClipData().getItemAt(i).getUri();
                        }
                    }
                    else
                    {
                        if (data.getDataString() != null) {
                            FileResults = new Uri[] {
                                    Uri.parse(data.getDataString())
                            };

                        }
                    }

                    //}
                }

                mUMA.onReceiveValue(FileResults);
                mUMA = null;
                SIGN=1;
            }
            else if(requestCode==CAMERA_REQUEST&& resultCode == Activity.RESULT_OK) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//            knop.setVisibility(Button.VISIBLE);
//                File file = new File(Environment.getExternalStorageDirectory(), "MyPhoto.jpg");
//
//                //Uri of camera image
//                Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file)

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), photo);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                FileResults = new Uri[]{
                        tempUri
                };
                mUMA.onReceiveValue(FileResults);
                mUMA = null;
                SIGN=1;
            }
            else if(requestCode==CONTACT_PICK_REQUEST && resultCode == Activity.RESULT_OK) {


//                ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
                ContactsResults.addAll(MultiContactPicker.obtainResult(data));
                String FeedID="0";
                String ContactID=appPreferences.getApplicant_id();
                String FriendFeedChannelID = appPreferences.getFriendFeedChannelID();
                String UserFriendFeedChannelID = appPreferences.getUserFeedChannelID();
                String PubnubChannel = appPreferences.getPubnubChannel();

                String AgentMode = appPreferences.getAgentMode();
                String FilterOnFeedChannelID = appPreferences.getFilterOnFeedChannelID();
                String CRMActivityChannelID = appPreferences.getCRMActivityChannelID();
                String EntityFeedChannelID = appPreferences.getEntityFeedChannelID();
                String FeedVisibilityID="4";
                String FeedbyFeedChannelID="";
                String Target_ID="1";
                String ApplicationFeedEventType_ID="1";
                if (AgentMode.equals("true")) {
                    FeedVisibilityID = "3";
                    FeedbyFeedChannelID = UserFriendFeedChannelID;
                    Target_ID = FilterOnFeedChannelID;
                    ContactID = "";
                    ApplicationFeedEventType_ID = "0";

                }
                if(ContactsResults.size()>0){
                 FeedID= crm_insert_feed(FriendFeedChannelID,  "0", "", "3", "1", FeedVisibilityID,
                         ApplicationFeedEventType_ID, Target_ID, "0", ContactID, "", "", "",
                        "", "", "1", "1", "0", "0", FeedID,
                         FeedbyFeedChannelID,AgentMode);
                }


                //String display="";
                for(int i=0;i<ContactsResults.size();i++){

                   // display += (i+1)+". "+selectedContacts.get(i).Email.toString()+"\n";
//                    String id=selectedContacts.get(i).id;
//                    String Email=selectedContacts.get(i).Email;
//                    String label=selectedContacts.get(i).label;
//                    String phone=selectedContacts.get(i).phone;
//                    String name=selectedContacts.get(i).name;

                    String id=ContactsResults.get(i).getContactID();
                    String Email="";
                    if(ContactsResults.get(i).getEmails().size()>0){
                        Email=ContactsResults.get(i).getEmails().get(0);
                    }

                    String label=ContactsResults.get(i).getDisplayName();
                    String phone=ContactsResults.get(i).getPhoneNumbers().get(0).getNumber();
                    String name=ContactsResults.get(i).getDisplayName();

                    String Vcard = "";
                    String LastName="";
                    String FirstName=name.split(" ")[0];
                    if(name.split(" ").length>1){
                        LastName=name.split(" ")[1];
                    }

//                    Vcard = "BEGIN:VCARD\r!" + "VERSION:3.0\r!";
//                    Vcard = Vcard + "FN:" + name;
//                    Vcard = Vcard + "!TEL;TYPE=WORK,VOICE:" + phone;
//                    Vcard=Vcard+"!EMAIL:"+Email+"!";
//                    Vcard = Vcard + "!END:VCARD";
                    Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                            new String[]{id},
                            null
                    );
                    //Toast.makeText(HomeActivity.this, "Contact Detail:"+AgentMode, Toast.LENGTH_SHORT).show();
//                    String ContactDetails="[{\"addresses\":[{\"city\":\"\",\"country\":\"\",\"country_code\":\"\"" +
//                            ",\"state\":\"\",\"street\":\"\",\"type\":\"\",\"zip\":\"\"}],\"birthday\":\"\"," +
//                            "\"emails\":[{\"email\":\""+Email+"\",\"type\":\"\"}],\"name\"" +
//                            ":{\"first_name\":\""+FirstName+"\",\"formatted_name\":\""+name+"\",\"last_name\":\""+LastName+"\"}" +
//                            ",\"phones\":[{\"phone\":\""+phone+"\"}]}]";
                    String Emails="";
                    if(!Email.equals("")){
                        Emails="\"emails\":[{\"email\":\""+Email+"\",\"type\":\"\"}],";
                    }
                    String ContactDetails="[{"+Emails+"\"name\"" +
                            ":{\"first_name\":\""+FirstName+"\",\"formatted_name\":\""+name+"\",\"last_name\":\""+LastName+"\"}" +
                            ",\"phones\":[{\"phone\":\""+phone+"\"}]}]";

                    SendMediaMessageThroughWhatsAppApi("","","contacts",ContactDetails,
                            FilterOnFeedChannelID,EntityFeedChannelID,CRMActivityChannelID,FriendFeedChannelID,FeedID);

//                    SendMediaMessageThroughWhatsAppApi("","","contacts",ContactDetails,
//                            "25","94096","","157569",FeedID);

                    if(cursor!=null&&cursor.getCount()>0)
                    {
                        cursor.moveToFirst();
//                        for(int k =0;k<cursor.getCount();k++)
//                        {
                            vCard=new ArrayList<String>();
                            get(cursor);
                            Vcard=vCard.get(0);
                            //Log.d("TAG", "Contact "+(k+1)+"VcF String is"+vCard.get(0));
                            cursor.moveToNext();
                        //}
                        if (cd.isConnectingToInternet()) {


                            try {

                                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                                    public void processResponse(Object response) {
                                        if (response != null) {

                                            // Bookingdetails(listView);
                                        }
                                    }
                                });
                                List<NameValuePair> args = new ArrayList<>();

                                args.add(new BasicNameValuePair("FriendFeedChannelID", FriendFeedChannelID));
                                args.add(new BasicNameValuePair("UserFeedChannelIID", UserFriendFeedChannelID));
                                args.add(new BasicNameValuePair("PubnubChannel", PubnubChannel));
//                args.add(new BasicNameValuePair("callstatus",callstatus));
                                args.add(new BasicNameValuePair("ContactNo", phone));
                                args.add(new BasicNameValuePair("ContactName", name));
                                args.add(new BasicNameValuePair("ContactEmail", Email));
                                args.add(new BasicNameValuePair("VcardString", Vcard));
                                args.add(new BasicNameValuePair("Contactid", ContactID));
                                args.add(new BasicNameValuePair("FeedID", FeedID));
                                String URL = "https://www.grapevine.work/";

                                String methodName = "workplace/InsertContactDetails";

                                dataFromNetwork.setConfig(URL, methodName, args);
                                Object response = dataFromNetwork.execute().get().toString();
                                if (response != null) {
                                    String Object = response.toString();
//                            JSONObject object = new JSONObject(response.toString());
//                            final String CityID = object.get("CityID").toString();
//                            final String LocalityID=object.get("LocalityID").toString();
                                    //  InsertCurrentLocation(CityID,LocalityID,address,Pincode,Latitude,Longitude,"9",appPreferences.getStuID().replace("+91",""));
                                    //Bookingdetails(listView);
                                }


                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    else
                    {
                        DeletePost( FeedID, "0", ContactID);
                        Log.d("TAG", "No Contacts in Your Phone");
                    }


                }


            }
            else if(requestCode==CONTACT_PICK_REQUEST_INVITATION && resultCode == Activity.RESULT_OK) {

                ContactsResults.addAll(MultiContactPicker.obtainResult(data));
                if(ContactsResults.size() > 0) {
                    for(int i=0;i<ContactsResults.size();i++){
                        String id=ContactsResults.get(i).getContactID();
                        String Email="";
                        if(ContactsResults.get(i).getEmails().size()>0){
                            Email=ContactsResults.get(i).getEmails().get(0);
                        }


                        String phone=ContactsResults.get(i).getPhoneNumbers().get(0).getNumber();
                        String name=ContactsResults.get(i).getDisplayName();
                        SendInvitation(phone, appPreferences.getApplicant_id(), Email, name);
                    }
                    Log.d("MyTag", ContactsResults.get(0).getDisplayName());
                }
                if(ContactsResults.size()>0){
                    Toast.makeText(HomeActivity.this, "Invitation Sent.", Toast.LENGTH_SHORT).show();
                }
//                ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
//
//                String ContactID=appPreferences.getApplicant_id();
//                for(int i=0;i<selectedContacts.size();i++){
//
//                    // display += (i+1)+". "+selectedContacts.get(i).Email.toString()+"\n";
//                    String id=selectedContacts.get(i).id;
//                    String Email=selectedContacts.get(i).Email;
//                    String label=selectedContacts.get(i).label;
//                    String phone=selectedContacts.get(i).phone;
//                    String name=selectedContacts.get(i).name;
//                    SendInvitation(phone, appPreferences.getApplicant_id(), Email, name);
//
//                }
//                if(selectedContacts.size()>0){
//                    Toast.makeText(HomeActivity.this, "Invitation Sent.", Toast.LENGTH_SHORT).show();
//                }


            }
            else if(requestCode==CONTACT_PICK_REQUEST_ADD_PHONE && resultCode == Activity.RESULT_OK) {
                ContactsResults.addAll(MultiContactPicker.obtainResult(data));
                String ContactID = appPreferences.getApplicant_id();
                if(ContactsResults.size() > 0) {
                    for(int i=0;i<ContactsResults.size();i++){
                        String id=ContactsResults.get(i).getContactID();
                        String Email="";
                        if(ContactsResults.get(i).getEmails().size()>0){
                            Email=ContactsResults.get(i).getEmails().get(0);
                        }


                        String phone=ContactsResults.get(i).getPhoneNumbers().get(0).getNumber();
                        String name=ContactsResults.get(i).getDisplayName();
                        SendInvitation(phone, appPreferences.getApplicant_id(), Email, name);
                    }
                    Log.d("MyTag", ContactsResults.get(0).getDisplayName());
                }
                if(ContactsResults.size()>0){
                    Toast.makeText(HomeActivity.this, "Invitation Sent.", Toast.LENGTH_SHORT).show();
                }


//                ArrayList<Contact> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");
//
//                String ContactID = appPreferences.getApplicant_id();
//                if(selectedContacts.size()>1){
//                    Toast.makeText(HomeActivity.this, "Select one contact at a time.", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    for (int i = 0; i < selectedContacts.size(); i++) {
//
//                        // display += (i+1)+". "+selectedContacts.get(i).Email.toString()+"\n";
//                        String id = selectedContacts.get(i).id;
//                        String Email = selectedContacts.get(i).Email;
//                        String label = selectedContacts.get(i).label;
//                        String phone = selectedContacts.get(i).phone;
//                        String name = selectedContacts.get(i).name;
//                       String Apl_ID= SendInvitation(phone, ContactID, Email, name);
//                        webView.loadUrl("javascript:NewContact('" + Apl_ID + "');");
//                        Toast.makeText(HomeActivity.this, "Invitation Sent.", Toast.LENGTH_SHORT).show();
//
//                    }
//                    if (selectedContacts.size() > 0) {
//                        Toast.makeText(HomeActivity.this, "Invitation Sent.", Toast.LENGTH_SHORT).show();
//                    }
//                }

            }
            else if(requestCode==CONTACT_ADD_TO_EXISTING && resultCode==Activity.RESULT_OK){
                String number = "";
                String ContactName = "";
                String Email="";
                Uri uri = data.getData();
                String Vcard = "";

                if (data.getDataString() != null) {
                    FileResults = new Uri[]{
                            uri
                    };
                }
                String ContactNumberToAdd=appPreferences.getContactNumberToAdd();
                String ContactEmailToAdd=appPreferences.getContactEmailToAdd();
                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(uri, null,
                        null, null, null);
                if (cursor.moveToFirst()) {
                    String contactId =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String Raw_ContactID=getRawContactId(contactId);
                    ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValue(ContactsContract.Data.RAW_CONTACT_ID, Raw_ContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactNumberToAdd)

                                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                            .build());
                    ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValue(ContactsContract.Data.RAW_CONTACT_ID, Raw_ContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)

                            .withValue(ContactsContract.CommonDataKinds.Email.DATA,ContactEmailToAdd)
                            .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                            .build());
                    try {
                        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                        appPreferences.setContactNumberToAdd("");
                        appPreferences.setContactEmailToAdd("");
                    } catch (OperationApplicationException e) {
                        e.printStackTrace();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }


                }
                cursor.close();
            }
            else if(requestCode==CONTACT_PICK_REQUEST_ADD_PHONE_MESSAGE && resultCode==Activity.RESULT_OK){
                String ContactEmail=appPreferences.getContactEmailToAdd();
                String ContactNo=appPreferences.getContactNumberToAdd();
                String ContactID = appPreferences.getApplicant_id();
                String ContactName=appPreferences.getContactNameToAdd();
                String Apl_ID= SendInvitation(ContactNo, ContactID, ContactEmail, ContactName);
                webView.loadUrl("javascript:NewContact('" + Apl_ID + "');");
            }
        }
        else
        {
            if (requestCode == FCR) {
                if (null == mUM) return;

                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
                SIGN=1;
            }
        }


    }

//    void LoadFFmpegLibrary()
//    {
//        Context context=getApplicationContext();
//        if(ffmpeg==null)
//        {
//            ffmpeg = FFmpeg.getInstance(context);
//            try {
//                ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
//
//                    @Override
//                    public void onStart() {}
//
//                    @Override
//                    public void onFailure() {
//                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                        Toast.makeText(context, "Success:Loaded", Toast.LENGTH_SHORT).show();
//                        File ffmpeg1 = new File(getApplicationContext().getFilesDir(), "ffmpeg");
//                        try {
//                            Runtime.getRuntime().exec("chmod -R 777 " + ffmpeg1.getAbsolutePath()).waitFor();
//                        } catch (InterruptedException e) {
//                            Toast.makeText(context, "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        } catch (IOException e) {
//                            Toast.makeText(context, "error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFinish() {}
//                });
//            } catch (FFmpegNotSupportedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public String getAppDir() {
        String outputPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        outputPath += "/" + "vvvvv";
        File file = new File(outputPath);
        if (!file.exists()) {
            file.mkdir();
        }
        outputPath += "/" + "videocompress";
        file = new File(outputPath);
        if (!file.exists()) {
            file.mkdir();
        }
        return outputPath;
    }


    private Locale getLocale() {
        Configuration config = getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config){
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config){
        return config.getLocales().get(0);
    }

    public void VideoCompression(String tv_output,String tv_input,int index,String FileName)
    {
        VideoCompress.compressVideoLow(tv_input, tv_output, new VideoCompress.CompressListener() {
            @Override
            public void onStart() {
                //Toast.makeText(getApplicationContext(), "Started", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess() {
                FileCounter=FileCounter+1;
                File imgFile=new File(tv_output);
                FileResults[index]=Uri.fromFile(imgFile);
                if(imgFile.isFile()){
                    //Toast.makeText(getApplicationContext(), "File Created", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(float percent) {
                //tv_progress.setText(String.valueOf(percent) + "%");
                String File=FileName+":"+ String.valueOf(Math.round(percent))+ "%";
                DataManager.getInstance().setDialogtext( File);
                //Toast.makeText(getApplicationContext(), File+ "%", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void compressVideo(String[] command, final String outputFilePath,int index) {
//        Context context=getApplicationContext();
//        ProgressCalculator mProgressCalculator=null;
//        try {
//
//            FFmpeg.getInstance(context).execute(command, new FFmpegExecuteResponseHandler() {
//                @Override
//                public void onSuccess(String message) {
//                    FileCounter=FileCounter+1;
//                    File imgFile=new File(outputFilePath);
//                    FileResults[index]=Uri.fromFile(imgFile);
//                    if(imgFile.isFile()){
//                        Toast.makeText(context, "File Created", Toast.LENGTH_SHORT).show();
//                    }
//                    //status = SUCCESS;
//                }
//
//                @Override
//                public void onProgress(String message) {
//                    //status = RUNNING;
//                    Log.e("VideoCronProgress", message);
//                    int progress = mProgressCalculator.calcProgress(message);
//                    Log.e("VideoCronProgress == ", progress + "..");
//                    if (progress != 0 && progress <= 100) {
//                        if (progress >= 99) {
//                            progress = 100;
//
//                        }
//                        //listener.onProgress(progress);
//                    }
//                }
//
//
//                @Override
//                public void onFailure(String message) {
//                    //status = FAILED;
//
//                    Toast.makeText(context, "Failed:"+message, Toast.LENGTH_SHORT).show();
//                    Log.e("VideoCompressor", message);
////                    if (listener != null) {
////                        listener.onFailure("Error : " + message);
////                    }
//                }
//
//                @Override
//                public void onStart() {
//
//                }
//
//                @Override
//                public void onFinish() {
//                    Log.e("VideoCronProgress", "finished");
//
////                    isFinished = true;
////                    if (listener != null) {
////                        listener.compressionFinished(status, true, outputFilePath);
////                    }
//                }
//            });
//        } catch (FFmpegCommandAlreadyRunningException e) {
//            //status = FAILED;
//            String errorMessage = e.getMessage();
//            Toast.makeText(context, "Error:"+errorMessage, Toast.LENGTH_SHORT).show();
////            if (listener != null) {
////                listener.onFailure("Error : " + e.getMessage());
////            }
//        }
//    }


    public void DeletePost(String FeedID,String DeleteMode,String ContactID){
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("DeleteMode", DeleteMode));
                args.add(new BasicNameValuePair("ContactID", ContactID));
                args.add(new BasicNameValuePair("FeedID", FeedID));
                String URL = "https://www.grapevine.work/";

                String methodName = "workplace/DeletePost";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    String Object = response.toString();
//                            JSONObject object = new JSONObject(response.toString());
//                            final String CityID = object.get("CityID").toString();
//                            final String LocalityID=object.get("LocalityID").toString();
                    //  InsertCurrentLocation(CityID,LocalityID,address,Pincode,Latitude,Longitude,"9",appPreferences.getStuID().replace("+91",""));
                    //Bookingdetails(listView);
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public String getRawContactId(String contactId)
    {
        String res = "";
        Uri uri = ContactsContract.RawContacts.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.RawContacts._ID};
        String selection = ContactsContract.RawContacts.CONTACT_ID + " = ?";
        String[] selectionArgs = new String[]{ contactId };
        Cursor c = getContentResolver().query(uri, projection, selection, selectionArgs, null);

        if(c != null && c.moveToFirst())
        {
            res = c.getString(c.getColumnIndex(ContactsContract.RawContacts._ID));
            c.close();
        }

        return res;
    }
    ArrayList<String> vCard;
    public void get(Cursor cursor)
    {
//        String lookupKey = cursor.getString(cursor
//                .getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
//
//
//        Uri uri = Uri.withAppendedPath(
//                ContactsContract.Contacts.CONTENT_VCARD_URI,
//                lookupKey);
//        AssetFileDescriptor fd;
//        try {
//            fd = this.getContentResolver()
//                    .openAssetFileDescriptor(uri, "r");
//            FileInputStream fis = fd.createInputStream();
//            byte[] b = new byte[(int) fd.getDeclaredLength()];
//            fis.read(b);
//            String vCard1 = new String(b);
//            vCard.add(vCard1);
//            Log.i(TAG, vCard1);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //cursor.moveToFirst();
//        @SuppressLint("Range")
        String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_VCARD_URI, lookupKey);
        AssetFileDescriptor fd;
        try {
            fd = this.getContentResolver().openAssetFileDescriptor(uri, "r");

            FileInputStream fis = fd.createInputStream();
            byte[] buf = readBytes(fis);
            String vcardstring= new String(buf);
            vCard.add(vcardstring);

//            String storage_path = Environment.getExternalStorageDirectory().toString() + File.separator + vfile;
//            FileOutputStream mFileOutputStream = new FileOutputStream(storage_path, false);
//            mFileOutputStream.write(vcardstring.toString().getBytes());

        } catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    public byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    private void CreateFeedForApplication(String Link,String Size,String CloudApplicationID,String FileName){
        String FeedByContactid=appPreferences.getApplicant_id();
        String ActivityID=appPreferences.getActivityID();
        String FeedChannelID=appPreferences.getFriendFeedChannelID();
        String FeedID=crm_insert_feed(FeedChannelID, "0", "",
                "10",
                "1", "3",
                "1", "1", "0",
                FeedByContactid,  "", "Feelings", "",
                "",  "0",  "0",  "1",
                ActivityID, "0",  "0","","");
        crm_insert_feed_objects( "9",  FeedID, Link,
                FeedChannelID,  Size,  "4",  "0", "0.0",
                "2",CloudApplicationID,"","","10",FileName);
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = getApplicationContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

//
//    public Bitmap ConvertToBitmap(String path){
//        Bitmap bitmapImage = BitmapFactory.decodeFile(path);
//        int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
//        Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
//        //your_imageview.setImageBitmap(scaled);
//        return scaled;
//    }

//----Compression Of Video--//
    //--Start--//
//public File mTranscodeOutputFile;
//    public long mTranscodeStartTime;
//    public Uri mTranscodeInputUri1;
//    public Uri mTranscodeInputUri2;
//    public Uri mTranscodeInputUri3;
//    public Uri mAudioReplacementUri;
//    public long mTrimStartUs = 0;
//    public long mTrimEndUs = 0;
//    public Future<Void> mTranscodeFuture;
////    public TrackStrategy mTranscodeVideoStrategy;
////    public TrackStrategy mTranscodeAudioStrategy;
//    public boolean mIsAudioOnly;
//    float speed=1F;
//    public void transcode() {
//        // Create a temporary file for output.
//        syncParameters();
//        try {
//            File outputDir = new File(getExternalFilesDir(null), "outputs");
//            //noinspection ResultOfMethodCallIgnored
//            outputDir.mkdir();
//            mTranscodeOutputFile = File.createTempFile("transcode_test", ".mp4", outputDir);
//           // LOG.i("Transcoding into " + mTranscodeOutputFile);
//        } catch (IOException e) {
//            //LOG.e("Failed to create temporary file.", e);
//            Toast.makeText(this, "Failed to create temporary file.", Toast.LENGTH_LONG).show();
//            return ;
//        }
//
//        int rotation=0;
////        switch (mVideoRotationGroup.getCheckedRadioButtonId()) {
////            case R.id.rotation_90: rotation = 90; break;
////            case R.id.rotation_180: rotation = 180; break;
////            case R.id.rotation_270: rotation = 270; break;
////            default: rotation = 0;
////        }
////
////        float speed;
////        switch (mSpeedGroup.getCheckedRadioButtonId()) {
////            case R.id.speed_05x: speed = 0.5F; break;
////            case R.id.speed_2x: speed = 2F; break;
////            default: speed = 1F;
////        }
//
//        // Launch the transcoding operation.
//        mTranscodeStartTime = SystemClock.uptimeMillis();
//        //setIsTranscoding(true);
//        DataSink sink = new DefaultDataSink(mTranscodeOutputFile.getAbsolutePath());
//        TranscoderOptions.Builder builder = Transcoder.into(sink);
//        if (mAudioReplacementUri == null) {
//            if (mTranscodeInputUri1 != null) {
//                DataSource source = new UriDataSource(this, mTranscodeInputUri1);
//                builder.addDataSource(new TrimDataSource(source, mTrimStartUs, mTrimEndUs));
//            }
//            if (mTranscodeInputUri2 != null) builder.addDataSource(this, mTranscodeInputUri2);
//            if (mTranscodeInputUri3 != null) builder.addDataSource(this, mTranscodeInputUri3);
//        } else {
//            if (mTranscodeInputUri1 != null) {
//                DataSource source = (DataSource) new UriDataSource(this, mTranscodeInputUri1);
//                builder.addDataSource(TrackType.VIDEO, new TrimDataSource((com.otaliastudios.transcoder.source.DataSource) source, mTrimStartUs, mTrimEndUs));
//            }
//            if (mTranscodeInputUri2 != null) builder.addDataSource(TrackType.VIDEO, this, mTranscodeInputUri2);
//            if (mTranscodeInputUri3 != null) builder.addDataSource(TrackType.VIDEO, this, mTranscodeInputUri3);
//            builder.addDataSource(TrackType.AUDIO, this, mAudioReplacementUri);
//        }
//        mTranscodeFuture = builder.setListener(this)
//                .setAudioTrackStrategy(mTranscodeAudioStrategy)
//                .setVideoTrackStrategy(mTranscodeVideoStrategy)
//                .setVideoRotation(rotation)
//                .setValidator(new DefaultValidator() {
//                    @Override
//                    public boolean validate(@NonNull TrackStatus videoStatus, @NonNull TrackStatus audioStatus) {
//                        mIsAudioOnly = !videoStatus.isTranscoding();
//                        return super.validate(videoStatus, audioStatus);
//                    }
//                })
//               .setSpeed(speed)
//                .transcode();
//       // return mTranscodeOutputFile;
//    }
//
//    private void syncParameters() {
//        int channels;
//
//            channels = DefaultAudioStrategy.CHANNELS_AS_INPUT;
//
//        int sampleRate;
//
//            sampleRate = DefaultAudioStrategy.SAMPLE_RATE_AS_INPUT;
//             boolean removeAudio;
//
//             removeAudio = false;
//                if (removeAudio) {
//            mTranscodeAudioStrategy = new RemoveTrackStrategy();
//        } else {
//            mTranscodeAudioStrategy = DefaultAudioStrategy.builder()
//                    .channels(channels)
//                    .sampleRate(sampleRate)
//                    .build();
//        }
//
//        int frames;
//
//             frames = DefaultVideoStrategy.DEFAULT_FRAME_RATE;
//
//        float fraction;
//
//             fraction = 1F;
//
//        float aspectRatio;
//
//            aspectRatio = 0F;
//
//        mTranscodeVideoStrategy = new DefaultVideoStrategy.Builder()
//                .addResizer(aspectRatio > 0 ? new AspectRatioResizer(aspectRatio) : new PassThroughResizer())
//                .addResizer(new FractionResizer(fraction))
//                .frameRate(frames)
//                .build();
//
//        try {
//            mTrimStartUs = Long.valueOf("0") * 1000000;
//        } catch (NumberFormatException e) {
//            mTrimStartUs = 0;
//            //LOG.w("Failed to read trimStart value.");
//        }
//        try {
//            mTrimEndUs = Long.valueOf("0") * 1000000;
//        } catch (NumberFormatException e) {
//            mTrimEndUs = 0;
//            //LOG.w("Failed to read trimEnd value.");
//        }
//        if (mTrimStartUs < 0) mTrimStartUs = 0;
//        if (mTrimEndUs < 0) mTrimEndUs = 0;
//    }
//
//    public void onTranscodeProgress(double progress) {
//        String abc="";
////        if (progress < 0) {
////            mProgressView.setIndeterminate(true);
////        } else {
////            mProgressView.setIndeterminate(false);
////            mProgressView.setProgress((int) Math.round(progress * PROGRESS_BAR_MAX));
////        }
//    }
//
//
//    public void onTranscodeCompleted(int successCode) {
//        if (successCode == Transcoder.SUCCESS_TRANSCODED) {
//            //LOG.w("Transcoding took " + (SystemClock.uptimeMillis() - mTranscodeStartTime) + "ms");
//            onTranscodeFinished(true, "Transcoded file placed on " + mTranscodeOutputFile,mTranscodeOutputFile);
//
//
////            File file = mTranscodeOutputFile;
////            String type = mIsAudioOnly ? "audio/mp4" : "video/mp4";
////            Uri uri = FileProvider.getUriForFile(this,
////                    FILE_PROVIDER_AUTHORITY, file);
////            startActivity(new Intent(Intent.ACTION_VIEW)
////                    .setDataAndType(uri, type)
////                    .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION));
//        } else if (successCode == Transcoder.SUCCESS_NOT_NEEDED) {
//            //LOG.i("Transcoding was not needed.");
//            onTranscodeFinished(true, "Transcoding not needed, source  file untouched.",null);
//        }
//
//    }
//
//
//    public void onTranscodeCanceled() {
//        onTranscodeFinished(false, "Transcoder canceled.",null);
//    }
//
//
//    public void onTranscodeFailed(@NonNull Throwable exception) {
//        onTranscodeFinished(false, "Transcoder error occurred. " + exception.getMessage(),null);
//    }
//
//    private void onTranscodeFinished(boolean isSuccess, String toastMessage,File file) {
////        mProgressView.setIndeterminate(false);
////        mProgressView.setProgress(isSuccess ? PROGRESS_BAR_MAX : 0);
////        setIsTranscoding(false);
//
//
//
//        if(file!=null){
//            Uri Path=Uri.parse(file.getAbsolutePath());
//            Uri[] results = new Uri[]{
//                    Path
//            };
//            Intent i=new Intent();
//            i.setData(Path);
//            Intent data = new Intent();
//            data.setData(Path);
//            setResult(SELECT_VIDEO_REQUEST, data);
//            finish();
////            Uri ipath=i.getData();
////            startActivityForResult(i, SELECT_VIDEO_REQUEST);
//            Toast.makeText(HomeActivity.this, toastMessage, Toast.LENGTH_LONG).show();
////            mUMA.onReceiveValue(results);
////            mUMA = null;
////            SIGN=1;
//        }
//        else
//        {
//            Toast.makeText(HomeActivity.this, toastMessage, Toast.LENGTH_LONG).show();
//        }
//
//        //return;
//
//       // return file;
//    }
//
//    //--End--//
//    //----Compression Of Video--//
//


    Bitmap bitmap;
    //method to get the file path from uri

    //----Compression Of Image ---//
    //--Start--////


    //Image Compression Code
    private String getRealPathFromURI_(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }

    }
    //PATH
    public static String getRealPathFromURI(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    //PATH

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public File getFilename(String FileOriginalName) {
        // String extstoragedir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Grapevine/Sent";
        String uriSting="";
        //File file = new File(Environment.getExternalStorageDirectory().getPath(), "SureBet");
        String path = Environment.getDataDirectory().getAbsolutePath().toString() + "/storage/emulated/0/Grapevine";
        File mFolder = new File(path);
        if (!mFolder.exists()) {
            Boolean a=mFolder.mkdir();
        }
        File file = new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Grapevine/Sent/");
        if (!file.exists()) {
            try{
                Boolean a=  file.mkdirs();
                //Boolean a=  file.createNewFile();
            }
            catch (Exception ex){
                ex.toString();
            }

        }
        try {
            //String uriSting = (file.getAbsolutePath() + "/" + FileOriginalName + ".png");

            uriSting = getContentResolver().openInputStream(Uri.fromFile(file))+"/" + FileOriginalName + ".png" ;
            //getContentResolver().openOutputStream(Uri.fromFile(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("IMAGES",""+uriSting);
        return file;
    }

    private String getFileExtension(String name) {
        // String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
    public String compressImage(String imageUri) {
        Uri contentUri = Uri.parse(imageUri);
        String filePath=FileUtil.getPath(this,contentUri);
        //String filePath = getRealPathFromURI(this,contentUri);
        int index=filePath.lastIndexOf("/");
        String FileOriginalName="";
        if(index!=-1){
            FileOriginalName=filePath.substring(index+1);
            String Extension=  getFileExtension(FileOriginalName);
            FileOriginalName= FileOriginalName.replaceFirst("[.][^.]+$", "");;

            if(Extension.contains("3gp") || Extension.contains("mpg") || Extension.contains("mpeg") || Extension.contains("mpe") || Extension.contains("mp4") || Extension.contains("avi"))
            {
                return "";
            }
        }

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        //String mypath=getRealPathFromURI(contentUri);
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = false;
        options.inSampleSize=1;
        Bitmap bmp=null;
        try {
            InputStream in=getContentResolver().openInputStream(contentUri);
            bmp=BitmapFactory.decodeStream(in,null,options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File imgFile = new  File(filePath);
        if(imgFile.exists()){

            //   bmp = BitmapFactory.decodeFile(filePath,options);

            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
//            bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
//            imageView.setImageBitmap(bitmap);
        }
        // bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;                actualWidth = (int) (imgRatio * actualWidth);               actualHeight = (int) maxHeight;             } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

//        try {
////          load the bitmap from its path
//            bmp = BitmapFactory.decodeFile(filePath, options);
//        } catch (OutOfMemoryError exception) {
//            exception.printStackTrace();
//
//        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
//            exif = new ExifInterface(filePath);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                exif = new ExifInterface(getContentResolver().openInputStream(contentUri));
                int orientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, 0);
                Log.d("EXIF", "Exif: " + orientation);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 3) {
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + orientation);
                } else if (orientation == 8) {
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + orientation);
                }
                scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                        scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                        true);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        File filename = getFilename(FileOriginalName);
        String uriSting="";

        uriSting =filename.getAbsolutePath()+"/"+FileOriginalName+".png";

        try {
            out = new FileOutputStream(uriSting,false);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //display
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(uriSting)));
            int heigth=bitmap.getHeight();
            int width=bitmap.getWidth();
//            imageView1.setImageBitmap(bitmap);
//            imgPath = filename;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR","Error "+e.getMessage());
        }
        return uriSting;

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }
    //--END--////

    //----Compression Of Image ---//

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            currentUrl=url;
            view.loadUrl(url);
            return true;
        }


    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            //Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    //flipscreen not loading again
    @Override
    public void onConfigurationChanged(Configuration newConfig){

        super.onConfigurationChanged(newConfig);

        // if(android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0) == 1){
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            // Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
//        }
//        else{
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
        // Checks the orientation of the screen

    }
    @Override
    protected void onSaveInstanceState(Bundle outState )
    {
        super.onSaveInstanceState(outState);
        //webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
       // webView.restoreState(savedInstanceState);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (!cd.isConnectingToInternet())
        {
            Intent i = new Intent(HomeActivity.this,noConnection.class);
            startActivity(i);
            finish();
        }

        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
                case KeyEvent.KEYCODE_BACK:
                    if(webView.canGoBack() == true){
                        String myurl="";
                        myurl=webView.getUrl();
                        if(myurl.contains("backtologin"))
                        {
                            //String token=FirebaseInstanceId.getInstance().getToken();
                            //DeleteAppToken(appPreferences.getApplicant_id(),token);
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
                            appPreferences.setApplicant_id("");

                                appPreferences.setLoginUserMobileNo("");

                            startActivity(new Intent(HomeActivity.this, SendOTPMSG91.class));
                            finish();
                        }
                        else
                        {
                            webView.goBack();
                        }

                    }
                    else{
                        webView.loadUrl("javascript:goAwayPopup();");
                        if (doubleBackToExitPressedOnce) {
                            finishAffinity();

                        }
                        return true;
//                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                        builder.setMessage("Are you sure you want to exit?")
//                                .setCancelable(false)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        finishAffinity();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog alert = builder.create();
//                        alert.show();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);


    }

    private Boolean exit = false;
    @Override
    public void onBackPressed()
    {
        if (!cd.isConnectingToInternet()) {

            finish();
        }
        else
        {
            if(webView.canGoBack() == true){

                String myurl="";
                myurl=webView.getUrl();
                if(myurl.contains("backtologin"))
                {
                    //String token=FirebaseInstanceId.getInstance().getToken();
                    //DeleteAppToken(appPreferences.getApplicant_id(),token);
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
                    startActivity(new Intent(HomeActivity.this, SendOTPMSG91.class));
                    finish();
                }
                else
                {
                    webView.goBack();
                }
            }

            else{
                webView.loadUrl("javascript:goAwayPopup();");
                if (doubleBackToExitPressedOnce) {
                    finishAffinity();
                    return;
                }


                return;
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage("Are you sure you want to exit?")
//                        .setCancelable(false)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                finishAffinity();
//                            }
//                        })
//                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = builder.create();
//                alert.show();
            }
        }
    }
    private void signOut() {
        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("577914942480-1fsep0lae8kgnsbu771ubaelb1t04rcg.apps.googleusercontent.com")
                .build();
        googleSignInClient = GoogleSignIn.getClient( this,gso);
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(HomeActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();;
                        //txtEmail.setText("Google Logged out");
                    }
                });
    }

    public void crm_insert_feed_objects(String WebsiteID, String FeedID, String FeedObjectFilePath,
                                        String FeedChannelID, String FileSize, String ObjectSourceID,
                                        String ObjectID,String PlayDuration,String FeedObjectAccessTypeID,
                                        String CloudApplicationID,String VideoImagePath,String Folder,
                                        String FeedObjectTypeID,String FileName)
    {
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


                args.add(new BasicNameValuePair("WebsiteID", WebsiteID));
                args.add(new BasicNameValuePair("FeedID", FeedID));
                args.add(new BasicNameValuePair("FeedObjectFilePath", FeedObjectFilePath));
                args.add(new BasicNameValuePair("FeedChannelID", FeedChannelID));
                args.add(new BasicNameValuePair("FileSize", FileSize));
                args.add(new BasicNameValuePair("ObjectSourceID", ObjectSourceID));
                args.add(new BasicNameValuePair("FeedObjectTypeID", FeedObjectTypeID));
                args.add(new BasicNameValuePair("ObjectID", ObjectID));
                args.add(new BasicNameValuePair("PlayDuration", PlayDuration));
                args.add(new BasicNameValuePair("FeedObjectAccessTypeID", FeedObjectAccessTypeID));
                args.add(new BasicNameValuePair("CloudApplicationID", CloudApplicationID));
                args.add(new BasicNameValuePair("VideoImagePath", VideoImagePath));
                args.add(new BasicNameValuePair("Folder", Folder));
                args.add(new BasicNameValuePair("SourceFileName", FileName));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/crm_insert_feed_object";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    FeedID=response.toString();
                    FeedID=FeedID.replaceAll("\"","").replaceAll("\n","");
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public String crm_insert_feed_message_app_reference(String ActionType,String FeedID,String ActivityChannelID,String MessageID,String Status){
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


                args.add(new BasicNameValuePair("ActionType", ActionType));
                args.add(new BasicNameValuePair("FeedID", FeedID));
                args.add(new BasicNameValuePair("ActivityChannelID", ActivityChannelID));
                args.add(new BasicNameValuePair("MessageID", MessageID));
                args.add(new BasicNameValuePair("Status", Status));


                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/crm_insert_feed_message_app_reference";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    FeedID=response.toString();
                    //FeedID=FeedID.replaceAll("\"","").replaceAll("\n","");
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return "";
    }
    public String SendMediaMessageThroughWhatsAppApi(String ToMobile,String MediaLink,String MediaType,String Location_Contact_Details,
      String MessageToFeedChannelID,String EntityFeedChannelID,String CRMActivityChannelID,String PageFeedChannelID,String FeedID){

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


                args.add(new BasicNameValuePair("ToMobile", ToMobile));
                args.add(new BasicNameValuePair("MediaLink", MediaLink));
                args.add(new BasicNameValuePair("MediaType", MediaType));
                args.add(new BasicNameValuePair("Location_Contact_Details", Location_Contact_Details));
                args.add(new BasicNameValuePair("MessageToFeedChannelID", MessageToFeedChannelID));
                args.add(new BasicNameValuePair("EntityFeedChannelID", EntityFeedChannelID));
                args.add(new BasicNameValuePair("CRMActivityChannelID", CRMActivityChannelID));
                args.add(new BasicNameValuePair("PageFeedChannelID", PageFeedChannelID));
                args.add(new BasicNameValuePair("FeedID", FeedID));


                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/SendMediaMessageThroughWhatsAppApi";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    String MessageID=response.toString();
                    MessageID=MessageID.replaceAll("\"","").replaceAll("\n","");
                    crm_insert_feed_message_app_reference("Insert",FeedID,CRMActivityChannelID, MessageID,"");
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return "";
    }

    public String crm_insert_feed(String FeedChannelID, String WorkTeamID, String FormatText, String FeedObjectTypeID,
                                  String FeedByID, String FeedVisibilityID, String ApplicationFeedEventType_ID,
                                  String Target_ID, String BOTID, String FeedByContactid, String MemberContactID,
                                  String Type, String VisibilityExceptFriendList, String VisibilitySpecificFriendList,
                                  String CustomFriendListID, String IsMessageFeed, String IsPosted, String ActivityID,
                                  String ListingID, String FeedID,String FeedbyFeedChannelID,String AgentMode) {


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


                args.add(new BasicNameValuePair("FeedChannelID", FeedChannelID));
                args.add(new BasicNameValuePair("WorkTeamID", WorkTeamID));
                args.add(new BasicNameValuePair("FeedObjectTypeID", FeedObjectTypeID));
                args.add(new BasicNameValuePair("FeedByID", FeedByID));
                args.add(new BasicNameValuePair("FeedVisibilityID", FeedVisibilityID));
                args.add(new BasicNameValuePair("ApplicationFeedEventType_ID", ApplicationFeedEventType_ID));
                args.add(new BasicNameValuePair("FormatText", FormatText));
                args.add(new BasicNameValuePair("Target_ID", Target_ID));
                args.add(new BasicNameValuePair("BOTID", BOTID));
                args.add(new BasicNameValuePair("FeedByContactid", FeedByContactid));
                args.add(new BasicNameValuePair("MemberContactID", MemberContactID));
                args.add(new BasicNameValuePair("Type", Type));
                args.add(new BasicNameValuePair("VisibilityExceptFriendList", VisibilityExceptFriendList));
                args.add(new BasicNameValuePair("VisibilitySpecificFriendList", VisibilitySpecificFriendList));
                args.add(new BasicNameValuePair("CustomFriendListID", CustomFriendListID));
                args.add(new BasicNameValuePair("IsMessageFeed", IsMessageFeed));
                args.add(new BasicNameValuePair("IsPosted", IsPosted));
                args.add(new BasicNameValuePair("ActivityID", ActivityID));
                args.add(new BasicNameValuePair("ListingID", ListingID));
                args.add(new BasicNameValuePair("FeedID", FeedID));
                args.add(new BasicNameValuePair("FeedbyFeedChannelID", FeedbyFeedChannelID));
                args.add(new BasicNameValuePair("AgentMode", AgentMode));


                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/crm_Insert_Feed_Post";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    FeedID=response.toString();
                    FeedID=FeedID.replaceAll("\"","").replaceAll("\n","");
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return FeedID;
    }
    public String crm_insert_feed_Short_url(String ActionType, String Url, String CodeUrl) {
        String OriginalUrl="";
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values = new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("ActionType", ActionType));
                args.add(new BasicNameValuePair("Url", Url));
                args.add(new BasicNameValuePair("CodeUrl", CodeUrl));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/crm_insert_feed_Short_url";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
                    OriginalUrl = response.toString();
                    OriginalUrl = OriginalUrl.replaceAll("\"", "").replaceAll("\n", "").replaceAll("%","&");
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return OriginalUrl;
    }
    public void UpdateToken(String applicantid) {
        String token = FirebaseInstanceId.getInstance().getToken();
        String AndroidID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values=new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("ContactID", applicantid));
                args.add(new BasicNameValuePair("DeviceID", token));
                args.add(new BasicNameValuePair("WebsiteID", "9"));
                args.add(new BasicNameValuePair("AndroidID", AndroidID));
                args.add(new BasicNameValuePair("DeviceOS", "Android"));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/InsertUpdateContactAlertKey";

                dataFromNetwork.setConfig(URL, methodName, args);
                dataFromNetwork.execute();
//                Object response = dataFromNetwork.execute().get().toString();
//                if (response != null) {
////                    FeedChannelID=response.toString();
////                    FeedChannelID=FeedChannelID.replaceAll("\"","").replaceAll("\n","");
//                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }
    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        if(SIGN!=1)
        {
            cd = new ConnectionDetector(getApplicationContext());
            GPStracker g=new GPStracker(getApplicationContext());
            Location l=g.getLocation();
            if(l!=null){
                double Lat=l.getLatitude();

                Latitude=String.valueOf(Lat);
                //txtLatitude.setText(Latitude);
                appPreferences.setSchoolID(Latitude);


                double Long=l.getLongitude();

                Longitude=String.valueOf(Long);
                appPreferences.setStatffID(Longitude);
                // txtLongitude.setText(Longitude);
                // Toast.makeText(getApplicationContext(),"Lat:"+Lat+"\n Long:"+Long,Toast.LENGTH_LONG).show();
            }
            String myurl="";
            myurl=webView.getUrl();
            stuId=appPreferences.getStuID().toString();
            if (cd.isConnectingToInternet()) {
                if (googleApiClient != null) {
                    googleApiClient.connect();
                }

            }
            else{
                Intent i = new Intent(HomeActivity.this,noConnection.class);
                startActivity(i);
                finish();
            }

        }
        SIGN=0;

    }
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        this.openFileChooser(uploadMsg, "*/*");
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        this.openFileChooser(uploadMsg, acceptType, null);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        HomeActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
    }

    public void PlayRingtone() {
        if (!CallAccepted) {
            RingtonePlay.playAudio(this);
        } else {
            CallAccepted = false;
        }
    }

    public void startCapturing(MediaProjection mediaProjection) {
        VideoEncodeConfig video = createVideoConfig();
        AudioEncodeConfig audio = createAudioConfig(); // audio can be null
        if (video == null) {
            toast(getString(R.string.create_screenRecorder_failure));
            return;
        }

        File dir = getSavingDir();
        if (!dir.exists() && !dir.mkdirs()) {
            cancelRecorder();
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
        final File file = new File(dir, "Screenshots-" + format.format(new Date())
                + "-" + video.width + "x" + video.height + ".mp4");
        Log.d("@@", "Create recorder with :" + video + " \n " + audio + "\n " + file);
        mRecorder = newRecorder(mediaProjection, video, audio, file);
        if (hasPermissions()) {
            startRecorder();
        } else {
            cancelRecorder();
        }
    }

    public MediaProjection.Callback mProjectionCallback = new MediaProjection.Callback() {
        @Override
        public void onStop() {
            if (mRecorder != null) {
                stopRecorder();
            }
        }
    };

    public ScreenRecorder newRecorder(MediaProjection mediaProjection, VideoEncodeConfig video,
                                      AudioEncodeConfig audio, File output) {
        final VirtualDisplay display = getOrCreateVirtualDisplay(mediaProjection, video);
        ScreenRecorder r = new ScreenRecorder(video, audio, display, output.getAbsolutePath());
        r.setCallback(new ScreenRecorder.Callback() {
            long startTime = 0;

            @Override
            public void onStop(Throwable error) {
                runOnUiThread(() -> stopRecorder());
                if (error != null) {
                    toast("Recorder error ! See logcat for more details");
                    error.printStackTrace();
                    output.delete();
                } else {
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                            .addCategory(Intent.CATEGORY_DEFAULT)
                            .setData(Uri.fromFile(output));
                    sendBroadcast(intent);
                }
            }

            @Override
            public void onStart() {
                mNotifications.recording(0);
            }

            @Override
            public void onRecording(long presentationTimeUs) {
                if (startTime <= 0) {
                    startTime = presentationTimeUs;
                }
                long time = (presentationTimeUs - startTime) / 1000;
                mNotifications.recording(time);
            }
        });
        return r;
    }


    public VirtualDisplay getOrCreateVirtualDisplay(MediaProjection mediaProjection, VideoEncodeConfig config) {
        if (mVirtualDisplay == null) {
            mVirtualDisplay = mediaProjection.createVirtualDisplay("ScreenRecorder-display0",
                    config.width, config.height, 1 /*dpi*/,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC,
                    null /*surface*/, null, null);
        } else {
            // resize if size not matched
            Point size = new Point();
            mVirtualDisplay.getDisplay().getSize(size);
            if (size.x != config.width || size.y != config.height) {
                mVirtualDisplay.resize(config.width, config.height, 1);
            }
        }
        return mVirtualDisplay;
    }

    public AudioEncodeConfig createAudioConfig() {
        if (!mAudioToggle.isChecked()) return null;
        String codec = getSelectedAudioCodec();
        if (codec == null) {
            return null;
        }
        int bitrate = getSelectedAudioBitrate();
        int samplerate = getSelectedAudioSampleRate();
        int channelCount = getSelectedAudioChannelCount();
        int profile = getSelectedAudioProfile();

        return new AudioEncodeConfig(codec, AUDIO_AAC, bitrate, samplerate, channelCount, profile);
    }

    public VideoEncodeConfig createVideoConfig() {
        final String codec = getSelectedVideoCodec();
        if (codec == null) {
            // no selected codec ??
            return null;
        }
        // video size
        int[] selectedWithHeight = getSelectedWithHeight();
        boolean isLandscape = isLandscape();
        int width = selectedWithHeight[isLandscape ? 0 : 1];
        int height = selectedWithHeight[isLandscape ? 1 : 0];
        int framerate = getSelectedFramerate();
        int iframe = getSelectedIFrameInterval();
        int bitrate = getSelectedVideoBitrate();
        MediaCodecInfo.CodecProfileLevel profileLevel = getSelectedProfileLevel();
        return new VideoEncodeConfig(width, height, bitrate,
                framerate, iframe, codec, VIDEO_AVC, profileLevel);
    }

    public static File getSavingDir() {
        return new File(getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
                "Screenshots");
    }

    public void requestMediaProjection() {
        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION);
    }

    public void bindViews(Dialog dialog) {
        mButton = dialog.findViewById(R.id.record_button);
        mButton.setOnClickListener(this::onButtonClick);

        mVideoCodec = dialog.findViewById(R.id.video_codec);
        mVieoResolution = dialog.findViewById(R.id.resolution);
        mVideoFramerate = dialog.findViewById(R.id.framerate);
        mIFrameInterval = dialog.findViewById(R.id.iframe_interval);
        mVideoBitrate = dialog.findViewById(R.id.video_bitrate);
        mOrientation = dialog.findViewById(R.id.orientation);

        mAudioCodec = dialog.findViewById(R.id.audio_codec);
        mVideoProfileLevel = dialog.findViewById(R.id.avc_profile);
        mAudioBitrate = dialog.findViewById(R.id.audio_bitrate);
        mAudioSampleRate = dialog.findViewById(R.id.sample_rate);
        mAudioProfile = dialog.findViewById(R.id.aac_profile);
        mAudioChannelCount = dialog.findViewById(R.id.audio_channel_count);

        mAudioToggle = dialog.findViewById(R.id.with_audio);
        mAudioToggle.setOnCheckedChangeListener((buttonView, isChecked) ->
                dialog.findViewById(R.id.audio_format_chooser)
                        .setVisibility(isChecked ? View.VISIBLE : View.GONE)
        );

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mOrientation.setSelectedPosition(1);
        }

        mVideoCodec.setOnItemSelectedListener((view, position) -> onVideoCodecSelected(view.getSelectedItem()));
        mAudioCodec.setOnItemSelectedListener((view, position) -> onAudioCodecSelected(view.getSelectedItem()));
        mVieoResolution.setOnItemSelectedListener((view, position) -> {
            onResolutionChanged(position, view.getSelectedItem());
        });
        mVideoFramerate.setOnItemSelectedListener((view, position) -> {
            onFramerateChanged(position, view.getSelectedItem());
        });
        mVideoBitrate.setOnItemSelectedListener((view, position) -> {
            onBitrateChanged(position, view.getSelectedItem());
        });
        mOrientation.setOnItemSelectedListener((view, position) -> {
            onOrientationChanged(position, view.getSelectedItem());
        });
    }

    public void onButtonClick(View v) {
        if (mRecorder != null) {
            stopRecordingAndOpenFile(v.getContext());
        } else if (hasPermissions()) {
            if (mMediaProjection == null) {
                requestMediaProjection();
            } else {
                startCapturing(mMediaProjection);
            }
        } else if (Build.VERSION.SDK_INT >= M) {
            requestPermissions();
        } else {
            toast(getString(R.string.no_permission_to_write_sd_ard));
        }
    }

    public void startRecorder() {
        if (mRecorder == null) return;
        mRecorder.start();
        mButton.setText(getString(R.string.stop_recorder));
        registerReceiver(mStopActionReceiver, new IntentFilter(ACTION_STOP));
        moveTaskToBack(true);
    }

    public void stopRecorder() {
        mNotifications.clear();
        if (mRecorder != null) {
            mRecorder.quit();
        }
        mRecorder = null;
        mButton.setText(getString(R.string.restart_recorder));
        try {
            unregisterReceiver(mStopActionReceiver);
        } catch (Exception e) {
            //ignored
        }
    }

    public void cancelRecorder() {
        if (mRecorder == null) return;
        Toast.makeText(this, getString(R.string.permission_denied_screen_recorder_cancel), Toast.LENGTH_SHORT).show();
        stopRecorder();
    }

    @TargetApi(M)
    public void requestPermissions() {
        String[] permissions = mAudioToggle.isChecked()
                ? new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}
                : new String[]{WRITE_EXTERNAL_STORAGE};
        boolean showRationale = false;
        for (String perm : permissions) {
            showRationale |= shouldShowRequestPermissionRationale(perm);
        }
        if (!showRationale) {
            requestPermissions(permissions, REQUEST_PERMISSIONS);
            return;
        }
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.using_your_mic_to_record_audio))
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, (dialog, which) ->
                        requestPermissions(permissions, REQUEST_PERMISSIONS))
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    public boolean hasPermissions() {
        PackageManager pm = getPackageManager();
        String packageName = getPackageName();
        int granted = (mAudioToggle.isChecked() ? pm.checkPermission(RECORD_AUDIO, packageName) : PackageManager.PERMISSION_GRANTED)
                | pm.checkPermission(WRITE_EXTERNAL_STORAGE, packageName);
        return granted == PackageManager.PERMISSION_GRANTED;
    }

    public void onResolutionChanged(int selectedPosition, String resolution) {
        String codecName = getSelectedVideoCodec();
        MediaCodecInfo codec = getVideoCodecInfo(codecName);
        if (codec == null) return;
        MediaCodecInfo.CodecCapabilities capabilities = codec.getCapabilitiesForType(VIDEO_AVC);
        MediaCodecInfo.VideoCapabilities videoCapabilities = capabilities.getVideoCapabilities();
        String[] xes = resolution.split("x");
        if (xes.length != 2) throw new IllegalArgumentException();
        boolean isLandscape = isLandscape();
        int width = Integer.parseInt(xes[isLandscape ? 0 : 1]);
        int height = Integer.parseInt(xes[isLandscape ? 1 : 0]);

        double selectedFramerate = getSelectedFramerate();
        int resetPos = Math.max(selectedPosition - 1, 0);
        if (!videoCapabilities.isSizeSupported(width, height)) {
            mVieoResolution.setSelectedPosition(resetPos);
            toast(getString(R.string.codec_unsupported_size),
                    codecName, width, height, mOrientation.getSelectedItem());
            Log.w("@@", codecName +
                    " height range: " + videoCapabilities.getSupportedHeights() +
                    "\n width range: " + videoCapabilities.getSupportedHeights());
        } else if (!videoCapabilities.areSizeAndRateSupported(width, height, selectedFramerate)) {
            mVieoResolution.setSelectedPosition(resetPos);
            toast(getString(R.string.codec_unsupported_size_with_framerate),
                    codecName, width, height, mOrientation.getSelectedItem(), (int) selectedFramerate);
        }
    }

    public void onBitrateChanged(int selectedPosition, String bitrate) {
        String codecName = getSelectedVideoCodec();
        MediaCodecInfo codec = getVideoCodecInfo(codecName);
        if (codec == null) return;
        MediaCodecInfo.CodecCapabilities capabilities = codec.getCapabilitiesForType(VIDEO_AVC);
        MediaCodecInfo.VideoCapabilities videoCapabilities = capabilities.getVideoCapabilities();
        int selectedBitrate = Integer.parseInt(bitrate) * 1000;

        int resetPos = Math.max(selectedPosition - 1, 0);
        if (!videoCapabilities.getBitrateRange().contains(selectedBitrate)) {
            mVideoBitrate.setSelectedPosition(resetPos);
            toast(getString(R.string.codec_unsupported_bitrate), codecName, selectedBitrate);
            Log.w("@@", codecName +
                    " bitrate range: " + videoCapabilities.getBitrateRange());
        }
    }

    public void onOrientationChanged(int selectedPosition, String orientation) {
        String codecName = getSelectedVideoCodec();
        MediaCodecInfo codec = getVideoCodecInfo(codecName);
        if (codec == null) return;
        MediaCodecInfo.CodecCapabilities capabilities = codec.getCapabilitiesForType(VIDEO_AVC);
        MediaCodecInfo.VideoCapabilities videoCapabilities = capabilities.getVideoCapabilities();
        int[] selectedWithHeight = getSelectedWithHeight();
        boolean isLandscape = selectedPosition == 1;
        int width = selectedWithHeight[isLandscape ? 0 : 1];
        int height = selectedWithHeight[isLandscape ? 1 : 0];
        int resetPos = Math.max(mVieoResolution.getSelectedItemPosition() - 1, 0);
        if (!videoCapabilities.isSizeSupported(width, height)) {
            mVieoResolution.setSelectedPosition(resetPos);
            toast(getString(R.string.codec_unsupported_size),
                    codecName, width, height, orientation);
            return;
        }

        int current = getResources().getConfiguration().orientation;

        if(android.provider.Settings.System.getInt(getContentResolver(), android.provider.Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            if (isLandscape && current == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (!isLandscape && current == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    public void onFramerateChanged(int selectedPosition, String rate) {
        String codecName = getSelectedVideoCodec();
        MediaCodecInfo codec = getVideoCodecInfo(codecName);
        if (codec == null) return;
        MediaCodecInfo.CodecCapabilities capabilities = codec.getCapabilitiesForType(VIDEO_AVC);
        MediaCodecInfo.VideoCapabilities videoCapabilities = capabilities.getVideoCapabilities();
        int[] selectedWithHeight = getSelectedWithHeight();
        int selectedFramerate = Integer.parseInt(rate);
        boolean isLandscape = isLandscape();
        int width = selectedWithHeight[isLandscape ? 0 : 1];
        int height = selectedWithHeight[isLandscape ? 1 : 0];

        int resetPos = Math.max(selectedPosition - 1, 0);
        if (!videoCapabilities.getSupportedFrameRates().contains(selectedFramerate)) {
            mVideoFramerate.setSelectedPosition(resetPos);
            toast(getString(R.string.codec_unsupported_with_framerate), codecName, selectedFramerate);
        } else if (!videoCapabilities.areSizeAndRateSupported(width, height, selectedFramerate)) {
            mVideoFramerate.setSelectedPosition(resetPos);
            toast(getString(R.string.codec_unsupported_size_with_framerate),
                    codecName, width, height, selectedFramerate);
        }
    }

    public void onVideoCodecSelected(String codecName) {
        MediaCodecInfo codec = getVideoCodecInfo(codecName);
        if (codec == null) {
            mVideoProfileLevel.setAdapter(null);
            return;
        }
        MediaCodecInfo.CodecCapabilities capabilities = codec.getCapabilitiesForType(VIDEO_AVC);

        resetAvcProfileLevelAdapter(capabilities);
    }


    public void resetAvcProfileLevelAdapter(MediaCodecInfo.CodecCapabilities capabilities) {
        MediaCodecInfo.CodecProfileLevel[] profiles = capabilities.profileLevels;
        if (profiles == null || profiles.length == 0) {
            mVideoProfileLevel.setEnabled(false);
            return;
        }
        mVideoProfileLevel.setEnabled(true);
        String[] profileLevels = new String[profiles.length + 1];
        profileLevels[0] = "Default";
        for (int i = 0; i < profiles.length; i++) {
            profileLevels[i + 1] = Utils.avcProfileLevelToString(profiles[i]);
        }

        SpinnerAdapter old = mVideoProfileLevel.getAdapter();
        if (old == null || !(old instanceof ArrayAdapter)) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.addAll(profileLevels);
            mVideoProfileLevel.setAdapter(adapter);
        } else {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) old;
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.addAll(profileLevels);
            adapter.notifyDataSetChanged();
        }
    }

    public void onAudioCodecSelected(String codecName) {
        MediaCodecInfo codec = getAudioCodecInfo(codecName);
        if (codec == null) {
            mAudioProfile.setAdapter(null);
            mAudioSampleRate.setAdapter(null);
            mAudioBitrate.setAdapter(null);
            return;
        }
        MediaCodecInfo.CodecCapabilities capabilities = codec.getCapabilitiesForType(AUDIO_AAC);

        resetAudioBitrateAdapter(capabilities);
        resetSampleRateAdapter(capabilities);
        resetAacProfileAdapter(capabilities);
        restoreSelections(mAudioBitrate, mAudioSampleRate, mAudioProfile);
    }

    public void resetAacProfileAdapter(MediaCodecInfo.CodecCapabilities capabilities) {
        String[] profiles = Utils.aacProfiles();
        SpinnerAdapter old = mAudioProfile.getAdapter();
        if (old == null || !(old instanceof ArrayAdapter)) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.addAll(profiles);
            mAudioProfile.setAdapter(adapter);
        } else {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) old;
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.addAll(profiles);
            adapter.notifyDataSetChanged();
        }

    }

    public void resetSampleRateAdapter(MediaCodecInfo.CodecCapabilities capabilities) {
        int[] sampleRates = capabilities.getAudioCapabilities().getSupportedSampleRates();
        List<Integer> rates = new ArrayList<>(sampleRates.length);
        int preferred = -1;
        for (int i = 0; i < sampleRates.length; i++) {
            int sampleRate = sampleRates[i];
            if (sampleRate == 44100) {
                preferred = i;
            }
            rates.add(sampleRate);
        }

        SpinnerAdapter old = mAudioSampleRate.getAdapter();
        if (old == null || !(old instanceof ArrayAdapter)) {
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.addAll(rates);
            mAudioSampleRate.setAdapter(adapter);
        } else {
            ArrayAdapter<Integer> adapter = (ArrayAdapter<Integer>) old;
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.addAll(rates);
            adapter.notifyDataSetChanged();
        }
        mAudioSampleRate.setSelectedPosition(preferred);
    }

    public void resetAudioBitrateAdapter(MediaCodecInfo.CodecCapabilities capabilities) {
        Range<Integer> bitrateRange = capabilities.getAudioCapabilities().getBitrateRange();
        int lower = Math.max(bitrateRange.getLower() / 1000, 80);
        int upper = bitrateRange.getUpper() / 1000;
        List<Integer> rates = new ArrayList<>();
        for (int rate = lower; rate < upper; rate += lower) {
            rates.add(rate);
        }
        rates.add(upper);

        SpinnerAdapter old = mAudioBitrate.getAdapter();
        if (old == null || !(old instanceof ArrayAdapter)) {
            ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.addAll(rates);
            mAudioBitrate.setAdapter(adapter);
        } else {
            ArrayAdapter<Integer> adapter = (ArrayAdapter<Integer>) old;
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.addAll(rates);
            adapter.notifyDataSetChanged();
        }
        mAudioSampleRate.setSelectedPosition(rates.size() / 2);
    }

    public MediaCodecInfo getVideoCodecInfo(String codecName) {
        if (codecName == null) return null;
        if (mAvcCodecInfos == null) {
            mAvcCodecInfos = Utils.findEncodersByType(VIDEO_AVC);
        }
        MediaCodecInfo codec = null;
        for (int i = 0; i < mAvcCodecInfos.length; i++) {
            MediaCodecInfo info = mAvcCodecInfos[i];
            if (info.getName().equals(codecName)) {
                codec = info;
                break;
            }
        }
        if (codec == null) return null;
        return codec;
    }

    public MediaCodecInfo getAudioCodecInfo(String codecName) {
        if (codecName == null) return null;
        if (mAacCodecInfos == null) {
            mAacCodecInfos = Utils.findEncodersByType(AUDIO_AAC);
        }
        MediaCodecInfo codec = null;
        for (int i = 0; i < mAacCodecInfos.length; i++) {
            MediaCodecInfo info = mAacCodecInfos[i];
            if (info.getName().equals(codecName)) {
                codec = info;
                break;
            }
        }
        if (codec == null) return null;
        return codec;
    }

    public String getSelectedVideoCodec() {
        return mVideoCodec == null ? null : mVideoCodec.getSelectedItem();
    }

    public SpinnerAdapter createCodecsAdapter(MediaCodecInfo[] codecInfos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, codecInfoNames(codecInfos));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public boolean isLandscape() {
        return mOrientation != null && mOrientation.getSelectedItemPosition() == 1;
    }

    public int getSelectedFramerate() {
        if (mVideoFramerate == null) throw new IllegalStateException();
        return Integer.parseInt(mVideoFramerate.getSelectedItem());
    }

    public int getSelectedVideoBitrate() {
        if (mVideoBitrate == null) throw new IllegalStateException();
        String selectedItem = mVideoBitrate.getSelectedItem(); //kbps
        return Integer.parseInt(selectedItem) * 1000;
    }

    public int getSelectedIFrameInterval() {
        return (mIFrameInterval != null) ? Integer.parseInt(mIFrameInterval.getSelectedItem()) : 5;
    }

    public MediaCodecInfo.CodecProfileLevel getSelectedProfileLevel() {
        return mVideoProfileLevel != null ? Utils.toProfileLevel(mVideoProfileLevel.getSelectedItem()) : null;
    }

    public int[] getSelectedWithHeight() {
        if (mVieoResolution == null) throw new IllegalStateException();
        String selected = mVieoResolution.getSelectedItem();
        String[] xes = selected.split("x");
        if (xes.length != 2) throw new IllegalArgumentException();
        return new int[]{Integer.parseInt(xes[0]), Integer.parseInt(xes[1])};

    }

    public String getSelectedAudioCodec() {
        return mAudioCodec == null ? null : mAudioCodec.getSelectedItem();
    }

    public int getSelectedAudioBitrate() {
        if (mAudioBitrate == null) throw new IllegalStateException();
        Integer selectedItem = mAudioBitrate.getSelectedItem();
        return selectedItem * 1000; // bps
    }

    public int getSelectedAudioSampleRate() {
        if (mAudioSampleRate == null) throw new IllegalStateException();
        Integer selectedItem = mAudioSampleRate.getSelectedItem();
        return selectedItem;
    }

    public int getSelectedAudioProfile() {
        if (mAudioProfile == null) throw new IllegalStateException();
        String selectedItem = mAudioProfile.getSelectedItem();
        MediaCodecInfo.CodecProfileLevel profileLevel = Utils.toProfileLevel(selectedItem);
        return profileLevel == null ? MediaCodecInfo.CodecProfileLevel.AACObjectMain : profileLevel.profile;
    }

    public int getSelectedAudioChannelCount() {
        if (mAudioChannelCount == null) throw new IllegalStateException();
        String selectedItem = mAudioChannelCount.getSelectedItem().toString();
        return Integer.parseInt(selectedItem);
    }

    public void toast(String message, Object... args) {

        int length_toast = Locale.getDefault().getCountry().equals("BR") ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT;
        // In Brazilian Portuguese this may take longer to read

        Toast toast = Toast.makeText(this,
                (args.length == 0) ? message : String.format(Locale.US, message, args),
                length_toast);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            runOnUiThread(toast::show);
        } else {
            toast.show();
        }
    }

    public static String[] codecInfoNames(MediaCodecInfo[] codecInfos) {
        String[] names = new String[codecInfos.length];
        for (int i = 0; i < codecInfos.length; i++) {
            names[i] = codecInfos[i].getName();
        }
        return names;
    }

    /**
     * Print information of all MediaCodec on this device.
     */
    public static void logCodecInfos(MediaCodecInfo[] codecInfos, String mimeType) {
        for (MediaCodecInfo info : codecInfos) {
            StringBuilder builder = new StringBuilder(512);
            MediaCodecInfo.CodecCapabilities caps = info.getCapabilitiesForType(mimeType);
            builder.append("Encoder '").append(info.getName()).append('\'')
                    .append("\n  supported : ")
                    .append(Arrays.toString(info.getSupportedTypes()));
            MediaCodecInfo.VideoCapabilities videoCaps = caps.getVideoCapabilities();
            if (videoCaps != null) {
                builder.append("\n  Video capabilities:")
                        .append("\n  Widths: ").append(videoCaps.getSupportedWidths())
                        .append("\n  Heights: ").append(videoCaps.getSupportedHeights())
                        .append("\n  Frame Rates: ").append(videoCaps.getSupportedFrameRates())
                        .append("\n  Bitrate: ").append(videoCaps.getBitrateRange());
                if (VIDEO_AVC.equals(mimeType)) {
                    MediaCodecInfo.CodecProfileLevel[] levels = caps.profileLevels;

                    builder.append("\n  Profile-levels: ");
                    for (MediaCodecInfo.CodecProfileLevel level : levels) {
                        builder.append("\n  ").append(Utils.avcProfileLevelToString(level));
                    }
                }
                builder.append("\n  Color-formats: ");
                for (int c : caps.colorFormats) {
                    builder.append("\n  ").append(Utils.toHumanReadable(c));
                }
            }
            MediaCodecInfo.AudioCapabilities audioCaps = caps.getAudioCapabilities();
            if (audioCaps != null) {
                builder.append("\n Audio capabilities:")
                        .append("\n Sample Rates: ").append(Arrays.toString(audioCaps.getSupportedSampleRates()))
                        .append("\n Bit Rates: ").append(audioCaps.getBitrateRange())
                        .append("\n Max channels: ").append(audioCaps.getMaxInputChannelCount());
            }
            Log.i("@@@", builder.toString());
        }
    }

    public void restoreSelections(NamedSpinner... spinners) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        for (NamedSpinner spinner : spinners) {
            restoreSelectionFromPreferences(preferences, spinner);
        }
    }

    public void saveSelections() {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor edit = preferences.edit();
        for (NamedSpinner spinner : new NamedSpinner[]{
                mVieoResolution,
                mVideoFramerate,
                mIFrameInterval,
                mVideoBitrate,
                mAudioBitrate,
                mAudioSampleRate,
                mAudioChannelCount,
                mVideoCodec,
                mAudioCodec,
                mAudioProfile,
        }) {
            saveSelectionToPreferences(edit, spinner);
        }
        edit.putBoolean(getResources().getResourceEntryName(mAudioToggle.getId()), mAudioToggle.isChecked());
        edit.apply();
    }

    public void saveSelectionToPreferences(SharedPreferences.Editor preferences, NamedSpinner spinner) {
        int resId = spinner.getId();
        String key = getResources().getResourceEntryName(resId);
        int selectedItemPosition = spinner.getSelectedItemPosition();
        if (selectedItemPosition >= 0) {
            preferences.putInt(key, selectedItemPosition);
        }
    }

    public void restoreSelectionFromPreferences(SharedPreferences preferences, NamedSpinner spinner) {
        int resId = spinner.getId();
        String key = getResources().getResourceEntryName(resId);
        int value = preferences.getInt(key, -1);
        if (value >= 0 && spinner.getAdapter() != null) {
            spinner.setSelectedPosition(value);
        }
    }

    public void stopRecordingAndOpenFile(Context context) {
        File file = new File(mRecorder.getSavedPath());
        stopRecorder();
        Toast.makeText(context, getString(R.string.recorder_stopped_saved_file) + " " + file, Toast.LENGTH_LONG).show();
        StrictMode.VmPolicy vmPolicy = StrictMode.getVmPolicy();
        try {
            // disable detecting FileUriExposure on public file
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
            viewResult(file);
        } finally {
            StrictMode.setVmPolicy(vmPolicy);
        }
    }

    public void viewResult(File file) {
        Intent view = new Intent(Intent.ACTION_VIEW);
        view.addCategory(Intent.CATEGORY_DEFAULT);
        view.setDataAndType(Uri.fromFile(file), VIDEO_AVC);
        view.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(view);
        } catch (ActivityNotFoundException e) {
            // no activity can open this video
        }
    }

    public int CheckingAccountExistence(String MobileNo, String AndroidID){
        if (cd.isConnectingToInternet()) {



            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {



                            // Bookingdetails(listView);
                        }
                    }
                });

                MobileNo=MobileNo.replace(" ","");
                MobileNo=MobileNo.replace("(","");
                MobileNo=MobileNo.replace(")","");
                MobileNo=MobileNo.replace("-","");
                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("MobileNo",MobileNo));
                args.add(new BasicNameValuePair("WebsiteID","9"));
                args.add(new BasicNameValuePair("AndroidID",AndroidID));
                args.add(new BasicNameValuePair("DeviceOS","Android"));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/CheckingAccountExistence";

                dataFromNetwork.setConfig(URL, methodName, args);
                //dataFromNetwork.execute();
                Object response = dataFromNetwork.execute().get().toString();

                if (response != null) {
                    JSONObject object = new JSONObject(response.toString());
                    int MobileNoAccount=object.getInt("MobileNoAccount");
                    int DeviceIDAccount=object.getInt("DeviceIDAccount");
                    if(DeviceIDAccount==0 && MobileNoAccount>0){
                        return 1;
                    }
                    else{
                        return 0;
                    }



                    //result=result.substring(0, result.length() - 1);

                }
                else{
                    return 0;
                }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return 0;
            }
        }
        else{
            return 0;
        }
    }
    public void DeleteCookies(){
        if (cd.isConnectingToInternet()) {
            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {
                        }
                    }
                });

                List<NameValuePair> args = new ArrayList<>();
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/DeleteCookies";

                dataFromNetwork.setConfig(URL, methodName, args);
                //dataFromNetwork.execute();
                Object response = dataFromNetwork.execute().get().toString();

                if (response != null) {

                }


            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public File QuotescreatePdf(String ProjectID, String Project_configID, String SalesPrice, String CRMInventoryID, String QuotationRevisionNumber, String inventoryID) throws IOException {

        String result=GetPriceDetail(ProjectID,Project_configID,SalesPrice,CRMInventoryID,QuotationRevisionNumber,inventoryID);

        // String extstoragedir = "/sdcard/pdf";
        String extstoragedir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/pdf1";
        File fol = new File(extstoragedir);

        if (!fol.exists()) {
            fol.mkdir();
        }
        try {
            String filename=result.replace("https://www.oxyzenhomes.com/PriceSheet/","").replaceAll("\n", "").replaceAll("\"", "");
            file = new File(fol, filename);
            file.createNewFile();
            fOut = new FileOutputStream(file);

            new DownloadFileFromURL().execute(result.replaceAll("\n", "").replaceAll("\"", "")).get();

            return file;


        } catch (IOException e) {

            Toast.makeText(HomeActivity.this, "Please give the permission for creating PDF..", Toast.LENGTH_SHORT).show();
            get_file();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return null;
    }
    public String DeletePaymentReceipt(String FileName){
        String result="";
        requestBean= new RequestBean();
        requestBean.setActivity(HomeActivity.this);
        cd = new ConnectionDetector(this.getApplicationContext());
        if (cd.isConnectingToInternet()) {



            try {


                GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {

                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("FileName",FileName));

                String URL = "https://www.grapevine.work/";
                String methodName = "OxyzenCRM/DeletePaymentReceipt";
                dataFromNetwork.setConfig(URL, methodName, args);
                String response=dataFromNetwork.execute().get().toString();
                if (response != null) {
                    result=response;
                }

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }
        }
        return result;
    }
    public String GetPriceDetail(final String Projectid, final String Project_Config_Id, String SalesPrice, String CRM_InventoryID, String QuotationRevisionNumber, String inventoryID) {


        requestBean= new RequestBean();
        requestBean.setActivity(HomeActivity.this);
        cd = new ConnectionDetector(this.getApplicationContext());

        if (cd.isConnectingToInternet()) {



            try {


                GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {

                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("Projectid",Projectid));
                args.add(new BasicNameValuePair("Project_Config_Id",Project_Config_Id));
                args.add(new BasicNameValuePair("SalesPrice",SalesPrice));
                args.add(new BasicNameValuePair("CRM_InventoryID",CRM_InventoryID));
                args.add(new BasicNameValuePair("QuotationRevisionNumber",QuotationRevisionNumber));
                args.add(new BasicNameValuePair("InventoryID",inventoryID));
                args.add(new BasicNameValuePair("LandRate","0.00"));

                String URL = "https://www.oxyzenhomes.com/";
                String methodName = "Interactions/NewPriceSheet";
                dataFromNetwork.setConfig(URL, methodName, args);
                String response=dataFromNetwork.execute().get().toString();
                if (response != null) {
                    directory=response.replace("~/PriceSheet/","").replaceAll("\n", "");
                    directory=directory.substring(1, directory.length()-1);
                    return directory;
                }

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }
        }
        return null;

    }
    public String QuotesMail(final String Projectid, final String Project_Config_Id, String SalesPrice, String CRM_InventoryID, String QuotationRevisionNumber, String inventoryID) {


        requestBean= new RequestBean();
        requestBean.setActivity(HomeActivity.this);
        cd = new ConnectionDetector(this.getApplicationContext());

        if (cd.isConnectingToInternet()) {



            try {


                GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {

                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("Projectid",Projectid));
                args.add(new BasicNameValuePair("Project_Config_Id",Project_Config_Id));
                args.add(new BasicNameValuePair("SalesPrice",SalesPrice));
                args.add(new BasicNameValuePair("CRM_InventoryID",CRM_InventoryID));
                args.add(new BasicNameValuePair("QuotationRevisionNumber",QuotationRevisionNumber));
                args.add(new BasicNameValuePair("Applicant_id",appPreferences.getApplicant_id()));
                args.add(new BasicNameValuePair("InventoryID",inventoryID));

                String URL = "https://grapevine.work/";
                String methodName = "OxyzenCRM/QuotesMail";
                dataFromNetwork.setConfig(URL, methodName, args);
                String response=dataFromNetwork.execute().get().toString();
                if (response != null) {

                    return directory;
                }

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }
        }
        return null;

    }

    public String treemenumail(final String leadthreadid, final String kbcdescription) {


        requestBean= new RequestBean();
        requestBean.setActivity(HomeActivity.this);
        cd = new ConnectionDetector(this.getApplicationContext());

        if (cd.isConnectingToInternet()) {



            try {


                GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {

                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("LeadThreadID",leadthreadid));
                args.add(new BasicNameValuePair("KnowledgeBaseContentDescription",kbcdescription));

                String URL = "https://grapevine.work/";
                String methodName = "OxyzenCRM/crm_sendmailto_Leadthread";
                dataFromNetwork.setConfig(URL, methodName, args);
                String response=dataFromNetwork.execute().get().toString();

                if (response != null) {
                    directory=response.replace("~/PriceSheet/","").replaceAll("\n", "");
                    return directory;
                }

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }
        }
        return null;

    }
    public List<String> messengermail(final String leadthreadid, final String kbcdescription,String Project_ID) {


        requestBean= new RequestBean();
        requestBean.setActivity(HomeActivity.this);
        cd = new ConnectionDetector(this.getApplicationContext());
        List<String> data=new ArrayList<>();
        if (cd.isConnectingToInternet()) {



            try {


                GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {


                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("LeadThreadID",leadthreadid));
                args.add(new BasicNameValuePair("KnowledgeBaseContentDescription",kbcdescription));
                args.add(new BasicNameValuePair("Project_ID",Project_ID));
                String URL = "https://grapevine.work/";
                String methodName = "OxyzenCRM/crm_sendWhatsappto_Leadthread";
                dataFromNetwork.setConfig(URL, methodName, args);
                String response=dataFromNetwork.execute().get().toString();
                try {
                    JSONObject object= new JSONObject(response.toString());
                    String result = object.getString("_crm_show_docpath");
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = new JSONObject(jsonArray.get(i).toString());

                        data.add(obj.getString("ArticleFilePath"));
                    }

                } catch (JSONException e) {

                }
                return data;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }
        }
        return null;

    }
    public JSONArray GetAllFeedContacts(final String FeedID,final String ContactNo) {

        JSONArray jsonArray=null;
        requestBean= new RequestBean();
        requestBean.setActivity(HomeActivity.this);
        cd = new ConnectionDetector(this.getApplicationContext());
        List<String> data=new ArrayList<>();
        if (cd.isConnectingToInternet()) {

            try {
                GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {

                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("FeedID",FeedID));
                args.add(new BasicNameValuePair("ContactNo",ContactNo));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/GetAllFeedContacts";
                dataFromNetwork.setConfig(URL, methodName, args);
                String response=dataFromNetwork.execute().get().toString();
                try {
//                    JSONObject object= new JSONObject(response.toString());
//                    String result = object.getString("_crm_show_docpath");
                    jsonArray = new JSONArray(response);



                } catch (JSONException e) {

                }
                return jsonArray;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }
        }
        return null;

    }
    public List<String> Whatsappmail(final String leadthreadid, final String kbcdescription,String Project_ID,String ShareWhat,String EntityFeedChannelID) {


        requestBean= new RequestBean();
        requestBean.setActivity(HomeActivity.this);
        cd = new ConnectionDetector(this.getApplicationContext());
        List<String> data=new ArrayList<>();
        if (cd.isConnectingToInternet()) {



            try {


                GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {


                    }
                });
                List<NameValuePair> args = new ArrayList<>();

                args.add(new BasicNameValuePair("LeadThreadID",leadthreadid));
                args.add(new BasicNameValuePair("KnowledgeBaseContentDescription",kbcdescription));
                args.add(new BasicNameValuePair("Project_ID",Project_ID));
                args.add(new BasicNameValuePair("ShareWhat",ShareWhat));
                args.add(new BasicNameValuePair("EntityFeedChannelID",EntityFeedChannelID));
                String URL = "https://grapevine.work/";
                String methodName = "OxyzenCRM/crm_sendWhatsappto_Leadthread";
                dataFromNetwork.setConfig(URL, methodName, args);
                String response=dataFromNetwork.execute().get().toString();
                try {
                    JSONObject object= new JSONObject(response.toString());
                    String result = object.getString("_crm_show_docpath");
                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = new JSONObject(jsonArray.get(i).toString());

                        data.add(obj.getString("ArticleFilePath"));
                    }

                } catch (JSONException e) {

                }
                return data;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());

            }
        }
        return null;

    }
    protected void sendMMS(final String body, final String imagePath, final String FacebookUserID, final List<String> data) {
        MediaScannerConnection.MediaScannerConnectionClient mediaScannerClient = new MediaScannerConnection.MediaScannerConnectionClient() {
            private MediaScannerConnection msc = null;
            {
                msc = new MediaScannerConnection(getApplicationContext(), this);
                msc.connect();
            }

            public void onMediaScannerConnected() {
                msc.scanFile(imagePath, null);
            }

            public void onScanCompleted(String path, Uri uri) {


                Uri urii = Uri.parse("fb://messaging/" + FacebookUserID);
                //urii = ContentUris.withAppendedId(urii,Long.parseLong("2658747794217908"));
                //urii = ContentUris.withAppendedId(urii,Long.parseLong(FacebookUserID));

                Intent intent = new Intent(Intent.ACTION_VIEW, urii);
                intent.putExtra(Intent.EXTRA_TEXT, "Hello");
//                intent.putExtra(Intent.EXTRA_TEXT, data.toString());
                intent.setType("*/*");
                intent.setPackage("com.facebook.orca");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setAction(Intent.ACTION_SEND);
//                intent.setType("image/png");
//                intent.setPackage("com.facebook.orca");
                startActivity(intent);

                msc.disconnect();
            }
        };
    }
    public JSONObject Get_User_DetailOverFeedChannelID(String FeedChannelID) {
        JSONObject object = null;
        if (cd.isConnectingToInternet()) {


            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {

                            // Bookingdetails(listView);
                        }
                    }
                });
                ContentValues values = new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("FeedChannelID", FeedChannelID));


                String URL = "https://www.grapevine.work/";
                String methodName = "OxyzenCRM/get_LoginUser_detail";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();

                if (response != null) {

                    object = new JSONObject(response.toString());
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return object;

    }

    public String crm_updateleadstage(String ThreadStageID,String Targetconversiondate,String LeadThreadID){
        String result="";

        if(cd==null){
            cd = new ConnectionDetector(getApplicationContext());
        }
        if(requestBean==null){
            requestBean = new RequestBean();

            requestBean.setActivity(this);
        }
        if (cd.isConnectingToInternet()) {
            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {
                        }
                    }
                });
                ContentValues values = new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("threadstageid", ThreadStageID));
                args.add(new BasicNameValuePair("Targetconversiondate", Targetconversiondate));
                args.add(new BasicNameValuePair("Leadthreadid", LeadThreadID));

                String URL = "https://www.grapevine.work/";
                String methodName = "OxyzenCRM/crm_updateleadstage";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                JSONObject object=null;
                if (response != null) {

                    result=response.toString();

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return result;
    }

    public String  Crm_Add_Activity(String LeadThreadID,String OldActivityThreadID,String ActivitySubTypeID,String ActivityChannelID,
                                    String ActivityDescription,String schedule_date,String Schedule_Enddate,String AssociateID,String ActivityReport,String OldActivityReport,
                                    String Enddatetime,String ActivityTagtypedata,String ActivityLocationTypeID) {
        String ActivityThreadID="";
        if(cd==null){
            cd = new ConnectionDetector(getApplicationContext());
        }
        if(requestBean==null){
            requestBean = new RequestBean();

            requestBean.setActivity(this);
        }
        if (cd.isConnectingToInternet()) {
            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {
                        }
                    }
                });
                ContentValues values = new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("LeadThreadID", LeadThreadID));
                args.add(new BasicNameValuePair("OldActivityThreadID", OldActivityThreadID));
                args.add(new BasicNameValuePair("ActivitySubTypeID", ActivitySubTypeID));
                args.add(new BasicNameValuePair("ActivityChannelID", ActivityChannelID));
                args.add(new BasicNameValuePair("ActivityDescription", ActivityDescription));
                args.add(new BasicNameValuePair("schedule_date", schedule_date));
                args.add(new BasicNameValuePair("Schedule_Enddate", Schedule_Enddate));
                args.add(new BasicNameValuePair("AssociateID", AssociateID));
                args.add(new BasicNameValuePair("ActivityReport", ActivityReport));
                args.add(new BasicNameValuePair("OldActivityReport", OldActivityReport));
                args.add(new BasicNameValuePair("Enddatetime", Enddatetime));
                args.add(new BasicNameValuePair("ActivityTagtypedata", ActivityTagtypedata));
                args.add(new BasicNameValuePair("ActivityLocationTypeID", ActivityLocationTypeID));
                String URL = "https://www.grapevine.work/";
                String methodName = "OxyzenCRM/Crm_Add_Activity";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                JSONObject object=null;
                if (response != null) {

                    object = new JSONObject(response.toString());

                   String ActionResult=object.getString("ActionResult");
                   object=new JSONObject(ActionResult);
                   ActionResult=object.getString("result");
                    JSONArray obj = new JSONArray(ActionResult);
                    if(obj.length()>0){
                        ActionResult=obj.getString(0);
                        obj = new JSONArray(ActionResult);
                        for (int i = 0; i < obj.length(); i++) {
                            object = obj.getJSONObject(i);
                            ActivityThreadID = object.getString("activitythreadid");
                        }
                    }


                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return ActivityThreadID;

    }
    public String  crm_feed_Get_leadthread_from_mobile(String AssociateMobileNo,String LeadMobileNo) {
        String LeadThreadID = null;
        if(cd==null){
            cd = new ConnectionDetector(getApplicationContext());
        }
        if(requestBean==null){
            requestBean = new RequestBean();

            requestBean.setActivity(this);
        }
        if (cd.isConnectingToInternet()) {
            try {

                final GetDataFromNetwork dataFromNetwork = new GetDataFromNetwork(requestBean.getActivity(), ProgressDialog.STYLE_SPINNER, "Please Wait...", new GetDataCallBack() {

                    public void processResponse(Object response) {
                        if (response != null) {
                        }
                    }
                });
                ContentValues values = new ContentValues();

                List<NameValuePair> args = new ArrayList<>();
                args.add(new BasicNameValuePair("AssociateMobileNo", AssociateMobileNo));
                args.add(new BasicNameValuePair("LeadMobileNo", LeadMobileNo));

                String URL = "https://www.grapevine.work/";
                String methodName = "OxyzenCRM/crm_feed_Get_leadthread_from_mobile";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();

                if (response != null) {
                    LeadThreadID=response.toString();
                    LeadThreadID=LeadThreadID.replaceAll("\"","").replaceAll("\n","");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return LeadThreadID;

    }

    public List<String> GetTextToShare(final String leadthreadid, final String kbcdescription,String ProjectID,String ShareWhat,String EntityFeedChannelID){
        List<String> data=Whatsappmail(leadthreadid,kbcdescription,ProjectID,ShareWhat,EntityFeedChannelID);
        return data;
    }

    public List<File> createPdf(final String leadthreadid, final String kbcdescription,String ProjectID,String ShareWhat,String EntityFeedChannelID) throws IOException {

        List<File> filelist=new ArrayList<>();
        List<String> data= new ArrayList<>();
                data = Whatsappmail(leadthreadid,kbcdescription,ProjectID,ShareWhat,EntityFeedChannelID);
        String result="";

        try {
            if(check_permission(2) && check_permission(4)){
                for (int i=0;i<data.size();i++) {
                    if(i<=28){
                        String extstoragedir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Grapevine/pdf";
                        File fol = new File(extstoragedir);

                        if (!fol.exists()) {
                            fol.mkdir();
                        }


                        String filename = data.get(i).toString();
                        String[] sn=filename.split("/");
                        String FileName=sn[sn.length-1];

                        String exten = getFileExtension(FileName);
                        exten = exten.replace(".", "");

                        String dateTime = String.valueOf(System.currentTimeMillis());
                        Log.e(TAG,"Current Time:-"+dateTime);
                        if (exten.equals("jpg") || exten.equals("jpeg") || exten.equals("png") || exten.equals("webp")) {

                            FileName = "IMG-" + dateTime + "." + exten;
                        } else if (exten.equals("mp4") || exten.equals("mkv") || exten.equals("gif")
                                || exten.equals("3gp") || exten.equals("wmv") || exten.equals("webm")|| exten.equals("mp4v")) {
                            FileName = "VID-" + dateTime + "." + exten;
                        }
//                        else if (exten.equals("xlsx") || exten.equals("xls") || exten.equals("doc") || exten.equals("docx") || exten.equals("ppt") || exten.equals("pptx") || exten.equals("txt") || exten.equals("pdf")) {
//                            FeedObjectTypeID = "10";
//
//                        }
                        else if (exten.equals("mp3") || exten.equals("m4a")|| exten.equals("mpeg")) {
                            FileName = "AUD-" + dateTime + "." + exten;
                        }



                        file = new File(fol, FileName);
                        if(!file.exists()) {
                            file.createNewFile();
                            fOut = new FileOutputStream(file);
                            directory = filename;
                            new DownloadFileFromURL().execute(filename).get();
                            //new FtpTask().execute().get().toString();
                        }

                        filelist.add(file);
                    }
                    else{
                        break;
                    }
                }
            }
            else{

                get_file();
            }


            return filelist;


        } catch (IOException e) {
            Toast.makeText(this, "Please give the permission for creating PDF..", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {

        } catch (ExecutionException e) {

        }


        return null;
    }


    public BroadcastReceiver mStopActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_STOP.equals(intent.getAction())) {
                stopRecordingAndOpenFile(context);
            }
        }
    };

}
