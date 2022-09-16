package com.realfinance.sofa.cg.core.domain.oa;

import javax.persistence.*;

@Entity
@Table(name = "CG_OA_ATTACHMENT")
public class OaAttachment {
    //流程编号(立项审批号)
    @Id
    private String DOCNO;

    //标题
    private String TITLE;

    //该流程的OA链接地址
    private String url;
    //紧急程度
    private String URGENTLEV;

    //附件文件名
    private String FILE;

    //附件相对路径
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

