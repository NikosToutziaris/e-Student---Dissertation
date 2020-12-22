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

public class AsemesterActivity extends AppCompatActivity {

    TextView textViewAsemester;
    ListView listViewAsemester;
    private Button buttonPrevious2;

    private void findViews(){
        textViewAsemester = (TextView) findViewById(R.id.textViewAsemester);
        listViewAsemester = (ListView) findViewById(R.id.listViewAsemester);
        buttonPrevious2 = (Button) findViewById(R.id.buttonPrevious2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asemester);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        final String filterUsername = intent.getStringExtra("USERNAME");
        final Boolean student_or_professor = intent.getBooleanExtra("STUDENT_OR_PROFESSOR", true);

        findViews();

        HashMap<String, Object> current_user = DataUtils.getUser(filterUsername);
        ArrayList<String> userLessonIDs = (ArrayList<String>) current_user.get(DataStore.KEY_LESSONS_IDS);
        final ArrayList<Map<String, Object>> lessons = DataUtils.getSemesterLessons("A", userLessonIDs);

        ListAdapter lessonsAdapter;
        if (student_or_professor) {
            //COMPLEX OBJECT BINDING
            lessonsAdapter = new SimpleAdapter(
                    this,
                    lessons,
                    R.layout.activity_asemester_list_item_student,
                    new String[]{DataStore.KEY_LESSON_NAME},
                    new int[]{R.id.user_item_lesson_student_1}
            );
        } else {
            //COMPLEX OBJECT BINDING
            lessonsAdapter = new SimpleAdapter(
                    this,
                    lessons,
                    R.layout.activity_asemester_list_item_professor,
                    new String[]{DataStore.KEY_LESSON_NAME},
                    new int[]{R.id.user_item_lesson_professor_1}
            );
        }
        listViewAsemester.setAdapter(lessonsAdapter);

        buttonPrevious2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //

                Intent intent = new Intent(AsemesterActivity.this, SemesterActivity.class);
                intent.putExtra("USERNAME", filterUsername);
                intent.putExtra("STUDENT_OR_PROFESSOR", student_or_professor);
                startActivity(intent);

            }
        });

        final Class activityClass;
        if (student_or_professor) {
            activityClass = PresencesAsemesterStudentActivity.class;
        } else {
            activityClass = PresencesAsemesterProfessorActivity.class;
        }

        listViewAsemester.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AsemesterActivity.this, activityClass);
                intent.putExtra(DataStore.KEY_POSITION, position);
                intent.putExtra("USERNAME",filterUsername);
                intent.putExtra("STUDENT_OR_PROFESSOR", student_or_professor);
                intent.putExtra("LESSON_ID", String.valueOf(lessons.get(position).get(DataStore.KEY_ID)));
                startActivity(intent);
            }
        });

    }
}
