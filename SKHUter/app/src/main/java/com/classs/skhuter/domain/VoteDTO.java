package com.classs.skhuter.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 투표 관련 객체 선언 및 getter(), setter() 메소드 정의
 *
 * @패키지 : com.classs.skhuter.domain
 * @파일명 : VoteDTO.java
 * @작성자 : 이종윤
 * @작성일 : 2017. 10. 07
 *
 */
public class VoteDTO implements Serializable {

	private int voteNo;
	private int userNo;
	private Date enrollDate;
	private String startDate;
	private String endDate;
	private String title;
	private String content;
	private String item1;
	private String item2;
	private String item3;
	private String item4;
	private String item5;
	private String item6;
	private int item1Count;
	private int item2Count;
	private int item3Count;
	private int item4Count;
	private int item5Count;
	private int item6Count;
	private int isVote;
	private int joinCount;

	// 구분을 위한 변수 : DTO가 아님
	private boolean isDone;
	
	
	public int getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(int joinCount) {
		this.joinCount = joinCount;
	}
	public int getIsVote() {
		return isVote;
	}
	public void setIsVote(int isVote) {
		this.isVote = isVote;
	}
	public int getItem1Count() {
		return item1Count;
	}
	public void setItem1Count(int item1Count) {
		this.item1Count = item1Count;
	}
	public int getItem2Count() {
		return item2Count;
	}
	public void setItem2Count(int item2Count) {
		this.item2Count = item2Count;
	}
	public int getItem3Count() {
		return item3Count;
	}
	public void setItem3Count(int item3Count) {
		this.item3Count = item3Count;
	}
	public int getItem4Count() {
		return item4Count;
	}
	public void setItem4Count(int item4Count) {
		this.item4Count = item4Count;
	}
	public int getItem5Count() {
		return item5Count;
	}
	public void setItem5Count(int item5Count) {
		this.item5Count = item5Count;
	}
	public int getItem6Count() {
		return item6Count;
	}
	public void setItem6Count(int item6Count) {
		this.item6Count = item6Count;
	}
	
	
	
	public int getVoteNo() {
		return voteNo;
	}
	public void setVoteNo(int voteNo) {
		this.voteNo = voteNo;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getItem1() {
		return item1;
	}
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	public String getItem2() {
		return item2;
	}
	public void setItem2(String item2) {
		this.item2 = item2; 
	}
	public String getItem3() {
		return item3;
	}
	public void setItem3(String item3) {
		this.item3 = item3;
	}
	public String getItem4() {
		return item4;
	}
	public void setItem4(String item4) {
		this.item4 = item4;
	}
	public String getItem5() {
		return item5;
	}
	public void setItem5(String item5) {
		this.item5 = item5;
	}
	public String getItem6() {
		return item6;
	}
	public void setItem6(String item6) {
		this.item6 = item6;
	}

	public boolean getIsDone() { return isDone; }
	public void setIsDone(boolean isDone) { this.isDone = isDone; }

	@Override
	public String toString() {
		return "VoteDTO [voteNo=" + voteNo + ", userNo=" + userNo + ", enrollDate=" + enrollDate + ", startDate="
				+ startDate + ", endDate=" + endDate + ", title=" + title + ", content=" + content + ", item1=" + item1
				+ ", item2=" + item2 + ", item3=" + item3 + ", item4=" + item4 + ", item5=" + item5 + ", item6=" + item6
				+ ", item1Count=" + item1Count + ", item2Count=" + item2Count + ", item3Count=" + item3Count
				+ ", item4Count=" + item4Count + ", item5Count=" + item5Count + ", item6Count=" + item6Count + "]";
	}
}
