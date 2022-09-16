package com.realfinance.sofa.cg.sup.impl;


import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.*;
import com.realfinance.sofa.cg.sup.facade.CgSupplierSolicitationFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.sup.repository.*;
import com.realfinance.sofa.cg.sup.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils;
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

import javax.persistence.criteria.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

import static com.realfinance.sofa.cg.sup.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgSupplierSolicitationFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierSolicitationImpl implements CgSupplierSolicitationFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierSolicitationImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierSolicitationRepository solicitationRepository;
    private final SupplierSolicitationDetailsMapper detailsMapper;
    private final SupplierSolicitationSaveMapper saveMapper;
    private final SupplierSolicitationMapper mapper;
    private final SolicitationEnrollRepository enrollRepository;
    private final SolicitationEnrollSaveMapper enrollSaveMapper;
    private final SolicitationEnrollMapper enrollMapper;
    private final SupplierRepository supplierRepository;
    private final SupplierAccountRepository accountRepository;
    private final SupplierAccountMapper accountMapper;
    private final SupplierSolicitationAttachmentRepository attachmentRepository;
    private final SupplierContactsMapper supplierContactsMapper;
    private final SupplierContactsRepository supplierContactsRepository;

    public CgSupplierSolicitationImpl(JpaQueryHelper jpaQueryHelper,
                                      SupplierSolicitationRepository solicitationRepository,
                                      SupplierSolicitationDetailsMapper detailsMapper,
                                      SupplierSolicitationSaveMapper saveMapper,
                                      SupplierSolicitationMapper mapper,
                                      SolicitationEnrollRepository enrollRepository,
                                      SolicitationEnrollSaveMapper enrollSaveMapper,
                                      SupplierRepository supplierRepository,
                                      SupplierAccountRepository accountRepository,
                                      SupplierAccountMapper accountMapper,
                                      SolicitationEnrollMapper enrollMapper,
                                      SupplierSolicitationAttachmentRepository attachmentRepository,
                                      SupplierContactsMapper supplierContactsMapper,
                                      SupplierContactsRepository supplierContactsRepository
    ) {

        this.jpaQueryHelper = jpaQueryHelper;
        this.solicitationRepository = solicitationRepository;
        this.detailsMapper = detailsMapper;
        this.saveMapper = saveMapper;
        this.mapper = mapper;
        this.enrollRepository = enrollRepository;
        this.enrollSaveMapper = enrollSaveMapper;
        this.supplierRepository = supplierRepository;
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.enrollMapper = enrollMapper;
        this.attachmentRepository = attachmentRepository;
        this.supplierContactsMapper=supplierContactsMapper;
        this.supplierContactsRepository=supplierContactsRepository;
    }


    /**
     * 保存报名记录
     *
     * @param enrollSave
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEnroll(EnrollSaveDto enrollSave) {
        SolicitationEnroll enroll = enrollSaveMapper.toEntity(enrollSave);
        enrollRepository.save(enroll);
    }

    /**
     * 保存报名信息
     *
     * @param information
     * @param solicitationId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveinformation(registrationInformation information, Integer solicitationId, Integer supplierId) {
        Objects.requireNonNull(information);
        SolicitationEnroll enroll = getEnlist(solicitationId, supplierId);
        enroll.setContactName(information.getContactName());
        enroll.setMobile(information.getMobile());
        enroll.setEmail(information.getEmail());
        enrollRepository.save(enroll);
    }


    /**
     * 获取已报名的供应商
     *
     * @param SolicitationId
     */
    @Override
    public Page<SolicitationEnrollDto> findById(Integer SolicitationId, Pageable pageable) {
        Page<SolicitationEnroll> list = enrollRepository.findAll(new Specification<SolicitationEnroll>() {
            @Override
            public Predicate toPredicate(Root<SolicitationEnroll> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> path = root.get("solicitationId");
                return criteriaBuilder.equal(path, SolicitationId);
            }
        }, pageable);
        return list.map(enrollMapper::toDto);
    }

    /**
     * 发布意向征集至门户
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void release(@NotNull Integer id,@NotNull String releaseStatus) {
        SupplierSolicitation solicitation = solicitationRepository.findById(id).get();
        solicitation.setReleaseStatus(releaseStatus);
        solicitationRepository.save(solicitation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void stops(List<Integer> ids) {
        List<SupplierSolicitation> solicitations = solicitationRepository.findAllById(ids);
        for (SupplierSolicitation solicitation : solicitations) {
            if (solicitation.getReleaseStatus().equals("0")){
                throw new RuntimeException("勾选数据存在未发布数据,不能批量停用");
            }
            solicitation.setReleaseStatus("2");
        }
        solicitationRepository.saveAll(solicitations);
    }

    /**
     * 检查是否已报名
     *
     * @param solicitationId
     * @param supplierId
     */
    @Override
    public boolean isEnlist(Integer solicitationId, Integer supplierId) {
        Objects.requireNonNull(supplierId);
        Objects.requireNonNull(solicitationId);
        List<SolicitationEnroll> list = enrollRepository.findAll(new Specification<SolicitationEnroll>() {
            @Override
            public Predicate toPredicate(Root<SolicitationEnroll> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("solicitationId"), solicitationId));
                predicates.add(criteriaBuilder.equal(root.get("supplierId"), supplierId));
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        });
        return list.isEmpty();


    }

    /**
     * 返回某一条报名记录
     */
    protected SolicitationEnroll getEnlist(Integer solicitationId, Integer supplierId) {
        Objects.requireNonNull(supplierId);
        Objects.requireNonNull(solicitationId);
        List<SolicitationEnroll> list = enrollRepository.findAll(new Specification<SolicitationEnroll>() {
            @Override
            public Predicate toPredicate(Root<SolicitationEnroll> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("solicitationId"), solicitationId));
                predicates.add(criteriaBuilder.equal(root.get("supplierId"), supplierId));
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        });
        return list.get(0);
    }


    /**
     * 列表
     *
     * @param pageable
     */
    @Override
    public Page<CgSupplierSolicitationDto> list(Pageable pageable,CgSupplierSolicitationQueryCriteria queryCriteria) {
        Objects.requireNonNull(pageable);
        Page<SupplierSolicitation> page = solicitationRepository.findAll((Specification<SupplierSolicitation>) (root, query, criteriaBuilder) -> {
            if (queryCriteria==null){
                return null;
            }

            if(StringUtils.isNotBlank(queryCriteria.getTitleLike())){
             return    criteriaBuilder.like(root.get("title"),"%"+queryCriteria.getTitleLike()+"%");
            }
         return null;
        }, pageable);

        Page<CgSupplierSolicitationDto> dtos = page.map(mapper::toDto);
        return dtos;
    }

    @Override
    public Page<CgSupplierSolicitationDto> listrefer(Pageable pageable) {
       return solicitationRepository.findAll(new Specification<SupplierSolicitation>() {
            @Override
            public Predicate toPredicate(Root<SupplierSolicitation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

           return   query.orderBy(criteriaBuilder.desc(root.get("modifiedTime"))).where(criteriaBuilder.equal( root.get("releaseStatus"),"1")).getRestriction();

            }
        },pageable).map(mapper::toDto);
    }


    /**
     * 保存更新
     *
     * @param saveDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgSupplierSolicitationSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        SupplierSolicitation solicitation;
        if (saveDto.getId() == null) {//新增
            solicitation = saveMapper.toEntity(saveDto);
            solicitation.setStatus(FlowStatus.EDIT);
            solicitation.setReleaseStatus("0");
            solicitation.setTenantId(DataScopeUtils.loadTenantId());
            solicitation.setDocumentNumber(UUID.randomUUID().toString().substring(1, 10));

        } else {//修改
            SupplierSolicitation entity = getSupplierSolicitation(saveDto.getId());
            solicitation = saveMapper.updateEntity(entity, saveDto);
            if (entity.getStatus() == FlowStatus.PASS) {
                throw businessException("流程已通过，不能修改");
            }

        }
        try {
            SupplierSolicitation saved = solicitationRepository.saveAndFlush(solicitation);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }

    }

    /**
     * 删除
     *
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(@NotNull Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }

        // 启用数据权限查询
        List<SupplierSolicitation> toDelete = solicitationRepository.findAll(
                ((Specification<SupplierSolicitation>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));

        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 检查是否可删除
        toDelete.forEach(e -> {

            if (e.getStatus() != FlowStatus.EDIT) {
                throw businessException("流程已启动，不能删除");
            }
        });

        // 删除
        try {
            solicitationRepository.deleteAll(toDelete);
            solicitationRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败", e);
            }
            throw businessException("删除失败");
        }

    }

    /**
     * 供应商意向征集详情
     *
     * @param id
     */
    @Override
    public CgSupplierSolicitationDetailsDto getdetail(Integer id) {
        Objects.requireNonNull(id);
        SupplierSolicitation solicitation = solicitationRepository.findById(id).get();
        return detailsMapper.toDto(solicitation);
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
        SupplierSolicitation solicitation = solicitationRepository.findById(id).orElseThrow(() -> entityNotFound(SupplierAnnouncement.class, "id", id));
        FlowStatus currentStatusEnum = solicitation.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id, status);
            }
            return;
        }
        solicitation.setStatus(statusEnum);
        if (statusEnum == PASS) {
            // 不知道逻辑
            handlePass(solicitation);
        }
        try {
            solicitationRepository.saveAndFlush(solicitation);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败", e);
            }
            throw businessException("更新状态失败", e);
        }

    }

    @Override
    public Page<SupplierContactsDto> listrefer(Pageable pageable,@NotNull Integer id) {
        Page<SupplierContacts> page = supplierContactsRepository.findAll(new Specification<SupplierContacts>() {
            @Override
            public Predicate toPredicate(Root<SupplierContacts> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return  criteriaBuilder.equal(root.get("supplier").get("id"),id);
            }
        },pageable);
        return page.map(supplierContactsMapper::toDto);
    }


    /**
     * 通过处理
     *
     * @param solicitation
     */
    protected void handlePass(SupplierSolicitation solicitation) {
        if (log.isInfoEnabled()) {
            log.info("执行供应商意向征集通过处理，ID：{}", solicitation.getId());
        }
        solicitation.setPassDate(LocalDateTime.now());


    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected SupplierSolicitation getSupplierSolicitation(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierSolicitation> all = solicitationRepository.findAll(
                ((Specification<SupplierSolicitation>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!solicitationRepository.existsById(id)) {
                throw entityNotFound(SupplierSolicitation.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }


}
