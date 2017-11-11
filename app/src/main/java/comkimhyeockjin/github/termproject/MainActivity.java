package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * TODO Google Map API를 적용시켜야 함.
 * Google 지도 API가 들어갈 Activity.
 * 이 Activity에서 추천, 통계 를 볼 수 있다.
 */
public class MainActivity extends AppCompatActivity {
    Context mContext = this;
    public static final int RECOMMEND_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button recommendBtn = (Button) findViewById(R.id.recommend);
        Button statisticBtn = (Button) findViewById(R.id.statistic);

        recommendBtn.setOnClickListener(new View.OnClickListener() {

            /**
             * TODO input="같이 간 사람, 추천받을 시간"을 받아 Activity 이동.
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecommendActivity.class);

                String personName = "";
                String time ="";

                intent.putExtra("personName",personName);
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

        init();
    }

    /**
     * TODO 이전에 있었던 장소에 마커 생성
     */
    private void init() {

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

}
