package activiities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cardgame.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import objects.Card;
import objects.Game;
import objects.Player;
import untils.ImgLoader;
import untils.MyPreference;
import untils.Sound;


public class GameActivity extends AppCompatActivity {

    //Auto fold timer
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private final int PACKSIZE = 52;
    private ScheduledFuture scheduledFuture;

    //Game objects
    private Game game;
    private Boolean isTimerCreated = false; // use only for auto fold mode, prevent from creating multiplay autofold timers.

    //UI objects
    private ImageView game_IMG_player1Card;
    private ImageView game_IMG_player2Card;
    private ImageView game_IMG_play;
    private TextView game_LBL_player1Name;
    private TextView game_LBL_player2Name;
    private TextView game_LBL_player1Score;
    private TextView game_LBL_player2Score;
    private ProgressBar game_PROGRESS;
    private int progressCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Log.d("pttt", "onCreate");
        //Receive player names from previous activity
        Intent myIntent = getIntent();
        String player1Name = myIntent.getStringExtra("player1Name");
        String player2Name = myIntent.getStringExtra("player2Name");
        //Define play_BTN activity, if true play_BTN will fold card automatically.
        Boolean autoFold = myIntent.getBooleanExtra("autoFold",false);
        //Check if name is not inserted
        if(player1Name.trim().equalsIgnoreCase(""))
            player1Name = "player1";
        if(player2Name.trim().equalsIgnoreCase(""))
            player2Name = "player2";
        this.findViews();           //find views
        this.game = new Game(player1Name,player2Name);          //Init game
        //init UI:
        //init players TextViews
        game_LBL_player1Name.setText(""+this.game.getPlayer1().getPlayerName());
        game_LBL_player2Name.setText(""+this.game.getPlayer2().getPlayerName());
        initProgress();     //initProgress
        Sound.getInstance().playSound(Sound.KEYS.SHUFFLE);          //Shuffle sound

        //Start click
        game_IMG_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pttt", "Start_BTN clicked!\tautoFold = " + autoFold +" isTimerCreated = "+isTimerCreated);
                if (autoFold) {
                    if(!isTimerCreated){
                        //change icon to timer icon
                        int imgId = getResources().getIdentifier("img_loading", "drawable", getPackageName());
                        game_IMG_play.setImageResource(imgId);
                        //autoFold running round by timer
                        autoFoldStart(game_IMG_player1Card,game_IMG_player2Card,game_LBL_player1Score,game_LBL_player2Score);
                        //allow only one time timer generate
                        isTimerCreated = true;
                    }
                }
                else
                    round(game_IMG_player1Card,game_IMG_player2Card,game_LBL_player1Score,game_LBL_player2Score);
            }
        });
    }

    private void findViews() {
        this.game_LBL_player1Name = (TextView) findViewById(R.id.game_LBL_player1Name);
        this.game_LBL_player2Name = (TextView) findViewById(R.id.game_LBL_player2Name);
        this.game_IMG_play = (ImageView)findViewById(R.id.game_IMG_play);
        this.game_LBL_player1Score = (TextView) findViewById(R.id.game_LBL_player1Score);
        this.game_LBL_player2Score = (TextView) findViewById(R.id.game_LBL_player2Score);
        this.game_IMG_player1Card = (ImageView)findViewById(R.id.game_IMG_player1Card);
        this.game_IMG_player2Card = (ImageView)findViewById(R.id.game_IMG_player2Card);
        game_PROGRESS = (ProgressBar) findViewById(R.id.game_PROGRESS);
    }

    public void initProgress() {
        game_PROGRESS.setVisibility(ProgressBar.VISIBLE);
        game_PROGRESS.setProgress(this.progressCounter);
        game_PROGRESS.setMax(PACKSIZE/2);

    }

    private void progressStep() {
        this.progressCounter++;
        game_PROGRESS.setProgress(progressCounter);
    }

    private void round(ImageView game_IMG_player1Card,ImageView game_IMG_player2Card,
                      TextView game_LBL_player1Score,TextView game_LBL_player2Score) {
        if(!game.gameOver())
        {
            Sound.getInstance().playSound(Sound.KEYS.CLICK);
            //Both players pop cards
            Card player1Card = this.game.playerNextCard(game.getPlayer1());
            Card player2Card = this.game.playerNextCard(game.getPlayer2());
            String roundWinner;
            //Update cards imgViews
            this.updateCardImgByGlide(player1Card,game_IMG_player1Card);
            this.updateCardImgByGlide(player2Card,game_IMG_player2Card);
            //Update the score according the winner of the round
            roundWinner = game.roundWinner(player1Card,player2Card);
            this.updateScoreLbl(roundWinner,game_LBL_player1Score,game_LBL_player2Score);
            progressStep();
        }
        else {
            //Calc the winner of the game, add the winner details to intent, and open next activity.
            Player p;
            String winnerName;
            int winnerScore;
            p = this.game.clacWinner();
            if(p == null){  //Tie
                winnerName="";
                winnerScore=this.game.getPlayer1().getScore();
            }
            else{
                winnerName=p.getPlayerName();
                winnerScore=p.getScore();
            }
            //Save record - if round was not tied
            if(!winnerName.equalsIgnoreCase(""))
                this.game.saveRecord(winnerName,winnerScore);

            Intent myIntent = new Intent(GameActivity.this, WinnerAcitvity.class);
            myIntent.putExtra("winner",winnerName);
            myIntent.putExtra("score", ""+winnerScore);
            if (isTimerCreated)
                this.scheduledFuture.cancel(false);
            startActivity(myIntent);
            finish();
        }
    }

    private void updateCardImgByGlide(Card card, ImageView img) {
        /*
         * Use to load cardImg using glide
         * */
        ImgLoader.getInstance().loadImgByGlide(card.getName(),img);
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

    private void autoFoldStart(ImageView game_IMG_player1Card, ImageView game_IMG_player2Card,
                          TextView game_LBL_player1Score, TextView game_LBL_player2Score) {
        this.scheduledFuture = executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("pttt", "----------------------AutoFoldRound!----------------------");
                        round(game_IMG_player1Card,game_IMG_player2Card,game_LBL_player1Score,game_LBL_player2Score);
                    }
                });
            }
        }, 0L, 2000, TimeUnit.MILLISECONDS);
    }

    private void autoFoldStop() {
        this.scheduledFuture.cancel(false);
    }

    @Override
    protected void onStart() {
        Log.d("pttt", "onStart");
        super.onStart();
        if(isTimerCreated)
            autoFoldStart(this.game_IMG_player1Card,this.game_IMG_player2Card,this.game_LBL_player1Score,this.game_LBL_player2Score);
    }

    @Override
    protected void onResume() {
        Log.d("pttt", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("pttt", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("pttt", "onStop");
        super.onStop();
        if(isTimerCreated)
            this.autoFoldStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("pttt", "onDestroy");
        super.onDestroy();
    }

}