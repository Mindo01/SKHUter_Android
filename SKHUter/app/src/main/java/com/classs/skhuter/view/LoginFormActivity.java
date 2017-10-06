package com.classs.skhuter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.classs.skhuter.R;

public class LoginFormActivity extends Activity {

    Button btnLogin;
    TextView textRegist, textFindPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFormActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textRegist = (TextView) findViewById(R.id.textRegist);

        textRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //액티비티 커밋이 안되어있음 일단 주석처리
                Intent intent = new Intent(LoginFormActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });

        textFindPW = (TextView) findViewById(R.id.textFindPW);

        textFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //액티비티 커밋이 안되어있음 일단 주석처리
                Intent intent = new Intent(LoginFormActivity.this, FindPwActivity.class);
                startActivity(intent);
            }
        });

    }
}
