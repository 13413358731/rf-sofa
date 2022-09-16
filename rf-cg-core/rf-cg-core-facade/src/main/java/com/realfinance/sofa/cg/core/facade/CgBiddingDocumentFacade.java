package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 招标文件
 */
public interface CgBiddingDocumentFacade {
    Page<CgBiddingDocumentDto> list(CgBiddingDocumentQueryCriteria queryCriteria, @NotNull Pageable pageable);

    CgBiddingDocumentDto getById(@NotNull Integer id);

    CgBiddingDocumentDto getByExecutionId(@NotNull Integer id);

    CgBiddingDocumentDetailsDto getDetailsById(@NotNull Integer id);

    Integer getIdByProjectExecutionId(@NotNull Integer projectExecutionId);

    Integer save(@NotNull CgBiddingDocumentDetailsSaveDto saveDto);

    void updateStatus(Integer id, String status);

    /**
     * 修改时间（投标截止时间， 开启时间）
     * @param id
     * @param bidEndTime
     * @param openBidStartTime
     * @param openBidEndTime
     */
    void updateTime(Integer id, LocalDateTime bidEndTime, LocalDateTime openBidStartTime, LocalDateTime openBidEndTime);

    /**
     * 获取招标文件模板
     * @param id
     */
    byte[] getBiddingDocumentTemplate(Integer id);

    /**
     * 保存招标文件，如果有同名附件会替换
     * @param id
     * @param att
     */
    void saveBiddingDocumentAttachment(Integer id, CgProjectExecutionAttDto att);

    /**
     * 监督同意开启价格标
     * @param id
     */
    void preQuote(Integer id);

    /**
     * 检查是否能开启
     * @param id
     * @return
     */
    boolean checkOpenQuote(Integer id);

    /**
     * 开启价格标
     * @param id
     */
    void openQuote(Integer id, List<CgProjectExecutionAttDto> atts);

    /**
     * 开标结束
     * @param id
     */
    void closequote(Integer id);
    /**
     * 监督同意开启商务
     * @param id
     */
    void preBiz(Integer id);

    /**
     * 检查是否能开启
     * @param id
     * @return
     */
    boolean checkOpenBiz(Integer id);
    /**
     * 开启商务标
     * @param id
     */
    void openBiz(Integer id, List<CgProjectExecutionAttDto> atts);

    /**
     * 更新骑缝章附件
     * @param id 招标文件ID
     * @param dtoList 骑缝章附件集合
     * @return
     */
    CgBiddingDocumentDetailsDto updateAttachment(Integer id, List<CgProjectExecutionAttDto> dtoList);

    /**
     * 启动流程 修改该字段 用于判断是否启动
     * @param id
     */
    void updatePreOpenBizTime(Integer id,String type);
}
