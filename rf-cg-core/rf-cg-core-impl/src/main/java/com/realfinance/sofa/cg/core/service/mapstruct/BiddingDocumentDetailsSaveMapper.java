package com.realfinance.sofa.cg.core.service.mapstruct;

import com.realfinance.sofa.cg.core.domain.exec.bid.BiddingDocument;
import com.realfinance.sofa.cg.core.model.CgBiddingDocumentDetailsSaveDto;
import org.mapstruct.Mapper;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface BiddingDocumentDetailsSaveMapper extends ToEntityMapper<BiddingDocument, CgBiddingDocumentDetailsSaveDto> {

}
