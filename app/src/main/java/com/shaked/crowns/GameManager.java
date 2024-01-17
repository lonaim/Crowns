package com.shaked.crowns;

import android.app.Activity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

public class GameManager {


    private Player[] players; //Array of players
    int kTurn; //index, Decides which player to turn
    private Deck gameDeck;//the main deck of the game
    private Deck burnDeck;// the burn deck
    private Card deckCard;// a card from the deck that the player will use
    private Activity activity;//the activity

    /*פעולה בונה*/
    public GameManager(Activity activity) {
        activity = activity;
        this.gameDeck = new Deck();
        this.gameDeck.shuffle();
        this.burnDeck = new Deck();
        this.deckCard = null;
        this.players = new Player[2];
        players[0] = new Player("Player 1", new Siege(new Card(13,"c"), new Card(12,"c"), gameDeck));
        players[1] = new Player("Player 2", new Siege(new Card(13,"h"), new Card(12,"h"), gameDeck));
        this.kTurn = 0;
    }

    public void resetGame(){
        this.gameDeck = new Deck();
        this.gameDeck.shuffle();
        this.burnDeck = new Deck();
        this.deckCard = null;
        this.players = new Player[2];
        players[0] = new Player(players[0].getName(), new Siege(new Card(12,"c"), new Card(11,"c"), gameDeck));
        players[1] = new Player(players[1].getName(), new Siege(new Card(12,"h"), new Card(11,"h"), gameDeck));
        this.kTurn = 0;
    }

    public String deckCardToString(){
        return this.deckCard.toString();
    }


    /*gets and sets*/
    public Player[] getPlayers() {
        return players;
    }


    public void setPlayers(Player[] players) {
        this.players = players;
    }


    public int getK() {
        return kTurn;
    }


    public void setK(int kTurn) {
        this.kTurn = kTurn;
    }


    public void setDeckCard(Card deckCard){
        this.deckCard = deckCard;
    }


    public Card getDeckCard(){
        return this.deckCard;
    }


    public Player getPlayerTurn() {
        return players[kTurn];
    }


    public Player getPlayerNotTurn() {
        if(kTurn == 0) return players[1];
        return players[0];
    }

    public Player getPlayer(int kTurn) {
        return players[kTurn];
    }


    public Deck getDeckGame(){
        return this.gameDeck;
    }

    //update the name of the players
    public void setPlayersName(String p1, String p2) {
        players[0].setName(p1);
        players[1].setName(p2);
    }

    //move the turn to the next player
    public void nextTurn() {
        if(this.kTurn == 0) this.kTurn = 1;
        else this.kTurn = 0;
    }

    public void pickCard(){
        if(gameDeck.getSize()>0)
            setDeckCard(gameDeck.removeCard());
    }

    //get a card and put it in the burn deck
    public void doBurn(Card burnCard) {
        burnDeck.addCard(burnCard);
    }


    //use - reset the deck after all burn
    //move all the cards from the burn deck to the game deck
    public void resetDeckAfterAllBurn() {
        for(int i=0;i<burnDeck.getSize();i++){
            gameDeck.addCard(burnDeck.removeCard());
        }
        gameDeck.shuffle();
    }

    /*turn action*/

