package edu.monash.infotech.caloriecounter.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sen on 2016/4/15.
 */
public class UserLocal implements Parcelable{


    //Database constants
    public static final String TABLE_NAME ="userlocal";
    public static final String USER_ID = "id";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String DATE ="date";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    NAME + " TEXT NOT NULL, " +
                    PASSWORD + " TEXT NOT NULL, " +
                    DATE + " TEXT NOT NULL" +
                    ")";

    // Properties
    private long id;
    private String name;
    private String password;
    private String date;


    // Static method to create Parcelable object (required)
    public static final Creator CREATOR = new Creator() {
        public UserLocal createFromParcel(Parcel in) {
            return new UserLocal(in);
        }
        public UserLocal[] newArray(int size) {
            return new UserLocal[size];
        }
    };
    // Default constructor with required params
    public UserLocal(){}

    public UserLocal(long id){
        this.id = id;
    }
    public UserLocal( long id, String name,String password, String date) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.date = date;
    }
    public UserLocal(String name,String password,String date) {
        this.name = name;
        this.password = password;
        this.date = date;
    }
    // Parcel constructor, reads in values in the order they were written
    public UserLocal(Parcel in) {
        this.date = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.id = in.readLong();
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(date);
    }
}
