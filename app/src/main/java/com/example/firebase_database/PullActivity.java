package com.example.firebase_database;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class PullActivity extends AppCompatActivity {

    FirebaseDatabase fbdb;
    DatabaseReference dbrf;

    Button queryButton1, queryButton2, pushButton, signOutButton;
    EditText IDText;

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;

    FirebaseAuth mAuth;
    FirebaseUser user = null;

    int studID;
    String name;
    String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pull_activity);

        //database stuff
        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference();

        //buttons
        queryButton1=findViewById(R.id.button_q1);
        queryButton2=findViewById(R.id.button_q2);
        pushButton=findViewById(R.id.pushActivityBtn);
        signOutButton=findViewById(R.id.logout);

        //editText
        IDText=findViewById(R.id.userID);

        // listView to array adapter connection
        listView = (ListView)findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public void query1Click(View view) {
        if(true) {
            int studID = Integer.parseInt(IDText.getText().toString());

            DatabaseReference gradeKey = dbrf.child("simpsons/grades/");

            Query query = gradeKey.orderByChild("student_id").equalTo(studID);
            query.addValueEventListener(valueEventListener1);

        } else{
            Toast.makeText(getApplicationContext(),"Please login",Toast.LENGTH_SHORT).show();
        }
    }

    public void query2Click(View view) {
        if (true) {
            studentID = ((EditText) findViewById(R.id.userID)).getText().toString();
            studID = Integer.parseInt(studentID);

            (dbrf.child("simpsons/grades")).addValueEventListener(valueEventListener);

        } else {
            Toast.makeText(getApplicationContext(), "Please login", Toast.LENGTH_SHORT).show();
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    Toast.makeText(getApplicationContext(),Integer.toString(studID) + Integer.toString(grade.student_id),Toast.LENGTH_SHORT).show();
                    if (grade.student_id >= studID) {
                        if (grade.student_id == 123) {
                            name = "Bart";
                        } else if (grade.student_id == 404) {

                            name = "Ralph";
                        } else if (grade.student_id == 456) {

                            name = "Milhouse";
                        } else if (grade.student_id == 888) {

                            name = "Lisa";
                        }
                        arrayList.add(name + ", " + grade.course_name + ", " + grade.grade);
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), "Query Failed", Toast.LENGTH_SHORT).show();
        }
    };

    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                //Toast.makeText(getApplicationContext(),"listening",Toast.LENGTH_SHORT).show();
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Grade grade = snapshot.getValue(Grade.class);
                    arrayList.add(grade.course_name + ", " + grade.grade);
                }

                arrayAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), "Query Failed", Toast.LENGTH_SHORT).show();
        }
    };

    public void LogoutClick(View view) { // Logout and go back to the Sign In Menu (MainActivity)
        mAuth.signOut();
        user = null;
        startActivity(new Intent(PullActivity.this, MainActivity.class)); // not sure If This is needed
        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
    }

    public void PushClick(View view) { // Button click to go to Push Activity
        startActivity(new Intent(PullActivity.this, PushActivity.class));
    }
}


