package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class StatisticActivity extends AppCompatActivity {
    LinearLayout chart;
    Context mContext = this;
    String filePath;
    TreeMap<Integer, String> treeMap = null;
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

    /**
     * TODO filePath 위치 지정하기.
     */
    private void init(Context context) {
//        filePath =
        if (treeMap == null) {
            try {
                readLocationData();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Can't read my location data", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
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

    /**
     *  TODO : File에서 데이터를 읽어 map에 넣는 작업.
     *  직접 다녔던 데이터를 read해서 Map에 넣어 return.
     */
    private void readLocationData() throws IOException {
        Map<String, Integer> tiemMap = new HashMap<>();
        File dataFile = new File(filePath);

    }

}
