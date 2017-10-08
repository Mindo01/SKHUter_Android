package com.classs.skhuter.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.classs.skhuter.R;
import com.classs.skhuter.adapter.UserListAdapter;
import com.classs.skhuter.domain.UserDTO;
import com.classs.skhuter.util.Connection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserListFragment extends Fragment {

    List<UserDTO> userDTOList = new ArrayList<UserDTO>();
    ListView listView;
    UserListAdapter userListAdapter;
    Handler handler = new Handler();
    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_list, container, false);

        // 회원 목록 조회
        getUserList();

        listView = (ListView) v.findViewById(R.id.lvUserList);
        listView.setOnItemClickListener(mOnItemClickListener);
        userListAdapter = new UserListAdapter(getActivity().getApplicationContext(),
                R.layout.item_user_list,
                userDTOList);
        listView.setAdapter(userListAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UserDTO nBean = userDTOList.get(position);
            // 아이템 클릭 시 이벤트 작성

        }
    }; // end of ItemClickListener

    /**
     * 학생 목록을 Volley로 데이터 받는 메소드
     */
    void getUserList() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Connection.ADDRESS+Connection.GET_USER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MyLog", "response : " + response);
                // 받아온 JSON 데이터 값을 객체에 넣어 리스트로 만들기
                JsonParser parser = new JsonParser();
                JsonObject rootObj = (parser.parse(response)).getAsJsonObject();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); // String to Date 포맷 변경 명시
                // "list" 키에 해당하는 값을 JSON 배열 형식으로 받기
                JsonArray list = rootObj.getAsJsonArray("list");
                // "list"목록의 각 아이템을 객체로 변환 후 userDTOList에 추가
                if (list != null && (list.size() > 0) ) {
                    for (int i = 0; i < list.size(); i++) {
                        // JSON Object를 UserDTO 객체로 변환
                        UserDTO user = gson.fromJson(list.get(i), UserDTO.class);
                        // userDTOList에 UserDTO 객체 추가
                        userDTOList.add(user);
                    } // end of for
                    // Adapter 변경사항 갱신
                    userListAdapter.notifyDataSetChanged();
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
                    }
                });
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("memberNo", sharedPreferences.getString(SHAREDPREFERENCES_MEMBER_NO, ""));
                //params.put("groupNo", groupBean.getGroupNo());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
