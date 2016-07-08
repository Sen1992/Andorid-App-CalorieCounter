package edu.monash.infotech.caloriecounter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import edu.monash.infotech.caloriecounter.Tools.Utility;
import edu.monash.infotech.caloriecounter.fragment.FoodUnitFragment;
import edu.monash.infotech.caloriecounter.fragment.GoalUnitFragment;
import edu.monash.infotech.caloriecounter.fragment.HistoryUnitFragment;
import edu.monash.infotech.caloriecounter.fragment.MainUnitFragment;
import edu.monash.infotech.caloriecounter.fragment.ReportUnitFragment;
import edu.monash.infotech.caloriecounter.fragment.StepUnitFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected TextView tvName;
    protected TextView tvDate;
    protected String loginname;
    protected String loginusername;
    protected TextView userInfo;
    protected DrawerLayout drawer;
    /**
     * The variable is to be used for transferring data form HomeActivity to GoalUnitFragment
     */
    protected Bundle bundleFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        //从LoginActivity里面获得数据
        Bundle bundle = intent.getExtras();
        loginname = bundle.getString("name");
        loginusername = bundle.getString("username");
        init();
        System.out.println("主界面输出" + loginname + "  " + loginusername);
        //使用Bundle作为媒介，将数据写入bundleGoal
        bundleFrag = new Bundle();
        bundleFrag.putString("username",loginusername);
        bundleFrag.putString("name",loginname);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MainUnitFragment main = new MainUnitFragment();
        fragmentManager.beginTransaction().replace(R.id.content_frame,main).commit();
        drawer.closeDrawer(GravityCompat.START);
    }


    private void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        userInfo = (TextView)navigationView.getHeaderView(0).findViewById(R.id.userInfo);
        userInfo.setText(loginname);
        tvName = (TextView)findViewById(R.id.name);
        tvName.setText(loginname + "," + "Welcome to Calorie Counter");
        tvDate = (TextView)findViewById(R.id.date);
        tvDate.setText(Utility.getDate());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment nextFragment = null;

        switch (id){
            case R.id.nav_home:
                nextFragment = new MainUnitFragment();
                break;
            case R.id.nav_food:
                nextFragment = new FoodUnitFragment();
                nextFragment.setArguments(bundleFrag);
                break;
            case R.id.nav_step:
                nextFragment = new StepUnitFragment();
                nextFragment.setArguments(bundleFrag);
                break;
            case R.id.nav_goal:
                nextFragment = new GoalUnitFragment();
                //将bundle作为参数传入fragment
                nextFragment.setArguments(bundleFrag);
                break;
            case R.id.nav_history:
                nextFragment = new HistoryUnitFragment();
                nextFragment.setArguments(bundleFrag);
                break;
            case R.id.nav_report:
                nextFragment = new ReportUnitFragment();
                nextFragment.setArguments(bundleFrag);
                break;
            case R.id.nav_map:
                Intent intent = new Intent();
                intent.setClass(HomeActivity.this, MapActivity.class);
                startActivity(intent);
                break;
            default:
                Utility.show(HomeActivity.this,"Unknow Exception");
        }
        if(id != R.id.nav_map) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
