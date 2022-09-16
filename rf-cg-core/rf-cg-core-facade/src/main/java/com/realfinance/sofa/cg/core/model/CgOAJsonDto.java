package com.realfinance.sofa.cg.core.model;

public class CgOAJsonDto {

    private String TITLE;

    private String url;

    private String URGENTLEV;

    private String DOCNO;

    //附件
    private String FILE;

    //附件下载的部分地址
    private String ZIPFILE;

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getURGENTLEV() {
        return URGENTLEV;
    }

    public void setURGENTLEV(String URGENTLEV) {
        this.URGENTLEV = URGENTLEV;
    }

    public String getDOCNO() {
        return DOCNO;
    }

    public void setDOCNO(String DOCNO) {
        this.DOCNO = DOCNO;
    }

    public String getFILE() {
        return FILE;
    }

    public void setFILE(String FILE) {
        this.FILE = FILE;
    }

    public String getZIPFILE() {
        return ZIPFILE;
    }

    public void setZIPFILE(String ZIPFILE) {
        this.ZIPFILE = ZIPFILE;
    }
}
