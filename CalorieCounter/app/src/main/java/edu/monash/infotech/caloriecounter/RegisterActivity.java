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
import android.widget.RadioButton;
import android.widget.Spinner;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

import edu.monash.infotech.caloriecounter.Tools.Utility;
import edu.monash.infotech.caloriecounter.model.DatabaseHelper;
import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.model.UserLocal;
import edu.monash.infotech.caloriecounter.model.UserServer;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;

public class RegisterActivity extends AppCompatActivity{
    public UserServer user;
    protected EditText      edName;
    protected EditText      edAge;
    protected RadioButton   gender1;
    protected RadioButton   gender2;
    protected EditText      edHeight;
    protected EditText      edWeight;
    protected Spinner       sLevel;
    protected EditText      edusername;
    protected EditText      edpassword;
    protected EditText      edstepPerMile;
    protected Button        cancelButton;
    protected Button        continueButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get the view from register.xml
        user = new UserServer();
        edName = (EditText)findViewById(R.id.nameEdit);
        edAge = (EditText) findViewById(R.id.editAge);
        gender1 = (RadioButton)findViewById(R.id.male);
        gender2 = (RadioButton)findViewById(R.id.female);
        edHeight = (EditText)findViewById(R.id.editHeight);
        edWeight = (EditText)findViewById(R.id.editWeight);
        sLevel = (Spinner)findViewById(R.id.spinner);
        edusername = (EditText)findViewById(R.id.usernameEdit);
        edpassword = (EditText)findViewById(R.id.passwordEdit);
        edstepPerMile = (EditText)findViewById(R.id.stepPerMileEdit);

        //achieve the function of cancel
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //achieve the function of continue
        continueButton = (Button)findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gender=null;
                String Name = edName.getText().toString();
                String Age = edAge.getText().toString();
                if(gender1.isChecked()) {
                    gender = "male";
                } else if(gender2.isChecked()) {
                    gender = "female";
                } else{
                    gender=null;
                }
                String Height = edHeight.getText().toString();
                String Weight = edWeight.getText().toString();
                String Level = sLevel.getSelectedItem().toString();
                String username = edusername.getText().toString();
                String password = edpassword.getText().toString();
                String stepPerMile = edstepPerMile.getText().toString();
                try {
                    if(new Utility().getUser(user,RegisterActivity.this,Name,Age,gender,Weight,Height,Level,username,password,stepPerMile)){
                        String url = URLvector.URL_userRegister+user.getName()+"/"+user.getAge()+"/"+user.getHeight()+"/"+
                                user.getWeight()+"/"+user.getGender()+"/"+user.getLevelActivity()+"/"+user.getStep_per_mile()+"/"+
                                user.getUsername()+"/"+user.getPassword();
                        System.out.println(url);
                        new UserRegister().execute(url);
                        //首先写入网络数据库，成功后写到本地数据库
                        new UserToSqlite().execute(new UserLocal(username,password,Utility.getDate()));
                        Utility.show(RegisterActivity.this,"Reister sucessfully,please login the Calorie Counter!");
                        Intent intent  = new Intent();
                        intent.setClass(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    /**
     * 异步处理网络操作，调用RESTful接口完成用户注册
     */

    private class UserRegister extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            return ConnectWeb.getContent(urls[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("写到网络数据库");
            Utility.show(RegisterActivity.this,"RegisterActivity successfully");
        }
    }

    /**
     * 异步处理数据库操作，将注册信息缓存到本地数据库Sqlite
     */

    private class UserToSqlite extends AsyncTask<UserLocal,Void,Void>{

        @Override
        protected Void doInBackground(UserLocal... user) {
            DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());
            try {
                dbhelper.addUser(user[0]);
                System.out.println("本地数据库写入成功");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
