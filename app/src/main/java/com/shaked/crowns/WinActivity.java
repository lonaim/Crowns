package com.shaked.crowns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity implements TextView.OnClickListener {
    private Intent in;
    private String playerName = "";
    private TextView tvPlayerName;
    private Button btnAgain,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        tvPlayerName = findViewById(R.id.tvPlayerName);
        btnAgain = findViewById(R.id.btnAgain);
        btnAgain.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        in=getIntent();
        if(in!=null&&in.getExtras()!=null){
            Bundle xtras = in.getExtras();
            playerName= xtras.getString("PLAYER_NAME");
        }

        tvPlayerName.setText(playerName);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnAgain) {
            GameActivity.game.resetGame();
            Intent intent = new Intent(WinActivity.this, GameActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.btnBack) {
            Intent intent = new Intent(WinActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}