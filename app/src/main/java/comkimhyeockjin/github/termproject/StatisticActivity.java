package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * TODO 차트에 실제 데이터 넣어야 함.
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

        //TODO FrameLayout으로 바꿔야 함.
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
                entries.add(new PieEntry(10, "a"));
                entries.add(new PieEntry(20, "b"));
                entries.add(new PieEntry(30, "c"));
                entries.add(new PieEntry(40, "d"));
                PieDataSet dataSet = new PieDataSet(entries, "ban");
                List<Integer> colors = new ArrayList<Integer>();
                for (int c : ColorTemplate.PASTEL_COLORS)
                    colors.add(c);
                dataSet.setColors(colors);
                dataSet.setSliceSpace(3f);

                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());

                pieChart.setData(data);
                pieChart.invalidate();
                break;
            case LINE_CHART:
                LineChart lineChart = (LineChart) findViewById(R.id.lineChart);
                ArrayList<Entry> lineEntries = new ArrayList<Entry>();
                lineEntries.add(new Entry(1, 20));
                lineEntries.add(new Entry(2, 10));
                lineEntries.add(new Entry(3, 30));
                LineDataSet lineDataSet1 = new LineDataSet(lineEntries, "set1");
                lineDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
                lineDataSets.add(lineDataSet1);

                LineData lineData = new LineData(lineDataSets);

                lineChart.setData(lineData);
                lineChart.invalidate();
                break;
            case BAR_CHART:
                BarChart barChart = (BarChart) findViewById(R.id.barChart);
                ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();
                barEntries.add(new BarEntry(1, 10));
                barEntries.add(new BarEntry(2, 20));
                barEntries.add(new BarEntry(3, 40));
                BarDataSet dataSet1 = new BarDataSet(barEntries, "bar");
                dataSet1.setColors(ColorTemplate.MATERIAL_COLORS);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                dataSets.add(dataSet1);

                BarData barData = new BarData(dataSets);
                barData.setBarWidth(0.9f);

                barChart.setData(barData);
                barChart.invalidate();
                break;
        }
    }

    /**
     *  TODO : File에서 데이터를 읽어 map에 넣는 작업.
     *  직접 다녔던 데이터를 read해서 Map에 넣어 return.
     */
    private void readLocationData() throws IOException {
        Map<String, Integer> tiemMap = new HashMap<>();
//        File dataFile = new File(filePath);

    }

}
