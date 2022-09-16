package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.ExpertLabel;
import com.realfinance.sofa.cg.core.exception.RfCgCoreException;
import com.realfinance.sofa.cg.core.facade.CgExpertLabelFacade;
import com.realfinance.sofa.cg.core.model.CgExpertLabelDto;
import com.realfinance.sofa.cg.core.model.CgExpertLabelQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgExpertLabelSaveDto;
import com.realfinance.sofa.cg.core.model.CgExpertLabelSmallDto;
import com.realfinance.sofa.cg.core.repository.ExpertLabelRepository;
import com.realfinance.sofa.cg.core.repository.ExpertLabelTypeRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertLabelMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertLabelSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertLabelSmallMapper;
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
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.core.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgExpertLabelFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgExpertLabelImpl implements CgExpertLabelFacade {

    private static final Logger log = LoggerFactory.getLogger(CgExpertLabelImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final ExpertLabelRepository expertLabelRepository;
    private final ExpertLabelTypeRepository expertLabelTypeRepository;
    private final ExpertLabelMapper expertLabelMapper;
    private final ExpertLabelSaveMapper expertLabelSaveMapper;
    private final ExpertLabelSmallMapper expertLabelSmallMapper;

    public CgExpertLabelImpl(JpaQueryHelper jpaQueryHelper, ExpertLabelRepository expertLabelRepository, ExpertLabelTypeRepository expertLabelTypeRepository, ExpertLabelMapper expertLabelMapper, ExpertLabelSaveMapper expertLabelSaveMapper, ExpertLabelSmallMapper expertLabelSmallMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.expertLabelRepository = expertLabelRepository;
        this.expertLabelTypeRepository = expertLabelTypeRepository;
        this.expertLabelMapper = expertLabelMapper;
        this.expertLabelSaveMapper = expertLabelSaveMapper;
        this.expertLabelSmallMapper = expertLabelSmallMapper;
    }

    @Override
    public List<CgExpertLabelDto> list(CgExpertLabelQueryCriteria queryCriteria) {
        List<ExpertLabel> result = expertLabelRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return expertLabelMapper.toDtoList(result);
    }

    @Override
    public Page<CgExpertLabelDto> queryRefer(CgExpertLabelQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<ExpertLabel> result = expertLabelRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(expertLabelMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgExpertLabelSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        try {
            ExpertLabel expertLabel;
            if (saveDto.getId() == null) { // 新增
                expertLabel = handleNewExpertLabel(saveDto);
            } else { // 修改
                expertLabel = handleUpdateExpertLabel(saveDto);
            }
            if(expertLabel==null){
                throw businessException("保存失败");
            }
            expertLabelRepository.flush();
            return expertLabel.getId();
        } catch (RfCgCoreException e) {
            throw e;
        }/* catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败");
        }*/
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<ExpertLabel> toDelete = expertLabelRepository.findAll(
                ((Specification<ExpertLabel>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        try {
            for (ExpertLabel expertLabel : toDelete) {
                ExpertLabel parent = expertLabel.getParent();
                expertLabelRepository.delete(expertLabel);
                updateLeafCount(parent);
            }
            expertLabelRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    @Override
    public CgExpertLabelSmallDto queryExpertLabelSmallDto(Integer expertLabelSmallId) {
        Objects.requireNonNull(expertLabelSmallId);
        return expertLabelRepository.findById(expertLabelSmallId)
                .map(expertLabelSmallMapper::toDto)
                .orElse(null);
    }

    /**
     * 处理新增标签
     * @param saveDto
     * @return
     */
    protected ExpertLabel handleNewExpertLabel(CgExpertLabelSaveDto saveDto) {
        String tenantId = DataScopeUtils.loadTenantId();
        boolean isFlag = expertLabelRepository.existsByTenantIdAndTypeAndNameAndValue(tenantId,
                expertLabelTypeRepository.getOne(saveDto.getType()), saveDto.getName(), saveDto.getValue());
        if (isFlag) {
            throw businessException("标签已存在");
        }

        ExpertLabel expertLabel = expertLabelSaveMapper.toEntity(saveDto);

        if (expertLabel.getParent() != null) {
            DataScopeUtils.checkTenantCanAccess(expertLabel.getParent().getTenantId());
            if (!Objects.equals(expertLabel.getType(), expertLabel.getParent().getType())) {
                throw businessException("与上级标签类型不一致");
            }
        }
        expertLabel.setTenantId(tenantId);
        expertLabel.setLeafCount(0);
        ExpertLabel saved = expertLabelRepository.save(expertLabel);
        updateLeafCount(saved.getParent());
        return saved;
    }

    /**
     * 处理修改标签
     * @param saveDto
     * @return
     */
    protected ExpertLabel handleUpdateExpertLabel(CgExpertLabelSaveDto saveDto) {
        String tenantId = DataScopeUtils.loadTenantId();
        ExpertLabel entity = expertLabelRepository.findById(saveDto.getId())
                .orElseThrow(() -> entityNotFound(ExpertLabel.class, "id", saveDto.getId()));

        if (!Objects.equals(saveDto.getType(), entity.getType().getId())
                ||!Objects.equals(saveDto.getName(),entity.getName()) || !Objects.equals(saveDto.getValue(), entity.getValue())) {
            if (expertLabelRepository.existsByTenantIdAndTypeAndNameAndValue(tenantId,
                    expertLabelTypeRepository.getOne(saveDto.getType()),saveDto.getName(),saveDto.getValue())) {
                throw businessException("标签已存在");
            }
        }

        // 修改前的父节点
        ExpertLabel formerParent = entity.getParent();

        ExpertLabel expertLabel = expertLabelSaveMapper.updateEntity(entity,saveDto);
        if (expertLabel.getParent() != null) {
            DataScopeUtils.checkTenantCanAccess(expertLabel.getParent().getTenantId());
            if (!Objects.equals(expertLabel.getType(), expertLabel.getParent().getType())) {
                throw businessException("与上级标签类型不一致");
            }
        }
        ExpertLabel saved = expertLabelRepository.save(expertLabel);
        updateLeafCount(formerParent);
        updateLeafCount(saved.getParent());
        return saved;
    }


    /**
     * 更新子页数
     * @param expertLabel
     */
    private void updateLeafCount(ExpertLabel expertLabel) {
        if (expertLabel == null) {
            return;
        }
        expertLabel.setLeafCount(expertLabelRepository.countByParent(expertLabel));
        try {
            expertLabelRepository.save(expertLabel);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新子页数失败",e);
            }
            throw businessException("更新子页数失败");
        }
    }

}
