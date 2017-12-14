package comkimhyeockjin.github.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

public class LocationDialog extends Activity {
    EditText editLocation;
    EditText editPerson;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_dialog);
        this.setTitle("뭐해여?");

        editLocation = (EditText) findViewById(R.id.editText1);
        editPerson = (EditText) findViewById(R.id.editText2);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }


    public void addLocation(View v) {
        String locationName = editLocation.getText().toString();
        String personName = editPerson.getText().toString();
        int score = (int)ratingBar.getRating();
        // TODO DB에 추가.
    }


    public void exit(View v) {
        finish();
    }
}
