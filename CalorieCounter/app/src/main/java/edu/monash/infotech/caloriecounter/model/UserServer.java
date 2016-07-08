package edu.monash.infotech.caloriecounter.model;

import java.io.Serializable;

/**
 * Created by sen on 2016/4/16.
 */
public class UserServer {
    private String name;
    private int age;
    private int step_per_mile;
    private String gender;
    private double height;
    private double weight;
    private char levelActivity;
    private String username;
    private String password;

    public UserServer() {
    }

    public UserServer(int step_per_mile,String password, String username, char levelActivity, double weight, String gender, double height, int age, String name) {
        this.step_per_mile = step_per_mile;
        this.password = password;
        this.username = username;
        this.levelActivity = levelActivity;
        this.weight = weight;
        this.gender = gender;
        this.height = height;
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public char getLevelActivity() {
        return levelActivity;
    }

    public void setLevelActivity(char levelActivity) {
        this.levelActivity = levelActivity;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public int getStep_per_mile() {
        return step_per_mile;
    }

    public void setStep_per_mile(int step_per_mile) {
        this.step_per_mile = step_per_mile;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
