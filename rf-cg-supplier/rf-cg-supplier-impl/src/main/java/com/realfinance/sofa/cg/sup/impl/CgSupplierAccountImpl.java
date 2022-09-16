package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.SupplierAccount;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAccountFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountAuthInfoDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountCreateDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountQueryCriteria;
import com.realfinance.sofa.cg.sup.repository.SupplierAccountRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAccountMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupplierAccountFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierAccountImpl implements CgSupplierAccountFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierAccountImpl.class);

    private final PasswordEncoder passwordEncoder;
    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierAccountRepository supplierAccountRepository;
    private final SupplierAccountMapper supplierAccountMapper;


    public CgSupplierAccountImpl(PasswordEncoder passwordEncoder,
                                 JpaQueryHelper jpaQueryHelper,
                                 SupplierAccountRepository supplierAccountRepository,
                                 SupplierAccountMapper supplierAccountMapper) {
        this.passwordEncoder = passwordEncoder;
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierAccountRepository = supplierAccountRepository;
        this.supplierAccountMapper = supplierAccountMapper;
    }


    @Override
    public Page<CgSupplierAccountDto> list(CgSupplierAccountQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierAccount> result = supplierAccountRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return result.map(supplierAccountMapper::toDto);
    }

    @Override
    public Boolean existsByUsername(String username) {
        Objects.requireNonNull(username);
        if (username.length() < 6 || username.length() > 50) {
            return false;
        }
        String tenantId = DataScopeUtils.loadTenantId();
        return supplierAccountRepository.existsByTenantIdAndUsername(tenantId,username);
    }

    @Override
    public CgSupplierAccountDto getByUsername(String username) {
        Objects.requireNonNull(username);
        SupplierAccount supplierAccount = requireSupplierAccount(DataScopeUtils.loadTenantId(),username);
        return supplierAccountMapper.toDto(supplierAccount);
    }

    @Override
    public CgSupplierAccountAuthInfoDto getAuthInfoDto(String tenantId, String username) {
        Objects.requireNonNull(tenantId);
        Objects.requireNonNull(username);
        SupplierAccount supplierAccount = requireSupplierAccount2(tenantId,username);
        CgSupplierAccountAuthInfoDto result = new CgSupplierAccountAuthInfoDto();
        result.setUsername(supplierAccount.getUsername());
        result.setPassword(supplierAccount.getPassword());
        result.setEnabled(supplierAccount.getEnabled());
        result.setMobile(supplierAccount.getMobile());
        result.setPasswordModifiedTime(supplierAccount.getPasswordModifiedTime());
        if (supplierAccount.getSupplier() == null) {
            result.setAuthorities(Collections.emptyList());
        } else {
            result.setSupplierId(supplierAccount.getSupplier().getId());
            result.setAuthorities(Collections.singleton("SUPPLIER"));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createAccount(CgSupplierAccountCreateDto createDto) {
        Objects.requireNonNull(createDto);
        Objects.requireNonNull(createDto.getUsername());
        if (log.isInfoEnabled()) {
            log.info("创建供应商账户，{},", createDto);
        }
        if (!createDto.getUsername().matches("^[a-zA-Z0-9_]{6,20}$")) {
            throw businessException("用户名必须为6-20个字符，包括大小写字母、数字、下划线");
        }
        String tenantId = DataScopeUtils.loadTenantId();
        if (supplierAccountRepository.existsByTenantIdAndUsername(tenantId, createDto.getUsername())) {
            throw businessException("用户名已存在");
        }
        SupplierAccount supplierAccount = new SupplierAccount();
        supplierAccount.setUsername(createDto.getUsername());
        supplierAccount.setPassword(passwordEncoder.encode(createDto.getPassword()));
        supplierAccount.setEnabled(createDto.getEnabled());
        supplierAccount.setType(createDto.getType());
        supplierAccount.setMobile(createDto.getMobile());
        supplierAccount.setTenantId(tenantId);
        return save(supplierAccount).getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        SupplierAccount supplierAccount = requireSupplierAccount(DataScopeUtils.loadTenantId(),username);
        if (password.length() > 20 || password.length() < 6) {
            throw businessException("密码长度必须为6-20位");
        }
        if (passwordEncoder.matches(password, supplierAccount.getPassword())) {
            throw businessException("新密码与旧密码相同");
        }
        supplierAccount.setPassword(passwordEncoder.encode(password));
        supplierAccount.setPasswordModifiedTime(LocalDateTime.now());
        save(supplierAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String username, String password, String newPassword, String newPassword2) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        Objects.requireNonNull(newPassword);
        Objects.requireNonNull(newPassword2);
        if (!newPassword.equals(newPassword2)) {
            throw businessException("两次密码不相同");
        }
        SupplierAccount supplierAccount = requireSupplierAccount(DataScopeUtils.loadTenantId(),username);
        if (!passwordEncoder.matches(password, supplierAccount.getPassword())) {
            throw businessException("旧密码错误");
        }
        resetPassword(username,newPassword);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEnabled(String username, Boolean enabled) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(enabled);
        if (log.isInfoEnabled()) {
            log.info("修改供应商账户可用状态，username：{}，enabled：{}",username,enabled);
        }
        SupplierAccount supplierAccount = requireSupplierAccount(DataScopeUtils.loadTenantId(),username);
        supplierAccount.setEnabled(enabled);
        save(supplierAccount);
    }

    protected SupplierAccount save(SupplierAccount supplierAccount) {
        try {
            return supplierAccountRepository.saveAndFlush(supplierAccount);
        } catch (Exception e) {
            log.error("保存供应商账户失败",e);
            throw businessException("保存失败");
        }
    }

    /**
     * 查询供应商账号，如果没有则抛出异常
     * @param username 供应商账号用户名
     * @return
     */
    protected SupplierAccount requireSupplierAccount(String tenantId, String username) {
        return supplierAccountRepository.findByTenantIdAndUsername(tenantId,username)
                .orElseThrow(() -> entityNotFound(SupplierAccount.class, "username", username));
    }

    /**
     * 登录时查询供应商账号，如果没有则抛出异常
     * @param username 供应商账号用户名
     * @return
     */
    protected SupplierAccount requireSupplierAccount2(String tenantId, String username) {
        return supplierAccountRepository.findByTenantIdAndUsername(tenantId,username)
                .orElseThrow(() -> supplierNotFound(SupplierAccount.class, "username", username));
    }
}
