package com.shaked.crowns;

import java.util.Stack;

public class Tower {
    private Stack<Card> cards; //מחסנית של קלפים

    public Tower(Card card) {
        this.cards = new Stack<Card>();
        this.cards.push(card);
    }

    /*==gets and sets==*/
    public Stack<Card> getCards() {
        return cards;
    }

    public void setCards(Stack<Card> cards) {
        this.cards = cards;
    }

    //מחזירה את הקלף למעלה ולא מחסירה אותו
    public Card getCard() {
        return (Card) cards.peek();
    }

    public int getSize() {
        return this.cards.size();
    }

    /*==פעולות==*/
    //פעולה שמוסיפה קלף למגדל
    public void addCard(Card card) {
        if(!(isFull())) {
            this.cards.push(card);
        }
    }

    //פעולה שמחסירה קלף מהמגדל ומחזירה אותו
    public Card removeCard() {
        Card c = this.cards.pop();
        return c;
    }

    //פעולה שמחזירה אמת אם המגדל מלא שקר אחרת
    public boolean isFull() {
        return cards.size() == 3;
    }

    /*==To String==*/
    @Override
    public String toString() {
        Stack<Card> temp = new Stack<Card>();
        String str = "";
        if(!getCard().equalsNum(0)) {
            str += "[";
            while (!cards.isEmpty())
            {
                temp.push(cards.pop());
            }
            while (!temp.isEmpty())
            {
                str += temp.peek();
                cards.push(temp.pop());
            }
            str += "]";
        }
        return str;
    }

}
