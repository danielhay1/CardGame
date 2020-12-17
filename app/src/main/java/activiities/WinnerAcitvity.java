package activiities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cardgame.R;

import untils.ImgLoader;
import untils.Sound;



public class WinnerAcitvity extends AppCompatActivity {

    private TextView winner_LBL_winnerPlayer;
    private TextView winner_LBL_score;
    private Button winner_BTN_playAgain;
    private Button winner_BTN_scoreboard;
    private ImageView winner_IMG_background;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);
        //Winner sound
        Sound.getInstance().playSound(Sound.KEYS.WINNER);
        //Find views
        findViews();
        //Load background
        loadBackground(winner_IMG_background);
        //Receive winner names and score from previous activity
        Intent myIntent = getIntent();
        String winnerName = myIntent.getStringExtra("winner");
        String winnerScore = myIntent.getStringExtra("score");
        Log.d("pttt", "The winner is: "+winnerName+"\tscore: "+winnerScore);
        setLblWinner(winnerName,winner_LBL_winnerPlayer);
        setLblScore(winnerScore,winner_LBL_score);

        //BTN actions
        winner_BTN_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sound.getInstance().stopSound();
                playAgain(WinnerAcitvity.this);
                finish();
            }
        });
        winner_BTN_scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sound.getInstance().stopSound();
                openScoreboardActivity(WinnerAcitvity.this);
                finish();
            }
        });
    }
    private void findViews() {
        this.winner_LBL_winnerPlayer = (TextView) findViewById(R.id.winner_LBL_winnerPlayer);
        this.winner_LBL_score = (TextView) findViewById(R.id.winner_LBL_score);
        this.winner_BTN_playAgain = (Button) findViewById(R.id.winner_BTN_playAgain);
        this.winner_BTN_scoreboard = (Button) findViewById(R.id.winner_BTN_scoreboard);
        this.winner_IMG_background = (ImageView) findViewById(R.id.winner_IMG_background);
    }

    private void loadBackground(ImageView background) {
        /**
         * Method load background image using glide.
         */
        String name = ImgLoader.KEYS.WINNER_ACTIVITY_BACKGROUND;
        ImgLoader.getInstance().loadImgByGlide(name,background);
    }

    private void playAgain(Activity activity) {
        Intent myIntent = new Intent(activity,MainActivity.class);
        startActivity(myIntent);
        this.finish();
    }

    private void openScoreboardActivity(Activity activity) {
        Intent myIntent = new Intent(activity,ScoreboardActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void setLblWinner(String winner, TextView score_LBL_winnerPlayer) {
        if(winner.trim().equalsIgnoreCase(""))
            score_LBL_winnerPlayer.setText("Tie!");
        else
            score_LBL_winnerPlayer.setText("The winner is: "+winner+"!");
    }

    private void setLblScore(String score, TextView score_LBL_score) {
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