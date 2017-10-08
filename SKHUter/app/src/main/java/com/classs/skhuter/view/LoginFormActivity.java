package com.classs.skhuter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.classs.skhuter.R;
import com.classs.skhuter.util.Connection;

public class LoginFormActivity extends Activity {

    Button btnLogin;
    TextView textRegist, textFindPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        //TODO 로그인 정보 임의로 설정
        Connection.loginUser.setUserNo(1);
        Connection.loginUser.setName("이종윤");
        Connection.loginUser.setGrade(4);
        Connection.loginUser.setId("201434025");

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
