package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by user on 2017-11-11.
 */

public class RecommendItemView extends LinearLayout {
    TextView textView1;
    TextView textView2;

    /**
     * TODO recommend_item에 넣을 item 형식 채우고 맞춰 넣기.
     */
    public RecommendItemView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recommend_item, this, true);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
    }

    public void setLocationName(String locationName) {
        textView1.setText(locationName);
    }

    public void setDistance(double distance) {
        textView2.setText(String.valueOf(distance));
        //TODO 뒤에 거리단위 붙여야 함
        //textView2.append("");
    }

}
