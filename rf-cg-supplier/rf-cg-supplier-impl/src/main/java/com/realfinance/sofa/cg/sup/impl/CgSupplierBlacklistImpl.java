package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.FlowStatus;
import com.realfinance.sofa.cg.sup.domain.Supplier;
import com.realfinance.sofa.cg.sup.domain.SupplierBlacklist;
import com.realfinance.sofa.cg.sup.facade.CgSupplierBlacklistFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgSupplierBlacklistSaveDto;
import com.realfinance.sofa.cg.sup.repository.SupplierBlacklistRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierBlacklistDetailsMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierBlacklistMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierBlacklistSaveMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.sup.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupplierBlacklistFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierBlacklistImpl implements CgSupplierBlacklistFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierBlacklistImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierBlacklistRepository supplierBlacklistRepository;
    private final SupplierBlacklistMapper supplierBlacklistMapper;
    private final SupplierBlacklistDetailsMapper supplierBlacklistDetailsMapper;
    private final SupplierBlacklistSaveMapper supplierBlacklistSaveMapper;

    public CgSupplierBlacklistImpl(JpaQueryHelper jpaQueryHelper,
                                   SupplierBlacklistRepository supplierBlacklistRepository,
                                   SupplierBlacklistMapper supplierBlacklistMapper,
                                   SupplierBlacklistDetailsMapper supplierBlacklistDetailsMapper,
                                   SupplierBlacklistSaveMapper supplierBlacklistSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierBlacklistRepository = supplierBlacklistRepository;
        this.supplierBlacklistMapper = supplierBlacklistMapper;
        this.supplierBlacklistDetailsMapper = supplierBlacklistDetailsMapper;
        this.supplierBlacklistSaveMapper = supplierBlacklistSaveMapper;
    }


    @Override
    public Page<CgSupplierBlacklistDto> list(CgSupplierBlacklistQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierBlacklist> result = supplierBlacklistRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierBlacklistMapper::toDto);
    }

    @Override
    public CgSupplierBlacklistDto getById(Integer id) {
        Objects.requireNonNull(id);
        return supplierBlacklistMapper.toDto(getSupplierBlacklist(id));
    }

    @Override
    public CgSupplierBlacklistDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        return supplierBlacklistDetailsMapper.toDto(getSupplierBlacklist(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgSupplierBlacklistSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        SupplierBlacklist supplierBlacklist;
        if (saveDto.getId() == null) { // 新增
            supplierBlacklist = supplierBlacklistSaveMapper.toEntity(saveDto);
            supplierBlacklist.setTenantId(DataScopeUtils.loadTenantId());
            supplierBlacklist.setStatus(FlowStatus.EDIT);
            supplierBlacklist.setValid(false);
        } else { // 修改
            SupplierBlacklist entity = getSupplierBlacklist(saveDto.getId());
            supplierBlacklist = supplierBlacklistSaveMapper.updateEntity(entity,saveDto);
        }
        preSave(supplierBlacklist);
        try {
            SupplierBlacklist saved = supplierBlacklistRepository.saveAndFlush(supplierBlacklist);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        SupplierBlacklist supplierBlacklist = supplierBlacklistRepository.findById(id)
                .orElseThrow(() -> entityNotFound(SupplierBlacklist.class,"id",id));

        FlowStatus currentStatusEnum = supplierBlacklist.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id,status);
            }
            return;
        }
        supplierBlacklist.setStatus(statusEnum);
        if (statusEnum == PASS) {
            handlePass(supplierBlacklist);
        }
        try {
            supplierBlacklistRepository.saveAndFlush(supplierBlacklist);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败",e);
            }
            throw businessException("更新状态失败",e);
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
        List<SupplierBlacklist> toDelete = supplierBlacklistRepository.findAll(
                ((Specification<SupplierBlacklist>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 检查是否可删除
        toDelete.forEach(e -> {
            if (e.getValid()) {
                throw businessException("黑名单已生效，不能删除");
            }
            if (e.getStatus() != FlowStatus.EDIT) {
                throw businessException("流程已启动，不能删除");
            }
        });
        // 删除
        try {
            supplierBlacklistRepository.deleteAll(toDelete);
            supplierBlacklistRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invalid(Integer id) {
        SupplierBlacklist supplierBlacklist = getSupplierBlacklist(id);
        if (!supplierBlacklist.getValid()) {
            throw businessException("黑名单已失效");
        }
        supplierBlacklist.setValid(false);
        Supplier supplier = supplierBlacklist.getSupplier();
        if (!supplierBlacklistRepository.existsBySupplierAndValid(supplier, true)) {
            supplier.setBlacklisted(false);
        }
        try {
            supplierBlacklistRepository.saveAndFlush(supplierBlacklist);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("失效失败",e);
            }
            throw businessException("失效失败");
        }
    }

    /**
     * 通过处理
     * @param supplierBlacklist
     */
    protected void handlePass(SupplierBlacklist supplierBlacklist) {
        if (log.isInfoEnabled()) {
            log.info("执行供应商黑名单通过处理，黑名单ID：{}",supplierBlacklist.getId());
        }
        supplierBlacklist.setPassTime(LocalDateTime.now());
        supplierBlacklist.setValid(true);
        Supplier supplier = supplierBlacklist.getSupplier();
        supplier.setBlacklisted(true);
    }

    protected void preSave(SupplierBlacklist supplierBlacklist) {
        if (supplierBlacklist.getStatus() == FlowStatus.PASS) {
            throw businessException("流程已通过，不能修改");
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected SupplierBlacklist getSupplierBlacklist(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierBlacklist> all = supplierBlacklistRepository.findAll(
                ((Specification<SupplierBlacklist>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!supplierBlacklistRepository.existsById(id)) {
                throw entityNotFound(SupplierBlacklist.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }
}
