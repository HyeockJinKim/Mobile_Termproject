package comkimhyeockjin.github.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class ServiceAsk extends Activity {

    PlaceDB placeDB;
    LocationDB locationDB;
    EditText placeName;
    EditText cate;
    EditText friend;
    RatingBar star;
    EditText memo;
    double lat;
    double lng;
    String date;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_ask);

        this.setTitle("방금 있던 장소는?");
        placeDB = new PlaceDB(this);
        locationDB = new LocationDB(this);

        placeName = (EditText) findViewById(R.id.placeName);
        cate = (EditText) findViewById(R.id.cate);
        friend = (EditText) findViewById(R.id.friend);
        star = (RatingBar) findViewById(R.id.star);
        memo = (EditText) findViewById(R.id.memo);
        star.setNumStars(5);
        star.setRating(0.5f);

        try {

            Intent intent = getIntent();
            lat = intent.getExtras().getDouble("lat");
            lng = intent.getExtras().getDouble("lng");
            time = intent.getExtras().getInt("time");
            placeName.setText(intent.getExtras().getString("name"));
            cate.setText(intent.getExtras().getString("cate"));
            date = intent.getExtras().getString("date");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void clickOk(View view){
        locationDB.insertInfo(date, lng, lat, friend.getText().toString(), time);
        placeDB.insertInfo(lng, lat, time, (int) (star.getRating() * 2),
                placeName.getText().toString(), memo.getText().toString(), cate.getText().toString());

        finish();
    }
}
