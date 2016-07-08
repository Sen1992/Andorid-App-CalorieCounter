package edu.monash.infotech.caloriecounter.model;

/**
 * Created by sen on 2016/4/15.
 */
public class StepLocal{

    // Database constants
    public static final String TABLE_NAME = "stepsLocal";
    public static final String RECORD_ID = "id";
    public static final String TIME="time";
    public static final String STEPS="steps";
    public static final String USER_ID="user_id";

    public static final String CREATE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                    RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    TIME + " TEXT NOT NULL, " +
                    STEPS + " INTEGER NOT NULL, " +
                    USER_ID+" INTEGER NOT NULL, "+
                    "FOREIGN KEY("+USER_ID+") REFERENCES "+ UserLocal.TABLE_NAME+"("+ UserLocal.USER_ID+
                    ")"
            +")";

    private long sid;
    private String time;
    private long steps;
    private long uid;

    public StepLocal(String time,long steps, long uid){
        this.uid = uid;
        this.steps = steps;
        this.time = time;
    }

    public StepLocal(long sid,String time,long steps, long uid){
        this.uid = uid;
        this.sid = sid;
        this.steps = steps;
        this.time = time;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getSteps() {
        return steps;
    }

    public void setSteps(long steps) {
        this.steps = steps;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
