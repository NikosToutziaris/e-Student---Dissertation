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

public class PresencesAsemesterStudentActivity extends AppCompatActivity {

    TextView textViewPresencesAsemesterStudent;
    ListView listViewPresencesAsemesterStudent;
    Button buttonPrevious6;

    HashMap<String, Object> user = null;

    private void findViews() {
        textViewPresencesAsemesterStudent = (TextView) findViewById(R.id.textViewPresencesAsemesterStudent);
        listViewPresencesAsemesterStudent = (ListView) findViewById(R.id.listViewPresencesAsemesterStudent);
        buttonPrevious6 = (Button) findViewById(R.id.buttonPrevious6);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presences_asemester_student);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        findViews();

        Intent intent = getIntent();
        final String filterUsername = intent.getStringExtra("USERNAME");
        HashMap<String, Object> studentUser = DataUtils.getUser(filterUsername);

        //Get Lecture Dates of the given lesson
        final String lessonID = intent.getStringExtra("LESSON_ID");
        HashMap<String, Object> lesson = DataUtils.getLesson(lessonID);
        ArrayList<String> LectureDates = (ArrayList<String>) lesson.get(DataStore.KEY_LECTURE_DATES);

        final ArrayList<HashMap<String, Object>> dates = new ArrayList<>();
        for (String lectureDate : LectureDates) {
            HashMap<String, Object> date = new HashMap<>();
            date.put("DATE", lectureDate);
            dates.add(date);
        }

        ListAdapter usersAdapter = new SimpleAdapter(
                this,
                dates,
                R.layout.activity_presences_asemester_list_item_student,
                new String[]{"DATE"},
                new int[]{R.id.textViewPesencesAsemesterListItemStudent_2}
        );
        listViewPresencesAsemesterStudent.setAdapter(usersAdapter);

        buttonPrevious6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                Intent intent = new Intent(PresencesAsemesterStudentActivity.this, AsemesterActivity.class);
                intent.putExtra("USERNAME", filterUsername);
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
