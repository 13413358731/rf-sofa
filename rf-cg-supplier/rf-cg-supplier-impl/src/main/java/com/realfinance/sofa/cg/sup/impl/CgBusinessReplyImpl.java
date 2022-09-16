package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.*;
import com.realfinance.sofa.cg.sup.facade.CgBusinessReplyFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.sup.repository.BusinessProjectRepository;
import com.realfinance.sofa.cg.sup.repository.BusinessReplyRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.BusinessReplyDetailsMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.BusinessReplyMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.entityNotFound;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.*;

@Service
@SofaService(interfaceType = CgBusinessReplyFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgBusinessReplyImpl implements CgBusinessReplyFacade {

    private static final Logger log = LoggerFactory.getLogger(CgBusinessReplyImpl.class);

    private final BusinessReplyRepository businessReplyRepository;
    private final BusinessProjectRepository businessProjectRepository;
    private final BusinessReplyMapper businessReplyMapper;
    private final BusinessReplyDetailsMapper businessReplyDetailsMapper;
    private final SupplierRepository supplierRepository;

    public CgBusinessReplyImpl(BusinessReplyRepository businessReplyRepository,
                               BusinessProjectRepository businessProjectRepository,
                               BusinessReplyMapper businessReplyMapper,
                               BusinessReplyDetailsMapper businessReplyDetailsMapper, SupplierRepository supplierRepository) {
        this.businessReplyRepository = businessReplyRepository;
        this.businessProjectRepository = businessProjectRepository;
        this.businessReplyMapper = businessReplyMapper;
        this.businessReplyDetailsMapper = businessReplyDetailsMapper;
        this.supplierRepository = supplierRepository;
    }


    @Override
    public List<CgBusinessReplyDto> list(CgBusinessReplyQueryCriteria queryCriteria) {
        List<BusinessReply> result = businessReplyRepository.findAll(toSpecification(queryCriteria));
        return result.stream().map(businessReplyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<CgBusinessReplyDto> list(CgBusinessReplyQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<BusinessReply> result = businessReplyRepository.findAll(toSpecification(queryCriteria), pageable);
        return result.map(businessReplyMapper::toDto);
    }

    @Override
    public CgBusinessReplyDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        BusinessReply businessReply = getBusinessReply(id);
        return businessReplyDetailsMapper.toDto(businessReply);
    }

    @Override
    public List<CgBusinessReplyDetailsDto> listDetails(CgBusinessReplyQueryCriteria queryCriteria) {
        List<BusinessReply> result=businessReplyRepository.findAll(toSpecification(queryCriteria));
        return result.stream().map(businessReplyDetailsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CgBusinessReplyDetailsDto getDetailsByAction(CgBusinessReplyQueryCriteria queryCriteria) {
        List<BusinessReply> all = businessReplyRepository.findAll(toSpecification(queryCriteria));
        BusinessReply reply = all.get(0);
        return businessReplyDetailsMapper.toDto(reply);
    }

    @Override
    public CgBusinessReplyDetailsDto getFaBuDetailsByAction(CgBusinessReplyQueryCriteria queryCriteria) {
        List<BusinessReply> all = businessReplyRepository.findAll(toSpecification1(queryCriteria));
        BusinessReply reply = all.get(0);
        return businessReplyDetailsMapper.toDto(reply);
    }

    @Override
    @Transactional
    public Integer updateBusinessReplyById(CgBusinessReplyQueryCriteria queryCriteria) {
        List<BusinessReply> all = businessReplyRepository.findAll(toSpecification2(queryCriteria));
        BusinessReply reply = all.get(0);
        reply.setFileDownloadTime(LocalDateTime.now());
        BusinessReply businessReply = businessReplyRepository.saveAndFlush(reply);
        //return businessReplyMapper.toDto(reply);
        return businessReply.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void release(String projectId, String releaseId, String releaseType, String name, String content, LocalDateTime deadline,
                        Boolean needQuote, LocalDateTime openTime,
                        List<CgAttachmentDto> attDs, List<String> prices, Collection<Integer> supplierIds) {
        if (supplierIds == null || supplierIds.isEmpty()) {
            throw businessException("供应商集合不能空");
        }
        String tenantId = DataScopeUtils.loadTenantId();
        BusinessProject businessProject = businessProjectRepository.lockBusinessProject(projectId,tenantId)
                .orElseThrow(() -> entityNotFound(BusinessProject.class, "projectId", projectId));
        if (businessReplyRepository.existsByProjectAndReleaseIdAndReplyType(businessProject,releaseId,releaseType)) {
            if (log.isDebugEnabled()) {
                log.debug("已存在的businessProjectId：{}，releaseId：{}，releaseType：{}",businessProject.getId(),releaseId,releaseType);
            }
            return;
        }
        businessProject.setProjectStatus(releaseType);
        List<BusinessReply> replies = businessProject.getReplies();
        List<BusinessReply> newBusinessReplies = supplierIds.stream().map(e -> {
            BusinessReply businessReply = new BusinessReply();
            businessReply.setName(name);
            businessReply.setTenantId(tenantId);
            businessReply.setReleaseId(releaseId);
            businessReply.setReplyType(releaseType);
            businessReply.setContent(content);
            businessReply.setDeadline(deadline);
            businessReply.setNeedQuote(needQuote);
            businessReply.setOpenTime(openTime);
            businessReply.setReleaseTime(LocalDateTime.now());
            businessReply.setQuoteRound(0);
            businessReply.setSupplierId(e);
            businessReply.setProjectName(businessProject.getProjectName());
            if (attDs != null) {
                List<BusinessReplyAttD> businessReplyAttDS = sealBusinessReplyAttD(attDs);
                businessReply.setAttDs(businessReplyAttDS);
            }
            if (prices != null) {
                List<BusinessReplyPrice> businessReplyPrices = prices.stream().map(p -> {
                    BusinessReplyPrice businessReplyPrice = new BusinessReplyPrice();
                    businessReplyPrice.setNeedBidEval(true);
                    businessReplyPrice.setProductName(p);
                    return businessReplyPrice;
                }).collect(Collectors.toList());
                businessReply.setPrices(businessReplyPrices);
            }
            /*if(prices.size()==0){
                List<BusinessReplyPrice> collect = businessReply.getPrices().stream().map(p -> {
                    p.setProductName(businessReply.getProject().getProjectName());
                    return p;
                }).collect(Collectors.toList());
                businessReply.setPrices(collect);
            }*/
            return businessReply;
        }).collect(Collectors.toList());
        if (needQuote) {
            int quoteRound = replies.stream().mapToInt(BusinessReply::getQuoteRound).max().orElse(0) + 1;
            for (BusinessReply newBusinessReply : newBusinessReplies) {
                newBusinessReply.setQuoteRound(quoteRound);
            }
        }
        replies.addAll(newBusinessReplies);
        List<BusinessReply> list = businessProject.getReplies();
        /*List<BusinessReply> newList=new ArrayList<>();
        for(BusinessReply businessReply:list){
            businessReply.setNeedQuote(false);
            newList.add(businessReply);
        }
        businessProject.getReplies().clear();
        businessProject.getReplies().addAll(newList);*/
        list.stream().filter(businessReply->{
            businessReply.setNeedQuote(false);
            return true;
        }).collect(Collectors.toList());
        try {
            businessProject.getReplies().addAll(replies);
            businessProjectRepository.saveAndFlush(businessProject);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional
    public void recordFileDownloadTime(Integer id) {
        BusinessReply businessReply = getBusinessReply(id);
        if (businessReply.getFileDownloadTime() == null) {
            businessReply.setFileDownloadTime(LocalDateTime.now());
            try {
                businessReplyRepository.saveAndFlush(businessReply);
            } catch (Exception e) {
                log.error("保存失败",e);
                throw businessException("保存失败");
            }
        }
    }

    @Override
    @Transactional
    public void recordSignTime(Integer id) {
        BusinessReply businessReply = getBusinessReply(id);
        if (businessReply.getSignTime()!=null){
            throw new RuntimeException("确认标书已收到已完成，不要重复提交");
        }
        if (businessReply.getSignTime() == null) {
            businessReply.setSignTime(LocalDateTime.now());
            try {
                businessReplyRepository.saveAndFlush(businessReply);
            } catch (Exception e) {
                log.error("保存失败",e);
                throw businessException("保存失败");
            }
        }
    }

    @Override
    @Transactional
    public Integer updateReplyDescription(Integer id, String desc) {
        BusinessReply businessReply = getBusinessReply(id);
        businessReply.setReplyDescription(desc);
        try {
            BusinessReply saved = businessReplyRepository.saveAndFlush(businessReply);
            return saved.getId();
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSupReply(Integer id, Boolean normal, String note) {
        BusinessReply businessReply = getBusinessReply(id);
        businessReply.setNormal(normal);
        businessReply.setNote(note);
        try {
            BusinessReply saved = businessReplyRepository.saveAndFlush(businessReply);
            return saved.getId();
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateReply(Integer id, List<CgAttachmentDto> attUs, String paymentDescription, String supplyDescription,
                               String taxRateDescription, String otherDescription,
                               List<CgBusinessReplyPriceDto> prices) {
        Objects.requireNonNull(id);
        BusinessReply businessReply = getBusinessReply(id);
        businessReply.setPaymentDescription(paymentDescription);
        businessReply.setSupplyDescription(supplyDescription);
        businessReply.setTaxRateDescription(taxRateDescription);
        businessReply.setOtherDescription(otherDescription);
        if(attUs != null && attUs.size() == 0){
            businessReply.getAttUs().clear();
        }
        if (attUs != null && !attUs.isEmpty()) {
            businessReply.getAttUs().clear();
            List<BusinessReplyAttU> businessReplyAttUS = sealBusinessReplyAttU(attUs);
            businessReply.getAttUs().addAll(businessReplyAttUS);
        }
        //List<BusinessReplyAttU> attUs1 = businessReply.getAttUs();
        // 价格
        if (prices != null && !prices.isEmpty()) {
            if (prices.stream().map(e -> e.getProductName() + "_" + e.getModel()).collect(Collectors.toSet()).size() < prices.size()) {
                throw businessException("产品名称/型号重复");
            }
            prices.forEach(e -> e.setTotalPrice(e.getUnitPrice().multiply(e.getNumber())));
            prices.stream().map(CgBusinessReplyPriceDto::getTotalPrice)
                    .reduce(BigDecimal::add).ifPresent(businessReply::setTotalPrice);
            List<BusinessReplyPrice> businessReplyPrices = prices.stream().map(e -> {
                BusinessReplyPrice businessReplyPrice = new BusinessReplyPrice();
                businessReplyPrice.setNeedBidEval(e.getNeedBidEval());
                businessReplyPrice.setProductName(e.getProductName());
                businessReplyPrice.setModel(e.getModel());
                businessReplyPrice.setDescription(e.getDescription());
                businessReplyPrice.setNumber(e.getNumber());
                businessReplyPrice.setUnit(e.getUnit());
                businessReplyPrice.setUnitPrice(e.getUnitPrice());
                businessReplyPrice.setTotalPrice(e.getTotalPrice());
                businessReplyPrice.setNote(e.getNote());
                return businessReplyPrice;
            }).collect(Collectors.toList());
            businessReply.getPrices().clear();
            businessReply.getPrices().addAll(businessReplyPrices);
            businessReply.setNeedQuote(true);  //修改价格信息则表示已报价
        }
        try {
            BusinessReply saved = businessReplyRepository.saveAndFlush(businessReply);
            return saved.getId();
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional
    public void updateTime(String releaseId, String releaseType, LocalDateTime deadline, LocalDateTime openTime) {
        List<BusinessReply> businessReplies = businessReplyRepository.findByReleaseIdAndReplyType(releaseId, releaseType);
        for (BusinessReply businessReply : businessReplies) {
            businessReply.setDeadline(deadline);//应答截止时间
            businessReply.setOpenTime(openTime);//开启时间
            try {
                businessReplyRepository.saveAndFlush(businessReply);
            } catch (Exception e) {
                log.error("保存失败",e);
                throw businessException("保存失败");
            }
        }
    }

    private BusinessReply getBusinessReply(Integer id) {
        return businessReplyRepository.findById(id)
                .orElseThrow(() -> entityNotFound(BusinessReply.class, "id", id));
    }

    @Override
    @Transactional
    public void updateOtherDescription(String releaseId, String releaseType,String otherDescription) {
        List<BusinessReply> businessReplies = businessReplyRepository.findByReleaseIdAndReplyType(releaseId, releaseType);
        for (BusinessReply businessReply : businessReplies) {
            businessReply.setOtherDescription(otherDescription);
        }
        try {
            businessReplyRepository.saveAll(businessReplies);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    public List<CgBusinessReplyDto> getCgBusinessReply(String releaseId, String releaseType) {
        List<BusinessReply> list = businessReplyRepository.findByReleaseIdAndReplyType(releaseId, releaseType);
        //获取 供应商名称
        List<Integer> ids=list.stream().map(e->e.getSupplierId()).collect(Collectors.toList());
        List<Supplier> supplierList = supplierRepository.findAllById(ids);
        Map<Integer,String> map=supplierList.stream().collect(Collectors.toMap(e->e.getId(),e->e.getName()));
        List<CgBusinessReplyDto> dtoList=new ArrayList<>();
        for (BusinessReply businessReply : list) {
            CgBusinessReplyDto dto = new CgBusinessReplyDto();
            dto.setName(map.get(businessReply.getSupplierId()));
            dto.setTotalPrice(businessReply.getTotalPrice());
            dtoList.add(dto);
        }
        return dtoList;
    }

    private List<BusinessReplyAttD> sealBusinessReplyAttD(List<CgAttachmentDto> attDs) {
        return attDs.stream().map(a -> {
            BusinessReplyAttD businessReplyAttD = new BusinessReplyAttD();
            businessReplyAttD.setCategory(null);
            businessReplyAttD.setExt(a.getExt());
            businessReplyAttD.setName(a.getName());
            businessReplyAttD.setNote(a.getNote());
            businessReplyAttD.setPath(a.getPath());
            businessReplyAttD.setSize(a.getSize());
            businessReplyAttD.setSource(a.getSource());
            businessReplyAttD.setUploadTime(a.getUploadTime());
            return businessReplyAttD;
        }).collect(Collectors.toList());
    }

    private List<BusinessReplyAttU> sealBusinessReplyAttU(List<CgAttachmentDto> attDs) {
        return attDs.stream().map(a -> {
            BusinessReplyAttU businessReplyAttU = new BusinessReplyAttU();
            businessReplyAttU.setCategory(SupplierAttachmentCategory.valueOf(a.getCategory()));
            businessReplyAttU.setExt(a.getExt());
            businessReplyAttU.setName(a.getName());
            businessReplyAttU.setNote(a.getNote());
            businessReplyAttU.setPath(a.getPath());
            businessReplyAttU.setSize(a.getSize());
            businessReplyAttU.setSource(a.getSource());
            businessReplyAttU.setUploadTime(a.getUploadTime());
            return businessReplyAttU;
        }).collect(Collectors.toList());
    }
}
