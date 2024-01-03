package com.shaked.crowns;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity implements TextView.OnClickListener {

    /*תכונות*/
    private ImageButton[] cardsP1; //מבצר של שחקן 1
    private ImageButton[] cardsP2; //מבצר של שחקן 2
    private ImageButton cardFromDeck; //קלף מהקופה
    private ImageButton deck; //הקופה
    private ImageButton srufim; //חפיסת השרופים

    GameManager game; // מנהל משחק

    int rePlace; //שמירת מיקום קלף במבצר

    int num;
    String shape;

    boolean isDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        cardFromDeck = (ImageButton) findViewById(R.id.useCard);
        deck = (ImageButton) findViewById(R.id.deck);
        deck.setOnClickListener(this);
        srufim = (ImageButton) findViewById(R.id.srufim);
        srufim.setOnClickListener(this);
        game = new GameManager(this);

        rePlace = 0;
        num = 0;
        shape = "";
        isDone = false;

        /*connect with id in xml to arr in activity*/
        cardsP1 = new ImageButton[9];
        cardsP2 = new ImageButton[9];
        int id;
        for (int i = 0; i < 9; i++){//9 קלפים מבצר 1
            id = getResources().getIdentifier("card" + i + "_p1", "id", getPackageName());
            cardsP1[i] =(ImageButton) findViewById(id);
            cardsP1[i].setOnClickListener(this);
        }
        for (int i = 0; i < 9; i++){//9 קלפים מבצר 2
            id = getResources().getIdentifier("card" + i + "_p2", "id", getPackageName());
            cardsP2[i] =(ImageButton) findViewById(id);
            cardsP2[i].setOnClickListener(this);
        }

        /*reload the texture*/
        reloadTexture();

        /*שינוי שמות*/
        Intent in = getIntent();
        if (in != null) {
            Bundle xBundle = in.getExtras();
            String strData1 = xBundle.getString("data1");
            String strData2 = xBundle.getString("data2");
            Log.d("****** in in ",strData1);
            Log.d("****** in in ",strData2);
            game.setPlayersName(strData1, strData2);
        }

        Toast.makeText(this,game.getPlayerTurn().getName() + "'s Turn",Toast.LENGTH_SHORT).show();
        //battery
    }

    @Override
    public void onClick(View v) {

    }
}