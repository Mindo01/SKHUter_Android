package com.classs.skhuter.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.classs.skhuter.R;
import com.classs.skhuter.adapter.UserListAdapter;
import com.classs.skhuter.domain.UserDTO;

import java.util.ArrayList;
import java.util.List;


public class UserListFragment extends Fragment {

    List<UserDTO> userDTOList = new ArrayList<UserDTO>();
    ListView listView;
    UserListAdapter userListAdapter;
    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_list, container, false);

        // TODO 임시 데이터로 확인 : 추후 컨트롤러에서 받아온 리스트 사용
        for (int i = 1; i < 16; i++) {
            UserDTO userEx = new UserDTO();
            userEx.setId("20133400"+i);
            userEx.setGrade((i%4)+1);
            userEx.setName("학생"+i);
            userDTOList.add(userEx);
        }

        listView = (ListView) v.findViewById(R.id.lvUserList);
        listView.setOnItemClickListener(mOnItemClickListener);
        userListAdapter = new UserListAdapter(getActivity().getApplicationContext(),
                R.layout.item_user_list,
                userDTOList);
        listView.setAdapter(userListAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UserDTO nBean = userDTOList.get(position);
            // 아이템 클릭 시 이벤트 작성

        }
    }; // end of ItemClickListener
}
