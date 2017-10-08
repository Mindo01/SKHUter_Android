package com.classs.skhuter.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.classs.skhuter.R;
import com.classs.skhuter.adapter.AccountingAdapter;
import com.classs.skhuter.domain.AccountingDTO;

import java.util.ArrayList;
import java.util.List;


public class AccountFragment extends Fragment {

    List<AccountingDTO> accountingDTOList = new ArrayList<AccountingDTO>();
    ListView listView;
    AccountingAdapter accountingAdapter;

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account, container, false);

        // TODO 임시 데이터로 확인 : 추후 컨트롤러에서 받아온 리스트 사용
        for (int i = 1; i < 16; i++) {
            AccountingDTO accountingEX = new AccountingDTO();

            accountingEX.setContent("회계 내역 " + i);
            accountingEX.setAccountDate("2017-10-08");
            accountingEX.setPrice(50000);

            accountingDTOList.add(accountingEX);
        }

        listView = (ListView) v.findViewById(R.id.lvAccountingList);
        listView.setOnItemClickListener(mOnItemClickListener);
        accountingAdapter = new AccountingAdapter(getActivity().getApplicationContext(),
                R.layout.item_accounting,
                accountingDTOList);
        listView.setAdapter(accountingAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AccountingDTO nBean = accountingDTOList.get(position);
            // 아이템 클릭 시 이벤트 작성

        }
    }; // end of ItemClickListener
}
