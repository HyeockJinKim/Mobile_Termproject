package comkimhyeockjin.github.termproject;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;

public class LocationService extends Service {
    Location mLastKnownLocation = null;
    private GoogleApiClient mGoogleApiClient = null;
    private int timer = 60000;
    private boolean isMove = true;
    private PlaceDetectionClient mPlaceDetectionClient;
    private double placeTime = 0;

    private LocationDB locationDB;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return Service.START_STICKY;
        } else {
            locationDB = new LocationDB(this);
            saveLocation();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void saveLocation() {
        while (true) {
            saveCurrentLocation();
            try {
                Thread.sleep(timer);
                placeTime += timer;
                switch (timer) {
                    case 60000:
                        timer = 300000;
                        break;
                    case 300000:
                        timer = 600000;
                        break;
                    case 600000:
                        timer = 1200000;
                        break;
                    default:
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location thisLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        if (!thisLocation.equals(mLastKnownLocation)){
            mLastKnownLocation = thisLocation;
            if (!isMove) {
                placeTime = 0;
                timer = 60000;
                isMove = true;
            }
        } else {
            if (timer == 1200000) {
                isMove = false;
            }
        }
    }



    private void saveCurrentPlaceName() {

        final double lng = mLastKnownLocation.getLongitude();
        final double lat = mLastKnownLocation.getLatitude();

        // Get the likely places - that is, the businesses and other points of interest that
        // are the best match for the device's current location.
        @SuppressWarnings("MissingPermission") final
        Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);

        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    double min = Double.MAX_VALUE;
                    PlaceLikelihood data = null;
                    PlaceLikelihoodBufferResponse places = task.getResult();

                    for (PlaceLikelihood placeLikelihood : places) {
                        // Build a list of likely places to show the user.

                        final double tempLating = placeLikelihood.getPlace().getLatLng().latitude;
                        final double tempLongitude = placeLikelihood.getPlace().getLatLng().longitude;
                        final double distance = Math.pow((lat - tempLating), 2) + Math.pow((lng - tempLongitude), 2);
                        if (min > distance) {
                            min = distance;
                            data = placeLikelihood;
                        }

                    }
                    if (mMapLocationManager.isMyLocationFixed() && data != null) {
                        saveMyLocation(data);
                        Date currentTime = new Date(System.currentTimeMillis());

                        locationDB.insertInfo(new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(currentTime),
                                lng, lat, placeTime, /*star*/5, "memo");

                    }
                } else {
                    Log.d("LocationService", task.getException().toString());
                }
            }
        });



    }


    @Override
    public void onCreate() {
        super.onCreate();

    }
}
