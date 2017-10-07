package com.classs.skhuter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import com.classs.skhuter.R;
import com.classs.skhuter.domain.UserDTO;

import java.util.List;

/**
 * 학생 목록 어댑터 - 리스트뷰
 *
 * @패키지 : com.classs.skhuter.adapter
 * @파일명 : UserListAdapter.java
 * @작성자 : 김민주
 * @작성일 : 2017. 10. 06
 *
 */

public class UserListAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<UserDTO> userDTOList;
    LayoutInflater inflater;

    public UserListAdapter(Context context, int layout, List<UserDTO> restaList) {
        this.context = context;
        this.layout = layout;
        this.userDTOList = restaList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    } // Constructor

    @Override
    public int getCount() {
        return userDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return userDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_user_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvUserSSN = (TextView) convertView.findViewById(R.id.tvUserSSN);
            viewHolder.tvUserGrade = (TextView) convertView.findViewById(R.id.tvUserGrade);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UserDTO user = (UserDTO) getItem(position);
        //Glide.with(context).load("http://ldh66210.cafe24.com/upload/"+nBean.getImgPath()).thumbnail(0.1f).error(R.drawable.ic_group).into(viewHolder.ivNewsImg);
        viewHolder.tvUserName.setText(user.getName());
        viewHolder.tvUserSSN.setText(user.getId());
        viewHolder.tvUserGrade.setText(user.getGrade() + "학년");
        return convertView;
    }

    static class ViewHolder {
        public ImageView ivProfile;
        public TextView tvUserName;
        public TextView tvUserSSN;
        public TextView tvUserGrade;
    } // end of inner class ViewHolder
} // end of class