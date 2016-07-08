package edu.monash.infotech.caloriecounter;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.xml.transform.stream.StreamSource;

import edu.monash.infotech.caloriecounter.Tools.FatSecretAPI;
import edu.monash.infotech.caloriecounter.Tools.Utility;
import edu.monash.infotech.caloriecounter.model.DatabaseHelper;
import edu.monash.infotech.caloriecounter.model.MyApplication;
import edu.monash.infotech.caloriecounter.model.StepLocal;
import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;


public class LoginActivity extends AppCompatActivity {

    protected   Button    loginButton;
    protected   TextView  registerTextview;
    protected   String    userName;
    protected   String    passWord;
    protected   String    result;
    protected MyApplication userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        /**
         * click the sign up,move to the register activity
         */
        registerTextview = (TextView)findViewById(R.id.register_link);
        registerTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        /**
         * click the login button,move to the
         */
        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = ((EditText) findViewById(R.id.username)).getText().toString();
                passWord = ((EditText) findViewById(R.id.password)).getText().toString();
                if (Utility.NotNull(userName) && Utility.NotNull(passWord)) {
                    String url= URLvector.URL_findByUsernameAndPassword+userName+"/"+passWord+"/"+Utility.getDate();
                    new UserLogin().execute(url);
                }
                else {
                    if (!Utility.NotNull(userName))
                        Utility.show(LoginActivity.this,"Please fill the username");
                    else
                        Utility.show(LoginActivity.this,"Please fill the password");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class UserLogin extends AsyncTask<String,Void,String>{
        /**
         * Get resourse form RESTful by username and password
         * @param urls the address of restful API
         * @return get the query result
         */
        @Override
        protected String doInBackground(String... urls) {
            return ConnectWeb.getContent(urls[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            if(!s.equals("[]")){
                String name=null,username=null;
                try {
                    System.out.println(s);
                    JSONArray result = new JSONArray(s);
                    name = result.getJSONObject(0).getString("uname");
                    username = result.getJSONObject(0).getString("username");
                    System.out.println("Name:"+name+":"+username);
                    userInfo = (MyApplication) getApplication();
                    userInfo.setName(name);
                    userInfo.setUsername(username);
                    userInfo.setAge(result.getJSONObject(0).getString("age"));
                    userInfo.setGender(result.getJSONObject(0).getString("gender"));
                    userInfo.setWeight(result.getJSONObject(0).getString("weight"));
                    userInfo.setHeight(result.getJSONObject(0).getString("height"));
                    userInfo.setActivity(result.getJSONObject(0).getString("levelOfActivity"));
                    userInfo.setStep(result.getJSONObject(0).getString("stepPerMile"));
                    userInfo.setPassword(passWord);
                    new generateStep().execute(userName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Utility.show(LoginActivity.this, "Welcome to the Calorie Counter");
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, HomeActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("username",username);
                startActivity(intent);
            }
            else {
                Utility.show(LoginActivity.this,"Wrong username or password,please try again");
            }
        }
    }
    private class generateStep extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());
            long uid = dbhelper.getUid(params[0]);
            System.out.println("用户名:"+params[0]);
            System.out.println("long uid = dbhelper.getUid(params[0]);新注册用户的ID"+uid);
            StepLocal s = dbhelper.getRecordByUsername(uid);
            if (s==null) {
                System.out.println("今天第一次登陆，生成本地整空表");
                dbhelper.addStep(new StepLocal(Utility.getDateTime(), 0, uid));
            }
            else {
                System.out.println("不是第一次登陆，不再生成本地记录表");
            }
            return null;
        }
    }
}
