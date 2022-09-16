package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.system.domain.Tenant;
import com.realfinance.sofa.system.facade.TenantMngFacade;
import com.realfinance.sofa.system.model.TenantDto;
import com.realfinance.sofa.system.model.TenantQueryCriteria;
import com.realfinance.sofa.system.model.TenantSaveDto;
import com.realfinance.sofa.system.repository.TenantRepository;
import com.realfinance.sofa.system.service.mapstruct.TenantMapper;
import com.realfinance.sofa.system.service.mapstruct.TenantSaveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.realfinance.sofa.system.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.system.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = TenantMngFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class TenantMngImpl implements TenantMngFacade {

    private static final Logger log = LoggerFactory.getLogger(TenantMngImpl.class);

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final TenantSaveMapper tenantSaveMapper;

    public TenantMngImpl(TenantRepository tenantRepository, TenantMapper tenantMapper, TenantSaveMapper tenantSaveMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
        this.tenantSaveMapper = tenantSaveMapper;
    }

    @Override
    public Page<TenantDto> list(TenantQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        return tenantRepository.findAll(toSpecification(queryCriteria),pageable).map(tenantMapper::toDto);
    }

    @Override
    public TenantDto getById(String id) {
        Objects.requireNonNull(id);
        return tenantRepository.findById(id).map(tenantMapper::toDto).orElse(null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String save(TenantSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        Optional<Tenant> tenant = tenantRepository.findById(saveDto.getId());
        Tenant entity;
        if (tenant.isEmpty()) { // 新增
            entity = tenantSaveMapper.toEntity(saveDto);
        } else { // 修改
            entity = tenantSaveMapper.updateEntity(tenant.get(),saveDto);
        }
        /*if(tenantRepository.existsById(saveDto.getId())){
            throw businessException("保存失败：法人编码重复");
        }*/
        try {
            Tenant saved = tenantRepository.saveAndFlush(entity);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<String> ids) {
        Objects.requireNonNull(ids);
        List<Tenant> toDelete = tenantRepository.findAllById(ids);
        try {
            tenantRepository.deleteAll(toDelete);
            tenantRepository.flush();
        } catch (JpaSystemException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof SQLIntegrityConstraintViolationException) {
                throw businessException("删除失败：请先删除Tenant下所有数据");
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败：" + e.getMessage());
        }
    }
}
