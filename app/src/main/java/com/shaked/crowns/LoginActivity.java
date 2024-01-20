package com.shaked.crowns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper myDb;
    Button btnWar,btnBack;
    EditText etP1Name, etP2Name;

    boolean clickTwice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        clickTwice = false;
        myDb = new DatabaseHelper(this);

        etP1Name = findViewById(R.id.etP1Name);
        etP2Name = findViewById(R.id.etP2Name);
        btnWar = findViewById(R.id.btnWar);
        btnWar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean isInserted=false;

                Intent intent = new Intent(LoginActivity.this, GameActivity.class);

                if(etP1Name.getText().toString().isEmpty() || etP2Name.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Names", Toast.LENGTH_SHORT).show();
                }
                else {
                    War war = new War(etP1Name.getText().toString(), etP2Name.getText().toString());
                    if(!myDb.dataExists(war.getP1Name(),war.getP2Name())&&!myDb.dataExists(war.getP2Name(),war.getP1Name())){
                    isInserted = myDb.insertData(new War(war.getP1Name(),war.getP2Name()));

                    if(isInserted){
                    intent.putExtra("P1", war.getP1Name());
                    intent.putExtra("P2", war.getP2Name());
                    startActivity(intent);}

                    else {
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                    }
                    else{
                        if(!clickTwice){
                        Toast.makeText(LoginActivity.this, "War like this Exists, if its you click again", Toast.LENGTH_SHORT).show();
                        clickTwice = true;}
                        else {
                            intent.putExtra("P1", etP1Name.getText().toString());
                            intent.putExtra("P2", etP2Name.getText().toString());
                            startActivity(intent);
                        }
                    }
                }
            };
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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
                            finish();
                        }
                    })/*.setIcon(R.drawable.btnback)*/.show();
        }

        if (id == R.id.About) {
            Intent go = new Intent(this, AboutMeActivity.class);
            startActivity(go);
        }

        return super.onOptionsItemSelected(item);
    }
}