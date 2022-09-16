package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.domain.PurchaseResultNotice;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionAtt;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStepType;
import com.realfinance.sofa.cg.core.domain.exec.bid.*;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.facade.CgBiddingDocumentFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.BiddingDocumentRepository;
import com.realfinance.sofa.cg.core.repository.BiddingDocumentWordRepository;
import com.realfinance.sofa.cg.core.repository.ProjectExecutionRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.BiddingDocumentDetailsMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.BiddingDocumentDetailsSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.BiddingDocumentMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectExecutionDetailsSaveMapper;
import com.realfinance.sofa.cg.core.util.ExceptionUtils;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgBiddingDocumentFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgBiddingDocumentImpl implements CgBiddingDocumentFacade {

    private static final Logger log = LoggerFactory.getLogger(CgBiddingDocumentImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final BiddingDocumentRepository biddingDocumentRepository;
    private final ProjectExecutionRepository projectExecutionRepository;
    private final BiddingDocumentWordRepository biddingDocumentWordRepository;
    private final ProjectExecutionDetailsSaveMapper projectExecutionDetailsSaveMapper;
    private final BiddingDocumentMapper biddingDocumentMapper;
    private final BiddingDocumentDetailsMapper biddingDocumentDetailsMapper;
    private final BiddingDocumentDetailsSaveMapper biddingDocumentDetailsSaveMapper;

    public CgBiddingDocumentImpl(JpaQueryHelper jpaQueryHelper,
                                 BiddingDocumentRepository biddingDocumentRepository,
                                 ProjectExecutionRepository projectExecutionRepository,
                                 BiddingDocumentWordRepository biddingDocumentWordRepository,
                                 ProjectExecutionDetailsSaveMapper projectExecutionDetailsSaveMapper,
                                 BiddingDocumentMapper biddingDocumentMapper,
                                 BiddingDocumentDetailsMapper biddingDocumentDetailsMapper,
                                 BiddingDocumentDetailsSaveMapper biddingDocumentDetailsSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.biddingDocumentRepository = biddingDocumentRepository;
        this.projectExecutionRepository = projectExecutionRepository;
        this.biddingDocumentWordRepository = biddingDocumentWordRepository;
        this.projectExecutionDetailsSaveMapper = projectExecutionDetailsSaveMapper;
        this.biddingDocumentMapper = biddingDocumentMapper;
        this.biddingDocumentDetailsMapper = biddingDocumentDetailsMapper;
        this.biddingDocumentDetailsSaveMapper = biddingDocumentDetailsSaveMapper;
    }

    @Override
    public Page<CgBiddingDocumentDto> list(CgBiddingDocumentQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<BiddingDocument> result = biddingDocumentRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return result.map(biddingDocumentMapper::toDto);
    }

    @Override
    public CgBiddingDocumentDto getById(Integer id) {
        Objects.requireNonNull(id);
        BiddingDocument document = getBiddingDocument(id);
        return biddingDocumentMapper.toDto(document);
    }

    @Override
    public CgBiddingDocumentDto getByExecutionId(Integer id) {
        Objects.requireNonNull(id);
        return biddingDocumentMapper.toDto(getBiddingDocumentByExec(id));
    }

    @Override
    public CgBiddingDocumentDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        BiddingDocument biddingDocument = getBiddingDocument(id);
        CgBiddingDocumentDetailsDto result = biddingDocumentDetailsMapper.toDto(biddingDocument);
        //以下发标中查看到采购方案中上传的文件
//        List<CgProjectExecutionAttDto> atts = biddingDocument.getProjectExecution().getProjectExecutionAtts().stream()
//                .filter(e -> isCurrentAtt(id, e))
//                .map(projectExecutionDetailsSaveMapper::cgProjectExecutionAttToProjectExecutionAttDto)
//                .collect(Collectors.toList());
//        result.setBiddingDocumentAtts(atts);
        return result;
    }

    @Override
    public Integer getIdByProjectExecutionId(Integer projectExecutionId) {
        Objects.requireNonNull(projectExecutionId);
        ProjectExecution projectExecution = projectExecutionRepository.getOne(projectExecutionId);
        return biddingDocumentRepository.findByProjectExecution(projectExecutionRepository.getOne(projectExecutionId))
                .map(BaseBiddingDocument::getId).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgBiddingDocumentDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        BiddingDocument biddingDocument;
        if (saveDto.getId() == null) { // 新增
            biddingDocument = biddingDocumentDetailsSaveMapper.toEntity(saveDto);
            LocalDateTime now = LocalDateTime.now();
            biddingDocument.setBidEndTime(now);
            biddingDocument.setOpenBidStartTime(now);
            biddingDocument.setOpenBidEndTime(now);
            biddingDocument.setName("");
            biddingDocument.setContent("");
            biddingDocument.setNeedQuote(false);
            biddingDocument.setStatus(FlowStatus.EDIT);
            biddingDocument.setTenantId(DataScopeUtils.loadTenantId());
            biddingDocument.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
            List<BiddingDocumentBiddingDocumentQualExamination> qualExaminations = new ArrayList<>();
            List<CgBiddingDocumentExaminationDto> biddingDocumentQualExaminations = saveDto.getBiddingDocumentQualExaminations();
            for (CgBiddingDocumentExaminationDto biddingDocumentQualExamination : biddingDocumentQualExaminations) {
                BiddingDocumentBiddingDocumentQualExamination qualExamination = new BiddingDocumentBiddingDocumentQualExamination();
                qualExamination.setCode(UUID.randomUUID().toString());
                qualExamination.setSubCode(UUID.randomUUID().toString());
                qualExamination.setName(biddingDocumentQualExamination.getName());
                qualExamination.setSubName(biddingDocumentQualExamination.getSubName());
                qualExaminations.add(qualExamination);
            }
            List<BiddingDocumentBiddingDocumentRespExamination> respExaminations = new ArrayList<>();
            List<CgBiddingDocumentExaminationDto> biddingDocumentRespExaminations = saveDto.getBiddingDocumentRespExaminations();
            for (CgBiddingDocumentExaminationDto biddingDocumentRespExamination : biddingDocumentRespExaminations) {
                BiddingDocumentBiddingDocumentRespExamination respExamination = new BiddingDocumentBiddingDocumentRespExamination();
                respExamination.setCode(UUID.randomUUID().toString());
                respExamination.setSubCode(UUID.randomUUID().toString());
                respExamination.setName(biddingDocumentRespExamination.getName());
                respExamination.setSubName(biddingDocumentRespExamination.getSubName());
                respExaminations.add(respExamination);
            }
            biddingDocument.setBiddingDocumentQualExaminations(qualExaminations);
            biddingDocument.setBiddingDocumentRespExaminations(respExaminations);
        } else { // 修改
            BiddingDocument entity = getBiddingDocument(saveDto.getId());
            biddingDocument = biddingDocumentDetailsSaveMapper.updateEntity(entity,saveDto);
        }
        preSave(biddingDocument);
        try {
            BiddingDocument saved = biddingDocumentRepository.save(biddingDocument);
            saveAtt(saveDto.getBiddingDocumentAtts(), saved);
            biddingDocumentRepository.flush();
            return saved.getId();
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        BiddingDocument biddingDocument = biddingDocumentRepository.findById(id)
                .orElseThrow(() -> entityNotFound(BiddingDocument.class,"id",id));

        FlowStatus currentStatusEnum = biddingDocument.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id,status);
            }
            return;
        }
        biddingDocument.setStatus(statusEnum);
        if (statusEnum == PASS) {
            handlePass(biddingDocument);
        }
        try {
            biddingDocumentRepository.saveAndFlush(biddingDocument);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败",e);
            }
            throw businessException("更新状态失败",e);
        }
    }

    @Override
    @Transactional
    public void updateTime(Integer id, LocalDateTime bidEndTime, LocalDateTime openBidStartTime, LocalDateTime openBidEndTime) {
        Objects.requireNonNull(id);
        BiddingDocument biddingDocument = getBiddingDocument(id);
        biddingDocument.setBidEndTime(bidEndTime);
        biddingDocument.setOpenBidStartTime(openBidStartTime);
        biddingDocument.setOpenBidEndTime(openBidEndTime);
        try {
            BiddingDocument saved = biddingDocumentRepository.saveAndFlush(biddingDocument);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    public byte[] getBiddingDocumentTemplate(Integer id) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        Project project = biddingDocument.getProjectExecution().getProject();
        BiddingDocumentWord word = biddingDocumentWordRepository.findByPurchaseModeAndTenantId(project.getPurMode(), project.getTenantId())
                .orElseThrow(() -> businessException("找不到招标文件模板"));
        return word.getData();
    }

    @Override
    public void saveBiddingDocumentAttachment(Integer id, CgProjectExecutionAttDto att) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(att);
        BiddingDocument biddingDocument = getBiddingDocument(id);
        List<ProjectExecutionAtt> projectExecutionAtts = biddingDocument.getProjectExecution().getProjectExecutionAtts();
        projectExecutionAtts.removeIf(e -> isCurrentAtt(id, e) && Objects.equals(e.getName(), att.getName()));
        ProjectExecutionAtt projectExecutionAtt = new ProjectExecutionAtt();
        projectExecutionAtt.setUploadTime(att.getUploadTime());
        projectExecutionAtt.setEncrypted(false);
        projectExecutionAtt.setStepDataId(id);
        projectExecutionAtt.setStepType(ProjectExecutionStepType.FBYD);
        projectExecutionAtt.setExt(att.getExt());
        projectExecutionAtt.setName(att.getName());
        projectExecutionAtt.setPath(att.getPath());
        projectExecutionAtt.setSize(att.getSize());
        projectExecutionAtt.setSource(att.getSource());
        biddingDocument.getProjectExecution().getProjectExecutionAtts().add(projectExecutionAtt);
        try {
            biddingDocumentRepository.saveAndFlush(biddingDocument);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional
    public void preQuote(Integer id) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        LocalDateTime now = LocalDateTime.now();
        if (biddingDocument.getOpenBidStartTime().isAfter(now)) {
            throw ExceptionUtils.businessException("未到开启时间");
        }
        if (biddingDocument.getPreOpenQuoteTime() != null) {
            throw ExceptionUtils.businessException("已开启");
        }
        biddingDocument.setPreOpenQuoteUserId(DataScopeUtils.loadPrincipalId().orElse(null));
        biddingDocument.setPreOpenQuoteTime(now);
        try {
            biddingDocumentRepository.saveAndFlush(biddingDocument);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    public boolean checkOpenQuote(Integer id) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        return biddingDocument.getPreOpenQuoteUserId() != null;
    }

    @Override
    @Transactional
    public void openQuote(Integer id, List<CgProjectExecutionAttDto> atts) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        if (LocalDateTime.now().isBefore(biddingDocument.getBidEndTime())){
            throw ExceptionUtils.businessException("投标截止时间未到!");
        }
        if (biddingDocument.getOpenQuoteTime() != null) {
            throw ExceptionUtils.businessException("已开启");
        }
        biddingDocument.setOpenQuoteTime(LocalDateTime.now());
        try {
            BiddingDocument saved = biddingDocumentRepository.saveAndFlush(biddingDocument);
            saveAtt(atts, saved);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }


    @Override
    @Transactional
    public void closequote(Integer id) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        if (biddingDocument.getOpenQuoteTime() == null){
            throw ExceptionUtils.businessException("未开启");
        }
        if (biddingDocument.getOpenBizTime()!=null){
            throw ExceptionUtils.businessException("已结束");
        }
        biddingDocument.setOpenBizTime(LocalDateTime.now());
        try {
            BiddingDocument saved = biddingDocumentRepository.saveAndFlush(biddingDocument);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional
    public void preBiz(Integer id) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        LocalDateTime now = LocalDateTime.now();
        biddingDocument.setPreOpenQuoteUserId(DataScopeUtils.loadPrincipalId().orElse(null));
        biddingDocument.setPreOpenQuoteTime(now);
        try {
            biddingDocumentRepository.saveAndFlush(biddingDocument);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    public boolean checkOpenBiz(Integer id) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        return biddingDocument.getPreOpenQuoteTime() != null;
    }

    @Override
    @Transactional
    public void openBiz(Integer id, List<CgProjectExecutionAttDto> atts) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        if (biddingDocument.getOpenQuoteTime() != null) {
            throw ExceptionUtils.businessException("已开启");
        }
        biddingDocument.setOpenQuoteTime(LocalDateTime.now());
        try {
            BiddingDocument saved = biddingDocumentRepository.saveAndFlush(biddingDocument);
            saveAtt(atts, saved);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional
    public CgBiddingDocumentDetailsDto updateAttachment(Integer id, List<CgProjectExecutionAttDto> dtoList) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(dtoList);
        BiddingDocument biddingDocument = biddingDocumentRepository.findById(id).orElseThrow(() -> entityNotFound(PurchaseResultNotice.class, "id", id));
        Map<Integer, CgProjectExecutionAttDto> map = dtoList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        for (BiddingDocumentAtt attachment : biddingDocument.getBiddingDocumentAtts()) {
            if (attachment.getExt().equals("pdf")){
                CgProjectExecutionAttDto c = map.get(attachment.getId());
                attachment.setPath(c.getPath());
                attachment.setUploadTime(c.getUploadTime());
            }
        }
        BiddingDocument b;
        try {
            b = biddingDocumentRepository.saveAndFlush(biddingDocument);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新附件失败", e);
            }
            throw businessException("更新附件失败", e);
        }
        return biddingDocumentDetailsMapper.toDto(b);
    }

    @Override
    @Transactional
    public void updatePreOpenBizTime(Integer id,String type) {
        BiddingDocument biddingDocument = getBiddingDocument(id);
        if (type.equals("START")){
            if (biddingDocument.getPreOpenBizTime()!=null){
                throw new RuntimeException("无法重复通知!");
            }
            biddingDocument.setPreOpenBizTime(LocalDateTime.now());
        }
        try {
            BiddingDocument saved = biddingDocumentRepository.saveAndFlush(biddingDocument);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }

    }

    private boolean isCurrentAtt(Integer id, ProjectExecutionAtt e) {
        return Objects.equals(e.getStepType(), ProjectExecutionStepType.FBYD)
                && Objects.equals(e.getStepDataId(), id);
    }

    private void saveAtt(List<CgProjectExecutionAttDto> biddingDocumentAtts, BiddingDocument biddingDocument) {
        List<ProjectExecutionAtt> atts = biddingDocument.getProjectExecution().getProjectExecutionAtts()
                .stream().filter(e -> isCurrentAtt(biddingDocument.getId(), e)).collect(Collectors.toList());
        if (biddingDocumentAtts != null) {
            for (CgProjectExecutionAttDto biddingDocumentAtt : biddingDocumentAtts) {
                ProjectExecutionAtt projectExecutionAtt = null;
                if (biddingDocumentAtt.getId() != null) {
                    for (ProjectExecutionAtt att : atts) {
                        if (att.getId().equals(biddingDocumentAtt.getId())) {
                            projectExecutionDetailsSaveMapper.updateAtt(att,biddingDocumentAtt);
                            break;
                        }
                    }
                } else {
                    projectExecutionAtt = projectExecutionDetailsSaveMapper
                            .cgProjectExecutionAttDtoToProjectExecutionAtt(biddingDocumentAtt);
                    projectExecutionAtt.setStepType(ProjectExecutionStepType.FBYD);
                    projectExecutionAtt.setStepDataId(biddingDocument.getId());
                    projectExecutionAtt.setEncrypted(false);
                    atts.add(projectExecutionAtt);
                }
            }
        }
    }

    private void handlePass(BiddingDocument biddingDocument) {
        biddingDocument.setPassTime(LocalDateTime.now());
        // TODO: 2021/3/10 保存历史记录
    }

    protected void preSave(BiddingDocument biddingDocument) {
        if (biddingDocument.getId() == null) {
            if (Objects.equals(biddingDocument.getProjectExecution().getCreatedUserId(),
                    DataScopeUtils.loadPrincipalId().orElse(null))) {
                throw businessException("非采购承办人不能新增数据");
            }
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected BiddingDocument getBiddingDocument(Integer id) {
        Objects.requireNonNull(id);
        List<BiddingDocument> all = biddingDocumentRepository.findAll(
                ((Specification<BiddingDocument>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!biddingDocumentRepository.existsById(id)) {
                throw entityNotFound(BiddingDocument.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected BiddingDocument getBiddingDocumentByExec(Integer id) {
        Objects.requireNonNull(id);
        List<BiddingDocument> all = biddingDocumentRepository.findAll(
                ((Specification<BiddingDocument>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("projectExecution").get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!biddingDocumentRepository.existsById(id)) {
                throw entityNotFound(BiddingDocument.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }
}
