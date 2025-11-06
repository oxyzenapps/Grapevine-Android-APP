package com.oxyzenhomes.grapevine.oxyzenrental;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//import org.apache.commons.net.ftp.FTPClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;

import com.dropbox.core.v1.DbxEntry;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;
import com.oxyzenhomes.grapevine.connectivity.GetDataCallBack;
import com.oxyzenhomes.grapevine.connectivity.GetDataFromNetwork;
import com.oxyzenhomes.grapevine.request.RequestBean;
import com.truecaller.android.sdk.TruecallerSDK;

import org.apache.commons.net.ftp.FTP;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.commons.net.ftp.FTPClient;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static android.Manifest.permission.RECORD_AUDIO;

public class ShareActivity extends Activity  {
    private AppPreferences appPreferences;
    private RequestBean requestBean;
    ConnectionDetector cd;
    Bitmap bitmap;
    private static final int REQUEST_PERMISSIONS = 20;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        startActivity(intent);
        finish();
    }
    Intent intent = null;
    String action = null;
    String type = null;
    ProgressDialog progress=null;

    int TotalFiles=0;
    int UploadedFiles=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_file);


        Toast.makeText (this, "File is sharing to Grapevine, please wait..." , Toast.LENGTH_SHORT ).show() ;
         progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        // Get intent, action and MIME type
         intent = getIntent();
         action = intent.getAction();
         type = intent.getType();


        String[] perms = {
                Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,//Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, RECORD_AUDIO,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR,
                //Manifest.permission.READ_PHONE_NUMBERS,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,Manifest.permission.READ_PHONE_STATE};
        ActivityCompat.requestPermissions(this, perms, REQUEST_PERMISSIONS);

        //String PackageName=intent.getPa.getPackage();
        appPreferences = new AppPreferences(ShareActivity.this);

        requestBean = new RequestBean();
        requestBean.setActivity(this);


        appPreferences = new AppPreferences(this);
        cd = new ConnectionDetector(getApplicationContext());
        String FeedChannelID=appPreferences.getUserFeedChannelID();
//        if(FeedChannelID==null || FeedChannelID.equals("")){
            FeedChannelID=GetFeedChannelID(appPreferences.getApplicant_id());
            appPreferences.setUserFeedChannelID(FeedChannelID);
//        }
        Log.e("Share Type",type);

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (!"text/plain".equals(type)) {
                handleSendImage(intent,type); // Handle single image being sent

                Timer timer= new Timer();
                //Toast.makeText(getApplicationContext(),"arr Size:"+arrSize+", fileSize:"+FileCounter,Toast.LENGTH_LONG).show();


                timer.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        //Toast.makeText(getApplicationContext(),"arr Size:"+UploadedFiles,Toast.LENGTH_LONG).show();
                        if(UploadedFiles == TotalFiles){
                            progress.dismiss();
                            timer.cancel();
                            appPreferences.setShareLink("PostPage");
                            Intent i=new Intent(ShareActivity.this,HomeActivity.class);
                            i.putExtra(Intent.EXTRA_TEXT,"PostPage");
                            startActivity(i);
                        }
                    }
                },0,1000);
            }
        }
        else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (!"text/plain".equals(type)) {
                handleSendMultipleImages(intent,type); // Handle multiple images being sent

                Timer timer= new Timer();
                timer.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        //Toast.makeText(getApplicationContext(),"arr Size:"+UploadedFiles,Toast.LENGTH_LONG).show();
                        if(UploadedFiles == TotalFiles){
                            progress.dismiss();
                            timer.cancel();
                            appPreferences.setShareLink("PostPage");
                            Intent i=new Intent(ShareActivity.this,HomeActivity.class);
                            i.putExtra(Intent.EXTRA_TEXT,"PostPage");
                            startActivity(i);
                        }
                    }
                },0,1000);
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }


    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        String[] website;
        ArrayList links = new ArrayList();
        String FeedChannelID=appPreferences.getUserFeedChannelID();
        DeleteTempFiles(FeedChannelID);
        if (sharedText != null) {


            String regex = "\\(?\\b(https://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(sharedText);
            while(m.find()) {
                String urlStr = m.group();
                if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                    urlStr = urlStr.substring(1, urlStr.length() - 1);
                }
                links.add(urlStr);
            }
            sharedText=links.get(0).toString();
