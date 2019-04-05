package com.example.firebase_database;

import android.content.Intent;
import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class PushActivity extends AppCompatActivity {

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;
    int radioID = R.id.Milhouse;
    int dbID = 456;

    private FirebaseUser user = null;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_activity);

        //Firebase database to check fot auth.
        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();
        mAuth= FirebaseAuth.getInstance();

        //editTexts
        EditText ID = findViewById(R.id.CourseID);
        EditText name = findViewById(R.id.Subject);
        EditText grade = findViewById(R.id.Grade);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
    }

    public void radioClick(View view) {
        radioID = view.getId();
        if(radioID==R.id.Bart){dbID = 123;}
        if(radioID==R.id.Ralph){dbID = 404;}
        if(radioID==R.id.Milhouse){dbID = 456;}
        if(radioID==R.id.Lisa){dbID = 888;}
    }

    public void PushClick(View view) {

        if (user != null) {
            //Get course ID number
            EditText courseID = findViewById(R.id.CourseID);
            String course_ID = courseID.getText().toString();
            courseID.getText().clear();

            //Get Subject name
            EditText courseName = findViewById(R.id.Subject);
            String course_name = courseName.getText().toString();
            courseName.getText().clear();

            //Get Grade of the Course
            EditText courseGrade = findViewById(R.id.Grade);
            String course_grade = courseGrade.getText().toString();
            courseGrade.getText().clear();

            Random rand = new Random();
            String grade_id = String.valueOf(rand.nextInt(10000));

            /*
            * Setting values to update the database with a new value
            * course_id, name, grade updated by which radio button it is on at the time
            */
            DatabaseReference passID = dbrf.child("simpsons/grades/" + grade_id + "/course_id");
            passID.setValue(Integer.parseInt(course_ID));

            DatabaseReference passName = dbrf.child("simpsons/grades/" + grade_id + "/course_name");
            passName.setValue(course_name);

            DatabaseReference passGrade = dbrf.child("simpsons/grades/" + grade_id + "/grade");
            passGrade.setValue(course_grade);

            DatabaseReference passStudentID = dbrf.child("simpsons/grades/" + grade_id + "/student_id");
            passStudentID.setValue(dbID);


        }
        else {
            Toast.makeText(getApplicationContext(),"SOMETHING WENT WRONG...... ",Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(PushActivity.this, PullActivity.class)); // Intent to go back to PullActivity, after Push is pressed
    }

}