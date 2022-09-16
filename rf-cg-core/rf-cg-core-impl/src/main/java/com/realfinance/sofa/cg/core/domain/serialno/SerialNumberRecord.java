package com.realfinance.sofa.cg.core.domain.serialno;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 流水号记录
 */
@Entity
@Table(name = "CG_CORE_SERIAL_NO_REC")
public class SerialNumberRecord {

    /**
     * 归零类型
     */
    public enum ResetType {
        /**
         * 不归零
         */
        NEVER,
        /**
         * 按年归零
         */
        YEAR,
        /**
         * 按月归零
         */
        MONTH,
        /**
         * 按填归零
         */
        DAY;
    }

    @Version
    private Long v;

    @EmbeddedId
    private SerialNumberRecordId id;

    @Column
    private String template;

    /**
     * 序列号
     */
    @Column(nullable = false)
    private Integer serialNumber;

    /**
     * 序列号长度
     */
    @Column
    private Integer serialNumberLength;

    @Column
    private Integer year;

    @Column
    private Integer month;

    @Column
    private Integer day;

    @Column(nullable = false)
    private ResetType resetType;

    /**
     * 下一个流水号
     * @return
     */
    public int nextSerialNumber() {
        LocalDateTime now = LocalDateTime.now();
        switch (resetType) {
            case NEVER:
                break;
            case YEAR:
                if (!Objects.equals(now.getYear(),year)) {
                    serialNumber = 0;
                }
                break;
            case MONTH:
                if (!Objects.equals(now.getMonthValue(),month)
                        || !Objects.equals(now.getYear(),year)) {
                    serialNumber = 0;
                }
                break;
            case DAY:
                if (!Objects.equals(now.getDayOfMonth(),day)
                        || !Objects.equals(now.getMonthValue(),month)
                        || !Objects.equals(now.getYear(),year)) {
                    serialNumber = 0;
                }
                break;
        }
        year = now.getYear();
        month = now.getMonthValue();
        day = now.getDayOfMonth();
        return ++ serialNumber;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public SerialNumberRecordId getId() {
        return id;
    }

    public void setId(SerialNumberRecordId id) {
        this.id = id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getSerialNumberLength() {
        return serialNumberLength;
    }

    public void setSerialNumberLength(Integer serialNumberLength) {
        this.serialNumberLength = serialNumberLength;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public ResetType getResetType() {
        return resetType;
    }

    public void setResetType(ResetType resetType) {
        this.resetType = resetType;
    }
}
