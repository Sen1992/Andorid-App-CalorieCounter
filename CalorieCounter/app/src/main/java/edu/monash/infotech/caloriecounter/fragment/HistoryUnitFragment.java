package edu.monash.infotech.caloriecounter.fragment;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.monash.infotech.caloriecounter.R;
import edu.monash.infotech.caloriecounter.Tools.Utility;
import edu.monash.infotech.caloriecounter.model.DatabaseHelper;
import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;

/**
 * Created by 王森 on 2016/4/15.
 */
public class HistoryUnitFragment extends Fragment {
    View vHistory;

    protected   TextView    daily;
    protected   TextView    weekly;
    protected   PieChart    mChart;
    protected   String      username;
    protected   BarChart    mBarChart;
    protected   LineChart   mLineChart;
    protected   LineChart   mLineChart1;
    protected   EditText    startDate;
    protected   EditText    endDate;
    protected   Button      showChart;
    protected   LinearLayout    weekLayout;
    protected   ScrollView  dailyLaout;
    protected   ImageView   picture;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vHistory = inflater.inflate(R.layout.fragment_history_uint,container,false);
        startDate = (EditText)vHistory.findViewById(R.id.startdate);
        endDate = (EditText)vHistory.findViewById(R.id.enddate);

        picture = (ImageView)vHistory.findViewById(R.id.homePic);

        weekLayout = (LinearLayout)vHistory.findViewById(R.id.weeklyChart);
        dailyLaout = (ScrollView)vHistory.findViewById(R.id.dailyChart);

        showChart = (Button)vHistory.findViewById(R.id.showChart);
        showChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date1=startDate.getText().toString();
                String date2=endDate.getText().toString();
                //显式输入开始和结束日期的折线图
                if(Utility.NotNull(date1)&& Utility.NotNull(date2)){
                    String url = URLvector.URL_getOnPeriod+username+"/"+date1+"/"+date2;
                    System.out.println(url);
                    new getOnPeriod().execute(url);
                    startDate.setText("");
                    endDate.setText("");
                }
                else{
                    Utility.show(getActivity(),"Please Input Date");
                }
            }
        });


        daily = (TextView)vHistory.findViewById(R.id.daily);
        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekly.setBackgroundColor(Color.WHITE);
                daily.setBackgroundColor(Color.RED);
                picture.setVisibility(View.GONE);
                weekLayout.setVisibility(View.GONE);
                dailyLaout.setVisibility(View.VISIBLE);
            }
        });
        weekly = (TextView)vHistory.findViewById(R.id.weekly);
        weekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daily.setBackgroundColor(Color.WHITE);
                weekly.setBackgroundColor(Color.RED);
                picture.setVisibility(View.GONE);
                dailyLaout.setVisibility(View.GONE);
                weekLayout.setVisibility(View.VISIBLE);
            }
        });
        Bundle bundle = getArguments();
        username = bundle.getString("username");
        mChart = (PieChart)vHistory.findViewById(R.id.stepPie);
        mBarChart = (BarChart)vHistory.findViewById(R.id.calorieBar);
        mLineChart = (LineChart)vHistory.findViewById(R.id.BurnedChart);
        mLineChart1 = (LineChart)vHistory.findViewById(R.id.ConsumeChart);
        new getStepOnOneDay().execute(username);
        String url = URLvector.URL_getInfo+username+"/"+Utility.getDate();
        new getInfo().execute(url);
        return vHistory;
    }
    private class getStepOnOneDay extends AsyncTask<String,Void,List<HashMap<String,String>>>{

        @Override
        protected List<HashMap<String,String>> doInBackground(String... params) {
            DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
            return dbhelper.getAllStepRecods(params[0]);
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            super.onPostExecute(hashMaps);

            int size = hashMaps.size();
            String [] time = new String[size];
            int [] steps = new int[size];
            for (int i =0;i<size;i++){
                time[i]=hashMaps.get(i).get("time");
                steps[i] = Integer.parseInt(hashMaps.get(i).get("steps"));
            }
            PieData mPieDate = getPieData(size,100,time,steps);
            showPieChart(mChart, mPieDate);
        }
    }
    private void showPieChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleRadius(60f);  //半径
        pieChart.setHoleRadius(0);  //实心圆

        pieChart.setDrawCenterText(false);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        pieChart.setRotationEnabled(true); // 可以手动旋转
        pieChart.setUsePercentValues(false);  //显示成百分比

