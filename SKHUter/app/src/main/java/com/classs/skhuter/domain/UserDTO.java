package com.classs.skhuter.domain;
import java.io.Serializable;
import java.sql.Date;

/**
 * 유저 관련 객체 선언 및 getter(), setter() 메소드 정의
 *
 * @패키지 : com.classs.skhuter.domain
 * @파일명 : UserDTO.java
 * @작성자 : 김민주
 * @작성일 : 2017. 10. 06
 *
 */
public class UserDTO implements Serializable {
    private int userNo;
    private String id;
    private String password;
    private String name;
    private String phone;
    private int grade;
    private int isLeave;
    private int status;
    private int takeoverCode;
    private Date recentLogin;
    private String sessionKey;
    private Date sessionLimit;
    private int secession;


    public UserDTO() {}


    public UserDTO(int userNo, String id, String password, String name, String phone, int grade, int isLeave,
                   int status, int takeoverCode, Date recentLogin, String sessionKey, Date sessionLimit, int secession) {
        this.userNo = userNo;
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.grade = grade;
        this.isLeave = isLeave;
        this.status = status;
        this.takeoverCode = takeoverCode;
        this.recentLogin = recentLogin;
        this.sessionKey = sessionKey;
        this.sessionLimit = sessionLimit;
        this.secession = secession;
    }


    public int getUserNo() {
        return userNo;
    }


    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public int getGrade() {
        return grade;
    }


    public void setGrade(int grade) {
        this.grade = grade;
    }


    public int getIsLeave() {
        return isLeave;
    }


    public void setIsLeave(int isLeave) {
        this.isLeave = isLeave;
    }


    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public int getTakeoverCode() {
        return takeoverCode;
    }


    public void setTakeoverCode(int takeoverCode) {
        this.takeoverCode = takeoverCode;
    }


    public Date getRecentLogin() {
        return recentLogin;
    }


    public void setRecentLogin(Date recentLogin) {
        this.recentLogin = recentLogin;
    }


    public String getSessionKey() {
        return sessionKey;
    }


    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }


    public Date getSessionLimit() {
        return sessionLimit;
    }


    public void setSessionLimit(Date sessionLimit) {
        this.sessionLimit = sessionLimit;
    }


    public int getSecession() {
        return secession;
    }


    public void setSecession(int secession) {
        this.secession = secession;
    }

    @Override
    public String toString() {
        return "UserDTO [userNo=" + userNo + ", id=" + id + ", password=" + password + ", name=" + name + ", phone="
                + phone + ", grade=" + grade + ", isLeave=" + isLeave + ", status=" + status + ", takeoverCode="
                + takeoverCode + ", recentLogin=" + recentLogin + ", sessionKey=" + sessionKey + ", sessionLimit="
                + sessionLimit + ", secession=" + secession + "]";
    }

}