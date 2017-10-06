package com.classs.skhuter.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.classs.skhuter.R;

public class RegistActivity extends AppCompatActivity {

    Button btnRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        Spinner gradeSpinner = (Spinner) findViewById(R.id.spinner_grade);
        ArrayAdapter gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        Spinner isLeaveSpinner = (Spinner) findViewById(R.id.spinner_isLeave);
        ArrayAdapter isLeaveAdapter = ArrayAdapter.createFromResource(this, R.array.isLeave, android.R.layout.simple_spinner_item);
        isLeaveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isLeaveSpinner.setAdapter(isLeaveAdapter);

        btnRegist = (Button)findViewById(R.id.btnRegist);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
