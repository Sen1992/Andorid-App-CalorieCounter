package edu.monash.infotech.caloriecounter.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.monash.infotech.caloriecounter.Tools.PasswordMD5;
import edu.monash.infotech.caloriecounter.Tools.Utility;

/**
 * Created by sen on 2016/4/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    // Set database properties
    public static final String DATABASE_NAME = "CalorieCounterDB";
    public static final int DATABASE_VERSION =1;

    //Construct
    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(edu.monash.infotech.caloriecounter.model.UserLocal.CREATE_STATEMENT);
        db.execSQL(StepLocal.CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserLocal.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StepLocal.TABLE_NAME);
        onCreate(db);
    }

    /**
     * UserLocal database method to add a user
     * @param user one user object
     */
    public void addUser(UserLocal user) throws NoSuchAlgorithmException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserLocal.NAME,user.getName());
        values.put(UserLocal.PASSWORD, PasswordMD5.generateCode(user.getPassword()));
        values.put(UserLocal.DATE, user.getDate());

        db.insert(UserLocal.TABLE_NAME, null, values);
    }

//    /**
//     * UserLocal database method to get all users
//     * @return  all users object by the form of Map
//     */
//    public HashMap<Long,UserLocal> getAllUser(){
//        HashMap<Long,UserLocal> users = new LinkedHashMap<Long,UserLocal>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM "+UserLocal.TABLE_NAME,null);
//        //Add user to hash map for each row result
//
//        if(cursor.moveToFirst()){
//            do{
//                UserLocal user  = new UserLocal(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
//                users.put(user.getId(),user);
//            }while (cursor.moveToNext());
//        }
//        cursor.close();
//
//        return users;
//    }

    /**
     * Step Database Method to add a step record
     * @param step
     */

    public void addStep(StepLocal step){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(StepLocal.STEPS,step.getSteps());
        values.put(StepLocal.TIME,step.getTime());
        values.put(StepLocal.USER_ID,step.getUid());
        db.insert(StepLocal.TABLE_NAME, null, values);
    }

    /**
     * UserLocal database method to get all users
     * @return  all users object by the form of Map
     */
    public ArrayList<HashMap<String,String>> getAllStepRecods(String name){
        ArrayList<HashMap<String,String>> records = new ArrayList<HashMap<String,String>>();
        SQLiteDatabase db = this.getReadableDatabase();
        long uid = getUid(name);
        Cursor cursor = db.rawQuery("SELECT * FROM " + StepLocal.TABLE_NAME + " WHERE " + StepLocal.USER_ID + "=" + uid +
                " and " + StepLocal.TIME + " like '" + Utility.getDate() + "%'", null);
        //Add record to hash map for each row result

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("time",cursor.getString(1));
                map.put("steps",String.valueOf(cursor.getLong(2)));
                records.add(map);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return records;
    }

//    /**
//     * get the user object by uid
//     * @param uid
//     * @return
//     */
//    public UserLocal getUserLocalByUid(Long uid){
//        UserLocal users = new UserLocal();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM " + UserLocal.TABLE_NAME + " WHERE " + UserLocal.USER_ID + " = " + uid, null);
//
//        UserLocal user  = new UserLocal(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
//        cursor.close();
//        return user;
//    }

    /**
     * 通过用户名取出一条记录
     * @return
     */

    public StepLocal getRecordByUsername(Long uid){
        SQLiteDatabase db = this.getReadableDatabase();

        System.out.println("数据组操作:"+uid);
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + StepLocal.TABLE_NAME + " WHERE " + StepLocal.USER_ID + " = " + uid, null);
        if(cursor1.moveToFirst()) {
            if (cursor1 == null) {
                System.out.println("null");
                return null;
            }
            else {
                System.out.println("!!!");
                StepLocal s = new StepLocal(cursor1.getLong(0),cursor1.getString(1), cursor1.getLong(2), cursor1.getLong(3));
                cursor1.close();
                return s;
            }

        }
        else {
            System.out.println("???");
            cursor1.close();
            return null;
        }
    }


    /**
     * 根据用户名，返回UID
     * @param name
     * @return
     */

    public long getUid(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + UserLocal.TABLE_NAME + " WHERE " + UserLocal.NAME + " = '" + name + "'", null);
        long uid = 0;
        if (cursor.moveToFirst())
            uid = cursor.getLong(0);
        cursor.close();
        return uid;
    }

    /**
     * 得到走的步数
     * @param name
     * @return
     */
    public long getTotalStep(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        long step = 0;
        long uid = getUid(name);
        Cursor cursor = db.rawQuery("SELECT * FROM " + StepLocal.TABLE_NAME + " WHERE " + StepLocal.USER_ID +"="+uid +
                " and "+StepLocal.TIME+" like '"+Utility.getDate()+"%'", null);
        if(cursor.moveToFirst()){
            do{
                step+=cursor.getLong(2);
            }while (cursor.moveToNext());
        }
        cursor.close();
        System.out.println("数据库中当前布书为" + step);
        return step;
    }
//    /**
//     * 跟新本地数据库的Step记录
//     */
//    public void addStep(String username,String Step){
//        long uid = getUid(username);
//        long newStep = Long.parseLong(Step);
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "update "+StepLocal.TABLE_NAME+" set "+StepLocal.STEPS+ " ="+newStep+" where "+StepLocal.USER_ID+"="+uid;
//        db.execSQL(sql);
//    }
}