//            String[] website=sharedText.split("\n");
//            sharedText=website[1];
            appPreferences.setShareLink(sharedText);
            Intent i=new Intent(ShareActivity.this,HomeActivity.class);
            startActivity(i);
            // Update UI to reflect text being shared
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    void handleSendImage(Intent intent,String type) {
        TotalFiles=1;
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        String FeedText = intent.getStringExtra(Intent.EXTRA_TEXT);
if(FeedText!=null){
    Log.e("FeedText",FeedText);
}
else{
    FeedText="";
}

        Log.e("Share Image uri",imageUri.toString());
        String FeedChannelID=appPreferences.getUserFeedChannelID();
        DeleteTempFiles(FeedChannelID);
        if (imageUri != null) {
            if(type.startsWith("image/") || type.contains("panorama")){
                String imagestring= null;
                try {
                    imagestring = compressImage(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UploadImagesToFtp(imagestring,FeedChannelID,FeedText);
            }
            else
            {
                String whatsAppuri=imageUri.toString();
                String fileType="";
                if(whatsAppuri.contains("content://")){
                    fileType=type.split("/")[0];
                    Log.e("File Type",type);
                    if(!fileType.equals("video") && !fileType.equals("audio")){
                        fileType=type.split("/")[1];
                    }
                    imageUri=GetFile(imageUri,fileType,FeedText);
                }

                String FilePath=imageUri.getPath().toString();
                String imagestring=FileUtil.getPath(this,imageUri);
                if((fileType.equals("msword") ||
                        fileType.equals("vnd.openxmlformats-officedocument.wordprocessingml.document")||
                        fileType.equals("vnd.openxmlformats-officedocument.wordprocessingml.template") ||
                        fileType.equals("vnd.ms-word.document.macroEnabled.12") ||
                        fileType.equals("vnd.ms-word.template.macroEnabled.12") ||
                        fileType.equals("vnd.ms-excel") ||
                        fileType.equals("vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                        fileType.equals("vnd.openxmlformats-officedocument.spreadsheetml.template") ||
                        fileType.equals("vnd.ms-powerpoint") ||
                        fileType.equals("vnd.openxmlformats-officedocument.presentationml.presentation")||
                        fileType.equals("vnd.openxmlformats-officedocument.presentationml.slideshow") ||
                        fileType.equals("pdf") ) && FeedText!=null && !FeedText.equals("")){
                    FeedText="";
                }
                UploadImagesToFtp(imagestring,FeedChannelID,FeedText);
            }

//            appPreferences.setShareLink("PostPage");
//            Intent i=new Intent(ShareActivity.this,HomeActivity.class);
//            i.putExtra(Intent.EXTRA_TEXT,"PostPage");
//            startActivity(i);
            // Update UI to reflect image being shared
        }
    }

    void handleSendMultipleImages(Intent intent,String type) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        TotalFiles=imageUris.size();
        String FeedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if(FeedText!=null){
            Log.e("FeedText",FeedText);
        }
        else{
            FeedText="";
        }
        String FeedChannelID=appPreferences.getUserFeedChannelID();
       DeleteTempFiles(FeedChannelID);
        if (imageUris != null) {
            for (Uri perm : imageUris) {
                if(type.startsWith("image/")){
                    String imagestring= null;
                    try {
                        imagestring = compressImage(perm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    UploadImagesToFtp(imagestring,FeedChannelID,FeedText);
                }
                else
                {
                    String fileType="";
                    String whatsAppuri=perm.toString();
                    if(whatsAppuri.contains("content://")){
                         fileType=type.split("/")[0];
                        if(!fileType.equals("video") && !fileType.equals("audio")){
                            fileType=type.split("/")[1];
                        }
                        perm=GetFile(perm,fileType,FeedText);
                    }
                    if((fileType.equals("msword") ||
                            fileType.equals("vnd.openxmlformats-officedocument.wordprocessingml.document")||
                            fileType.equals("vnd.openxmlformats-officedocument.wordprocessingml.template") ||
                            fileType.equals("vnd.ms-word.document.macroEnabled.12") ||
                            fileType.equals("vnd.ms-word.template.macroEnabled.12") ||
                            fileType.equals("vnd.ms-excel") ||
                            fileType.equals("vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                            fileType.equals("vnd.openxmlformats-officedocument.spreadsheetml.template") ||
                            fileType.equals("vnd.ms-powerpoint") ||
                            fileType.equals("vnd.openxmlformats-officedocument.presentationml.presentation")||
                            fileType.equals("vnd.openxmlformats-officedocument.presentationml.slideshow") ||
                            fileType.equals("pdf") ) && FeedText!=null && !FeedText.equals("")){
                        FeedText="";
                    }
                    String imagestring=FileUtil.getPath(this,perm);
                    UploadImagesToFtp(imagestring,FeedChannelID,FeedText);
                }

            }
//            Intent i=new Intent(ShareActivity.this,HomeActivity.class);
//            appPreferences.setShareLink("PostPage");
//            i.putExtra(Intent.EXTRA_TEXT,"PostPage");
//            startActivity(i);
            // Update UI to reflect multiple images being shared
        }
    }
    public String compressImage1(Uri contentUri) {
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

        // bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        int actImgWidth=actualWidth;
        int actImgHeight=actualHeight;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;
        float scalefactor=0.5f;
        boolean comNeeded=true;


//        if (actualHeight > maxHeight || actualWidth > maxWidth) {
//            if (imgRatio < maxRatio) {
//                imgRatio = maxHeight / actualHeight;
//                actualWidth = (int) (imgRatio * actualWidth);
//                actualHeight = (int) maxHeight;
//            } else if (imgRatio > maxRatio) {
//                imgRatio = maxWidth / actualWidth;
//                actualHeight = (int) (imgRatio * actualHeight);
//                actualWidth = (int) maxWidth;
//            } else {
//                actualHeight = (int) maxHeight;
//                actualWidth = (int) maxWidth;
//
//            }
//        }

        while (comNeeded){
            float newHeight=actImgHeight/scalefactor;
            float newWidth=actImgWidth/scalefactor;

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                scaledBitmap = Bitmap.createBitmap(Math.round(newWidth),Math.round(newHeight) ,Bitmap.Config.ARGB_8888);
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

                    int size=scaledBitmap.getByteCount();

                    if(size<307200){
                        comNeeded=false;
                    }
                    else{
                        scalefactor=scalefactor+0.5f;
                    }
                    Log.e("Image Size",String.valueOf(size));
                    Log.e("Scale Factor",String.valueOf(scalefactor));
                    Log.e("Compression Needed",String.valueOf(comNeeded));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }





        FileOutputStream out = null;
        File filename = getFilename(FileOriginalName);
        String uriSting="";

        uriSting =filename.getAbsolutePath() + "/" + FileOriginalName + ".png";

        try {
            out = new FileOutputStream(uriSting,false);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //display
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(uriSting)));
            int heigth=bitmap.getHeight();
            int width=bitmap.getWidth();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR","Error "+e.getMessage());
        }
        return uriSting;

    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public Uri GetFile(Uri imageUri,String fileType,String FeedText){
        InputStream inputStream= null;
        Uri imgUri=null;
        Date c = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy_HH_mm_ss_SSS", Locale.getDefault());
        String formattedDate = df.format(c);

        String FileOriginalName="";
        String FileExtension="";
        if(fileType.contains("video")){
            FileOriginalName="VID-"+formattedDate+".mp4";
        }
        else if(fileType.contains("audio")){
            FileOriginalName="AUD-"+formattedDate+".mp3";
        }
        else if((fileType.equals("msword") ||
                fileType.equals("vnd.openxmlformats-officedocument.wordprocessingml.document")||
                fileType.equals("vnd.openxmlformats-officedocument.wordprocessingml.template") ||
                fileType.equals("vnd.ms-word.document.macroEnabled.12") ||
                fileType.equals("vnd.ms-word.template.macroEnabled.12") ||
                fileType.equals("vnd.ms-excel") ||
                fileType.equals("vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                fileType.equals("vnd.openxmlformats-officedocument.spreadsheetml.template") ||
                fileType.equals("vnd.ms-powerpoint") ||
                fileType.equals("vnd.openxmlformats-officedocument.presentationml.presentation")||
                fileType.equals("vnd.openxmlformats-officedocument.presentationml.slideshow") ||
                fileType.equals("pdf") ) && FeedText!=null && !FeedText.equals("")){
            FileOriginalName=FeedText;
        }
        else if(fileType.equals("msword"))
        {
            FileOriginalName="DOC-"+formattedDate+".doc";
        }
        else if(fileType.equals("vnd.openxmlformats-officedocument.wordprocessingml.document")){
            FileOriginalName="DOC-"+formattedDate+".docx";
        }
        else if(fileType.equals("vnd.openxmlformats-officedocument.wordprocessingml.template")){
            FileOriginalName="DOC-"+formattedDate+".dotx";
        }
        else if(fileType.equals("vnd.ms-word.document.macroEnabled.12")){
            FileOriginalName="DOC-"+formattedDate+".docm";
        }
        else if(fileType.equals("vnd.ms-word.template.macroEnabled.12")){
            FileOriginalName="DOC-"+formattedDate+".dotm";
        }
        else if(fileType.equals("vnd.ms-excel")){
            FileOriginalName="DOC-"+formattedDate+".xls";
        }
        else if(fileType.equals("vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            FileOriginalName="DOC-"+formattedDate+".xlsx";
        }
        else if(fileType.equals("vnd.openxmlformats-officedocument.spreadsheetml.template")){
            FileOriginalName="DOC-"+formattedDate+".xltx";
        }
        else if(fileType.equals("vnd.ms-powerpoint")){
            FileOriginalName="DOC-"+formattedDate+".ppt";
        }
        else if(fileType.equals("vnd.openxmlformats-officedocument.presentationml.presentation")){
            FileOriginalName="DOC-"+formattedDate+".pptx";
        }
        else if(fileType.equals("vnd.openxmlformats-officedocument.presentationml.slideshow")){
            FileOriginalName="DOC-"+formattedDate+".ppsx";
        }
        else if(fileType.equals("pdf")){
            FileOriginalName="DOC-"+formattedDate+".pdf";
        }
        File file = new File(getCacheDir(), FileOriginalName);
        try {
            this.grantUriPermission(this.getPackageName(), imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            inputStream = getContentResolver().openInputStream(imageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            OutputStream output = new FileOutputStream(file);
            try {

                byte[] buffer = new byte[4 * 1024]; // or other buffer size
                int read;

                while ((read = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }

                output.flush();
            } finally {
                output.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes =getFileFromPath(file);
           imgUri=  Uri.fromFile(file);
           Log.e("Video File URI",imgUri.getPath());
            //Upload Bytes.
        }
        return imgUri;
    }
    public static byte[] getFileFromPath(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    //@RequiresApi(api = Build.VERSION_CODES.N)
    public String compressWhatsappImage(Uri contentUri){
        String whatsAppuri=contentUri.toString();
        Date c = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy_HH_mm_ss_SSS", Locale.getDefault());
        String formattedDate = df.format(c);

        String FileOriginalName="IMG-"+formattedDate+".png";
        OutputStream output=null;
        Bitmap scaledBitmap = null;
        if(whatsAppuri.contains("content://")){
            //contentUri=GetFile(contentUri,"image");
            //filePath= getRealPathFromURI(this,contentUri);
            try {
                Bitmap bm= BitmapFactory.decodeStream(getContentResolver().openInputStream(contentUri));
                this.grantUriPermission(this.getPackageName(), contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //InputStream is = getContentResolver().openInputStream(contentUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                //String mypath=getRealPathFromURI(contentUri);
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
                options.inJustDecodeBounds = false;
                options.inSampleSize=1;
                Bitmap bmp=null;
                try {
                    InputStream in=getContentResolver().openInputStream(contentUri);

                    File file = new File(getCacheDir(), FileOriginalName);
                    InputStream inputStream=getContentResolver().openInputStream(contentUri);
                    try {


                         output = new FileOutputStream(file);
                        try {
                            byte[] buffer = new byte[4 * 1024]; // or other buffer size
                            int read;

                            while ((read = inputStream.read(buffer)) != -1) {
                                output.write(buffer, 0, read);
                            }

                            output.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] bytes =getFileFromPath(file);

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        File filename = getFilename(FileOriginalName);
        String uriSting="";

        BitmapFactory.Options options = new BitmapFactory.Options();
        //String mypath=getRealPathFromURI(contentUri);
//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = false;
        options.inSampleSize=1;
        int actImgHeight = options.outHeight;
        int actImgWidth = options.outWidth;
        float actualWidth=400;
        float actualHeight=500;
//      max Height and width values of the compressed image is taken as 816x612
        float scalefactor=0.5f;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;

        uriSting =filename.getAbsolutePath() + "/" + FileOriginalName;
        try {
            scaledBitmap = Bitmap.createBitmap(Math.round(actualWidth),Math.round(actualHeight), Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }


//          write the compressed bitmap at the destination specified by filename.
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

        try {
            //display
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(uriSting)));
            int heigth=bitmap.getHeight();
            int width=bitmap.getWidth();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR","Error "+e.getMessage());
        }
        return uriSting;

    }
    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String compressImage(Uri contentUri) throws IOException {
        Bitmap bp= null;
        String filePath="";
        String uri_string="";
        String whatsAppuri=contentUri.toString();
        if(whatsAppuri.contains("content://")){
            //contentUri=GetFile(contentUri,"image");
            //filePath= getRealPathFromURI(this,contentUri);
            uri_string=compressWhatsappImage( contentUri);
        }
else{
            filePath=FileUtil.getPathFromUri(this,contentUri);
        }

filePath=uri_string;
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

        // bmp = BitmapFactory.decodeFile(filePath, options);

        int actImgHeight = options.outHeight;
        int actImgWidth = options.outWidth;
        float actualWidth=actImgWidth;
        float actualHeight=actImgHeight;
//      max Height and width values of the compressed image is taken as 816x612
        float scalefactor=0.5f;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image


boolean comNeeded=true;
        while (comNeeded) {
//      setting inSampleSize value allows to load a scaled down version of the original image
            actualHeight = actImgHeight/scalefactor;
            actualWidth = actImgWidth/scalefactor;

            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inTempStorage = new byte[16 * 1024];

            try {
                scaledBitmap = Bitmap.createBitmap(Math.round(actualWidth),Math.round(actualHeight), Bitmap.Config.ARGB_8888);
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
                    int size=scaledBitmap.getByteCount();

                    if(size<409600){
                        comNeeded=false;
                    }
                    else{
                        scalefactor=scalefactor+0.5f;
                    }
                    Log.e("Image Size",String.valueOf(size));
                    Log.e("Scale Factor",String.valueOf(scalefactor));
                    Log.e("Compression Needed",String.valueOf(comNeeded));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileOutputStream out = null;
        File filename = getFilename(FileOriginalName);
        String uriSting="";

        uriSting =filename.getAbsolutePath() + "/" + FileOriginalName + ".png";

        try {
            out = new FileOutputStream(uriSting,false);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //display
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(new File(uriSting)));
            int heigth=bitmap.getHeight();
            int width=bitmap.getWidth();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR","Error "+e.getMessage());
        }
        return uriSting;

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
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Grapevine/Sent/");
        if (!file.exists()) {
            try{
                Boolean a=  file.mkdirs();
                //Boolean a=  file.createNewFile();
            }
            catch (Exception ex){
                ex.toString();
            }

        }

            uriSting = file.getAbsolutePath()+"/" + FileOriginalName + ".png" ;
            Log.e("urlstring",uriSting);

        Log.d("IMAGES",""+uriSting);
        return file;
    }
    public int calculateInSampleSize(BitmapFactory.Options options, float reqWidth, float reqHeight) {
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
    private void UploadImagesToFtp(String filePath,String FeedChannelID,String FeedText){
//        String server="36.255.3.139";
//        String user="cdn.grapevine.work";
//        String password="11BYRV^#C!@%^#R1";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder()
                .permitAll()
                .build();
if(FeedText==null || FeedText.equals("null")){
    FeedText="";
}
        StrictMode.setThreadPolicy(policy);


        String serverRoad=FeedChannelID;
       // String filePath=FileUtil.getPath(this,fileUri);
        int index=filePath.lastIndexOf("/");
        String FileOriginalName="";
        String ImageFileLength="";
        if(index!=-1) {
            FileOriginalName = filePath.substring(index + 1);
        }
        File imgFile = new  File(filePath);
         ImageFileLength=String.valueOf(imgFile.length());

        String exten=  getFileExtension(FileOriginalName);
        exten=exten.replace(".","");
        String FeedObjectTypeID="";
        String mimeType="";
        if (exten.equals("jpg") || exten.equals("jpeg") || exten.equals("png") )
        {

            FeedObjectTypeID = "5";
            mimeType="image/"+exten;
        }
        else if (exten.equals("mp4")   || exten.equals("mkv")   || exten.equals("gif")  || exten.equals("3gp")  || exten.equals("wmv")  || exten.equals("webm") )
        {
            FeedObjectTypeID = "6";
            mimeType="video/"+exten;
        }
        else if ( exten.equals("xlsx") ||  exten.equals("xls") ||  exten.equals("doc") ||  exten.equals("docx")  || exten.equals("ppt")  || exten.equals("pptx") || exten.equals("txt")  || exten.equals("pdf"))
        {
            FeedObjectTypeID = "10";
            mimeType="application/pdf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.presentationml.presentation,text/plain";

        }
        else if (exten.equals("mp3") || exten.equals("m4a"))
        {
            FeedObjectTypeID = "13";
            mimeType="audio/"+exten;
        }

        long duration=0;
        int Height =0;
        int width=0;
        if(FeedObjectTypeID =="6" || FeedObjectTypeID =="13"){
            MediaPlayer mp = MediaPlayer.create(this, Uri.parse(filePath));
            duration = TimeUnit.MILLISECONDS.toSeconds(mp.getDuration());
            Height = mp.getVideoHeight();
            width = mp.getVideoWidth();
            mp.release();
        }

        int size = (int) imgFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(imgFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            uploadObject(imgFile, mimeType, FileOriginalName, FeedChannelID,String.valueOf(duration),String.valueOf(width), String.valueOf(Height), FeedText);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public  void uploadObject(File file,String mimeType,String FileName,String FeedChannelID,String duration,String width,String Height,String FeedText) throws IOException {
        //Toast.makeText (this, FileName , Toast.LENGTH_SHORT ).show() ;
        String attachmentName = getFileExtension(FileName);
        attachmentName=FileName.replace("."+attachmentName,"");
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart(attachmentName,FileName,
                        RequestBody.create(MediaType.parse(mimeType),
                                file))
                .build();
        Request request = new Request.Builder()
                .url("https://www.grapevine.work/workplace/SendFileToFTP?FolderPath="+FeedChannelID+"&Time_Duration=" +duration+"&Width="+width+"&Height="+Height+"&FeedText="+FeedText+"&UploadFrom=Android")
                .method("POST", body)
                .build();
        //Response response =
                client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, IOException e) {
                // Error

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // For the example, you can show an error dialog or a toast
                        // on the main UI thread
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String res = response.body().string();

UploadedFiles=UploadedFiles+1;

                Log.e("call response",response.message().toString());
                // Do something with the response
                //System.out.println(response.body().string());
                //Toast.makeText (getApplicationContext(), response.message() , Toast.LENGTH_SHORT ).show() ;

//                appPreferences.setShareLink("PostPage");
//                Intent i=new Intent(ShareActivity.this,HomeActivity.class);
//                i.putExtra(Intent.EXTRA_TEXT,"PostPage");
//                startActivity(i);

            }
        });
        //.execute();

        //Toast.makeText (this, response.message() , Toast.LENGTH_SHORT ).show() ;

//        appPreferences.setShareLink("PostPage");
//        Intent i=new Intent(ShareActivity.this,HomeActivity.class);
//        i.putExtra(Intent.EXTRA_TEXT,"PostPage");
//        startActivity(i);
//        System.out.println(response.body().string());
    }



    private String getFileExtension(String name) {
        // String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }
    public void Insert_FeebObjects(String FeedChannelID,String url,String ImageFileLength,
                                   String FeedObjectTypeID,long TimeDuration,int Height,int width,String FeedText) {

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
                args.add(new BasicNameValuePair("url", url));
                args.add(new BasicNameValuePair("ImageFileLength", ImageFileLength));
                args.add(new BasicNameValuePair("FeedObjectTypeID", FeedObjectTypeID));
                args.add(new BasicNameValuePair("Time_Duration",String.valueOf(TimeDuration)));
                args.add(new BasicNameValuePair("Width",String.valueOf(width)));
                args.add(new BasicNameValuePair("Height",String.valueOf(Height)));
                args.add(new BasicNameValuePair("FeedText",String.valueOf(FeedText)));

                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/UpdateTempFile";

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
    public String DeleteTempFiles(String FeedChannelID) {

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
                args.add(new BasicNameValuePair("FeedObjectFilePath", ""));
                String URL = "https://www.grapevine.work/";
                String methodName = "workplace/DeleteFileFromFtp";

                dataFromNetwork.setConfig(URL, methodName, args);
                Object response = dataFromNetwork.execute().get().toString();
                if (response != null) {
//                    FeedChannelID=response.toString();
//                    FeedChannelID=FeedChannelID.replaceAll("\"","").replaceAll("\n","");
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return FeedChannelID;
    }

}
