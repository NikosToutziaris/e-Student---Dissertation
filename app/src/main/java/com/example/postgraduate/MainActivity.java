package com.example.postgraduate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.postgraduate.classes.DataStore;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText textUsername;
    private EditText textPassword;
    private Button buttonLogin;

    private void findViews(){
        textUsername = (EditText)findViewById(R.id.editTextUsername);
        textPassword = (EditText)findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Animation when this Activity appears
        overridePendingTransition(R.anim.pull_in_from_right, R.anim.hold);

        findViews();

        // hide password
        textPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

        //get user filters from Intent
        Intent intent = getIntent();
        String filterUsername = intent.getStringExtra("USERNAME");
        String filterPassword = intent.getStringExtra("PASSWORD");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                // Code will run on Button Click
                //
                String filterUsername = textUsername.getText().toString();
                String filterPassword = textPassword.getText().toString();
                for (int i = 0; i < DataStore.Users.size(); i++) {
                    HashMap<String, Object> user = DataStore.Users.get(i);
                    String userName = String.valueOf(user.get(DataStore.KEY_USERNAME));
                    Boolean student_or_professor = (Boolean) user.get(DataStore.KEY_STUDENT_OR_PROFESSOR);
                    if (userName.equals(filterUsername)) {
                        String password = String.valueOf(user.get(DataStore.KEY_PASSWORD));
                        if (password.equals(filterPassword)) {
                            Intent intent = new Intent(MainActivity.this, SemesterActivity.class);
                            intent.putExtra("USERNAME", filterUsername);
                            intent.putExtra("PASSWORD", filterPassword);
                            intent.putExtra("STUDENT_OR_PROFESSOR", student_or_professor);
                            startActivity(intent);
                        } else {
                            String message = String.format("wrong password");
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                        return;
                    }
                }
                String message = String.format("username not found");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        DataStore.Init(getApplicationContext());
        DataStore.LoadUsers();
        DataStore.LoadLessons();

    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_right);
        super.onPause();
    }

}
