package com.classs.skhuter.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.classs.skhuter.R;
import com.classs.skhuter.domain.AccountingDTO;
import com.classs.skhuter.util.Connection;

public class AccountCustomDialog extends Dialog {

    // CUSTOM DIALOG drawable
    public final static int IMG_DIALOG= R.layout.custom_img_dialog;

    Context context;

    private TextView mTitleView;
    private ImageView mImgContentView;
    private Button mOkButton;
    private String mTitle;
    private String mImgPath;
    private AccountingDTO accountingDTO;

    private int dialogType;

    private View.OnClickListener mOkClickListener;

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
        mImgContentView = (ImageView) findViewById(R.id.ivImgContent);
        mOkButton = (Button) findViewById(R.id.btn_ok);

        // 제목과 내용을 생성자에서 셋팅한다.
        mTitleView.setText(mTitle);
        // 이미지 파일명 조정 : uuid앞에 붙은 /skhuter/s_ 자름
        mImgPath = mImgPath.substring(11);
        // 이미지 내용 채우기 : Glide 사용
        Glide.with(context).load(Connection.ADDRESS+Connection.IMG_UPLOAD+mImgPath).thumbnail(0.1f).error(R.drawable.error).into(mImgContentView);
        // 클릭 이벤트 셋팅
        if (mOkClickListener != null) {
            mOkButton.setOnClickListener(mOkClickListener);
        } else {

        }
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public AccountCustomDialog(Context context, String title,
                               String mImgPath, int dialogType,
                               View.OnClickListener okListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.mTitle = title;
        this.mImgPath = mImgPath;
        this.mOkClickListener = okListener;
        this.dialogType = dialogType;
    }
    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public AccountCustomDialog(Context context, AccountingDTO accountingDTO, int dialogType,
                               View.OnClickListener okListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.mTitle = accountingDTO.getContent();
        this.mImgPath = accountingDTO.getUuidName();
        this.mOkClickListener = okListener;
        this.accountingDTO = accountingDTO;
        this.dialogType = dialogType;
    }
}