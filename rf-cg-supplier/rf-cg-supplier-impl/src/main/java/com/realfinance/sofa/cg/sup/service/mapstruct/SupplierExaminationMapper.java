package com.realfinance.sofa.cg.sup.service.mapstruct;

import com.realfinance.sofa.cg.sup.domain.*;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = RootConfig.class, uses = {CustomMapper.class})
public interface SupplierExaminationMapper extends ToDtoMapper<SupplierExamination, CgSupplierExaminationDto> {

    @Mapping(target = "createdUserId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "modifiedUserId", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "v", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blacklisted", constant = "false")
    Supplier toSupplier(SupplierExamination source);

    @Mapping(target = "createdUserId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "modifiedUserId", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "v", ignore = true)
    @Mapping(target = "id", ignore = true)
    // 门户提交的停用字段不能更新到供应商库
    @Mapping(target = "disableTermStart", expression = "java(from.getCategory() == com.realfinance.sofa.cg.sup.domain.SupplierExamination.Category.MODIFY_FROM_PORTAL ? supplier.getDisableTermStart() : from.getDisableTermStart())")
    @Mapping(target = "disableTermEnd", expression = "java(from.getCategory() == com.realfinance.sofa.cg.sup.domain.SupplierExamination.Category.MODIFY_FROM_PORTAL ? supplier.getDisableTermEnd() : from.getDisableTermEnd())")
    Supplier updateSupplier(@MappingTarget Supplier supplier, SupplierExamination from);

    @Mapping(target = "id", ignore = true)
    SupplierAttachment toSupplierAttachment(SupplierExaminationAttachment source);

    @Mapping(target = "id", ignore = true)
    SupplierContacts toSupplierContacts(SupplierExaminationContacts source);

    @Mapping(target = "id", ignore = true)
    QualityAuth toSupplierQualityAuth(SupplierExaminationQualityAuth source);

    @Mapping(target = "createdUserId", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "modifiedUserId", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    @Mapping(target = "v", ignore = true)
    @Mapping(target = "id", ignore = true)
    SupplierExamination fromSupplier(Supplier source);

    @Mapping(target = "id", ignore = true)
    SupplierExaminationAttachment toSupplierAttachment(SupplierAttachment source);

    @Mapping(target = "id", ignore = true)
    SupplierExaminationContacts toSupplierContacts(SupplierContacts source);

    @Mapping(target = "id", ignore = true)
    SupplierExaminationQualityAuth toSupplierQualityAuth(QualityAuth source);
}
