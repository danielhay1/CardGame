package untils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

public class MyLocationServices {
    private LocationManager locationManager;
    private Context appContext;
    private static MyLocationServices instance;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public interface CallBack_Location {
        void locationReady(Location location);
        void onError(String error);
    }


    public static MyLocationServices getInstance() {
        return instance;
    }

    private MyLocationServices(Context context) {
        this.appContext = context.getApplicationContext();
        this.locationManager = (LocationManager) appContext.getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if (!isGpsEnabled()) {
            //GPS is not enabled !!
            Log.d("pttt", "GPS is not enabled");
        }
    }

    public static void Init(Context appContext) {
        if (instance == null) {
            Log.d("pttt", "Init: LocationServices");
            instance = new MyLocationServices(appContext);
        }
    }

    public void setLastBestLocation(CallBack_Location callBack_location) {
        if (ActivityCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (callBack_location != null) {
                callBack_location.onError("LOCATION PERMISSION IS NOT ALLOW");
            }
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //Location found
                if (location != null) {
                    if (callBack_location != null) {
                        callBack_location.locationReady(location);
                    }
                } else {
                    if (callBack_location != null) {
                        callBack_location.onError("Location is null");
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (callBack_location != null) {
                    callBack_location.onError(e.getLocalizedMessage());
                }
            }
        });
    }

    public boolean isGpsEnabled() {
        return  this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