//        pieChart.setCenterText("Quarterly Revenue");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        pieChart.animateXY(1000, 1000);  //设置动画
    }
    private PieData getPieData(int count, float range,String [] time,int [] num) {

        List<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        List<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        List<Integer> colors = new ArrayList<Integer>();    //设置不同的颜色

        for (int i = 0; i < count; i++) {
            xValues.add(Utility.extraTime(time[i]));  //饼块上显示成的属性
            yValues.add(new Entry(num[i], 0)); //每一部分显示的数值
        }

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "Steps Record on"+ Utility.getDate());
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离


         //饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));
        colors.add(Color.rgb(0, 0, 0));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
    private class getInfo extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return ConnectWeb.getContent(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject result = new JSONObject(s);
                String consume = result.getString("consume");
                String goal = result.getString("goal");
                String burned = result.getString("burned");
                float [] array = {Float.parseFloat(consume),Float.parseFloat(goal),Float.parseFloat(burned)};
                BarData mBarData = getBarData(array);
                showBarChart(mBarChart,mBarData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void showBarChart(BarChart barChart,BarData barData){
        barChart.setDrawBorders(false);
        barChart.setNoDataTextDescription("You need to provide data for the chart.");
        barChart.setDrawGridBackground(false);
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(true);
        barChart.setData(barData);
        Legend mLegend = barChart.getLegend();
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色
        barChart.animateX(2500);
    }
    private BarData getBarData(float [] num){
        List<String> xValues = new ArrayList<String>();
        xValues.add("Consume");
        xValues.add("Burned");
        xValues.add("Goal");
        List<BarEntry> yValues = new ArrayList<BarEntry>();
        yValues.add(new BarEntry(num[0], 0));
        yValues.add(new BarEntry(num[1], 1));
        yValues.add(new BarEntry(num[2], 2));
        BarDataSet barDataSet = new BarDataSet(yValues,"test bar graph");
        List<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(255, 123, 124));
        barDataSet.setColors(colors);

        BarData barData = new BarData(xValues,barDataSet);
        return barData;
    }

    private class getOnPeriod extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return ConnectWeb.getContent(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                JSONArray resultArray = jsonObject.getJSONArray("result");
                int size = resultArray.length();
                float [] arrayConsume = new float[size];
                float [] arrayBurned = new float[size];
                String [] arrayDate = new String[size];
                for(int i=0;i<size;i++){
                    arrayConsume[i] = Float.parseFloat(resultArray.getJSONObject(i).getString("cosume"));
                    arrayBurned[i]  = Float.parseFloat(resultArray.getJSONObject(i).getString("burned"));
                    arrayDate[i]    = resultArray.getJSONObject(i).getString("date");
                }
                LineData lineData = getLineData(size,arrayDate,arrayConsume);
                LineData lineData1 = getLineData(size,arrayDate,arrayBurned);
                showLineChart(mLineChart,lineData,Color.rgb(114, 188, 223));
                showLineChart(mLineChart1,lineData1,Color.rgb(114, 188, 223));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private LineData getLineData(int count,String [] date,float []num) {
        List<String> xValues = new ArrayList<String>();
        List<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            xValues.add(date[i]);
            yValues.add(new Entry(num[i], i));
        }
        LineDataSet lineDataSet = new LineDataSet(yValues, "Calorie Consumed On Period");
        lineDataSet.setLineWidth(1.75f); // 线宽
        lineDataSet.setCircleSize(3f);// 显示的圆形大小
        lineDataSet.setColor(Color.WHITE);// 显示颜色
        lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
        lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
        // create a data object with the datasets
        LineData lineData = new LineData(xValues,lineDataSet);

        return lineData;
    }

    private void showLineChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);

        // no description text
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        lineChart.setDrawGridBackground(false);
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//

        lineChart.setBackgroundColor(color);

        // add data
        lineChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色
//      mLegend.setTypeface(mTf);// 字体

        lineChart.animateX(2500); // 立即执行的动画,x轴
    }
}


