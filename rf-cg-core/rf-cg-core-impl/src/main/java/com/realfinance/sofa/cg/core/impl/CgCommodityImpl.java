package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.commodity.Commodity;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import com.realfinance.sofa.cg.core.facade.CgCommodityFacade;
import com.realfinance.sofa.cg.core.facade.CgVendorRatingsFacade;
import com.realfinance.sofa.cg.core.model.CgCommodityDto;
import com.realfinance.sofa.cg.core.model.CgCommodityQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsDto;
import com.realfinance.sofa.cg.core.model.CgVendorRatingsQueryCriteria;
import com.realfinance.sofa.cg.core.repository.CommodityRepository;
import com.realfinance.sofa.cg.core.repository.PurchaseResultNoticeRepository;
import com.realfinance.sofa.cg.core.repository.VendorRatingsRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.CommodityMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.VendorRatingsMapper;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.dataAccessForbidden;


@Service
@SofaService(interfaceType = CgCommodityFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgCommodityImpl implements CgCommodityFacade {
    private static final Logger log = LoggerFactory.getLogger(CgCommodityImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final CommodityRepository commodityRepository;
    private final CommodityMapper commodityMapper;
    private final PurchaseResultNoticeRepository purchaseResultNoticeRepository;

    public CgCommodityImpl(JpaQueryHelper jpaQueryHelper, CommodityRepository commodityRepository, CommodityMapper commodityMapper, PurchaseResultNoticeRepository purchaseResultNoticeRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.commodityRepository = commodityRepository;
        this.commodityMapper = commodityMapper;
        this.purchaseResultNoticeRepository = purchaseResultNoticeRepository;
    }

    @Override
    public Page<CgCommodityDto> list(CgCommodityQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<Commodity> result = commodityRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(commodityMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<Commodity> toDelete = commodityRepository.findAll(
                ((Specification<Commodity>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            commodityRepository.deleteAll(toDelete);
            commodityRepository.flush();
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
