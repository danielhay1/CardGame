package activiities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.cardgame.R;
import com.google.android.gms.maps.model.LatLng;

import objects.Card;
import objects.Fragment_CallBack;
import objects.Map_Fragment;
import objects.ScoreLst_Fragment;
import untils.ImgLoader;

public class ScoreboardActivity extends AppCompatActivity {

    //Layouys
    private FrameLayout scoreboard_LAY_score_lst;
    private FrameLayout scoreboard_LAY_map;
    //Fragments
    private Map_Fragment map_fragment;
    private ScoreLst_Fragment scoreLst_fragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
        findViews();

        //Init fragments
        scoreLst_fragment = new ScoreLst_Fragment();
        scoreLst_fragment.setFragment_callBack(fragment_callBack);
        map_fragment = new Map_Fragment();
        //Open fragments
        getSupportFragmentManager().beginTransaction().add(R.id.scoreboard_LAY_score_lst,scoreLst_fragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.scoreboard_LAY_map,map_fragment).commit();
    }

    private void findViews() {
        scoreboard_LAY_score_lst = findViewById(R.id.scoreboard_LAY_score_lst);
        scoreboard_LAY_map = findViewById(R.id.scoreboard_LAY_map);
    }

    private Fragment_CallBack fragment_callBack = new Fragment_CallBack() {
        @Override
        public void addMarkerToMap(double latitude, double longitude, String name) {
            map_fragment.addLatLng(latitude,longitude,name);
        }

        @Override
        public void setFocusLocation(LatLng latLng) {
            map_fragment.setFocus(latLng,15);
        }
    };
}