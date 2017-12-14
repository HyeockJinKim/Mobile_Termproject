package comkimhyeockjin.github.termproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationService extends Service {
    private PlaceDetectionClient mPlaceDetectionClient;
    private GoogleApiClient mGoogleApiClient = null;
    Location mLastKnownLocation = null;
    private int timer = 60000;
    private int placeTime = 0;
    private String lastDate = "";
    private double lat;
    private double lng;
    private Context mContext;
    private Handler handler;
    private LatLng DEFAULT_ZONE;

    private LocationDB locationDB;
    private PlaceDB placeDB;

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
            placeDB = new PlaceDB(this);
            checkLocation();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void checkLocation() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                checkCurrentPlace();
                handler.postDelayed(this, timer);
            }
        };

        handler.postDelayed(runnable, 0);
    }

    private void checkCurrentPlace() {
        @SuppressWarnings("MissingPermission") final Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);

        placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
            @Override
            public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    PlaceLikelihoodBufferResponse places = task.getResult();
                    Place currentPlace = places.get(0).getPlace();
                    if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mLastKnownLocation = LocationServices.FusedLocationApi
                            .getLastLocation(mGoogleApiClient);


                    double curLat = currentPlace.getLatLng().latitude;
                    double curLng = currentPlace.getLatLng().longitude;
                    String name;
                    if (Math.pow(mLastKnownLocation.getLatitude()-DEFAULT_ZONE.latitude,2)+Math.pow(mLastKnownLocation.getLongitude()-DEFAULT_ZONE.longitude,2) >
                            Math.pow(mLastKnownLocation.getLatitude()-curLat,2)+Math.pow(mLastKnownLocation.getLongitude()-curLng,2)) {
                        name = currentPlace.getName().toString();
                    } else {
                        curLat = DEFAULT_ZONE.latitude;
                        curLng = DEFAULT_ZONE.longitude;
                        name = "공대 5호관";
                    }
                    if (lat == curLat && lng == curLng) {
                        placeTime += timer;
                        if (timer < 900000) {
                            timer *= 2;
                        }

                        // DB 저장 필요.
                        // 창을 띄워서 별점같은 것을 받아야함.
                        if (placeDB.checkInfo(lat, lng)) {
                            for (PlaceInfo placeInfo : placeDB.getAllInfo()) {
                                if (placeInfo.getLat() == curLat && placeInfo.getLng() == curLng) {
                                    placeInfo.setTime(placeInfo.getTime() + (placeTime / 60000));
                                    placeDB.updateInfo(placeInfo);
                                    break;
                                }
                            }
                            LocationInfo locationInfo = locationDB.getAllInfo().get(0);
                            if (locationInfo.getLat() == curLat && locationInfo.getLng() == curLng) {
                                locationInfo.setTime(locationInfo.getTime() + (placeTime / 60000));
                                locationDB.updateInfo(locationInfo);
                            }
                        } else {
                            Intent intent = new Intent(mContext, ServiceAsk.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("time", (placeTime / 60000)); // 분 단위로 보내줌.
                            intent.putExtra("lat", curLat);
                            intent.putExtra("lng", curLng);
                            intent.putExtra("name", name);
                            switch (currentPlace.getPlaceTypes().get(0)) {

                            }
                            intent.putExtra("cate", currentPlace.getPlaceTypes().get(0));
                            intent.putExtra("date", lastDate);

                            startActivity(intent);
                        }

                        placeTime = 0;
                        return;
                    }
                    placeTime = 0;
                    if (timer > 120000) {
                        timer /= 4;
                    }
                    lat = curLat;
                    lng = curLng;
                    lastDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(System.currentTimeMillis()));

                    Log.d("MainActivity", "name : " + currentPlace.getName() + "long : " + currentPlace.getLatLng().longitude
                            + "lat : " + currentPlace.getLatLng().latitude);

                } else {
                    Log.d("LocationService", task.getException().toString());
                }


            }
        });

    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        handler = new Handler();
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
        DEFAULT_ZONE = new LatLng(36.3665928,127.3443893);
    }
}
