package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by user on 2017-11-11.
 */

public class RecommendItemView extends LinearLayout {

    /**
     * TODO recommend_item에 넣을 item 형식 채우고 맞춰 넣기.
     */
    public RecommendItemView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recommend_item, this, true);

    }

}
