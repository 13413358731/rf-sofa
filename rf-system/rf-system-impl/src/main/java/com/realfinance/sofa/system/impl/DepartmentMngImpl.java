package com.realfinance.sofa.system.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.domain.Department;
import com.realfinance.sofa.system.exception.RfSystemException;
import com.realfinance.sofa.system.facade.DepartmentMngFacade;
import com.realfinance.sofa.system.model.DepartmentDto;
import com.realfinance.sofa.system.model.DepartmentSaveDto;
import com.realfinance.sofa.system.repository.DepartmentRepository;
import com.realfinance.sofa.system.repository.TenantRepository;
import com.realfinance.sofa.system.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.system.service.mapstruct.DepartmentSaveMapper;
import com.realfinance.sofa.system.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.realfinance.sofa.common.datascope.DataScopeUtils.checkTenantCanAccess;
import static com.realfinance.sofa.system.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.system.util.ExceptionUtils.entityNotFound;

@Service
@SofaService(interfaceType = DepartmentMngFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class DepartmentMngImpl implements DepartmentMngFacade {
    private static final Logger log = LoggerFactory.getLogger(DepartmentMngImpl.class);

    // 部门查询排序
    private static final Sort SORT = Sort.by("sort");

    private final DepartmentRepository departmentRepository;
    private final TenantRepository tenantRepository;
    private final DepartmentMapper departmentMapper;
    private final DepartmentSaveMapper departmentSaveMapper;

    public DepartmentMngImpl(DepartmentRepository departmentRepository,
                             TenantRepository tenantRepository,
                             DepartmentMapper departmentMapper,
                             DepartmentSaveMapper departmentSaveMapper) {
        this.departmentRepository = departmentRepository;
        this.tenantRepository = tenantRepository;
        this.departmentMapper = departmentMapper;
        this.departmentSaveMapper = departmentSaveMapper;
    }

    @Override
    public List<DepartmentDto> listFirstLevel() {
        Department example = new Department();
        example.setType(Department.Type.FIRST_LEVEL);
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            example.setTenant(tenantRepository.getOne(DataScopeUtils.loadTenantId()));
        }
        List<Department> firstLevelMenus = departmentRepository.findAll(Example.of(example), SORT);
        return departmentMapper.toDtoList(firstLevelMenus);
    }

    @Override
    public List<DepartmentDto> listByParentId(Integer parentId) {
        List<Department> departments;
        if (parentId == null) {
            departments = departmentRepository.findByParentIsNull(SORT);
        } else {
            departments = departmentRepository.findByParent(departmentRepository.getOne(parentId), SORT);
        }
        return departmentMapper.toDtoList(departments);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(DepartmentSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        checkTenantCanAccess(saveDto.getTenant());
        try {
            Department saved;
            if (saveDto.getId() == null) { // 新增
                saved = handleNewDepartment(saveDto);
            } else { // 修改
                saved = handleUpdateDepartment(saveDto);
            }
            departmentRepository.flush();
            return saved.getId();
        } catch (RfSystemException e) {
            throw e;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw ExceptionUtils.businessException("保存失败：" + e.getMessage());
        }
    }

    /**
     * 处理新增部门
     * @param saveDto
     * @return
     */
    protected Department handleNewDepartment(DepartmentSaveDto saveDto) {
        Department department = departmentSaveMapper.toEntity(saveDto);
        department.resetPath();
        setParentToNullIfTypeIsFirstLevel(department);
        // 检查子级类型的部门必须包含父ID
        if (department.getType() != Department.Type.FIRST_LEVEL && department.getParent() == null) {
            throw businessException("父部门不能为null");
        }
        // 检查部门租户与上级部门的租户是否一致
        if (department.getParent() != null && !Objects.equals(department.getParent().getTenant(),department.getTenant())) {
            throw businessException("与上级部门Tenant不一致");
        }
        // 检查部门编码是否已使用
        if (departmentRepository.existsByTenantAndCode(department.getTenant(),department.getCode())) {
            throw businessException("部门编码已存在");
        }
        department.setLeafCount(0);
        Department saved = departmentRepository.save(department);
        updateLeafCount(department.getParent());
        return saved;
    }

    /**
     * 处理更新部门
     * @param saveDto
     * @return
     */
    protected Department handleUpdateDepartment(DepartmentSaveDto saveDto) {
        Department department = departmentRepository.findById(saveDto.getId())
                .orElseThrow(() -> entityNotFound(Department.class, "id", saveDto.getId()));
        // 检查用户租户是否修改
        if (!Objects.equals(department.getTenant().getId(),saveDto.getTenant())) {
            throw businessException("Tenant不能修改");
        }
        // 如果部门编码修改，检查部门编码是否已使用
        if (!Objects.equals(saveDto.getCode(),department.getCode())
                && departmentRepository.existsByTenantAndCode(department.getTenant(),saveDto.getCode())) {
            throw businessException("部门编码已存在");
        }
        // 修改前的父节点
        Department formerParent = department.getParent();
        departmentSaveMapper.updateEntity(department,saveDto);
        boolean pathChanged = department.resetPath();
        setParentToNullIfTypeIsFirstLevel(department);
        // 检查子级类型的部门必须包含父ID
        if (department.getType() != Department.Type.FIRST_LEVEL && department.getParent() == null) {
            throw businessException("父部门不能为null");
        }

        Department saved = departmentRepository.save(department);
        updateLeafCount(formerParent);
        updateLeafCount(department.getParent());
        // 如果path改变，修改子节点的path
        if (pathChanged) {
            resetChildrenPath(department);
        }
        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        List<Department> toDelete = departmentRepository.findAllById(ids).stream()
                .peek(e -> checkTenantCanAccess(e.getTenant().getId()))
                .collect(Collectors.toList());
        try {
            for (Department department : toDelete) {
                if (department.getUsers()!=null && department.getUsers().size()!=0){
                    throw ExceptionUtils.businessException("删除失败：部门下存在用户");
                }
            }
            if (!toDelete.isEmpty()) {
                for (Department department : toDelete) {
                    Department parent = department.getParent();
                    departmentRepository.delete(department);
                    updateLeafCount(parent);
                }
                departmentRepository.flush();
            }
        } catch (JpaSystemException e) {
            Throwable rootCause = e.getRootCause();
            if (rootCause instanceof SQLIntegrityConstraintViolationException) {
                throw ExceptionUtils.businessException("删除失败：部门下存在用户"); // Department实体放弃维护users的外键了
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw ExceptionUtils.businessException("删除失败：" + e.getMessage());
        }
    }

    /**
     * 更新子页数
     * @param department
     */
    private void updateLeafCount(Department department) {
        if (department == null) {
            return;
        }
        department.setLeafCount(departmentRepository.countByParent(department));
        try {
            departmentRepository.save(department);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新子页数失败",e);
            }
            throw businessException("更新子页数失败");
        }
    }

    /**
     * 递归修改子节点path
     * @param department
     */
    private void resetChildrenPath(Department department) {
        if (department == null || department.getLeafCount() <= 0) {
            return;
        }
        departmentRepository.findByParent(department).stream()
                .filter(Department::resetPath)
                .forEach(e -> {
                    departmentRepository.save(e);
                    resetChildrenPath(e);
                });
    }

    /**
     * 如果部门类型为一级部门，则设置父ID为{@code null}
     * @param department
     */
    private void setParentToNullIfTypeIsFirstLevel(Department department) {
        if (department != null && department.getType() == Department.Type.FIRST_LEVEL) {
            department.setParent(null);
        }
    }
}
