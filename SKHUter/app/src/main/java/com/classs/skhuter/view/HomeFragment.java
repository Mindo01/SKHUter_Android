package com.classs.skhuter.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.classs.skhuter.R;
import com.classs.skhuter.util.Connection;

public class HomeFragment extends Fragment {

    TextView tvName;
    Button goNoticeBtn, goVoteBtn, goBoardBtn;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        tvName = (TextView)v.findViewById(R.id.tvName);
        goNoticeBtn = (Button)v.findViewById(R.id.goNoticeBtn);
        goVoteBtn = (Button)v.findViewById(R.id.goVoteBtn);
        goBoardBtn = (Button)v.findViewById(R.id.goBoardBtn);

        tvName.setText(Connection.loginUser.getName());
        goNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.moveToFragment(R.id.nav_notice);
            }
        });
        goVoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.moveToFragment(R.id.nav_vote);
            }
        });
        goBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.moveToFragment(R.id.nav_board);
            }
        });

        return v;
    }
}
