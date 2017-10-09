package com.classs.skhuter.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.classs.skhuter.R;
import com.classs.skhuter.domain.CouncilScheduleDTO;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleFragment extends Fragment {
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    private List<CouncilScheduleDTO> councilScheduleDTOList = new ArrayList<>();
    private TextView tvSelectedDay;
    private TextView tvError;
    Handler handler = new Handler();

    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        mCalendarView = (CalendarView)v.findViewById(R.id.datePicker);
        tvSelectedDay = (TextView)v.findViewById(R.id.tvToday);
        tvError = (TextView)v.findViewById(R.id.tvError);

        Calendar cal = Calendar.getInstance();
        Log.e("DateHere", cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
        // 이벤트 목록에 추가
        mEventDays.add(new EventDay(cal, R.drawable.ic_meeting_note));
        mEventDays.add(new EventDay(cal, R.drawable.ic_meeting_note));
        // 이벤트 목록을 달력에 등록
        mCalendarView.setEvents(mEventDays);

        // 일자 클릭 시 이벤트 실행
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Log.e("onDay", "날짜 누름");
                // 선택된 날짜 받아오기
                Calendar selectedDay = eventDay.getCalendar();
                String year = selectedDay.get(Calendar.YEAR)+"";
                String month = selectedDay.get(Calendar.MONTH)+1+"";
                if (month.length() < 2) {
                    month = "0"+month;
                }
                String day = selectedDay.get(Calendar.DATE)+"";
                if (day.length() < 2) {
                    day = "0"+day;
                }
                // 선택된 날짜 표기하기
                tvSelectedDay.setText(year+"-"+month+"-"+day);
                // 선택된 날짜의 이벤트 표시해주기
            }
        });

        return v;
    }

    /**
     * 회의록 목록을 Volley로 데이터 받는 메소드
     */
    void getMeetingNote() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Connection.ADDRESS+Connection.GET_COUNCIL_SCHEDULE, new Response.Listener<String>() {
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
                JsonArray list = rootObj.getAsJsonArray("scheduleList");
                // "list"목록의 각 아이템을 객체로 변환 후 meetingNoteDTOList 추가
                if (list != null && (list.size() > 0) ) {
                    for (int i = 0; i < list.size(); i++) {
                        // JSON Object를 CouncilScheduleDTO 객체로 변환
                        CouncilScheduleDTO councilScheduleDTO = gson2.fromJson(list.get(i), CouncilScheduleDTO.class);
                        // councilScheduleDTOList에 CouncilScheduleDTO 객체 추가
                        councilScheduleDTOList.add(councilScheduleDTO);
                    } // end of for
                }

                // 데이터가 없을 때
                if (list.size() <= 0) {
                    tvError.setText("일정이 존재하지 않습니다");
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
        });
        requestQueue.add(stringRequest);
    }
}
