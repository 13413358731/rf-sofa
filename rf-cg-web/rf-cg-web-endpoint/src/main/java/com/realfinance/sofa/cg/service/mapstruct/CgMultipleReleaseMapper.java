package com.realfinance.sofa.cg.service.mapstruct;

import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgBusinessReplyVo;
import com.realfinance.sofa.cg.model.cg.CgMultipleReleaseVo;
import com.realfinance.sofa.cg.model.cg.CgProjectExecutionSupVo;
import com.realfinance.sofa.cg.sup.model.CgAttachmentDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessReplyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface CgMultipleReleaseMapper {

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    CgMultipleReleaseVo toVo(CgMultipleReleaseDto dto);

    @Mapping(target = "createdUser", source = "createdUserId")
    @Mapping(target = "modifiedUser", source = "modifiedUserId")
    @Mapping(target = "department", source = "departmentId")
    CgMultipleReleaseVo toVo(CgMultipleReleaseDetailsDto dto);

    CgMultipleReleaseDetailsSaveDto toSaveDto(CgMultipleReleaseVo vo);

    CgBusinessReplyVo toVo(CgBusinessReplyDto dto);

    @Mapping(target = "supplier", source = "supplierId")
    CgBusinessReplyVo toVo(CgBusinessReplyDetailsDto dto);

    @Mapping(target = "supplier", source = "supplierId")
    List<CgBusinessReplyVo> toVo(List<CgBusinessReplyDto> dto);

    List<CgAttachmentDto> tranAtt(List<CgProjectExecutionAttDto> dto);

    @Mapping(target = "supplier", source = "supplierId")
    CgProjectExecutionSupVo toVo(CgProjectExecutionSupDto dto);
}
