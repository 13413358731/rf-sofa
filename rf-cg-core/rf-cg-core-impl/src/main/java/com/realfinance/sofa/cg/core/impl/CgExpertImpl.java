package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.Expert;
import com.realfinance.sofa.cg.core.domain.ExpertLabel;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.facade.CgExpertFacade;
import com.realfinance.sofa.cg.core.model.CgExpertDetailsDto;
import com.realfinance.sofa.cg.core.model.CgExpertDto;
import com.realfinance.sofa.cg.core.model.CgExpertQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgExpertSaveDto;
import com.realfinance.sofa.cg.core.repository.ExpertLabelRepository;
import com.realfinance.sofa.cg.core.repository.ExpertRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertDetailsMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertSaveMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.core.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.core.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgExpertFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgExpertImpl implements CgExpertFacade {

    private static final Logger log = LoggerFactory.getLogger(CgExpertImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final ExpertRepository expertRepository;
    private final ExpertMapper expertMapper;
    private final ExpertLabelRepository expertLabelRepository;
    private final ExpertDetailsMapper expertDetailsMapper;
    private final ExpertSaveMapper expertSaveMapper;

    public CgExpertImpl(JpaQueryHelper jpaQueryHelper, ExpertRepository expertRepository, ExpertMapper expertMapper, ExpertLabelRepository expertLabelRepository, ExpertDetailsMapper expertDetailsMapper, ExpertSaveMapper expertSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.expertRepository = expertRepository;
        this.expertMapper = expertMapper;
        this.expertLabelRepository = expertLabelRepository;
        this.expertDetailsMapper = expertDetailsMapper;
        this.expertSaveMapper = expertSaveMapper;
    }

    @Override
    public Page<CgExpertDto> list(CgExpertQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<Expert> result = expertRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(expertMapper::toDto);
    }

    @Override
    public CgExpertDto getById(Integer id) {
        Objects.requireNonNull(id);
        return expertMapper.toDto(getExpert(id));
    }

    @Override
    public CgExpertDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return expertDetailsMapper.toDto(getExpert(id));
    }

    @Override
    public CgExpertDetailsDto getDetailsByUsername(@NotNull String username) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(@NotNull CgExpertSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        Expert expert;
        if (saveDto.getId() == null) { // 新增
            expert = expertSaveMapper.toEntity(saveDto);
            expert.setTenantId(DataScopeUtils.loadTenantId());
            expert.setExpertStatus(FlowStatus.EDIT);
            expert.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
            preSave(expert);
        } else { // 修改
            Expert entity = getExpert(saveDto.getId());
            expert = expertSaveMapper.updateEntity(entity,saveDto);
            if (entity.getExpertStatus() == PASS) {
                throw businessException("流程已通过，不能修改");
            }
        }
        try {
            Expert saved = expertRepository.saveAndFlush(expert);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        Expert expert = expertRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Expert.class,"id",id));

        FlowStatus currentStatusEnum = expert.getExpertStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id,status);
            }
            return;
        }
        expert.setExpertStatus(statusEnum);
        if (statusEnum == PASS) {
            handlePass(expert);
        }
        try {
            expertRepository.saveAndFlush(expert);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败",e);
            }
            throw businessException("更新状态失败",e);
        }
    }

    /**
     * 通过处理
     * @param expert
     */
    protected void handlePass(Expert expert) {
        if (log.isInfoEnabled()) {
            log.info("执行供应商黑名单通过处理，黑名单ID：{}",expert.getId());
        }
        expert.setPassTime(LocalDateTime.now());
        expert.setValid(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<Expert> toDelete = expertRepository.findAll(
                ((Specification<Expert>) (root, query, criteriaBuilder) ->
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
            if (e.getExpertStatus() != FlowStatus.EDIT) {
                throw businessException("流程已启动，不能删除");
            }
        });
        // 删除
        try {
            expertRepository.deleteAll(toDelete);
            expertRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    @Override
    public Page<CgExpertDto> queryRefer(CgExpertQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<CgExpertDto> experts = expertRepository.findAll(toSpecification(queryCriteria), pageable).map(expertMapper::toDto);
        return experts;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addExpertLabels(Integer id, Set<Integer> expertLabelIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(expertLabelIds);
        if (expertLabelIds.isEmpty()) {
            return;
        }
        Expert expert = getExpert(id);
        String tenantId = expert.getTenantId();
        Set<ExpertLabel> expertLabels = expert.getExpertLabels();
        expertLabelRepository.findAllById(expertLabelIds).forEach(e -> {
            if (!StringUtils.equals(tenantId, e.getTenantId())) {
                throw businessException("关联标签失败，TenantId不匹配");
            }
            expertLabels.add(e);
        });

        try {
            expertRepository.saveAndFlush(expert);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存标签关联失败",e);
            }
            throw businessException("保存标签关联失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeExpertLabels(Integer id, Set<Integer> expertLabelIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(expertLabelIds);
        Expert Expert = getExpert(id);
        Set<ExpertLabel> expertLabels = Expert.getExpertLabels();
        expertLabels.removeIf(s -> expertLabelIds.contains(s.getId()));
        try {
            expertRepository.saveAndFlush(Expert);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除关联标签失败",e);
            }
            throw businessException("删除关联标签失败");
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected Expert getExpert(Integer id) {
        Objects.requireNonNull(id);
        List<Expert> all = expertRepository.findAll(
                ((Specification<Expert>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!expertRepository.existsById(id)) {
                System.out.println("找不到相应专家");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 保存专家前处理
     * @param expert
     */
    protected void preSave(Expert expert) {
        if (expertRepository.existsByMemberCode(expert.getMemberCode())) {
            throw businessException("添加的专家已存在");
        }
    }

}
