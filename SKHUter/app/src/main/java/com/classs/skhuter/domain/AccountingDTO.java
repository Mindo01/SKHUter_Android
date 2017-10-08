package com.classs.skhuter.domain;

import java.io.Serializable;

/**
 * 회계 내역 관련 객체 선언 및 getter(), setter() 메소드 정의
 *
 * @패키지 : com.classs.skhuter.domain
 * @파일명 : AccountingDTO.java
 * @작성자 : 이종윤
 * @작성일 : 2017. 10. 08.
 *
 */
public class AccountingDTO implements Serializable {
	private int accountNo;
	private int userNo;
	private String accountDate;
	private String content;
	private int status;
	private int price;
	private String fileName;
	private String uuidName;

	public AccountingDTO() {}

	public AccountingDTO(int accountNo, int userNo, String accountDate, String content, int status, int price, String fileName, String uuidName) {
		this.accountNo = accountNo;
		this.userNo = userNo;
		this.accountDate = accountDate;
		this.content = content;
		this.status = status;
		this.price = price;
		this.fileName = fileName;
		this.uuidName = uuidName;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getAccountDate() {
		return accountDate;
	}

	public void setAccountDate(String accountDate) {
		this.accountDate = accountDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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

	@Override
	public String toString() {
		return "AccountingDTO [accountNo=" + accountNo + ", userNo=" + userNo + ", accountDate=" + accountDate
				+ ", content=" + content + ", status=" + status + ", price=" + price + ", fileName=" + fileName
				+ ", uuidName=" + uuidName + "]";
	}
}
