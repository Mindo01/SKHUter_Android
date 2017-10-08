package com.classs.skhuter.domain;
import java.io.Serializable;
import java.util.Date;

/**
 * 회의록 관련 객체 선언 및 getter(), setter() 메소드 정의
 *
 * @패키지 : com.classs.skhuter.user.domain
 * @파일명 : MeetingNoteDTO.java
 * @작성자 : 이종윤
 * @작성일 : 2017. 10. 07
 *
 */
public class MeetingNoteDTO implements Serializable {
	private int meetingNoteNo;
	private int userNo;
	private String title;
	private String fileName;
	private String uuidName;
	private String originName;
	private Date regdate;
	
	public MeetingNoteDTO() {}

	public MeetingNoteDTO(int meetingNoteNo, int userNo, String title, String fileName, String uuidName,String originName, Date regdate) {
		this.meetingNoteNo = meetingNoteNo;
		this.userNo = userNo;
		this.title = title;
		this.fileName = fileName;
		this.uuidName = uuidName;
		this.originName = originName;
		this.regdate = regdate;
	}

	public int getMeetingNoteNo() {
		return meetingNoteNo;
	}

	public void setMeetingNoteNo(int meetingNoteNo) {
		this.meetingNoteNo = meetingNoteNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUuidName() {
		return uuidName;
	}

	public void setUuidName(String uuidName) {
		this.uuidName = uuidName;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	@Override
	public String toString() {
		return "MeetingNoteDTO [meetingNoteNo=" + meetingNoteNo + ", userNo=" + userNo + ", title=" + title
				+ ", fileName=" + fileName + ", uuidName=" + uuidName + ", originName=" + originName + ", regdate="
				+ regdate + "]";
	}
}
