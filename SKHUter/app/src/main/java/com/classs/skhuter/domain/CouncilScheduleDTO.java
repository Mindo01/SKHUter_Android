package com.classs.skhuter.domain;

import java.io.Serializable;

/**
 * Created by 김민도 on 2017-10-09.
 */
public class CouncilScheduleDTO implements Serializable {
    private int councilScheduleNo;
    private String content;
    private String startDate;
    private String endDate;

    public CouncilScheduleDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public CouncilScheduleDTO(int councilScheduleNo, String content, String startDate, String endDate) {
        super();
        this.councilScheduleNo = councilScheduleNo;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getCouncilScheduleNo() {
        return councilScheduleNo;
    }

    public void setCouncilScheduleNo(int councilScheduleNo) {
        this.councilScheduleNo = councilScheduleNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "CouncilScheduleDTO [councilScheduleNo=" + councilScheduleNo + ", content=" + content + ", startDate="
                + startDate + ", endDate=" + endDate + "]";
    }

}