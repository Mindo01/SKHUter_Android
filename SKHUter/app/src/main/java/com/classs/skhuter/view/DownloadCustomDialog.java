package com.classs.skhuter.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.classs.skhuter.R;
import com.classs.skhuter.util.Connection;
import com.classs.skhuter.util.MyFile;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class DownloadCustomDialog extends Dialog {

    // CUSTOM DIALOG drawable
    public final static int DOWNLOAD_DIALOG = R.layout.custom_download_dialog;

    public final static DecimalFormat DECIMAL_POINT = new DecimalFormat("0.0");

    DownloadCustomDialog dialog;

    private TextView mTitleView;
    private TextView mContentView;
    private ProgressBar mDownloaderPb;
    private TextView mDownloaderView;
    private Button mDownloadButton;
    private Button mCancelButton;
    private String mTitle;
    private String mContent;
    private MyFile myFile;

    private int dialogType;

    private View.OnClickListener mDownloadClickListener;
    private View.OnClickListener mCancelClickListener;

    // 파일 다운로더 요소들 : DownloadManager
    private long latestId = -1;
    private DownloadManager downloadManager;
    private DownloadManager.Request request;
    private Uri urlToDownload;
    private File mSaveDir;

    Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = this;
        activity = MainActivity.activity;

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(dialogType);

        mTitleView = (TextView) findViewById(R.id.tvTitle);
        mContentView = (TextView) findViewById(R.id.tvContent);
        mDownloaderPb = (ProgressBar) findViewById(R.id.pbDownloader);
        mDownloaderView = (TextView) findViewById(R.id.tvDownloader);
        mDownloadButton = (Button) findViewById(R.id.btn_download);
        mCancelButton = (Button) findViewById(R.id.btn_cancel);

        // 제목과 내용을 생성자에서 셋팅한다.
        mTitleView.setText(mTitle);
        if (mContent != null && !"".equals(mContent)) {
            mContentView.setText(mContent);
        }

        //////////////////// 다운로더 설정들 - 시작 ////////////////////////
        // 저장할 위치 설정 - Connection.FILE_DOWNLOAD : 안드로이드 기기 내 다운로드 될 폴더명
       mSaveDir = new File(Environment.getExternalStorageDirectory(), Connection.FILE_DOWNLOAD);
        // 존재하지 않는 폴더일 경우 생성
        if (!mSaveDir.exists()) mSaveDir.mkdir();

        downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        //////////////////// 다운로더 설정들 - 끝 ////////////////////////

        // 클릭 이벤트 셋팅
        if (mDownloadClickListener == null) {
            mDownloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 다운로드
                    doDownload();
                }
            });
        } else {
            mDownloadButton.setOnClickListener(mDownloadClickListener);
        }
        if (mCancelClickListener == null) {
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 다이얼로그 dismiss()
                    dialog.dismiss();
                }
            });
        } else {
            mCancelButton.setOnClickListener(mCancelClickListener);
        }
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public DownloadCustomDialog(Context context, String title,
                                String content, int dialogType,
                                View.OnClickListener okListener,
                                View.OnClickListener cancelListener) {
        super(context.getApplicationContext(), android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.mTitle = title;
        this.mContent = content;
        this.mDownloadClickListener = okListener;
        this.mCancelClickListener = cancelListener;
        this.dialogType = dialogType;
    }
    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public DownloadCustomDialog(Context context, MyFile myFile, int dialogType,
                                View.OnClickListener downloadListener,
                                View.OnClickListener cancelListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.mTitle = myFile.getFileName();
        this.mContent = "";
        this.mDownloadClickListener = downloadListener;
        this.mCancelClickListener = cancelListener;
        this.myFile = myFile;
        this.dialogType = dialogType;
    }

    /**
     * 다운로더 초기화 작업
     */
    // BroadcastReceiver 설정 - 다운로드 완료시 호출
    public BroadcastReceiver completeReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            // check whether the download-id is mine
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
            if (id != latestId) {
                // not our download id, ignore
                return;
            }

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            // make a query
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id);

            // check the status
            Cursor cursor = downloadManager.query(query);
            if (cursor.moveToFirst()) {
                // when download completed
                int statusColumn = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (DownloadManager.STATUS_SUCCESSFUL != cursor.getInt(statusColumn)) {
                    Log.w("DownloadDialog", "Download Failed");
                    return;
                }

                int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                String downloadedPackageUriString = cursor.getString(uriIndex);

            }else{
                // when canceled
                return;
            }
            mContentView.setText("다운로드가 완료되었습니다");
            Toast.makeText(context, "다운로드가 완료되었습니다.",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }

    };

    /**
     * 다운로드 진행 작업
     */
    private void doDownload() {
        urlToDownload = Uri.parse(Connection.ADDRESS+Connection.FILE_UPLOAD+myFile.getFilePath());
        List<String> pathSegments = urlToDownload.getPathSegments();
        request = new DownloadManager.Request(urlToDownload);
        request.setTitle(myFile.getFileName());
        request.setDescription(myFile.getFileName()+" 다운로드 중...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);

        // 권한 확인
        if (haveStoragePermission()) {
            if (android.os.Environment.getExternalStorageState()
                    .equals(android.os.Environment.MEDIA_MOUNTED)) {
                request.setDestinationInExternalPublicDir(
                        android.os.Environment.DIRECTORY_DOWNLOADS, myFile.getFileName());
            } else {
                Log.e("다운로드 시도", "NOPE");
            }

            //request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pathSegments.get(pathSegments.size()-1));
            Log.e("PUBLIC_dir_downloads", Environment.DIRECTORY_DOWNLOADS);

            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdirs();
            latestId = downloadManager.enqueue(request);
        } else {
            Toast.makeText(context, "다운로드 권한이 거부되었습니다", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    /**
     * 쓰기 권한 요청 작업
     */
    public  boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            return true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter completeFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(completeReceiver, completeFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        context.unregisterReceiver(completeReceiver);
    }
}