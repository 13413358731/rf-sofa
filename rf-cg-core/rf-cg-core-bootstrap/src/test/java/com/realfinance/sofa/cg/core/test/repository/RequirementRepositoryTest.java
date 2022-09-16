//package com.realfinance.sofa.cg.core.test.repository;
//
//import com.realfinance.sofa.cg.core.domain.*;
//import com.realfinance.sofa.cg.core.domain.proj.PurchaseProjAttachment;
//import com.realfinance.sofa.cg.core.domain.req.Requirement;
//import com.realfinance.sofa.cg.core.domain.req.RequirementAtt;
//import com.realfinance.sofa.cg.core.repository.RequirementRepository;
//import com.realfinance.sofa.cg.core.test.base.AbstractTestBase;
//import org.junit.Test;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//
//public class RequirementRepositoryTest extends AbstractTestBase {
//
//    @Resource
//    RequirementRepository requirementRepository;
//
//    @Test
//    @Transactional
//    public void test1() {
//        Requirement requirement = new Requirement();
//        requirement.setTenantId("sdebank");
//        requirement.setDepartmentId(1);
//        requirement.setName("test");
//        requirement.setReqTotalAmount(new BigDecimal(500));
//        requirement.setMarketPrice(new BigDecimal(500));
//        requirement.setReqUserPhone("123");
//        requirement.setUseDepartmentIds("3");
//        requirement.setPurDepartmentId(4);
//        requirement.setAcceptStatus(Requirement.AcceptStatus.CS);
//        requirement.setReason("");
//        requirement.setSupRequirements("asd");
//        requirement.setSupSpecialRequirements("test");
//        requirement.setNote("test");
//        requirement.setPurType(PurchaseType.INFRASTRUCTURE);
//        requirement.setPurMode(PurchaseMode.GGZB);
//        requirement.setProjFeatures(ProjectFeatures.QT);
//        requirement.setSugEvalMethod(EvalMethod.HLDJF);
//        requirement.setContractCategory(ContractCategory.DDHT);
//        requirement.setContractCreatedUserId(1);
//        requirement.setContractValidity(LocalDate.now());
//        requirement.setPurCategory(PurchaseCategory.FMLCG);
//        requirement.setPlanStatus(Requirement.PlanStatus.JHN);
//        requirement.setStatus(FlowStatus.EDIT);
//
//        RequirementAtt requirementAtt = new RequirementAtt();
//        requirementAtt.setName("aa");
//        requirementAtt.setUploadTime(LocalDateTime.now());
//        requirementAtt.setUploadUserId(1);
//        requirementAtt.setSource("2");
//        requirementAtt.setSize(2L);
//        requirementAtt.setExt(".rar");
//        requirementAtt.setPath("/aa/aa.rar");
//        requirement.setRequirementAtts(Collections.singleton(requirementAtt));
//        Requirement save = requirementRepository.save(requirement);
//        requirementRepository.flush();
//
//        PurchaseProjAttachment purchaseProjAttachment = new PurchaseProjAttachment();
//        purchaseProjAttachment.setName("aa2");
//        purchaseProjAttachment.setUploadTime(LocalDateTime.now());
//        purchaseProjAttachment.setUploadUserId(1);
//        purchaseProjAttachment.setSource("2");
//        purchaseProjAttachment.setSize(2L);
//        purchaseProjAttachment.setExt(".rar");
//        purchaseProjAttachment.setPath("/aa/aa.rar");
//    }
//
//    @Test
//    @Transactional
//    public void test2() {
//        List<Requirement> all = requirementRepository.findAll();
//        Set<RequirementAtt> requirementAtts = all.get(0).getRequirementAtts();
//        System.out.println(requirementAtts.size());
//    }
//
//}
