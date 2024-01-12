package com.shaked.crowns;

public class War {
    private String p1Name;
    private String p2Name;
    private int p1Scor;
    private int p2Scor;

    public War(String p1Name, String p2Name, int p1Scor, int p2Scor) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.p1Scor = p1Scor;
        this.p2Scor = p2Scor;
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

    public boolean isSameWar(War war) {
        return p1Scor == war.p1Scor && p2Scor == war.p2Scor && p1Name.equals(war.p1Name) && p2Name.equals(war.p2Name);
    }

    @Override
    public String toString() {
        return "War{" + p1Name + " - " + p1Scor+ '\'' + p2Name + " - " + p2Scor + '\'' + '}';
    }
}
