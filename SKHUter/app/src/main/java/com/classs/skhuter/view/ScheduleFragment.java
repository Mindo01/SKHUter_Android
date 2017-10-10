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



        // 이벤트 목록에 추가
        //////////////////////////////// 더미데이터
        /*Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();
        Calendar cal22 = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2, date3;
        try {
            date1 = format.parse("2017-10-08 17:00:00");
            date2 = format.parse("2017-10-09 16:30:00");
            date3 = format.parse("2017-10-10 13:00:00");

            cal1.setTime(date1);
            cal2.setTime(date2);
            cal3.setTime(date3);
            cal22.setTime(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MyEventDay eventDay1 = new MyEventDay(cal1, R.drawable.ic_meeting_note, "2017-10-08");
        MyEventDay eventDay11 = new MyEventDay(cal1, R.drawable.ic_meeting_note, "2017-10-08 2");
        MyEventDay eventDay2 = new MyEventDay(cal2, R.drawable.ic_logout, "2017-10-09");
        MyEventDay eventDay3 = new MyEventDay(cal3, R.drawable.ic_logout, "2017-10-10");
        CouncilScheduleDTO councilScheduleDTO1 = new CouncilScheduleDTO();
        councilScheduleDTO1.setCouncilScheduleNo(1);
        councilScheduleDTO1.setContent("첫 번째 일정");
        councilScheduleDTO1.setStartDate("2017-10-09 18:00:00");
        councilScheduleDTO1.setEndDate("2017-10-10 19:00:00");
        eventDay2.addSchedule(councilScheduleDTO1);
        eventDay3.addSchedule(councilScheduleDTO1);
        CouncilScheduleDTO councilScheduleDTO2 = new CouncilScheduleDTO();
        councilScheduleDTO2.setCouncilScheduleNo(2);
        councilScheduleDTO2.setContent("두 번째 일정");
        councilScheduleDTO2.setStartDate("2017-10-09 17:00:00");
        councilScheduleDTO2.setEndDate("2017-10-10 11:00:00");
        eventDay3.addSchedule(councilScheduleDTO2);
        CouncilScheduleDTO councilScheduleDTO3 = new CouncilScheduleDTO();
        councilScheduleDTO3.setCouncilScheduleNo(3);
        councilScheduleDTO3.setContent("세 번째 일정");
        councilScheduleDTO3.setStartDate("2017-10-09 17:00:00");
        councilScheduleDTO3.setEndDate("2017-10-09 19:00:00");
        eventDay2.addSchedule(councilScheduleDTO3);
        CouncilScheduleDTO councilScheduleDTO4 = new CouncilScheduleDTO();
        councilScheduleDTO4.setCouncilScheduleNo(3);
        councilScheduleDTO4.setContent("네 번째 일정");
        councilScheduleDTO4.setStartDate("2017-10-08 10:00:00");
        councilScheduleDTO4.setEndDate("2017-10-08 20:00:00");
        eventDay1.addSchedule(councilScheduleDTO4);
        CouncilScheduleDTO councilScheduleDTO5 = new CouncilScheduleDTO();
        councilScheduleDTO5.setCouncilScheduleNo(3);
        councilScheduleDTO5.setContent("다섯 번째 일정");
        councilScheduleDTO5.setStartDate("2017-10-08 10:00:00");
        councilScheduleDTO5.setEndDate("2017-10-08 20:00:00");
        eventDay11.addSchedule(councilScheduleDTO5);
        mEventDays.add(eventDay1);
        mEventDays.add(eventDay2);
        mEventDays.add(eventDay3);
        mEventDays.add(eventDay11);

        // eventDay를 받아서 사용해보자
        Log.e("10월9일 EventDay 있습니까?", mEventDays.indexOf(new MyEventDay(cal22, R.drawable.ic_logout, "2017-10-09 2"))+"에 있습니다");
        MyEventDay getEventDay = (MyEventDay)mEventDays.get(mEventDays.indexOf(new MyEventDay(cal22, R.drawable.ic_logout, "")));
        getEventDay.addSchedule(councilScheduleDTO2);
        mEventDays.add(getEventDay);

        mCalendarView.setEvents(mEventDays); // 헤헤*/
        //////////////////////////////// 더미데이터

        scheduleAdapter = new ScheduleAdapter(getActivity().getApplicationContext(),
                R.layout.item_schedule,
                councilScheduleDTOList);
        listView.setAdapter(scheduleAdapter);

        // 일자 클릭 시 이벤트 실행
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Log.e("onDay", "날짜 누름 "+format.format(eventDay.getCalendar().getTime()));
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
                        Log.e("일정 추가 "+i+"번째", myEventDay.get(i).getContent()+"//cal="+myEventDay.getCalendar().toString());
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
                            Log.e("START DATE "+tmpScheduleDTO.getStartDate(), tmpScheduleDTO.getContent()+":"+startDate.getTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar startCal = Calendar.getInstance(); // 일정 시작일
                        Calendar endCal = Calendar.getInstance();   // 일정 종료일
                        startCal.setTime(startDate);
                        endCal.setTime(endDate);
                        Log.e("DAYS", format.format(startCal.getTime())+"~"+format.format(endCal.getTime()));
                        /// 중요! Calendar 객체를 바꾸면 그 객체에 레퍼런스를 둔 값들이 전부 바뀐다
                        for (int j = 0; true; j++) {
                            Calendar putCal = Calendar.getInstance();
                            putCal.setTime(startDate);
                            putCal.add(Calendar.DATE, j);
                            // 일정 추가하려는 일자가 종료일자보다 이후 날짜일 경우 작업 그만
                            if (putCal.compareTo(endCal) > 0) {
                                break;
                            }
                            // 이미 존재하는 EventDay인지 EventDay List에서 index 검색
                            // - MyEventDay에서 Equals를 override해서, 날짜(yyyy-MM-dd)가 동일하면 같은 객체로 판단하게 구현함
                            int hasEventDay = mEventDays.indexOf(new MyEventDay(putCal, R.drawable.ic_meeting_note, ""));
                            MyEventDay tmpEd;
                            if (hasEventDay >= 0) {
                                // 이미 존재하는 EventDay일 경우 받아서 사용
                                tmpEd = (MyEventDay)mEventDays.get(hasEventDay);
                            } else {
                                // 존재하지 않는 EventDay일 경우 생성해서 일정 넣어주기
                                tmpEd = new MyEventDay(putCal, R.drawable.ic_meeting_note, format.format(putCal.getTime()) + tmpScheduleDTO.getCouncilScheduleNo());
                            }
                            tmpEd.addSchedule(tmpScheduleDTO);
                            Log.e("ADDING SCHEDULE", format.format(putCal.getTime())+"에 "+tmpScheduleDTO.getContent() + "//putCal="+putCal.toString());
                            mEventDays.add(tmpEd);
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
