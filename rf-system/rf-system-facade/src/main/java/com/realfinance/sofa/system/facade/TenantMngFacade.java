package com.realfinance.sofa.system.facade;

import com.realfinance.sofa.system.model.TenantDto;
import com.realfinance.sofa.system.model.TenantQueryCriteria;
import com.realfinance.sofa.system.model.TenantSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 租户管理
 */
public interface TenantMngFacade {

    /**
     * 查询列表
     * @param queryCriteria
     * @param pageable
     * @return
     */
    Page<TenantDto> list(TenantQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    TenantDto getById(@NotNull String id);

    /**
     * 保存租户
     * @param tenantSaveDto
     * @return ID
     */
    String save(@NotNull TenantSaveDto tenantSaveDto);

    /**
     * 删除租户
     * @param ids
     */
    void delete(@NotNull Set<String> ids);
}
