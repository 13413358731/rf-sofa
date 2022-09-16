package com.realfinance.sofa.cg.core.domain.exec.bid;

import com.realfinance.sofa.cg.core.domain.PurchaseMode;
import com.realfinance.sofa.common.jpa.converter.DistinctStringListConverter;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 招标文件word模板
 */
@Entity
@Table(name = "CG_CORE_BID_DOC_WORD")
public class BiddingDocumentWord implements IEntity<Integer> {

    @Version
    private Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private PurchaseMode purchaseMode;

    /**
     *
     */
    @Lob
    @Column(nullable = false)
    private byte[] data;

    /**
     * 自定义段落名称
     */
    @Convert(converter = DistinctStringListConverter.class)
    private List<String> diySections;

    public BiddingDocumentWord() {
        diySections = new ArrayList<>();
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public PurchaseMode getPurchaseMode() {
        return purchaseMode;
    }

    public void setPurchaseMode(PurchaseMode purchaseMode) {
        this.purchaseMode = purchaseMode;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] word) {
        this.data = word;
    }

    public List<String> getDiySections() {
        return diySections;
    }

    public void setDiySections(List<String> diySection) {
        this.diySections = diySection;
    }
}
