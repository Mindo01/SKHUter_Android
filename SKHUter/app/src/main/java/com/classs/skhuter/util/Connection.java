package com.classs.skhuter.util;

import com.classs.skhuter.domain.UserDTO;

/**
 * Created by 김민도 on 2017-10-08.
 * @패키지 : com.classs.skhuter.util
 * @파일명 : Connection
 * @작성자 : 김민주
 * @작성일 : 2017. 10. 08.
 * @설명 : 내부IP주소 및 각 컨트롤러 URI 저장소
 */

public class Connection {
    public static UserDTO loginUser = new UserDTO();
    // 내부 IP주소
    public static final String ADDRESS = "http://192.168.10.2"; // 영등포구청 사무실
//    public static final String ADDRESS = "http://192.168.10.5"; // 영등포구청 사무실
//    public static final String ADDRESS = "http://192.168.55.149"; // 종윤 집
//    public static final String ADDRESS = "http://172.30.2.104"; // 학교

    // 이미지 업로드 경로 : 회계내역
    public static final String IMG_UPLOAD = "/resources/upload/skhuter/";
    // 파일 업로드 경로 : 회의록
    public static final String FILE_UPLOAD = "/resources/upload/skhuter/";
    // 파일 다운로드 경로 : 회의록 - 앱 externalStorageDirectory 하위 폴더
    public static final String FILE_DOWNLOAD = "skhuter";

    // 회원가입
    public static final String GET_REGIST = "/user/registerMobile";

    // 로그인
    public static final String GET_Login = "/user/loginMobile";

    // 학생 목록
    public static final String GET_USER_LIST = "/user/userListMobile";
    // 회의록
    public static final String GET_MEETING_NOTE = "/council/meetingNoteMobile";
    // 회계 내역
    public static final String GET_ACCOUNTING = "/notice/accountingListMobile";
    // 투표
    public static final String GET_VOTE_LIST = "/notice/voteListMobile";
    public static final String DO_VOTE = "/notice/doVoteMobile";
    // 학생회 일정
    public static final String GET_COUNCIL_SCHEDULE = "/council/scheduleMobile";
    // 학사 일정
    public static final String GET_STU_SCHEDULE = "/notice/stuScheduleMobile";

    // 익명게시판
    public static final String GET_VIEW_BOARD = "/board/boardListWV";
    public static final String GET_VIEW_NOTICE = "/notice/noticeListM";


    // SHARED PREFERENCE 설정값들
    public static final String SH_UNAME = "LoginSettings.dat";

    public static final String SH_LOGIN_ID = "LoginId";
    public static final String SH_LOGIN_PW = "LoginPassword";
    public static final String SH_LOGIN_NAME = "LoginName";
    public static final String SH_AUTO_LOGIN = "AutoLogin";
}
