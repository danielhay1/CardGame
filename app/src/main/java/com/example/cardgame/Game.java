package com.example.cardgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    //Constants
    private final int PACK_SIZE = 52;
    private final int MAX_CARD_VALUE = 14;
    private final int CARD_SHAPES_AMOUNT = 4;

    //Game objects
    private ArrayList<Card> cardPack;
    private Player player1;
    private Player player2;

    public Game() {}

    public Game(Context gameActivitycontext, String player1Name, String player2Name) {
        //Init card pack
        this.cardPack = initCardPack(gameActivitycontext);
        this.shufflePack(this.cardPack);
        //init players
        this.initPlayers(player1Name,player2Name,this.cardPack);
        //Print players
        Log.d("pttt", "Player1Name:"+ player1.getPlayerName() + "\t player1Score: ("+player1.getScore()+")");
        Log.d("pttt", "Player2Name:"+ player2.getPlayerName() + "\t player1Score: ("+player1.getScore()+")");
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    private ArrayList<Card> initCardPack(Context gameActivitycontext) {
        ArrayList<Card> cardPack = new ArrayList<Card>();
        String imgPrefix = "img_card_";
        for (int value = 2; value <= MAX_CARD_VALUE; value++) {
            String imgLongerPrefix = imgPrefix + value;
            char shape = 'a';

            for (int i = 0; i < CARD_SHAPES_AMOUNT; i++) {
                String imgName = imgLongerPrefix+"_"+ shape;
                Log.d("pttt", "imgName: [\""+imgName+"\"]");
                int imgId = gameActivitycontext.getResources().getIdentifier(imgName, "drawable", gameActivitycontext.getPackageName());
                cardPack.add(new Card(imgId,value));
                Log.d("pttt", "----------------------");
                shape++;
            }
        }
        return cardPack;
    }

    private void shufflePack(List<Card>cardPack) {
        Collections.shuffle(cardPack);
    }

    private void initPlayers(String player1Name, String player2Name,List <Card> cardPack) {
        //Split the card pack between players.
        List<Card> player1Pack= cardPack.subList(0,(PACK_SIZE)/2);
        List<Card> player2Pack = cardPack.subList((PACK_SIZE)/2,PACK_SIZE);
        this.printPack(player1Pack);
        this.printPack(player2Pack);
        //Create players
        this.player1 = new Player(player1Name,player1Pack);
        this.player2 = new Player(player2Name,player2Pack);
    }

    public Card playerNextCard(Player player){
        return player.popCard();
    }

    public Player clacWinner() {
        /**
         * Method check which player won and return the player.
         */
        if (this.player1.getScore()>this.player2.getScore())
            return this.player1;
        else if(this.player1.getScore()==this.player2.getScore())
            return null;
        else
            return this.player2;
    }


    public String roundWinner(Card player1Card,Card player2Card) {
        /**
         * param Card,Card.
         * Function receive players card, function increase the winner score
         * Function returns winner player name.
         */
        if(player1Card.getValue() - player2Card.getValue() > 0) {
            this.player1.scoreIncrease();
            Log.d("pttt", "roundWinner: " +player1.getPlayerName()+ " Wins!\nPlayer1 score:["+player1.getScore()+"]"
                    +"\t Player2 score:["+player2.getScore()+"]");
            return this.player1.getPlayerName();
        }
        else if (player1Card.getValue() - player2Card.getValue() == 0) {
            this.player1.scoreIncrease();
            this.player2.scoreIncrease();
            Log.d("pttt", "roundWinner: Tie!\nPlayer1 score:["+player1.getScore()+"]"
                    +"\t Player2 score:["+player2.getScore()+"]");
            return "tie";
        }
        else{
            this.player2.scoreIncrease();
            Log.d("pttt", "roundWinner: " +player2.getPlayerName()+ " Wins!\nPlayer1 score:["+player1.getScore()+"]"
                    +"\t Player2 score:["+player2.getScore()+"]");
            return this.player2.getPlayerName();
        }
    }

    public boolean gameOver(){
        if((this.getPlayer1().packIsEmpty()) || (this.getPlayer2().packIsEmpty()))
            return true;
        else
            return false;
    }

    //Debug methods:
    private void printPack(List<Card> pack) {
        int i=0;
        for (Card card: pack) {
            i++;
            Log.d("pttt", "Card "+i+", value:"+card.getValue()+"\tid"+card.getImgId());
        }
    }
}
