package com.realfinance.sofa.cg.core.util;

import com.realfinance.sofa.cg.core.domain.*;
import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionAtt;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionSup;
import com.realfinance.sofa.cg.core.domain.exec.bid.BiddingDocument;
import com.realfinance.sofa.cg.core.domain.exec.release.MultipleRelease;
import com.realfinance.sofa.cg.core.domain.meeting.*;
import com.realfinance.sofa.cg.core.domain.plan.PurchasePlan;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.proj.ProjectSchedule;
import com.realfinance.sofa.cg.core.domain.purresult.PurchaseResult;
import com.realfinance.sofa.cg.core.domain.req.BaseRequirement;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.domain.vendor.VendorRating;
import com.realfinance.sofa.cg.core.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class QueryCriteriaUtils {

    public static Specification<Expert> toSpecification(CgExpertQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(queryCriteria.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), queryCriteria.getId()));
            }
            if (queryCriteria.getNameLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            if (queryCriteria.getExpertDeptId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("expertDepartment"), queryCriteria.getExpertDeptId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<ExpertLabel> toSpecification(CgExpertLabelQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getParentIsNull() != null) {
                predicates.add(criteriaBuilder.isNull(root.get("parent")));
            }
            if (queryCriteria.getParentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("parent").get("id"), queryCriteria.getParentId()));
            }
            if (queryCriteria.getExpertLabelTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type").get("id"), queryCriteria.getExpertLabelTypeId()));
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<ProductLibrary> toSpecification(ProductLibraryQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getPurchaseCatalogId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("purchaseCatalog"), queryCriteria.getPurchaseCatalogId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<Project> toSpecificationL(CgProjectQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getNameLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            if (queryCriteria.getProjectNoLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("projectNo"), "%" + queryCriteria.getProjectNoLike() + "%"));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<DrawExpert> toSpecificationDrawExpert(CgDrawExpertQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getEventLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("event"), "%" + queryCriteria.getEventLike() + "%"));
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<ExpertConfirm> toSpecificationExpertConfirm(CgExpertConfirmQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getExpertUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("expertUserId"), queryCriteria.getExpertUserId()));
            }
            if (queryCriteria.getEventLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("event"), "%" + queryCriteria.getEventLike() + "%"));
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<DrawExpertList> toSpecificationDrawExpertList(CgDrawExpertListQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(queryCriteria.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), queryCriteria.getId()));
            }
            if (queryCriteria.getIsAttend() != null) {
                predicates.add(criteriaBuilder.equal(root.get("IsAttend"), queryCriteria.getIsAttend()));
            }
            if (queryCriteria.getIsAttend() != null) {
                predicates.add(criteriaBuilder.equal(root.get("drawExpert").get("id"), queryCriteria.getDrawExpertId()));
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<Requirement> toSpecification(CgRequirementQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(queryCriteria.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getName() + "%"));
            }
            if (queryCriteria.getReqTotalAmount() != null && !queryCriteria.getReqTotalAmount().equals(BigDecimal.ZERO)) {
                predicates.add(criteriaBuilder.equal(root.get("reqTotalAmount"), queryCriteria.getReqTotalAmount()));
            }
            if (queryCriteria.getMarketPrice() != null && !queryCriteria.getMarketPrice().equals(BigDecimal.ZERO)) {
                predicates.add(criteriaBuilder.equal(root.get("marketPrice"), queryCriteria.getMarketPrice()));
            }
            if (queryCriteria.getDepartmentId() != null && queryCriteria.getDepartmentId() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("departmentId"), queryCriteria.getDepartmentId()));
            }
            if (queryCriteria.getCreatedUserId() != null && queryCriteria.getCreatedUserId() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("createdUserId"), queryCriteria.getCreatedUserId()));
            }
            if (queryCriteria.getPurDepartmentId() != null && queryCriteria.getPurDepartmentId() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("purDepartmentId"), queryCriteria.getPurDepartmentId()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getAcceptStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("acceptStatus"),
                        Requirement.AcceptStatus.valueOf(queryCriteria.getAcceptStatus())));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), FlowStatus.valueOf(queryCriteria.getStatus())));
            }
            /*if (queryCriteria.getOperatorUserIdIsNull() != null) {
                if (queryCriteria.getOperatorUserIdIsNull()){
                    predicates.add(criteriaBuilder.isNull(root.get("operatorUserId")));
                }else{
                    predicates.add(criteriaBuilder.isNotNull(root.get("operatorUserId")));
                }
            }*/
            if (StringUtils.isNoneEmpty(queryCriteria.getPlanStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("planStatus"), BaseRequirement.PlanStatus.valueOf(queryCriteria.getPlanStatus())));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<Project> toSpecification(CgProjectQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getNameLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            if (queryCriteria.getProjectNoLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("projectNo"), "%" + queryCriteria.getProjectNoLike() + "%"));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getPurCategory())) {
                predicates.add(criteriaBuilder.equal(root.get("purCategory"), ProjectFeatures.valueOf(queryCriteria.getPurCategory())));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getPurMode())) {
                predicates.add(criteriaBuilder.equal(root.get("purMode"), PurchaseMode.valueOf(queryCriteria.getPurMode())));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getEvalMethod())) {
                predicates.add(criteriaBuilder.equal(root.get("evalMethod"), EvalMethod.valueOf(queryCriteria.getEvalMethod())));
            }
            if (queryCriteria.getReqTotalAmount() != null && !queryCriteria.getReqTotalAmount().equals(BigDecimal.ZERO)) {
                predicates.add(criteriaBuilder.equal(root.get("reqTotalAmount"), queryCriteria.getReqTotalAmount()));
            }
            if (queryCriteria.getNumberOfWinSup() != null && queryCriteria.getNumberOfWinSup() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("numberOfWinSup"), queryCriteria.getNumberOfWinSup()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), FlowStatus.valueOf(queryCriteria.getStatus())));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<ProjectSchedule> toSpecification(CgProjectScheduleQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getProjectNameLike() != null && StringUtils.isNotEmpty(queryCriteria.getProjectNameLike())) {
                predicates.add(criteriaBuilder.like(root.get("projectName"), "%" + queryCriteria.getProjectNameLike() + "%"));
            }
            if (queryCriteria.getProjectStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projectStatus"), queryCriteria.getProjectStatus()));
            }
            if (queryCriteria.getContractStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("contractStatus"), queryCriteria.getContractStatus()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<ContractManage> toSpecification(CgContractManageQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getContractNameLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("contractName"), "%" + queryCriteria.getContractNameLike() + "%"));
            }
            if (queryCriteria.getProjectNoLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("projectNo"), "%" + queryCriteria.getProjectNoLike() + "%"));
            }
            if (queryCriteria.getStartDateAfter() != null) {
                //在这时间之后
                System.out.println(queryCriteria.getStartDateAfter());
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), queryCriteria.getStartDateAfter()));
            }
            if (queryCriteria.getStartDateBefore() != null) {
                //在这时间之前
                System.out.println(queryCriteria.getStartDateBefore());
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), queryCriteria.getStartDateBefore()));
            }
            if (queryCriteria.getExpireDateAfter() != null) {

                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expireDate"), queryCriteria.getExpireDateAfter()));
            }
            if (queryCriteria.getExpireDateBefore() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("expireDate"), queryCriteria.getExpireDateBefore()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<VendorRating> toSpecification(CgVendorRatingsQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getVendorNameLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("contractName"), "%" + queryCriteria.getVendorNameLike() + "%"));
            }
