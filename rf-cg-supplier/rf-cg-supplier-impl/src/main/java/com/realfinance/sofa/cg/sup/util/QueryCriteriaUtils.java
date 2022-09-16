package com.realfinance.sofa.cg.sup.util;

import com.realfinance.sofa.cg.sup.domain.*;
import com.realfinance.sofa.cg.sup.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.Id;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class QueryCriteriaUtils {
    public static Specification<SupplierAccount> toSpecification(CgSupplierAccountQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(queryCriteria.getType())) {
                predicates.add(criteriaBuilder.equal(root.get("type"), queryCriteria.getType()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getUsernameLike())) {
                predicates.add(criteriaBuilder.like(root.get("username"),"%" + queryCriteria.getUsernameLike() + "%"));
            }
            if (StringUtils.isNotBlank(queryCriteria.getMobile())) {
                predicates.add(criteriaBuilder.like(root.get("mobile"),"%" +queryCriteria.getMobile()+ "%"));
            }
            if (queryCriteria.getEnabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("enabled"), queryCriteria.getEnabled()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<SupplierExamination> toSpecification(CgSupplierExaminationQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(queryCriteria.getUnifiedSocialCreditCode())) {
                predicates.add(criteriaBuilder.equal(root.get("unifiedSocialCreditCode"),queryCriteria.getUnifiedSocialCreditCode()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getNameLike())) {
                predicates.add(criteriaBuilder.like(root.get("name"),"%" + queryCriteria.getNameLike() + "%"));
            }
            if (queryCriteria.getAccountId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("account").get("id"),queryCriteria.getAccountId()));
            }
            if (queryCriteria.getSupplierId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("account").get("supplier").get("id"),queryCriteria.getSupplierId()));
            }
            if (StringUtils.isNotBlank(queryCriteria.getCategory())) {
                predicates.add(criteriaBuilder.equal(root.get("category"), SupplierExamination.Category.valueOf(queryCriteria.getCategory())));
            }

            if (StringUtils.isNotBlank(queryCriteria.getUsername())) {
                Join<Object, Object> account = root.join("account");
                predicates.add(criteriaBuilder.equal(account.get("username"),queryCriteria.getUsername()));
            }
            if (!CollectionUtils.isEmpty(queryCriteria.getCategoryIn())) {
                CriteriaBuilder.In<Object> categoryIn = criteriaBuilder.in(root.get("category"));
                for (String category : queryCriteria.getCategoryIn()) {
                    categoryIn.value(SupplierExamination.Category.valueOf(category));
                }
                predicates.add(categoryIn);
            }
            if (StringUtils.isNotBlank(queryCriteria.getStatus())) {
                FlowStatus flowStatus = FlowStatus.valueOf(queryCriteria.getStatus());
                predicates.add(criteriaBuilder.equal(root.get("status"),flowStatus));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<Supplier> toSpecification(CgSupplierQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(queryCriteria.getUnifiedSocialCreditCode())) {
                predicates.add(criteriaBuilder.equal(root.get("unifiedSocialCreditCode"),queryCriteria.getUnifiedSocialCreditCode()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getNameLike())) {
                predicates.add(criteriaBuilder.like(root.get("name"),"%" + queryCriteria.getNameLike() + "%"));
            }

            if (StringUtils.isNotBlank(queryCriteria.getUsername())) {
                Join<Object, Object> account = root.join("account");
                predicates.add(criteriaBuilder.equal(account.get("username"),queryCriteria.getUsername()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<SupplierBlacklist> toSpecification(CgSupplierBlacklistQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getSupplierId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("supplier").get("id"),queryCriteria.getSupplierId()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getTitleLike())) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + queryCriteria.getTitleLike() + "%"));
            }
            if (StringUtils.isNotBlank(queryCriteria.getStatus())) {
                FlowStatus flowStatus = FlowStatus.valueOf(queryCriteria.getStatus());
                predicates.add(criteriaBuilder.equal(root.get("status"),flowStatus));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<SupplierLabel> toSpecification(CgSupplierLabelQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getParentIsNull() != null) {
                predicates.add(criteriaBuilder.isNull(root.get("parent")));
            }
            if (queryCriteria.getParentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("parent").get("id"),queryCriteria.getParentId()));
            }
            if (queryCriteria.getSupplierLabelTypeId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type").get("id"),queryCriteria.getSupplierLabelTypeId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }


    public static Specification<SupplierAnnouncement> toSpecification(CgSupplierAnnouncementQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getChannelId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("channels").get("id"),queryCriteria.getChannelId()));
            }
            predicates.add(criteriaBuilder.equal(root.get("releasestatus"),"1"));
            predicates.add(criteriaBuilder.equal(root.get("type"),"1"));
            List<Order> orders=new ArrayList<>();
            orders.add(criteriaBuilder.desc(root.get("isTop")));
            orders.add(criteriaBuilder.desc(root.get("releaseDate")));


            return query.orderBy(orders).where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<BusinessReply> toSpecification(CgBusinessReplyQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getSupplierId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("supplierId"),queryCriteria.getSupplierId()));
            }
            if (queryCriteria.getReplyType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("replyType"),queryCriteria.getReplyType()));
            }
            if (queryCriteria.getBusinessProjectId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("project").get("projectId"),queryCriteria.getBusinessProjectId()));
            }
            if (queryCriteria.getNeedQuote() != null) {
                predicates.add(criteriaBuilder.equal(root.get("needQuote"),queryCriteria.getNeedQuote()));
            }
            if (queryCriteria.getReleaseId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("releaseId"),queryCriteria.getReleaseId()));
            }
            if(queryCriteria.getId()!=null){
                predicates.add(criteriaBuilder.equal(root.get("project").get("id"),queryCriteria.getId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<BusinessReply> toSpecification1(CgBusinessReplyQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getBusinessReplyId()!= null) {
                predicates.add(criteriaBuilder.equal(root.get("id"),queryCriteria.getBusinessReplyId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<BusinessReply> toSpecification2(CgBusinessReplyQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getId()!= null) {
                predicates.add(criteriaBuilder.equal(root.get("id"),queryCriteria.getId()));
            }
            if (queryCriteria.getSupplierId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("supplierId"),queryCriteria.getSupplierId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<BusinessProject> toSpecification(CgBusinessProjectQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getSupplierId() != null) {
                predicates.add(criteriaBuilder.equal(root.join("replies", JoinType.INNER).get("supplierId"),queryCriteria.getSupplierId()));
            }
            if (queryCriteria.getProjectStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projectStatus"),queryCriteria.getProjectStatus()));
            }
            if (queryCriteria.getProjectStatusNotIn() != null && !queryCriteria.getProjectStatusNotIn().isEmpty()) {
                predicates.add(criteriaBuilder.not(root.get("projectStatus").in(queryCriteria.getProjectStatusNotIn())));
            }
            if (queryCriteria.getProjectId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("projectId"),queryCriteria.getProjectId()));
            }
            query.distinct(true);
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<SupplierAssessment> toSpecification(CgSupplierAssessmentQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<SupplierAssessmentIndicator> toSpecification(CgSupplierAssessmentIndicatorQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getAssessmentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("supplierAssessment"),queryCriteria.getAssessmentId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<SupplierEvaluationSheetMain> toSpecification(CgSupplierEvaluationSheetMainQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<SupplierEvaluationMain> toSpecification(CgSupplierEvaluationMainQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<SupplierEvaluationProcessMng> toSpecification(CgSupEvaluationProcessMngQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

}
