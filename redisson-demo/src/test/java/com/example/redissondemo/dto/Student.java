package com.example.redissondemo.dto;


import java.util.List;

public class Student {

    public Student(){}

    public Student(String name, int age, String city, List<Integer> marks) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.marks = marks;
    }

    private String name;
    private int age;
    private String city;
    private List<Integer> marks;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }
}
