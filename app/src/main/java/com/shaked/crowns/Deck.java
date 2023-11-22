package com.shaked.crowns;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Deck {
    private LinkedList<Card> deck;//חפיסת קלפים

    /*פעולה בונה*/
    public Deck() {
        this.deck = new LinkedList<Card>();
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
                    case 1:this.deck.add(new Card(j,"c"));
                    break;
                    case 2: this.deck.add(new Card(j,"d"));
                    break;
                    case 3: this.deck.add(new Card(j,"h"));
                    break;
                    case 4: this.deck.add(new Card(j,"s"));
                    break;
                }
            }
            this.deck.add(new Card(12,"s"));
            this.deck.add(new Card(12,"s"));
            this.deck.add(new Card(13,"d"));
            this.deck.add(new Card(13,"d"));

        }
    }


    //מוסיפה קלף
    public void addCard(Card card){
        this.deck.add(card);
    }

    //מוציא קלף מהחפיסה
    public Card removeCard(){
        Card card = this.deck.remove();
        return card;
    }

    //מערבב את החפישה
    public void shuffle(){
        Card[] arr = new Card[48];
        Random rnd = new Random();

        //ממיר את התור למערך
        for(int i=0;i<arr.length;i++){
            arr[i] = this.deck.remove();
        }

        //מערבב את המערך, עם משתנה מילו
        for (int i=0;i<arr.length;i++){
            int randomPOs = rnd.nextInt(arr.length);
            Card temp = arr[i];
            arr[i] = arr[randomPOs];
            arr[randomPOs] = temp;
        }

        //מחזיר את הקלפים לתור
        for(int i=0;i<arr.length;i++){
            this.deck.add(arr[i]);
        }
    }

    @Override
    public String toString(){
        String str="{"+'\n';
        for (int i =0;i<48;i++){
            Card temp = this.deck.remove();
            str+=temp.toString()+'\n';
            this.deck.add(temp);
        }
        return str;
    }
}
