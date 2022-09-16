package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.exec.bid.BiddingDocument;
import com.realfinance.sofa.cg.core.domain.exec.release.MultipleRelease;
import com.realfinance.sofa.cg.core.model.CgBiddingDocumentDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgMultipleReleaseDetailsSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface MultipleReleaseDetailsSaveMapper extends ToEntityMapper<MultipleRelease, CgMultipleReleaseDetailsSaveDto> {

}
