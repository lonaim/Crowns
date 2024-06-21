package com.shaked.crowns;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Deck {
    private Stack<Card> deck;//חפיסת קלפים

    /*פעולה בונה*/
    public Deck() {
        this.deck = new Stack<Card>();
        fill();
    }

    /*gets the size of the deck*/
    public int getSize(){
        return this.deck.size();
    }

    /*פעולות*/

    public void fill(){
        //the shapes
        for(int i=1;i<=4;i++){
            //the numbers
            for (int j=1;j<=11;j++){
                switch (i){
                    case 1:this.deck.push(new Card(j,"c"));
                    break;
                    case 2: this.deck.push(new Card(j,"d"));
                    break;
                    case 3: this.deck.push(new Card(j,"h"));
                    break;
                    case 4: this.deck.push(new Card(j,"s"));
                    break;
                }
            }
        }
        this.deck.push(new Card(12,"s"));
        this.deck.push(new Card(12,"d"));
        this.deck.push(new Card(13,"s"));
        this.deck.push(new Card(13,"d"));
    }

    public void burnAll(){
        while(!this.deck.isEmpty()){
            this.removeCard();
        }
    }


    //מוסיפה קלף
    public void addCard(Card card){
        this.deck.push(card);
    }

    //מוציא קלף מהחפיסה
    public Card removeCard(){
        Card card = this.deck.pop();
        return card;
    }

    //מערבב את החפישה
    public void shuffle(){
        Card[] arr = new Card[deck.size()];
        Random rnd = new Random();

        //ממיר את המחסנית למערך
        for(int i=0;i<arr.length;i++){
            arr[i] = this.deck.pop();
        }

        //מערבב את המערך, עם משתנה מילו
        for (int i=0;i<arr.length;i++){
            int randomPOs = rnd.nextInt(arr.length);
            Card temp = arr[i];
            arr[i] = arr[randomPOs];
            arr[randomPOs] = temp;
        }

        //מחזיר את הקלפים למחסנית
        for(int i=0;i<arr.length;i++){
            this.deck.push(arr[i]);
        }
    }

    @Override
    public String toString(){
        String str="{";
        Stack<Card> tempS = new Stack<Card>();
        str+="deck - size: "+deck.size()+"\n";
        int deckSize= deck.size();
        for (int i =0;i<deckSize;i++){
            Card temp = this.deck.pop();
            tempS.push(temp);
            str+=temp.toString()+'\n';
        }
        for(int i=0;tempS.size()>0;i++){
            this.deck.push(tempS.pop());
        }
        str+="}";
        return str;
    }
}
