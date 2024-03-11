package com.shaked.crowns;

import java.util.ArrayList;

public class Siege {
    private ArrayList<Tower> qk;
    private ArrayList<Tower> line1;
    private ArrayList<Tower> line2;

    /*==פעולה בונה==*/
    public Siege(Card k, Card q, Deck deck) {
        qk = new ArrayList<Tower>();
        qk.add(new Tower(k));
        qk.add(new Tower(q));

        line1 = new ArrayList<Tower>();
        line2 = new ArrayList<Tower>();
        fillSiege(deck);
    }

    /*==gets and sets*/
    public ArrayList<Tower> getQk() {
        return qk;
    }

    public ArrayList<Tower> getLine1() {
        return line1;
    }

    public ArrayList<Tower> getLine2() {
        return line2;
    }


    /*==פעולות פרטיות==*/
    private void fillSiege(Deck deck) {
        Card c; //קלף מהחפיסה
        Tower[] temp = new Tower[7]; //חפיסה זמנית
        int place = 0;//מיקום הקלף הדומה
        int count = 0;//מה ההמקום האחרון שהוכנס

        //אתחול מערך זמין
        for (int i = 0; i < temp.length; i++) {
            temp[i] = new Tower(new Card(0,""));
        }

        //מילוי מערך זמין
        for (int i = 0; i < temp.length; i++) {
            c= deck.removeCard();
            if(exist(temp, c)) {//אם קיים קלף
                place = foundPlace(temp, c);
                if(temp[place].isFull()) {
                    temp[count] = new Tower(c);
                    count++;
                }
                else {
                    temp[place].addCard(c);
                }
            }else {//אם לא קיים קלף
                temp[count] = new Tower(c);
                count++;
            }
        }
        while (count < 5) {
            c= deck.removeCard();
            if(exist(temp, c)) {//אם קיים קלף
                place = foundPlace(temp, c);
                if(temp[place].isFull()) {
                    temp[count] = new Tower(c);
                    count++;
                }
                else {
                    temp[place].addCard(c);
                }
            }else {//אם לא קיים קלף
                temp[count] = new Tower(c);
                count++;
            }
        } 


        //למלות את השורות
        for (int i = 0; i < 4; i++) {
            line2.add(temp[i]);
        }
        for (int i = 0; i < 3; i++) {
            line1.add(temp[i+4]);
        }
    }

    //פעולה שמחזירה את מיקום התא של הקלף האחרון שצריך להכניס
    private int finalPlace(Tower[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].getCard().equalsNum(0)) return i;
        }
        return 0;
    }

    //פעולה שמחזירה אמת אם הקלף קיים במערך
    private boolean exist(Tower[] arr, Card c) {
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].getCard().equalsNum(c.getNum())) return true;
        }
        return false;
    }

    //פעולה שמחזירה את המיקום של הקלף שדומה לקלף המתקבל
    private int foundPlace(Tower[] arr, Card c) {
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].getCard().equalsNum(c.getNum())) return i;
        }
        return 0;
    }

    public boolean isLine1Empty() {
        for (int i = 0; i < this.line1.size(); i++) {
            if(!(this.line1.get(i).getCard().equalsNum(0))) return false;
        }
        return true;
    }

    public boolean isLine2Empty() {
        for (int i = 0; i < this.line2.size(); i++) {
            if(!(this.line2.get(i).getCard().equalsNum(0))) return false;
        }
        return true;
    }

    public boolean isLineQKEmpty() {
        for (int i = 0; i < this.qk.size(); i++) {
            if(!(this.qk.get(i).getCard().equalsNum(0))) return false;
        }
        return true;
    }

    public boolean isEmpty() {
        if(isLine1Empty() && isLine2Empty() && isLineQKEmpty()) return true;
        return false;
    }

    /*==TO STRING==*/
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String str = "";
        for (int i = 0; i < line1.size(); i++) {
            str += line1.get(i).toString();
        }
        str += "\n"; //יורד שורה
        for (int i = 0; i < line2.size(); i++) {
            str += line2.get(i).toString();
        }
        str += "\n";
        for (int i = 0; i < qk.size(); i++) {
            str += qk.get(i).toString();
        }
        return str;
    }

}
