package com.classs.skhuter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.classs.skhuter.R;
import com.classs.skhuter.domain.MeetingNoteDTO;

import java.util.List;

/**
 * 회의록 어댑터 - 리스트뷰
 *
 * @패키지 : com.classs.skhuter.adapter
 * @파일명 : MeetingNoteAdapter.java
 * @작성자 : 이종윤
 * @작성일 : 2017. 10. 07
 *
 */

public class MeetingNoteAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<MeetingNoteDTO> meetingNoteDTOList;
    LayoutInflater inflater;

    public MeetingNoteAdapter(Context context, int layout, List<MeetingNoteDTO> restaList) {
        this.context = context;
        this.layout = layout;
        this.meetingNoteDTOList = restaList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    } // Constructor

    @Override
    public int getCount() {
        return meetingNoteDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return meetingNoteDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_meeting_note, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivNote = (ImageView) convertView.findViewById(R.id.ivNote);
            viewHolder.tvMeetingNoteTitle = (TextView) convertView.findViewById(R.id.tvMeetingNoteTitle);
            viewHolder.tvMeetingNoteWriter = (TextView) convertView.findViewById(R.id.tvMeetingNoteWriter);
            viewHolder.ivDownload = (ImageView) convertView.findViewById(R.id.ivDownload);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MeetingNoteDTO meetingNote = (MeetingNoteDTO) getItem(position);
        viewHolder.tvMeetingNoteTitle.setText(meetingNote.getTitle());
        viewHolder.tvMeetingNoteWriter.setText("작성자 : " + meetingNote.getUserNo());
        Glide.with(context).load(R.drawable.download).thumbnail(0.1f).error(R.drawable.download).into(viewHolder.ivDownload);

        return convertView;
    }

    static class ViewHolder {
        public ImageView ivNote;
        public TextView tvMeetingNoteTitle;
        public TextView tvMeetingNoteWriter;
        public ImageView ivDownload;
    } // end of inner class ViewHolder
} // end of class