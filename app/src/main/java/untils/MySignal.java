package untils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class MySignal {
    private  static MySignal instance;
    private Context appContext;

    public static MySignal getInstance() {
        return instance;
    }

    private MySignal(Context context) {
        this.appContext = context.getApplicationContext();
    }

    public static void Init(Context appContext){
        if(instance == null) {
            Log.d("pttt", "Init: MySignal");
            instance = new MySignal(appContext);
        }
    }

    public void Toast(String msg) {
        Log.d("pttt", "toast: "+msg);
        Toast.makeText(appContext,msg,Toast.LENGTH_SHORT).show();

    }
}
