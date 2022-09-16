package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.PurchaseCatalog;
import com.realfinance.sofa.cg.core.facade.CgPurchaseCatalogFacade;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgPurchaseCatalogSaveDto;
import com.realfinance.sofa.cg.core.repository.PurchaseCatalogRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.PurchaseCatalogMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.PurchaseCatalogSaveMapper;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.cg.sup.exception.RfCgSupplierException;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.lang.ref.PhantomReference;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgPurchaseCatalogFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgPurchaseCatalogImpl implements CgPurchaseCatalogFacade {

    private static final Logger log = LoggerFactory.getLogger(CgPurchaseCatalogImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final PurchaseCatalogRepository purchaseCatalogRepository;
    private final PurchaseCatalogMapper purchaseCatalogMapper;
    private final PurchaseCatalogSaveMapper purchaseCatalogSaveMapper;

    public CgPurchaseCatalogImpl(JpaQueryHelper jpaQueryHelper,
                                 PurchaseCatalogRepository purchaseCatalogRepository,
                                 PurchaseCatalogMapper purchaseCatalogMapper,
                                 PurchaseCatalogSaveMapper purchaseCatalogSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.purchaseCatalogRepository = purchaseCatalogRepository;
        this.purchaseCatalogMapper = purchaseCatalogMapper;
        this.purchaseCatalogSaveMapper = purchaseCatalogSaveMapper;
    }

    @Override
    public List<CgPurchaseCatalogDto> list(CgPurchaseCatalogQueryCriteria queryCriteria) {
        List<PurchaseCatalog> result = purchaseCatalogRepository
                .findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                        .and(jpaQueryHelper.dataRuleSpecification()));
        return purchaseCatalogMapper.toDtoList(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgPurchaseCatalogSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        try {
            PurchaseCatalog purchaseCatalog;
            if (saveDto.getId() == null) { // 新增
                purchaseCatalog = handleNewPurchaseCatalog(saveDto);
            } else { // 修改
                purchaseCatalog = handleUpdatePurchaseCatalog(saveDto);
            }
            if(purchaseCatalog==null){
                throw businessException("保存失败");
            }
            purchaseCatalogRepository.flush();
            return purchaseCatalog.getId();
        } catch (RfCgSupplierException e) {
            throw e;
        }/* catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            };
            throw businessException("保存失败");
        }*/
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEnabled(Set<Integer> ids, Boolean enabled) {
        Objects.requireNonNull(ids);
        Objects.requireNonNull(enabled);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<PurchaseCatalog> toSave = purchaseCatalogRepository.findAll(
                ((Specification<PurchaseCatalog>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toSave.isEmpty()) {
            throw dataAccessForbidden();
        }
        toSave.forEach(e -> e.setEnabled(enabled));
        try {
            purchaseCatalogRepository.saveAll(toSave);
            purchaseCatalogRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    @Override
    public CgPurchaseCatalogDto getById(@NotNull Integer id) {
        PurchaseCatalog purchaseCatalog = getPurchaseCatalog(id);
     return    purchaseCatalogMapper.toDto(purchaseCatalog);
    }

    /**
     * 处理新增
     * @param saveDto
     * @return
     */
    protected PurchaseCatalog handleNewPurchaseCatalog(CgPurchaseCatalogSaveDto saveDto) {
        String tenantId = DataScopeUtils.loadTenantId();
        if (purchaseCatalogRepository.existsByTenantIdAndCode(tenantId,saveDto.getCode())) {
            throw businessException("编码已存在");
        }

        PurchaseCatalog purchaseCatalog = purchaseCatalogSaveMapper.toEntity(saveDto);

        if (purchaseCatalog.getParent() != null) {
            DataScopeUtils.checkTenantCanAccess(purchaseCatalog.getParent().getTenantId());
        }
        purchaseCatalog.setTenantId(tenantId);
        purchaseCatalog.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
        purchaseCatalog.setLeafCount(0);
        PurchaseCatalog saved = purchaseCatalogRepository.save(purchaseCatalog);
        updateLeafCount(saved.getParent());
        return saved;
    }


    /**
     * 处理修改
     * @param saveDto
     * @return
     */
    protected PurchaseCatalog handleUpdatePurchaseCatalog(CgPurchaseCatalogSaveDto saveDto) {
        String tenantId = DataScopeUtils.loadTenantId();
        PurchaseCatalog entity = purchaseCatalogRepository.findById(saveDto.getId())
                .orElseThrow(() -> entityNotFound(PurchaseCatalog.class, "id", saveDto.getId()));

        if (!Objects.equals(saveDto.getCode(), entity.getCode())) {
            if (purchaseCatalogRepository.existsByTenantIdAndCode(tenantId,saveDto.getCode())) {
                throw businessException("编码已存在");
            }
        }

        // 修改前的父节点
        PurchaseCatalog formerParent = entity.getParent();

        PurchaseCatalog purchaseCatalog = purchaseCatalogSaveMapper.updateEntity(entity,saveDto);
        if (purchaseCatalog.getParent() != null) {
            DataScopeUtils.checkTenantCanAccess(purchaseCatalog.getParent().getTenantId());
        }
        PurchaseCatalog saved = purchaseCatalogRepository.save(purchaseCatalog);
        updateLeafCount(formerParent);
        updateLeafCount(saved.getParent());
        return saved;
    }

    protected PurchaseCatalog getPurchaseCatalog(Integer id) {
        Objects.requireNonNull(id);
        List<PurchaseCatalog> all = purchaseCatalogRepository.findAll(
                ((Specification<PurchaseCatalog>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!purchaseCatalogRepository.existsById(id)) {
                throw entityNotFound(PurchaseCatalog.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 更新子页数
     * @param purchaseCatalog
     */
    private void updateLeafCount(PurchaseCatalog purchaseCatalog) {
        if (purchaseCatalog == null) {
            return;
        }
        purchaseCatalog.setLeafCount(purchaseCatalogRepository.countByParent(purchaseCatalog));
        try {
            purchaseCatalogRepository.save(purchaseCatalog);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新子页数失败",e);
            }
            throw businessException("更新子页数失败");
        }
    }
}
