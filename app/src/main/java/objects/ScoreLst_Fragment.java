package objects;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cardgame.R;
import com.google.android.gms.maps.model.LatLng;

import untils.MyPreference;

public class ScoreLst_Fragment extends Fragment {
    private final int MAX_ROWS = 10;
    private ListView fragmentScoreLst_LISTVIEW;
    //topScore
    private String names[] = new String[MAX_ROWS];
    private String dates[] = new String[MAX_ROWS];
    private String locations[] = new String[MAX_ROWS];
    private String scores[] = new String[MAX_ROWS];
    private int scoreCounter = 0;
    //Callback
    private Fragment_CallBack fragment_callBack;

    public void setFragment_callBack(Fragment_CallBack fragment_callBack) {
        this.fragment_callBack = fragment_callBack;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scorelst, container, false);
        findViews(view);
        this.initTopScores();
        CustomListView customListView = new CustomListView(this.getActivity(),names,dates,locations,scores);
        fragmentScoreLst_LISTVIEW.setAdapter(customListView);
        addAllMarkers();
        fragmentScoreLst_LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentScoreLst_LISTVIEW.setSelector(R.color.gray);
                this.itemBackgroundColorSet(getResources().getColor(R.color.gray),position);
                //setFocusOnItemLocation
                LatLng latLng = getItemLatlng(position);
                if(latLng != null) {
                    if (((latLng.latitude!=0)||(latLng.longitude!=0)))
                        sendLatLng(latLng);
                }
            }

            private void itemBackgroundColorSet(int color,int position) {
                for (int i = 0; i < fragmentScoreLst_LISTVIEW.getChildCount(); i++) {
                    if(position == i){
                        fragmentScoreLst_LISTVIEW.getChildAt(i).setBackgroundColor(color);;
                    }else{
                        fragmentScoreLst_LISTVIEW.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });
        return view;
    }

    public LatLng getItemLatlng(int index) {
        Log.d("pttt", "getItemLatlng: ");
        if(locations[index] != null) {
            String temp = locations[index].substring(1,locations[index].length()-1);
            String[] location = temp.split(",");
            double latitude = Double.parseDouble(location[0]);
            double longitude = Double.parseDouble(location[1]);
            return new LatLng(latitude,longitude);
        }
        else
            return null;
    }

    public void addAllMarkers() {
        Log.d("pttt", "Fragment Scorelist: addAllMarkers: (NumberOfElements = "+locations.length+")");
        for (int i = 0; i <scoreCounter ; i++) {
            LatLng latLng = this.getItemLatlng(i);
            String name = this.names[i];
            if ((latLng.latitude != 0) || (latLng.longitude != 0)) {
                addMarker(latLng, name);
            }
        }
    }

    public void addMarker(LatLng latLng, String name) {
        if (fragment_callBack != null) {
            fragment_callBack.addMarkerToMap(latLng.latitude, latLng.longitude ,name);
        }
    }

    public void sendLatLng(LatLng latLng) {
        if (fragment_callBack != null) {
            fragment_callBack.setFocusLocation(latLng);
        }
    }

    private TopScores loadTopScores(String key) {
        return MyPreference.getInstance().getTopScore(key);
    }

    private void findViews(View view) {
        fragmentScoreLst_LISTVIEW = view.findViewById(R.id.fragmentScoreLst_LISTVIEW);
    }

    private void initTopScores() {
        TopScores topScores = loadTopScores(MyPreference.KEYS.TOP_SCORES_ARRAY);
        if(topScores!=null) {
            Log.d("pttt", "loadScoreBoard: " + topScores);
            int size = topScores.getSize();
            Log.d("pttt", "loadScoreBoard:\tscore board size = "+size);
            for (int i = 0; i < size; i++) {
                this.names[i] = topScores.getRecords()[i].getName();
                this.dates[i] = topScores.getRecords()[i].getDate();
                this.locations[i] = "("+topScores.getRecords()[i].getLatitude()+","+topScores.getRecords()[i].getLongitude()+")";
                this.scores[i] = ""+topScores.getRecords()[i].getScore();
                scoreCounter++;
            }
        }

    }
}
