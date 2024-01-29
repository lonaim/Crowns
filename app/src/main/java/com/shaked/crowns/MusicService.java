package com.shaked.crowns;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MusicService extends Service  implements OnErrorListener {

    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mPlayer;
    private int length = 0;
    private static final String DEFAULT_SONG = "musicmf";
    private String currentSong = DEFAULT_SONG;


    public MusicService() {
    }

    public class ServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        int rawResourceId = getRawResourceId(currentSong);

        // Create MediaPlayer with the raw resource
        mPlayer = MediaPlayer.create(this, rawResourceId);

        // Now you can start or perform other operations on the MediaPlayer
        mPlayer.start();

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100, 100);
        }


        mPlayer.setOnErrorListener(new OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int
                    extra) {

                onError(mPlayer, what, extra);
                return true;
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.start();
        return START_NOT_STICKY;
    }

    public void pauseMusic() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            length = mPlayer.getCurrentPosition();

        }
    }

    public void resumeMusic() {
        if (mPlayer.isPlaying() == false) {
            mPlayer.seekTo(length);
            mPlayer.start();
        }
    }

    public void playMusic() {
        int rawResourceId = getRawResourceId(currentSong);

        // Create MediaPlayer with the raw resource
        mPlayer = MediaPlayer.create(this, rawResourceId);

        // Now you can start or perform other operations on the MediaPlayer
        mPlayer.start();

        if (mPlayer != null) {
            mPlayer.setLooping(true);
            mPlayer.setVolume(100, 100);
        }
        }

    public void stopMusic() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        stopSelf();  // Stop the service
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
        if (mPlayer != null) {
            try {
                mPlayer.stop();
                mPlayer.release();
            } finally {
                mPlayer = null;
            }
        }
        return false;
    }

    // Method to get the resource ID for a given raw file name
    private int getRawResourceId(String fileName) {
        return getResources().getIdentifier(fileName, "raw", getPackageName());
    }

    // Method to change the song
    public void changeSong(String newSong) {
        // Stop and release the current MediaPlayer
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }

        // Set the new song
        currentSong = newSong;

        // Specify the raw resource identifier for the new song
        playMusic();
    }

    
}