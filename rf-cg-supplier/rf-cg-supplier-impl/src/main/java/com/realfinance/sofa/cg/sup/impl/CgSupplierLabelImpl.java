package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.SupplierLabel;
import com.realfinance.sofa.cg.sup.exception.RfCgSupplierException;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelSmallDto;
import com.realfinance.sofa.cg.sup.repository.SupplierLabelRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierLabelTypeRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierLabelMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierLabelSaveMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierLabelSmallMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupplierLabelFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierLabelImpl implements CgSupplierLabelFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierLabelImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierLabelRepository supplierLabelRepository;
    private final SupplierLabelTypeRepository supplierLabelTypeRepository;
    private final SupplierLabelMapper supplierLabelMapper;
    private final SupplierLabelSmallMapper supplierLabelSmallMapper;
    private final SupplierLabelSaveMapper supplierLabelSaveMapper;

    public CgSupplierLabelImpl(JpaQueryHelper jpaQueryHelper,
                               SupplierLabelRepository supplierLabelRepository,
                               SupplierLabelTypeRepository supplierLabelTypeRepository,
                               SupplierLabelMapper supplierLabelMapper,
                               SupplierLabelSmallMapper supplierLabelSmallMapper,
                               SupplierLabelSaveMapper supplierLabelSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierLabelRepository = supplierLabelRepository;
        this.supplierLabelTypeRepository = supplierLabelTypeRepository;
        this.supplierLabelMapper = supplierLabelMapper;
        this.supplierLabelSmallMapper = supplierLabelSmallMapper;
        this.supplierLabelSaveMapper = supplierLabelSaveMapper;
    }

    @Override
    public List<CgSupplierLabelDto> list(CgSupplierLabelQueryCriteria queryCriteria) {
        List<SupplierLabel> result = supplierLabelRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return supplierLabelMapper.toDtoList(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgSupplierLabelSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        try {
            SupplierLabel supplierLabel;
            if (saveDto.getId() == null) { // 新增
                supplierLabel = handleNewSupplierLabel(saveDto);
            } else { // 修改
                supplierLabel = handleUpdateSupplierLabel(saveDto);
            }
            supplierLabelRepository.flush();
            return supplierLabel.getId();
        } catch (RfCgSupplierException e) {
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<SupplierLabel> toDelete = supplierLabelRepository.findAll(
                ((Specification<SupplierLabel>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        try {
            for (SupplierLabel supplierLabel : toDelete) {
                SupplierLabel parent = supplierLabel.getParent();
                supplierLabelRepository.delete(supplierLabel);
                updateLeafCount(parent);
            }
            supplierLabelRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    @Override
    public CgSupplierLabelSmallDto querySupplierLabelSmallDto(Integer supplierLabelSmallId) {
        Objects.requireNonNull(supplierLabelSmallId);
        return supplierLabelRepository.findById(supplierLabelSmallId)
                .map(supplierLabelSmallMapper::toDto)
                .orElse(null);
    }

    /**
     * 处理新增标签
     * @param saveDto
     * @return
     */
    protected SupplierLabel handleNewSupplierLabel(CgSupplierLabelSaveDto saveDto) {
        String tenantId = DataScopeUtils.loadTenantId();
        if (supplierLabelRepository.existsByTenantIdAndTypeAndValue(tenantId,
                supplierLabelTypeRepository.getOne(saveDto.getType()),saveDto.getValue())) {
            throw businessException("标签已存在");
        }

        SupplierLabel supplierLabel = supplierLabelSaveMapper.toEntity(saveDto);

        if (supplierLabel.getParent() != null) {
            DataScopeUtils.checkTenantCanAccess(supplierLabel.getParent().getTenantId());
            if (!Objects.equals(supplierLabel.getType(), supplierLabel.getParent().getType())) {
                throw businessException("与上级标签类型不一致");
            }
        }
        supplierLabel.setTenantId(tenantId);
        supplierLabel.setLeafCount(0);
        SupplierLabel saved = supplierLabelRepository.save(supplierLabel);
        updateLeafCount(saved.getParent());
        return saved;
    }

    /**
     * 处理修改标签
     * @param saveDto
     * @return
     */
    protected SupplierLabel handleUpdateSupplierLabel(CgSupplierLabelSaveDto saveDto) {
        String tenantId = DataScopeUtils.loadTenantId();
        SupplierLabel entity = supplierLabelRepository.findById(saveDto.getId())
                .orElseThrow(() -> entityNotFound(SupplierLabel.class, "id", saveDto.getId()));

        if (!Objects.equals(saveDto.getType(), entity.getType().getId())
                || !Objects.equals(saveDto.getValue(), entity.getValue())) {
            if (supplierLabelRepository.existsByTenantIdAndTypeAndValue(tenantId,
                    supplierLabelTypeRepository.getOne(saveDto.getType()),saveDto.getValue())) {
                throw businessException("标签已存在");
            }
        }

        // 修改前的父节点
        SupplierLabel formerParent = entity.getParent();

        SupplierLabel supplierLabel = supplierLabelSaveMapper.updateEntity(entity,saveDto);
        if (supplierLabel.getParent() != null) {
            DataScopeUtils.checkTenantCanAccess(supplierLabel.getParent().getTenantId());
            if (!Objects.equals(supplierLabel.getType(), supplierLabel.getParent().getType())) {
                throw businessException("与上级标签类型不一致");
            }
        }
        SupplierLabel saved = supplierLabelRepository.save(supplierLabel);
        updateLeafCount(formerParent);
        updateLeafCount(saved.getParent());
        return saved;
    }


    /**
     * 更新子页数
     * @param supplierLabel
     */
    private void updateLeafCount(SupplierLabel supplierLabel) {
        if (supplierLabel == null) {
            return;
        }
        supplierLabel.setLeafCount(supplierLabelRepository.countByParent(supplierLabel));
        try {
            supplierLabelRepository.save(supplierLabel);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新子页数失败",e);
            }
            throw businessException("更新子页数失败");
        }
    }
}
