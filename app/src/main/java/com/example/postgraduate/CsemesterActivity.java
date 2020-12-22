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

public class CsemesterActivity extends AppCompatActivity {

    TextView textViewCsemester;
    ListView listViewCsemester;
    private Button buttonPrevious4;

    private void findViews(){
        textViewCsemester = (TextView) findViewById(R.id.textViewCsemester);
        listViewCsemester = (ListView) findViewById(R.id.listViewCsemester);
        buttonPrevious4 = (Button) findViewById(R.id.buttonPrevious4);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csemester);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        final String username = intent.getStringExtra("USERNAME");
        final Boolean student_or_professor = intent.getBooleanExtra("STUDENT_OR_PROFESSOR", true);

        findViews();

        ArrayList<HashMap<String, Object>> dissertations;

        // In case of a student, show all dissertations
        if (student_or_professor) {
            dissertations = DataStore.Dissertations;
        }
        // In case of a professor, find the dissertations that the user has and only show them
        else {
            dissertations = new ArrayList<>();
            HashMap<String, Object> user = DataUtils.getUser(username);
            ArrayList<String> dissertationIDs = (ArrayList<String>) user.get(DataStore.KEY_DISSERTATIONS_IDS);
            for (int i = 0; i < dissertationIDs.size(); i++) {
                String dissertationID = dissertationIDs.get(i);
                for (int j = 0; j < DataStore.Dissertations.size(); j++) {
                    HashMap<String, Object> currentDissertation = DataStore.Dissertations.get(j);
                    String currentDissertationID = String.valueOf(currentDissertation.get(DataStore.KEY_ID));
                    if (dissertationID.equals(currentDissertationID)) {
                        dissertations.add(currentDissertation);
                    }
                }
            }
        }

        //COMPLEX OBJECT BINDING
        ListAdapter usersAdapter = new SimpleAdapter(
                this,
                dissertations,
                R.layout.activity_csemester_list_item_student,
                new String[]{DataStore.KEY_DISSERTATION},
                new int[]{R.id.user_item_dissertation_1}
        );
        listViewCsemester.setAdapter(usersAdapter);

        buttonPrevious4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                Intent intent = new Intent(CsemesterActivity.this, SemesterActivity.class);
                intent.putExtra("USERNAME", username);
                intent.putExtra("STUDENT_OR_PROFESSOR", student_or_professor);
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
