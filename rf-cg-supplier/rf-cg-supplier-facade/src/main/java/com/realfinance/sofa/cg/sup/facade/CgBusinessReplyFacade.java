package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface CgBusinessReplyFacade {

    List<CgBusinessReplyDto> list(CgBusinessReplyQueryCriteria queryCriteria);

    Page<CgBusinessReplyDto> list(CgBusinessReplyQueryCriteria queryCriteria, Pageable pageable);

    CgBusinessReplyDetailsDto getDetailsById(Integer id);

    List<CgBusinessReplyDetailsDto> listDetails(CgBusinessReplyQueryCriteria queryCriteria);

    CgBusinessReplyDetailsDto getDetailsByAction(CgBusinessReplyQueryCriteria queryCriteria);

    CgBusinessReplyDetailsDto getFaBuDetailsByAction(CgBusinessReplyQueryCriteria queryCriteria);

    Integer updateBusinessReplyById(CgBusinessReplyQueryCriteria queryCriteria);

    /**
     * 发布
     *
     * @param projectId   项目ID
     * @param releaseId   发布ID
     * @param releaseType 应答类型
     * @param content     应答内容
     * @param deadline    应答截止时间
     * @param needQuote   是否需要报价
     * @param openTime    开启时间
     * @param attDs       文档
     * @param prices      报价项
     * @param supplierIds 发布的供应商ID集合
     */
    void release(String projectId,
                 String releaseId,
                 String releaseType,
                 String name,
                 String content,
                 LocalDateTime deadline,
                 Boolean needQuote,
                 LocalDateTime openTime,
                 List<CgAttachmentDto> attDs,
                 List<String> prices,
                 Collection<Integer> supplierIds);

    /**
     * 记录文件下载时间
     *
     * @param id
     */
    void recordFileDownloadTime(Integer id);

    /**
     * 记录签名时间
     *
     * @param id
     */
    void recordSignTime(Integer id);

    /**
     * 更新应答说明
     *
     * @param id
     * @param desc
     * @return
     */
    Integer updateReplyDescription(Integer id, String desc);

    /**
     * 更新应答说明
     *
     * @param id
     * @param note
     * @return
     */
    Integer updateSupReply(Integer id, Boolean normal, String note);

    /**
     * 应答
     *
     * @param id                 ID
     * @param attUs              上传的文件
     * @param paymentDescription 付款方式说明
     * @param supplyDescription  供货期说明
     * @param taxRateDescription 税率说明
     * @param otherDescription   其他说明
     * @param prices             报价信息
     * @return
     */
    Integer updateReply(Integer id, List<CgAttachmentDto> attUs,
                        String paymentDescription, String supplyDescription, String taxRateDescription, String otherDescription,
                        List<CgBusinessReplyPriceDto> prices);

    /**
     * 修改时间（应答截止时间， 开启时间）
     *
     * @param releaseId
     * @param releaseType
     * @param deadline
     * @param openTime
     * @return
     */
    void updateTime(String releaseId, String releaseType, LocalDateTime deadline, LocalDateTime openTime);

    void updateOtherDescription(String releaseId, String releaseType,String otherDescription);

    List<CgBusinessReplyDto> getCgBusinessReply(String releaseId, String releaseType);
}
