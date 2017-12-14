package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        Log.d("RecommendActivity", "personName:"+name+", time:"+time);

        ListView recommendList = (ListView) findViewById(R.id.recommendList);
        recommendAdapter = new RecommendAdapter();
        //TODO 어댑터에 아이템 추가해야 함.
        //일단 임의로
        recommendAdapter.addItem(new RecommendItem("name1", 10, "category1"));
        recommendAdapter.addItem(new RecommendItem("name2", 20, "category2"));

        recommendList.setAdapter(recommendAdapter);

        recommendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            // TODO : 아이템 클릭시 mainActivity로 돌아가 해당 장소에 표시 및 연결.
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecommendItem item = (RecommendItem) recommendAdapter.getItem(i);
                String name = item.getLocationName();
                double distance = item.getDistance();
                Log.d("RecommendActivity", name+", "+distance);
                //TODO 클릭하면 지도에 띄워야 하는거 아님?
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    class RecommendAdapter extends BaseAdapter {
        ArrayList<RecommendItem> items = new ArrayList<>();

        public void addItem(RecommendItem item) {
            items.add(item);
        }

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

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            RecommendItemView recommendItemView = new RecommendItemView(mContext);

            RecommendItem item = items.get(i);
            recommendItemView.setLocationName(item.getLocationName());
            recommendItemView.setDistance(item.getDistance());

            return recommendItemView;
        }
    }
}
