package com.realfinance.sofa.cg.service.mapstruct;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.*;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.RoleVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.sup.facade.CgSupplierEvaluationSheetFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierEvaluationSheetDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelSmallDto;
import com.realfinance.sofa.common.model.IdentityObject;
import com.realfinance.sofa.common.model.ReferenceObject;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentSmallDto;
import com.realfinance.sofa.system.model.RoleSmallDto;
import com.realfinance.sofa.system.model.UserSmallDto;
import org.mapstruct.TargetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

// TODO: 2020/12/13 可以缓存Vo
@Component
public class CustomMapper {

    private static final Logger log = LoggerFactory.getLogger(CustomMapper.class);

    public static final String USER_SMALL_KEY_PREFIX = "USER_SMALL:";
    public static final String TENANT_SMALL_KEY_PREFIX = "TENANT_SMALL:";
    public static final String DEPARTMENT_SMALL_KEY_PREFIX = "DEPARTMENT_SMALL:";
    public static final String ROLE_SMALL_KEY_PREFIX = "ROLE_SMALL:";
    public static final String CG_SUPPLIER_SMALL_KEY_PREFIX = "CG_SUPPLIER_SMALL:";
    public static final String CG_SUPPLIER_LABEL_SMALL_KEY_PREFIX = "CG_SUPPLIER_LABEL_SMALL:";
    public static final String CG_PROJECT_SMALL_KEY_PREFIX = "CG_PROJECT_SMALL:";
    public static final String CG_SUPPLIER_EVALUATION_MAIN_SMALL_KEY_PREFIX = "CG_SUPPLIER_EVALUATION_MAIN_SMALL:";
    public static final String CG_PROJECT_EXECUTION_SMALL_KEY_PREFIX = "CG_PROJECT_EXECUTION_SMALL:";
    public static final String CG_EXPERT_LABEL_SMALL_KEY_PREFIX = "CG_EXPERT_LABEL_SMALL_SMALL:";
    public static final String CG_PURCHASECATALOGTREE_SMALL_KEY_PREFIX="CG_PURCHASECATALOGTREE_SMALL:";
    public static final String CG_PURCHASE_PLAN_SMALL_KEY_PREFIX="CG_PURCHASE_PLAN_SMALL:";
    public static final String CG_EXPERT_SMALL_KEY_PREFIX="CG_EXPERT_SMALL:";
    public static final String CG_REQUIREMENT_SMALL_KEY_PREFIX = "CG_REQUIREMENT_SMALL:";
    public static final String CG_PROJECTSCHEDULEUSER_SMALL_KEY_PREFIX="CG_PROJECTSCHEDULEUSER_SMALL:";


    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;
    @SofaReference(interfaceType = CgSupplierLabelFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierLabelFacade cgSupplierLabelFacade;
    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectFacade cgProjectFacade;
    @SofaReference(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectExecutionFacade cgProjectExecutionFacade;
    @SofaReference(interfaceType = CgExpertLabelFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelFacade cgExpertLabelFacade;
    @SofaReference(interfaceType = CgSupplierEvaluationSheetFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierEvaluationSheetFacade cgSupplierEvaluationSheetFacade;
    @SofaReference(interfaceType = CgPurchaseCatalogFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseCatalogFacade cgPurchaseCatalogFacade;
    @SofaReference(interfaceType = CgPurchasePlanFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchasePlanFacade cgPurchasePlanFacade;
    @SofaReference(interfaceType = CgExpertFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertFacade cgExpertFacade;
    @SofaReference(interfaceType = CgRequirementFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgRequirementFacade cgRequirementFacade;
    @SofaReference(interfaceType = CgProjectScheduleUserFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgProjectScheduleUserFacade cgProjectScheduleUserFacade;

    @Resource
    private UserMapper userMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private CgSupplierMapper cgSupplierMapper;
    @Resource
    private CgSupplierLabelMapper cgSupplierLabelMapper;
    @Resource
    private CgProjectMapper cgProjectMapper;
    @Resource
    private CgProjectExecutionMapper cgProjectExecutionMapper;
    @Resource
    private CgExpertLabelMapper cgExpertLabelMapper;
    @Resource
    private CgPurchaseCatalogMapper cgPurchaseCatalogMapper;
    @Resource
    private CgSupplierEvaluationSheetMapper cgSupplierEvaluationSheetMapper;
    @Resource
    private CgPurchasePlanMapper cgPurchasePlanMapper;
    @Resource
    private CgExpertMapper cgExpertMapper;
    @Resource
    private CgRequirementMapper cgRequirementMapper;
    @Resource
    private CgProjectScheduleUserMapper cgProjectScheduleUserMapper;

    public UserVo resolveUserVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        UserSmallDto dto = RpcUtils.getCache(USER_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return systemQueryFacade.queryUserSmallDto(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new UserSmallDto(id);
                    }
                });
        return userMapper.userSmallDto2UserVo(dto);
    }

    public DepartmentVo resolveDepartmentVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        DepartmentSmallDto dto = RpcUtils.getCache(DEPARTMENT_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return systemQueryFacade.queryDepartmentSmallDto(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new DepartmentSmallDto(id);
                    }
                });
        return departmentMapper.departmentSmallDto2DepartmentVo(dto);
    }

    public RoleVo resolveRoleVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        RoleSmallDto dto = RpcUtils.getCache(ROLE_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return systemQueryFacade.queryRoleSmallDto(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new RoleSmallDto(id);
                    }
                });
        return roleMapper.roleSmallDto2RoleVo(dto);
    }

    public CgSupplierVo resolveCgSupplierVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgSupplierDto dto = RpcUtils.getCache(CG_SUPPLIER_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgSupplierFacade.getById(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgSupplierDto(id);
                    }
                });
        return cgSupplierMapper.toVo(dto);
    }

    public CgRequirementVo resolveCgRequirementVo(Integer id){
        if(id==null||id<1){
            return null;
        }
        CgRequirementDto dto = RpcUtils.getCache(CG_REQUIREMENT_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgRequirementFacade.getById(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgRequirementDto(id);
                    }
                });
        return cgRequirementMapper.toVo(dto);
    }

