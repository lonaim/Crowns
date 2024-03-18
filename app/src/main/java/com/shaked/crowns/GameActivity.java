package com.shaked.crowns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class GameActivity extends AppCompatActivity implements TextView.OnClickListener {

    /*תכונות*/
    private CountDownTimer countDownTimer;//timer
    private TextView time,tvName1,tvName2;//textviews
    private ImageButton[] cardsP1; //מבצר של שחקן 1
    private ImageButton[] cardsP2; //מבצר של שחקן 2
    private ImageButton cardFromDeck; //קלף מהקופה
    private ImageButton deck; //הקופה
    private boolean isFlipped = false;
    private ImageButton srufim; //חפיסת השרופים

    public static GameManager game; // מנהל משחק

    int rePlace; //שמירת מיקום קלף במבצר

    int num;
    String shape;

    Player p1, p2;
    String p1Name, p2Name;

    Intent in;
    private static final long VIBRATION_DURATION = 3000; // 3 seconds
    private Vibrator vibrator;//vibration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        time = findViewById(R.id.timerTextView);
        startCountdown();

        cardFromDeck = (ImageButton) findViewById(R.id.useCard);
        cardFromDeck.setOnClickListener(this);
        deck = (ImageButton) findViewById(R.id.deck);
        deck.setOnClickListener(this);
        srufim = (ImageButton) findViewById(R.id.srufim);
        srufim.setOnClickListener(this);
        game = new GameManager(this);
        tvName1 = findViewById(R.id.tvName1);
        tvName2 = findViewById(R.id.tvName2);



        in = getIntent();
        if (in != null && in.getExtras() != null) {
            Bundle xtras = in.getExtras();
            p1Name = xtras.getString("P1");
            p2Name = xtras.getString("P2");
        }
        p1 = new Player(p1Name, game.getPlayer(0).getSiege());
        p2 = new Player(p2Name, game.getPlayer(1).getSiege());
        tvName1.setText(p1Name);
        tvName2.setText(p2Name);
        game.setPlayers(new Player[]{p1, p2});

        rePlace = 0;
        num = 0;
        shape = "";

        /*connect with id in xml to arr in activity*/
        cardsP1 = new ImageButton[9];
        cardsP2 = new ImageButton[9];
        int id;
        for (int i = 0; i < 9; i++) {//9 קלפים מבצר 1
            id = getResources().getIdentifier("card" + i + "_p1", "id", getPackageName());
            cardsP1[i] = (ImageButton) findViewById(id);
            cardsP1[i].setOnClickListener(this);
        }
        for (int i = 0; i < 9; i++) {//9 קלפים מבצר 2
            id = getResources().getIdentifier("card" + i + "_p2", "id", getPackageName());
            cardsP2[i] = (ImageButton) findViewById(id);
            cardsP2[i].setOnClickListener(this);
        }

        /*reload the texture*/
        reloadTexture();

        registerReceiver(new BatteryReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

    }

    public void reloadTexture() {
        int cardNum = 0;
        String cardShape = "";
        int countCard = 1;
        int resCount;
        int res;

        if (game.getDeckCard() == null) {
            cardFromDeck.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        } else {
            cardFromDeck.setBackgroundResource(cardFromDeck.getResources().getIdentifier("card" + game.getDeckCard().getShape() +
                    game.getDeckCard().getNum(), "drawable", getPackageName()));
        }

        for (int i = 0; i < cardsP1.length; i++) { //מבצר 1
            if (i < 3) {
                cardNum = game.getPlayer(0).getSiege().getLine1().get(i).getCard().getNum();
                cardShape = game.getPlayer(0).getSiege().getLine1().get(i).getCard().getShape();
                countCard = game.getPlayer(0).getSiege().getLine1().get(i).getSize();
            } else if (i >= 3 && i < 7) {
                cardNum = game.getPlayer(0).getSiege().getLine2().get(i - 3).getCard().getNum();
                cardShape = game.getPlayer(0).getSiege().getLine2().get(i - 3).getCard().getShape();
                countCard = game.getPlayer(0).getSiege().getLine2().get(i - 3).getSize();
            } else if (i >= 7 && i < 9) {
                cardNum = game.getPlayer(0).getSiege().getQk().get(i - 7).getCard().getNum();
                cardShape = game.getPlayer(0).getSiege().getQk().get(i - 7).getCard().getShape();
                countCard = game.getPlayer(0).getSiege().getQk().get(i - 7).getSize();
            }

            //להפוך קלף
            res = cardsP1[i].getResources().getIdentifier("card" + cardShape + cardNum, "drawable", getPackageName());
            cardsP1[i].setBackgroundResource(res);
            if (countCard > 1) {
                resCount = cardsP1[i].getResources().getIdentifier("count" + countCard, "drawable", getPackageName());
                cardsP1[i].setImageResource(resCount);
            } else {
                resCount = cardsP1[i].getResources().getIdentifier("", "drawable", getPackageName());
                cardsP1[i].setImageResource(resCount);
            }

            //בודק אם המשחק בפיינל רוונד- נשאר עם מלך ומלכה
            if (!game.getPlayer(0).isFinalRound()) {
                cardsP1[7].setClickable(false);
                cardsP1[8].setClickable(false);
            } else {
                cardsP1[7].setClickable(true);
                cardsP1[8].setClickable(true);
            }

        }
        for (int j = 0; j < cardsP2.length; j++) { //מבצר 2
            if (j < 3) {
                cardNum = game.getPlayer(1).getSiege().getLine1().get(j).getCard().getNum();
                cardShape = game.getPlayer(1).getSiege().getLine1().get(j).getCard().getShape();
                countCard = game.getPlayer(1).getSiege().getLine1().get(j).getSize();
            } else if (j >= 3 && j < 7) {
                cardNum = game.getPlayer(1).getSiege().getLine2().get(j - 3).getCard().getNum();
                cardShape = game.getPlayer(1).getSiege().getLine2().get(j - 3).getCard().getShape();
                countCard = game.getPlayer(1).getSiege().getLine2().get(j - 3).getSize();
            } else if (j >= 7 && j < 9) {
                cardNum = game.getPlayer(1).getSiege().getQk().get(j - 7).getCard().getNum();
                cardShape = game.getPlayer(1).getSiege().getQk().get(j - 7).getCard().getShape();
                countCard = game.getPlayer(1).getSiege().getQk().get(j - 7).getSize();
            }

            //להפוך קלף
            res = cardsP2[j].getResources().getIdentifier("card" + cardShape + cardNum, "drawable", getPackageName());
            cardsP2[j].setBackgroundResource(res);
            if (countCard > 1) {
                resCount = cardsP2[j].getResources().getIdentifier("count" + countCard, "drawable", getPackageName());
                cardsP2[j].setImageResource(resCount);
            } else {
                resCount = cardsP2[j].getResources().getIdentifier("", "drawable", getPackageName());
                cardsP2[j].setImageResource(resCount);
            }

            if (!game.getPlayer(1).isFinalRound()) {
                cardsP2[7].setClickable(false);
                cardsP2[8].setClickable(false);
            } else {
                cardsP2[7].setClickable(true);
                cardsP2[8].setClickable(true);
            }

        }
    }//סוגר פעולה

    @Override
    public void onClick(View view) {
        ImageButton btn = (ImageButton) view;
        final int RESDEF = R.drawable.back; //כתובת של קלף הפוך

        int res; //כתובת קלף הפוך
        boolean isTapDeck = game.getDeckCard() != null;
        int id = btn.getId();
        boolean doTurn = false;

        if (R.id.deck == id) {//אם לחץ על הקופה
            if (!isTapDeck && game.getDeckGame().getSize() < 1) {//אם אין קלפים בקופה
                Toast.makeText(this, "The Deck is Empty! - the system resting the Deck", Toast.LENGTH_SHORT).show();
                game.resetDeckAfterAllBurn();//תאפס את הקופה
                game.pickCard();//תבחר קלף
            } else if (!isTapDeck) {//אם זה פעם ראשונה שהקופה נלחצה בתור
                game.pickCard();
                num = game.getDeckCard().getNum();
                shape = game.getDeckCard().getShape();

                res = cardFromDeck.getResources().getIdentifier("card" + shape + num, "drawable", getPackageName());
                cardFromDeck.setBackgroundResource(res);//משנה את הטקסטורה של הקלף
            } else {
                Toast.makeText(this, "You have card", Toast.LENGTH_SHORT).show();
            }
        } else if (R.id.srufim == id) {//לחץ על הקופת שרופים
            if (isTapDeck) {//אם לחץ על הקופסה לפני
                game.doBurn(game.getDeckCard());
                game.setDeckCard(null);
                doTurn = true;

            } else {
                Toast.makeText(this, "Take card from deck", Toast.LENGTH_LONG).show();
            }
        } else {//לחץ על אחד הקלפים
            if (isTapDeck) {//יש קלף
                if (game.getK() == 0) {//אם זה תור שחקן1
                    for (int i = 0; i < cardsP1.length; i++) {
                        if (cardsP1[i] == view) {
                            if (game.makeATurn(i, 2)) doTurn = true;
                        }
                    }

                    for (int j = 0; j < cardsP2.length; j++) {
                        if (cardsP2[j] == view) {
                            if (game.makeATurn(j, 3)) doTurn = true;
                        }
                    }
                } else {//תור שחקן2
                    for (int i = 0; i < cardsP2.length; i++) {
                        if (cardsP2[i] == view) {
                            if (game.makeATurn(i, 2)) doTurn = true;
                        }
                    }

                    for (int j = 0; j < cardsP1.length; j++) {
                        if (cardsP1[j] == view) {
                            if (game.makeATurn(j, 3)) doTurn = true;
                        }
                    }
                }
            }
            else {
                Toast.makeText(this, "Take card from deck", Toast.LENGTH_LONG).show();
            }
        }
        reloadTexture();//מרענן את הטקסטורה
        isWon();//האם בתור הזה מישהו נצח
        if (doTurn) {//האם התרחש פעולה בתור
            game.nextTurn();
            flipImageButton();//סובב את הקלפים
        }
    }

    //פעולה שעוברת אקטיביטי עם יש ניצחון ומעבירה את שם המנצח
    public void isWon() {
        //בודק ניצחון
        if (game.getPlayerNotTurn().getSiege().isEmpty()) {
            Toast.makeText(this, game.getPlayerTurn().getName() + " WON", Toast.LENGTH_LONG).show();
            String playerWon = game.getPlayerTurn().getName();
            String playerLose = game.getPlayerNotTurn().getName();
            Intent go = new Intent(this, WinActivity.class);
            go.putExtra("TIMER_END",false );
            go.putExtra("PLAYERWIN_NAME", playerWon);
            go.putExtra("PLAYERLOSE_NAME", playerLose);
            startActivity(go);
        }
    }

    //פעולה שמסובבת את הקלפים ב-180 מעלות לפי מי שתורו לנוחות
    private void flipImageButton() {
        int deg;
        if (isFlipped) {//אם כבר סובב בתור הקודם
            deg = 0;
        } else {
            deg = 180;// Rotate 180 degrees
        }

        //מסובב הכל
        deck.setRotation(deg);
        srufim.setRotation(deg);
        for (int i = 0; i < cardsP1.length; i++) {
            cardsP1[i].setRotation(deg);
        }
        for (int j = 0; j < cardsP2.length; j++) {
            cardsP2[j].setRotation(deg);
        }
        isFlipped = !isFlipped; // משנה את מצב ה-"אם סובב"
    }

    private void vibrateDevice() {
        // Check if the device supports vibration
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATION_DURATION, VibrationEffect.DEFAULT_AMPLITUDE));
        }
    }


    //פעולת טיימר שמשנה את הניראות של הטיימר במסך ופעולת בהתאם
    private void startCountdown() {
        countDownTimer = new CountDownTimer(20 * 60 * 1000, 1000) {//יוצר טיימר של 20 דקות
            @Override
            public void onTick(long millisUntilFinished) {//כל טיק
                long minutes = millisUntilFinished / 1000 / 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                time.setText(String.format("%02d:%02d", minutes, seconds));//משנה את הניראות של הטיימר

                if(minutes<1){//אם נשאר דקה
                    Animation vibratAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.vibrat_animation);
                    time.startAnimation(vibratAnimation);
                    vibrateDevice();
                }
            }

            @Override
            public void onFinish() {//אם הזמן נגמר
                time.setText("00:00");
                Intent go = new Intent(GameActivity.this, WinActivity.class);
                go.putExtra("TIMER_END",true );
                startActivity(go);//עבור למסך התוצאות
            }
        };
        countDownTimer.start();
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


    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.mServ.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.mServ != null) {
            MainActivity.mServ.resumeMusic();
        }
    }
}
