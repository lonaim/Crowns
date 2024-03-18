package com.shaked.crowns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class StatActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView tableRowPlayer,tableRowScore;

    Button btnDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);
        myDb = new DatabaseHelper(this);
        tableRowPlayer = findViewById(R.id.infoTabPlayers);
        tableRowScore = findViewById(R.id.infoTabScore);

        relodTab();

        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDb.deleteData();
                relodTab();
            }
        });
    }

    public void relodTab(){
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            tableRowPlayer.setText("");
            tableRowScore.setText("");
        } else {
            StringBuffer bufferPlayer = new StringBuffer();
            StringBuffer bufferScore = new StringBuffer();
            while (res.moveToNext()) {
                bufferPlayer.append(res.getString(0) + " vs " + res.getString(2) + "\n"+"\n");
                bufferScore.append(Integer.parseInt(res.getString(1)) + " - " + Integer.parseInt(res.getString(3)) + "\n"+"\n");
            }
            tableRowPlayer.setText(bufferPlayer);
            tableRowScore.setText(bufferScore);
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

    protected void onResume() {
        super.onResume();
        if (MainActivity.mServ != null) {
            MainActivity.mServ.resumeMusic();
            if (MainActivity.isPlaying) {
                MainActivity.mServ.resumeMusic();
            } else {
                MainActivity.mServ.pauseMusic();
            }
        }
    }

    protected void onPause() {
        super.onPause();
        MainActivity.mServ.pauseMusic();
    }
}