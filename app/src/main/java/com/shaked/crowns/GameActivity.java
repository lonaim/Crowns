package com.shaked.crowns;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

        Toast.makeText(this,game.getPlayerTurn().getName() + "'s Turn",Toast.LENGTH_SHORT).show();
        //battery
    }

    @Override
    public void onClick(View v) {
        ImageButton btn = (ImageButton) v;
        for (int i = 0; i < cardsP1.length; i++)//עובר עם מבצר 1
            if (cardsP1[i] == v) {
               Toast.makeText(this,game.giveCard(i,1),Toast.LENGTH_SHORT).show();
            }
        for (int j = 0; j < cardsP2.length; j++)//עובר על מבצר 2
            if (cardsP2[j] == v) {
                Toast.makeText(this,game.giveCard(j,2),Toast.LENGTH_SHORT).show();
            }
    }

    /**
     * Reload texture.
     */
//פעולה שעושה רענון לכל הטקסטורות של הקלפים
    public void reloadTexture(){
        int cardNum = 0;
        String cardShape = "";
        int countCard = 1;
        int resCount;
        int res;

        if(game.getDeckCard() != null){
            cardFromDeck.setBackgroundResource(cardFromDeck.getResources().getIdentifier("card" + game.getDeckCard().getShape() +
                    game.getDeckCard().getNum(), "drawable", getPackageName()));
        }
        else{cardFromDeck.setBackgroundColor(Color.parseColor("#00FFFFFF"));}
        for(int i = 0; i < cardsP1.length; i++) { //מבצר 1
            if(i < 3){
                cardNum = game.getPlayer(0).getSiege().getLine1().get(i).getCard().getNum();
                cardShape = game.getPlayer(0).getSiege().getLine1().get(i).getCard().getShape();
                countCard = game.getPlayer(0).getSiege().getLine1().get(i).getSize();
            }else if(i >= 3 && i < 7){
                cardNum = game.getPlayer(0).getSiege().getLine2().get(i-3).getCard().getNum();
                cardShape = game.getPlayer(0).getSiege().getLine2().get(i-3).getCard().getShape();
                countCard = game.getPlayer(0).getSiege().getLine2().get(i-3).getSize();
            }else if(i >= 7 && i < 9){
                cardNum = game.getPlayer(0).getSiege().getQk().get(i-7).getCard().getNum();
                cardShape = game.getPlayer(0).getSiege().getQk().get(i-7).getCard().getShape();
                countCard = game.getPlayer(0).getSiege().getQk().get(i-7).getSize();
            }

            //להפוך קלף
            res = cardsP1[i].getResources().getIdentifier("card" + cardShape + cardNum, "drawable", getPackageName());
            cardsP1[i].setBackgroundResource(res);
            if(countCard > 1){
                resCount = cardsP1[i].getResources().getIdentifier("count" + countCard, "drawable", getPackageName());
                cardsP1[i].setImageResource(resCount);
            }else {
                resCount = cardsP1[i].getResources().getIdentifier("", "drawable", getPackageName());
                cardsP1[i].setImageResource(resCount);
            }

            //בודק אם המשחק בפיינל רוונד- נשאר עם מלך ומלכה
            if(!game.getPlayer(0).isFinalRound()){
                cardsP1[7].setClickable(false);
                cardsP1[8].setClickable(false);
            }else{
                cardsP1[7].setClickable(true);
                cardsP1[8].setClickable(true);
            }

        }
        for(int j = 0; j < cardsP2.length; j++) { //מבצר 2
            if(j < 3){
                cardNum = game.getPlayer(1).getSiege().getLine1().get(j).getCard().getNum();
                cardShape = game.getPlayer(1).getSiege().getLine1().get(j).getCard().getShape();
                countCard = game.getPlayer(1).getSiege().getLine1().get(j).getSize();
            }else if(j >= 3 && j < 7){
                cardNum = game.getPlayer(1).getSiege().getLine2().get(j-3).getCard().getNum();
                cardShape = game.getPlayer(1).getSiege().getLine2().get(j-3).getCard().getShape();
                countCard = game.getPlayer(1).getSiege().getLine2().get(j-3).getSize();
            }else if(j >= 7 && j < 9){
                cardNum = game.getPlayer(1).getSiege().getQk().get(j-7).getCard().getNum();
                cardShape = game.getPlayer(1).getSiege().getQk().get(j-7).getCard().getShape();
                countCard = game.getPlayer(1).getSiege().getQk().get(j-7).getSize();
            }

            //להפוך קלף
            res = cardsP2[j].getResources().getIdentifier("card" + cardShape + cardNum, "drawable", getPackageName());
            cardsP2[j].setBackgroundResource(res);
            if(countCard > 1){
                resCount = cardsP2[j].getResources().getIdentifier("count" + countCard, "drawable", getPackageName());
                cardsP2[j].setImageResource(resCount);
            }else {
                resCount = cardsP2[j].getResources().getIdentifier("", "drawable", getPackageName());
                cardsP2[j].setImageResource(resCount);
            }

            if(!game.getPlayer(1).isFinalRound()){
                cardsP2[7].setClickable(false);
                cardsP2[8].setClickable(false);
            }else{
                cardsP2[7].setClickable(true);
                cardsP2[8].setClickable(true);
            }
        }
    }//סוגר פעולה
}