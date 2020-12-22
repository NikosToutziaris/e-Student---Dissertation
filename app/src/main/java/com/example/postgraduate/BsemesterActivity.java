package com.example.postgraduate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.postgraduate.classes.DataStore;
import com.example.postgraduate.classes.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BsemesterActivity extends AppCompatActivity {

    TextView textViewBsemester;
    ListView listViewBsemester;
    Button buttonPrevious3;

    private void findViews(){
        textViewBsemester = (TextView)findViewById(R.id.textViewBsemester);
        listViewBsemester = (ListView)findViewById(R.id.listViewBsemester);
        buttonPrevious3 = (Button) findViewById(R.id.buttonPrevious3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsemester);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        final String filterUsername = intent.getStringExtra("USERNAME");
        final Boolean student_or_professor = intent.getBooleanExtra("STUDENT_OR_PROFESSOR", true);

        findViews();

        HashMap<String, Object> current_user = DataUtils.getUser(filterUsername);
        ArrayList<String> userLessonIDs = (ArrayList<String>) current_user.get(DataStore.KEY_LESSONS_IDS);
        final ArrayList<Map<String, Object>> lessons = DataUtils.getSemesterLessons("B", userLessonIDs);

        if (student_or_professor) {
            //COMPLEX OBJECT BINDING
            ListAdapter usersAdapter = new SimpleAdapter(
                    this,
                    lessons,
                    R.layout.activity_bsemester_list_item_student,
                    new String[]{DataStore.KEY_LESSON_NAME},
                    new int[]{R.id.user_item_lesson_student_5}
            );
            listViewBsemester.setAdapter(usersAdapter);
        }
        else
        {
            //COMPLEX OBJECT BINDING
            ListAdapter usersAdapter = new SimpleAdapter(
                    this,
                    lessons,
                    R.layout.activity_bsemester_list_item_professor,
                    new String[]{DataStore.KEY_LESSON_NAME},
                    new int[]{R.id.user_item_lesson_professor_2}
            );
            listViewBsemester.setAdapter(usersAdapter);
        }

        buttonPrevious3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                Intent intent = new Intent(BsemesterActivity.this, SemesterActivity.class);
                intent.putExtra("USERNAME", filterUsername);
                intent.putExtra("STUDENT_OR_PROFESSOR", student_or_professor);
                startActivity(intent);
            }
        });

        final Class activityClass;
        if (student_or_professor) {
            activityClass = PresencesBsemesterStudentActivity.class;
        } else {
           activityClass = PresencesBsemesterProfessorActivity.class;
        }

        listViewBsemester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BsemesterActivity.this, activityClass);
                intent.putExtra(DataStore.KEY_POSITION, position);
                intent.putExtra("USERNAME",filterUsername);
                intent.putExtra("STUDENT_OR_PROFESSOR", student_or_professor);
                intent.putExtra("LESSON_ID", String.valueOf(lessons.get(position).get(DataStore.KEY_ID)));
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }
}
