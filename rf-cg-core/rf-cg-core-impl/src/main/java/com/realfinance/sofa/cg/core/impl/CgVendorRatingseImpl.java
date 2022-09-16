package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.ContractAttachment;
import com.realfinance.sofa.cg.core.domain.PurchaseResultNotice;
import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import com.realfinance.sofa.cg.core.facade.CgContractManageFacade;
import com.realfinance.sofa.cg.core.facade.CgVendorRatingsFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.ContractManageRepository;
import com.realfinance.sofa.cg.core.repository.PurchaseResultNoticeRepository;
import com.realfinance.sofa.cg.core.repository.VendorRatingsRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.ContractManageDetailsMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ContractManageMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ContractManageSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.VendorRatingsMapper;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.dataAccessForbidden;


@Service
@SofaService(interfaceType = CgVendorRatingsFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgVendorRatingseImpl implements CgVendorRatingsFacade {
    private static final Logger log = LoggerFactory.getLogger(CgVendorRatingseImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final VendorRatingsRepository vendorRatingsRepository;
    private final VendorRatingsMapper vendorRatingsMapper;
    private final PurchaseResultNoticeRepository purchaseResultNoticeRepository;

    public CgVendorRatingseImpl(JpaQueryHelper jpaQueryHelper, VendorRatingsRepository vendorRatingsRepository, VendorRatingsMapper vendorRatingsMapper, PurchaseResultNoticeRepository purchaseResultNoticeRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.vendorRatingsRepository = vendorRatingsRepository;
        this.vendorRatingsMapper = vendorRatingsMapper;
        this.purchaseResultNoticeRepository = purchaseResultNoticeRepository;
    }

    @Override
    public Page<CgVendorRatingsDto> list(CgVendorRatingsQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        System.out.println("搞一个代码冲突");
        Page<VendorRating> result = vendorRatingsRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(vendorRatingsMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<VendorRating> toDelete = vendorRatingsRepository.findAll(
                ((Specification<VendorRating>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            vendorRatingsRepository.deleteAll(toDelete);
            vendorRatingsRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败", e);
            }
            throw businessException("删除失败");
        }
    }


//    @Override
//    public CgContractManageDetailsDto getDetailsById(@NotNull Integer id) {
//        Objects.requireNonNull(id);
//        return contractManageDetailsMapper.toDto(getContractManage(id));
//    }


    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
//    protected ContractManage getContractManage(Integer id) {
//        Objects.requireNonNull(id);
//        List<ContractManage> all = vendorRatingsRepository.findAll(
//                ((Specification<ContractManage>) (root, query, criteriaBuilder) ->
//                        criteriaBuilder.equal(root.get("id"), id))
//                        .and(jpaQueryHelper.dataRuleSpecification()));
//        if (all.isEmpty()) {
//            if (!vendorRatingsRepository.existsById(id)) {
//                System.out.println("找不到相应合同管理");
//            }
//            throw dataAccessForbidden();
//        }
//        return all.get(0);
//    }
}
