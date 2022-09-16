package com.realfinance.sofa.cg.controller.cg.exp;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.core.facade.CgExpertLabelFacade;
import com.realfinance.sofa.cg.core.facade.CgExpertLabelTypeFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgExpertLabelTypeVo;
import com.realfinance.sofa.cg.model.cg.CgExpertLabelVo;
import com.realfinance.sofa.cg.service.mapstruct.CgExpertLabelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Tag(name = "专家标签管理")
@RequestMapping("/cg/core/expertlabel")
public class ExpertLabelController {

    private static final Logger log = LoggerFactory.getLogger(ExpertLabelController.class);

    public static final String MENU_CODE_ROOT = "expertlabel";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";
    public static final String MENU_CODE_TYPEMNG = MENU_CODE_ROOT + ":typemng"; // 标签类型管理权限

    @SofaReference(interfaceType = CgExpertLabelFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelFacade cgExpertLabelFacade;
    @SofaReference(interfaceType = CgExpertLabelTypeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgExpertLabelTypeFacade cgExpertLabelTypeFacade;

    @Resource
    private CgExpertLabelMapper cgExpertLabelMapper;


    @GetMapping("/listtype")
    @Operation(summary = "查询标签类型")
    public ResponseEntity<Page<CgExpertLabelTypeVo>> listTypes(@Parameter(description = "过滤条件") @RequestParam(required = false) String filter,
                                                                 Pageable pageable) {
        Page<CgExpertLabelTypeDto> result = cgExpertLabelTypeFacade.list(filter, pageable);
        return ResponseEntity.ok(result.map(cgExpertLabelMapper::toVo));
    }

   @PostMapping("savetype")
    @Operation(summary = "保存标签类型")
    public ResponseEntity<Integer> saveType(@Validated(CgExpertLabelTypeVo.Save.class)
                                            @RequestBody
                                            @Schema(implementation = CgExpertLabelTypeVo.Save.class) CgExpertLabelTypeVo vo) {
        CgExpertLabelTypeSaveDto saveDto = cgExpertLabelMapper.toSaveDto(vo);
        Integer id = cgExpertLabelTypeFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }
 
    @DeleteMapping("deleteType")
    @Operation(summary = "删除标签类型")
    public ResponseEntity<?> deleteType(@Parameter(description = "采购供应商标签类型ID") @RequestParam Set<Integer> expertLabelTypeId) {
        cgExpertLabelTypeFacade.delete(expertLabelTypeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("listfirstlevel")
    @Operation(summary = "查询第一级标签")
    public ResponseEntity<List<CgExpertLabelVo>> listFirstLevel(@Parameter(description = "采购供应商标签类型ID") @RequestParam Integer expertLabelTypeId) {
        CgExpertLabelQueryCriteria queryCriteria = new CgExpertLabelQueryCriteria();
        queryCriteria.setExpertLabelTypeId(expertLabelTypeId);
        queryCriteria.setParentIsNull(true);
        List<CgExpertLabelDto> result = cgExpertLabelFacade.list(queryCriteria);
        return ResponseEntity.ok(result.stream().map(cgExpertLabelMapper::toVo).collect(Collectors.toList()));
    }

    @GetMapping("listbyparentid")
    @Operation(summary = "根据父节点ID查询标签")
    public ResponseEntity<List<CgExpertLabelVo>> listByParentId(@Parameter(description = "上级标签ID") @RequestParam(value = "parentId",required = false) Integer parentId) {
        CgExpertLabelQueryCriteria queryCriteria = new CgExpertLabelQueryCriteria();
        queryCriteria.setParentId(parentId);
        List<CgExpertLabelDto> result = cgExpertLabelFacade.list(queryCriteria);
        return ResponseEntity.ok(result.stream().map(cgExpertLabelMapper::toVo).collect(Collectors.toList()));
    }

    @PostMapping("save")
    @Operation(summary = "保存标签")
    public ResponseEntity<Integer> save(@Validated(CgExpertLabelVo.Save.class)
                                        @RequestBody
                                        @Schema(implementation = CgExpertLabelVo.Save.class) CgExpertLabelVo vo) {
        CgExpertLabelSaveDto saveDto = cgExpertLabelMapper.toSaveDto(vo);
        Integer id = cgExpertLabelFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除标签")
    public ResponseEntity<?> delete(@Parameter(description = "标签ID") @RequestParam Set<Integer> id) {
        cgExpertLabelFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
