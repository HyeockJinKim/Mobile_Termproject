package comkimhyeockjin.github.termproject;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class RecommendDialog extends Activity {
    public static final int RECOMMEND_REQUEST = 1;
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
        Log.d("TimePicker", (timeBtn==null)?"null":timeBtn.getText().toString());
        timeBtn.setText(hour+":"+min);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //TODO 스피너 클릭리스너 구현. https://developer.android.com/guide/topics/ui/controls/spinner.html?hl=ko#SelectListener 참고.
    }


    public void exit(View v) {
        finish();
    }


    public void startRecommendActivity(View v) {
        finish();

        Intent intent = new Intent(getApplicationContext(), RecommendActivity.class);
        // personName, time은 dialog에서 입력받은 값으로...
        String personName = currentPersonName;
        String time = currentTime;
        intent.putExtra("personName", personName);
        intent.putExtra("time", time);
        startActivityForResult(intent, RECOMMEND_REQUEST);
    }

    public void timeBtnClick(View v) {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker v, int hh, int mm) {
                //버튼에 설정한 시간 보여주기.
                hour = hh; min = mm;
                timeBtn.setText(hour+":"+min);
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(this,listener, hour, min, false);
        dialog.show();
    }
}
