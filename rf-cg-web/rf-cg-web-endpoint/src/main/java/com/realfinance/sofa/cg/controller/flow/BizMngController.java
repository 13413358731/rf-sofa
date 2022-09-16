package com.realfinance.sofa.cg.controller.flow;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.flow.BizSaveRequest;
import com.realfinance.sofa.cg.model.flow.BizVo;
import com.realfinance.sofa.cg.service.mapstruct.FlowBizMapper;
import com.realfinance.sofa.flow.facade.BizMngFacade;
import com.realfinance.sofa.flow.model.BizDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.Set;

@RestController
@Tag(name = "业务管理")
@RequestMapping("/process/bizmng")
public class BizMngController {

    private static final Logger log = LoggerFactory.getLogger(BizMngController.class);

    public static final String MENU_CODE_ROOT = "bizmng";

    @SofaReference(interfaceType = BizMngFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private BizMngFacade bizMngFacade;

    @Resource
    private FlowBizMapper flowBizMapper;

    @GetMapping("list")
    @Operation(summary = "查询列表")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<BizVo>> list(@Parameter(description = "过滤条件") @RequestParam(required = false) String filter,
                                            Pageable pageable) {
        Page<BizDto> result = bizMngFacade.list(filter, pageable);
        return ResponseEntity.ok(result.map(flowBizMapper::bizDto2BizVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Integer> save(@Validated @RequestBody BizSaveRequest bizSaveRequest) {
        Integer id = bizMngFacade.save(flowBizMapper.bizSaveRequest2BizSaveDto(bizSaveRequest));
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> delete(@Parameter(description = "业务ID") @RequestParam Set<Integer> id) {
        bizMngFacade.delete(id);
        return ResponseEntity.ok().build();
    }
}
