package com.classs.skhuter.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.classs.skhuter.R;
import com.classs.skhuter.util.Connection;

public class BoardFragment extends Fragment {
    Context context;
    WebView wvBoard;

    public BoardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_board, container, false);
        context = getActivity().getApplicationContext();

        wvBoard = (WebView)v.findViewById(R.id.wvBoard);
        //자바스트립트 사용 셋팅
        wvBoard.getSettings().setLoadWithOverviewMode(true); // 웹뷰에서 페이지가 확대되는 문제해결
        wvBoard.getSettings().setUseWideViewPort(true);
        wvBoard.setInitialScale(1); // 기기별 화면사이트에 맞게 조절

        // 화면 zoom 허용
        wvBoard.getSettings().setBuiltInZoomControls(true);
        wvBoard.getSettings().setDisplayZoomControls(false);

        wvBoard.setWebViewClient(new WebViewClientHandler());

        // userNo와 status(학생등급)를 파라미터로 전송함
        String PARAMS = "?userNo="+Connection.loginUser.getUserNo()
                +"&status="+Connection.loginUser.getStatus();

        wvBoard.loadUrl(Connection.ADDRESS+Connection.GET_VIEW_BOARD+PARAMS);

        wvBoard.getSettings().setJavaScriptEnabled(true);

        // Inflate the layout for this fragment
        return v;
    }

    class WebViewClientHandler extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }
        @Override
        public void onPageFinished(WebView view, String url) {
        }
    }

}
