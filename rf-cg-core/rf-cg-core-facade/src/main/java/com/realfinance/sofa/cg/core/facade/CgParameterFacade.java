package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgParameterQueryCriteria;
import com.realfinance.sofa.cg.core.model.ParameterDto;
import com.realfinance.sofa.cg.core.model.ParameterSaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CgParameterFacade {

    /**
     *
     * @param pageable
     * @return
     */
    Page<ParameterDto> list(Pageable pageable, CgParameterQueryCriteria queryCriteria);


    /**
     *
     * @param saveDto
     * @return
     */

    Integer save(ParameterSaveDto saveDto);

    ParameterDto findByParameterCode(String ParameterCode);
}
