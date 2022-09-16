package com.realfinance.sofa.cg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "OA系统接收的Json数据")
public class CgOAJsonVo {

    //标题
    @JsonProperty
    private String TITLE;

    private String url;

    @JsonProperty
    private String URGENTLEV;

    //任务单号
    @JsonProperty
    private String DOCNO;

    //附件
    @JsonProperty
    private String FILE;

    //附件下载的部分地址
    @JsonProperty
    private String ZIPFILE;

    @JsonIgnore
    public String getTITLE() {
        return TITLE;
    }

    @JsonIgnore
    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    public String getURGENTLEV() {
        return URGENTLEV;
    }

    @JsonIgnore
    public void setURGENTLEV(String URGENTLEV) {
        this.URGENTLEV = URGENTLEV;
    }

    @JsonIgnore
    public String getDOCNO() {
        return DOCNO;
    }

    @JsonIgnore
    public void setDOCNO(String DOCNO) {
        this.DOCNO = DOCNO;
    }

    @JsonIgnore
    public String getFILE() {
        return FILE;
    }

    @JsonIgnore
    public void setFILE(String FILE) {
        this.FILE = FILE;
    }

    @JsonIgnore
    public String getZIPFILE() {
        return ZIPFILE;
    }

    @JsonIgnore
    public void setZIPFILE(String ZIPFILE) {
        this.ZIPFILE = ZIPFILE;
    }
}

