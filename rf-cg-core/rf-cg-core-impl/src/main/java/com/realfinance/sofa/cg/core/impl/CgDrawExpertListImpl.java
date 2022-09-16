package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.*;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.proj.ProjectDrawExpertRule;
import com.realfinance.sofa.cg.core.facade.CgDrawExpertListFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.DrawExpertListRepository;
import com.realfinance.sofa.cg.core.repository.DrawExpertRepository;
import com.realfinance.sofa.cg.core.repository.ExpertRepository;
import com.realfinance.sofa.cg.core.repository.ProjectRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertDetailsMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertListMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertListSaveMapper;
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
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.core.util.QueryCriteriaUtils.toSpecification;
import static com.realfinance.sofa.cg.core.util.QueryCriteriaUtils.toSpecificationDrawExpertList;

@Service
@SofaService(interfaceType = CgDrawExpertListFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgDrawExpertListImpl implements CgDrawExpertListFacade {

    private static final Logger log = LoggerFactory.getLogger(CgDrawExpertListImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final DrawExpertListRepository drawExpertListRepository;
    private final DrawExpertRepository drawExpertRepository;
    private final DrawExpertListMapper drawExpertListMapper;
    private final ExpertRepository expertRepository;
    private final DrawExpertDetailsMapper drawExpertDetailsMapper;
    private final DrawExpertListSaveMapper drawExpertListSaveMapper;
    private final ProjectRepository projectRepository;

    public CgDrawExpertListImpl(JpaQueryHelper jpaQueryHelper, DrawExpertListRepository drawExpertListRepository, DrawExpertRepository drawExpertRepository, DrawExpertListMapper drawExpertListMapper, ExpertRepository expertRepository, DrawExpertDetailsMapper drawExpertDetailsMapper, DrawExpertListSaveMapper drawExpertListSaveMapper, ProjectRepository projectRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.drawExpertListRepository = drawExpertListRepository;
        this.drawExpertRepository = drawExpertRepository;
        this.drawExpertListMapper = drawExpertListMapper;
        this.expertRepository = expertRepository;
        this.drawExpertDetailsMapper = drawExpertDetailsMapper;
        this.drawExpertListSaveMapper = drawExpertListSaveMapper;
        this.projectRepository = projectRepository;
    }

    /**
     * 参会专家列表（分页形式）
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @Override
    public Page<CgDrawExpertListDto> list(CgDrawExpertListQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<DrawExpertList> result = drawExpertListRepository.findAll(toSpecificationDrawExpertList(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(drawExpertListMapper::toDto);
    }

    /**
     * 参会专家列表（List形式）
     * @param queryCriteria
     * @return
     */
    @Override
    public List<CgDrawExpertListDto> list(CgDrawExpertListQueryCriteria queryCriteria) {
        List<DrawExpertList> result = drawExpertListRepository.findAll(toSpecificationDrawExpertList(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return result.stream().map(drawExpertListMapper::toDto).collect(Collectors.toList());
    }

    /**
     * 保存抽取列表直接指定的专家
     * @param id 专家抽取主表ID
     * @param saveDto 专家抽取列表对象
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(Integer id, CgDrawExpertListSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        DrawExpertList drawExpertList;
        if (saveDto.getId() == null) { // 新增
            drawExpertList = drawExpertListSaveMapper.toEntity(saveDto);
            drawExpertList.setTenantId(DataScopeUtils.loadTenantId());
            drawExpertList.setDrawExpert(drawExpertRepository.getOne(id));
            drawExpertList.setIsAttend(0);
            //新增时判断此用户是否已经添加到抽取列表
            if (drawExpertList.getDrawExpert().getDrawExpertLists().stream()
                    .anyMatch(e -> Objects.equals(e.getExpert().getId(),saveDto.getExpert()))) {
                throw businessException("此专家已被添加到抽取列表中");
            }
        } else { // 修改
            //修改时判断此用户是否已经添加到抽取列表
            DrawExpertList entity = getDrawExpertList(saveDto.getId());
            if (entity.getDrawExpert().getDrawExpertLists().stream()
                    .anyMatch(e -> Objects.equals(e.getExpert().getId(),saveDto.getExpert()))) {
                throw businessException("此专家已被添加到抽取列表中");
            }
            entity.setIsAttend(0);
            drawExpertList = drawExpertListSaveMapper.updateEntity(entity,saveDto);
        }
        DrawExpert drawExpert = getDrawExpert(id);
        //想要抽取的人数
        int drawNumber = drawExpert.getDrawNumber();
        //已经抽取出来到列表的人数
        int drawedNumber = drawExpert.getDrawExpertLists().size();
        if(drawedNumber >= drawNumber){
            throw businessException("抽取人数已足够");
        }
        try {
            DrawExpertList saved = drawExpertListRepository.saveAndFlush(drawExpertList);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    /**
     * 保存抽取列表条件抽取的专家
     * @param id 专家抽取主表ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer saveRandomly(@NotNull Integer id) {
        //根据专家抽取id查找相应的专家抽取
        DrawExpert drawExpert = getDrawExpert(id);
        //专家抽取规则
        List<DrawExpertRule> drawExpertRules = drawExpert.getDrawExpertRules();
        //根据每一条抽取规则来抽取专家
        for (DrawExpertRule drawExpertRule : drawExpertRules) {
            CgExpertQueryCriteria queryCriteria = new CgExpertQueryCriteria();
            queryCriteria.setExpertDeptId(drawExpertRule.getExpertDeptId());
            Integer expertLabelId = drawExpertRule.getExpertLabelId();
            //根据部门找出相应的专家
            List<Expert> expertsResult = expertRepository.findAll(toSpecification(queryCriteria));
            List<Expert> result2 = new ArrayList<>();
            List<Expert> result3 = new ArrayList<>();
            //根据专家标签找出相应的专家
            for (Expert expert : expertsResult) {
                Set<ExpertLabel> expertLabels = expert.getExpertLabels();
                for (ExpertLabel expertLabel : expertLabels) {
                    if(expertLabel.getId().equals(expertLabelId)){
                        result2.add(expert);
                    }
                }
            }
            //把符合条件的专家集合进行随机打乱
            Collections.shuffle(result2);
            //判断需要抽取多少个专家，符合条件的专家如果少于需要的专家，就全部抽出来
            int drawNumber = drawExpertRule.getExpertCount() > result2.size()?result2.size():drawExpertRule.getExpertCount();
            //确定最后符合条件的专家集合
            for (int i = 0; i < drawNumber; i++) {
                result3.add(result2.get(i));//取得随机数所对应的信息，即抽取的结果
            }
            for (Expert expertsdrawed : result3) {
                DrawExpertList drawExpertList = new DrawExpertList();
                drawExpertList.setTenantId(DataScopeUtils.loadTenantId());
                drawExpertList.setExpertDepartment(DataScopeUtils.loadDepartmentId().orElse(null));
                drawExpertList.setDrawExpert(drawExpertRepository.getOne(id));
                drawExpertList.setDrawWay(DrawExpertWay.DRAWWITHITEM);
                drawExpertList.setExpert(expertsdrawed);
                drawExpertList.setInternalExpert(expertsdrawed.getInternalExpert());
                drawExpertList.setExpertType(expertsdrawed.getExpertType());
                drawExpertList.setIsAttend(0);
                DrawExpert drawExpert2 = getDrawExpert(id);
                //抽取人数
                int drawNumber2 = drawExpert2.getDrawNumber();
                //已经抽取出的人数
                int drawedNumber2 = drawExpert2.getDrawExpertLists().size();
                //如果抽取了的专家数小于需要的抽取人数，就存入表中
                if(drawedNumber2 < drawNumber2){
                    //判断此专家是否已经添加
                    if (drawExpertList.getDrawExpert().getDrawExpertLists().stream()
                            .noneMatch(e -> Objects.equals(e.getExpert().getId(),expertsdrawed.getId()))) {
                        try {
                            DrawExpertList saved = drawExpertListRepository.saveAndFlush(drawExpertList);
                        } catch (Exception e) {
                            if (log.isErrorEnabled()) {
                                log.error("保存失败",e);
                            }
                            throw businessException("保存失败,because:"+e.getMessage());
                        }
                    }
                }

            }
        }
        return 200;
    }

    /**
     * 删除
     * @param ids 抽取的专家列表ID集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<DrawExpertList> toDelete = drawExpertListRepository.findAll(
                ((Specification<DrawExpertList>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            drawExpertListRepository.deleteAll(toDelete);
            drawExpertListRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    /**
     * 根据ID获取专家抽取列表的实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected DrawExpertList getDrawExpertList(Integer id) {
        Objects.requireNonNull(id);
        List<DrawExpertList> all = drawExpertListRepository.findAll(
                ((Specification<DrawExpertList>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!drawExpertListRepository.existsById(id)) {
                System.out.println("抽取专家列表下找不到相应的专家列表");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
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
                System.out.println("找不到相应专家抽取主表");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取采购方案实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected Project getProject(Integer id) {
        Objects.requireNonNull(id);
        List<Project> all = projectRepository.findAll(
                ((Specification<Project>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!projectRepository.existsById(id)) {
                throw entityNotFound(Project.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}
