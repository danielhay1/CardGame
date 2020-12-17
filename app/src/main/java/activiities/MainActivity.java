package activiities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.cardgame.R;

import untils.MyLocationServices;
import untils.Sound;
import untils.MySignal;

public class MainActivity extends AppCompatActivity {
    /*
/0. Create button
/1. Create label
/2. Give ids to views
/3. connect them to activity
/4. Listener
/5. Create function
/6. increment counter
     */

    //Boolean autoFold - if true, on gameActivity press on play btn will fold card automatically
    private ImageView main_BTN_start;
    private Button main_BTN_scoreboard;
    private EditText main_EDTTXT_player1;
    private EditText main_EDTTXT_player2;
    //main_SWITCH_autoFold - if true, on gameActivity press on play btn will fold card automatically
    private Switch main_SWITCH_autoFold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find views
        this.findViews();
        this.getPermissionLocation();   //Ask for location permission
        if (!MyLocationServices.getInstance().isGpsEnabled()) {
            MySignal.getInstance().Toast("Gps conncetion is off.");
        }

        //create sound
        main_SWITCH_autoFold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //toggle click sound
                Sound.getInstance().playSound(Sound.KEYS.SWITCH);
                switchToast(main_SWITCH_autoFold);
            }
        });
        main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pttt", "START_BTN click");
                Log.d("EditText", main_EDTTXT_player1.getText().toString());
                Log.d("EditText", main_EDTTXT_player2.getText().toString());
                openGameActivity(MainActivity.this);
            }
        });

        main_BTN_scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pttt", "SCOREBOARD_BTN click");
                openScoreboardActivity(MainActivity.this);
            }
        });
    }

    private void findViews () {
        main_BTN_start = findViewById(R.id.main_BTN_start);
        main_BTN_scoreboard = findViewById(R.id.main_BTN_scoreboard);
        main_EDTTXT_player1 = findViewById(R.id.main_EDTTXT_player1);
        main_EDTTXT_player2 = findViewById(R.id.main_EDTTXT_player2);
        main_SWITCH_autoFold = findViewById(R.id.main_SWITCH_autoFold);
    }

    private void switchToast(Switch main_SWITCH_autoFold) {
        String status;
        if(main_SWITCH_autoFold.isChecked() == true)
            status = "activated";
        else
            status = "disabled";
        MySignal.getInstance().Toast("auto fold: "+status);
    }

    private void openGameActivity(Activity activity) {
        Intent myIntent = new Intent(activity,GameActivity.class);
        myIntent.putExtra("player1Name", main_EDTTXT_player1.getText().toString());
        myIntent.putExtra("player2Name", main_EDTTXT_player2.getText().toString());
        myIntent.putExtra("autoFold",main_SWITCH_autoFold.isChecked());
        startActivity(myIntent);
    }

    private void openScoreboardActivity(Activity activity) {
        Intent myIntent = new Intent(activity,ScoreboardActivity.class);
        startActivity(myIntent);
    }

    private void getPermissionLocation() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
    }

    @Override
    protected void onStart() {
        Log.d("pttt", "onStart");
        super.onStart();
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

    @Override
    protected void onPause() {
        Log.d("pttt", "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("pttt", "onResume");
        super.onResume();
    }
}

/*
View types:
BTN
LBL
EDT
LST
IMG
PRG
LAY
 */