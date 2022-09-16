package com.realfinance.sofa.cg.core.service.projectexec.impl;

import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.domain.exec.*;
import com.realfinance.sofa.cg.core.domain.exec.bid.BiddingDocument;
import com.realfinance.sofa.cg.core.domain.exec.bid.BiddingDocumentSection;
import com.realfinance.sofa.cg.core.domain.exec.bid.BiddingDocumentWord;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.exception.RfCgCoreException;
import com.realfinance.sofa.cg.core.repository.BiddingDocumentRepository;
import com.realfinance.sofa.cg.core.repository.BiddingDocumentWordRepository;
import com.realfinance.sofa.cg.core.repository.ProjectExecutionStepRepository;
import com.realfinance.sofa.cg.core.service.projectexec.ProjectExecutionStepService;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.businessException;

/**
 * 发标
 */
@Service("projectExecution-FBYD")
public class FBYDProjectExecutionStepServiceImpl extends ProjectExecutionStepService {

    private static final Logger log = LoggerFactory.getLogger(FBYDProjectExecutionStepServiceImpl.class);

    private final BiddingDocumentRepository biddingDocumentRepository;
    private final BiddingDocumentWordRepository biddingDocumentWordRepository;

    public FBYDProjectExecutionStepServiceImpl(BiddingDocumentRepository biddingDocumentRepository,
                                               ProjectExecutionStepRepository projectExecutionStepRepository,
                                               BiddingDocumentWordRepository biddingDocumentWordRepository) {
        super(projectExecutionStepRepository);
        this.biddingDocumentRepository = biddingDocumentRepository;
        this.biddingDocumentWordRepository = biddingDocumentWordRepository;
    }

    @Override
    public void initStep(Integer projectExecutionStepId) {
        Objects.requireNonNull(projectExecutionStepId);
        ProjectExecutionStep projectExecutionStep = getProjectExecutionStep(projectExecutionStepId);
        ProjectExecution projectExecution = projectExecutionStep.getProjectExecution();
        try {
            BiddingDocument biddingDocument = biddingDocumentRepository.findByProjectExecution(projectExecution)
                    .orElseGet(() -> {
                        // 在第一次初始化时开始环节
                        startStep(projectExecutionStepId);
                        return biddingDocumentRepository.save(newBiddingDocument(projectExecution));
                    });
            // 如果未开始流程，则将供应商看板中的供应商更新到中标节点供应商子表
            if (biddingDocument.getStatus() == FlowStatus.EDIT) {
                updateBiddingDocumentSup(biddingDocument,projectExecution);
            }
            // flush
            projectExecutionStepRepository.flush();
        } catch (RfCgCoreException e) {
            throw e;
        } catch (Exception e) {
            log.error("初始化标书失败",e);
            throw businessException("初始化标书失败");
        }
    }

    /**
     * 根据方案执行的供应商看板更新发标中的供应商子表
     * @param biddingDocument
     * @param projectExecution
     */
    private void updateBiddingDocumentSup(BiddingDocument biddingDocument, ProjectExecution projectExecution) {
       /* Set<Integer> bidDocSupSupplierIdSet = biddingDocument.getBiddingDocumentSups().stream()
                .map(BasePurSup::getSupplierId).collect(Collectors.toSet());
        for (ProjectExecutionSup projectExecutionSup : projectExecution.getProjectExecutionSups()) {
            if (projectExecutionSup.getModifyMode() == ProjectExecutionSup.ModifyMode.TT) {
                if (bidDocSupSupplierIdSet.contains(projectExecutionSup.getSupplierId())) {
                    biddingDocument.getBiddingDocumentSups()
                            .removeIf(e -> Objects.equals(e.getSupplierId(), projectExecutionSup.getSupplierId()));
                }
            } else {
                if (!bidDocSupSupplierIdSet.contains(projectExecutionSup.getSupplierId())) {
                    BiddingDocumentSup biddingDocumentSup = new BiddingDocumentSup();
                    biddingDocumentSup.setBiddingDocument(biddingDocument);
                    biddingDocumentSup.setSelected(false);
                    biddingDocumentSup.setSupplierId(projectExecutionSup.getSupplierId());
                    biddingDocumentSup.setContactName(projectExecutionSup.getContactName());
                    biddingDocumentSup.setContactEmail(projectExecutionSup.getContactEmail());
                    biddingDocumentSup.setContactMobile(projectExecutionSup.getContactMobile());
                    biddingDocumentSup.setNote(projectExecutionSup.getNote());
                    biddingDocumentSup.setReason(projectExecutionSup.getReason());
                    biddingDocumentSup.setSource(projectExecutionSup.getSource());
                    biddingDocument.getBiddingDocumentSups().add(biddingDocumentSup);
                }
            }
        }
        biddingDocumentRepository.save(biddingDocument);*/
    }

    /**
     * 新键一个发标数据
     * @param projectExecution
     * @return
     */
    private BiddingDocument newBiddingDocument(ProjectExecution projectExecution) {
        // 如果不是采购承办人，不能初始化
        if (Objects.equals(projectExecution.getCreatedUserId(), DataScopeUtils.loadPrincipalId().orElse(null))) {
            throw businessException("非采购承办人");
        }
        Project project = projectExecution.getProject();
        // 开始新键标书主表
        BiddingDocument newBiddingDocument = new BiddingDocument();
        newBiddingDocument.setProjectExecution(projectExecution);
        newBiddingDocument.setTenantId(projectExecution.getTenantId());
        newBiddingDocument.setDepartmentId(projectExecution.getDepartmentId());
        BiddingDocumentWord biddingDocumentWord = biddingDocumentWordRepository
                .findByPurchaseModeAndTenantId(project.getPurMode(), projectExecution.getTenantId())
                .orElseThrow(() -> businessException("找不到标书Word模板"));
        if (!biddingDocumentWord.getDiySections().isEmpty()) {
            List<BiddingDocumentSection> biddingDocumentSections = biddingDocumentWord.getDiySections().stream().map(e -> {
                BiddingDocumentSection biddingDocumentSection = new BiddingDocumentSection();
                biddingDocumentSection.setSectionName(e);
                return biddingDocumentSection;
            }).collect(Collectors.toList());
            newBiddingDocument.setBiddingDocumentSections(biddingDocumentSections);
        }
        return newBiddingDocument;
    }
}
