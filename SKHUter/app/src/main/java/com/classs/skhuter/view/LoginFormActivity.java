package com.classs.skhuter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
                // TODO 바꾸자
                UserDTO user = new UserDTO();
                if (edtID.getText().toString().length() > 0) {
                    user.setUserNo(Integer.valueOf(edtID.getText().toString()));
                    user.setId(edtID.getText().toString());

                }

                if (edtPW.getText().toString().length() > 0) {
                    user.setPassword(edtPW.getText().toString());
                }

                sendLoginResult(user);


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
                        try {
                            result = obj.getJSONObject("user");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (result == null) {
                            // 로그인 실패
                            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        } else {
                            // 로그인 성공
                            // 로그인 정보 확인
                            Connection.loginUser = gson.fromJson(result.toString(), UserDTO.class);

                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                            // 로그인 정보 저장
                            Log.e("Login USER", Connection.loginUser.getName()+", "+Connection.loginUser.getUserNo()+", "+Connection.loginUser.getId());
                            // 홈 화면으로 이동
                            Intent intent = new Intent(LoginFormActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("MyLog", "error : " + error);
                final VolleyError err = error;

/*                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MyLog", "error : " + err);
                        tvError.setText("네트워크 연결이 원활하지 않습니다");
                        tvError.setVisibility(View.VISIBLE);
                    }
                });*/
            }
        });
        requestQueue.add(request);
    }
}
