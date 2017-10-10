package com.classs.skhuter.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;
import com.classs.skhuter.domain.StuScheduleDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 김민도 on 2017-10-09.
 */

public class MyStuEventDay extends EventDay implements Parcelable {
    private String mNote;
    private List<StuScheduleDTO> scheduleDTOList;
    private String jsonScheduleList;
    public MyStuEventDay(Calendar day, int imageResource, String mNote) {
        super(day, imageResource);
        this.mNote = mNote;
        scheduleDTOList  = new ArrayList<>();
    }

    public String getNote() {
        return mNote;
    }

    public void addSchedule(StuScheduleDTO scheduleDTO) {
        scheduleDTOList.add(scheduleDTO);
    }

    public StuScheduleDTO get(int ind) {
        return scheduleDTOList.get(ind);
    }

    public int getSize() { return scheduleDTOList.size(); }
    public List<StuScheduleDTO> getScheduleDTOList() { return scheduleDTOList; }

    private MyStuEventDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        mNote = in.readString();
        // 문자열 변환
        jsonScheduleList = in.readString();
        Type listType = new TypeToken<List<StuScheduleDTO>>() {}.getType();
        scheduleDTOList = new Gson().fromJson(jsonScheduleList, listType);
    }

    public static final Creator<MyStuEventDay> CREATOR = new Creator<MyStuEventDay>() {
        @Override
        public MyStuEventDay createFromParcel(Parcel in) {
            return new MyStuEventDay(in);
        }

        @Override
        public MyStuEventDay[] newArray(int size) {
            return new MyStuEventDay[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(getImageResource());
        parcel.writeString(mNote);
        // 리스트 변환
        Type listType = new TypeToken<List<StuScheduleDTO>>() {}.getType();
        jsonScheduleList = new Gson().toJson(scheduleDTOList, listType);
        parcel.writeString(jsonScheduleList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyStuEventDay) {
            MyStuEventDay myObj = (MyStuEventDay)obj;
            if (myObj == null)
                return false;
            else {
                // this와 myObj의 long값(timeInMillis)이 같으면 같은 객체로 취급한다
                if (myObj.getCalendar().getTime().getTime() == this.getCalendar().getTime().getTime())
                {
                    return true;
                }
            }
        }
        return super.equals(obj);
    }
}