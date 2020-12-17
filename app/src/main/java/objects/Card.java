package objects;

import android.util.Log;

public class Card {
    private final int MAX_CARD_VALUE = 14;
    private int value;
    private String name;

    public Card() { }

    public Card(String name, int value) {
        this.name = name;
        try {
            this.setValue(value);
        } catch (CardValueException e) {
            e.getMessage();
            Log.d("pttt", ""+e.getMessage());
        }
    }

    private void setValue(int value) throws CardValueException {
        if(value > MAX_CARD_VALUE)
            throw new CardValueException("Illegal value inserted");
        else
            this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return this.name;
    }
}
