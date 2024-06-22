package com.shaked.crowns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WinActivity extends AppCompatActivity implements TextView.OnClickListener {
    private Intent in;

    private DatabaseHelper myDb;

    private String playerWinName = "";
    private String playerLoseName = "";
    private boolean timerEnd;
    private War war;
    private TextView tvPlayerName,tvWon;
    private Button btnAgain,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        myDb = new DatabaseHelper(this);

        tvPlayerName = findViewById(R.id.tvPlayerName);
        tvWon = findViewById(R.id.tvWon);
        btnAgain = findViewById(R.id.btnAgain);
        btnAgain.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        in=getIntent();
        Bundle xtras = in.getExtras();
        timerEnd = xtras.getBoolean("TIMER_END");

        if(!timerEnd){
        if(in!=null&&in.getExtras()!=null){
            Bundle morerXtras = in.getExtras();
            playerWinName= morerXtras.getString("PLAYERWIN_NAME");
            playerLoseName= morerXtras.getString("PLAYERLOSE_NAME");
        }

        tvPlayerName.setText(playerWinName);

        if(myDb.dataExists(playerWinName,playerLoseName)){
            Cursor res = myDb.getDataCursor(playerWinName,playerLoseName);
            while (res.moveToNext()) {
            war = new War(res.getString(0),res.getString(2),Integer.parseInt(res.getString(1)),Integer.parseInt(res.getString(3)));
            }
            war.setP1Scor(war.getP1Scor()+1);
        }
        else{
            Cursor res = myDb.getDataCursor(playerLoseName,playerWinName);
            while (res.moveToNext()) {
                war = new War(res.getString(0), res.getString(2), Integer.parseInt(res.getString(1)), Integer.parseInt(res.getString(3)));
            }
            war.setP2Scor(war.getP2Scor()+1);
        }

        myDb.updateData(war);
    }
        else{
            tvPlayerName.setText("Tie");
            tvWon.setText("no one won");
        }
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