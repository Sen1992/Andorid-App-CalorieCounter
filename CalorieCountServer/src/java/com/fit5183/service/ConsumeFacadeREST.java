/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fit5183.service;

import com.fit5183.Consume;
import com.fit5183.ConsumePK;
import com.fit5183.Food;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author 王森
 */
@Stateless
@Path("com.fit5183.consume")
public class ConsumeFacadeREST extends AbstractFacade<Consume> {

    @PersistenceContext(unitName = "Fit5183ServerPU")
    private EntityManager em;

    private ConsumePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;uid=uidValue;fid=fidValue;datetime=datetimeValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.fit5183.ConsumePK key = new com.fit5183.ConsumePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> uid = map.get("uid");
        if (uid != null && !uid.isEmpty()) {
            key.setUid(new java.lang.Integer(uid.get(0)));
        }
        java.util.List<String> fid = map.get("fid");
        if (fid != null && !fid.isEmpty()) {
            key.setFid(new java.lang.Integer(fid.get(0)));
        }
        java.util.List<String> datetime = map.get("datetime");
        if (datetime != null && !datetime.isEmpty()) {
            key.setDatetime(new java.util.Date(datetime.get(0)));
        }
        return key;
    }

    public ConsumeFacadeREST() {
        super(Consume.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consume entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, Consume entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.fit5183.ConsumePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Consume find(@PathParam("id") PathSegment id) {
        com.fit5183.ConsumePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consume> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consume> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public List<Consume> findByUid(@PathParam("uid") int uid) {
        Query query =em.createNamedQuery("Consume.findByUid");
        query.setParameter("uid",uid);
        return query.getResultList();
    }

    @GET
    @Path("findByFid/{fid}")
    @Produces({"application/json"})
    public List<Consume> findByFid(@PathParam("fid") int fid) {
        Query query =em.createNamedQuery("Consume.findByFid");
        query.setParameter("fid",fid);
        return query.getResultList();
    }
    
    @GET
    @Path("findByDatetime/{datetime}")
    @Produces({"application/json"})
    public List<Consume> findByDatetime(@PathParam("datetime") String datetime) {
        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c WHERE c.consumePK.datetime like "+"'%"+datetime+"%'",Consume.class);
        return q.getResultList();
    }
        
    @GET
    @Path("findByPeriod/{fromDatetime}/{endDatetime}")
    @Produces({"application/json"})
    public List<Consume> findByPeriod(@PathParam("fromDatetime") String fromDatetime,@PathParam("endDatetime") String endDatetime) {
        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c WHERE c.consumePK.datetime between '"+fromDatetime+"' AND '"+endDatetime+"'",Consume.class);
        return q.getResultList();
    }
    
    @GET
    @Path("findByNum/{num}")
    @Produces({"application/json"})
    public List<Consume> findByNum(@PathParam("num") int num) {
        Query query =em.createNamedQuery("Consume.findByNum");
        query.setParameter("num",num);
        return query.getResultList();
    }
    
    @GET
    @Path("findByUidANDFid/{uid}/{fid}")
    @Produces({"application/json"})
    public List<Consume> findByUidANDFid(@PathParam("uid") int uid,@PathParam("fid") int fid) {
        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c WHERE c.user.uid = "+uid+" AND c.food.fid = "+fid,Consume.class);
        return q.getResultList();
    }
    @GET
    @Path("findByUidANDDietidAndDatetime/{uid}/{fid}/{datetime}")
    @Produces({"application/json"})
    public List<Consume> findByUidANDDietidAndDatetime(@PathParam("uid") int uid,@PathParam("fid") int fid,@PathParam("datetime") String datetime) {
        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c WHERE c.user.uid = "+uid+" AND c.food.fid = "+fid+" AND c.consumePK.datetime like "+"'%"+datetime+"%'",Consume.class);
        return q.getResultList();
    }
    
    @GET
    @Path("findByUnamedAndDatetime/{uname}/{datetime}")
    @Produces({"application/json"})
    public List<Consume> findByUnameAndDatetime(@PathParam("uname") String uname,@PathParam("datetime") String datetime) {
        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c WHERE c.user.uname = '"+uname+"' AND c.consumePK.datetime like "+"'%"+datetime+"%'",Consume.class);
        return q.getResultList();
    }
    
    @GET
    @Path("findByUsername/{uname}")
    @Produces({"application/json"})
    public List<Consume> findByUsername(@PathParam("uname") String uname) {
        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c WHERE c.user.uname = '"+uname+"'",Consume.class);
        return q.getResultList();
    }
    
    @GET
    @Path("findByJoin")
    @Produces({"application/json"})
    public List<Consume> findByJoin(){
        Query query =em.createNamedQuery("Consume.findByJoin");
        return query.getResultList();
    }
 
    @GET
    @Path("addItem/{username}/{foodname}/{num}/{datetime}")
    @Produces({"application/json"})
    public List<Consume> addItem(@PathParam("username") String username,@PathParam("foodname") String foodname,@PathParam("num") String numString,@PathParam("datetime") String datetime) throws ParseException {
        
        System.out.println(username+":"+foodname+":"+numString+":"+datetime);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
        int num = Integer.parseInt(numString);
        System.out.println("实物数量:"+num);
        Date date = sdf.parse(datetime);
        int uid,fid;
        Query user = em.createQuery("select u from User u where u.username = '"+username+"'");
        Query food = em.createQuery("select f from Food f where f.fname ='"+foodname+"'");
        uid = ((User)user.getSingleResult()).getUid();
        fid = ((Food)food.getSingleResult()).getFid();
        Consume item = new Consume();
        item.setConsumePK(new ConsumePK(uid,fid,date));
        item.setNum(num);
        item.setFood(new Food(fid));
        item.setUser(new User(uid));
        super.create(item);
        TypedQuery<Consume> q = em.createQuery("SELECT c FROM Consume c WHERE c.user.uid = "+uid+" AND c.food.fid = "+fid+" AND c.consumePK.datetime like "+"'%"+datetime+"%' AND c.num = "+num,Consume.class);
        return q.getResultList();
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
