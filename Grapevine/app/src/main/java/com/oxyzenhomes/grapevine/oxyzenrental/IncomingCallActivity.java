package com.oxyzenhomes.grapevine.oxyzenrental;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.oxyzenhomes.grapevine.R;
import com.oxyzenhomes.grapevine.appprefrence.AppPreferences;

public class IncomingCallActivity extends AppCompatActivity {
    private static AppPreferences appPreferences;

    private static Activity activity;
    float mPreviousX;
    float mPreviousY;
    Context context;
    OnSwipeTouchListener onSwipeTouchListener;
    ImageView imageView;
    ImageView reject_icon;
    Intent intent;

    private static String AlertType;
    private static String FeedChannelID;
    private static String ChannelFeedChannelID;
    private static String UserName;
    private static String ImageUrl;
    private static String FeedID;
    private static int NOTIFY_ID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_incoming_call);
        context=getApplicationContext();
        intent=getIntent();
        activity=this;
        appPreferences=new AppPreferences(getApplicationContext());
        AlertType=intent.getStringExtra("AlertType");
        FeedChannelID=intent.getStringExtra("FeedChannelID");
        ChannelFeedChannelID=intent.getStringExtra("ChannelFeedChannelID");
        UserName= intent.getStringExtra("UserName");
        ImageUrl=intent.getStringExtra("UserPic");
        FeedID= intent.getStringExtra("FeedID");
        if(UserName!=null){
            UserName=UserName.replace("Incoming video call from ","").replace("Incoming audio call from","");
        }

         NOTIFY_ID=intent.getIntExtra("NOTIFY_ID",0);
        //get the image view
         imageView = (ImageView)findViewById(R.id.call_icon);
         imageView.setContentDescription("Accept");
        reject_icon = (ImageView)findViewById(R.id.reject_icon);
        reject_icon.setContentDescription("Reject");
        onSwipeTouchListener = new OnSwipeTouchListener(this, imageView);
        onSwipeTouchListener = new OnSwipeTouchListener(this, reject_icon);
        TextView User_Name=(TextView) findViewById(R.id.User_Name);
        User_Name.setText(UserName);
        CircularImageView imageProfile=(CircularImageView)findViewById(R.id.imageProfile);
        Glide.with(this).load(ImageUrl).into(imageProfile);

        blink();
        //set the ontouch listener
//        imageView.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                float x = event.getX();
//                float y = event.getY();
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//                        ImageView view = (ImageView) v;
//                        //overlay is black with transparency of 0x77 (119)
//                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                        view.invalidate();
//
//                        break;
//                    }
//                    case MotionEvent.ACTION_MOVE:
//
//                        float dx = x - mPreviousX;
//                        float dy = y - mPreviousY;
//
//                        // Here you can try to detect the swipe. It will be necessary to
//                        // store more than the previous value to check that the user move constantly in the same direction
//                        //detectSwipe(dx, dy);
//                    case MotionEvent.ACTION_UP:
//                    {
//                        ImageView view = (ImageView) v;
//                        //overlay is black with transparency of 0x77 (119)
//                        view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
//                        view.invalidate();
//                        Toast.makeText(getApplicationContext(), "Swipe Up", Toast.LENGTH_LONG).show();
//                        break;
//                    }
//                    case MotionEvent.ACTION_CANCEL: {
//                        ImageView view = (ImageView) v;
//                        //clear the overlay
//                        view.getDrawable().clearColorFilter();
//                        view.invalidate();
//                        break;
//                    }
//                }
//                mPreviousX = x;
//                mPreviousY = y;
//                return false;
//            }
//        });

    }
    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView swipe_reject = (TextView) findViewById(R.id.swipe_reject);
                        TextView swipe_accept = (TextView) findViewById(R.id.swipe_accept);
                        if(swipe_accept.getVisibility() == View.VISIBLE){
                            swipe_accept.setVisibility(View.INVISIBLE);
                        }else{
                            swipe_accept.setVisibility(View.VISIBLE);
                        }
                        if(swipe_reject.getVisibility() == View.VISIBLE){
                            swipe_reject.setVisibility(View.INVISIBLE);
                        }else{
                            swipe_reject.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
    public static class OnSwipeTouchListener implements View.OnTouchListener {
        private final GestureDetector gestureDetector;
        Context context;
        View imageView1;
        String Description="";
        OnSwipeTouchListener(Context ctx, View mainView) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
            mainView.setOnTouchListener(this);
            imageView1=mainView;
            Description=mainView.getContentDescription().toString();
            context = ctx;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
        public class GestureListener extends
                GestureDetector.SimpleOnGestureListener {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();

                            //imageView.setTranslationX(e2.getX());
                        } else {
                            onSwipeTop(e1.getY()+e2.getY());

                        }
                        result = true;
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }
        void onSwipeRight() {
            //Toast.makeText(context, "Swiped Right", Toast.LENGTH_SHORT).show();
            this.onSwipe.swipeRight();
        }
        void onSwipeLeft() {
            //Toast.makeText(context, "Swiped Left", Toast.LENGTH_SHORT).show();
            this.onSwipe.swipeLeft();
        }
        void onSwipeTop(float diffY) {
            //Toast.makeText(context, Description, Toast.LENGTH_SHORT).show();
            //imageView1.setTranslationY(diffY);
            if(Description.equals("Accept")){
                CallAccepted(context);
            }
            else{
                CallRejected(context);
            }

            this.onSwipe.swipeTop();
        }
        void onSwipeBottom() {
            //Toast.makeText(context, "Swiped Down", Toast.LENGTH_SHORT).show();
            this.onSwipe.swipeBottom();
        }
        interface onSwipeListener {
            void swipeRight();
            void swipeTop();
            void swipeBottom();
            void swipeLeft();
        }
        onSwipeListener onSwipe;
    }

    public static void CallAccepted(Context context){
        if(RingtonePlay.isplayingAudio){
            RingtonePlay.stopAudio();
        }
        appPreferences.setIsAccepted(true);
        Intent acceptIntent = new Intent(context, SplashScreen.class);
        acceptIntent.putExtra("type",AlertType+"-Accepted");
        acceptIntent.putExtra("FeedChannelID",FeedChannelID);
        acceptIntent.putExtra("ChannelFeedChannelID",ChannelFeedChannelID);
        acceptIntent.putExtra("UserName",UserName);
        acceptIntent.putExtra("UserPic",ImageUrl);
        acceptIntent.putExtra("FeedID",FeedID);
        acceptIntent.putExtra("NOTIFY_ID",NOTIFY_ID);
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
        nMgr.cancel(NOTIFY_ID);
        context.startActivity(acceptIntent);
    }
    public static void CallRejected(Context context){
        if(RingtonePlay.isplayingAudio){
            RingtonePlay.stopAudio();
        }
        appPreferences.setIsAccepted(true);
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) context.getSystemService(ns);
        nMgr.cancel(NOTIFY_ID);
        activity.finishAffinity();

    }
}
