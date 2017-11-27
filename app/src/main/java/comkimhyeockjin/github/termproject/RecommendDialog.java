package comkimhyeockjin.github.termproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class RecommendDialog extends Activity {
    public static final int RECOMMEND_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ask_dialog2);
        this.setTitle("어디로 갈까?");

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        Button timeBtn = (Button) findViewById(R.id.timeBtn);
        Log.d("TimePicker", (timeBtn==null)?"null":timeBtn.getText().toString());
        timeBtn.setText(hour+":"+min);

    }


    public void exit(View v) {
        finish();
    }


    public void startRecommendActivity(View v) {
        finish();

        Intent intent = new Intent(getApplicationContext(), RecommendActivity.class);
        // personName, time은 dialog에서 입력받은 값으로...
        String personName = "";
        String time = "";
        intent.putExtra("personName", personName);
        intent.putExtra("time", time);
        startActivityForResult(intent, RECOMMEND_REQUEST);
    }
}
