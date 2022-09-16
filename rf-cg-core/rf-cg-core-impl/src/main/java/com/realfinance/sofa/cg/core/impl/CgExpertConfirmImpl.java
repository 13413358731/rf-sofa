package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.domain.DrawExpertList;
import com.realfinance.sofa.cg.core.domain.ExpertConfirm;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.facade.CgExpertConfirmFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.DrawExpertListRepository;
import com.realfinance.sofa.cg.core.repository.DrawExpertRepository;
import com.realfinance.sofa.cg.core.repository.ExpertConfirmRepository;
import com.realfinance.sofa.cg.core.repository.ProjectRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertDetailsMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.DrawExpertListMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertConfirmMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertConfirmSaveMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.core.util.QueryCriteriaUtils.toSpecificationExpertConfirm;

@Service
@SofaService(interfaceType = CgExpertConfirmFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional()
public class CgExpertConfirmImpl implements CgExpertConfirmFacade {

    private static final Logger log = LoggerFactory.getLogger(CgExpertConfirmImpl.class);

    private final ExpertConfirmMapper expertConfirmMapper;
    private final ExpertConfirmSaveMapper expertConfirmSaveMapper;
    private final JpaQueryHelper jpaQueryHelper;
    private final ExpertConfirmRepository expertConfirmRepository;
    private final DrawExpertDetailsMapper drawExpertDetailsMapper;
    private final DrawExpertListMapper drawExpertListMapper;
    private final DrawExpertRepository drawExpertRepository;
    private final DrawExpertListRepository drawExpertListRepository;
    private final ProjectRepository projectRepository;

    public CgExpertConfirmImpl(ExpertConfirmMapper expertConfirmMapper, ExpertConfirmSaveMapper expertConfirmSaveMapper, JpaQueryHelper jpaQueryHelper, ExpertConfirmRepository expertConfirmRepository, DrawExpertDetailsMapper drawExpertDetailsMapper, DrawExpertListMapper drawExpertListMapper, DrawExpertRepository drawExpertRepository, DrawExpertListRepository drawExpertListRepository, ProjectRepository projectRepository) {
        this.expertConfirmMapper = expertConfirmMapper;
        this.expertConfirmSaveMapper = expertConfirmSaveMapper;
        this.jpaQueryHelper = jpaQueryHelper;
        this.expertConfirmRepository = expertConfirmRepository;
        this.drawExpertDetailsMapper = drawExpertDetailsMapper;
        this.drawExpertListMapper = drawExpertListMapper;
        this.drawExpertRepository = drawExpertRepository;
        this.drawExpertListRepository = drawExpertListRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Page<CgExpertConfirmDto> list(CgExpertConfirmQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<ExpertConfirm> result = expertConfirmRepository.findAll(toSpecificationExpertConfirm(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(expertConfirmMapper::toDto);
    }

    @Override
    public CgExpertConfirmDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return expertConfirmMapper.toDto(getExpertConfirm(id));
    }

    @Override
    public List<CgExpertConfirmDto> notifyExperts(@NotNull Integer id, Set<Integer> ids) {
        List<ExpertConfirm> list=new ArrayList<>();
        for (Integer expertList : ids) {
            ExpertConfirm expertConfirm = new ExpertConfirm();
            CgDrawExpertDetailsDto cgDrawExpertDetailsDto = drawExpertDetailsMapper.toDto(getDrawExpert(id));
            CgDrawExpertListDto cgDrawExpertListDto = drawExpertListMapper.toDto(getDrawExpertList(expertList));
            expertConfirm.setEvent(cgDrawExpertDetailsDto.getEvent());
            expertConfirm.setSort(cgDrawExpertDetailsDto.getSort());
            expertConfirm.setNoticeContent(cgDrawExpertDetailsDto.getNoticeExpert());
            expertConfirm.setTenantId(cgDrawExpertDetailsDto.getTenantId());
            expertConfirm.setDepartmentId(cgDrawExpertDetailsDto.getDepartmentId());
            expertConfirm.setDrawer(cgDrawExpertDetailsDto.getDrawer());
            expertConfirm.setConfirmStatus(0);//默认为待确认状态
            expertConfirm.setExpertListId(cgDrawExpertListDto.getId());
            expertConfirm.setExpertUserId(cgDrawExpertListDto.getExpert().getMemberCode());
            expertConfirm.setProjectId(cgDrawExpertDetailsDto.getProjectId());
            expertConfirm.setCreatedUserId(cgDrawExpertListDto.getExpert().getMemberCode());
            expertConfirm.setModifiedUserId(cgDrawExpertListDto.getExpert().getMemberCode());
            expertConfirm.setTenantId(cgDrawExpertListDto.getExpert().getTenantId());
            expertConfirm.setDepartmentId(cgDrawExpertListDto.getExpert().getExpertDepartment());
            try {
                ExpertConfirm saved = expertConfirmRepository.saveAndFlush(expertConfirm);
                list.add(saved);
//            return saved.getId();
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败",e);
                }
                throw businessException("保存失败,because:"+e.getMessage());
            }

            //更新专家通知状态
            DrawExpertList drawExpertList = getDrawExpertList(expertList);
            drawExpertList.setNoticeStatus(true);
            try {
                DrawExpertList saved = drawExpertListRepository.saveAndFlush(drawExpertList);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败",e);
                }
                throw businessException("保存失败,because:"+e.getMessage());
            }
        }
        return expertConfirmMapper.toDtoList(list);
    }


//    @Override
    public void notifyExperts2(@NotNull Integer id) {
        ExpertConfirm expertConfirm = new ExpertConfirm();
        expertConfirm.setEvent(drawExpertDetailsMapper.toDto(getDrawExpert(id)).getEvent());
        expertConfirm.setSort(drawExpertDetailsMapper.toDto(getDrawExpert(id)).getSort());
        expertConfirm.setNoticeContent(drawExpertDetailsMapper.toDto(getDrawExpert(id)).getNoticeExpert());
        expertConfirm.setTenantId(drawExpertDetailsMapper.toDto(getDrawExpert(id)).getTenantId());
        expertConfirm.setDepartmentId(drawExpertDetailsMapper.toDto(getDrawExpert(id)).getDepartmentId());
        expertConfirm.setDrawer(drawExpertDetailsMapper.toDto(getDrawExpert(id)).getDrawer());

        try {
            ExpertConfirm saved = expertConfirmRepository.saveAndFlush(expertConfirm);
//            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer confirm(@NotNull CgExpertConfirmSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        DrawExpertList drawExpertList = getDrawExpertList(saveDto.getExpertListId());
        DrawExpert drawExpert = drawExpertList.getDrawExpert();
        int confirmNumber = drawExpert.getConfirmNumber();
        int drawNumber = drawExpert.getMeetingNumber();

        //更新专家抽取主表的已确认出席专家数
        if(saveDto.getConfirmStatus()==1){
            drawExpert.setConfirmNumber(++confirmNumber);
        }else if (saveDto.getConfirmStatus()==2){
            drawExpert.setConfirmNumber(--confirmNumber);
        }
        try {
            drawExpertRepository.saveAndFlush(drawExpert);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }

        ExpertConfirm expertConfirmSaved;
        if(confirmNumber <= drawNumber||(saveDto.getConfirmStatus()==2)){
            ExpertConfirm expertConfirm;
            System.out.println(saveDto.getId());
            ExpertConfirm entity = getExpertConfirm(saveDto.getId());
            expertConfirm = expertConfirmSaveMapper.updateEntity(entity,saveDto);
            expertConfirm.setConfirmTime(LocalDateTime.now());
            try {
                expertConfirmSaved = expertConfirmRepository.saveAndFlush(expertConfirm);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败",e);
                }
                throw businessException("保存失败,because:"+e.getMessage());
            }

            //更新抽取列表的确认状态
            drawExpertList.setIsAttend(saveDto.getConfirmStatus());
            drawExpertList.setAbsentReason(saveDto.getAbsentReason());
            try {
                drawExpertListRepository.saveAndFlush(drawExpertList);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败",e);
                }
                throw businessException("保存失败,because:"+e.getMessage());
            }
        }else {
            throw businessException("参会人数已足够，无法确认参会");
        }
        return expertConfirmSaved.getId();
    }

    /**
     * 根据ID获取专家列表实体
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
                System.out.println("专家确认下找不到相应的专家列表");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取抽取主表实体
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
                System.out.println("找不到相应抽取专家主表");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取抽取主表实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected ExpertConfirm getExpertConfirm(Integer id) {
        Objects.requireNonNull(id);
        List<ExpertConfirm> all = expertConfirmRepository.findAll(
                ((Specification<ExpertConfirm>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!expertConfirmRepository.existsById(id)) {
                System.out.println("找不到相应专家确认列表");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取抽取主表实体
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
