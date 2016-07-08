/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fit5183.service;

import com.fit5183.Food;
import java.util.List;
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

/**
 *
 * @author 王森
 */
@Stateless
@Path("com.fit5183.food")
public class FoodFacadeREST extends AbstractFacade<Food> {

    @PersistenceContext(unitName = "Fit5183ServerPU")
    private EntityManager em;

    public FoodFacadeREST() {
        super(Food.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Food entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Food entity) {
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
    public Food find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Food> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByFid/{fid}")
    @Produces({"application/json"})
    public List<Food> findByFid(@PathParam("fid") Integer fid) {
        Query query =em.createNamedQuery("Food.findByFid");
        query.setParameter("fid",fid);
        return query.getResultList();
    }
    
    @GET
    @Path("findByCategory/{category}")
    @Produces({"application/json"})
    public List<Food> findByCategory(@PathParam("category") String category) {
        Query query =em.createNamedQuery("Food.findByCategory");
        query.setParameter("category",category);
        return query.getResultList();
    }
    
    
    @GET
    @Path("findByFname/{fname}")
    @Produces({"application/json"})
    public List<Food> findByFname(@PathParam("fname") String fname) {
        TypedQuery<Food> q = em.createQuery("SELECT f FROM Food f WHERE f.fname like "+"'%"+fname+"%'",Food.class);
        return q.getResultList();
    }
    
    @GET
    @Path("findByCalorie/{calorie}")
    @Produces({"application/json"})
    public List<Food> findByCalorie(@PathParam("calorie") int calorie){
        Query query = em.createNamedQuery("Food.findByCalorie");
        query.setParameter("calorie", calorie);
        return query.getResultList();
    }
    
    @GET
    @Path("findByFat/{fat}")
    @Produces({"application/json"})
    public List<Food> findByFat(@PathParam("fat") int fat){
        Query query = em.createNamedQuery("Food.findByFat");
        query.setParameter("fat", fat);
        return query.getResultList();
    }
    
    @GET
    @Path("findByServing/{serving}")
    @Produces({"application/json"})
    public List<Food> findByServing(@PathParam("serving") String serving){
        Query query = em.createNamedQuery("Food.findByServing");
        query.setParameter("serving", serving);
        return query.getResultList();
    }
    
    @GET
    @Path("addFood/{fname}/{calorie}/{fat}/{serving}")
    @Produces({"application/json"})
    public List<Food> addFood(@PathParam("fname") String fname,@PathParam("calorie") int calorie,@PathParam("fat") 
            int fat,@PathParam("serving") String serving){
        Food item = new Food();
        item.setFname(fname);
        item.setCalorie(calorie);
        item.setFat(fat);
        item.setServing(serving);
        super.create(item);
        TypedQuery<Food> q = em.createQuery("SELECT f FROM Food f",Food.class);
        return q.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
