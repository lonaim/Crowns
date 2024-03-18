package com.shaked.crowns;

public class War {
    private String p1Name;//שם שחקן 1
    private String p2Name;//שם שחקן 2
    private int p1Scor;//נצחונות של שחקן 1
    private int p2Scor;//נצחונות של שחקן 2

    /*==פעולות בונות==*/
    public War(String p1Name, String p2Name,int p1Scor, int p2Scor) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.p1Scor = p1Scor;
        this.p2Scor = p2Scor;
    }

    //כשהמשתמשים לא קיימים
    public War(String p1Name, String p2Name) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.p1Scor = 0;
        this.p2Scor = 0;
    }

    public String getP1Name() {
        return p1Name;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    public int getP1Scor() {
        return p1Scor;
    }

    public void setP1Scor(int p1Scor) {
        this.p1Scor = p1Scor;
    }

    public int getP2Scor() {
        return p2Scor;
    }

    public void setP2Scor(int p2Scor) {
        this.p2Scor = p2Scor;
    }

    //אם המלחמה דומה
    public boolean isSameWar(War war) {
        return p1Name.equals(war.p1Name) && p2Name.equals(war.p2Name);
    }

    @Override
    public String toString() {
        return "War{" + p1Name + " - " + p1Scor+ '\'' + p2Name + " - " + p2Scor + '\'' + '}';
    }


}
