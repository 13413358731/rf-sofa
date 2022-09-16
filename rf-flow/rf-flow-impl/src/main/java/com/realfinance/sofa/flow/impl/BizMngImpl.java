package com.realfinance.sofa.flow.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.flow.domain.Biz;
import com.realfinance.sofa.flow.facade.BizMngFacade;
import com.realfinance.sofa.flow.model.BizDto;
import com.realfinance.sofa.flow.model.BizSaveDto;
import com.realfinance.sofa.flow.repository.BizRepository;
import com.realfinance.sofa.flow.service.mapstruct.BizMapper;
import com.realfinance.sofa.flow.service.mapstruct.BizSaveMapper;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Set;

import static com.realfinance.sofa.flow.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.flow.util.ExceptionUtils.entityNotFound;

@Service
@SofaService(interfaceType = BizMngFacade.class, uniqueId = "${service.rf-flow.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional
public class BizMngImpl implements BizMngFacade {

    private static final Logger log = LoggerFactory.getLogger(BizMngImpl.class);

    private final BizRepository bizRepository;
    private final BizMapper bizMapper;
    private final BizSaveMapper bizSaveMapper;

    public BizMngImpl(BizRepository bizRepository,
                      BizMapper bizMapper,
                      BizSaveMapper bizSaveMapper) {
        this.bizRepository = bizRepository;
        this.bizMapper = bizMapper;
        this.bizSaveMapper = bizSaveMapper;
    }

    @Override
    public Page<BizDto> list(String filter, Pageable pageable) {
        if (StringUtils.isEmpty(filter)) {
            return bizRepository.findAll(pageable).map(bizMapper::toDto);
        } else {
            return bizRepository.findByFilter("%" + filter + "%",pageable)
                    .map(bizMapper::toDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(BizSaveDto saveDto) {
        if (saveDto.getId() == null) { // 新增
            Biz biz = bizSaveMapper.toEntity(saveDto);
            Biz saved = bizRepository.save(biz);
            return saved.getId();
        } else { // 修改
            Biz biz = bizRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(Biz.class, "id", saveDto.getId()));
            if (!Objects.equals(biz.getCode(),saveDto.getCode())) {
                throw businessException("code不能修改");
            }
            bizSaveMapper.updateEntity(biz,saveDto);
            Biz saved = bizRepository.save(biz);
            return saved.getId();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        List<Biz> toDelete = bizRepository.findAllById(ids);
        try {
            if (!toDelete.isEmpty()) {
                for (Biz biz : toDelete) {
                    bizRepository.delete(biz);
                    bizRepository.flush();
                }
            }
        } catch (JpaSystemException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof SQLIntegrityConstraintViolationException) {
                throw businessException("删除失败：已和模型绑定");
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败", e);
            }
            throw businessException("删除失败：" + e.getMessage());
        }
    }
}
