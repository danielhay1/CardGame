package com.example.cardgame;

import android.util.Log;

public class cardValueException extends Exception {
    public cardValueException() { }
    public cardValueException(String msg) {
        Log.d("pttt", ""+ msg);
    }
}
