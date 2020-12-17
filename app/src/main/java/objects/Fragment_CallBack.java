package objects;

import com.google.android.gms.maps.model.LatLng;

public interface Fragment_CallBack {
    void addMarkerToMap(double latitude, double longitude,String name);
    void setFocusLocation(LatLng latLng);
}
