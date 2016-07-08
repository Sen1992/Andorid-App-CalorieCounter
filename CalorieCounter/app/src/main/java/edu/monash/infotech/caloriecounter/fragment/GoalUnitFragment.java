package edu.monash.infotech.caloriecounter.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.monash.infotech.caloriecounter.CallBack.CallBack;
import edu.monash.infotech.caloriecounter.R;
import edu.monash.infotech.caloriecounter.Tools.Utility;
import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;

/**
 * Created by sen on 2016/4/15.
 */
public class GoalUnitFragment extends Fragment{
    View vGoal;
    protected   String  goal;
    protected   String  username;
    protected   LinearLayout    display;
    protected   TextView    goalText;
    protected   Button  modifyButton;
    protected   Button  submitButton;
    protected   Dialog  alterDialog;
    protected   ListView goallist;
    protected   SimpleAdapter adapter;
    protected   List<HashMap<String,String>> goalArrray;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vGoal = inflater.inflate(R.layout.fragment_goal_unit, container, false);
        //从HomeActivity中获取数据
        Bundle bundle = getArguments();
        username = bundle.getString("username");
        goalArrray = new ArrayList<HashMap<String, String>>();
        init();
        String url = URLvector.URL_getGoal+username+"/"+Utility.getDate();
        String url1 = URLvector.URL_getPastGoal+username;
        new getGoal().execute(url);
        new getPastGoal().execute(url1);
        return vGoal;
    }

    /**
     * 初始化组件
     */
    private void init(){
        display = (LinearLayout) vGoal.findViewById(R.id.displayGoal);
        goalText = (TextView)vGoal.findViewById(R.id.goalEdit);
        goallist =(ListView)vGoal.findViewById(R.id.gl);
        modifyButton = (Button)vGoal.findViewById(R.id.modifyButton);
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterDialog.show();
            }
        });
        submitButton = (Button)vGoal.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交数据库
                String url = URLvector.URL_setGoal+username+"/"+Utility.getDate()+"/"+goalText.getText().toString();
                new returnTodatabase().execute(url);
            }
        });
        //建立一个对话框
        final EditText et=new EditText(getActivity());
        et.setHint("Please input number");
        et.setInputType(0x00002002);
        alterDialog = new AlertDialog.Builder(getActivity())
                .setTitle("Please Input The Goal")
                .setIcon(R.drawable.goal)
                .setView(et)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String term = et.getText().toString();
                        if(Utility.NotNull(term)) {
                            Utility.show(getActivity(),"Set Calorie Goal successfully");
                            goal=term;
                            goalText.setText(goal);
                            display.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }


    private class getGoal extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... url) {
            return ConnectWeb.getContent(url[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            String goal;
            try {
                JSONObject result = new JSONObject(s);
                goal = result.getString("status");
                System.out.println("calorie goal"+goal);
                if(goal.equals("0.0"))
                    alterDialog.show();
                else {
                    goalText.setText(goal);
                    display.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class returnTodatabase extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... url) {
            return ConnectWeb.getContent(url[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private class getPastGoal extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            return ConnectWeb.getContent(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray resultArray = jsonObject.getJSONArray("result");
                String[] colHEAD = new String[] {"TIME","GOAL"};
                int[] dataCell = new int[] {R.id.timelistview,R.id.goallistview};
                for(int i = 0;i<resultArray.length();i++){
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("TIME",resultArray.getJSONObject(i).getString("date"));
                    map.put("GOAL",resultArray.getJSONObject(i).getString("goal"));
                    goalArrray.add(map);
                }
                adapter = new SimpleAdapter(getActivity(),goalArrray,R.layout.goal_list_view,colHEAD,dataCell);
                goallist.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
