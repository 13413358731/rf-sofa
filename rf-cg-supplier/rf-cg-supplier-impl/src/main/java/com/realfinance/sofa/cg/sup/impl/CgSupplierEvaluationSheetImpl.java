package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.FlowStatus;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationSheetMain;
import com.realfinance.sofa.cg.sup.facade.CgSupplierEvaluationSheetFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetMainDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetMainQueryCriteria;
import com.realfinance.sofa.cg.sup.repository.SupplierEvaluationSheetRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierEvaluationSheetDetailsMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierEvaluationSheetDtoMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierEvaluationSheetMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierEvaluationSheetSaveMapper;
import com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.sup.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupplierEvaluationSheetFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierEvaluationSheetImpl implements CgSupplierEvaluationSheetFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierEvaluationSheetImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierEvaluationSheetRepository supplierEvaluationSheetRepository;
    private final SupplierEvaluationSheetMapper supplierEvaluationSheetMapper;
    private final SupplierEvaluationSheetDetailsMapper supplierEvaluationSheetDetailsMapper;
    private final SupplierEvaluationSheetSaveMapper supplierEvaluationSheetSaveMapper;
    private final SupplierEvaluationSheetDtoMapper supplierEvaluationSheetDtoMapper;

    public CgSupplierEvaluationSheetImpl(JpaQueryHelper jpaQueryHelper, SupplierEvaluationSheetRepository supplierEvaluationSheetRepository, SupplierEvaluationSheetMapper supplierEvaluationSheetMapper, SupplierEvaluationSheetDetailsMapper supplierEvaluationSheetDetailsMapper, SupplierEvaluationSheetSaveMapper supplierEvaluationSheetSaveMapper, SupplierEvaluationSheetDtoMapper supplierEvaluationSheetDtoMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierEvaluationSheetRepository = supplierEvaluationSheetRepository;
        this.supplierEvaluationSheetMapper = supplierEvaluationSheetMapper;
        this.supplierEvaluationSheetDetailsMapper = supplierEvaluationSheetDetailsMapper;
        this.supplierEvaluationSheetSaveMapper = supplierEvaluationSheetSaveMapper;
        this.supplierEvaluationSheetDtoMapper = supplierEvaluationSheetDtoMapper;
    }

    @Override
    public Page<CgSupplierEvaluationSheetMainDto> list(CgSupplierEvaluationSheetMainQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierEvaluationSheetMain> result = supplierEvaluationSheetRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierEvaluationSheetMapper::toDto);
    }

    @Override
    public CgSupplierEvaluationSheetDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return supplierEvaluationSheetDetailsMapper.toDto(getSupplierEvaluationSheet(id));
    }

//    @Override
//    public CgSupplierEvaluationSheetMainDto getById(Integer id) {
//        Objects.requireNonNull(id);
//        SupplierEvaluationSheetMain result = getSupplierEvaluationSheet(id);
//        CgSupplierEvaluationSheetMainDto result2 = supplierEvaluationSheetMapper.toDto(result);
//        return result2;
//    }
    @Override
    public CgSupplierEvaluationSheetDetailsSaveDto getById(Integer id) {
        Objects.requireNonNull(id);
        SupplierEvaluationSheetMain result = getSupplierEvaluationSheet(id);
        CgSupplierEvaluationSheetDetailsSaveDto result2 = supplierEvaluationSheetDtoMapper.toDto(result);
        return result2;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(@NotNull CgSupplierEvaluationSheetDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        SupplierEvaluationSheetMain supplierEvaluationSheetMain;
        System.out.println(saveDto.getId());
        if (saveDto.getId() == null) { // 新增
            supplierEvaluationSheetMain = supplierEvaluationSheetSaveMapper.toEntity(saveDto);
            supplierEvaluationSheetMain.setTenantId(DataScopeUtils.loadTenantId());
            supplierEvaluationSheetMain.setStatus(FlowStatus.EDIT);
            supplierEvaluationSheetMain.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
            supplierEvaluationSheetMain.setValid(false);
        } else { // 修改
            SupplierEvaluationSheetMain entity = getSupplierEvaluationSheet(saveDto.getId());
            supplierEvaluationSheetMain = supplierEvaluationSheetSaveMapper.updateEntity(entity,saveDto);
            if (entity.getStatus() == PASS) {
                throw businessException("流程已通过，不能修改");
            }
        }
//        preSave(supplierEvaluationSheetMain);
        try {
            SupplierEvaluationSheetMain saved = supplierEvaluationSheetRepository.saveAndFlush(supplierEvaluationSheetMain);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,becauser:"+e.getMessage());
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
        List<SupplierEvaluationSheetMain> toDelete = supplierEvaluationSheetRepository.findAll(
                ((Specification<SupplierEvaluationSheetMain>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            supplierEvaluationSheetRepository.deleteAll(toDelete);
            supplierEvaluationSheetRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected SupplierEvaluationSheetMain getSupplierEvaluationSheet(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierEvaluationSheetMain> all = supplierEvaluationSheetRepository.findAll(
                ((Specification<SupplierEvaluationSheetMain>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!supplierEvaluationSheetRepository.existsById(id)) {
                System.out.println("找不到相应的评估样表");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        SupplierEvaluationSheetMain supplierEvaluationSheetMain = supplierEvaluationSheetRepository.findById(id)
                .orElseThrow(() -> entityNotFound(SupplierEvaluationSheetMain.class,"id",id));

        FlowStatus currentStatusEnum = supplierEvaluationSheetMain.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id,status);
            }
            return;
        }
        supplierEvaluationSheetMain.setStatus(statusEnum);
        if (statusEnum == PASS) {
            handlePass(supplierEvaluationSheetMain);
        }
        try {
            supplierEvaluationSheetRepository.saveAndFlush(supplierEvaluationSheetMain);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败",e);
            }
            throw businessException("更新状态失败",e);
        }
    }

    @Override
    public Page<CgSupplierEvaluationSheetMainDto> queryRefer(CgSupplierEvaluationSheetMainQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierEvaluationSheetMain> result = supplierEvaluationSheetRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierEvaluationSheetMapper::toDto);
    }

    /**
     * 通过处理
     * @param supplierEvaluationSheetMain
     */
    protected void handlePass(SupplierEvaluationSheetMain supplierEvaluationSheetMain) {
        if (log.isInfoEnabled()) {
            log.info("执行供应商黑名单通过处理，黑名单ID：{}",supplierEvaluationSheetMain.getId());
        }
        supplierEvaluationSheetMain.setPassTime(LocalDateTime.now());
        supplierEvaluationSheetMain.setValid(true);
    }

    /**
     * 保存专家前处理
     * @param supplierEvaluationSheetMain
     */
//    protected void preSave(SupplierEvaluationSheetMain supplierEvaluationSheetMain) {
//        if (!supplierEvaluationSheetMain.getStatus().toString().equals("EDIT")&&supplierEvaluationSheetRepository.existsByMemberCode(supplierEvaluationSheetMain.getMemberCode())) {
//            throw businessException("添加的专家已存在");
//        }
//    }
}
