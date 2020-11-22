package com.example.cardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

    private ImageView main_BTN_start;
    private EditText main_EDTTXT_player1;
    private EditText main_EDTTXT_player2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find views
        main_BTN_start = findViewById(R.id.main_BTN_start);
        main_EDTTXT_player1 = findViewById(R.id.main_EDTTXT_player1);
        main_EDTTXT_player2 = findViewById(R.id.main_EDTTXT_player2);

        main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pttt", "START_BTN click");
                Log.d("EditText", main_EDTTXT_player1.getText().toString());
                Log.d("EditText", main_EDTTXT_player2.getText().toString());
                openGameActivity(MainActivity.this);
            }
        });

    }

    private void openGameActivity(Activity activity) {
        Intent myIntent = new Intent(activity,GameActivity.class);
        myIntent.putExtra("player1Name", main_EDTTXT_player1.getText().toString());
        myIntent.putExtra("player2Name", main_EDTTXT_player2.getText().toString());
        startActivity(myIntent);
        finish();
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