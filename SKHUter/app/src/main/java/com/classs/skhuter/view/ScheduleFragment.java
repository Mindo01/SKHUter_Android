package com.classs.skhuter.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.classs.skhuter.adapter.ScheduleAdapter;
import com.classs.skhuter.domain.CouncilScheduleDTO;
import com.classs.skhuter.util.Connection;
import com.classs.skhuter.util.MyEventDay;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleFragment extends Fragment {
    private CalendarView mCalendarView;
    private TextView tvSelectedDay;
    private TextView tvError;
    private ListView listView;
    private List<EventDay> mEventDays = new ArrayList<>();
    private List<CouncilScheduleDTO> allScheduleList = new ArrayList<>();
    private List<CouncilScheduleDTO> councilScheduleDTOList = new ArrayList<>();
    private ScheduleAdapter scheduleAdapter;

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
        listView = (ListView)v.findViewById(R.id.lvSchedule);

        // 일정 목록 받아오기
        getSchedule();

        Calendar cal = Calendar.getInstance();
        Log.e("DateHere", cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE));
        // 이벤트 목록에 추가
        //////////////////////////////// 더미데이터
        /*MyEventDay eventDay = new MyEventDay(cal, R.drawable.ic_meeting_note, "하하하");
        MyEventDay eventDay2 = new MyEventDay(cal, R.drawable.ic_logout, "호호호");
        CouncilScheduleDTO councilScheduleDTO = new CouncilScheduleDTO();
        councilScheduleDTO.setContent("첫 번째 일정");
        councilScheduleDTO.setStartDate("2017-10-09 18:00:00");
        councilScheduleDTO.setEndDate("2017-10-10 19:00:00");
        eventDay.addSchedule(councilScheduleDTO);
        councilScheduleDTO = new CouncilScheduleDTO();
        councilScheduleDTO.setContent("두 번째 일정");
        councilScheduleDTO.setStartDate("2017-10-09 17:00:00");
        councilScheduleDTO.setEndDate("2017-10-10 11:00:00");
        eventDay.addSchedule(councilScheduleDTO);
        mEventDays.add(eventDay);
        eventDay.addSchedule(councilScheduleDTO);
        mEventDays.add(eventDay);*/
        //////////////////////////////// 더미데이터

        scheduleAdapter = new ScheduleAdapter(getActivity().getApplicationContext(),
                R.layout.item_schedule,
                councilScheduleDTOList);
        listView.setAdapter(scheduleAdapter);

        // 일자 클릭 시 이벤트 실행
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Log.e("onDay", "날짜 누름");
                // 리스트 초기화
                councilScheduleDTOList.clear();
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
                if(eventDay instanceof MyEventDay){
                    MyEventDay myEventDay = (MyEventDay)eventDay;
                    // 해당 날짜의 일정들 불러와 리스트뷰에 추가
                    for (int i = 0; i < myEventDay.getSize(); i++) {
                        councilScheduleDTOList.add(myEventDay.get(i));
                        Log.e("일정 추가 "+i+"번째", myEventDay.get(i).getContent());
                    }
                }
                scheduleAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    /**
     * 일정을 Volley로 데이터 받는 메소드
     */
    void getSchedule() {
        allScheduleList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Connection.ADDRESS+Connection.GET_COUNCIL_SCHEDULE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MyLog", "response : " + response);
                // 받아온 JSON 데이터 값을 객체에 넣어 리스트로 만들기
                JsonParser parser = new JsonParser();
                JsonObject rootObj = (parser.parse(response)).getAsJsonObject();

                // Timestamp(Long) to Date Parsing GSONBuilder 등록
                GsonBuilder builder = new GsonBuilder();
                builder.setDateFormat("yyyy-MM-dd");
                builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
                    @Override
                    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });
                Gson gson = builder.create();

                // "list" 키에 해당하는 값을 JSON 배열 형식으로 받기
                JsonArray list = rootObj.getAsJsonArray("scheduleList");
                // "list"목록의 각 아이템을 객체로 변환 후 meetingNoteDTOList 추가
                if (list != null && (list.size() > 0) ) {
                    for (int i = 0; i < list.size(); i++) {
                        // JSON Object를 CouncilScheduleDTO 객체로 변환
                        CouncilScheduleDTO councilScheduleDTO = gson.fromJson(list.get(i), CouncilScheduleDTO.class);
                        // councilScheduleDTOList에 CouncilScheduleDTO 객체 추가
                        allScheduleList.add(councilScheduleDTO);
                        Log.e("JSON to Object "+i, councilScheduleDTO.toString());
                    } // end of for

                    /** 리스트를 받아서 MyEventDay로 만들어 넣어주기 */
                    List<CouncilScheduleDTO> scheduleList = allScheduleList;
                    Log.e("scheduleList SIZE", scheduleList.size()+"입니다");
                    for (int i=0; i < scheduleList.size(); i++) {
                        // 한 일정에 대한 EventDay 넣어주기
                        CouncilScheduleDTO tmpScheduleDTO = scheduleList.get(i);
                        // 시간 비교를 위한 변환과정
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = null;
                        Date endDate = null;
                        try {
                            startDate = format.parse(tmpScheduleDTO.getStartDate());
                            endDate = format.parse(tmpScheduleDTO.getEndDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar startCal = Calendar.getInstance(); // 일정 시작일
                        Calendar endCal = Calendar.getInstance();   // 일정 종료일
                        startCal.setTime(startDate);
                        endCal.setTime(endDate);
                        Log.e("DAYS", format.format(startCal.getTime())+"~"+format.format(endCal.getTime()));
                        while(startCal.compareTo(endCal) <= 0) {
                            MyEventDay tmpEd = new MyEventDay(startCal, R.drawable.ic_meeting_note, format.format(startCal.getTime())+tmpScheduleDTO.getCouncilScheduleNo());
                            tmpEd.addSchedule(tmpScheduleDTO);
                            Log.e("ADDING SCHEDULE", format.format(startCal.getTime())+"에 "+tmpScheduleDTO.getContent());
                            mEventDays.add(tmpEd);
                            startCal.add(Calendar.DATE, 1);
                        }
                    }

                    // 이벤트 목록을 달력에 등록
                    mCalendarView.setEvents(mEventDays);
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
