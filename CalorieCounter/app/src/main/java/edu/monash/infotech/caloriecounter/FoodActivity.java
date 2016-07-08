package edu.monash.infotech.caloriecounter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;
import edu.monash.infotech.caloriecounter.CallBack.CallBack;

public class FoodActivity extends AppCompatActivity{

    private List<HashMap<String,String>> mData;
    private ListView listView;
    private TextView title;
    private String url;
    private String titleFood;
    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final Intent intend = getIntent();
        Bundle bundle = intend.getExtras();
        titleFood = bundle.getString("title");
        username  = bundle.getString("username");
        url = URLvector.URL_findByCategory+titleFood;
        title = (TextView)findViewById(R.id.foodtitle);
        title.setText(titleFood+" Items");
        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String foodname = mData.get(position).get("item").toString();
                System.out.println("hahahahaha:" + foodname);
                Intent intent1  = new Intent();
                intent1.setClass(FoodActivity.this,FoodViewActivity.class);
                intent1.putExtra("foodname", foodname);
                intent1.putExtra("username",username);
                startActivity(intent1);
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
            return mData.size();
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
            convertView = mInflater.inflate(R.layout.food_list_item, null);


            //寻找控件，并给控件写入数据
            TextView item = (TextView) convertView.findViewById(R.id.item);
            item.setText(mData.get(position).get("item").toString());


            return convertView;
        }
    }

    //初始化一个List
    private List<HashMap<String,String>> getData(){
        //新建一个集合类，用于存放多条数据
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();

        HashMap<String,String> map = null;

        try {
            JSONArray result = new JSONArray(new getFood().execute(url).get());
            for(int i=0;i<result.length();i++){
                JSONObject jo = (JSONObject)result.get(i);
                map = new HashMap<String, String>();
                map.put("item",jo.getString("fname"));
                list.add(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return list;
    }

    private class getFood extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return ConnectWeb.getContent(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println(s);
        }
    }
}
