package com.shaked.crowns;

public class Card {
    private int num;//מספר הקלף
    private String shape;//צורת הקלף

    /*פעולה בונה*/
    public Card(int num, String shape) {
        this.num = num;
        this.shape = shape;
    }

    /*gets and sets*/
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    /*פעולות השוואה*/

    public boolean equalsCard(Card card){
        return (card.getNum() == this.num && card.getShape()==this.shape);
    }
    public boolean equalsNum(int num) {
        return num == this.num;
    }

    /*To String*/

    @Override
    public String toString() {
        String str="";
        str+="["+this.num+", "+this.shape.toString()+"]";
        return str;
    }
}
