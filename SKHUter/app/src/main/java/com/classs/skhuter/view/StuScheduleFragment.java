package com.classs.skhuter.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.classs.skhuter.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StuScheduleFragment extends Fragment {
    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    private TextView tvSelectedDay;
    public StuScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stu_schedule, container, false);

        mCalendarView = (CalendarView)v.findViewById(R.id.stu_calendar);
        tvSelectedDay = (TextView)v.findViewById(R.id.tvToday);

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
}
