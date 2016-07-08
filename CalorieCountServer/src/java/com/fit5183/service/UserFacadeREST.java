/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fit5183.service;
import com.fit5183.Report;
import com.fit5183.ReportPK;
import com.fit5183.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
import org.json.JSONObject;


/**
 *
 * @author 王森
 */
@Stateless
@Path("com.fit5183.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "Fit5183ServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByUid/{uid}")
    @Produces({"application/json"})
    public List<User> findByUid(@PathParam("uid") Integer uid) {
        Query query =em.createNamedQuery("User.findByUid");
        query.setParameter("uid",uid);
        return query.getResultList();
    }
 
    @GET
    @Path("findByUname/{uname}")
    @Produces({"application/json"})
    public List<User> findByUname(@PathParam("uname") String uname) {
        Query query =em.createNamedQuery("User.findByUname");
        query.setParameter("uname",uname);
        return query.getResultList();
    }
    
    @GET
    @Path("findByAge/{age}")
    @Produces({"application/json"})
    public List<User> findByAge(@PathParam("age") int age){
        Query query = em.createNamedQuery("User.findByAge");
        query.setParameter("age",age);
        return query.getResultList();
    }
    
    @GET
    @Path("findByHeight/{height}")
    @Produces({"application/json"})
    public List<User> findByHeight(@PathParam("height") int height){
        Query query = em.createNamedQuery("User.findByHeight");
        query.setParameter("height", height);
        return query.getResultList();
    }
    
    @GET
    @Path("findByWeight/{weight}")
    @Produces({"application/json"})
    public List<User> findByWeight(@PathParam("weight") int weight){
        Query query = em.createNamedQuery("User.findByWeight");
        query.setParameter("weight", weight);
        return query.getResultList();
    }
    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<User> findByGender(@PathParam("gender") String gender) {
        Query query =em.createNamedQuery("User.findByGender");
        query.setParameter("gender",gender);
        return query.getResultList();
    }
   
    @GET
    @Path("findByLevelOfActivity/{levelOfActivity}")
    @Produces({"application/json"})
    public List<User> findByLevelOfActivity(@PathParam("levelOfActivity") Character levelofactivity) {
        Query query =em.createNamedQuery("User.findByLevelOfActivity");
        query.setParameter("levelOfActivity",levelofactivity);
        return query.getResultList();
    }

    @GET
    @Path("findByStepPerMile/{stepPerMile}")
    @Produces({"application/json"})
    public List<User> findByStepPerMile(@PathParam("stepPerMile") int stepPerMile){
        Query query = em.createNamedQuery("User.findByStepPerMile");
        query.setParameter("stepPerMile",stepPerMile);
        return query.getResultList();
    }
    /**
     * This API is used to test whether the username is only one
     * @param username
     * @return 
     */
    @GET
    @Path("findByUsername/{username}")
    @Produces({"application/json"})
    public List<User> findByUsername(@PathParam("username") String username){
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        return query.getResultList();
    }
 
    @GET
    @Path("findByPassword/{password}")
    @Produces({"application/json"})
    public List<User> findByPassword(@PathParam("password") String password){
        Query query = em.createNamedQuery("User.findByPassword");
        query.setParameter("password", password);
        return query.getResultList();
    }
    /**
     * get user information by username and password
     * @param username
     * @param password
     * @return 
     */
    @GET
    @Path("findByUsernameAndPassword/{username}/{password}/{datetime}")
    @Produces({"application/json"})
    public List<User> findByUsernameAndPassword(@PathParam("username") String username,@PathParam("password") String password,@PathParam("datetime") String datetime) throws ParseException{
        int flag =1;
        Query query = em.createNamedQuery("User.findByUsernameAndPassword");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> result = query.getResultList();
        //判断用户名密码是否正确
        if(result.isEmpty())
            flag = 0;
        if(flag ==1){
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
            Date date = sdf.parse(datetime);
            Report item = new Report(); 
            Query q = em.createQuery("select u from User u where u.username = '"+username+"'");
            int uid = ((User)q.getSingleResult()).getUid();
            System.out.println("登陆用户的ID"+uid);
            //判用户是否是第一次登陆，如果是则置空一张report的表
            Query q1 = em.createQuery("select r from Report r where r.reportPK.uid = "+uid+" and r.reportPK.datetime = '"+datetime+"'");
            List<Report> r1 = q1.getResultList();
            if(r1.isEmpty()){//说明第一次登陆，则置空report表
                System.out.println("用户第一次登陆");
                item.setReportPK(new ReportPK(uid,date));
                item.setUser(new User(uid)); 
                item.setTotalSteps(0);
                item.setAimCalorie(0);
                item.setTotalCalorie(0);
                item.setConsumeCalorie(0);
                item.setRemaining(0);
                em.persist(item);
            }
            else
                System.out.println("用户不是第一次登陆");
          }
        return query.getResultList();
    }
    
    @GET
    @Path("userRegister/{uname}/{age}/{height}/{weight}/{gender}/{levelOfActivity}/{stepPerMile}/{username}/{password}")
    @Produces({"application/json"})
    public String userRegister(@PathParam("uname") String uname,@PathParam("age") int age,@PathParam("height") 
            double height,@PathParam("weight") double weight, @PathParam("gender") String gender,@PathParam("levelOfActivity") 
                    Character levelOfActivity,@PathParam("stepPerMile") int stepPerMile,@PathParam("username") String username,
                    @PathParam("password") String password){
        User item = new User();
        item.setUname(uname);
        item.setAge(age);
        item.setHeight(height);
        item.setWeight(weight);
        item.setGender(gender);
        item.setLevelOfActivity(levelOfActivity);
        item.setStepPerMile(stepPerMile);
        item.setUsername(username);
        item.setPassword(password);
        em.persist(item);
        return "[status:1]";
    }
    
    @GET
    @Path("userModify/{uname}/{age}/{height}/{weight}/{gender}/{levelOfActivity}/{stepPerMile}/{username}/{password}")
    @Produces({"application/json"})
    public String userModify(@PathParam("uname") String uname,@PathParam("age") String age,@PathParam("height") 
            double height,@PathParam("weight") String weight, @PathParam("gender") String gender,@PathParam("levelOfActivity") 
                    String levelOfActivity,@PathParam("stepPerMile") String stepPerMile,@PathParam("username") String username,
                    @PathParam("password") String password){
        
        Query query = em.createNamedQuery("User.findByUsername");
        query.setParameter("username", username);
        User u = (User)query.getSingleResult();
        u.setUname(uname);
        u.setAge(Integer.parseInt(age));
        u.setGender(gender);
        u.setHeight(height);
        u.setWeight(Double.parseDouble(weight));
        u.setStepPerMile(Integer.parseInt(stepPerMile));
        u.setLevelOfActivity(levelOfActivity.charAt(0));
        u.setPassword(password);
        em.persist(u);
        
        
        
        return "";
    }
    
    
    
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
