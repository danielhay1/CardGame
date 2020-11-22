package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class GameActivity extends AppCompatActivity {
    //Constants
    private final int PACK_SIZE = 52;
    private final int MAX_CARD_VALUE = 14;
    private final int CARD_SHAPES_AMOUNT = 4;

    //Game objects
    private ArrayList<Card> cardPack;
    private Game game;

    //UI objects
    private ImageView game_IMG_player1Card;
    private ImageView game_IMG_player2Card;
    private ImageView game_IMG_play;
    private TextView game_LBL_player1Name;
    private TextView game_LBL_player2Name;
    private TextView game_LBL_player1Score;
    private TextView game_LBL_player2Score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Receive player names from previous activity
        Intent myIntent = getIntent();
        String player1Name = myIntent.getStringExtra("player1Name");
        String player2Name = myIntent.getStringExtra("player2Name");
        //Check if name is not inserted
        if(player1Name.trim().equalsIgnoreCase(""))
            player1Name = "player1";
        if(player2Name.trim().equalsIgnoreCase(""))
            player2Name = "player2";

        //find views
        TextView game_LBL_player1Name = (TextView) findViewById(R.id.game_LBL_player1Name);
        TextView game_LBL_player2Name = (TextView) findViewById(R.id.game_LBL_player2Name);
        TextView game_LBL_player1Score = (TextView) findViewById(R.id.game_LBL_player1Score);
        TextView game_LBL_player2Score = (TextView) findViewById(R.id.game_LBL_player2Score);
        ImageView game_IMG_player1Card = (ImageView)findViewById(R.id.game_IMG_player1Card);
        ImageView game_IMG_player2Card = (ImageView)findViewById(R.id.game_IMG_player2Card);
        ImageView game_IMG_play = (ImageView)findViewById(R.id.game_IMG_play);

        //Init game
        this.game = new Game(this.getApplicationContext(),player1Name,player2Name);
        //init UI:
        //init players TextViews
        game_LBL_player1Name.setText(""+this.game.getPlayer1().getPlayerName());
        game_LBL_player2Name.setText(""+this.game.getPlayer2().getPlayerName());

        //Start click
        game_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                round(game_IMG_player1Card,game_IMG_player2Card,game_LBL_player1Score,game_LBL_player2Score);
            }
        });
    }

    private void round(ImageView game_IMG_player1Card,ImageView game_IMG_player2Card,
                      TextView game_LBL_player1Score,TextView game_LBL_player2Score) {
        if(!game.gameOver())
        {
            //Both players pop cards
            Card player1Card = this.game.playerNextCard(game.getPlayer1());
            Card player2Card = this.game.playerNextCard(game.getPlayer2());
            String roundWinner;
            //Update cards imgViews
            //game_IMG_player1Card.setImageResource(player1Card.getImgId());
            //game_IMG_player2Card.setImageResource(player2Card.getImgId());
            this.updateCardImg(player1Card,game_IMG_player1Card);
            this.updateCardImg(player2Card,game_IMG_player2Card);
            //Update the score according the winner of the round
            roundWinner = game.roundWinner(player1Card,player2Card);
            this.updateScoreLbl(roundWinner,game_LBL_player1Score,game_LBL_player2Score);
        }
        else {
            //Calc the winner of the game, add the winner details to intent, and open next activity.
            Player p;
            String winnerName,score;
            p = this.game.clacWinner();
            if(p == null){  //Tie
                winnerName="";
                score=""+this.game.getPlayer1().getScore();
            }
            else{
                winnerName=p.getPlayerName();
                score=""+p.getScore();
            }
            Intent myIntent = new Intent(GameActivity.this,ScoreBoardAcitvity.class);
            myIntent.putExtra("winner",winnerName);
            myIntent.putExtra("score", score);
            startActivity(myIntent);
            finish();
        }
    }

    private void updateCardImg(Card card, ImageView img) {
        img.setImageResource(card.getImgId());
    }

    private void updateScoreLbl(String winner,TextView game_LBL_player1Score,TextView game_LBL_player2Score) {
        //case1: player1 won the round
         if(this.game.getPlayer1().getPlayerName().equalsIgnoreCase(winner))
             game_LBL_player1Score.setText(""+this.game.getPlayer1().getScore());
         //case2: player2 won the round
         else if(this.game.getPlayer2().getPlayerName().equalsIgnoreCase(winner))
             game_LBL_player2Score.setText(""+this.game.getPlayer2().getScore());
         //case3: no player won the round (tie)
         else {
             game_LBL_player1Score.setText(""+this.game.getPlayer1().getScore());
             game_LBL_player2Score.setText(""+this.game.getPlayer2().getScore());
         }
    }

    @Override
    protected void onResume() {
        Log.d("pttt", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        //save state
        Log.d("pttt", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("pttt", "onStop");
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        Log.d("pttt", "onDestroy");
        super.onDestroy();
    }
}