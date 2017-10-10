package com.classs.skhuter.util;

import java.io.Serializable;

/**
 * Created by 밍쥬 ^0^ on 2017-10-10.
 * @설명 : 파일 다운로드를 위한 커스텀 클래스
 */

public class MyFile implements Serializable{
    private String fileName;
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "MyFile [fileName="+fileName
                    +", filePath="+filePath+"]";
    }
}