    public CgSupplierLabelVo resolveCgSupplierLabelVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgSupplierLabelSmallDto dto = RpcUtils.getCache(CG_SUPPLIER_LABEL_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgSupplierLabelFacade.querySupplierLabelSmallDto(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgSupplierLabelSmallDto(id);
                    }
                });
        return cgSupplierLabelMapper.toVo(dto);
    }

    public CgExpertLabelVo resolveCgExpertLabelVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgExpertLabelSmallDto dto = RpcUtils.getCache(CG_EXPERT_LABEL_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgExpertLabelFacade.queryExpertLabelSmallDto(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgExpertLabelSmallDto(id);
                    }
                });
        return cgExpertLabelMapper.toVo(dto);
    }

    public CgProjectVo resolveCgProjectVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgProjectDto dto = RpcUtils.getCache(CG_PROJECT_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgProjectFacade.getById(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgProjectDto(id);
                    }
                });
        return cgProjectMapper.toVo(dto);
    }

   public CgPurchaseCatalogVo resolveCgPurchaseCatalogTreeVo(Integer id){
       if (id == null || id < 1) {
           return null;
       }
       CgPurchaseCatalogDto dto=RpcUtils.getCache(CG_PURCHASECATALOGTREE_SMALL_KEY_PREFIX + id,
               () -> {
                   try {
                       return cgPurchaseCatalogFacade.getById(id);
                   } catch (Exception e) {
                       if (log.isErrorEnabled()) {
                           log.error("", e);
                       }
                       return new CgPurchaseCatalogDto(id);
                   }
               });
        return  cgPurchaseCatalogMapper.toVo(dto);
   }



    public CgSupplierEvaluationSheetMainVo resolveCgSupplierEvaluationMainVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgSupplierEvaluationSheetDetailsSaveDto dto = RpcUtils.getCache(CG_SUPPLIER_EVALUATION_MAIN_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgSupplierEvaluationSheetFacade.getById(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgSupplierEvaluationSheetDetailsSaveDto(id);
                    }
                });
        return cgSupplierEvaluationSheetMapper.toVo(dto);
    }

    public CgProjectExecutionVo resolveCgProjectExecutionVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgProjectExecutionDto dto = RpcUtils.getCache(CG_PROJECT_EXECUTION_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgProjectExecutionFacade.getById(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgProjectExecutionDto(id);
                    }
                });
        return cgProjectExecutionMapper.toVo(dto);
    }

    public CgPurchasePlanVo resolveCgPurchasePlanVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgPurchasePlanDto dto = RpcUtils.getCache(CG_PURCHASE_PLAN_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgPurchasePlanFacade.getById(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgPurchasePlanDto(id);
                    }
                });
        return cgPurchasePlanMapper.toVo(dto);
    }

    public CgExpertVo resolveCgExpertVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgExpertDto dto = RpcUtils.getCache(CG_EXPERT_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgExpertFacade.getById(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgExpertDto(id);
                    }
                });
        return cgExpertMapper.toVo(dto);
    }

    public CgProjectScheduleUserVo resolveCgProjectScheduleUserVo(Integer id) {
        if (id == null || id < 1) {
            return null;
        }
        CgProjectScheduleUserDto dto = RpcUtils.getCache(CG_PROJECTSCHEDULEUSER_SMALL_KEY_PREFIX + id,
                () -> {
                    try {
                        return cgProjectScheduleUserFacade.getById(id);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("", e);
                        }
                        return new CgProjectScheduleUserDto(id);
                    }
                });
        return cgProjectScheduleUserMapper.toVo(dto);
    }


    // -------------------------------------------------------------------------

    public String resolveStringId(IdentityObject<String> identityObject) {
        if (identityObject == null) {
            return null;
        }
        return Objects.requireNonNull(identityObject.getId());
    }

    public Integer resolveIntegerId(IdentityObject<Integer> identityObject) {
        if (identityObject == null) {
            return null;
        }
        return Objects.requireNonNull(identityObject.getId());
    }

    public Long resolveLongId(IdentityObject<Long> identityObject) {
        if (identityObject == null) {
            return null;
        }
        return Objects.requireNonNull(identityObject.getId());
    }

    @SuppressWarnings("unchecked")
    public <ID extends Serializable,T extends ReferenceObject<ID>> T resolveReferenceObject(IdentityObject<ID> identityObject, @TargetType Class<T> t) {
        if (identityObject == null) {
            return null;
        }
        return (T) new ReferenceObject<>(identityObject.getId());
    }


    public LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
