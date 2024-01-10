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
    private boolean isFlipped = false;
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
        cardFromDeck.setOnClickListener(this);
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
    }

    public void reloadTexture(){
        int cardNum = 0;
        String cardShape = "";
        int countCard = 1;
        int resCount;
        int res;

        if(game.getDeckCard()==null){
            cardFromDeck.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        }
        else{
        cardFromDeck.setBackgroundResource(cardFromDeck.getResources().getIdentifier("card" + game.getDeckCard().getShape() +
                game.getDeckCard().getNum(), "drawable", getPackageName()));}

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

    @Override
    public void onClick(View view) {
        /*for (int i = 0; i < cardsP1.length; i++)//עובר עם מבצר 1
            if (cardsP1[i] == view) {
                Toast.makeText(this, game.returnCard(i).toString(), Toast.LENGTH_SHORT).show();
                ;
            }
        for (int j = 0; j < cardsP2.length; j++)//עובר על מבצר 2
            if (cardsP2[j] == view) {
                Toast.makeText(this, game.returnCard(j).toString(), Toast.LENGTH_SHORT).show();
            }*/


        ImageButton btn = (ImageButton) view;
        final int RESDEF = R.drawable.back; //כתובת של קלף הפוך

        int res; //כתובת קלף הפוך
        boolean isTapDeck = game.getDeckCard() != null;
        int id = btn.getId();
        Card cardPress = new Card(0, "");

        if (R.id.deck == id) {
            if (!isTapDeck && game.getDeckGame().getSize() < 1)
                Toast.makeText(this, "The Deck is Empty!", Toast.LENGTH_SHORT).show();
            else if (!isTapDeck) {
                game.pickCard();
                num = game.getDeckCard().getNum();
                shape = game.getDeckCard().getShape();

                res = cardFromDeck.getResources().getIdentifier("card" + shape + num, "drawable", getPackageName());
                cardFromDeck.setBackgroundResource(res);
            } else {
                Toast.makeText(this, "You have card", Toast.LENGTH_SHORT).show();
            }
        } else if (R.id.srufim == id) {
            if (isTapDeck) {
                game.doBurn(game.getDeckCard());
                game.setDeckCard(null);
                game.nextTurn();

            } else {
                Toast.makeText(this, "Take card from deck", Toast.LENGTH_LONG).show();
            }
        }
        else {
            if(isTapDeck) {
                if (game.getK() == 0) {
                    for (int i = 0; i < cardsP1.length; i++) {
                        if (cardsP1[i] == view) {
                            if(game.makeATurn(i, 2))game.nextTurn();
                        }
                    }

                    for (int j = 0; j < cardsP2.length; j++) {
                        if (cardsP2[j] == view) {
                            if(game.makeATurn(j, 3))game.nextTurn();
                        }
                    }
                }
                else {
                    for (int i = 0; i < cardsP2.length; i++) {
                        if (cardsP2[i] == view) {
                            if(game.makeATurn(i, 2))game.nextTurn();
                            ;
                        }
                    }

                    for (int j = 0; j < cardsP1.length; j++) {
                        if (cardsP1[j] == view) {
                            if(game.makeATurn(j, 3))game.nextTurn();
                            ;
                        }
                    }
                }
            }
        }
        reloadTexture();
    }

    //פעולה שעוברת אקטיביטי עם יש ניצחון ומעבירה את שם המנצח
    public void isWon(){
        //בודק ניצחון
        if(game.getPlayerNotTurn().getSiege().isEmpty()){
            Toast.makeText(this,game.getPlayerTurn().getName()+" WON",Toast.LENGTH_LONG).show();
        }

    }

    private void flipImageButton(ImageButton imageButton) {
        if (isFlipped) {
            imageButton.setRotation(0); // Reset rotation to 0 degrees
        } else {
            imageButton.setRotation(180); // Rotate 180 degrees
        }
        isFlipped = !isFlipped; // Toggle the flipped state
    }
}