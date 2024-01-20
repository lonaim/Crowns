package com.shaked.crowns;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AboutMeActivity extends AppCompatActivity {
    TextView tvWelcome;
    InputStream is;
    InputStreamReader isr;
    BufferedReader br;
    Button btBack;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        tvWelcome = findViewById(R.id.tvWelcome);
        intent = new Intent(this,MainActivity.class);
        buildText();
    }

    private void buildText() {
        is = getResources().openRawResource(R.raw.about_me);
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
        String st, all = "";

        try {
            while ((st = br.readLine()) != null)
                all += st + "\n";
            br.close();
        } catch (IOException e) {
            Toast.makeText(this, "Erorr", Toast.LENGTH_SHORT).show();
        }
        tvWelcome.setText(all);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        if (menu instanceof MenuBuilder) {
            MenuBuilder mb = (MenuBuilder) menu;
            mb.setOptionalIconsVisible(true);
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
        if (id == R.id.Home) {
            Intent go = new Intent(this, MainActivity.class);
            startActivity(go);
        }

        if (id == R.id.About) {
            Intent go = new Intent(this, AboutMeActivity.class);
            startActivity(go);
        }

        return super.onOptionsItemSelected(item);
    }
}