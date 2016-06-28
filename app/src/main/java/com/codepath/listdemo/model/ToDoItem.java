package com.codepath.listdemo.model;

public class ToDoItem {

    private int id;
    private String name;
    private String date;
    private String priority;

    public ToDoItem(){}

    public ToDoItem(int id,String name,String date,String priority){

        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public ToDoItem(String name,String date,String priority){

        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
