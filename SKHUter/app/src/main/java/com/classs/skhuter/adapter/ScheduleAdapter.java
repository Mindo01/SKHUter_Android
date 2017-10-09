package com.classs.skhuter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.classs.skhuter.R;
import com.classs.skhuter.domain.CouncilScheduleDTO;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 투표 목록 어댑터 - 리스트뷰
 *
 * @패키지 : com.classs.skhuter.adapter
 * @파일명 : ScheduleAdapter.java
 * @작성자 : 김민주
 * @작성일 : 2017. 10. 09
 *
 */

public class ScheduleAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<CouncilScheduleDTO> scheduleDTOList;
    LayoutInflater inflater;

    public ScheduleAdapter(Context context, int layout, List<CouncilScheduleDTO> scheduleDTOList) {
        this.context = context;
        this.layout = layout;
        this.scheduleDTOList = scheduleDTOList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    } // Constructor

    @Override
    public int getCount() {
        return scheduleDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return scheduleDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_schedule, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SimpleDateFormat showFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CouncilScheduleDTO scheduleDTO = (CouncilScheduleDTO) getItem(position);
        viewHolder.tvContent.setText(scheduleDTO.getContent());
        viewHolder.tvDate.setText(scheduleDTO.getStartDate().substring(0, 16)+"~"+scheduleDTO.getEndDate().substring(0, 16));

        return convertView;
    }

    static class ViewHolder {
        public TextView tvContent;
        public TextView tvDate;
    } // end of inner class ViewHolder
} // end of class