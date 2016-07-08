package edu.monash.infotech.caloriecounter.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edu.monash.infotech.caloriecounter.R;
import edu.monash.infotech.caloriecounter.Tools.Utility;
import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;

/**
 * Created by sen on 2016/4/15.
 */
public class ReportUnitFragment extends Fragment {
    View vReport;


    protected   TextView    goalText;
    protected   TextView    remainingText;
    protected   TextView    consume;
    protected   TextView    burned;
    protected   TextView    net;
    protected   Button      generate;
    protected   TextView    stepView;
    protected   String      username;

    protected   PieChart    mChart;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vReport = inflater.inflate(R.layout.fragment_report_unit, container, false);
        mChart = (PieChart)vReport.findViewById(R.id.goalPie);
        //从HomeActivity中获取数据
        Bundle bundle = getArguments();
        username = bundle.getString("username");
        init();
        String url = URLvector.URL_getInfo+username+"/"+Utility.getDate();
        new GetInfo().execute(url);
        return vReport;
    }

    /**
     * 初始化组件
     */
    private void init(){
        goalText        =   (TextView) vReport.findViewById(R.id.GoalText);
//        goalUnAchieved.setWidth(500);
        remainingText   =   (TextView) vReport.findViewById(R.id.remainingText);
        consume         =   (TextView) vReport.findViewById(R.id.consume);
        burned          =   (TextView) vReport.findViewById(R.id.burned);
        net             =   (TextView) vReport.findViewById(R.id.net);
        //显式总共走了多少步
        stepView        =   (TextView)vReport.findViewById(R.id.StepTotalView);
        generate        =   (Button)   vReport.findViewById(R.id.Generate);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String burnedS = burned.getText().toString();
                String consumeS = consume.getText().toString();
                String remainingS = remainingText.getText().toString();
                if (Utility.NotNull(burnedS)&&Utility.NotNull(consumeS)&&Utility.NotNull(remainingS)) {
                    String url = URLvector.URL_updateReport + username + "/" + burnedS + "/" + consumeS + "/" + remainingS;
                    new updateReport().execute(url);
                }
                else {
                    Utility.show(getActivity(),"conten is null");
                }
            }
        });
    }
    private class GetInfo extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... url) {
            System.out.println(url[0]);
            return ConnectWeb.getContent(url[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject result = new JSONObject(s);
                consume.setText(result.getString("consume"));
                burned.setText(result.getString("burned"));
                goalText.setText(result.getString("goal"));
                stepView.setText(result.getString("steps"));
                remainingText.setText(result.getString("remianing"));
                net.setText(result.getString("net"));
                String [] title = {"achieved","remianing"};
                int []values = {(int)Double.parseDouble(net.getText().toString()),(int)Double.parseDouble(remainingText.getText().toString())};
                PieData mPieDate = getPieData(2,100,title,values);
                showPieChart(mChart, mPieDate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class updateReport extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return ConnectWeb.getContent(params[0]);
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
            xValues.add(time[i]);  //饼块上显示成的属性
            yValues.add(new Entry(num[i], 0)); //每一部分显示的数值
        }

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离


        //饼图颜色
        colors.add(Color.rgb(205, 205, 205));

        colors.add(Color.rgb(57, 135, 200));


        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
}
