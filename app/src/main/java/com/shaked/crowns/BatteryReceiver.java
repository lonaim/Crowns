package com.shaked.crowns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int level = intent.getIntExtra("level", 0);

        if (level < 30) {
            Toast.makeText(context, "The Battery is Low", Toast.LENGTH_SHORT).show();

        }
    }
}