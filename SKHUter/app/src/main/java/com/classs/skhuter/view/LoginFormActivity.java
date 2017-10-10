package com.classs.skhuter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.classs.skhuter.R;
import com.classs.skhuter.domain.UserDTO;
import com.classs.skhuter.util.Connection;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFormActivity extends Activity {

    EditText edtID, edtPW;
    Button btnLogin;
    TextView textRegist, textFindPW;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);

        Connection.loginUser = null;

        edtID = (EditText)findViewById(R.id.edtID);
        edtPW = (EditText)findViewById(R.id.edtPW);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인
                UserDTO user = new UserDTO();
                if (edtID.getText().toString().length() > 0 && edtPW.getText().toString().length() > 0) {
                    user.setId(edtID.getText().toString());
                    user.setPassword(edtPW.getText().toString());

                    sendLoginResult(user);
                }

                // 로그인 유효성 검사
                if (edtID.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else if (edtPW.getText().toString().length() <= 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
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

    /**
     * 회원가입을 Volley로 데이터 보내는 메소드
     */
    void sendLoginResult(UserDTO user) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String PARAMS = "?id="+ user.getId()
                +"&password="+user.getPassword();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Connection.ADDRESS+Connection.GET_Login+PARAMS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {

                        Gson gson = new Gson();
                        JSONObject result = null;
                        String status = null, secession = null;

                        try {
                            result = obj.getJSONObject("user");

                            status = result.getString("status");
                            secession = result.getString("secession");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // 로그인 실패
                        if (secession.equals("1")) {
                            Toast.makeText(getApplicationContext(), "탈퇴한 회원입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (result == null) {
                                Toast.makeText(getApplicationContext(), "일치하는 회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                            } else if (status.equals("0")) {
                                Toast.makeText(getApplicationContext(), "가입 승인되지 않은 회원입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                // 로그인 성공
                                // 로그인 정보 확인
                                Connection.loginUser = gson.fromJson(result.toString(), UserDTO.class);

                                Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                // 로그인 정보 저장
                                Log.e("Login USER", Connection.loginUser.getName() + ", " + Connection.loginUser.getUserNo() + ", " + Connection.loginUser.getId());
                                // 홈 화면으로 이동
                                Intent intent = new Intent(LoginFormActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("MyLog", "error : " + error);
                final VolleyError err = error;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MyLog", "error : " + err);
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 원활하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        requestQueue.add(request);
    }
}
