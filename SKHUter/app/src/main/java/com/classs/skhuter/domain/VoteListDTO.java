package com.classs.skhuter.domain;

/**
 * 투표 내역 관련 객체 선언 및 getter(), setter() 메소드 정의
 *
 * @패키지 : com.classs.skhuter.user.domain
 * @파일명 : VoteListDTO.java
 * @작성자 : 이종윤
 * @작성일 : 2017. 10. 07
 *
 */
public class VoteListDTO {
	
	private int voteListNo;
	private int voteNo;
	private int userNo;
	private int isVote;

	public int getIsVote() {
		return isVote;
	}
	public void setIsVote(int isVote) {
		this.isVote = isVote;
	}
	private String selectItem;
	public int getVoteListNo() {
		return voteListNo;
	}
	public void setVoteListNo(int voteListNo) {
		this.voteListNo = voteListNo;
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
	public String getSelectItem() {
		return selectItem;
	}
	public void setSelectItem(String selectItem) {
		this.selectItem = selectItem;
	}

	@Override
	public String toString() {
		return "VoteListDTO [voteListNo=" + voteListNo + ", voteNo=" + voteNo + ", userNo=" + userNo + ", selectItem="
				+ selectItem + "]";
	}
	
}
