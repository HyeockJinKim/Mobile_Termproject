package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RecommendActivity extends AppCompatActivity {
    RecommendAdapter recommendAdapter;
    public static final int RECOMMEND_REQUEST = 1;
    Context mContext = this;

    /**
     * TODO 같이 가는 사람, 시간으로 분석한 추천 장소 입력.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("personName");
        String time = intent.getExtras().getString("time");

        ListView recommendList = (ListView) findViewById(R.id.recommendList);
        recommendAdapter = new RecommendAdapter();

        recommendList.setAdapter(recommendAdapter);

        recommendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // TODO : 아이템 클릭시 mainActivity로 돌아가 해당 장소에 표시 및 연결.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecommendItem item = (RecommendItem) recommendAdapter.getItem(i);
                String name = item.getLocationName();
                double distance = item.getDistance();

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    class RecommendAdapter extends BaseAdapter {
        ArrayList<RecommendItem> items = new ArrayList<>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        /**
         * TODO : Recommend item 값들 채우기.
         */
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RecommendItemView recommendItemView = new RecommendItemView(mContext);

            RecommendItem item = items.get(i);



            return recommendItemView;
        }
    }
}
