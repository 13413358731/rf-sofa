package com.realfinance.sofa.cg.util;

import com.realfinance.sofa.cg.core.model.CgRequirementOaDatumDto;
import com.realfinance.sofa.cg.model.ModifiedRecordVo;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgProjectVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementSupVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementItemVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class RequirementModifiedRecorder {

    public static List<ModifiedRecordVo> getRecords(List<CgRequirementVo> list) {
        List<ModifiedRecordVo> result = new ArrayList<>();
        if (list == null || list.size() < 2) {
            return result;
        }
        boolean showVersion = list.size() > 2;
        int size = list.size();
        CgRequirementVo pre = null;
        Iterator<CgRequirementVo> iterator = list.iterator();
        while (iterator.hasNext()) {
            CgRequirementVo next = iterator.next();
            if (pre != null) {
                String version = showVersion ? String.valueOf(-- size) : "/";
                List<ModifiedRecordVo> records = getRecords(next, pre, version);
                result.addAll(records);
            }
            pre = next;
        }
        return result;
    }


    public static List<ModifiedRecordVo> getRecords(CgRequirementVo before, CgRequirementVo after, String version) {
        LocalDateTime modifiedTime = after.getModifiedTime();
        List<ModifiedRecordVo> result = new ArrayList<>();
        String name1 = before.getName();
        String name2 = after.getName();
        if (isModified(name1, name2)) {
            result.add(newModifiedRecordVo(version,"采购申请名称",
                    name1,name2,modifiedTime));
        }
        BigDecimal reqTotalAmount1 = before.getReqTotalAmount();
        BigDecimal reqTotalAmount2 = after.getReqTotalAmount();
        if (isModified(reqTotalAmount1, reqTotalAmount2)) {
            result.add(newModifiedRecordVo(version,"申请采购金额",
                    reqTotalAmount1,reqTotalAmount2,modifiedTime));
        }
        BigDecimal marketPrice1 = before.getMarketPrice();
        BigDecimal marketPrice2 = after.getMarketPrice();
        if (isModified(marketPrice1, marketPrice2)) {
            result.add(newModifiedRecordVo(version,"市场参考价/市场控制总价（总）",
                    marketPrice1,marketPrice2,modifiedTime));
        }
        String reqUserPhone1 = before.getReqUserPhone();
        String reqUserPhone2 = after.getReqUserPhone();
        if (isModified(before.getReqUserPhone(), after.getReqUserPhone())) {
            result.add(newModifiedRecordVo(version,"申请人联系电话",
                    reqUserPhone1,reqUserPhone2,modifiedTime));
        }
        List<DepartmentVo> useDepartments1 = before.getUseDepartments();
        List<DepartmentVo> useDepartments2 = after.getUseDepartments();
        if (isModified(useDepartments1 == null ? Collections.emptySet() : useDepartments1.stream().map(DepartmentVo::getId).collect(Collectors.toSet()),
                useDepartments2 == null ? Collections.emptySet() : useDepartments2.stream().map(DepartmentVo::getId).collect(Collectors.toSet()))) {
            String beforeValue = useDepartments1 == null ? "" : useDepartments1.stream().map(DepartmentVo::getName).collect(Collectors.joining(","));
            String afterValue = useDepartments2 == null ? "" : useDepartments2.stream().map(DepartmentVo::getName).collect(Collectors.joining(","));
            result.add(newModifiedRecordVo(version,"使用部门",
                    beforeValue,afterValue,modifiedTime));
        }
        DepartmentVo purDepartment1 = before.getPurDepartment();
        DepartmentVo purDepartment2 = after.getPurDepartment();
        if (isModified(purDepartment1.getId(), purDepartment2.getId())) {
            String beforeValue = purDepartment1.getName();
            String afterValue = purDepartment2.getName();
            result.add(newModifiedRecordVo(version,"采购部门",
                    beforeValue,afterValue,modifiedTime));
        }
        if (isModified(before.getSupRequirements(), after.getSupRequirements())) {
            result.add(newModifiedRecordVo(version,"供应商资格要求",
                    before.getSupRequirements(),after.getSupRequirements(),modifiedTime));
        }
        if (isModified(before.getSupSpecialRequirements(), after.getSupSpecialRequirements())) {
            result.add(newModifiedRecordVo(version,"供应商特殊资格要求",
                    before.getSupSpecialRequirements(),after.getSupSpecialRequirements(),modifiedTime));
        }
        if (isModified(before.getNote(), after.getNote())) {
            result.add(newModifiedRecordVo(version,"备注",
                    before.getNote(),after.getNote(),modifiedTime));
        }
        if (isModified(before.getPurType(), after.getPurType())) {
            result.add(newModifiedRecordVo(version,"采购种类",
                    before.getPurType(),after.getPurType(),modifiedTime));
        }
        if (isModified(before.getPurMode(), after.getPurMode())) {
            result.add(newModifiedRecordVo(version,"拟用采购方式",
                    before.getPurMode(),after.getPurMode(),modifiedTime));
        }
        if (isModified(before.getProjFeatures(), after.getProjFeatures())) {
            result.add(newModifiedRecordVo(version,"项目特性",
                    before.getProjFeatures(),after.getProjFeatures(),modifiedTime));
        }
        if (isModified(before.getSugEvalMethod(), after.getSugEvalMethod())) {
            result.add(newModifiedRecordVo(version,"推荐评分方法",
                    before.getSugEvalMethod(),after.getSugEvalMethod(),modifiedTime));
        }
        if (isModified(before.getContractCategory(), after.getContractCategory())) {
            result.add(newModifiedRecordVo(version,"合同类别",
                    before.getContractCategory(),after.getContractCategory(),modifiedTime));
        }
        UserVo contractCreatedUser1 = before.getContractCreatedUser();
        UserVo contractCreatedUser2 = after.getContractCreatedUser();
        if (isModified(contractCreatedUser1.getId(), contractCreatedUser2.getId())) {
            result.add(newModifiedRecordVo(version,"合同制单人",
                    contractCreatedUser1.getRealname(),contractCreatedUser2.getRealname(),modifiedTime));
        }
        if (isModified(before.getContractValidity(), after.getContractValidity())) {
            result.add(newModifiedRecordVo(version,"合约有效期",
                    before.getContractValidity(),after.getContractValidity(),modifiedTime));
        }
        if (isModified(before.getPurCategory(), after.getPurCategory())) {
            result.add(newModifiedRecordVo(version,"采购类别",
                    before.getPurCategory(),after.getPurCategory(),modifiedTime));
        }
        if (isModified(before.getPlanStatus(), after.getPlanStatus())) {
            result.add(newModifiedRecordVo(version,"是否计划内",
                    before.getPlanStatus(),after.getPlanStatus(),modifiedTime));
        }
        if (isModified(before.getNumber(), after.getNumber())) {
            result.add(newModifiedRecordVo(version,"数量",
                    before.getNumber(),after.getNumber(),modifiedTime));
        }
        if (isModified(before.getNumberOfWinSup(), after.getNumberOfWinSup())) {
            result.add(newModifiedRecordVo(version,"中标供应商数",
                    before.getNumberOfWinSup(),after.getNumberOfWinSup(),modifiedTime));
        }
        if (isModified(before.getPurContent(), after.getPurContent())) {
            result.add(newModifiedRecordVo(version,"采购内容",
                    before.getPurContent(),after.getPurContent(),modifiedTime));
        }
        if (isModified(before.getSupplyLocation(), after.getSupplyLocation())) {
            result.add(newModifiedRecordVo(version,"供货（服务）地点",
                    before.getSupplyLocation(),after.getSupplyLocation(),modifiedTime));
        }
        if (isModified(before.getDeliveryTime(), after.getDeliveryTime())) {
            result.add(newModifiedRecordVo(version,"交货（完成）时间",
                    before.getDeliveryTime(),after.getDeliveryTime(),modifiedTime));
        }
        if (isModified(before.getContractTerm(), after.getContractTerm())) {
            result.add(newModifiedRecordVo(version,"合同期限",
                    before.getContractTerm(),after.getContractTerm(),modifiedTime));
        }
        if (isModified(before.getQualityRequirements(), after.getQualityRequirements())) {
            result.add(newModifiedRecordVo(version,"质量要求",
                    before.getQualityRequirements(),after.getQualityRequirements(),modifiedTime));
        }
        if (isModified(before.getServiceRequirements(), after.getServiceRequirements())) {
            result.add(newModifiedRecordVo(version,"服务（含实施团队和人员）要求",
                    before.getServiceRequirements(),after.getServiceRequirements(),modifiedTime));
        }
        if (isModified(before.getTransportRequirements(), after.getTransportRequirements())) {
            result.add(newModifiedRecordVo(version,"包装和运输要求",
                    before.getTransportRequirements(),after.getTransportRequirements(),modifiedTime));
        }
        if (isModified(before.getDeliveryRequirements(), after.getDeliveryRequirements())) {
            result.add(newModifiedRecordVo(version,"交货（或提交成果）要求",
                    before.getDeliveryRequirements(),after.getDeliveryRequirements(),modifiedTime));
        }
        if (isModified(before.getAfterSalesService(), after.getAfterSalesService())) {
            result.add(newModifiedRecordVo(version,"售后服务",
                    before.getAfterSalesService(),after.getAfterSalesService(),modifiedTime));
        }
        if (isModified(before.getPaymentMethod(), after.getPaymentMethod())) {
            result.add(newModifiedRecordVo(version,"付款方式",
                    before.getPaymentMethod(),after.getPaymentMethod(),modifiedTime));
        }
        if (isModified(before.getBreachClause(), after.getBreachClause())) {
            result.add(newModifiedRecordVo(version,"违约条款",
                    before.getBreachClause(),after.getBreachClause(),modifiedTime));
        }
        if (isModified(before.getHasBond(), after.getHasBond())) {
            result.add(newModifiedRecordVo(version,"是否含保证金",
                    before.getHasBond(),after.getHasBond(),modifiedTime));
        }
        if (isModified(before.getPerformanceBond(), after.getPerformanceBond())) {
            result.add(newModifiedRecordVo(version,"履约保证金",
                    before.getPerformanceBond(),after.getPerformanceBond(),modifiedTime));
        }
        if (isModified(before.getPerformanceYears(), after.getPerformanceYears())) {
            result.add(newModifiedRecordVo(version,"履约保证金年限",
                    before.getPerformanceYears(),after.getPerformanceYears(),modifiedTime));
        }
        if (isModified(before.getWarrantyBond(), after.getWarrantyBond())) {
            result.add(newModifiedRecordVo(version,"质保金",
                    before.getWarrantyBond(),after.getWarrantyBond(),modifiedTime));
        }
        if (isModified(before.getWarrantyYears(), after.getWarrantyYears())) {
            result.add(newModifiedRecordVo(version,"质保金年限",
                    before.getWarrantyYears(),after.getWarrantyYears(),modifiedTime));
        }
        if (isModified(before.getSupplement(), after.getSupplement())) {
            result.add(newModifiedRecordVo(version,"其他补充",
                    before.getSupplement(),after.getSupplement(),modifiedTime));
        }
        // oaData
        List<CgRequirementOaDatumDto> beforeOaData = before.getRequirementOaData() == null ? Collections.emptyList() : before.getRequirementOaData();
        List<CgRequirementOaDatumDto> afterOaData = after.getRequirementOaData() == null ? Collections.emptyList() : after.getRequirementOaData();
        Set<String> beforeApprovalNoSet = beforeOaData.stream().map(CgRequirementOaDatumDto::getApprovalNo).collect(Collectors.toSet());
        Set<String> afterApprovalNoSet = afterOaData.stream().map(CgRequirementOaDatumDto::getApprovalNo).collect(Collectors.toSet());
        for (String beforeApprovalNo : beforeApprovalNoSet) {
            if (!afterApprovalNoSet.contains(beforeApprovalNo)) {
                result.add(newModifiedRecordVo(version,"【删除】立项审批号", beforeApprovalNo,"",modifiedTime));
            }
        }
        for (String afterApprovalNo : afterApprovalNoSet) {
            if (!beforeApprovalNoSet.contains(afterApprovalNo)) {
                result.add(newModifiedRecordVo(version,"【新增】立项审批号", "",afterApprovalNo,modifiedTime));
            }
        }
        // item
        List<CgRequirementItemVo> beforeItems = before.getRequirementItems() == null ? Collections.emptyList() : before.getRequirementItems();
        List<CgRequirementItemVo> afterItems = after.getRequirementItems() == null ? Collections.emptyList() : after.getRequirementItems();
        loop:
        for (CgRequirementItemVo beforeItem : beforeItems) {
            for (CgRequirementItemVo afterItem : afterItems) {
                if (Objects.equals(beforeItem.getName(), afterItem.getName())) {
                    if (isModified(beforeItem.getMarketPrice(), afterItem.getMarketPrice())) {
                        result.add(newModifiedRecordVo(version, String.format("采购清单(%s).市场参考价", beforeItem.getName()), beforeItem.getMarketPrice(),afterItem.getMarketPrice(),modifiedTime));
                    }
                    if (isModified(beforeItem.getNeedSample(), afterItem.getNeedSample())) {
                        result.add(newModifiedRecordVo(version, String.format("采购清单(%s).是否需提供样板", beforeItem.getName()), beforeItem.getNeedSample(),afterItem.getNeedSample(),modifiedTime));
                    }
                    if (isModified(beforeItem.getNumber(), afterItem.getNumber())) {
                        result.add(newModifiedRecordVo(version, String.format("采购清单(%s).数量", beforeItem.getName()), beforeItem.getNumber(),afterItem.getNumber(),modifiedTime));
                    }
                    String beforePurchaseCatalog = beforeItem.getPurchaseCatalog() == null ? "" : beforeItem.getPurchaseCatalog().getName();
                    String afterPurchaseCatalog = afterItem.getPurchaseCatalog() == null ? "" : afterItem.getPurchaseCatalog().getName();
                    if (isModified(beforePurchaseCatalog, afterPurchaseCatalog)) {
                        result.add(newModifiedRecordVo(version, String.format("采购清单(%s).采购目录", beforeItem.getName()), beforePurchaseCatalog,afterPurchaseCatalog,modifiedTime));
                    }
                    continue loop;
                }
            }
            result.add(newModifiedRecordVo(version,"【删除】采购清单",before.getName(),"",modifiedTime));
        }
        loop:
        for (CgRequirementItemVo afterItem : afterItems) {
            for (CgRequirementItemVo beforeItem : beforeItems) {
                if (Objects.equals(beforeItem.getName(), afterItem.getName())) {
                    continue loop;
                }
            }
            result.add(newModifiedRecordVo(version,"【新增】采购清单","",afterItem.getName(),modifiedTime));
        }

        // sup
        List<CgRequirementSupVo> beforeSups = before.getRequirementSups() == null ? Collections.emptyList() : before.getRequirementSups();
        List<CgRequirementSupVo> afterSups = after.getRequirementSups() == null ? Collections.emptyList() : after.getRequirementSups();
        loop:
        for (CgRequirementSupVo beforeSup : beforeSups) {
            for (CgRequirementSupVo afterSup : afterSups) {
                if (Objects.equals(afterSup.getSupplier().getId(), beforeSup.getSupplier().getId())) {
                    if (isModified(beforeSup.getContactName(), afterSup.getContactName())) {
                        result.add(newModifiedRecordVo(version, String.format("推荐供应商(%s).联系人名称", beforeSup.getSupplier().getName()), beforeSup.getContactName(),afterSup.getContactName(),modifiedTime));
                    }
                    if (isModified(beforeSup.getContactMobile(), afterSup.getContactMobile())) {
                        result.add(newModifiedRecordVo(version, String.format("推荐供应商(%s).联系人手机", beforeSup.getSupplier().getName()), beforeSup.getContactMobile(),afterSup.getContactMobile(),modifiedTime));
                    }
                    if (isModified(beforeSup.getContactEmail(), afterSup.getContactEmail())) {
                        result.add(newModifiedRecordVo(version, String.format("推荐供应商(%s).联系人邮箱", beforeSup.getSupplier().getName()), beforeSup.getContactEmail(),afterSup.getContactEmail(),modifiedTime));
                    }
                    continue loop;
                }
            }
            result.add(newModifiedRecordVo(version, "【删除】推荐供应商",beforeSup.getSupplier().getName(),"",modifiedTime));
        }
        loop:
        for (CgRequirementSupVo afterSup : afterSups) {
            for (CgRequirementSupVo beforeSup : beforeSups) {
                if (Objects.equals(afterSup.getSupplier().getId(), beforeSup.getSupplier().getId())) {
                    continue loop;
                }
            }
            result.add(newModifiedRecordVo(version, "【新增】推荐供应商","",afterSup.getSupplier().getName(),modifiedTime));
        }

        // att
        List<CgAttVo> beforeAtts = before.getRequirementAtts() == null ? Collections.emptyList() : before.getRequirementAtts();
        List<CgAttVo> afterAtts = after.getRequirementAtts() == null ? Collections.emptyList() : after.getRequirementAtts();
        Set<String> beforeAttNameSet = beforeAtts.stream().map(CgAttVo::getName).collect(Collectors.toSet());
        Set<String> afterAttNameSet = afterAtts.stream().map(CgAttVo::getName).collect(Collectors.toSet());
        for (String beforeAttName : beforeAttNameSet) {
            if (!afterAttNameSet.contains(beforeAttName)) {
                result.add(newModifiedRecordVo(version,"【删除】附件", beforeAttName,"",modifiedTime));
            }
        }
        for (String afterAttName : afterAttNameSet) {
            if (!beforeAttNameSet.contains(afterAttName)) {
                result.add(newModifiedRecordVo(version,"【新增】附件", "",afterAttName,modifiedTime));
            }
        }
        return result;
    }

    protected static ModifiedRecordVo newModifiedRecordVo(String version, String columnName, Object beforeValue,
                                                          Object afterValue, LocalDateTime modifiedTime) {
        ModifiedRecordVo modifiedRecordVo = new ModifiedRecordVo();
        modifiedRecordVo.setVersion(version);
        modifiedRecordVo.setColumnName(columnName);
        modifiedRecordVo.setBeforeValue(beforeValue);
        modifiedRecordVo.setAfterValue(afterValue);
        modifiedRecordVo.setModifiedTime(modifiedTime);
        return modifiedRecordVo;
    }

    protected static boolean isModified(Object o1, Object o2) {
        return !Objects.equals(o1,o2);
    }
}
