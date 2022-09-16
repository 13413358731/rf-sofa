package com.realfinance.sofa.system.util;

import com.realfinance.sofa.system.domain.*;
import com.realfinance.sofa.system.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据自定义查询条件对象创建Specification的工具类
 */
public class QueryCriteriaUtils {

    public static Specification<Tenant> toSpecification(TenantQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), queryCriteria.getId()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getNameLike())) {
                predicates.add(criteriaBuilder.like(root.get("name"),"%" + queryCriteria.getNameLike() + "%"));
            }
            if (queryCriteria.getEnabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("enabled"), queryCriteria.getEnabled()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<User> toSpecification(UserQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (queryCriteria.getIdIn() != null) {
                predicates.add(root.get("id").in(queryCriteria.getIdIn()));
            }
            if (queryCriteria.getDepartmentIdIn() != null) {
                predicates.add(root.get("departmentId").in(queryCriteria.getDepartmentIdIn()));
            }

            /*if (queryCriteria.getRoleIdIn() != null) {
                Predicate r = root.get("roles").get("id").in(queryCriteria.getRoleIdIn());
                Predicate rg = root.get("roleGroups").get("roles").get("id").in(queryCriteria.getRoleIdIn());
                predicates.add(criteriaBuilder.or(r,rg));
            }*/

            if (StringUtils.isNotEmpty(queryCriteria.getUsernameLike())) {
                predicates.add(criteriaBuilder.like(root.get("username").as(String.class),"%" + queryCriteria.getUsernameLike() + "%"));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getRealnameLike())) {
                predicates.add(criteriaBuilder.like(root.get("realname").as(String.class),"%" + queryCriteria.getRealnameLike() + "%"));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getUsernameLikeOrRealnameLike())) {
                Predicate usernameLikeOrRealnameLike = criteriaBuilder.or(criteriaBuilder.like(root.get("username").as(String.class), "%" + queryCriteria.getUsernameLikeOrRealnameLike() + "%"),
                        criteriaBuilder.like(root.get("realname").as(String.class), "%" + queryCriteria.getUsernameLikeOrRealnameLike() + "%"));
                predicates.add(usernameLikeOrRealnameLike);
            }
            if (queryCriteria.getEnabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("enabled").as(Boolean.class),queryCriteria.getEnabled()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getClassification())) {
                predicates.add(criteriaBuilder.equal(root.get("classification").as(String.class),queryCriteria.getClassification()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email").as(String.class),queryCriteria.getEmail()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getMobile())) {
                predicates.add(criteriaBuilder.equal(root.get("mobile").as(String.class),queryCriteria.getMobile()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getTenantId())) {
                predicates.add(criteriaBuilder.equal(root.get("tenant").get("id"),queryCriteria.getTenantId()));
            }
            if (queryCriteria.getDepartmentId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("department").get("id"),queryCriteria.getDepartmentId()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getDepartmentCodePathContains())) {
                predicates.add(criteriaBuilder.like(root.get("department").get("codePath"),
                        "%/" + queryCriteria.getDepartmentCodePathContains() + "/%"));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<Role> toSpecification(RoleQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(queryCriteria.getCode())) {
                predicates.add(criteriaBuilder.equal(root.get("code"), queryCriteria.getCode()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getCodeLike())) {
                predicates.add(criteriaBuilder.like(root.get("code"), "%" + queryCriteria.getCodeLike() + "%"));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getNameLike())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getCodeLikeOrNameLike())) {
                Predicate codeLikeOrNameLike = criteriaBuilder.or(criteriaBuilder.like(root.get("code").as(String.class), "%" + queryCriteria.getCodeLikeOrNameLike() + "%"),
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + queryCriteria.getCodeLikeOrNameLike() + "%"));
                predicates.add(codeLikeOrNameLike);
            }
            if (queryCriteria.getEnabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("enabled").as(Boolean.class),queryCriteria.getEnabled()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getTenantId())) {
                predicates.add(criteriaBuilder.equal(root.get("tenant").get("id"),queryCriteria.getTenantId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<Department> toSpecification(DepartmentQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(queryCriteria.getCode())) {
                predicates.add(criteriaBuilder.equal(root.get("code"), queryCriteria.getCode()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("name"), queryCriteria.getName()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getNameLike())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getTenantId())) {
                predicates.add(criteriaBuilder.equal(root.get("tenant").get("id"),queryCriteria.getTenantId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<RoleGroup> toSpecification(RoleGroupQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(queryCriteria.getCode())) {
                predicates.add(criteriaBuilder.equal(root.get("code"), queryCriteria.getCode()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getName())) {
                predicates.add(criteriaBuilder.equal(root.get("name"), queryCriteria.getName()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getCodeLikeOrNameLike())) {
                Predicate codeLikeOrNameLike = criteriaBuilder.or(criteriaBuilder.like(root.get("code").as(String.class), "%" + queryCriteria.getCodeLikeOrNameLike() + "%"),
                        criteriaBuilder.like(root.get("name").as(String.class), "%" + queryCriteria.getCodeLikeOrNameLike() + "%"));
                predicates.add(codeLikeOrNameLike);
            }
            if (queryCriteria.getEnabled() != null) {
                predicates.add(criteriaBuilder.equal(root.get("enabled").as(Boolean.class),queryCriteria.getEnabled()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getTenantId())) {
                predicates.add(criteriaBuilder.equal(root.get("tenant").get("id"),queryCriteria.getTenantId()));
            }
            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }

    public static Specification<TextTemplate> toSpecification(TextTemplateQueryCriteria queryCriteria) {
        return (root, query, criteriaBuilder) -> {
            if (queryCriteria == null) {
                return null;
            }
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(queryCriteria.getNameLike())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getNameLike() + "%"));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getType())) {
                predicates.add(criteriaBuilder.equal(root.get("type").as(String.class),queryCriteria.getType()));
            }
            if (StringUtils.isNotEmpty(queryCriteria.getTenantId())) {
                predicates.add(criteriaBuilder.equal(root.get("tenantId"),queryCriteria.getTenantId()));
            }
            if (queryCriteria.getUserScopeIn() != null) {
                predicates.add(root.get("userScope").in(queryCriteria.getUserScopeIn()));
            }
            if (queryCriteria.getDepartmentScopeIn() != null) {
                predicates.add(root.get("departmentScope").in(queryCriteria.getDepartmentScopeIn()));
            }
            if (queryCriteria.getRoleScopeIn() != null) {
                predicates.add(root.get("roleScope").in(queryCriteria.getRoleScopeIn()));
            }
            if (queryCriteria.getCreatedUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("createdUserId"), queryCriteria.getCreatedUserId()));
            }


            // OR
            List<Predicate> orPredicates = new ArrayList<>();
            if (queryCriteria.getOrUserScopeIn() != null) {
                orPredicates.add(root.get("userScope").in(queryCriteria.getOrUserScopeIn()));
            }
            if (queryCriteria.getOrDepartmentScopeIn() != null) {
                orPredicates.add(root.get("departmentScope").in(queryCriteria.getOrDepartmentScopeIn()));
            }
            if (queryCriteria.getOrRoleScopeIn() != null) {
                orPredicates.add(root.get("roleScope").in(queryCriteria.getOrRoleScopeIn()));
            }
            if (queryCriteria.getOrCreatedUserId() != null) {
                orPredicates.add(criteriaBuilder.equal(root.get("createdUserId"), queryCriteria.getOrCreatedUserId()));
            }
            if (!orPredicates.isEmpty()) {
                predicates.add(criteriaBuilder.or(orPredicates.toArray(Predicate[]::new)));
            }

            return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
        };
    }
}
