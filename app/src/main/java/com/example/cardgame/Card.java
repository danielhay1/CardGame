package com.example.cardgame;

import android.util.Log;

public class Card {
    private final int MAX_CARD_VALUE = 14;
    private int value;
    private int imgId;

    public Card() { }

    public Card(int imgId, int value) {
        this.imgId = imgId;
        try {
            this.setValue(value);
        } catch (cardValueException e) {
            e.getMessage();
            Log.d("pttt", ""+e.getMessage());
        }
    }

    private void setValue(int value) throws cardValueException {
        if(value > MAX_CARD_VALUE)
            throw new cardValueException("Illegal value inserted");
        else
            this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getImgId() {
        return this.imgId;
    }
}
