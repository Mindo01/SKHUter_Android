package com.classs.skhuter.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

/**
 * Created by kosta on 2017-04-24.
 */

public class IntroActivity extends Activity {

    // SharedPreferences 선언
    private SharedPreferences sharedPreferences;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        handler = new Handler();
        // SharedPreferences 초기화
        sharedPreferences = getSharedPreferences(Connection.SH_UNAME, MODE_PRIVATE);
    } // end of onCreate

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        boolean autoFlag = true;
        if (sharedPreferences != null) {
            if (sharedPreferences.getBoolean(Connection.SH_AUTO_LOGIN, false)) {
                // 자동 로그인 설정시
                autoFlag = false;
                UserDTO user = new UserDTO();
                user.setId(sharedPreferences.getString(Connection.SH_LOGIN_ID, ""));
                user.setPassword(sharedPreferences.getString(Connection.SH_LOGIN_PW, ""));
                if ("".equals(user.getId())) {
                    autoFlag = true;
                } else {
                    // 로그인 가능한 로그인 정보인지 검사
                    sendLoginResult(user);
                }
            }
        }
        if (autoFlag) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(IntroActivity.this, LoginFormActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 1000);
        }
        super.onResume();
    } // end of onResume

    /**
     * 로그인값을 Volley로 데이터 보내는 메소드
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

                        boolean canAutoLogin = false;
                        // 로그인 실패
                        if (result == null) {
                            Toast.makeText(getApplicationContext(), "일치하는 회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            if (secession.equals("1")) {
                                Toast.makeText(getApplicationContext(), "탈퇴한 회원입니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                if (status.equals("0")) {
                                    Toast.makeText(getApplicationContext(), "가입 승인되지 않은 회원입니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    // 로그인 성공
                                    // 로그인 정보 확인
                                    Connection.loginUser = gson.fromJson(result.toString(), UserDTO.class);
                                    canAutoLogin = true;
                                    // 자동로그인 내역이 맞을 때 - 홈 화면
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 1000);
                                }
                            }
                        }
                        if (canAutoLogin == false) {
                            // 자동로그인 내역이 맞지 않을 때
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(IntroActivity.this, LoginFormActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);
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
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(IntroActivity.this, LoginFormActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);
            }
        });
        requestQueue.add(request);
    }

} // end of class
