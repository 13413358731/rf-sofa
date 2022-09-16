package com.realfinance.sofa.cg.controller.system;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.*;
import com.realfinance.sofa.cg.service.mapstruct.DepartmentMapper;
import com.realfinance.sofa.cg.service.mapstruct.TenantMapper;
import com.realfinance.sofa.cg.service.mapstruct.UserMapper;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.UserMngFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import com.realfinance.sofa.system.model.TenantSmallDto;
import com.realfinance.sofa.system.model.UserDetailsDto;
import com.realfinance.sofa.system.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: 2020/11/19 查询角色，角色组参照接口
@RestController
@Tag(name = "用户管理")
@RequestMapping("/system/usermng")
public class UserMngController {

    private static final Logger log = LoggerFactory.getLogger(UserMngController.class);

    public static final String MENU_CODE_ROOT = "usermng";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";

    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private UserMngFacade userMngFacade;
    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @Resource
    private UserMapper userMapper;
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private TenantMapper tenantMapper;

    @GetMapping("list")
    @Operation(summary = "查询用户列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.UserMngController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<UserVo>> list(UserQueryCriteriaRequest queryCriteria,
                                             Pageable pageable) {
        Page<UserDto> result = userMngFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(userMapper::userDto2UserVo));
    }

    @GetMapping("get")
    @Operation(summary = "查询用户详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.UserMngController).MENU_CODE_VIEW)")
    public ResponseEntity<UserDetailsVo> get(@Parameter(description = "ID") @RequestParam Integer id) {
        UserDetailsDto result = userMngFacade.getDetailsById(id);
        return ResponseEntity.ok(userMapper.userDetailsDto2UserDetailsVo(result));
    }

    @PostMapping("save")
    @Operation(summary = "保存用户")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.UserMngController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@Validated @RequestBody UserSaveRequest userSaveRequest) {
        Integer id = userMngFacade.save(userMapper.userSaveRequest2UserSaveDto(userSaveRequest));
        return ResponseEntity.ok(id);
    }

    @GetMapping("querydepartmentrefer")
    @Operation(summary = "查询部门参照",description = "返回部门树，非系统租户只能返回当前登录的法人下的部门树")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DepartmentTreeVo>> queryDepartmentRefer() {
        List<DepartmentSmallTreeDto> result = systemQueryFacade.queryDepartmentRefer();
        return ResponseEntity.ok(result.stream().map(departmentMapper::departmentSmallTreeDto2DepartmentTreeVo).collect(Collectors.toList()));
    }

    @GetMapping("querytenantrefer")
    @Operation(summary = "查询法人参照", description = "非系统租户只返回当前登录的法人")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> queryTenantRefer(TenantQueryCriteriaRequest queryCriteria,
                                              Pageable pageable) {
        Page<TenantSmallDto> result = systemQueryFacade.queryTenantRefer(queryCriteria,pageable);
        return ResponseEntity.ok(result.map(tenantMapper::tenantSmallDto2TenantVo));
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除用户")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.UserMngController).MENU_CODE_DELETE)")
    public ResponseEntity<?> delete(@Parameter(description = "用户ID") @RequestParam Set<Integer> id) {
        userMngFacade.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("resetpassword")
    @Operation(summary = "修改密码")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.UserMngController).MENU_CODE_SAVE)")
    public ResponseEntity<?> resetPassword(@Validated @RequestBody ResetPasswordRequest resetPasswordRequest) {
        userMngFacade.resetPassword(resetPasswordRequest.getUserId(), null,
                resetPasswordRequest.getNewPassword(), resetPasswordRequest.getNewPassword());
        return ResponseEntity.ok().build();
    }

    public static class ResetPasswordRequest {
        @NotNull
        @Schema(description = "用户ID", type = "integer")
        private Integer userId;
        @NotBlank
        @Size(min = 6, max = 20)
        @Schema(description = "新密码", type = "string")
        private String newPassword;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
