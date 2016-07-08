/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fit5183.service;

import com.fit5183.Consume;
import com.fit5183.Report;
import com.fit5183.ReportPK;
import com.fit5183.User;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author 王森
 */
@Stateless
@Path("com.fit5183.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "Fit5183ServerPU")
    private EntityManager em;

    private ReportPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;uid=uidValue;datetime=datetimeValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.fit5183.ReportPK key = new com.fit5183.ReportPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> uid = map.get("uid");
        if (uid != null && !uid.isEmpty()) {
            key.setUid(new java.lang.Integer(uid.get(0)));
        }
        java.util.List<String> datetime = map.get("datetime");
        if (datetime != null && !datetime.isEmpty()) {
            key.setDatetime(new java.util.Date(datetime.get(0)));
        }
        return key;
    }

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Report entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.fit5183.ReportPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Report find(@PathParam("id") PathSegment id) {
        com.fit5183.ReportPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("findByUid/{uid}")
    @Produces({"application/json"})
    public List<Report> findByUid(@PathParam("uid") Integer uid) {
        Query query =em.createNamedQuery("Report.findByUid");
        query.setParameter("uid",uid);
        return query.getResultList();
    }
    /**
     * This API is designed for setting Calorie Goal
     * @param username
     * @param goal
     * @return 
     */
        
    @GET
    @Path("setGoal/{username}/{date}/{goal}")
    @Produces({"application/json"})
    public String setGoal(@PathParam("username") String username,@PathParam("date") String date,@PathParam("goal") String goal) {
        Query q = em.createQuery("select r from Report r join User u on r.reportPK.uid = u.uid where r.reportPK.datetime = '"+date+"' and u.username = '"+username+"'");
        Report r = (Report)q.getSingleResult();
        r.setAimCalorie(Double.parseDouble(goal));
        em.persist(r);
        return "{\"status\":\"1\"}";
    }
   
    /**
     * 返货用户名在该天的卡路里记录
     * @param username
     * @param date
     * @return 
     */
    
    @GET
    @Path("getInfo/{username}/{date}")
    @Produces({"application/json"})
    public String getInfo(@PathParam("username") String username,@PathParam("date") String date) throws ParseException {
        double  goal;
        double  consume;
        double burned;
        int     steps;
        double net;
        double remianing;
        Query q = em.createQuery("select r from Report r join User u on r.reportPK.uid = u.uid where r.reportPK.datetime = '"+date+"' and u.username = '"+username+"'");
        Report r = (Report)q.getSingleResult();
        goal = r.getAimCalorie();
        consume = Double.parseDouble(this.calculateCalorieFromFood(username, date));
        steps = r.getTotalSteps();
        burned = steps*Double.parseDouble(this.calculateCaloriePerSteps(username))+Double.parseDouble(this.calculateDailyCalorieBurned(username));
        net = consume - burned;
        remianing = goal - net;
        return "{\"goal\":\""+goal+"\",\"consume\":\""+consume+"\",\"burned\":\""+
                burned+"\",\"steps\":\""+steps+"\",\"remianing\":\""+remianing+"\",\"net\":\""+net+"\"}";
    }
    
    
    
    
    
    /**
     * This API is used to test whether the someone's goal is set on someday
     * @param username
     * @param date
     * @return 
     */
    @GET
    @Path("getGoal/{username}/{date}")
    @Produces({"application/json"})
    public String getGoal(@PathParam("username") String username,@PathParam("date") String date){
        Query q = em.createQuery("select r from Report r join User u on r.reportPK.uid = u.uid where r.reportPK.datetime = '"+date+"' and u.username = '"+username+"'");
        Report r = (Report)q.getSingleResult();
        double goal = r.getAimCalorie();
        return "{\"status\":\""+goal+"\"}";

        
        
    }
    
    @GET
    @Path("findByNameAndDatetime/{uname}/{datetime}")
    @Produces({"application/json"})
    public List<Report> findByNameAndDatetime(@PathParam("uname") String uname,@PathParam("datetime") String datetime) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        Date date = sdf.parse(datetime);
        Query query =em.createNamedQuery("Report.findByNameAndDatetime");
        query.setParameter("uname",uname);
        query.setParameter("datetime",date);
        return query.getResultList();
    }
    
    

    /**
     * 只要用户第一次登陆，就生成一条report记录，来跟踪用户，其他值全部初始化为0
     * @param username
     * @param datetime
     * @return
     * @throws ParseException 
     */  
    @GET
    @Path("generateReport/{username}/{datetime}")
    @Produces({"application/json"})
    public Report generateReport(@PathParam("username") String username,@PathParam("datetime") String datetime) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        Date date = sdf.parse(datetime);
        Report item = new Report(); 
        Query q = em.createQuery("select u from User u where u.username = '"+username+"'");
        int uid = ((User)q.getSingleResult()).getUid();
        item.setReportPK(new ReportPK(uid,date));
        item.setUser(new User(uid)); 
