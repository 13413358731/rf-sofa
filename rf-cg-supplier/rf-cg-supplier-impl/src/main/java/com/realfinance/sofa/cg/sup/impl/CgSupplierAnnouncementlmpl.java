package com.realfinance.sofa.cg.sup.impl;


import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.*;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAnnouncementFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.sup.repository.SupplierAnnouncementAttachmentRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierAnnouncementRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAnnouncementChannelMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAnnouncementDetailsMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAnnouncementMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAnnouncementSaveMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.realfinance.sofa.cg.sup.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupplierAnnouncementFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierAnnouncementlmpl implements CgSupplierAnnouncementFacade {
    private static final Logger log = LoggerFactory.getLogger(CgSupplierAnnouncementlmpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierAnnouncementRepository announcementRepository;
    private final SupplierAnnouncementSaveMapper announcementSaveMapper;
    private final SupplierAnnouncementMapper announcementMapper;
    private final SupplierAnnouncementDetailsMapper detailsMapper;
    private final SupplierAnnouncementAttachmentRepository announcementAttachmentRepository;

    public CgSupplierAnnouncementlmpl(SupplierAnnouncementRepository announcementRepository,
                                      SupplierAnnouncementSaveMapper announcementSaveMapper,
                                      SupplierAnnouncementMapper announcementMapper,
                                      SupplierAnnouncementDetailsMapper detailsMapper,
                                      JpaQueryHelper jpaQueryHelper,
                                      SupplierAnnouncementAttachmentRepository announcementAttachmentRepository

    ) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.announcementRepository = announcementRepository;
        this.announcementSaveMapper = announcementSaveMapper;
        this.announcementMapper = announcementMapper;
        this.detailsMapper = detailsMapper;
        this.announcementAttachmentRepository = announcementAttachmentRepository;

    }

    /**
     * 公告列表
     *
     * @param pageable
     */
    @Override
    public Page<SupplierAnnouncementDto> list(Pageable pageable, AnnouncementQueryCriteria queryCriteria) {
        Objects.requireNonNull(pageable);
        Page<SupplierAnnouncement> all = announcementRepository.findAll(new Specification<SupplierAnnouncement>() {
            @Override
            public Predicate toPredicate(Root<SupplierAnnouncement> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (queryCriteria == null) {
                    return null;
                }
                List<Predicate> predicates = new ArrayList<>();

                if (StringUtils.isNotEmpty(queryCriteria.getChannelName())){
                predicates.add(criteriaBuilder.like(root.get("channels").get("channelName"),"%"+queryCriteria.getChannelName()+"%"));

                }
                if (StringUtils.isNotEmpty(queryCriteria.getType())) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), queryCriteria.getType()));
                }
                if (StringUtils.isNotEmpty(queryCriteria.getTitleLike())) {
                    predicates.add(criteriaBuilder.like(root.get("title"),"%"+queryCriteria.getTitleLike()+"%"));
                }
                List<Order> orders=new ArrayList<>();
                orders.add(criteriaBuilder.desc(root.get("isTop")));
                orders.add(criteriaBuilder.desc(root.get("modifiedTime")));
                return query.orderBy(orders).where(predicates.toArray(Predicate[]::new)).getRestriction();

            }
        },pageable);
        return all.map(announcementMapper::toDto);


    }


    /**
     * 保存更新
     *
     * @param SaveDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(SupplierAnnouncementSaveDto SaveDto) {
        Objects.requireNonNull(SaveDto);
        SupplierAnnouncement supplierAnnouncement;
        if (SaveDto.getId() == null) {//新增
            supplierAnnouncement = announcementSaveMapper.toEntity(SaveDto);
            supplierAnnouncement.setTenantId(DataScopeUtils.loadTenantId());
            supplierAnnouncement.setDepartmentId(DataScopeUtils.loadDepartmentId().get());
            supplierAnnouncement.setStatus(FlowStatus.EDIT);
            supplierAnnouncement.setReleasestatus("0");
        } else {  //修改
            SupplierAnnouncement entity = getSupplierAnnouncement(SaveDto.getId());
            supplierAnnouncement = announcementSaveMapper.updateEntity(entity, SaveDto);
            if (entity.getStatus() == FlowStatus.PASS) {
                throw businessException("流程已通过，不能修改");
            }
        }
        try {
            SupplierAnnouncement saved = announcementRepository.saveAndFlush(supplierAnnouncement);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }

    }


    /**
     * 公告详情
     *
     * @param id
     */
    @Override
    public SupplierAnnouncementDetailsDto getdetails(@NotNull Integer id) {
        SupplierAnnouncement ment = getSupplierAnnouncement(id);
        SupplierAnnouncementDetailsDto detailsDto = detailsMapper.toDto(ment);
        return detailsDto;

    }

    @Override
    public SupplierAnnouncementDto getdetail(@NotNull Integer id) {
        SupplierAnnouncement announcement = announcementRepository.findById(id).orElse(null);

        return announcementMapper.toDto(announcement);
    }


    /**
     * 条件查询
     *
     * @param queryCriteria
     */
    @Override
    public Page<SupplierAnnouncementDto> querylist(CgSupplierAnnouncementQueryCriteria queryCriteria, Pageable pageable) {
        Page<SupplierAnnouncement> all = announcementRepository.
                findAll(toSpecification(queryCriteria), pageable);

        Page<SupplierAnnouncementDto> Dtos = all.map(announcementMapper::toDto);
        return Dtos;
    }


    /**
     * 更新处理状态
     *
     * @param id
     * @param status
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(@NotNull Integer id, @NotNull String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);
        SupplierAnnouncement announcement = announcementRepository.findById(id).orElseThrow(() -> entityNotFound(SupplierAnnouncement.class, "id", id));
        FlowStatus currentStatusEnum = announcement.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id, status);
            }
            return;
        }
        announcement.setStatus(statusEnum);
        if (statusEnum == PASS) {
            // 不知道逻辑
            handlePass(announcement);
        }
        try {
            announcementRepository.saveAndFlush(announcement);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败", e);
            }
            throw businessException("更新状态失败", e);
        }


    }


    /**
     * 通过处理
     *
     * @param announcement
     */
    protected void handlePass(SupplierAnnouncement announcement) {
        if (log.isInfoEnabled()) {
            log.info("执行系统公告通过处理，公告ID：{}", announcement.getId());
        }
        announcement.setPassDate(LocalDateTime.now());

    }


    /**
     * 删除
     *
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleate(@NotNull Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<SupplierAnnouncement> toDelete = announcementRepository.findAll(
                ((Specification<SupplierAnnouncement>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));

        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 检查是否可删除
        toDelete.forEach(e -> {
            if (e.getReleasestatus().equals("1")) {
                throw businessException("公告已发布，不能删除");
            }
            if (e.getStatus() != FlowStatus.EDIT) {
                throw businessException("流程已启动，不能删除");
            }
        });
        // 删除
        try {
            announcementRepository.deleteAll(toDelete);
            announcementRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败", e);
            }
            throw businessException("删除失败");
        }

    }


    /**
     * 发布公告
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void release(@NotNull Integer id) {
        Objects.requireNonNull(id);
        SupplierAnnouncement byId;
        byId = getSupplierAnnouncement(id);
        if (byId.getStatus() == FlowStatus.PASS && byId.getReleasestatus().equals("0")) {
            byId.setReleasestatus("1");
            byId.setReleaseDate(LocalDateTime.now());
            announcementRepository.save(byId);
        } else {
            throw businessException("未通过审核，无法发布");
        }

    }


    /**
     * 停用公告
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stop(@NotNull Integer id, String name) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        SupplierAnnouncement byId;
        byId = getSupplierAnnouncement(id);
        if (byId.getStatus() == FlowStatus.PASS && byId.getReleasestatus().equals("1")) {
            byId.setReleasestatus("2");
            byId.setDisabledMan(name);
            byId.setStopDate(LocalDateTime.now());
            announcementRepository.save(byId);
        } else {
            throw businessException("未通过审核，无法停用");
        }


    }


    /**
     * 恢复公告
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recovery(@NotNull Integer id) {
        Objects.requireNonNull(id);
        SupplierAnnouncement byid;
        byid = getSupplierAnnouncement(id);
        if (byid.getStatus() == PASS && byid.getReleasestatus().equals("2")) {
            byid.setReleasestatus("1");
            announcementRepository.save(byid);
        } else {
            throw businessException("未通过审核,无法恢复");
        }

    }


    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected SupplierAnnouncement getSupplierAnnouncement(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierAnnouncement> all = announcementRepository.findAll(
                ((Specification<SupplierAnnouncement>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!announcementRepository.existsById(id)) {
                throw entityNotFound(SupplierAnnouncement.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}