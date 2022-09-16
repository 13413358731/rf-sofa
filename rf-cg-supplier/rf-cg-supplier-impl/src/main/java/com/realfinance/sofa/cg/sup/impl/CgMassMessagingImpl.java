package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.FlowStatus;
import com.realfinance.sofa.cg.sup.domain.MassMessaging;
import com.realfinance.sofa.cg.sup.domain.SupplierAnnouncement;
import com.realfinance.sofa.cg.sup.domain.SupplierContacts;
import com.realfinance.sofa.cg.sup.facade.CgBusinessReplyFacade;
import com.realfinance.sofa.cg.sup.facade.CgMassMessagingFacade;
import com.realfinance.sofa.cg.sup.model.CgMassMessagingQueryCriteria;
import com.realfinance.sofa.cg.sup.model.MassMessagingDto;
import com.realfinance.sofa.cg.sup.model.MassMessagingSaveDto;
import com.realfinance.sofa.cg.sup.model.SupplierContactsDto;
import com.realfinance.sofa.cg.sup.repository.MassMessagingRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierContactsRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.MassMessagingMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.MassMessagingSaveMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierContactsMapper;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.dataAccessForbidden;


@Service
@SofaService(interfaceType = CgMassMessagingFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgMassMessagingImpl implements CgMassMessagingFacade {

    private static final Logger log = LoggerFactory.getLogger(CgMassMessagingImpl.class);

    private final MassMessagingRepository massMessagingRepository;
    private final MassMessagingSaveMapper massMessagingSaveMapper;
    private final MassMessagingMapper massMessagingMapper;
    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierContactsMapper supplierContactsMapper;
    private final SupplierContactsRepository supplierContactsRepository;

    public CgMassMessagingImpl(MassMessagingRepository massMessagingRepository,
                               MassMessagingSaveMapper massMessagingSaveMapper,
                               MassMessagingMapper massMessagingMapper,
                               JpaQueryHelper jpaQueryHelper,
                               SupplierContactsMapper supplierContactsMapper,
                               SupplierContactsRepository supplierContactsRepository
    ) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.massMessagingSaveMapper = massMessagingSaveMapper;
        this.massMessagingMapper = massMessagingMapper;
        this.massMessagingRepository = massMessagingRepository;
         this.supplierContactsMapper=supplierContactsMapper;
         this.supplierContactsRepository=supplierContactsRepository;

    }


    @Override
    public Page<MassMessagingDto> list(Pageable pageable, CgMassMessagingQueryCriteria queryCriteria) {
        Page<MassMessaging> page = massMessagingRepository.findAll(new Specification<MassMessaging>() {
            @Override
            public Predicate toPredicate(Root<MassMessaging> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (queryCriteria == null) {
                    return null;
                }
                List<Predicate>  predicates=new ArrayList<>();
                if (StringUtils.isNotBlank(queryCriteria.getMsgTitleLike())) {
                    predicates.add(criteriaBuilder.like(root.get("msgTitle"), "%" + queryCriteria.getMsgTitleLike() + "%"));
                }
                if (queryCriteria.getSendStatus()!=null){
                    predicates.add(criteriaBuilder.equal(root.get("sendStatus"),  queryCriteria.getSendStatus()));
                }

                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        }, pageable);

        return page.map(massMessagingMapper::toDto);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(MassMessagingSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        MassMessaging massMessaging;
        if (saveDto.getId() == null) {//新增
            massMessaging = massMessagingSaveMapper.toEntity(saveDto);
            massMessaging.setTenantId(DataScopeUtils.loadTenantId());

        } else {//修改
            MassMessaging entity = massMessagingRepository.findById(saveDto.getId()).get();
            massMessaging = massMessagingSaveMapper.updateEntity(entity, saveDto);

        }
        try {
            MassMessaging saved = massMessagingRepository.saveAndFlush(massMessaging);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NotNull Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<MassMessaging> toDelete = massMessagingRepository.findAll(
                ((Specification<MassMessaging>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));

        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 检查是否可删除
        toDelete.forEach(e -> {
            if (e.getSendStatus() == 1) {
                throw businessException("消息已发送，不能删除");
            }

        });
        // 删除
        try {
            massMessagingRepository.deleteAll(toDelete);
            massMessagingRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败", e);
            }
            throw businessException("删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void send(@NotNull Integer id) {
        Objects.requireNonNull(id);
        MassMessaging massMessaging = massMessagingRepository.findById(id).get();
        massMessaging.setSendStatus(1);
        massMessaging.setSendTime(LocalDateTime.now());
        massMessagingRepository.saveAndFlush(massMessaging);

    }

    @Override
    public Page<SupplierContactsDto> list(Pageable pageable) {
        Page<SupplierContacts> page = supplierContactsRepository.findAll(pageable);
        return page.map(supplierContactsMapper::toDto);
    }

    @Override
    public MassMessagingDto findById(@NotNull Integer id) {
        MassMessaging massMessaging = massMessagingRepository.findById(id).get();
       return massMessagingMapper.toDto(massMessaging);
    }


}