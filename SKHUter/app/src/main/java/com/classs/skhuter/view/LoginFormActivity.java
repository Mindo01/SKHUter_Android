package com.classs.skhuter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.classs.skhuter.R;
import com.classs.skhuter.util.Connection;

public class LoginFormActivity extends Activity {

    EditText edtID, edtPW;
    Button btnLogin;
    TextView textRegist, textFindPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        //TODO 로그인 정보 임의로 설정
        Connection.loginUser.setUserNo(2);
        Connection.loginUser.setName("김민주");
        Connection.loginUser.setGrade(4);
        Connection.loginUser.setId("201334005");

        edtID = (EditText)findViewById(R.id.edtID);
        edtPW = (EditText)findViewById(R.id.edtPW);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인
                // TODO 바꾸자
                if (edtID.getText().toString().length() > 0) {
                    Connection.loginUser.setUserNo(Integer.valueOf(edtID.getText().toString()));
                }
                Intent intent = new Intent(LoginFormActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textRegist = (TextView) findViewById(R.id.textRegist);

        textRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입
                Intent intent = new Intent(LoginFormActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });

        textFindPW = (TextView) findViewById(R.id.textFindPW);

        textFindPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //비밀번호 찾기
                Intent intent = new Intent(LoginFormActivity.this, FindPwActivity.class);
                startActivity(intent);
            }
        });

    }
}
