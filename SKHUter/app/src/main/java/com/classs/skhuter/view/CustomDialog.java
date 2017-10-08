package com.classs.skhuter.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.classs.skhuter.R;
import com.classs.skhuter.domain.VoteDTO;

public class CustomDialog extends Dialog {

    // CUSTOM DIALOG drawable
    public final static int RADIO_DIALOG= R.layout.custom_radio_dialog;
    public final static int RESULT_DIALOG = R.layout.custom_result_dialog;
    public final static int LIMIT_DIALOG = R.layout.custom_limit_dialog;

    // RESULT_DIALOG
    private TextView[] tvItem = {(TextView)findViewById(R.id.tvItem1), (TextView)findViewById(R.id.tvItem2), (TextView)findViewById(R.id.tvItem3), (TextView)findViewById(R.id.tvItem4), (TextView)findViewById(R.id.tvItem5), (TextView)findViewById(R.id.tvItem6)};
    private ProgressBar[] pgItem = {(ProgressBar)findViewById(R.id.pbItem1), (ProgressBar)findViewById(R.id.pbItem2), (ProgressBar)findViewById(R.id.pbItem3), (ProgressBar)findViewById(R.id.pbItem4), (ProgressBar)findViewById(R.id.pbItem5), (ProgressBar)findViewById(R.id.pbItem6)};
    private TextView[] tvItemCount = {(TextView)findViewById(R.id.tvItemCount1), (TextView)findViewById(R.id.tvItemCount2), (TextView)findViewById(R.id.tvItemCount3), (TextView)findViewById(R.id.tvItemCount4), (TextView)findViewById(R.id.tvItemCount5), (TextView)findViewById(R.id.tvItemCount6)};

    // RADIO_DIALOG
    private RadioButton[] rbItem = {(RadioButton)findViewById(R.id.rb1), (RadioButton)findViewById(R.id.rb2), (RadioButton)findViewById(R.id.rb3), (RadioButton)findViewById(R.id.rb4), (RadioButton)findViewById(R.id.rb5), (RadioButton)findViewById(R.id.rb6)};

    private TextView mTitleView;
    private TextView mContentView;
    private Button mOkButton;
    private Button mCancelButton;
    private String mTitle;
    private String mContent;

    private int dialogType;

    private View.OnClickListener mOkClickListener;
    private View.OnClickListener mCancelClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(dialogType);

        mTitleView = (TextView) findViewById(R.id.tvTitle);
        mContentView = (TextView) findViewById(R.id.tvContent);
        mOkButton = (Button) findViewById(R.id.btn_ok);
        mCancelButton = (Button) findViewById(R.id.btn_cancel);

        // 제목과 내용을 생성자에서 셋팅한다.
        mTitleView.setText(mTitle);
        mContentView.setText(mContent);

        // 아이템 셋팅
        if (dialogType == RADIO_DIALOG) {
            // 투표 진행 다이얼로그
            // TODO
        } else if (dialogType == RESULT_DIALOG) {
            // 투표 종료 다이얼로그
            // TODO
        }

        // 클릭 이벤트 셋팅
        if (mOkClickListener != null && mCancelClickListener != null) {
            mOkButton.setOnClickListener(mOkClickListener);
            mCancelButton.setOnClickListener(mCancelClickListener);
        } else if (mOkClickListener != null
                && mCancelClickListener == null) {
            mOkButton.setOnClickListener(mOkClickListener);
        } else {

        }
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public CustomDialog(Context context, String title,
                        String content, int dialogType,
                        View.OnClickListener okListener,
                        View.OnClickListener cancelListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.mOkClickListener = okListener;
        this.mCancelClickListener = cancelListener;
        this.dialogType = dialogType;
    }
    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public CustomDialog(Context context, VoteDTO vote, int dialogType,
                        View.OnClickListener okListener,
                        View.OnClickListener cancelListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = vote.getTitle();
        this.mContent = vote.getContent();
        this.mOkClickListener = okListener;
        this.mCancelClickListener = cancelListener;

        this.dialogType = dialogType;
    }
}