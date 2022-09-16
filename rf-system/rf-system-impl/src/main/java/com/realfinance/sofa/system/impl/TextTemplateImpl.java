package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.domain.TextTemplate;
import com.realfinance.sofa.system.facade.TextTemplateFacade;
import com.realfinance.sofa.system.model.TextTemplateDetailsDto;
import com.realfinance.sofa.system.model.TextTemplateDto;
import com.realfinance.sofa.system.model.TextTemplateQueryCriteria;
import com.realfinance.sofa.system.model.TextTemplateSaveDto;
import com.realfinance.sofa.system.repository.TextTemplateRepository;
import com.realfinance.sofa.system.service.mapstruct.TextTemplateDetailsMapper;
import com.realfinance.sofa.system.service.mapstruct.TextTemplateMapper;
import com.realfinance.sofa.system.service.mapstruct.TextTemplateSaveMapper;
import com.realfinance.sofa.system.util.QueryCriteriaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.realfinance.sofa.common.datascope.DataScopeUtils.checkTenantCanAccess;
import static com.realfinance.sofa.system.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.system.util.ExceptionUtils.entityNotFound;

@Service
@SofaService(interfaceType = TextTemplateFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class TextTemplateImpl implements TextTemplateFacade {

    private static final Logger log = LoggerFactory.getLogger(TextTemplateImpl.class);

    private final TextTemplateRepository textTemplateRepository;
    private final TextTemplateMapper textTemplateMapper;
    private final TextTemplateDetailsMapper textTemplateDetailsMapper;
    private final TextTemplateSaveMapper textTemplateSaveMapper;

    public TextTemplateImpl(TextTemplateRepository textTemplateRepository,
                            TextTemplateMapper textTemplateMapper,
                            TextTemplateDetailsMapper textTemplateDetailsMapper,
                            TextTemplateSaveMapper textTemplateSaveMapper) {
        this.textTemplateRepository = textTemplateRepository;
        this.textTemplateMapper = textTemplateMapper;
        this.textTemplateDetailsMapper = textTemplateDetailsMapper;
        this.textTemplateSaveMapper = textTemplateSaveMapper;
    }

    @Override
    public Page<TextTemplateDto> list(TextTemplateQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            queryCriteria.setTenantId(DataScopeUtils.loadTenantId());
        }
        Page<TextTemplate> result = textTemplateRepository
                .findAll(QueryCriteriaUtils.toSpecification(queryCriteria), pageable);
        return result.map(textTemplateMapper::toDto);
    }

    @Override
    public TextTemplateDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        Optional<TextTemplate> entity = textTemplateRepository.findById(id);
        entity.ifPresent(e -> checkTenantCanAccess(e.getTenantId()));
        return entity.map(textTemplateDetailsMapper::toDto)
                .orElseThrow(() -> entityNotFound(TextTemplate.class,"id",id));
    }

    @Override
    public String getText(Integer id) {
        Objects.requireNonNull(id);
        Optional<TextTemplate> textTemplate = textTemplateRepository
                .findById(id);
        return textTemplate.map(TextTemplate::getText)
                .orElseThrow(() -> entityNotFound(TextTemplate.class,"id",id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(TextTemplateSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        TextTemplate textTemplate;
        if (saveDto.getId() == null) { // 新增
            textTemplate = textTemplateSaveMapper.toEntity(saveDto);
            textTemplate.setTenantId(DataScopeUtils.loadTenantId());
            textTemplate.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
        } else { // 修改
            TextTemplate entity = textTemplateRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(TextTemplate.class,"id",saveDto.getId()));
            textTemplate = textTemplateSaveMapper.updateEntity(entity,saveDto);
        }
        try {
            TextTemplate saved = textTemplateRepository.saveAndFlush(textTemplate);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        List<TextTemplate> toDelete = textTemplateRepository.findAllById(ids).stream()
                .peek(e -> checkTenantCanAccess(e.getTenantId()))
                .collect(Collectors.toList());
        // 删除
        try {
            textTemplateRepository.deleteAll(toDelete);
            textTemplateRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }
}
