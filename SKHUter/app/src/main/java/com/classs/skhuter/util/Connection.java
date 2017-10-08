package com.classs.skhuter.util;

/**
 * Created by 김민도 on 2017-10-08.
 * @패키지 : com.classs.skhuter.util
 * @파일명 : Connection
 * @작성자 : 김민주
 * @작성일 : 2017. 10. 08.
 * @설명 : 내부IP주소 및 각 컨트롤러 URI 저장소
 */

public class Connection {
    // 내부 IP주소
    public static final String ADDRESS = "http://192.168.10.5";

    // 학생 목록
    public static final String GET_USER_LIST = "/user/userListMobile";
    // 회의록
    public static final String GET_MEETING_NOTE = "/council/meetingNoteMobile";
    // 회계 내역
    public static final String GET_ACCOUNTING = "/notice/accountingListMobile";
    // 투표
    public static final String GET_VOTE_LIST = "/notice/VoteListMobile";
}
