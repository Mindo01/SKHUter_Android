package com.classs.skhuter.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.classs.skhuter.R;
import com.classs.skhuter.adapter.VoteAdapter;
import com.classs.skhuter.domain.VoteDTO;
import com.classs.skhuter.util.Connection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VoteFragment extends Fragment {

    List<VoteDTO> voteDTOList = new ArrayList<VoteDTO>();
    ListView listView;
    TextView tvError;
    VoteAdapter voteAdapter;
    Handler handler = new Handler();
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

        listView = (ListView) v.findViewById(R.id.lvVoteList);
        tvError = (TextView) v.findViewById(R.id.tvError);

        getVoteList();

        listView.setOnItemClickListener(mOnItemClickListener);
        voteAdapter = new VoteAdapter(getActivity().getApplicationContext(),
                R.layout.item_vote,
                voteDTOList);
        listView.setAdapter(voteAdapter);

        // Inflate the layout for this fragment
        return v;
    }

    /**
     * 투표 목록을 Volley로 데이터 받는 메소드
     */
    void getVoteList() {
        tvError.setVisibility(View.GONE);
        // 리스트 초기화 후 목록 받아와야 함
        voteDTOList.clear();
        // volley HTTP request 큐 선언
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        // 전송할 파라미터 설정
        String PARAMS = "?userNo="+Connection.loginUser.getUserNo();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Connection.ADDRESS+Connection.GET_VOTE_LIST+PARAMS, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                Log.d("MyLog", "response : " + obj.toString());
                // 받아온 JSON 데이터 값을 객체에 넣어 리스트로 만들기
                JsonParser parser = new JsonParser();
                JsonObject rootObj = (parser.parse(obj.toString())).getAsJsonObject();

                // Timestamp(Long) & String to Date Parsing GSONBuilder 등록
                GsonBuilder builder = new GsonBuilder();
                builder.setDateFormat("yyyy-MM-dd");
                builder.registerTypeAdapter(Date.class, new JsonDeserializer() {
                    @Override
                    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return new Date(json.getAsJsonPrimitive().getAsLong());
                    }
                });
                Gson gson = builder.create();

                // "doneVoteList", "doingVoteList" 키에 해당하는 값을 JSON 배열 형식으로 받기
                JsonArray doneVoteList = rootObj.getAsJsonArray("doneVoteList");
                JsonArray doingVoteList = rootObj.getAsJsonArray("doingVoteList");
                // "list"목록의 각 아이템을 객체로 변환 후 voteDTOList 추가
                if (doingVoteList != null && (doingVoteList.size() > 0) ) {
                    for (int i = 0; i < doingVoteList.size(); i++) {
                        // JSON Object를 VoteDTO 객체로 변환
                        VoteDTO voteDTO = gson.fromJson(doingVoteList.get(i), VoteDTO.class);
                        voteDTO.setIsDone(false);
                        // voteDTOList VoteDTO 객체 추가
                        voteDTOList.add(voteDTO);
                    } // end of for

                }
                if (doneVoteList != null && (doneVoteList.size() > 0) ) {
                    for (int i = 0; i < doneVoteList.size(); i++) {
                        // JSON Object를 VoteDTO 객체로 변환
                        VoteDTO voteDTO = gson.fromJson(doneVoteList.get(i), VoteDTO.class);
                        voteDTO.setIsDone(true);
                        // voteDTOList VoteDTO 객체 추가
                        voteDTOList.add(voteDTO);
                    } // end of for
                }
                // Adapter 변경사항 갱신
                voteAdapter.notifyDataSetChanged();

                // 데이터가 없을 때
                if (voteDTOList.size() <= 0) {
                    tvError.setText("투표가 존재하지 않습니다");
                    tvError.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("MyLog", "error : " + error);
                Log.d("MyLog", "error : " + error.getMessage());
                Log.d("MyLog", "error : " + error.getLocalizedMessage());
                final VolleyError err = error;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MyLog", "error : " + err);
                        tvError.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        requestQueue.add(request);
    }

    /**
     * 투표 참여를 Volley로 데이터 보내는 메소드
     */
    void sendVoteResult(VoteDTO vote) {
        getVoteList();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        int[] itemCounts = {vote.getItem1Count(), vote.getItem2Count(), vote.getItem3Count(), vote.getItem4Count(), vote.getItem5Count(), vote.getItem6Count()};
        itemCounts[vote.getSelectedItem()-1]++;
        String PARAMS = "?userNo="+Connection.loginUser.getUserNo()
                        +"&voteNo="+vote.getVoteNo()
                        +"&item1Count="+itemCounts[0]
                        +"&item2Count="+itemCounts[1]
                        +"&item3Count="+itemCounts[2]
                        +"&item4Count="+itemCounts[3]
                        +"&item5Count="+itemCounts[4]
                        +"&item6Count="+itemCounts[5];
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Connection.ADDRESS+Connection.DO_VOTE+PARAMS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {
                        Toast.makeText(getActivity().getApplicationContext(), "투표에 참여했습니다", Toast.LENGTH_SHORT).show();
                        getVoteList();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("MyLog", "error : " + error);
                final VolleyError err = error;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("MyLog", "error : " + err);
                        tvError.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        requestQueue.add(request);
    }

    VoteCustomDialog resultDialog;
    VoteCustomDialog radioDialog;
    VoteCustomDialog limitDialog;

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final VoteDTO vote = voteDTOList.get(position);
            // 아이템 클릭 시 이벤트 작성
            if (vote.getIsDone()) {
                // 이미 종료된 투표
                resultDialog = new VoteCustomDialog(getActivity(),
                        vote,
                        VoteCustomDialog.RESULT_DIALOG,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                resultDialog.dismiss();
                            }
                        }, null);
                resultDialog.show();
            } else if (!vote.getIsStart()) {
                // 시작 예정인 투표
                limitDialog = new VoteCustomDialog(getActivity(),
                        "투표 제한 알림", // 제목
                        "["+vote.getTitle()+"] : 아직 시작되지 않은 투표입니다.", // 내용
                        VoteCustomDialog.LIMIT_DIALOG,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                limitDialog.dismiss();
                            }
                        }, null);
                limitDialog.show();
            } else if (vote.getIsVote() > 0) {
                // 이미 참여한 투표
                limitDialog = new VoteCustomDialog(getActivity(),
                        "투표 제한 알림", // 제목
                        "["+vote.getTitle()+"] : 이미 참여한 투표입니다.", // 내용
                        VoteCustomDialog.LIMIT_DIALOG,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                limitDialog.dismiss();
                            }
                        }, null);
                limitDialog.show();
            } else {
                    // 진행 중인 투표
                    radioDialog = new VoteCustomDialog(getActivity(),
                            vote,
                            VoteCustomDialog.RADIO_DIALOG,
                            new View.OnClickListener() {
                                public void onClick(View v) {
                                    // 투표 등록
                                    if (vote.getSelectedItem() <= 0) {
                                        // 선택한 항목이 없으면
                                        Toast.makeText(getActivity().getApplicationContext(), "선택한 항목이 없습니다", Toast.LENGTH_SHORT).show();
                                        return ;
                                    }
                                    sendVoteResult(vote);
                                    radioDialog.dismiss();
                                }
                            },
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    radioDialog.dismiss();
                                }
                            });
                    radioDialog.show();
                }
            }
    }; // end of ItemClickListener

}
