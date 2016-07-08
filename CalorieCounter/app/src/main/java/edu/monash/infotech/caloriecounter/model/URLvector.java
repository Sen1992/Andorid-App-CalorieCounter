package edu.monash.infotech.caloriecounter.model;

/**
 * the class to store the URL to consume RESTful services
 * Created by on 2016/4/17.
 */
public class URLvector {

    public final static String URL_findByUsernameAndPassword = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.user/findByUsernameAndPassword/";
    public final static String URL_findByUsername="http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.user/findByUsername/";
    public final static String URL_userRegister = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.user/userRegister/";
    public final static String URL_getGoal="http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.report/getGoal/";
    public final static String URL_CalorieConsumed = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.report/CalorieConsumed/";
    public final static String URL_setGoal = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.report/setGoal/";
    public final static String URL_findByCategory = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.food/findByCategory/";
//    public final static String URL_generateReport = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.report/generateReport/";
    public final static String URL_updateStep="http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.report/updateStep/";
    public final static String URL_addDiet="http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.consume/addItem/";
    public final static String URL_getInfo = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.report/getInfo/";
    public final static String URL_modifyUser = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.user/userModify/";
    public final static String URL_getPastGoal = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.report/getPastGoal/";
    public final static String URL_getOnPeriod = "http://172.16.120.43:8080/Fit5183Server/webresources/com.fit5183.report/calculateCalorieBurnedAndConsumeOnPeriod/";
    public final static String URL_updateReport = "http://localhost:8080/Fit5183Server/webresources/com.fit5183.report/updateReport/";
}
