package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class StatisticActivity extends AppCompatActivity {
    LinearLayout chart;
    /**
     * TODO 차트 채울 것 필요.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        chart = (LinearLayout) findViewById(R.id.chart);
        init(this);
    }

    private void init(Context context) {
        viewChart(context, "pie_chart");
    }

    /**
     * TODO chart 마다 채울 수 있게끔.
     * @param context
     * @param chartName
     */
    private void viewChart(Context context, String chartName) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int chartId = R.layout.pie_chart;
        switch (chartName) {
            case "pie_chart":
                chartId = R.layout.pie_chart;
                break;
            case "bar_chart":

                break;
        }
        inflater.inflate(chartId, chart, true);

    }
}
