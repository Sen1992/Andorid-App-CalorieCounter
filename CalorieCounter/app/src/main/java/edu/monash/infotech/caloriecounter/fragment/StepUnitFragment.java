package edu.monash.infotech.caloriecounter.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import edu.monash.infotech.caloriecounter.R;
import edu.monash.infotech.caloriecounter.Tools.Utility;
import edu.monash.infotech.caloriecounter.model.DatabaseHelper;
import edu.monash.infotech.caloriecounter.model.StepLocal;
import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;

/**
 * Created by sen on 2016/4/15.
 */
public class StepUnitFragment extends Fragment {
    View vStep;
    protected TextView step; //Input step;
    protected Button addStep;
    protected Button endStep;
    protected String username;
    protected TextView stepView; //display current steps
    protected ListView stepList;
    protected MyAdapter adapter;
    protected ArrayList<HashMap<String,String>> records;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vStep = inflater.inflate(R.layout.fragment_step_uint, container, false);
        Bundle bundle = getArguments();
        username = bundle.getString("username");
        init();
        new getTotalStep().execute(username);
        try {
            records = new ViewMulRecord().execute(username).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        adapter = new MyAdapter(getActivity());
        stepList = (ListView)vStep.findViewById(R.id.stepList);
        stepList.setAdapter(adapter);
        return vStep;
    }

    /**
     * 初始化组件
     */
    private void init(){
        stepView = (TextView)vStep.findViewById(R.id.stepView);
        step = (TextView)vStep.findViewById(R.id.step);
        addStep = (Button)vStep.findViewById(R.id.addstep);
        addStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stepInput = step.getText().toString();//获取输入的步数
                step.setText("");
                if(Utility.NotNull(stepInput)){
                    Utility.show(getActivity(), "update successful");
                    new AddStep().execute(username, stepInput);
                    int currentStep = Integer.parseInt(stepView.getText().toString().trim())+Integer.parseInt(stepInput);
                    stepView.setText(String.valueOf(currentStep));
                    HashMap<String,String> map = new HashMap<String, String>();
                    map.put("time",Utility.getDateTime());
                    map.put("steps",stepInput);
                    records.add(map);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Utility.show(getActivity(),"Please input the steps");
                }
            }
        });
        endStep = (Button)vStep.findViewById(R.id.endstep);
        endStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = URLvector.URL_updateStep + username + "/" + Utility.getDate() + "/" + stepView.getText().toString();
                System.out.println("更新restful数据库的URL为:" + url);
                new UpdateReport().execute(url);
                stepView.setText("0");
                Utility.show(getActivity(), "update database successfully");
            }
        });
    }


    private class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;//动态布局映射

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return records.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //根据布局文件实例化View
            convertView = mInflater.inflate(R.layout.step_list_view, null);
            //寻找控件，并给控件写入数据
            TextView time = (TextView) convertView.findViewById(R.id.listtime);
            TextView steps = (TextView) convertView.findViewById(R.id.liststep);
            time.setText(records.get(position).get("time"));
            steps.setText(records.get(position).get("steps"));
            return convertView;
        }
    }
    private class AddStep extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
            long uid = dbhelper.getUid(params[0]);
            dbhelper.addStep(new StepLocal(Utility.getDateTime(), Long.parseLong(params[1]), uid));
            return null;
        }
    }
    private class UpdateReport extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            ConnectWeb.getContent(params[0]);
            return null;
        }
    }

    private class getTotalStep extends AsyncTask<String,Void,Long>{

        @Override
        protected Long doInBackground(String... params) {
            DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
            long step = dbhelper.getTotalStep(params[0]);
            return step;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            stepView.setText(String.valueOf(aLong));
        }
    }
    private class ViewMulRecord extends AsyncTask<String,Void,ArrayList<HashMap<String,String>>>{

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {
            DatabaseHelper dbhelper = new DatabaseHelper(getActivity());
            return dbhelper.getAllStepRecods(params[0]);
        }
    }
}
