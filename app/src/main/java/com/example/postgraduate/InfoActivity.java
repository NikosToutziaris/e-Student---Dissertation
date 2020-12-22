package com.example.postgraduate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity {


    private Button buttonSerres;
    private Button buttonTeiOfSerres;
    private Button buttonInformaticsAndCommunications;
    private Button buttonPostgraduate;
    private Button buttonPrevious5;

    private void findViews(){
        buttonSerres = (Button)findViewById(R.id.buttonSerres);
        buttonTeiOfSerres = (Button)findViewById(R.id.buttonTeiOfSerres);
        buttonInformaticsAndCommunications = (Button)findViewById(R.id.buttonInformaticsAndCommunications);
        buttonPostgraduate = (Button)findViewById(R.id.buttonPostgraduate);
        buttonPrevious5 = (Button)findViewById(R.id.buttonPrevious5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        //get user filters from Intent
        Intent intent = getIntent();
        final String filterUsername = intent.getStringExtra("USERNAME");
        final Boolean student_or_professor = intent.getBooleanExtra("STUDENT_OR_PROFESSOR", true);

        findViews();

        buttonSerres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://el.wikipedia.org/wiki/%CE%A3%CE%AD%CF%81%CF%81%CE%B5%CF%82")));
            }
        });

        buttonTeiOfSerres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.teicm.gr/")));
            }
        });

        buttonInformaticsAndCommunications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://informatics.teicm.gr/")));
            }
        });

        buttonPostgraduate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://informatics.teicm.gr/msc_informatics/")));
            }
        });

        buttonPrevious5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                Intent intent = new Intent(InfoActivity.this, SemesterActivity.class);
                intent.putExtra("USERNAME", filterUsername);
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
