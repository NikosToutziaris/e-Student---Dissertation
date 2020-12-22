package com.example.postgraduate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.postgraduate.classes.DataStore;
import com.example.postgraduate.classes.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class PresencesAsemesterProfessorActivity extends AppCompatActivity {

    TextView textViewPresencesAsemesterProfessor;
    ListView listViewPresencesAsemesterProfessor;
    Button buttonInsertion;
    Button buttonPrevious9;

    HashMap<String, Object> user = null;

    private void findViews(){
        textViewPresencesAsemesterProfessor = (TextView) findViewById(R.id.textViewPresencesAsemesterProfessor);
        listViewPresencesAsemesterProfessor = (ListView) findViewById(R.id.listViewPresencesAsemesterProfessor);
        buttonInsertion = (Button) findViewById(R.id.buttonInsertion);
        buttonPrevious9 = (Button) findViewById(R.id.buttonPrevious9);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presences_asemester_professor);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        final int userPosition = intent.getIntExtra(DataStore.KEY_POSITION, 0);
        final Boolean student_or_professor = intent.getBooleanExtra("STUDENT_OR_PROFESSOR", true);
        final String filterUsername = intent.getStringExtra("USERNAME");
        final String lesson_ID = intent.getStringExtra("LESSON_ID");

        findViews();

        user = DataStore.Users.get(userPosition);

        buttonInsertion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //

            }
        });

        buttonPrevious9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                Intent intent = new Intent(PresencesAsemesterProfessorActivity.this, AsemesterActivity.class);
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
                R.layout.activity_presences_asemester_list_item_professor,
                new String[]{DataStore.KEY_FULLNAME},
                new int[]{R.id.user_item_presences_lesson_professor_1}
        );
        listViewPresencesAsemesterProfessor.setAdapter(usersAdapter);

    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }

}
