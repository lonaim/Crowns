package com.shaked.crowns;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
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
}