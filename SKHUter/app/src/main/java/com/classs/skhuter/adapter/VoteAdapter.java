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
import com.classs.skhuter.domain.VoteDTO;

import java.util.List;

/**
 * 투표 목록 어댑터 - 리스트뷰
 *
 * @패키지 : com.classs.skhuter.adapter
 * @파일명 : VoteAdapter.java
 * @작성자 : 이종윤
 * @작성일 : 2017. 10. 07
 *
 */

public class VoteAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<VoteDTO> voteDTOList;
    LayoutInflater inflater;

    public VoteAdapter(Context context, int layout, List<VoteDTO> restaList) {
        this.context = context;
        this.layout = layout;
        this.voteDTOList = restaList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    } // Constructor

    @Override
    public int getCount() {
        return voteDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return voteDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_vote, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivVoteIcon = (ImageView) convertView.findViewById(R.id.ivVoteIcon);
            viewHolder.tvVoteName = (TextView) convertView.findViewById(R.id.tvVoteName);
            viewHolder.tvVoteDate = (TextView) convertView.findViewById(R.id.tvVoteDate);
            viewHolder.tvVoteCount = (TextView) convertView.findViewById(R.id.tvVoteCount);
            viewHolder.ivVoteImg = (ImageView) convertView.findViewById(R.id.ivVoteImg);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        VoteDTO vote = (VoteDTO) getItem(position);
        viewHolder.tvVoteName.setText(vote.getTitle());
        viewHolder.tvVoteDate.setText(vote.getStartDate() + " ~ " + vote.getEndDate());
        viewHolder.tvVoteCount.setText(vote.getJoinCount() + "명 참여");
        Glide.with(context).load(R.drawable.end_vote).thumbnail(0.1f).error(R.drawable.none_vote).into(viewHolder.ivVoteImg);

        return convertView;
    }

    static class ViewHolder {
        public ImageView ivVoteIcon;
        public TextView tvVoteName;
        public TextView tvVoteDate;
        public TextView tvVoteCount;
        public ImageView ivVoteImg;
    } // end of inner class ViewHolder
} // end of class