//            if (queryCriteria.getProjectNoLike() != null) {
//                predicates.add(criteriaBuilder.like(root.get("projectNo"), "%" + queryCriteria.getProjectNoLike() + "%"));
//            }
//            if (queryCriteria.getStartDateAfter() != null) {
//                //在这时间之后
//                System.out.println(queryCriteria.getStartDateAfter());
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), queryCriteria.getStartDateAfter()));
//            }
//            if (queryCriteria.getStartDateBefore() != null) {
//                //在这时间之前
//                System.out.println(queryCriteria.getStartDateBefore());
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), queryCriteria.getStartDateBefore()));
//            }
//            if (queryCriteria.getExpireDateAfter() != null) {
//
//                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("expireDate"), queryCriteria.getExpireDateAfter()));
//            }
//            if (queryCriteria.getExpireDateBefore() != null) {
//                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("expireDate"), queryCriteria.getExpireDateBefore()));
//            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<PurchaseCatalog> toSpecification(CgPurchaseCatalogQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getEnabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("enabled"), queryCriteria.getEnabled()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<Meeting> toSpecification(CgMeetingQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getMeetingHostUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("meetingHostUserId"), queryCriteria.getMeetingHostUserId()));
            }
            if (queryCriteria.getMeetingConfereeUserId() != null) {
                Join<Object, Object> join = root.join("conferees", JoinType.INNER);
                predicates.add(criteriaBuilder.equal(join.get("userId"), queryCriteria.getMeetingConfereeUserId()));
            }
            if (queryCriteria.getEndTimeIsNull() != null) {
                if (queryCriteria.getEndTimeIsNull()) {
                    predicates.add(criteriaBuilder.isNull(root.get("endTime")));
                } else {
                    predicates.add(criteriaBuilder.isNotNull(root.get("endTime")));
                }
            }
            if (queryCriteria.getNameLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<MeetingChatRecord> toSpecification(CgChatRecordQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getMeetingId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("meeting"), queryCriteria.getMeetingId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<MeetingConferee> toSpecification(CgConfereeQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getType() != null) {
                predicates.add(criteriaBuilder.notEqual(root.get("type"), ExpertType.valueOf(queryCriteria.getType())));
            }
            if (queryCriteria.getProjectId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projectId"), queryCriteria.getProjectId()));
            }
            if (queryCriteria.getMeetingId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("meeting").get("id"), queryCriteria.getMeetingId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<MeetingConferee> toSpecificationExpert(CgConfereeQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), ExpertType.valueOf(queryCriteria.getType())));
            }
            if (queryCriteria.getMeetingId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("meeting").get("id"), queryCriteria.getMeetingId()));
            }
            if (queryCriteria.getProjectId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projectId"), queryCriteria.getProjectId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<ProjectExecutionSup> toSpecification(CgSupplierQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getProjectExecutionId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projectExecution").get("id"), queryCriteria.getProjectExecutionId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<AuditQualification> toSpecification(CgAuditQualQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getExpertId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("expert"), queryCriteria.getExpertId()));
            }
            if (queryCriteria.getBiddingDocumentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("biddingDocumentId"), queryCriteria.getBiddingDocumentId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<AuditResponse> toSpecification(CgAuditRespQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getExpertId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("expert"), queryCriteria.getExpertId()));
            }
            if (queryCriteria.getBiddingDocumentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("biddingDocumentId"), queryCriteria.getBiddingDocumentId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<GradeSupSum> toSpecification(CgGradeSupSumQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getProjectId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projId").get("id"), queryCriteria.getProjectId()));
            }
            if (queryCriteria.getMeetingId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("meetingId"), queryCriteria.getMeetingId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<GradeSup> toSpecification(CgGradeSupQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getProjectId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projId"), queryCriteria.getProjectId()));
            }
            if (queryCriteria.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("expert"), queryCriteria.getUserId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<ProjectExecutionAtt> toSpecification(CgAttaFileQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getProjectExecution() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projectExecution").get("id"), queryCriteria.getProjectExecution()));
            }
            if (queryCriteria.getAttSign() != null) {
                predicates.add(criteriaBuilder.equal(root.get("attSign"), queryCriteria.getAttSign()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<BiddingDocument> toSpecification(CgBiddingDocumentQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getNameLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("projectExecution").get("project").get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            if (queryCriteria.getExecutionId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projectExecution").get("id"), queryCriteria.getExecutionId()));
            }
            if (queryCriteria.getProjectNoLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("projectExecution").get("project").get("projectNo"), "%" + queryCriteria.getProjectNoLike() + "%"));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<ProjectExecution> toSpecification(CgProjectExecutionQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getProjectId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("project").get("id"), queryCriteria.getProjectId()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getNameLike())) {
                predicates.add(criteriaBuilder.like(root.get("project").get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            if (queryCriteria.getReturnReq() != null) {
                predicates.add(criteriaBuilder.equal(root.get("returnReq"), queryCriteria.getReturnReq()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

//    public static Specification<ProjectExecution> toSpecification(CgValidSupplierQueryCriteria queryCriteria) {
//        return (root, query, criteriaBuilder) -> {
//            if (queryCriteria == null) {
//                return null;
//            }
//            List<Predicate> predicates = new ArrayList<>();
//            if (queryCriteria.getId() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("project").get("id"), queryCriteria.getProjectId()));
//            }
//            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
//        };
//    }

    public static Specification<MultipleRelease> toSpecification(CgMultipleReleaseQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getProjectExecutionId() != null) {
                predicates.add(criteriaBuilder.equal(root.join("projectExecution", JoinType.INNER).get("id"), queryCriteria.getProjectExecutionId()));
            }
            if (queryCriteria.getNameLike() != null) {
                predicates.add(criteriaBuilder.like(root.get("projectExecution").get("project").get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<PurchaseResult> toSpecification(CgPurchaseResultQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
//            if (queryCriteria.getProjectId() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("project").get("id"), queryCriteria.getProjectId()));
//            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<PurchasePlan> toSpecification(CgPurchasePlanQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("annualPlan"), queryCriteria.getId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
