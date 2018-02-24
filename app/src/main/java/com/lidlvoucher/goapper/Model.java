package com.lidlvoucher.goapper;


public class Model {
    private int id;
    private String name;
    private String lastName;
    private int age;
    private int height;
    private String combination;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public String getCombination() {
        return combination;
    }
}
