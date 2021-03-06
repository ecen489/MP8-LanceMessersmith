package com.example.firebase_database;

import android.provider.MediaStore;

import java.io.Serializable;


public class Grade implements Serializable {

    int course_id;
    String course_name;
    String grade;
    int student_id;

    public Grade() {} // blank constructor to read in the values

    public Grade(int course, String name, String grad, int ID){ // initialize variables and strings

        course_id = course;
        course_name = name;
        grade = grad;
        student_id = ID;

    }
    /*public String toString() {  // used to get the values for Query 1.
        String result = course_name + " " + grade;
        return  result;
    } */

    public int getcourse_id()       { return course_id; }

    public String getcourse_name()  { return course_name; }

    public String getgrade() { return grade; }

    public int getstudent_id() { return student_id;}

}