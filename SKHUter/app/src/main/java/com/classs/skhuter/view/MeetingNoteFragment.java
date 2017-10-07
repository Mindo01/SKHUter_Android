package com.classs.skhuter.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.classs.skhuter.R;
import com.classs.skhuter.adapter.MeetingNoteAdapter;
import com.classs.skhuter.domain.MeetingNoteDTO;

import java.util.ArrayList;
import java.util.List;

public class MeetingNoteFragment extends Fragment {

    List<MeetingNoteDTO> meetingNoteDTOList = new ArrayList<MeetingNoteDTO>();
    ListView listView;
    MeetingNoteAdapter meetingNoteAdapter;

    public MeetingNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_meeting_note, container, false);

        // TODO 임시 데이터로 확인 : 추후 컨트롤러에서 받아온 리스트 사용
        // TODO : 유저 번호로 회원 이름 검색해서 작성자 제대로 보이게 해주기
        for (int i = 1; i < 16; i++) {
            MeetingNoteDTO meetingNoteEx = new MeetingNoteDTO();

            meetingNoteEx.setTitle("회의록 제목" + i);
            meetingNoteEx.setUserNo(1);

            meetingNoteDTOList.add(meetingNoteEx);
        }

        listView = (ListView) v.findViewById(R.id.lvMeetingNoteList);
        listView.setOnItemClickListener(mOnItemClickListener);
        meetingNoteAdapter = new MeetingNoteAdapter(getActivity().getApplicationContext(),
                R.layout.item_meeting_note,
                meetingNoteDTOList);
        listView.setAdapter(meetingNoteAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MeetingNoteDTO nBean = meetingNoteDTOList.get(position);
            // 아이템 클릭 시 이벤트 작성

        }
    }; // end of ItemClickListener
}
