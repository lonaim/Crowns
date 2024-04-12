package com.shaked.crowns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    ImageButton btnRules;
    ListView lView;
    private static MyCustomAdapter adapter;
    private MediaPlayer mWork;
    ArrayList<String> songs;
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        doBindService();

        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

        lView = findViewById(R.id.lView);

        String[] dataSongs = {
                "Medieval Story", "Medieval Man"};

        adapter = new MyCustomAdapter(this,dataSongs);
        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                int place = i;
                switch (i+1){
                    case 1: mServ.changeSong("musicmf");
                        break;
                    case 2:
                        mServ.changeSong("medievalfantasy");
                        break;
                }
                if(MainActivity.isPlaying) mServ.resumeMusic();
                else mServ.pauseMusic();
            }
        });

        btnRules = findViewById(R.id.btnRules);
        btnRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(view);
            }
        });
    }

    public void openUrl(View view) {
        // Replace the URL with your desired URL
        String url = "https://docs.google.com/presentation/d/1EINGutdJT0P2vyYOQlnGDGbCqcZY7I4z/edit#slide=id.p1";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle the case where the activity to handle the intent is not found
            Toast.makeText(this, "Cannot open URL. Please make sure you have an internet connection and try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder mb = (MenuBuilder) menu;
            mb.setOptionalIconsVisible(true);
        }
        if (MainActivity.isPlaying) {
            MainActivity.mServ.resumeMusic();
            menu.findItem(R.id.btnMute).setIcon(R.drawable.unmute);
        }
        else {
            MainActivity.mServ.pauseMusic();
            menu.findItem(R.id.btnMute).setIcon(R.drawable.mute);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnMute) {
            if (MainActivity.isPlaying) {
                MainActivity.mServ.pauseMusic();
                item.setTitle("Unmute");
                item.setIcon(R.drawable.mute);
            } else {
                MainActivity.mServ.resumeMusic();
                item.setTitle("Mute");
                item.setIcon(R.drawable.unmute);
            }
            MainActivity.isPlaying = !MainActivity.isPlaying;
        }
        if (id == R.id.ExitApp) {
            new AlertDialog.Builder(this).setTitle("Exit").
                    setMessage("Are you sure you want go home?").
                    setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent go = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(go);
                        }
                    })/*.setIcon(R.drawable.btnback)*/.show();
        }

        if (id == R.id.About) {
            Intent go = new Intent(this, AboutMeActivity.class);
            startActivity(go);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.mServ.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.mServ != null) {
            MainActivity.mServ.resumeMusic();
            if (MainActivity.isPlaying) {
                MainActivity.mServ.resumeMusic();
            }
            else {
                MainActivity.mServ.pauseMusic();
            }
        }
    }

}