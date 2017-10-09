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

public class NoticeFragment extends Fragment {
    Context context;
    WebView wvNotice;
    public NoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notice, container, false);
        context = getActivity().getApplicationContext();

        wvNotice = (WebView)v.findViewById(R.id.wvNotice);
        //자바스트립트 사용 셋팅
        wvNotice.getSettings().setLoadWithOverviewMode(true); // 웹뷰에서 페이지가 확대되는 문제해결
        wvNotice.getSettings().setUseWideViewPort(true);
        wvNotice.setInitialScale(1); // 기기별 화면사이트에 맞게 조절
        wvNotice.setWebViewClient(new WebViewClientHandler());
        wvNotice.getSettings().setJavaScriptEnabled(true);
        wvNotice.loadUrl(Connection.ADDRESS+Connection.GET_VIEW_NOTICE);

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
