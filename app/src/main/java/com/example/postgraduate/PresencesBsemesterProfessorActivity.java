package com.example.postgraduate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.postgraduate.classes.DataStore;
import com.example.postgraduate.classes.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class PresencesBsemesterProfessorActivity extends AppCompatActivity {

    TextView textViewPresencesBsemesterProfessor;
    ListView listViewPresencesBsemesterProfessor;
    Button buttonEntry;
    Button buttonPreviousBsemesterProfessor;

    HashMap<String, Object> user = null;

    private void findViews() {
        textViewPresencesBsemesterProfessor = (TextView) findViewById(R.id.textViewPresencesBsemesterProfessor);
        listViewPresencesBsemesterProfessor = (ListView) findViewById(R.id.listViewPresencesBsemesterProfessor);
        buttonEntry = (Button) findViewById(R.id.buttonEntry);
        buttonPreviousBsemesterProfessor = (Button) findViewById(R.id.buttonPreviousBsemesterProfessor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presences_bsemester_professor);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        final String filterUsername = intent.getStringExtra("USERNAME");
        final Boolean student_or_professor = intent.getBooleanExtra("STUDENT_OR_PROFESSOR", true);
        final int userPosition = intent.getIntExtra(DataStore.KEY_POSITION, 0);
        final String lesson_ID = intent.getStringExtra("LESSON_ID");

        findViews();

        user = DataStore.Users.get(userPosition);

        buttonEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //

            }
        });

        buttonPreviousBsemesterProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                Intent intent = new Intent(PresencesBsemesterProfessorActivity.this, BsemesterActivity.class);
                intent.putExtra("USERNAME", filterUsername);
                intent.putExtra("STUDENT_OR_PROFESSOR", student_or_professor);
                startActivity(intent);
            }
        });

        final ArrayList<HashMap<String, Object>> lessonStudents = DataUtils.getLessonStudents(lesson_ID);
        //COMPLEX OBJECT BINDING
        ListAdapter usersAdapter = new SimpleAdapter(
                this,
                lessonStudents,
                R.layout.activity_presences_bsemester_list_item_professor,
                new String[]{DataStore.KEY_FULLNAME},
                new int[]{R.id.user_item_presences_lesson_professor_2}
        );
        listViewPresencesBsemesterProfessor.setAdapter(usersAdapter);

    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }

}
