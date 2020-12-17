package objects;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cardgame.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import untils.MyLocationServices;

public class Map_Fragment extends Fragment {
    private ArrayList<LatLng> latLngs = new ArrayList<>(); //define arraylist of latlngs
    private ArrayList<String> names = new ArrayList<>(); //define arraylist of names
    private GoogleMap googleMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_maps);
        Log.d("pttt", "Map_Fragment: latLngs=\n"+latLngs.toString());
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                //When map is loaded
                addMarkerToMap(map);
                googleMap = map;
                currentLocationFocus();
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    }
                });
            }
        });
        return view;
    }

    private void currentLocationFocus() {
        MyLocationServices.getInstance().setLastBestLocation(new MyLocationServices.CallBack_Location() {
            @Override
            public void locationReady(Location location) {
                LatLng currentLocation = new LatLng(location.getLatitude(),location.getLongitude());
                if(currentLocation.latitude !=0||currentLocation.longitude!=0)
                {
                    setFocus(currentLocation,5);
                }
            }
            @Override
            public void onError(String error) {
                Log.d("pttt", "onError: "+error);
            }
        });
    }

    public void setFocus(LatLng focusLatlng,float scale) {
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(focusLatlng);
        if(focusLatlng == null) {
            Log.d("pttt", "setFocus: NULL");
        }
        else {
            Log.d("pttt", "setFocus: \tLatlng= " + focusLatlng.toString());
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(scale);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }
    }

    public void addMarkerToMap(GoogleMap map) {
        int i=0;
        if(!latLngs.isEmpty()&&!names.isEmpty()&&map!=null) {
            for(LatLng item : latLngs){
                Log.d("pttt", "addMarkerToMap: ");
                map.addMarker(new MarkerOptions()
                        .position(new LatLng(item.latitude, item.longitude))
                        .title(this.names.get(i)));
                i++;
            }
        }
    }

    public void addLatLng(double latitude, double longitude,String name) {
        Log.d("pttt", "Map Fragment: addLatLng:\t("+latitude+","+longitude+")"+", Winner name ="+name);
        this.latLngs.add(new LatLng(latitude,longitude));
        this.names.add(name);
        Log.d("pttt", "addLatLng: "+latLngs.toString()+names.toString());
    }
}
