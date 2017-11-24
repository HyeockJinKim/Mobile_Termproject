package comkimhyeockjin.github.termproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.TreeMap;

/**
 * TODO Google Map API를 적용시켜야 함.
 * Google 지도 API가 들어갈 Activity.
 * 이 Activity에서 추천, 통계 를 볼 수 있다.
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    Context mContext = this;
    // 목록 정렬을 위해 만들었음.
    private TreeMap<Integer, String> freindMap = new TreeMap<>();
    public static final int RECOMMEND_REQUEST = 1;
    private static final int PERMISSION_LOCATION = 101;
    private static final LatLng DEFAULT_ZOOM = new LatLng(38.9, 124.5);
    private GoogleApiClient mGoogleApiClient = null;
    private GoogleMap googleMap = null;
    private Marker currentMarker = null;
    Location mLastKnownLocation = null;
    double lat;
    double lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionCheck();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        setButtonClickListener();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();
    }


    private void setButtonClickListener() {
        Button recommendBtn = (Button) findViewById(R.id.recommend);
        Button statisticBtn = (Button) findViewById(R.id.statistic);

        // TODO : askSituation에서 Intent까지 처리하게 하자!
        recommendBtn.setOnClickListener(new View.OnClickListener() {

            /**
             * TODO input="같이 간 사람, 추천받을 시간"을 받아 Activity 이동.
             */
            @Override
            public void onClick(View view) {
                askSituation();
                Intent intent = new Intent(mContext, RecommendActivity.class);
                String personName = "";
                String time = "";
                intent.putExtra("personName", personName);
                intent.putExtra("time", time);
                startActivityForResult(intent, RECOMMEND_REQUEST);
            }
        });

        statisticBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StatisticActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * TODO : 현재 위치로 이동시켜
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (mLastKnownLocation != null) {
            Log.d("MainActivity", "longitude =" + lng + ", latitude=" + lat);
//            LatLng myLocation = new LatLng(lat, lng);
//            this.googleMap.addMarker(new MarkerOptions().position(myLocation).title("Marker in my location"));
//            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
            setCurrentLocation(mLastKnownLocation, "나의 위치", "내 현재 위치");
        }
    }


    /**
     *
     */
    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        if ( currentMarker != null ) currentMarker.remove();

        if ( location != null) {
            //현재위치의 위도 경도 가져옴
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            currentMarker = this.googleMap.addMarker(markerOptions);

            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            return;
        } else {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(DEFAULT_ZOOM);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            currentMarker = this.googleMap.addMarker(markerOptions);

            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_ZOOM));
        }

    }

    /**
     * TODO 선택한 Item 위치에 표시
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RECOMMEND_REQUEST:
                if (resultCode == RESULT_OK) {

                }
                break;
        }
    }

    /**
     * TODO 추천 버튼 눌렀을 때에 다이얼로그 띄워서 물어보는 부분.
     */
    private void askSituation() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle("어디로 갈까?");
        dialog.setView(R.layout.ask_dialog);


    }

    private void permissionCheck() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mContext, "Location 권한 승인", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Location 권한 없음", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                return;
        }
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            finish();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Location location = new Location("");
        location.setLatitude(DEFAULT_ZOOM.latitude);
        location.setLongitude((DEFAULT_ZOOM.longitude));

        setCurrentLocation(location, "위치정보 가져올 수 없음",
                "위치 퍼미션과 GPS활성 여부 확인");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
