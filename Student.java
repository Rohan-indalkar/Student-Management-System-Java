package com.Studd;
import java.io.Serializable;

public class Student implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    // getters & setters
    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }
    
    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    
    public void setAge(int age) { this.age = age; }
    
    public String getCourse() { return course; }
    
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Age: %d | Course: %s", id, name, age, course);
    }
    public static void main(String[] args)
    {
    	
    }
}
