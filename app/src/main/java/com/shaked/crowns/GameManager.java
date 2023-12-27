package com.shaked.crowns;

import android.app.Activity;

public class GameManager {


    private Player[] players; //Array of players
    int kTurn; //index, Decides which player to turn
    private Deck gameDeck;//the main deck of the game
    private Deck burnDeck;// the burn deck
    private Card deckCard;// a card from the deck
    private Activity activity;//the activity

    /*פעולה בונה*/
    public GameManager(Activity activity) {
        activity = activity;
        this.gameDeck = new Deck();
        this.gameDeck.shuffle();
        this.burnDeck = new Deck();
        this.deckCard = new Card(0, "");
        this.players = new Player[2];
        players[0] = new Player("Player 1", new Siege(new Card(12,"c"), new Card(11,"c"), gameDeck));
        players[1] = new Player("Player 2", new Siege(new Card(12,"h"), new Card(11,"h"), gameDeck));
        this.kTurn = 0;
    }

    public void resetGame(){
        this.gameDeck = new Deck();
        this.gameDeck.shuffle();
        this.burnDeck = new Deck();
        this.deckCard = new Card(0, "");
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

    public void setPlayersName(String p1, String p2) {
        players[0].setName(p1);
        players[1].setName(p2);
    }

    public void nextTurn() {
        if(this.kTurn == 0) this.kTurn = 1;
        else this.kTurn = 0;
    }

    public void doBurn() {
        burnDeck.addCard(deckCard);
    }

    public void resetDeckAfterAllBurn() {
        gameDeck = burnDeck.cloneDeck();
        burnDeck = new Deck();
        gameDeck.shuffle();
    }
}
