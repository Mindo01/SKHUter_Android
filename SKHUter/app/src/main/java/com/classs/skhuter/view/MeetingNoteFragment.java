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
import com.classs.skhuter.adapter.MeetingNoteAdapter;
import com.classs.skhuter.domain.MeetingNoteDTO;
import com.classs.skhuter.domain.UserDTO;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingNoteFragment extends Fragment {

    List<MeetingNoteDTO> meetingNoteDTOList = new ArrayList<MeetingNoteDTO>();
    ListView listView;
    MeetingNoteAdapter meetingNoteAdapter;
    Handler handler = new Handler();
    public MeetingNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meeting_note, container, false);

        // 회의록 목록 불러오기
        getMeetingNote();

        listView = (ListView) v.findViewById(R.id.lvMeetingNoteList);
        listView.setOnItemClickListener(mOnItemClickListener);
        meetingNoteAdapter = new MeetingNoteAdapter(getActivity().getApplicationContext(),
                R.layout.item_meeting_note,
                meetingNoteDTOList);
        listView.setAdapter(meetingNoteAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    /**
     * 회의록 목록을 Volley로 데이터 받는 메소드
     */
    void getMeetingNote() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Connection.ADDRESS+Connection.GET_MEETING_NOTE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MyLog", "response : " + response);
                // 받아온 JSON 데이터 값을 객체에 넣어 리스트로 만들기
                JsonParser parser = new JsonParser();
                JsonObject rootObj = (parser.parse(response)).getAsJsonObject();

                // String to Date Parsing GSONBuilder 등록
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); // String to Date 포맷 변경 명시

                // Timestamp(Long) to Date Parsing GSONBuilder 등록
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
                    @Override
                    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });
                Gson gson2 = builder.create();

                // "list" 키에 해당하는 값을 JSON 배열 형식으로 받기
                JsonArray list = rootObj.getAsJsonArray("list");
                JsonArray nameList = rootObj.getAsJsonArray("name");
                // "list"목록의 각 아이템을 객체로 변환 후 meetingNoteDTOList 추가
                if (list != null && (list.size() > 0) ) {
                    for (int i = 0; i < list.size(); i++) {
                        // JSON Object를 MeetingNoteDTO 객체로 변환
                        MeetingNoteDTO noteDTO = gson2.fromJson(list.get(i), MeetingNoteDTO.class);
                        UserDTO userDTO = gson.fromJson(nameList.get(i), UserDTO.class);
                        noteDTO.setUserName(userDTO.getName());
                        // meetingNoteDTOList MeetingNoteDTO 객체 추가
                        meetingNoteDTOList.add(noteDTO);
                    } // end of for
                    // Adapter 변경사항 갱신
                    meetingNoteAdapter.notifyDataSetChanged();
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

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MeetingNoteDTO nBean = meetingNoteDTOList.get(position);
            // 아이템 클릭 시 이벤트 작성

        }
    }; // end of ItemClickListener


}
