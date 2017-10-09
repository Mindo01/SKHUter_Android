package com.classs.skhuter.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;
import com.classs.skhuter.domain.CouncilScheduleDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 김민도 on 2017-10-09.
 */

public class MyEventDay extends EventDay implements Parcelable {
    private String mNote;
    private List<CouncilScheduleDTO> scheduleDTOList;
    private String jsonScheduleList;
    public MyEventDay(Calendar day, int imageResource, String mNote) {
        super(day, imageResource);
        this.mNote = mNote;
        scheduleDTOList  = new ArrayList<>();
    }

    public String getNote() {
        return mNote;
    }

    public void addSchedule(CouncilScheduleDTO scheduleDTO) {
        scheduleDTOList.add(scheduleDTO);
    }

    public CouncilScheduleDTO get(int ind) {
        return scheduleDTOList.get(ind);
    }

    public int getSize() { return scheduleDTOList.size(); }
    public List<CouncilScheduleDTO> getScheduleDTOList() { return scheduleDTOList; }

    private MyEventDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        mNote = in.readString();
        // 문자열 변환
        jsonScheduleList = in.readString();
        Type listType = new TypeToken<List<CouncilScheduleDTO>>() {}.getType();
        scheduleDTOList = new Gson().fromJson(jsonScheduleList, listType);
    }

    public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
        @Override
        public MyEventDay createFromParcel(Parcel in) {
            return new MyEventDay(in);
        }

        @Override
        public MyEventDay[] newArray(int size) {
            return new MyEventDay[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(getImageResource());
        parcel.writeString(mNote);
        // 리스트 변환
        Type listType = new TypeToken<List<CouncilScheduleDTO>>() {}.getType();
        jsonScheduleList = new Gson().toJson(scheduleDTOList, listType);
        parcel.writeString(jsonScheduleList);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}