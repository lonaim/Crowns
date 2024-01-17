package com.shaked.crowns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
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
        startActivity(intent);
    }
}