//        item.setAimCalorie();
        item.setTotalSteps(0);
        item.setTotalCalorie(0);
        item.setConsumeCalorie(0);
        item.setRemaining(0);
        super.create(item);
        return item;
    }

    @GET
    @Path("calculateBMR/{uid}")
    @Produces({"application/json"})
    public String calculateBMR(@PathParam("uid") int uid){
        Query query = em.createNamedQuery("User.findByUid");
        query.setParameter("uid", uid);
        DecimalFormat df = new DecimalFormat("0.000");
        User result = (User)query.getSingleResult();
        if(result == null)
            return "The information of uid is empty";
        else if(result.getGender().equals("male"))
                return df.format(13.75*result.getWeight()+5*result.getHeight()-6.76*result.getAge()+66);
            else
                return df.format(9.56*result.getWeight()+1.85*result.getHeight()-4.68*result.getAge()+665);
    }
    /**
     * 计算日常精致消耗
     * @param username
     * @return 
     */
    
    @GET
    @Path("calculateDailyCalorieBurned/{username}")
    @Produces({"application/json"})
    public String calculateDailyCalorieBurned(@PathParam("username") String username){
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username",username);
        User u = (User)query.getSingleResult();
        int uid = u.getUid();
        double BMR = Double.parseDouble(calculateBMR(uid));
        double calorieDailyBurned=0;
        DecimalFormat df = new DecimalFormat("0.0");
        Query q = em.createQuery("select u from User u where u.uid = "+uid);
        User item = (User)q.getSingleResult();
        char i = item.getLevelOfActivity();
        switch(i){
            case '1':
                calorieDailyBurned = BMR*1.2;
                break;
            case '2':
                calorieDailyBurned = BMR*1.375;
                break;
            case '3':
                calorieDailyBurned = BMR*1.55;
                break;
            case '4':
                calorieDailyBurned = BMR*1.725;
                break;
            case '5':
                calorieDailyBurned = BMR*1.9;
                break;
        }
        return df.format(calorieDailyBurned);
    }
    
    /**
     * 根据用户名计算每一个人的每一步消耗的卡路里
     * @param uid
     * @return 返回的是一个String类型
     */
    
    @GET
    @Path("calculateCaloriePerSteps/{username}")
    @Produces({"application/json"})
    public String calculateCaloriePerSteps(@PathParam("username") String username){
        DecimalFormat df = new DecimalFormat("0.0");
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username",username);
        User u = (User)query.getSingleResult();
        double average = u.getWeight()*2.2046*0.49/u.getStepPerMile();
        return df.format(average);
    }
    /**
     * This API is designed for returning the Calorie Consumed from food
     * @param uid
     * @param datetime
     * @return 
     */
    @GET
    @Path("CalorieConsumed/{username}/{datetime}")
    @Produces({"application/json"})
    public String calculateCalorieFromFood(@PathParam("username") String username,@PathParam("datetime") String datetime) throws ParseException{
        DecimalFormat df = new DecimalFormat("0.0");
        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c JOIN User u on c.consumePK.uid = u.uid WHERE u.username = '"+username+"' AND "
                + "c.consumePK.datetime like "+"'%"+datetime+"%'",Consume.class);
        double totalCalorie = 0;
        List <Consume> result = q.getResultList();
        for(Consume r :result){
 //           System.out.println(r.getUser().getUname()+" "+r.getConsumePK().getDatetime()+" "+r.getFood().getFname()+":"+r.getFood().getCalorie()+":"+r.getNum());
            totalCalorie+=r.getNum()*r.getFood().getCalorie();
        }
        return df.format(totalCalorie);
    }
