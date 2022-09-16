package com.realfinance.sofa.cg.controller.flow;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.google.common.collect.Streams;
import com.realfinance.sofa.flow.facade.ModelerFacade;
import com.realfinance.sofa.flow.model.IdmGroupDto;
import com.realfinance.sofa.flow.model.IdmUserDto;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentQueryCriteria;
import com.realfinance.sofa.system.model.RoleQueryCriteria;
import com.realfinance.sofa.system.model.UserQueryCriteria;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Tag(name = "流程编辑器")
@Hidden
@RequestMapping("/flow/modeler-app")
public class EditorController {

    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    @SofaReference(interfaceType = ModelerFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ModelerFacade modelerFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @GetMapping(value = "/rest/models/{modelId}/model-json", produces = "application/json")
    public JSONObject getModelJSON(@PathVariable String modelId) {
        String displayNode = modelerFacade.getEditorDisplayJson(modelId);
        return JSONObject.parseObject(displayNode);
    }

    @GetMapping(value = "/rest/editor-users")
    public Object getUsers(@RequestParam(value = "filter", required = false) String filter) {
        PageRequest pageRequest = PageRequest.of(0, 20);
        UserQueryCriteria queryCriteria = new UserQueryCriteria();
        if (filter != null && !filter.isEmpty()) {
            queryCriteria.setUsernameLikeOrRealnameLike(filter);
        }
        try {
            Page<IdmUserDto> page = systemQueryFacade.queryUserRefer(queryCriteria, pageRequest).map(e -> {
                IdmUserDto idmUserDto = new IdmUserDto();
                idmUserDto.setId(e.getId().toString());
                idmUserDto.setFirstName(e.getUsername());
                idmUserDto.setLastName(e.getRealname());
                idmUserDto.setEmail(e.getEmail());
                idmUserDto.setFullName((e.getUsername() != null ? e.getUsername() : "") + " " + (e.getRealname() != null ? e.getRealname() : ""));
                idmUserDto.setTenantId(e.getTenant().getId());
                return idmUserDto;
            });
            Map<String,Object> result = new HashMap<>();
            result.put("size", page.getSize());
            result.put("total", page.getTotalElements());
            result.put("start", 0);
            result.put("data", page.getContent());
            return result;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("查询users失败",e);
            }
            Map<String,Object> result = new HashMap<>();
            result.put("size", pageRequest.getPageSize());
            result.put("total", 0);
            result.put("start", pageRequest.getPageNumber());
            result.put("data", Collections.emptyList());
            return result;
        }
    }

    @GetMapping(value = "/rest/editor-groups")
    public Object getGroups(@RequestParam(required = false, value = "filter") String filter) {
        PageRequest pageRequest = PageRequest.of(0, 20);
        RoleQueryCriteria roleQueryCriteria = new RoleQueryCriteria();
        roleQueryCriteria.setNameLike(filter);
        DepartmentQueryCriteria departmentQueryCriteria = new DepartmentQueryCriteria();
        departmentQueryCriteria.setNameLike(filter);
        try {
            Page<IdmGroupDto> page1 = systemQueryFacade.queryRoleRefer(roleQueryCriteria,pageRequest).map(e -> {
                IdmGroupDto idmGroupDto = new IdmGroupDto();
                //取出角色code
                idmGroupDto.setId("R_" + e.getCode());
                idmGroupDto.setName(e.getName());
                return idmGroupDto;
            });
            Page<IdmGroupDto> page2 = systemQueryFacade.queryDepartmentRefer(departmentQueryCriteria, pageRequest).map(e -> {
                IdmGroupDto idmGroupDto = new IdmGroupDto();
                //取出机构code
                idmGroupDto.setId("D_" + e.getCode());
                idmGroupDto.setName(e.getName());
                return idmGroupDto;
            });
            Map<String,Object> result = new HashMap<>();
            result.put("size", page1.getSize() + page2.getSize());
            result.put("total", page1.getTotalElements() + page2.getTotalElements());
            result.put("start", 0);
            result.put("data", Streams.concat(page1.getContent().stream(),page2.getContent().stream()).collect(Collectors.toList()));
            return result;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("查询groups失败",e);
            }
            Map<String,Object> result = new HashMap<>();
            result.put("size", pageRequest.getPageSize());
            result.put("total", 0);
            result.put("start", pageRequest.getPageNumber());
            result.put("data", Collections.emptyList());
            return result;
        }
    }
}
