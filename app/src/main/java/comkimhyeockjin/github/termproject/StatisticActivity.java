package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class StatisticActivity extends AppCompatActivity {
    LinearLayout chart;
    Context mContext = this;

    static final int LINE_CHART = R.layout.line_chart;
    static final int BAR_CHART = R.layout.bar_chart;
    static final int PIE_CHART = R.layout.pie_chart;

    /**
     * TODO 차트 채울 것 필요.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        chart = (LinearLayout) findViewById(R.id.chart);
        init(mContext);


        Button line = (Button) findViewById(R.id.lineBtn);
        Button bar = (Button) findViewById(R.id.barBtn);
        Button pie = (Button) findViewById(R.id.pieBtn);


        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewChart(mContext, LINE_CHART);
            }
        });

        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewChart(mContext, BAR_CHART);
            }
        });

        pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewChart(mContext, PIE_CHART);
            }
        });
    }

    private void init(Context context) {
        viewChart(context, PIE_CHART);
    }

    /**
     * TODO chart 마다 채울 수 있게끔.
     * @param context
     */
    private void viewChart(Context context, int chartId) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(chartId, chart, true);
        makeChart(chartId);
    }

    private void makeChart(int chartId) {
        switch (chartId) {
            case PIE_CHART:
                PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(10, "hz"));
                entries.add(new PieEntry(15, "khz"));
                PieDataSet dataSet = new PieDataSet(entries, "ban");
                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());
                pieChart.setData(data);
                pieChart.invalidate();
                break;
            case LINE_CHART:
                LineChart lineChart = (LineChart) findViewById(R.id.lineChart);

                break;
            case BAR_CHART:
                BarChart barChart = (BarChart) findViewById(R.id.barChart);

                break;
        }
    }

    
}
