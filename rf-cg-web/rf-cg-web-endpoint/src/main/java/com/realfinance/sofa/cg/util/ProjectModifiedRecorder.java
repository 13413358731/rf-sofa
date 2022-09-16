package com.realfinance.sofa.cg.util;

import com.realfinance.sofa.cg.core.model.CgProjectOaDatumDto;
import com.realfinance.sofa.cg.core.model.CgRequirementOaDatumDto;
import com.realfinance.sofa.cg.model.ModifiedRecordVo;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgProjectItemVo;
import com.realfinance.sofa.cg.model.cg.CgProjectSupVo;
import com.realfinance.sofa.cg.model.cg.CgProjectVo;
import com.realfinance.sofa.cg.model.cg.CgPurchasePlanVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementItemVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementSupVo;
import com.realfinance.sofa.cg.model.cg.CgRequirementVo;
import com.realfinance.sofa.cg.model.cg.CgSupplierLabelVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectModifiedRecorder {

    public static List<ModifiedRecordVo> getRecords(List<CgProjectVo> list) {
        List<ModifiedRecordVo> result = new ArrayList<>();
        if (list == null || list.size() < 2) {
            return result;
        }
        boolean showVersion = list.size() > 2;
        int size = list.size();
        CgProjectVo pre = null;
        Iterator<CgProjectVo> iterator = list.iterator();
        while (iterator.hasNext()) {
            CgProjectVo next = iterator.next();
            if (pre != null) {
                String version = showVersion ? String.valueOf(-- size) : "/";
                List<ModifiedRecordVo> records = getRecords(next, pre, version);
                result.addAll(records);
            }
            pre = next;
        }
        return result;
    }
    

    public static List<ModifiedRecordVo> getRecords(CgProjectVo before, CgProjectVo after, String version) {
        LocalDateTime modifiedTime = after.getModifiedTime();
        List<ModifiedRecordVo> result = new ArrayList<>();
        String projectNo1=before.getProjectNo();
        String projectNo2=after.getProjectNo();
        if(isModified(projectNo1,projectNo2)){
            result.add(newModifiedRecordVo(version,"项目编号",
                    projectNo1,projectNo2,modifiedTime));
        }
        String name1 = before.getName();
        String name2 = after.getName();
        if (isModified(name1, name2)) {
            result.add(newModifiedRecordVo(version,"项目名称",
                    name1,name2,modifiedTime));
        }
        BigDecimal reqTotalAmount1 = before.getReqTotalAmount();
        BigDecimal reqTotalAmount2 = after.getReqTotalAmount();
        if (isModified(reqTotalAmount1, reqTotalAmount2)) {
            result.add(newModifiedRecordVo(version,"申请采购总金额",
                    reqTotalAmount1,reqTotalAmount2,modifiedTime));
        }
        BigDecimal marketPrice1 = before.getMarketPrice();
        BigDecimal marketPrice2 = after.getMarketPrice();
        if (isModified(marketPrice1, marketPrice2)) {
            result.add(newModifiedRecordVo(version,"市场参考价/市场控制总价（总）",
                    marketPrice1,marketPrice2,modifiedTime));
        }
        Integer numberOfWinSup1=before.getNumberOfWinSup();
        Integer numberOfWinSup2=after.getNumberOfWinSup();
        if(isModified(numberOfWinSup1,numberOfWinSup2)){
            result.add(newModifiedRecordVo(version,"拟成交供应商数",
                    numberOfWinSup1,numberOfWinSup2,modifiedTime));
        }
        String purDuration1=before.getPurDuration();
        String purDuration2=after.getPurDuration();
        if(isModified(purDuration1,purDuration2)){
            result.add(newModifiedRecordVo(version,"采购实施工时",
                    purDuration1,purDuration2,modifiedTime));
        }
        String purMode1=before.getPurMode();
        String purMode2=after.getPurMode();
        if(isModified(purMode1,purMode2)){
            result.add(newModifiedRecordVo(version,"选取的采购方式",
                    purMode1,purMode2,modifiedTime));
        }
        String projFeatures1=before.getProjFeatures();
        String projFeatures2=after.getProjFeatures();
        if(isModified(projFeatures1,projFeatures2)){
            result.add(newModifiedRecordVo(version,"项目特性",
                    projFeatures1,projFeatures2,modifiedTime));
        }
        String evalMethod1=before.getEvalMethod();
        String evalMethod2=after.getEvalMethod();
        if(isModified(evalMethod1,evalMethod2)){
            result.add(newModifiedRecordVo(version,"选取的评标方法",
                    evalMethod1,evalMethod2,modifiedTime));
        }
        DepartmentVo reqDepartment1=before.getReqDepartment();
        DepartmentVo reqDepartment2=after.getReqDepartment();
        if(isModified(reqDepartment1.getId(),reqDepartment2.getId())){
            String beforeValue=reqDepartment1.getName();
            String afterValue=reqDepartment2.getName();
            result.add(newModifiedRecordVo(version,"项目申请部门",
                    beforeValue,afterValue,modifiedTime));
        }
        UserVo reqUser1=before.getReqUser();
        UserVo reqUser2=after.getReqUser();
        if(isModified(reqUser1.getId(),reqUser2.getId())){
            result.add(newModifiedRecordVo(version,"申请人",
                    reqUser1.getRealname(),reqUser2.getRealname(),modifiedTime));
        }
        String reqUserPhone1=before.getReqUserPhone();
        String reqUserPhone2=after.getReqUserPhone();
        if(isModified(reqUserPhone1,reqUserPhone2)){
            result.add(newModifiedRecordVo(version,"申请人联系电话",
                    reqUserPhone1,reqUserPhone2,modifiedTime));
        }
        if(isModified(before.getAcceptTime(),after.getAcceptTime())){
            result.add(newModifiedRecordVo(version,"受理日期",
                    before.getAcceptTime(),after.getAcceptTime(),modifiedTime));
        }
        CgSupplierLabelVo supLabel1=before.getSupLabel();
        CgSupplierLabelVo supLabel2=after.getSupLabel();
        if(isModified(supLabel1.getId(),supLabel2.getId())){
            result.add(newModifiedRecordVo(version,"全体年度资格商",
                    supLabel1.getName(),supLabel2.getName(),modifiedTime));
        }
        if(isModified(before.getPurType(),after.getPurType())){
            result.add(newModifiedRecordVo(version,"采购种类",
                    before.getPurType(),after.getPurType(),modifiedTime));
        }
        if(isModified(before.getContractCategory(),after.getContractCategory())){
            result.add(newModifiedRecordVo(version,"合同类别",
                    before.getContractCategory(),after.getContractCategory(),modifiedTime));
        }
        if(isModified(before.getPurCategory(),after.getPurCategory())){
            result.add(newModifiedRecordVo(version,"采购类别",
                    before.getPurCategory(),after.getPurCategory(),modifiedTime));
        }
        CgPurchasePlanVo purchasePlan1=before.getPurchasePlan();
        CgPurchasePlanVo purchasePlan2=after.getPurchasePlan();
        if(isModified(purchasePlan1.getId(),purchasePlan2.getId())){
            result.add(newModifiedRecordVo(version,"采购方案",
                    purchasePlan1.getProjectName(),purchasePlan2.getProjectName(),modifiedTime));
        }
        if(isModified(before.getNewPurchase(),after.getNewPurchase())){
            result.add(newModifiedRecordVo(version,"全新/以往采购",
                    before.getNewPurchase(),after.getNewPurchase(),modifiedTime));
        }
        if(isModified(before.getProjectCategory(),after.getProjectCategory())){
            result.add(newModifiedRecordVo(version,"项目类别",
                    before.getProjectCategory(),after.getProjectCategory(),modifiedTime));
        }
        if(isModified(before.getPurpose(),after.getPurpose())){
            result.add(newModifiedRecordVo(version,"采购用途",
                    before.getPurpose(),after.getPurpose(),modifiedTime));
        }
        if(isModified(before.getApplicableScope(),after.getApplicableScope())){
            result.add(newModifiedRecordVo(version,"适用范围",
                    before.getApplicableScope(),after.getApplicableScope(),modifiedTime));
        }
        if(isModified(before.getPurReq(),after.getPurReq())){
            result.add(newModifiedRecordVo(version,"采购需求",
                    before.getPurReq(),after.getPurReq(),modifiedTime));
        }
        /*CgRequirementVo requirement1=before.getRequirement();
        CgRequirementVo requirement2=after.getRequirement();
        if(isModified(requirement1.getId(),requirement2.getId())){
            result.add(newModifiedRecordVo(version,"采购需求(名称)",
                    requirement1.getName(),requirement2.getName(),modifiedTime));
        }*/
        if(isModified(before.getPurHistory(),after.getPurHistory())){
            result.add(newModifiedRecordVo(version,"我行历史采购情况",
                    before.getPurHistory(),after.getPurHistory(),modifiedTime));
        }
        if(isModified(before.getSupQualReq(),after.getSupQualReq())){
            result.add(newModifiedRecordVo(version,"供应商资质要求",
                    before.getSupQualReq(),after.getSupQualReq(),modifiedTime));
        }
        if(isModified(before.getPurGroup(),after.getPurGroup())){
            result.add(newModifiedRecordVo(version,"采购小组组成",
                    before.getPurGroup(),after.getPurGroup(),modifiedTime));
        }
        if(isModified(before.getPurModeReason(),after.getPurModeReason())){
            result.add(newModifiedRecordVo(version,"采购方式原因",
                    before.getPurModeReason(),after.getPurModeReason(),modifiedTime));
        }
        if(isModified(before.getEvalMethodReason(),after.getEvalMethodReason())){
            result.add(newModifiedRecordVo(version,"评价办法说明",
                    before.getEvalMethodReason(),after.getEvalMethodReason(),modifiedTime));
        }
        if(isModified(before.getMarketSupProfile(),after.getMarketSupProfile())){
            result.add(newModifiedRecordVo(version,"市场及供应商情况",
                    before.getMarketSupProfile(),after.getMarketSupProfile(),modifiedTime));
        }
        if(isModified(before.getNote(),after.getNote())){
            result.add(newModifiedRecordVo(version,"有关情况说明",
                    before.getNote(),after.getNote(),modifiedTime));
        }
        if(isModified(before.getReturnReq(),after.getReturnReq())){
            result.add(newModifiedRecordVo(version,"退回申请",
                    before.getReturnReq(),after.getReturnReq(),modifiedTime));
        }
       /* if(isModified(before.getStatus(),after.getStatus())){
            result.add(newModifiedRecordVo(version,"状态",
                    before.getStatus(),after.getStatus(),modifiedTime));
        }*/
        //item
        List<CgProjectItemVo> beforeItems=before.getProjectItems()==null?Collections.emptyList():before.getProjectItems();
        List<CgProjectItemVo> afterItems=after.getProjectItems()==null?Collections.emptyList():after.getProjectItems();
        loop:
        for(CgProjectItemVo beforeItem:beforeItems){
            for(CgProjectItemVo afterItem:afterItems){
                if(Objects.equals(beforeItem.getName(),afterItem.getName())){
                    if(isModified(beforeItem.getMarketPrice(),afterItem.getMarketPrice())){
                        result.add(newModifiedRecordVo(version,String.format("采购清单(%s).市场参考价", beforeItem.getName()),beforeItem.getMarketPrice(),afterItem.getMarketPrice(),modifiedTime));
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
        for (CgProjectItemVo afterItem : afterItems) {
            for (CgProjectItemVo beforeItem : beforeItems) {
                if (Objects.equals(beforeItem.getName(), afterItem.getName())) {
                    continue loop;
                }
            }
            result.add(newModifiedRecordVo(version,"【新增】采购清单","",afterItem.getName(),modifiedTime));
        }

        //sup
        List<CgProjectSupVo> beforeSups=before.getProjectSups()==null?Collections.emptyList():before.getProjectSups();
        List<CgProjectSupVo> afterSups=after.getProjectSups()==null?Collections.emptyList():after.getProjectSups();
        loop:
        for(CgProjectSupVo beforeSup:beforeSups){
            for(CgProjectSupVo afterSup:afterSups){
                if(Objects.equals(beforeSup.getSupplier().getId(),afterSup.getSupplier().getId())){
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
        for(CgProjectSupVo afterSup:afterSups){
            for(CgProjectSupVo beforeSup:beforeSups){
                if(Objects.equals(afterSup.getSupplier().getId(),beforeSup.getSupplier().getId())){
                    continue loop;
                }
            }
            result.add(newModifiedRecordVo(version, "【新增】推荐供应商","",afterSup.getSupplier().getName(),modifiedTime));
        }

        //att
        List<CgAttVo> beforeAtts=before.getProjectAtts()==null?Collections.emptyList():before.getProjectAtts();
        List<CgAttVo> afterAtts=after.getProjectAtts()==null?Collections.emptyList():after.getProjectAtts();
        Set<String> beforeAttNameSet = beforeAtts.stream().map(CgAttVo::getName).collect(Collectors.toSet());
        Set<String> afterAttNameSet = afterAtts.stream().map(CgAttVo::getName).collect(Collectors.toSet());
        for(String beforeAttName:beforeAttNameSet){
            if (!afterAttNameSet.contains(beforeAttName)) {
                result.add(newModifiedRecordVo(version,"【删除】附件", beforeAttName,"",modifiedTime));
            }
        }
        for (String afterAttName : afterAttNameSet) {
            if (!beforeAttNameSet.contains(afterAttName)) {
                result.add(newModifiedRecordVo(version,"【新增】附件", "",afterAttName,modifiedTime));
            }
        }

        //oaData
        List<CgProjectOaDatumDto> beforeOaData=before.getProjectOaData();
        List<CgProjectOaDatumDto> afterOaData=after.getProjectOaData();
        Set<String> beforeApprovalNoSet = beforeOaData.stream().map(CgProjectOaDatumDto::getApprovalNo).collect(Collectors.toSet());
        Set<String> afterApprovalNoSet = afterOaData.stream().map(CgProjectOaDatumDto::getApprovalNo).collect(Collectors.toSet());
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
