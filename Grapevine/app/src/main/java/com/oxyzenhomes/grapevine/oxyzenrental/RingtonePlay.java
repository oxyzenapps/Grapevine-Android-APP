package com.oxyzenhomes.grapevine.oxyzenrental;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.provider.Settings;

import com.oxyzenhomes.grapevine.R;


public class RingtonePlay {

    public static MediaPlayer mMediaPlayer;
    private static SoundPool soundPool;
    public static boolean isplayingAudio=false;
    public int mediaplayer=0;
    public static void playAudio(Context c){

        Uri alarmSound =Uri.parse("android.resource://"+c.getPackageName()+"/"+ R.raw.videocallring);
        //mMediaPlayer = MediaPlayer.create(c, alarmSound);
        mMediaPlayer = MediaPlayer.create(c, Settings.System.DEFAULT_RINGTONE_URI);
        mMediaPlayer.setLooping(true);

        if(!mMediaPlayer.isPlaying())
        {

            isplayingAudio=true;
            mMediaPlayer.start();
//            if(mediaplayer==0){
//
//                mMediaPlayer.setLooping(true);
//                mediaplayer=1;
//                mMediaPlayer.start();
//            }
        }
    }
    public static void playTringRingtone(Context c){

        Uri alarmSound =Uri.parse("android.resource://"+c.getPackageName()+"/"+ R.raw.tringringtone);
        mMediaPlayer = MediaPlayer.create(c, alarmSound);
        //mMediaPlayer = MediaPlayer.create(c, Settings.System.DEFAULT_RINGTONE_URI);
        mMediaPlayer.setLooping(true);

        if(!mMediaPlayer.isPlaying())
        {

            isplayingAudio=true;
            mMediaPlayer.start();
//            if(mediaplayer==0){
//
//                mMediaPlayer.setLooping(true);
//                mediaplayer=1;
//                mMediaPlayer.start();
//            }
        }
    }
    public static void stopAudio(){
        isplayingAudio=false;
        mMediaPlayer.stop();
    }

}