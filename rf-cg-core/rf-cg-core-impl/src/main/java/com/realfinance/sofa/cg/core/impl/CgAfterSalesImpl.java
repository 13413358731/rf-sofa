package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.AfterSales.AfterSales;
import com.realfinance.sofa.cg.core.domain.commodity.Commodity;
import com.realfinance.sofa.cg.core.facade.CgAfterSalesFacade;
import com.realfinance.sofa.cg.core.facade.CgCommodityFacade;
import com.realfinance.sofa.cg.core.model.CgAfterSalesDto;
import com.realfinance.sofa.cg.core.model.CgAfterSalesQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgCommodityDto;
import com.realfinance.sofa.cg.core.model.CgCommodityQueryCriteria;
import com.realfinance.sofa.cg.core.repository.AfterSalesRepository;
import com.realfinance.sofa.cg.core.repository.CommodityRepository;
import com.realfinance.sofa.cg.core.repository.PurchaseResultNoticeRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.AfterSalesMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.CommodityMapper;
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
@SofaService(interfaceType = CgAfterSalesFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgAfterSalesImpl implements CgAfterSalesFacade {
    private static final Logger log = LoggerFactory.getLogger(CgAfterSalesImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final AfterSalesRepository afterSalesRepository;
    private final AfterSalesMapper afterSalesMapper;
    private final PurchaseResultNoticeRepository purchaseResultNoticeRepository;

    public CgAfterSalesImpl(JpaQueryHelper jpaQueryHelper, AfterSalesRepository afterSalesRepository, AfterSalesMapper afterSalesMapper, PurchaseResultNoticeRepository purchaseResultNoticeRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.afterSalesRepository = afterSalesRepository;
        this.afterSalesMapper = afterSalesMapper;
        this.purchaseResultNoticeRepository = purchaseResultNoticeRepository;
    }

    @Override
    public Page<CgAfterSalesDto> list(CgAfterSalesQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<AfterSales> result = afterSalesRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(afterSalesMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<AfterSales> toDelete = afterSalesRepository.findAll(
                ((Specification<AfterSales>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            afterSalesRepository.deleteAll(toDelete);
            afterSalesRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败", e);
            }
            throw businessException("删除失败");
        }
    }
}
