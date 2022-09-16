package com.realfinance.sofa.cg.sup.impl;


import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;

import com.realfinance.sofa.cg.sup.domain.SupplierAnnouncementChannel;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAnnouncementChannelFacade;
import com.realfinance.sofa.cg.sup.model.ChannelQueryCriteriaRequest;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelSaveDto;
import com.realfinance.sofa.cg.sup.repository.SupplierAnnouncementChannelRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAnnouncementChannelMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAnnouncementChannelSaveMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.businessException;

@Service
@SofaService(interfaceType = CgSupplierAnnouncementChannelFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierAnnouncementChannellmpl implements CgSupplierAnnouncementChannelFacade {


    private static final Logger log = LoggerFactory.getLogger(CgSupplierAnnouncementChannellmpl.class);


    private final SupplierAnnouncementChannelRepository supplierAnnouncementChannelRepository;
    private final SupplierAnnouncementChannelMapper supplierAnnouncementChannelMapper;
    private final SupplierAnnouncementChannelSaveMapper supplierAnnouncementChannelSaveMapper;


    public CgSupplierAnnouncementChannellmpl(SupplierAnnouncementChannelRepository supplierAnnouncementChannelRepository,
                                             SupplierAnnouncementChannelMapper supplierAnnouncementChannelMapper,
                                             SupplierAnnouncementChannelSaveMapper supplierAnnouncementChannelSaveMapper

    ) {

        this.supplierAnnouncementChannelRepository = supplierAnnouncementChannelRepository;
        this.supplierAnnouncementChannelMapper = supplierAnnouncementChannelMapper;
        this.supplierAnnouncementChannelSaveMapper = supplierAnnouncementChannelSaveMapper;


    }

    /**
     * 频道列表
     *
     * @param pageable
     */
    @Override
    public Page<SupplierAnnouncementChannelDto> list(Pageable pageable, ChannelQueryCriteriaRequest request) {
        Objects.requireNonNull(pageable);
        return supplierAnnouncementChannelRepository.findAll(new Specification<SupplierAnnouncementChannel>() {
            @Override
            public Predicate toPredicate(Root<SupplierAnnouncementChannel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (request==null){
                    return  null;
                }
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotEmpty(request.getNameLike())) {
                    predicates.add(criteriaBuilder.like(root.get("channelName"),"%"+request.getNameLike()+"%"));
                }
              return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        },pageable).map(supplierAnnouncementChannelMapper::toDto);
    }


    /**
     * 登录：通用公告频道
     * 未登录：所有公告频道
     *
     * @param pageable
     * @param flag
     */
    @Override
    public Page<SupplierAnnouncementChannelDto> listquery(Pageable pageable, Boolean flag) {
        Objects.requireNonNull(pageable);
        Objects.requireNonNull(flag);
        Page<SupplierAnnouncementChannel> all = supplierAnnouncementChannelRepository.findAll(new Specification<SupplierAnnouncementChannel>() {
            @Override
            public Predicate toPredicate(Root<SupplierAnnouncementChannel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> path = root.get("channelName");
                Path<Object> type = root.get("channelType");
                if (flag) {
                    return criteriaBuilder.equal(type,"1");
                }
                return criteriaBuilder.equal(path, "通用公告");
            }
        }, pageable);
        return all.map(supplierAnnouncementChannelMapper::toDto);
    }


    /**
     * 查询
     *
     * @param id
     */
    @Override
    public SupplierAnnouncementChannelDto getOne(@NotNull Integer id) {
        Objects.requireNonNull(id);
        SupplierAnnouncementChannel channel = supplierAnnouncementChannelRepository.findById(id).get();
        return supplierAnnouncementChannelMapper.toDto(channel);

    }


    /**
     * 删除
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleate(@NotNull Set<Integer> ids) {
        Objects.requireNonNull(ids);
        for (Integer id : ids) {
            supplierAnnouncementChannelRepository.deleteById(id);
        }

    }


    /**
     * 保存更新
     *
     * @param ChannelSaveDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(SupplierAnnouncementChannelSaveDto ChannelSaveDto) {
        Objects.requireNonNull(ChannelSaveDto);
        if (ChannelSaveDto.getId() == null) {
            SupplierAnnouncementChannel entity = supplierAnnouncementChannelSaveMapper.toEntity(ChannelSaveDto);
            entity.setTenantId(DataScopeUtils.loadTenantId());
            try {
                return supplierAnnouncementChannelRepository.saveAndFlush(entity).getId();
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败,同一租户下，频道编码不能重复", e);
                }
                throw businessException("同一租户下，频道编码不能重复");
            }
        } else {
            SupplierAnnouncementChannel one = supplierAnnouncementChannelRepository.findById(ChannelSaveDto.getId()).orElseThrow();
            SupplierAnnouncementChannel channel = supplierAnnouncementChannelSaveMapper.updateEntity(one, ChannelSaveDto);
            try {
           return  supplierAnnouncementChannelRepository.saveAndFlush(channel).getId();
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败,同一租户下，频道编码不能重复", e);
                }
                throw businessException("同一租户下，频道编码不能重复");
            }

        }
    }

}