//    @GET
//    @Path("calculateCalorieFromFood/{uid}/{datetime}")
//    @Produces({"application/json"})
//    public String calculateCalorieFromFood(@PathParam("uid") int uid,@PathParam("datetime") String datetime){
//        DecimalFormat df = new DecimalFormat("0.000");
//        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c WHERE c.user.uid = "+uid+" AND "
//                + "c.consumePK.datetime like "+"'%"+datetime+"%'",Consume.class);
//        double totalCalorie = 0;
//        List <Consume> result = q.getResultList();
//        for(Consume r :result){
// //           System.out.println(r.getUser().getUname()+" "+r.getConsumePK().getDatetime()+" "+r.getFood().getFname()+":"+r.getFood().getCalorie()+":"+r.getNum());
//            totalCalorie+=r.getNum()*r.getFood().getCalorie();
//        }
//        return df.format(totalCalorie);
//    }
    
    @GET
    @Path("calculateCalorieBurnedAndConsumeOnday/{uid}/{datetime}")
    @Produces({"application/json"})
    public String calculateCalorieBurnedAndConsumeOnDay(@PathParam("uid") int uid,@PathParam("datetime") String datetime){
        DecimalFormat df = new DecimalFormat("0.0");
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.reportPK.uid = "+uid+" AND r.reportPK.datetime like "+"'%"+datetime+"%'",Report.class);
        Report r = q.getSingleResult();
        String result = "{totalCalorieBurned :"+df.format(r.getTotalCalorie())+"} {calorieConsume:"+df.format(r.getConsumeCalorie())+"}";
        return result;
    }
    @GET
    @Path("calculateCalorieBurnedAndConsumeOnPeriod/{username}/{fromdate}/{todate}")
    @Produces({"application/json"})
    public String calculateCalorieBurnedAndConsumeOnPeriod(@PathParam("username") String username,@PathParam("fromdate") String fromdate,@PathParam("todate") String todate) throws ParseException{
        DecimalFormat df = new DecimalFormat("0.0");
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        String json = "{\"result\":[";
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r join User u on r.reportPK.uid = u.uid where u.username = '"+username+"' AND r.reportPK.datetime between '"+fromdate+"' AND '"+todate+"'",Report.class);
        List<Report> l =q.getResultList();
        double totalCalorieBurned= 0;
        double calorieConsume = 0;
        for(int i = 0;i<l.size();i++){
            json+="{\"date\":\""+sdf.format(l.get(i).getReportPK().getDatetime())+"\",\"cosume\":\""+String.valueOf(l.get(i).getConsumeCalorie())+
                    "\",\"burned\":\""+String.valueOf(l.get(i).getTotalCalorie())+"\"},";
        }
        json = json.substring(0,json.length()-1);
        json +="]}";
        return json;
    }
    /**
     * 根据用户名查找report表，来更新步数
     * @param username
     * @param date
     * @return 
     */
    @GET
    @Path("updateStep/{username}/{datetime}/{steps}")
    @Produces({"application/json"})
    public String updateStep(@PathParam("username") String username,@PathParam("datetime") String date,@PathParam("steps") String steps){
         Query q = em.createQuery("select r from Report r join User u on r.reportPK.uid = u.uid where r.reportPK.datetime = '"+
                 date+"' and u.username = '"+username+"'");
         List<Report> result = q.getResultList();
         if(result.isEmpty()){
             return "{\"status\":\"0\"}";
         }
         else{
             int Step = Integer.parseInt(steps);
             result.get(0).setTotalSteps(Step);
             return "{\"status\":\"1\"}";
         }
    }
    
    
    /**
     * 得到最近七天的卡路里目标
     * @param username
     * @return 
     */
    @GET
    @Path("getPastGoal/{username}")
    @Produces({"application/json"})
    public String getPastGoal(@PathParam("username") String username){
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        String json = "{\"result\":[";
        Query q = em.createQuery("select r from Report r join User u on r.reportPK.uid = u.uid where u.username = '"+username+"'");
        List<Report> result = q.getResultList();
        int start;
        int size = result.size();
        if(size<=7)
            start = 0 ;
        else
            start = size - 7;
        for (int i=start;i<size;i++)
             json+="{\"date\":\""+sdf.format(result.get(i).getReportPK().getDatetime())+"\",\"goal\":\""+String.valueOf(result.get(i).getAimCalorie())+"\"},";
        json = json.substring(0,json.length()-1);
        json +="]}";
        return json;
    }
    
    
    
    @GET
    @Path("updateReport/{username}/{datetime}/{burned}/{consume}/{remaining}")
    @Produces({"application/json"})
    public String updateReport(@PathParam("username") String username,@PathParam("datetime") 
            String datetime,@PathParam("burned") String burned,@PathParam("consume") String consume,@PathParam("remaining")
                    String remaining) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        Date date = sdf.parse(datetime);
        
        Query q = em.createQuery("select r from Report r join User u on r.reportPK.uid = u.uid where u.username = '"+username+"' and r.reportPK.datetime ='"+
                datetime+"'");
        Report item = (Report)q.getSingleResult();
        item.setTotalCalorie(Double.parseDouble(burned));
        item.setConsumeCalorie(Double.parseDouble(consume));
        item.setRemaining(Double.parseDouble(remaining));
        em.persist(item);
        return "";
    }
    
    
    
    
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
