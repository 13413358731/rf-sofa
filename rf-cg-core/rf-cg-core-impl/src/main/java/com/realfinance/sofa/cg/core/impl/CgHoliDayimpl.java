package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.HoliDay;
import com.realfinance.sofa.cg.core.facade.CgHoliDayFacade;
import com.realfinance.sofa.cg.core.model.CgHoliDayQueryCriteria;
import com.realfinance.sofa.cg.core.model.HoliDayDto;
import com.realfinance.sofa.cg.core.model.HoliDaySaveDto;
import com.realfinance.sofa.cg.core.repository.HoliDayRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.HoliDayMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.HoliDaySaveMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgHoliDayFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgHoliDayimpl implements CgHoliDayFacade {
    private static final Logger log = LoggerFactory.getLogger(CgHoliDayimpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final HoliDaySaveMapper holiDaySaveMapper;
    private final HoliDayMapper holiDayMapper;
    private final HoliDayRepository holiDayRepository;

    public CgHoliDayimpl(JpaQueryHelper jpaQueryHelper,
                         HoliDaySaveMapper holiDaySaveMapper,
                         HoliDayMapper holiDayMapper,
                         HoliDayRepository holiDayRepository
    ) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.holiDayMapper = holiDayMapper;
        this.holiDaySaveMapper = holiDaySaveMapper;
        this.holiDayRepository = holiDayRepository;


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(HoliDaySaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        HoliDay holiDay;

        holiDay= holiDayRepository.findByHolidayDate(saveDto.getHolidayDate());


        holiDay.setHolidayName(saveDto.getHolidayName());
        holiDay.setHolidayFlag(saveDto.getHolidayFlag());
        holiDay.setRemark(saveDto.getRemark());
      return   holiDayRepository.save(holiDay).getId();
        /*if (saveDto.getId() == null) {//新增


            holiDay = holiDaySaveMapper.toEntity(saveDto);
            holiDay.setTenantId(DataScopeUtils.loadTenantId());

        } else {//修改

            HoliDay entity = getHoliDay(saveDto.getId());
            holiDay = holiDaySaveMapper.updateEntity(entity, saveDto);
        }
        try {
            HoliDay saved = holiDayRepository.saveAndFlush(holiDay);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }*/

    }

    @Override
    public Page<HoliDayDto> list(Pageable pageable, CgHoliDayQueryCriteria queryCriteria) {
        Page<HoliDay> page = holiDayRepository.findAll(new Specification<HoliDay>() {
            @Override
            public Predicate toPredicate(Root<HoliDay> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (queryCriteria == null) {
                    return null;
                }
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(queryCriteria.getHolidayDate())) {
                    predicates.add(criteriaBuilder.like(root.get("holidayDate"), queryCriteria.getHolidayDate() + "%"));
                }
                predicates.add(criteriaBuilder.or(criteriaBuilder.equal(root.get("holidayFlag"), 1), criteriaBuilder.equal(root.get("holidayFlag"), 0)));
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        }, pageable);

        return page.map(holiDayMapper::toDto);

    }


    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected HoliDay getHoliDay(Integer id) {
        Objects.requireNonNull(id);
        List<HoliDay> all = holiDayRepository.findAll(
                ((Specification<HoliDay>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!holiDayRepository.existsById(id)) {
                throw entityNotFound(HoliDay.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }


}
