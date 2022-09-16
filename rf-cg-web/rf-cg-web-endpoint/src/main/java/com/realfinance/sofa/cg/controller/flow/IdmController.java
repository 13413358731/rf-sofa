package com.realfinance.sofa.cg.controller.flow;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.UserDto;
import com.realfinance.sofa.system.model.UserQueryCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "流程身份管理")
@RequestMapping("/flow/idm")
public class IdmController {

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @GetMapping("queryuserrefer")
    @Operation(summary = "查询流程模块人员")
    public ResponseEntity<Page<UserVo>> queryUserRefer(@Parameter(description = "过滤条件") @RequestParam(required = false) String filter,
                                                       @Parameter(description = "用户范围") @RequestParam(required = false) Set<String> u,
                                                       @Parameter(description = "组范围") @RequestParam(required = false) Set<String> g,
                                                       Pageable pageable) {
        UserQueryCriteria queryCriteria = new UserQueryCriteria();
        if (filter != null && !filter.isEmpty()) {
            queryCriteria.setUsernameLikeOrRealnameLike(filter);
        }
        if (u != null && !u.isEmpty()) {
            queryCriteria.setIdIn(u.stream().map(Integer::parseInt).collect(Collectors.toSet()));
        }
        if (g != null && !g.isEmpty()) {
            Set<String> departmentCodes=g.stream()
                    .filter(e->e.startsWith("D_"))
                    .map(e->e.substring(2))
                    .collect(Collectors.toSet());
            if (!departmentCodes.isEmpty()) {
                queryCriteria.setDepartmentCodeIn(departmentCodes);
            }
            Set<String> roleCodes = g.stream()
                    .filter(e -> e.startsWith("R_"))
                    .map(e -> e.substring(2))
                    .collect(Collectors.toSet());
            if (!roleCodes.isEmpty()) {
                queryCriteria.setRoleCodeIn(roleCodes);
            }
        }
        //筛选角色表,角色组表
        if (queryCriteria.getRoleCodeIn()!=null && queryCriteria.getRoleCodeIn().size()!=0){
            Collection<String> list = queryCriteria.getRoleCodeIn();
            Collection<Integer> ids = systemQueryFacade.findRoleCodeInToUserIdIn(list);
            if (queryCriteria.getIdIn()!=null){
                Collection<Integer> idIn = queryCriteria.getIdIn();
                idIn.retainAll(ids);
            }else{
                queryCriteria.setIdIn(ids);
            }
        }
        //筛选机构表
        if (queryCriteria.getDepartmentCodeIn()!=null && queryCriteria.getDepartmentCodeIn().size()!=0){
            Collection<String> list = queryCriteria.getDepartmentCodeIn();
            //筛选当前登录用户机构
            for (String s : list) {
                if (s.equals("curdept")){
                    list.remove(s);
                    list.add(DataScopeUtils.loadDepartmentCode().get());
                    break;
                }
            }
            Collection<Integer> ids = systemQueryFacade.findDepartmentCodeInToUserIdIn(list);
            if (queryCriteria.getIdIn()!=null){
                Collection<Integer> idIn = queryCriteria.getIdIn();
                idIn.retainAll(ids);
            }else{
                queryCriteria.setIdIn(ids);
            }
        }
        //交集后 无数据 着不执行查询
        if (((queryCriteria.getRoleCodeIn()!=null && queryCriteria.getRoleCodeIn().size()!=0)
                ||(queryCriteria.getDepartmentCodeIn()!=null && queryCriteria.getDepartmentCodeIn().size()!=0) )
                && (queryCriteria.getIdIn()==null || queryCriteria.getIdIn().size()==0 )){
            return  ResponseEntity.ok(null);

        }
        Page<UserDto> result = systemQueryFacade.queryUserRefer(queryCriteria, pageable);

        return ResponseEntity.ok(result.map(e -> {
            UserVo userVo = new UserVo();
            userVo.setId(e.getId());
            userVo.setUsername(e.getUsername());
            userVo.setRealname(e.getRealname());
            return userVo;
        }));
    }
}
