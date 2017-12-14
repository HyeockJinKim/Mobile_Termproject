package comkimhyeockjin.github.termproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
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
    TreeMap<Integer, String> treeMap = null;
    private PlaceDB placeDB;
    private LocationDB locationDB;
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
        placeDB = new PlaceDB(this);
        locationDB = new LocationDB(this);
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
        chart.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(chartId, chart, true);
        makeChart(chartId);
    }

    private void makeChart(int chartId) {
        //더미 데이터
        String[] locationName = {"a", "b", "c", "d"};

        List<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        ArrayList<PlaceInfo> placeInfoArrayList = placeDB.getAllInfo();

        switch (chartId) {
            case PIE_CHART:
                /*
                장소명을 먼저 배열에 저장.
                그 후에 장소명으로 그룹지어서 시간의 합 구하여 배열에 저장.
                참고. 뒤에 '%'붙일거면 전체 합 구해서 나눠야 함.
                 */
                //더미 데이터
                int[] time = {10, 20, 30, 40};

                PieChart pieChart = (PieChart) findViewById(R.id.pieChart);
                ArrayList<PieEntry> entries = new ArrayList<>();
                //실제 데이터
                if (placeInfoArrayList != null) {
                    for (PlaceInfo placeInfo : placeInfoArrayList) {
                        entries.add(new PieEntry((int) placeInfo.getTime(), placeInfo.getName()));
                    }
                }
            
                PieDataSet dataSet = new PieDataSet(entries, "장소명");
                dataSet.setColors(colors);
                dataSet.setSliceSpace(3f);

                PieData data = new PieData(dataSet);
                data.setValueFormatter(new PercentFormatter());

                pieChart.setData(data);
                pieChart.getDescription().setText("");
                pieChart.invalidate();
                break;
            case LINE_CHART:
                //TODO line chart랑 bar chart의 x축,y축 눈금 조정이 가능하면 조정.
                /*
                장소명을 먼저 배열에 저장.
                장소명으로 저장된 머무른? 내역 확인해서 특정 시간(혹은 시간대)에 그 장소에 있었으면 freq[시간]에 1 추가.
                장소 하나가 data set 1개.
                 */
                //더미 데이터. freq[13]은 13시에 해당 장소에 있었던 횟수.
                int[][] freq = { {0, 20, 10, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                                {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20},
                                {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30} };

                LineChart lineChart = (LineChart) findViewById(R.id.lineChart);

                ArrayList<Double> latList = new ArrayList<>();
                ArrayList<Double> lngList = new ArrayList<>();
                if (placeInfoArrayList != null) {
                    for (PlaceInfo placeInfo : placeInfoArrayList) {
                        latList.add(placeInfo.getLat());
                        lngList.add(placeInfo.getLng());
                    }
                    freq = calcFrequency(latList, lngList);
                }

                ArrayList<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();
                for (int i=0; i<freq.length; i++) {
                    ArrayList<Entry> lineEntries = new ArrayList<Entry>();
                    for (int j=0; j<freq[0].length; j++) {
                        lineEntries.add(new Entry(j, freq[i][j]));
                    }
                    LineDataSet lineDataSet = new LineDataSet(lineEntries, placeInfoArrayList.get(i).getName());
                    lineDataSet.setColor(colors.get(i));

                    lineDataSets.add(lineDataSet);
                }

                LineData lineData = new LineData(lineDataSets);

                lineChart.setData(lineData);
                lineChart.setVisibleXRange(0, 24);lineChart.getDescription().setText("");
                lineChart.setVisibleYRange(0, 7, YAxis.AxisDependency.LEFT);

                Log.d("test", "ScaleX:"+lineChart.getScaleX()+", ScaleY:"+lineChart.getScaleY());
                lineChart.invalidate();
                break;
            case BAR_CHART:
                /*
                장소명을 먼저 배열에 저장.
                장소명으로 저장된 머무른? 내역 확인해서 특정 시간(혹은 시간대)에 그 장소에 있었으면 freq[시간]에 1 추가.
                엔트리 1개가 장소 1개.
                 */
                //더미 데이터
                int[][] freqs = { {0, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                        {3, 3, 1, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3} };

                BarChart barChart = (BarChart) findViewById(R.id.barChart);

                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                for (int t = 0; t < freqs[0].length; t++) {
                    ArrayList<BarEntry> barEntry = new ArrayList<BarEntry>();
                    int max = maxLocIndex(freqs, t);
                    barEntry.add(new BarEntry(t+0.5f, freqs[max][t]));

                    BarDataSet barDataSet = new BarDataSet(barEntry, locationName[max]);
                    barDataSet.setColors(colors.get(max));

                    dataSets.add(barDataSet);
                }

                BarData barData = new BarData(dataSets);
                barData.setBarWidth(0.9f);  //bar 사이의 간격

                barChart.setData(barData);

                barChart.setVisibleXRange(0, 24);
                barChart.setVisibleYRange(0, 7, YAxis.AxisDependency.LEFT);
                barChart.getDescription().setText("");
                barChart.invalidate();
                break;
        }
    }

    private int[][] calcFrequency(ArrayList<Double> latList, ArrayList<Double> lngList) {
        ArrayList<LocationInfo> locationInfoArrayList = locationDB.getAllInfo();
        int[][] freq = new int[latList.size()][24];
        Log.d("test", "locationInfoArrayList.size():"+locationInfoArrayList.size());
        for (int i=0; i<latList.size(); i++) {
            for (LocationInfo locationInfo : locationInfoArrayList) {
                if (latList.get(i)==locationInfo.getLat() && lngList.get(i)==locationInfo.getLng()) {
                    Log.d("test", "날짜:"+locationInfo.getDate()+", time:"+locationInfo.getTime());
                    int hour = Integer.parseInt(locationInfo.getDate().split(" ")[1].split(":")[0]);
                    for (int h=0; h<locationInfo.getTime()/60 +1; h++) {
                        freq[i][hour+h]++;
                        Log.d("test", "freq["+i+"]["+(hour+h)+"] : "+freq[i][hour+h]);
                    }
                }
            }
        }
        return freq;
    }

    private int maxLocIndex(int[][] freq, int time) {
        int maxIndex = 0;
        for (int i=1; i<freq.length; i++) {
            if (freq[maxIndex][time] < freq[i][time]) maxIndex = i;
        }

        return maxIndex;
    }

    /**
     *  TODO : ?에서 데이터를 읽어 map에 넣는 작업.
     *  직접 다녔던 데이터를 read해서 Map에 넣어 return.
     */
    private void readLocationData() throws IOException {
        Map<String, Integer> tiemMap = new HashMap<>();

    }

}
