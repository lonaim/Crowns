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
    private War war;
    private TextView tvPlayerName;
    private Button btnAgain,btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        myDb = new DatabaseHelper(this);

        tvPlayerName = findViewById(R.id.tvPlayerName);
        btnAgain = findViewById(R.id.btnAgain);
        btnAgain.setOnClickListener(this);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        in=getIntent();
        if(in!=null&&in.getExtras()!=null){
            Bundle xtras = in.getExtras();
            playerWinName= xtras.getString("PLAYERWIN_NAME");
            playerLoseName= xtras.getString("PLAYERLOSE_NAME");
        }

        tvPlayerName.setText(playerWinName);

        if(myDb.dataExists(playerWinName,playerLoseName)){
            Cursor res = myDb.getDataCursor(playerWinName,playerLoseName);
            while (res.moveToNext()) {
            war = new War(res.getString(0),res.getString(2),Integer.parseInt(res.getString(1)),Integer.parseInt(res.getString(3)));
            }
            war.setP1Scor(war.getP1Scor()+1);
            Toast.makeText(this, "War like this Exists", Toast.LENGTH_SHORT).show();
        }
        else{
            Cursor res = myDb.getDataCursor(playerLoseName,playerWinName);
            while (res.moveToNext()) {
                war = new War(res.getString(0), res.getString(2), Integer.parseInt(res.getString(1)), Integer.parseInt(res.getString(3)));
            }
            war.setP2Scor(war.getP2Scor()+1);
            Toast.makeText(this, "War like this Exists", Toast.LENGTH_SHORT).show();
        }

        myDb.updateData(war);
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