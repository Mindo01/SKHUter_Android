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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.classs.skhuter.R;
import com.classs.skhuter.adapter.AccountingAdapter;
import com.classs.skhuter.domain.AccountingDTO;
import com.classs.skhuter.util.Connection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AccountFragment extends Fragment {

    List<AccountingDTO> accountingDTOList = new ArrayList<AccountingDTO>();
    ListView listView;
    AccountingAdapter accountingAdapter;
    TextView tvIncome, tvExpenditure, tvRestMoney;
    TextView tvError;
    Handler handler = new Handler();
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        tvIncome = (TextView) v.findViewById(R.id.tvIncome);
        tvExpenditure = (TextView) v.findViewById(R.id.tvExpenditure);
        tvRestMoney = (TextView) v.findViewById(R.id.tvRestMoney);
        tvError = (TextView) v.findViewById(R.id.tvError);
        listView = (ListView) v.findViewById(R.id.lvAccountingList);

        getAccountingList();

        listView.setOnItemClickListener(mOnItemClickListener);
        accountingAdapter = new AccountingAdapter(getActivity().getApplicationContext(),
                R.layout.item_accounting,
                accountingDTOList);
        listView.setAdapter(accountingAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    /**
     * 회계 내역 목록을 Volley로 데이터 받는 메소드
     */
    void getAccountingList() {
        // 목록 초기화
        accountingDTOList.clear();
        // Volley
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Connection.ADDRESS+Connection.GET_ACCOUNTING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MyLog", "response : " + response);
                // 받아온 JSON 데이터 값을 객체에 넣어 리스트로 만들기
                JsonParser parser = new JsonParser();
                JsonObject rootObj = (parser.parse(response)).getAsJsonObject();

                GsonBuilder builder = new GsonBuilder();
                builder.setDateFormat("yyyy-MM-dd");  // String to Date Parsing GSONBuilder 등록
                // Timestamp(Long) to Date Parsing GSONBuilder 등록
                builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
                    @Override
                    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });
                Gson gson = builder.create();

                // "list" 키에 해당하는 값을 JSON 배열 형식으로 받기
                JsonArray list = rootObj.getAsJsonArray("list");
                JsonPrimitive money = rootObj.getAsJsonPrimitive("money");
                // 잔액, 수입, 지출 설정
                int restMoney = gson.fromJson(money, Integer.class);
                int income = 0;      // 수입
                int expenditure = 0; // 지출
                // "list"목록의 각 아이템을 객체로 변환 후 meetingNoteDTOList 추가
                if (list != null && (list.size() > 0) ) {
                    for (int i = 0; i < list.size(); i++) {
                        // JSON Object를 MeetingNoteDTO 객체로 변환
                        AccountingDTO accountingDTO = gson.fromJson(list.get(i), AccountingDTO.class);
                        if (accountingDTO.getStatus() == 0) {
                            // 수입
                            income += accountingDTO.getPrice();
                        } else {
                            // 지출
                            expenditure += accountingDTO.getPrice();
                        }
                        // meetingNoteDTOList MeetingNoteDTO 객체 추가
                        accountingDTOList.add(accountingDTO);
                    } // end of for
                    // Adapter 변경사항 갱신
                    accountingAdapter.notifyDataSetChanged();

                    // 수입/지출/잔액 설정
                    tvIncome.setText(income+"원");
                    tvExpenditure.setText(expenditure+"원");
                    tvRestMoney.setText(restMoney+"원");
                }

                // 데이터가 없을 때
                if (list.size() <= 0) {
                    tvError.setText("회의록이 존재하지 않습니다");
                    tvError.setVisibility(View.VISIBLE);
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
                        tvError.setText("네트워크 연결이 원활하지 않습니다");
                        tvError.setVisibility(View.VISIBLE);
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

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AccountingDTO nBean = accountingDTOList.get(position);
            // 아이템 클릭 시 이벤트 작성

        }
    }; // end of ItemClickListener
}
