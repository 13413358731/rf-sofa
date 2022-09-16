package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.domain.DrawExpertList;
import com.realfinance.sofa.cg.core.domain.DrawExpertRule;
import com.realfinance.sofa.cg.core.domain.meeting.Meeting;
import com.realfinance.sofa.cg.core.facade.CgDrawExpertFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.DrawExpertRepository;
import com.realfinance.sofa.cg.core.repository.DrawExpertRuleRepository;
import com.realfinance.sofa.cg.core.repository.ExpertLabelRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertDetailsMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertRuleSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertSaveMapper;
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
import java.util.UUID;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.core.util.QueryCriteriaUtils.toSpecificationDrawExpert;

@Service
@SofaService(interfaceType = CgDrawExpertFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgDrawExpertImpl implements CgDrawExpertFacade {

    private static final Logger log = LoggerFactory.getLogger(CgDrawExpertImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final DrawExpertRepository drawExpertRepository;
    private final DrawExpertMapper drawExpertMapper;
    private final ExpertLabelRepository drawExpertLabelRepository;
    private final DrawExpertDetailsMapper drawExpertDetailsMapper;
    private final DrawExpertSaveMapper drawExpertSaveMapper;
    private final DrawExpertRuleSaveMapper drawExpertRuleSaveMapper;
    private final DrawExpertRuleRepository drawExpertRuleRepository;

    public CgDrawExpertImpl(JpaQueryHelper jpaQueryHelper, DrawExpertRepository drawExpertRepository, DrawExpertMapper drawExpertMapper, ExpertLabelRepository drawExpertLabelRepository, DrawExpertDetailsMapper drawExpertDetailsMapper, DrawExpertSaveMapper drawExpertSaveMapper, DrawExpertRuleSaveMapper drawExpertRuleSaveMapper, DrawExpertRuleRepository drawExpertRuleRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.drawExpertRepository = drawExpertRepository;
        this.drawExpertMapper = drawExpertMapper;
        this.drawExpertLabelRepository = drawExpertLabelRepository;
        this.drawExpertDetailsMapper = drawExpertDetailsMapper;
        this.drawExpertSaveMapper = drawExpertSaveMapper;
        this.drawExpertRuleSaveMapper = drawExpertRuleSaveMapper;
        this.drawExpertRuleRepository = drawExpertRuleRepository;
    }

    /**
     * 获取专家抽取列表
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Page<CgDrawExpertDto> list(CgDrawExpertQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<DrawExpert> result = drawExpertRepository.findAll(toSpecificationDrawExpert(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(drawExpertMapper::toDto);
    }

    /**
     * 获取某一个具体专家抽取详情
     * @param id 供应商ID
     * @return
     */
    @Override
    public CgDrawExpertDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return drawExpertDetailsMapper.toDto(getDrawExpert(id));
    }

    /**
     * 根据采购方案id查询专家抽取详情
     * @param id
     * @return
     */
    @Override
    public CgDrawExpertDetailsDto getDetailsByProjectId(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return drawExpertDetailsMapper.toDto(getDrawExpertByProjectId(id));
    }

    /**
     * 根据采购方案id查询专家抽取id
     * @param projectId
     * @return
     */
    @Override
    public Integer getIdByProjectId(Integer projectId) {
        Objects.requireNonNull(projectId);
        return drawExpertRepository.findByProjectId(projectId).map(DrawExpert::getId).orElse(null);
    }

    /**
     * 保存&&修改专家抽取
     * @param saveDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(@NotNull CgDrawExpertSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        DrawExpert drawExpert;
        System.out.println(saveDto.getId());
        if (saveDto.getId() == null) { // 新增
            drawExpert = drawExpertSaveMapper.toEntity(saveDto);
            drawExpert.setTenantId(DataScopeUtils.loadTenantId());
            drawExpert.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
            drawExpert.setDrawTime(LocalDateTime.now());
            drawExpert.setDrawer(DataScopeUtils.loadPrincipalId().orElse(null));
            drawExpert.setReceipt(UUID.randomUUID().toString());
            drawExpert.setConfirmNumber(0);
        } else { // 修改
            DrawExpert entity = getDrawExpert(saveDto.getId());
            drawExpert = drawExpertSaveMapper.updateEntity(entity,saveDto);
            for (DrawExpertList drawExpertList : drawExpert.getDrawExpertLists()) {
                drawExpertList.setDrawExpert(drawExpert);
            }
        }
        try {
            DrawExpert saved = drawExpertRepository.saveAndFlush(drawExpert);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    /**
     * 更新专家抽取状态
     * @param drawexpId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(@NotNull Integer drawexpId) {
        Objects.requireNonNull(drawexpId);
        DrawExpert drawExpert = getDrawExpert(drawexpId);
        drawExpert.setFinishStatus(true);
        try {
            DrawExpert saved = drawExpertRepository.saveAndFlush(drawExpert);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    /**
     * 删除
     * @param ids 专家抽取id集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<DrawExpert> toDelete = drawExpertRepository.findAll(
                ((Specification<DrawExpert>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            drawExpertRepository.deleteAll(toDelete);
            drawExpertRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    /**
     * 保存专家抽取规则
     * @param saveDto 抽取规则表Dto
     * @param drawExpertId 专家抽取表的id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(@NotNull List<CgDrawExpertRuleDto> saveDto,@NotNull Integer drawExpertId) {
        Objects.requireNonNull(saveDto);
        if(saveDto.isEmpty()){
            throw businessException("抽取规则不能为空");
        }
        for (CgDrawExpertRuleDto cgDrawExpertRuleVo : saveDto) {
            if (cgDrawExpertRuleVo.getExpertCount()==null){
                throw businessException("抽取人数不能为空");
            }else if(cgDrawExpertRuleVo.getExpertDeptId()==null){
                throw businessException("抽取部门不能为空");
            }
        }
        for (CgDrawExpertRuleDto cgDrawExpertRuleDto : saveDto) {
            DrawExpertRule drawExpertRule;
            if (cgDrawExpertRuleDto.getId() == null) { // 新增
                drawExpertRule = drawExpertRuleSaveMapper.toEntity(cgDrawExpertRuleDto);
                drawExpertRule.setDrawExpert(getDrawExpert(drawExpertId));
            } else { // 修改
                DrawExpertRule entity = getDrawExpertRule(cgDrawExpertRuleDto.getId());
                drawExpertRule = drawExpertRuleSaveMapper.updateEntity(entity,cgDrawExpertRuleDto);
            }
            try {
                DrawExpertRule saved = drawExpertRuleRepository.saveAndFlush(drawExpertRule);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败",e);
                }
                throw businessException("保存失败,because:"+e.getMessage());
            }
        }
        return 200;
    }

    /**
     * 根据ID获取专家抽取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected DrawExpert getDrawExpert(Integer id) {
        Objects.requireNonNull(id);
        List<DrawExpert> all = drawExpertRepository.findAll(
                ((Specification<DrawExpert>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!drawExpertRepository.existsById(id)) {
                System.out.println("找不到相应专家");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取专家抽取规则实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected DrawExpertRule getDrawExpertRule(Integer id) {
        Objects.requireNonNull(id);
        List<DrawExpertRule> all = drawExpertRuleRepository.findAll(
                ((Specification<DrawExpertRule>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!drawExpertRuleRepository.existsById(id)) {
                System.out.println("找不到相应专家抽取规则");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }


    /**
     * 根据采购方案Id获取专家抽取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected DrawExpert getDrawExpertByProjectId(Integer id) {
        Objects.requireNonNull(id);
        List<DrawExpert> all = drawExpertRepository.findAll(
                ((Specification<DrawExpert>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("projectId"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!drawExpertRepository.existsById(id)) {
                throw entityNotFound(DrawExpert.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}
