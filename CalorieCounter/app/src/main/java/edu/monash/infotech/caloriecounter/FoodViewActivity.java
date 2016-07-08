package edu.monash.infotech.caloriecounter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import edu.monash.infotech.caloriecounter.Tools.FatSecretAPI;
import edu.monash.infotech.caloriecounter.Tools.Utility;
import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;

public class FoodViewActivity extends AppCompatActivity {

    protected TextView  foodDesc;
    protected ImageView foodImage;
    protected TextView  fat;
    protected TextView  calorie;
    protected ProgressBar pb1;
    protected ProgressBar pb2;
    protected String foodname;
    protected TextView fooditem;
    protected Button add;
    protected String username;
    protected EditText numText;
    protected String num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        foodname = bundle.getString("foodname");
        username = bundle.getString("username");
        System.out.println("从这里输出"+foodname);
        init();
    }

    private void init(){
        fat = (TextView)findViewById(R.id.fat);
        calorie = (TextView)findViewById(R.id.calorie);
        foodDesc = (TextView)findViewById(R.id.FoodDescruiption);
        foodImage = (ImageView)findViewById(R.id.imageFood);
        fooditem = (TextView)findViewById(R.id.foodItem);
        fooditem.setText(foodname);
        pb1 = (ProgressBar)findViewById(R.id.pb1);
        pb2 = (ProgressBar)findViewById(R.id.pb2);
        numText = (EditText)findViewById(R.id.num);
        add = (Button)findViewById(R.id.Add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = numText.getText().toString().trim();
                if(Utility.NotNull(num)) {
                    String url = URLvector.URL_addDiet + username + "/" + foodname + "/" + num + "/"+Utility.getDateTime();
                    new AddDiet().execute(url);
                    System.out.println(url);
                    Utility.show(FoodViewActivity.this,"Add diet successfully");
                }
                else
                    Utility.show(FoodViewActivity.this,"Please input the num");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new BingWebSearch().execute(foodname);
        new BingImageSearch().execute(foodname);
        new FatSecret().execute(foodname);

    }

    private class AddDiet extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            System.out.println("woxiangkankan "+params[0]);
            return ConnectWeb.getContent(params[0]);
        }
    }

    private class BingImageSearch extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... paras) {
            String accountKey = "SXr+/UFv0ZHyDs9clpzUZeV39iH7qi0wbTA3G+PHhP4";
            String parameters = "%27&$top=3" + "&$format=JSON&Market=%27en-us%27";
            byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
            String accountKeyEnc = new String(accountKeyBytes);
            String path = "https://api.datamarket.azure.com/Bing/Search/Image";
            HttpURLConnection conn = null;
            String Content = paras[0].replaceAll(" ", "%20");
            path += "?Query=%27" + Content + parameters;

            System.out.println("Path=" + path);

            //网络访问
            try {
                URL url = new URL(path);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String result = sb.toString();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                JSONObject d = jsonObject.getJSONObject("d");
                JSONArray results = d.getJSONArray("results");
                JSONObject r = (JSONObject) results.get(1);
                String Result = r.getString("MediaUrl");
                Picasso.with(FoodViewActivity.this).load(Result).into(foodImage);
//                Picasso.with(FoodViewActivity.this).load(Result).resize(200,200).centerCrop().into(foodImage);
                pb1.setVisibility(View.GONE);
                foodImage.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class BingWebSearch extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... paras) {
            String accountKey = "SXr+/UFv0ZHyDs9clpzUZeV39iH7qi0wbTA3G+PHhP4";
            String parameters = "%27&$top=3" + "&$format=JSON&Market=%27en-us%27";
            byte[] accountKeyBytes = Base64.encodeBase64((accountKey + ":" + accountKey).getBytes());
            String accountKeyEnc = new String(accountKeyBytes);
            String path = "https://api.datamarket.azure.com/Bing/Search/Web";
            HttpURLConnection conn = null;

            String Content = paras[0].replaceAll(" ", "%20");
            path += "?Query=%27"+Content+ parameters;

            System.out.println("Path=" + path);

            //网络访问

            try {
                URL url = new URL(path);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Basic " + accountKeyEnc);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                String result = sb.toString();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                JSONObject d = jsonObject.getJSONObject("d");
                JSONArray results = d.getJSONArray("results");
                JSONObject r = (JSONObject)results.get(0);
                String Result = r.getString("Description");
                pb2.setVisibility(View.GONE);
                foodDesc.setText(Result);
                foodDesc.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class FatSecret extends AsyncTask<String,Void,JSONObject>{

        @Override
        protected JSONObject doInBackground(String... params) {
            FatSecretAPI api = new FatSecretAPI("fb94787b2bf44cb499b4e1a411c8ef70", "87adc1809c6043db808d6c095146c559");
            try {
                System.out.println("fats:"+params[0]);
                JSONObject foods = api.getFoodItems(params[0]);
                String str = foods.toString();
                System.out.println(str);
                return foods;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//For getting recipes
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            super.onPostExecute(s);
            try {
                JSONObject r1 = s.getJSONObject("result");
                JSONObject r2 = r1.getJSONObject("foods");
                JSONArray r3= r2.getJSONArray("food");
                JSONObject r4 =  (JSONObject)r3.get(0);
                String desc = r4.getString("food_description");
                System.out.println(desc);
                String []r = Utility.ParseString(desc);
                calorie.setText(r[1]);
                fat.setText(r[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




}
