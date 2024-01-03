package com.shaked.crowns;

public class Player {



    private String name; //player name
    private Siege siege; //the player's siege

    public Player(String name, Siege s) {
        this.name = name;
        this.siege = s;
    }

    //gets and sets
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Siege getSiege() {
        return siege;
    }

    public void setSiege(Siege siege) {
        this.siege = siege;
    }

    public boolean isFinalRound(){
        return this.siege.isLine1Empty() && this.siege.isLine2Empty();
    }

    /*To String*/
    @Override
    public String toString() {
        return this.name;
    }
}
