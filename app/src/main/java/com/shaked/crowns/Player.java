package com.shaked.crowns;

public class Player {

    private String name; //name of player
    private Siege siege; //The siege of player

    /**
     * Instantiates a new Player.
     *
     * @param name the name
     * @param s    the s
     */
    public Player(String name, Siege s) {
        this.name = name;
        this.siege = s;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
//gets and sets
    public String getName() {
        return this.name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets siege.
     *
     * @return the siege
     */
    public Siege getSiege() {
        return siege;
    }

    /**
     * Sets siege.
     *
     * @param siege the siege
     */
    public void setSiege(Siege siege) {
        this.siege = siege;
    }


    /**
     * Is final round boolean.
     *
     * @return the boolean
     */
//פעולה שמחזירה אמת אם המלך והמלכה חשופי שקר אחרת
    public boolean isFinalRound() {
        return this.siege.isLine1Empty() && this.siege.isLine2Empty();
    }

    /*To String*/
    @Override
    public String toString() {
        return this.name;

    }
}