    //return if the turn was done
    public boolean makeATurn(int i/*place of the card in the tower*/, int action/*which action is*/) {
        ArrayList<Tower> line;
        ArrayList<Tower> enemyLine;
        Card attackCard;

        if (deckCard != null && gameDeck.getSize() != 0) {
            if (i < 3) {/*line 1*/
                line = getPlayerTurn().getSiege().getLine1();
                enemyLine = getPlayerNotTurn().getSiege().getLine1();
                switch (action) {
                    case 2://fortify
                            if (line.get(i).getCard().equalsNum(deckCard.getNum())) {
                                if (!line.get(i).isFull()) {
                                line.get(i).addCard(deckCard);
                                deckCard = null;
                                return true;}
                            } else if (line.get(i).getCard().equalsNum(0) && deckCard.equalsNum(1)) {
                                line.get(i).getCard().setNum(1);
                                line.get(i).getCard().setShape(deckCard.getShape());
                                deckCard = null;
                                return true;
                        }
                    case 3:
                        //if (deckCard.getNum() - enemyLine.get(i).getCard().getNum() == 1 || (deckCard.equalsNum(1) && deckCard.getNum() - enemyLine.get(i).getCard().getNum() == -12)) {
                            doBurn(enemyLine.get(i).removeCard());
                            doBurn(deckCard);
                            deckCard = null;
                            if (enemyLine.get(i).getSize() == 0) {
                                enemyLine.get(i).addCard(new Card(0, ""));
                            }
                            return true;
                        //}
                }
            }/*end of line 1*/

            if (i >= 3 && i < 7) {/*line 2*/
                line = getPlayerTurn().getSiege().getLine2();
                enemyLine = getPlayerNotTurn().getSiege().getLine2();
                switch (action) {
                    case 2://לבצר
                        if (!line.get(i - 3).isFull()) {
                            if (line.get(i - 3).getCard().equalsNum(deckCard.getNum())) {
                                line.get(i - 3).addCard(deckCard);
                                deckCard = null;
                                return true;
                            } else if (line.get(i - 3).getCard().equalsNum(0) && deckCard.equalsNum(1)) {
                                line.get(i - 3).getCard().setNum(1);
                                line.get(i - 3).getCard().setShape(deckCard.getShape());
                                deckCard = null;
                                return true;
                            }
                        }
                    case 3:
                        //if (deckCard.getNum() - enemyLine.get(i-3).getCard().getNum() == 1 || (deckCard.equalsNum(1) && deckCard.getNum() - enemyLine.get(i-3).getCard().getNum() == -12)) {
                            doBurn(enemyLine.get(i-3).removeCard());
                            doBurn(deckCard);
                            deckCard = null;
                            if (enemyLine.get(i-3).getSize() == 0) {
                                enemyLine.get(i-3).addCard(new Card(0, ""));
                            }
                            return true;
                        //}
                }
            }
            if (i >= 7 && i < 9) {/*line 3*/
                line = getPlayerTurn().getSiege().getQk();
                enemyLine = getPlayerNotTurn().getSiege().getQk();
                switch (action) {
                    case 2://fortify
                        return false;
                    case 3:
                        //if (deckCard.getNum() - enemyLine.get(i-7).getCard().getNum() == 1 || (deckCard.equalsNum(1) && deckCard.getNum() - enemyLine.get(i-7).getCard().getNum() == -12)) {
                            doBurn(enemyLine.get(i-7).removeCard());
                            doBurn(deckCard);
                            deckCard = null;
                            if (enemyLine.get(i-7).getSize() == 0) {
                                enemyLine.get(i-7).addCard(new Card(0, ""));
                            }
                            return true;
                        //}
                }//end of switch
            }/*end of line 3*/
        }
            return false;
        }



    public Card returnCard(int i,int player){
        ArrayList<Tower> line;
        Card card = new Card(0,"");
        switch (player) {
            case 0:
                if (i < 3) {/*line 1*/
                    line = players[0].getSiege().getLine1();
                    card = line.get(i).getCard();
                }/*end of line 1*/

                if (i >= 3 && i < 7) {/*line 2*/
                    line = players[0].getSiege().getLine2();
                    card = line.get(i - 3).getCard();
                }/*end of line 2*/

                if (i >= 7 && i < 9) {/*line 3*/
                    line = players[0].getSiege().getQk();
                    card = line.get(i - 7).getCard();
                }/*end of line 3*/
                break;
            case 1:
                if (i < 3) {/*line 1*/
                    line = players[1].getSiege().getLine1();
                    card = line.get(i).getCard();
                }/*end of line 1*/

                if (i >= 3 && i < 7) {/*line 2*/
                    line = players[1].getSiege().getLine2();
                    card = line.get(i - 3).getCard();
                }/*end of line 2*/

                if (i >= 7 && i < 9) {/*line 3*/
                    line = players[1].getSiege().getQk();
                    card = line.get(i - 7).getCard();
                }/*end of line 3*/
                break;
        }
        return (card);
    }

}
