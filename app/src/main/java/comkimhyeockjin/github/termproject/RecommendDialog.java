package comkimhyeockjin.github.termproject;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

public class RecommendDialog extends Activity {
    int hour, min;
    Button timeBtn;

    String[] spinnerItems = {"혼자", "사람1", "사람2", "사람3"};
    String currentPersonName;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_dialog);
        this.setTitle("어디로 갈까?");

        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        timeBtn = (Button) findViewById(R.id.timeBtn);
        timeBtn.setText(hour+":"+min);
        currentTime = hour+":"+min;

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                Log.d("스피너 클릭리스너", adapterView.getSelectedItem().toString());
                currentPersonName = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void exit(View v) {
        finish();
    }


    public void startRecommendActivity(View v) {
        finish();

        Intent intent = new Intent();
        // personName, time은 dialog에서 입력받은 값으로...
        String personName = currentPersonName;
        String time = currentTime;
        intent.putExtra("personName", personName);
        intent.putExtra("time", time);
        setResult(RESULT_OK, intent);
    }

    public void timeBtnClick(View v) {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker v, int hh, int mm) {
                //버튼에 설정한 시간 보여주기.
                hour = hh; min = mm;
                timeBtn.setText(hour+":"+min);
                currentTime = hh+":"+mm;
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(this,listener, hour, min, false);
        dialog.show();
    }
}
