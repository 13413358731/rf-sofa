package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgProductSaveDto;
import com.realfinance.sofa.cg.core.model.ProductLibraryDto;
import com.realfinance.sofa.cg.core.model.ProductLibraryQueryCriteria;
import com.realfinance.sofa.cg.model.cg.CgProductLibraryImportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgProductLibraryFacade {


    /**
     * 保存
     *
     * @param saveDto
     * @return
     */
    Integer save(CgProductSaveDto saveDto);


    /**
     * 保存
     *
     * @param queryCriteria
     * @return
     */
    Page<ProductLibraryDto> list(Pageable pageable, ProductLibraryQueryCriteria queryCriteria);

    List<ProductLibraryDto> getList(ProductLibraryQueryCriteria queryCriteria);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    void delete(@NotNull Set<Integer> ids);


    Integer saveList(List<CgProductLibraryImportDto> list, Integer purchaseCatalogId);
}
