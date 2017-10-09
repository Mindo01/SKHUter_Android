package com.classs.skhuter.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONObject;

public class RegistActivity extends AppCompatActivity {

    private EditText registID;
    private EditText edPassword;
    private EditText edName;
    private EditText edPhone;
    private Spinner gradeSpinner;
    private Spinner isLeaveSpinner;

    private Button btnRegist;

    // Handler 객체
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        // xml 위젯 초기화
        registID = (EditText)findViewById(R.id.registID);
        edPassword = (EditText)findViewById(R.id.edPassword);
        edName = (EditText)findViewById(R.id.edName);
        edPhone = (EditText)findViewById(R.id.edPhone);
        gradeSpinner = (Spinner) findViewById(R.id.spinner_grade);
        isLeaveSpinner = (Spinner) findViewById(R.id.spinner_isLeave);
        btnRegist = (Button)findViewById(R.id.btnRegist);


        final ArrayAdapter gradeAdapter = ArrayAdapter.createFromResource(this, R.array.grade, android.R.layout.simple_spinner_item);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(gradeAdapter);

        ArrayAdapter isLeaveAdapter = ArrayAdapter.createFromResource(this, R.array.isLeave, android.R.layout.simple_spinner_item);
        isLeaveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isLeaveSpinner.setAdapter(isLeaveAdapter);


        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDTO user = new UserDTO();
                user.setId(registID.getText().toString());
                user.setPassword(edPassword.getText().toString());
                user.setName(edName.getText().toString());
                user.setPhone(edPhone.getText().toString());
                user.setGrade(Integer.valueOf(gradeSpinner.getSelectedItem().toString().substring(0,1)));
                user.setIsLeave(isLeaveSpinner.getSelectedItemPosition());

                sendRegistResult(user);
                finish();
            }
        });
    }

    /**
     * 회원가입을 Volley로 데이터 보내는 메소드
     */
    void sendRegistResult(UserDTO user) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String PARAMS = "?id="+ user.getId()
                +"&password="+user.getPassword()
                +"&name="+user.getName()
                +"&phone="+user.getPhone()
                +"&grade="+user.getGrade()
                +"&isLeave="+user.getIsLeave();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Connection.ADDRESS+Connection.GET_REGIST+PARAMS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {
                        Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
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
