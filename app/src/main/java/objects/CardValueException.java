package objects;

import android.util.Log;

public class CardValueException extends Exception {
    public CardValueException() { }
    public CardValueException(String msg) {
        Log.d("pttt", ""+ msg);
    }
}
