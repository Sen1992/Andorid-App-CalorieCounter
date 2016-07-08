package edu.monash.infotech.caloriecounter.Tools;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import edu.monash.infotech.caloriecounter.model.URLvector;
import edu.monash.infotech.caloriecounter.model.UserServer;
import edu.monash.infotech.caloriecounter.networkConnection.ConnectWeb;

/**
 * Created by Sen on 2016/4/13.
 * There are many functions to be called to handle data
 */
public class Utility {
    /**
     * Check for Null String object
     * @param txt
     * @return true for not null and false for null String object
     */
    public static boolean NotNull(String txt){
        return txt !=null && txt.trim().length()>0 ? true : false;
    }

    /**
     * Output some additional information to remain the user state
     * @param context current activity
     * @param txt  the information to be output
     */
    public static void show(Context context,String txt){
        Toast.makeText(context,txt, Toast.LENGTH_SHORT).show();
    }

    /**
     * Get system date
     * @return the int form of date
     */
    public static String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * Input the necessary information from RegisterActivity UI to construct the UserServer class
     * @param user
     * @param context
     * @param name
     * @param age
     * @param gender
     * @param weight
     * @param height
     * @param level
     * @param username
     * @param password
     * @param steppermile
     * @return if success return true,else return false
     */
    public  boolean getUser(UserServer user,Context context,String name,String age,String gender, String weight,
                                     String height,String level,String username,String password,String steppermile) throws ExecutionException, InterruptedException {
        if (NotNull(name) && NotNull(age) && NotNull(gender) && NotNull(weight) && NotNull(height) && NotNull(level) &&
                NotNull(username) && NotNull(password) && NotNull(steppermile) && check_user(username)) {
            user.setName(name);
            user.setAge(Integer.parseInt(age));
            user.setGender(gender);
            user.setWeight(Double.parseDouble(weight));
            user.setHeight(Double.parseDouble(height));
            user.setLevelActivity(level.charAt(0));
            user.setUsername(username);
            user.setPassword(password);
            user.setStep_per_mile(Integer.parseInt(steppermile));
            return true;
        } else {
            if (!NotNull(name)) {
                show(context, "please fill the name");
                return false;
            }
            if (!NotNull(age)) {
                show(context, "please fill the age");
                return false;
            }
            if (!NotNull(gender)) {
                show(context, "please select the gender");
                return false;
            }
            if (!NotNull(weight)) {
                show(context, "please fill the weight");
                return false;
            }
            if (!NotNull(height)) {
                show(context, "please fill the height");
                return false;
            }
            if (!NotNull(level)) {
                show(context, "please select the level");
                return false;
            }
            if (!NotNull(username)) {
                show(context, "please fill the username"); //在数据库中按username查找，如果有结果，则username重复
                return false;
            }
            if (!NotNull(password)) {
                show(context, "please fill the password");
                return false;
            }
            if (!NotNull(steppermile)) {
                show(context, "please fill the steps per mile");
                return false;
            }
            if(!check_user(username)){
                show(context,"username already exists,please change it");
                return false;
            }
            return false;
        }
    }

    /**
     * 检测用户名是否唯一
     * @param username
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public boolean check_user(String username) throws ExecutionException, InterruptedException {

        String url= URLvector.URL_findByUsername+username;
        String result = new UserCheck().execute(url).get();
        if(result.equals("[]"))
            return true;
        else
            return false;
    }

    class UserCheck extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            return ConnectWeb.getContent(urls[0]);
        }
    }

    /**
     * 解析字符串
     * @param str
     * @return
     */
    public static String [] ParseString(String str){
        String str1 = str.replace(" ","");
        String [] s1= str1.split("\\||-|:");
        String [] s2 ={s1[2],s1[4]};
        return s2;
    }
    /**
     * 返回时间
     */
    public static String getDateTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String extraTime(String datetime){
        int size = datetime.length();
        return datetime.substring(11,size);
    }





}
