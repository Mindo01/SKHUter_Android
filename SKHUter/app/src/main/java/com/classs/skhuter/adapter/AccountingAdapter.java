package com.classs.skhuter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.classs.skhuter.R;
import com.classs.skhuter.domain.AccountingDTO;

import java.util.List;

/**
 * 회계 목록 어댑터 - 리스트뷰
 *
 * @패키지 : com.classs.skhuter.adapter
 * @파일명 : AccountingAdapter.java
 * @작성자 : 이종윤
 * @작성일 : 2017. 10. 08
 *
 */

public class AccountingAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<AccountingDTO> accountingDTOList;
    LayoutInflater inflater;

    public AccountingAdapter(Context context, int layout, List<AccountingDTO> restaList) {
        this.context = context;
        this.layout = layout;
        this.accountingDTOList = restaList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    } // Constructor

    @Override
    public int getCount() {
        return accountingDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return accountingDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_accounting, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvAccountingTitle = (TextView) convertView.findViewById(R.id.tvAccountingTitle);
            viewHolder.tvAccountingDate = (TextView) convertView.findViewById(R.id.tvAccountingDate);
            viewHolder.tvMoney = (TextView) convertView.findViewById(R.id.tvMoney);

            AccountingDTO accounting = (AccountingDTO) getItem(position);
            viewHolder.tvAccountingTitle.setText(accounting.getContent());
            viewHolder.tvAccountingDate.setText(accounting.getAccountDate());
            viewHolder.tvMoney.setText(accounting.getPrice() +"원");
            viewHolder.price = accounting.getPrice();
            viewHolder.status = accounting.getStatus();

            if (viewHolder.status == 0) {
                // 수입
                viewHolder.tvMoney.setTextColor(Color.parseColor("#3F51B5"));
            } else {
                // 지출
                viewHolder.tvMoney.setTextColor(Color.parseColor("#FF0000"));
            }

            viewHolder.fileName = accounting.getFileName();
            viewHolder.uuidName = accounting.getUuidName();

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView tvAccountingTitle;
        public TextView tvAccountingDate;
        public TextView tvMoney;
        public int status;
        public int price;
        public String fileName;
        public String uuidName;
    } // end of inner class ViewHolder
} // end of class