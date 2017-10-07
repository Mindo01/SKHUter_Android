package com.classs.skhuter.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.classs.skhuter.R;
import com.classs.skhuter.adapter.VoteListAdapter;
import com.classs.skhuter.domain.VoteDTO;

import java.util.ArrayList;
import java.util.List;

public class VoteFragment extends Fragment {

    List<VoteDTO> voteDTOList = new ArrayList<VoteDTO>();
    ListView listView;
    VoteListAdapter voteListAdapter;
    public VoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_vote, container, false);

        // TODO 임시 데이터로 확인 : 추후 컨트롤러에서 받아온 리스트 사용
        for (int i = 1; i < 16; i++) {
            VoteDTO voteEx = new VoteDTO();

            voteEx.setTitle("투표 제목" + i);
            voteEx.setStartDate("2017-10-05");
            voteEx.setEndDate("2017-10-07");
            voteEx.setJoinCount(13);

            voteDTOList.add(voteEx);
        }

        listView = (ListView) v.findViewById(R.id.lvVoteList);
        listView.setOnItemClickListener(mOnItemClickListener);
        voteListAdapter = new VoteListAdapter(getActivity().getApplicationContext(),
                R.layout.item_vote_list,
                voteDTOList);
        listView.setAdapter(voteListAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            VoteDTO nBean = voteDTOList.get(position);
            // 아이템 클릭 시 이벤트 작성

        }
    }; // end of ItemClickListener

}
