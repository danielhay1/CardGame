package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ScoreBoardAcitvity extends AppCompatActivity {

    private TextView score_LBL_winnerPlayer;
    private TextView score_LBL_score;
    private Button score_BTN_playAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        //find views
        TextView score_LBL_winnerPlayer = (TextView) findViewById(R.id.score_LBL_winnerPlayer);
        TextView score_LBL_score = (TextView) findViewById(R.id.score_LBL_score);
        Button score_BTN_playAgain = (Button) findViewById(R.id.score_BTN_playAgain);
        //Receive winner names and score from previous activity
        Intent myIntent = getIntent();
        String winner = myIntent.getStringExtra("winner");
        String score = myIntent.getStringExtra("score");
        Log.d("pttt", "The winner is: "+winner+"\tscore: "+score);
        setWinner(winner,score_LBL_winnerPlayer);
        setScore(score,score_LBL_score);

        score_BTN_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain(ScoreBoardAcitvity.this);
            }
        });
    }
    public void playAgain(Activity activity) {
        Intent myIntent = new Intent(activity,MainActivity.class);
        startActivity(myIntent);
        this.finish();
    }

    private void setWinner(String winner,TextView score_LBL_winnerPlayer) {
        if(winner.trim().equalsIgnoreCase(""))
            score_LBL_winnerPlayer.setText("Tie!");
        else
            score_LBL_winnerPlayer.setText("The winner is: "+winner+"!");
    }
    private void setScore(String score,TextView score_LBL_score) {
        score_LBL_score.setText("Score: "+score);
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